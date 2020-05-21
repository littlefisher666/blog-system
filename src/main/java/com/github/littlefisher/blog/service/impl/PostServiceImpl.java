package com.github.littlefisher.blog.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.littlefisher.blog.configuration.qiniu.client.QiniuClient;
import com.github.littlefisher.blog.configuration.qiniu.dto.QiniuDownloadResponseDto;
import com.github.littlefisher.blog.configuration.qiniu.dto.QiniuUploadResponseDto;
import com.github.littlefisher.blog.configuration.sftp.client.SftpClient;
import com.github.littlefisher.blog.controller.dto.PostDto;
import com.github.littlefisher.blog.controller.dto.SimplePostDto;
import com.github.littlefisher.blog.controller.dto.TagDto;
import com.github.littlefisher.blog.controller.dto.request.InsertPostRequestDto;
import com.github.littlefisher.blog.controller.dto.request.UpdatePostRequestDto;
import com.github.littlefisher.blog.dao.AuthorDao;
import com.github.littlefisher.blog.dao.PostDao;
import com.github.littlefisher.blog.dao.PostTagRelationDao;
import com.github.littlefisher.blog.dao.TagDao;
import com.github.littlefisher.blog.dao.model.AuthorDo;
import com.github.littlefisher.blog.dao.model.PostDo;
import com.github.littlefisher.blog.dao.model.PostTagRelationDo;
import com.github.littlefisher.blog.dao.model.TagDo;
import com.github.littlefisher.blog.dao.model.ext.PostTagRelationWithNameDo;
import com.github.littlefisher.blog.service.PostService;
import com.github.littlefisher.mybatis.pagehelper.PageInfo;
import com.github.littlefisher.mybatis.pagehelper.PageParam;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jinyanan
 * @since 2019-07-29 11:04
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private PostTagRelationDao postTagRelationDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private SftpClient sftpClient;

    @Autowired
    private QiniuClient qiniuClient;

    private static final String PATTERN_STR = "<!--[\\s]*more[\\s]*-->";

    private static final Pattern PATTERN = Pattern.compile(PATTERN_STR);

    /** 文件上传默认前缀 */
    private static final String DEFAULT_FILE_PREFIX = File.separator + "upload";

    /** markdown文件后缀 */
    private static final String MARKDOWN_SUFFIX = ".md";

    @Override
    public PageInfo<SimplePostDto> queryPostByAuthorId(Integer authorId, Integer tagId, PageParam page) {
        PageInfo<PostDo> pageInfo = postDao.findByAuthorIdAndTag(authorId, tagId, page);
        if (pageInfo.getSize() == 0) {
            return new PageInfo<>();
        }
        List<Integer> authorIdList = pageInfo.getList()
            .stream()
            .map(PostDo::getAuthorId)
            .distinct()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        List<Integer> postIdList = pageInfo.getList()
            .stream()
            .map(PostDo::getPostId)
            .distinct()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        Map<Integer, AuthorDo> authorMap = queryAuthorInfo(authorIdList);
        Map<Integer, List<TagDto>> tagMap = queryPostTag(postIdList);
        return new PageInfo<>(pageInfo, input -> {
            AuthorDo author = authorMap.get(input.getAuthorId());
            List<TagDto> tagList = tagMap.get(input.getPostId());
            return SimplePostDto.builder()
                .authorId(input.getAuthorId())
                .authorName(author == null ? null : author.getName())
                .likedTimes(input.getLikedTimes())
                .createTime(input.getCreateTime())
                .postId(input.getPostId())
                .previewContent(input.getPreview())
                .readTimes(input.getReadTimes())
                .tagList(CollectionUtils.isEmpty(tagList) ? Lists.newArrayList() : tagList)
                .title(input.getTitle())
                .build();
        });
    }

    private Map<Integer, AuthorDo> queryAuthorInfo(List<Integer> authorIdList) {
        List<AuthorDo> authorList = authorDao.findAllByIdList(authorIdList);
        return CollectionUtils.isEmpty(authorList) ? Maps.newHashMap() : authorList.stream()
            .collect(Collectors.toMap(AuthorDo::getAuthorId, input -> input));
    }

    private Map<Integer, List<TagDto>> queryPostTag(List<Integer> postIdList) {
        List<PostTagRelationDo> relationList = postTagRelationDao.findByPostIdIn(postIdList);
        List<Integer> tagIdList = relationList.stream()
            .map(PostTagRelationDo::getTagId)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(relationList)) {
            return Maps.newHashMap();
        }
        List<TagDo> tagList = tagDao.findAllByIdList(tagIdList);
        Map<Integer, TagDo> tagMap = tagList.stream()
            .collect(Collectors.toMap(TagDo::getCode, input -> input));
        Map<Integer, List<PostTagRelationDo>> tagRelationMap = relationList.stream()
            .collect(Collectors.groupingBy(PostTagRelationDo::getPostId));
        Map<Integer, List<TagDto>> postTagMap = Maps.newHashMapWithExpectedSize(tagRelationMap.size());
        tagRelationMap.forEach((key, value) -> {
            List<Integer> tagIdPerPostList = CollectionUtils.isEmpty(value) ? Lists.newArrayList() : value.stream()
                .map(PostTagRelationDo::getTagId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
            List<TagDto> tagPerPostList = tagIdPerPostList.stream()
                .map(tagMap::get)
                .filter(Objects::nonNull)
                .map(input -> TagDto.builder()
                    .code(input.getCode())
                    .name(input.getName())
                    .build())
                .collect(Collectors.toList());
            postTagMap.put(key, tagPerPostList);
        });
        return postTagMap;
    }

    @Override
    public PostDto queryPostContent(Integer postId) {
        PostDo post = postDao.selectByPrimaryKey(postId);
        if (post == null) {
            return null;
        } else {
            String contentUrl = post.getContentUrl();
            Map<Integer, AuthorDo> authorMap = queryAuthorInfo(Lists.newArrayList(post.getAuthorId()));
            AuthorDo author = authorMap.get(post.getAuthorId());
            Map<Integer, List<TagDto>> tagListMap = queryPostTag(Lists.newArrayList(postId));
            List<TagDto> tagList = tagListMap.get(postId);
            QiniuDownloadResponseDto qiniuDownloadResponse = qiniuClient.download(post.getFilePath());
            return PostDto.builder()
                .postId(postId)
                .content(new String(qiniuDownloadResponse.getData(), StandardCharsets.UTF_8))
                .authorId(post.getAuthorId())
                .authorName(author == null ? null : author.getName())
                .likedTimes(post.getLikedTimes())
                .createTime(post.getCreateTime())
                .previewContent(post.getPreview())
                .readTimes(post.getReadTimes())
                .tagList(CollectionUtils.isEmpty(tagList) ? Lists.newArrayList() : tagList)
                .title(post.getTitle())
                .build();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void read(Integer postId) {
        postDao.updateReadTimes(postId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertPost(InsertPostRequestDto request) {
        LocalDateTime now = LocalDateTime.now();
        // 上传
        String fileUrl = sftpClient.uploadFile(DEFAULT_FILE_PREFIX + File.separator + now.toLocalDate()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd")), request.getTitle() + MARKDOWN_SUFFIX,
            processContent(request.getContent()).getBytes());
        // 保存博文
        PostDo post = new PostDo();
        post.setAuthorId(request.getAuthorId());
        post.setContentUrl(fileUrl);
        post.setPreview(processOverview(request.getContent()));
        post.setTitle(request.getTitle());
        post.setLikedTimes(0);
        post.setReadTimes(0);
        post.setCreateTime(now);
        post.setUpdateTime(now);
        postDao.insert(post);
        // 保存tag
        request.getTagList()
            .forEach(tag -> saveTag(post.getPostId(), tag));
    }

    /**
     * 保存tag
     *
     * @param postId 博文id
     * @param tag 标签
     */
    private void saveTag(Integer postId, TagDto tag) {
        LocalDateTime now = LocalDateTime.now();
        TagDo tagInDb;
        if (tag.getCode() == null) {
            Optional<TagDo> tagOptional = tagDao.findOneByName(tag.getName());
            tagInDb = tagOptional.orElseGet(() -> {
                TagDo newTag = new TagDo();
                newTag.setName(tag.getName());
                newTag.setCreateTime(now);
                newTag.setUpdateTime(now);
                tagDao.insert(newTag);
                return newTag;
            });
        } else {
            tagInDb = new TagDo();
            tagInDb.setCode(tag.getCode());
            tagInDb.setName(tag.getName());
        }

        PostTagRelationDo relation = new PostTagRelationDo();
        relation.setPostId(postId);
        relation.setTagId(tagInDb.getCode());
        relation.setCreateTime(now);
        relation.setUpdateTime(now);
        postTagRelationDao.insert(relation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePost(Integer postId, UpdatePostRequestDto request) {
        LocalDateTime now = LocalDateTime.now();
        PostDo postInDb = postDao.selectByPrimaryKey(postId);
        String oldContentUrl = postInDb.getContentUrl();
        sftpClient.rm(oldContentUrl);
        sftpClient.uploadFile(DEFAULT_FILE_PREFIX + File.separator + postInDb.getCreateTime()
                .toLocalDate()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd")), request.getTitle() + MARKDOWN_SUFFIX,
            processContent(request.getContent()).getBytes());

        PostDo post = new PostDo();
        post.setAuthorId(request.getAuthorId());
        post.setContentUrl(postInDb.getContentUrl());
        post.setPreview(processOverview(request.getContent()));
        post.setTitle(request.getTitle());
        post.setLikedTimes(postInDb.getLikedTimes());
        post.setReadTimes(postInDb.getReadTimes());
        post.setCreateTime(postInDb.getCreateTime());
        post.setUpdateTime(now);
        postDao.insert(post);
        // 保存tag
        List<PostTagRelationWithNameDo> postTagRelations = postTagRelationDao.findAllTagRelationByPostId(postId);
        request.getTagList()
            .stream()
            // 过滤已存在关联的tag
            .filter(input -> postTagRelations.stream()
                .noneMatch(relation -> relation.getTagId()
                    .equals(input.getCode()) && relation.getName()
                    .equals(input.getName())))
            .forEach(tag -> saveTag(postId, tag));
    }

    @Override
    public void convert2Qiniu() {
        List<PostDo> postList = postDao.selectAll();
        for (PostDo post : postList) {
            String contentUrl = post.getContentUrl();
            byte[] file = sftpClient.getFile(contentUrl);
            String fileName = contentUrl.substring(contentUrl.lastIndexOf('/') + 1);
            QiniuUploadResponseDto qiniuUploadResponse = qiniuClient.upload(file, fileName);
            post.setFilePath(qiniuUploadResponse.getFilePath());
            postDao.updateByPrimaryKey(post);
        }
    }

    private String processContent(String mainBody) {
        return mainBody.replaceAll(PATTERN_STR, StringUtils.EMPTY);
    }

    private String processOverview(String mainBody) {
        Matcher matcher = PATTERN.matcher(mainBody);
        if (matcher.find()) {
            int moreStartIndex = matcher.start();
            return mainBody.substring(0, moreStartIndex);
        } else {
            return mainBody;
        }
    }

}
