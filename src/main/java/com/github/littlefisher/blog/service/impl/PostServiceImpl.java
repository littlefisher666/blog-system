package com.github.littlefisher.blog.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.littlefisher.blog.configuration.sftp.client.SftpClient;
import com.github.littlefisher.blog.controller.dto.PostDto;
import com.github.littlefisher.blog.controller.dto.SimplePostDto;
import com.github.littlefisher.blog.controller.dto.TagDto;
import com.github.littlefisher.blog.dao.entity.Author;
import com.github.littlefisher.blog.dao.entity.Post;
import com.github.littlefisher.blog.dao.entity.PostTagRelation;
import com.github.littlefisher.blog.dao.entity.Tag;
import com.github.littlefisher.blog.dao.repository.AuthorRepository;
import com.github.littlefisher.blog.dao.repository.PostRepository;
import com.github.littlefisher.blog.dao.repository.PostTagRelationRepository;
import com.github.littlefisher.blog.dao.repository.TagRepository;
import com.github.littlefisher.blog.exception.FileNotFoundException;
import com.github.littlefisher.blog.service.PostService;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jinyanan
 * @since 2019-07-29 11:04
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PostTagRelationRepository postTagRelationRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SftpClient sftpClient;

    private static final String PATTERN_STR = "<!--[\\s]*more[\\s]*-->\\n";

    private static final Pattern PATTERN = Pattern.compile(PATTERN_STR);

    private static final Integer DEFAULT_AUTHOR_ID = 1;

    /** 文件上传默认前缀 */
    private static final String DEFAULT_FILE_PREFIX = File.separator + "upload";

    /** markdown文件后缀 */
    private static final String MARKDOWN_SUFFIX = ".md";

    @Override
    public Page<SimplePostDto> queryPostByAuthorId(Integer authorId, PageRequest page) {
        Post query = new Post();
        query.setAuthorId(authorId);
        Example<Post> example = Example.of(query);
        page = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(Sort.Order.desc("createTime")));
        Page<Post> postPage = postRepository.findAll(example, page);
        if (postPage.isEmpty()) {
            return Page.empty(page);
        }
        List<Integer> authorIdList = postPage.stream()
            .map(Post::getAuthorId)
            .distinct()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        List<Integer> postIdList = postPage.stream()
            .map(Post::getPostId)
            .distinct()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        Map<Integer, Author> authorMap = queryAuthorInfo(authorIdList);
        Map<Integer, List<TagDto>> tagMap = queryPostTag(postIdList);
        return postPage.map(input -> {
            Author author = authorMap.get(input.getAuthorId());
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

    private Map<Integer, Author> queryAuthorInfo(List<Integer> authorIdList) {
        List<Author> authorList = authorRepository.findAllById(authorIdList);
        return CollectionUtils.isEmpty(authorList) ? Maps.newHashMap() : authorList.stream()
            .collect(Collectors.toMap(Author::getAuthorId, input -> input));
    }

    private Map<Integer, List<TagDto>> queryPostTag(List<Integer> postIdList) {
        List<PostTagRelation> relationList = postTagRelationRepository.findByPostIdIn(postIdList);
        List<Integer> tagIdList = relationList.stream()
            .map(PostTagRelation::getTagId)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(relationList)) {
            return Maps.newHashMap();
        }
        List<Tag> tagList = tagRepository.findAllById(tagIdList);
        Map<Integer, Tag> tagMap = tagList.stream()
            .collect(Collectors.toMap(Tag::getCode, input -> input));
        Map<Integer, List<PostTagRelation>> tagRelationMap = relationList.stream()
            .collect(Collectors.groupingBy(PostTagRelation::getPostId));
        Map<Integer, List<TagDto>> postTagMap = Maps.newHashMapWithExpectedSize(tagRelationMap.size());
        tagRelationMap.forEach((key, value) -> {
            List<Integer> tagIdPerPostList = CollectionUtils.isEmpty(value) ? Lists.newArrayList() : value.stream()
                .map(PostTagRelation::getTagId)
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
        return postRepository.findById(postId)
            .map(post -> {
                String contentUrl = post.getContentUrl();
                Map<Integer, Author> authorMap = queryAuthorInfo(Lists.newArrayList(post.getAuthorId()));
                Author author = authorMap.get(post.getAuthorId());
                Map<Integer, List<TagDto>> tagListMap = queryPostTag(Lists.newArrayList(postId));
                List<TagDto> tagList = tagListMap.get(postId);
                byte[] content = sftpClient.getFile(contentUrl);
                return PostDto.builder()
                    .postId(postId)
                    .content(new String(content))
                    .authorId(post.getAuthorId())
                    .authorName(author == null ? null : author.getName())
                    .likedTimes(post.getLikedTimes())
                    .createTime(post.getCreateTime())
                    .previewContent(post.getPreview())
                    .readTimes(post.getReadTimes())
                    .tagList(CollectionUtils.isEmpty(tagList) ? Lists.newArrayList() : tagList)
                    .title(post.getTitle())
                    .build();
            })
            .orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void loanFromDisk(String directoryPath, String statisticPath) {
        List<MarkdownInfo> markdownInfoList = getMarkdown(directoryPath);
        Map<String, Integer> readTimesMap = getReadTimes(statisticPath);
        insertMarkdown(markdownInfoList, readTimesMap);
    }

    private void insertMarkdown(List<MarkdownInfo> markdownInfoList, Map<String, Integer> readTimesMap) {
        markdownInfoList.forEach(markdown -> {
            log.debug("start insert post, title: [{}]", markdown.getTitle()
                .getTitle());
            Integer readTimes = readTimesMap.get(markdown.getTitle()
                .getTitle());
            Post post = new Post();
            post.setAuthorId(DEFAULT_AUTHOR_ID);
            // post.setContent(markdown.getContent());
            post.setCreateTime(markdown.getTitle()
                .getCreateTime()
                .atTime(0, 0));
            post.setLikedTimes(0);
            post.setPreview(markdown.getOverview());
            post.setReadTimes(readTimes);
            post.setTitle(markdown.getTitle()
                .getTitle());
            post.setUpdateTime(LocalDateTime.now());
            Post postInDb = postRepository.save(post);
            List<String> tags = markdown.getTitle()
                .getTags();
            tags.forEach(tag -> {
                Optional<Tag> tagOptional = tagRepository.findOneByName(tag);
                Tag tagInDb = tagOptional.orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setName(tag);
                    newTag.setCreateTime(LocalDateTime.now());
                    newTag.setUpdateTime(LocalDateTime.now());
                    return tagRepository.save(newTag);
                });
                PostTagRelation relation = new PostTagRelation();
                relation.setPostId(postInDb.getPostId());
                relation.setTagId(tagInDb.getCode());
                relation.setCreateTime(LocalDateTime.now());
                relation.setUpdateTime(LocalDateTime.now());
                postTagRelationRepository.save(relation);
            });
            log.debug("end insert post, title: [{}]", markdown.getTitle()
                .getTitle());
        });
    }

    private Map<String, Integer> getReadTimes(String statisticPath) {
        Path path = Paths.get(statisticPath);
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new FileNotFoundException("文件路径不存在");
        }
        return lines.stream()
            .map(line -> {
                List<String> statistic = Splitter.on(',')
                    .trimResults()
                    .omitEmptyStrings()
                    .splitToList(line);
                String title = statistic.get(0)
                    .startsWith("/") ? statistic.get(0)
                    .substring("/".length()) : statistic.get(0);
                Integer readTimes = StringUtils.isEmpty(statistic.get(1)) ? 0 : Integer.parseInt(statistic.get(1));
                return ReadTimes.builder()
                    .title(title)
                    .readTimes(readTimes)
                    .build();
            })
            .collect(Collectors.toMap(ReadTimes::getTitle, ReadTimes::getReadTimes, Integer::sum));

    }

    private List<MarkdownInfo> getMarkdown(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (Files.isDirectory(path)) {
            MarkdownVisitor visitor = new MarkdownVisitor(".md");
            try {
                Files.walkFileTree(path, visitor);
            } catch (IOException e) {
                throw new FileNotFoundException("文件路径不存在");
            }
            return visitor.getMarkdownFilePath()
                .stream()
                .map(this::processMarkdown)
                .collect(Collectors.toList());
        } else {
            throw new FileNotFoundException("文件路径不存在");
        }

    }

    private MarkdownInfo processMarkdown(Path path) {
        String markdown = readFile(path);
        String titleStr = title(markdown);
        Title title = processTitle(titleStr);
        String mainBody = markdown.substring(titleStr.length());
        String overview = processOverview(mainBody);
        String content = processContent(mainBody);
        return MarkdownInfo.builder()
            .title(title)
            .overview(overview)
            .content(content)
            .build();
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

    private String readFile(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new FileNotFoundException("文件路径不存在");
        }
    }

    private String title(String markdown) {
        int beginIndex = markdown.indexOf("---");
        int endIndex = markdown.indexOf("---", beginIndex + "---".length());
        return markdown.substring(beginIndex, endIndex + "---".length());
    }

    private Title processTitle(String titleStr) {
        int beginIndex = titleStr.indexOf("---") + "---".length();
        int endIndex = titleStr.indexOf("---", beginIndex);
        titleStr = titleStr.substring(beginIndex, endIndex);
        List<String> lines = Splitter.on('\n')
            .trimResults()
            .omitEmptyStrings()
            .splitToList(titleStr);
        Title title = new Title();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (String line : lines) {
            List<String> items = Splitter.on(':')
                .trimResults()
                .omitEmptyStrings()
                .splitToList(line);
            if (Title.TITLE.equals(items.get(0))) {
                title.setTitle(items.get(1));
                continue;
            }
            if (Title.CREATE_TIME.equals(items.get(0))) {
                title.setCreateTime(LocalDate.parse(items.get(1), formatter));
                continue;
            }
            if (Title.CATEGORY.equals(items.get(0))) {
                title.setCategory(items.get(1));
                continue;
            }
            if (Title.TAGS.equals(items.get(0))) {
                String value = items.get(1);
                if (value.startsWith("[") && value.endsWith("]")) {
                    List<String> tags = Splitter.on(',')
                        .trimResults()
                        .omitEmptyStrings()
                        .splitToList(value.substring(value.indexOf("[") + "[".length(), value.lastIndexOf("]")));
                    title.setTags(tags);
                } else {
                    title.setTags(Lists.newArrayList(value));
                }
            }
        }
        return title;
    }

    @Data
    @NoArgsConstructor
    private static class Title {

        private static final String TITLE = "title";

        private static final String CREATE_TIME = "date";

        private static final String CATEGORY = "categories";

        private static final String TAGS = "tags";

        private String title;

        private LocalDate createTime;

        private String category;

        private List<String> tags = new ArrayList<>();
    }

    @Data
    @Builder
    private static class MarkdownInfo {

        private Title title;

        private String overview;

        private String content;
    }

    @Data
    @Builder
    private static class ReadTimes {

        private String title;

        private Integer readTimes;
    }

    private static class MarkdownVisitor extends SimpleFileVisitor<Path> {

        private String fileSuffix;

        @Getter
        private List<Path> markdownFilePath = Lists.newArrayList();

        MarkdownVisitor(String fileSuffix) {
            this.fileSuffix = fileSuffix;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (file.toString()
                .endsWith(fileSuffix)) {
                markdownFilePath.add(file);
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
