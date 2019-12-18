package com.github.littlefisher.blog.dao.impl;

import org.springframework.stereotype.Repository;

import com.github.littlefisher.blog.configuration.dao.AbstractBaseDaoImpl;
import com.github.littlefisher.blog.dao.UserDao;
import com.github.littlefisher.blog.dao.example.UserDoExample;
import com.github.littlefisher.blog.dao.mapper.UserDoMapper;
import com.github.littlefisher.blog.dao.model.UserDo;

/**
 * @author jinyanan
 * @since 2019/11/27 10:02
 */
@Repository
public class UserDaoImpl extends AbstractBaseDaoImpl<UserDo, UserDoMapper> implements UserDao {
    @Override
    public UserDo queryUserByAccountNoAndPassword(String accountNo, String password) {
        UserDoExample example = new UserDoExample();
        example.createCriteria()
            .andAccountNoEqualTo(accountNo)
            .andPasswordEqualTo(password);
        return getMapper().selectOneByExample(example);
    }
}
