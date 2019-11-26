package com.github.littlefisher.blog.configuration.dao;

import java.util.List;

/**
 * @author jinyanan
 * @since 2019/11/26 10:00
 */
public interface IBaseDao<T> {

    /**
     * 全量插入数据
     *
     * @param record 数据
     * @return 数据
     */
    T insert(T record);

    /**
     * 插入非null数据
     *
     * @param record 数据
     * @return 数据
     */
    T insertSelective(T record);

    /**
     * 根据record删除数据
     *
     * @param record 数据
     * @return 影响数据条数
     */
    int deleteByRecord(T record);

    /**
     * 根据主键删除数据
     *
     * @param key 主键
     * @return 影响数据条数
     */
    int deleteByPrimaryKey(Object key);

    /**
     * 根据record中非null作为条件查询数据
     *
     * @param record 数据条件
     * @return 查询出来的数据
     */
    List<T> selectByRecord(T record);

    /**
     * 根据record中非null作为条件查询数据，仅查出至多一条，否则报错 如果根据条件查询出来的数据会是多条的，则该接口会直接抛出异常，不会进行{@link List#get(int)}操作
     *
     * @param record 数据条件
     * @return 查询出来的数据
     */
    T selectOneByRecord(T record);

    /**
     * 根据主键查询数据
     *
     * @param key 主键
     * @return 数据
     */
    T selectByPrimaryKey(Object key);

    /**
     * 根据record中非null作为条件查询总数据量
     *
     * @param record 数据条件
     * @return 符合要求的数据量
     */
    int selectCountByRecord(T record);

    /**
     * 根据主键查询是否存在数据
     *
     * @param key 主键
     * @return true-存在数据 false-不存在数据
     */
    boolean existsWithPrimaryKey(Object key);

    /**
     * 根据record中的主键，全量修改数据 record必须带主键
     *
     * @param record 数据
     * @return 修改的数据
     */
    T updateByPrimaryKey(T record);

    /**
     * 根据record中的主键，非null字段被修改数据 record必须带主键
     *
     * @param record 数据
     * @return 修改的数据
     */
    T updateByPrimaryKeySelective(T record);

}
