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
import com.github.littlefisher.blog.dao.AuthorDao;
import com.github.littlefisher.blog.dao.AuthorTagRelationDao;
import com.github.littlefisher.blog.dao.CityDao;
import com.github.littlefisher.blog.dao.ProvinceDao;
import com.github.littlefisher.blog.dao.TagDao;
import com.github.littlefisher.blog.dao.model.AuthorDo;
import com.github.littlefisher.blog.dao.model.AuthorTagRelationDo;
import com.github.littlefisher.blog.dao.model.CityDo;
import com.github.littlefisher.blog.dao.model.ProvinceDo;
import com.github.littlefisher.blog.dao.model.TagDo;
import com.github.littlefisher.blog.service.AuthorService;

/**
 * @author jinyanan
 * @since 2019-07-25 17:55
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Integer DEFAULT_AUTHOR_ID = 1;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private ProvinceDao provinceDao;

    @Autowired
    private AuthorTagRelationDao authorTagRelationDao;

    @Autowired
    private TagDao tagDao;

    @Override
    public CurrentAuthorDto queryCurrentAuthor() {
        AuthorDo currentAuthor = authorDao.selectByPrimaryKey(DEFAULT_AUTHOR_ID);
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
        CityDo city = cityDao.selectByPrimaryKey(cityCode);
        ProvinceDo province = provinceDao.selectByPrimaryKey(city.getProvinceCode());
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
        List<AuthorTagRelationDo> relationList = authorTagRelationDao.getByAuthorId(authorId);
        if (CollectionUtils.isNotEmpty(relationList)) {
            List<TagDo> tagList = tagDao.findAllByIdList(relationList.stream()
                .map(AuthorTagRelationDo::getTagCode)
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
        List<TagDo> tagList = tagDao.queryPostTagByAuthorId(authorId);
        return tagList.stream()
            .map(input -> TagDto.builder()
                .name(input.getName())
                .code(input.getCode())
                .build())
            .collect(Collectors.toList());
    }
}
