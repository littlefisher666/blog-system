package com.github.littlefisher.blog.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.littlefisher.blog.controller.dto.CityDto;
import com.github.littlefisher.blog.controller.dto.CurrentAuthorDto;
import com.github.littlefisher.blog.controller.dto.ProvinceDto;
import com.github.littlefisher.blog.controller.dto.TagDto;
import com.github.littlefisher.blog.dao.entity.Author;
import com.github.littlefisher.blog.dao.entity.AuthorTagRelation;
import com.github.littlefisher.blog.dao.entity.City;
import com.github.littlefisher.blog.dao.entity.Province;
import com.github.littlefisher.blog.dao.entity.Tag;
import com.github.littlefisher.blog.dao.repository.AuthorRepository;
import com.github.littlefisher.blog.dao.repository.AuthorTagRelationRepository;
import com.github.littlefisher.blog.dao.repository.CityRepository;
import com.github.littlefisher.blog.dao.repository.ProvinceRepository;
import com.github.littlefisher.blog.dao.repository.TagRepository;
import com.github.littlefisher.blog.service.AuthorService;

/**
 * @author jinyanan
 * @since 2019-07-25 17:55
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Integer DEFAULT_AUTHOR_ID = 1;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private AuthorTagRelationRepository authorTagRelationRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public CurrentAuthorDto queryCurrentAuthor() {
        Author currentAuthor = authorRepository.getOne(DEFAULT_AUTHOR_ID);
        return CurrentAuthorDto.builder()
            .authorId(currentAuthor.getAuthorId())
            .address(currentAuthor.getAddress())
            .avatar(currentAuthor.getAvatar())
            .email(currentAuthor.getEmail())
            .group(currentAuthor.getGroup())
            .job(currentAuthor.getJob())
            .name(currentAuthor.getName())
            .phone(currentAuthor.getPhone())
            .signature(currentAuthor.getSignature())
            .city(queryAuthorCity(currentAuthor.getCityCode()))
            .authorTags(queryAuthorTag(currentAuthor.getAuthorId()))
            .postTags(queryPostTag(currentAuthor.getAuthorId()))
            .build();
    }

    private CityDto queryAuthorCity(Integer cityCode) {
        City city = cityRepository.getOne(cityCode);
        Province province = provinceRepository.getOne(city.getProvinceCode());
        return CityDto.builder()
            .code(city.getCode())
            .name(city.getName())
            .province(ProvinceDto.builder()
                .name(province.getName())
                .code(province.getCode())
                .build())
            .build();
    }

    private List<TagDto> queryAuthorTag(Integer authorId) {
        List<AuthorTagRelation> relationList = authorTagRelationRepository.getByAuthorId(authorId);
        if (CollectionUtils.isNotEmpty(relationList)) {
            List<Tag> tagList = tagRepository.findAllById(relationList.stream()
                .map(AuthorTagRelation::getTagCode)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList()));
            return tagList.stream()
                .map(input -> TagDto.builder()
                    .code(input.getCode())
                    .name(input.getName())
                    .build())
                .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private List<TagDto> queryPostTag(Integer authorId) {
        List<Tag> tagList = tagRepository.queryPostTagByAuthorId(authorId);
        return tagList.stream()
            .map(input -> TagDto.builder()
                .name(input.getName())
                .code(input.getCode())
                .build())
            .collect(Collectors.toList());
    }
}
