package com.github.littlefisher.blog.dao.impl;

import org.springframework.stereotype.Repository;

import com.github.littlefisher.blog.configuration.dao.AbstractBaseDaoImpl;
import com.github.littlefisher.blog.dao.ProvinceDao;
import com.github.littlefisher.blog.dao.mapper.ProvinceDoMapper;
import com.github.littlefisher.blog.dao.model.ProvinceDo;

/**
 * @author jinyanan
 * @since 2019/11/26 10:11
 */
@Repository
public class ProvinceDaoImpl extends AbstractBaseDaoImpl<ProvinceDo, ProvinceDoMapper> implements ProvinceDao {
}
