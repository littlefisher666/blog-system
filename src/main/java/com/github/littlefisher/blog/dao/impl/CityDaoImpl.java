package com.github.littlefisher.blog.dao.impl;

import org.springframework.stereotype.Repository;

import com.github.littlefisher.blog.configuration.dao.AbstractBaseDaoImpl;
import com.github.littlefisher.blog.dao.CityDao;
import com.github.littlefisher.blog.dao.mapper.CityDoMapper;
import com.github.littlefisher.blog.dao.model.CityDo;

/**
 * @author jinyanan
 * @since 2019/11/26 10:11
 */
@Repository
public class CityDaoImpl extends AbstractBaseDaoImpl<CityDo, CityDoMapper> implements CityDao {
}
