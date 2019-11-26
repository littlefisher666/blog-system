package com.github.littlefisher.blog.dao;

import java.util.List;

import com.github.littlefisher.blog.configuration.dao.IBaseDao;
import com.github.littlefisher.blog.dao.model.AuthorDo;

/**
 * @author jinyanan
 * @since 2019/11/26 10:09
 */
public interface AuthorDao extends IBaseDao<AuthorDo> {

    /**
     * 根据id批量查询
     *
     * @param authorIdList 作者编号列表
     * @return 作者
     */
    List<AuthorDo> findAllByIdList(List<Integer> authorIdList);
}
