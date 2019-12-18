package com.github.littlefisher.blog.dao;

import com.github.littlefisher.blog.configuration.dao.IBaseDao;
import com.github.littlefisher.blog.dao.model.UserDo;

/**
 * @author jinyanan
 * @since 2019/11/27 10:02
 */
public interface UserDao extends IBaseDao<UserDo> {

    /**
     * 根据账户和密码查询用户
     *
     * @param accountNo 账户
     * @param password 密码
     * @return 用户
     */
    UserDo queryUserByAccountNoAndPassword(String accountNo, String password);
}
