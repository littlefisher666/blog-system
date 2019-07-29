package com.github.littlefisher.blog.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
import com.github.littlefisher.blog.service.PostService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author jinyanan
 * @since 2019-07-29 11:04
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PostTagRelationRepository postTagRelationRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Page<SimplePostDto> queryPostByAuthorId(Integer authorId, PageRequest page) {
        Post query = new Post();
        query.setAuthorId(authorId);
        Example<Post> example = Example.of(query);
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
            .map(post -> PostDto.builder()
                .postId(postId)
                .content(post.getContent())
                .build())
            .orElse(null);
    }
}
