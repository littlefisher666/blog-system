package com.github.littlefisher.blog.service.impl;

import com.github.littlefisher.blog.controller.dto.CurrentAuthorDto;
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
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        if (currentAuthor != null) {
            City city = cityRepository.getOne(currentAuthor.getCityCode());
            if (city != null) {
                Province province = provinceRepository.getOne(city.getProvinceCode());
            }
            List<Tag> tagList = queryAuthorTag(currentAuthor.getAuthorId());
        }
        return null;
    }

    private List<Tag> queryAuthorTag(Integer authorId) {
        List<AuthorTagRelation> relationList = authorTagRelationRepository.getByAuthorId(authorId);
        if (CollectionUtils.isNotEmpty(relationList)) {
            return tagRepository.findAllById(relationList.stream().map(AuthorTagRelation::getTagCode).filter(Objects::nonNull).distinct().collect(Collectors.toList()));
        }
        return Collections.emptyList();
    }
}
