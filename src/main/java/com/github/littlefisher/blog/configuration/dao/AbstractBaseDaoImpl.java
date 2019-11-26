package com.github.littlefisher.blog.configuration.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.littlefisher.mybatis.common.CommonMapper;

/**
 * @author jinyanan
 * @since 2019/11/26 10:01
 */
public abstract class AbstractBaseDaoImpl<T, D extends CommonMapper<T>> implements IBaseDao<T> {

    @Autowired
    private D mapper;

    @Override
    public T insert(T record) {
        mapper.insert(record);
        return record;
    }

    @Override
    public T insertSelective(T record) {
        mapper.insertSelective(record);
        return record;
    }

    @Override
    public int deleteByRecord(T record) {
        return mapper.delete(record);
    }

    @Override
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public List<T> selectByRecord(T record) {
        return mapper.select(record);
    }

    @Override
    public T selectOneByRecord(T record) {
        return mapper.selectOne(record);
    }

    @Override
    public T selectByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int selectCountByRecord(T record) {
        return mapper.selectCount(record);
    }

    @Override
    public boolean existsWithPrimaryKey(Object key) {
        return mapper.existsWithPrimaryKey(key);
    }

    @Override
    public T updateByPrimaryKey(T record) {
        mapper.updateByPrimaryKey(record);
        return record;
    }

    @Override
    public T updateByPrimaryKeySelective(T record) {
        mapper.updateByPrimaryKeySelective(record);
        return record;
    }

    public D getMapper() {
        return mapper;
    }

}
