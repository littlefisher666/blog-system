package com.github.littlefisher.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.littlefisher.blog.controller.dto.UserDto;
import com.github.littlefisher.blog.controller.dto.request.LoginRequestDto;
import com.github.littlefisher.blog.controller.dto.response.LoginResponseDto;
import com.github.littlefisher.blog.dao.AuthorDao;
import com.github.littlefisher.blog.dao.UserDao;
import com.github.littlefisher.blog.dao.model.UserDo;
import com.github.littlefisher.blog.enums.UserTypeEnum;
import com.github.littlefisher.blog.service.UserService;

/**
 * @author jinyanan
 * @since 2019/11/27 09:58
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorDao authorDao;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        UserDo user = userDao.queryUserByAccountNoAndPassword(request.getAccountNo(), request.getPassword());
        if (user == null) {
            return LoginResponseDto.builder()
                .success(false)
                .build();
        }
        UserDto userInfo = UserDto.builder()
            .accountNo(user.getAccountNo())
            .createTime(user.getCreateTime())
            .email(user.getEmail())
            .nickName(user.getNickName())
            .userId(user.getUserId())
            .build();
        boolean isAuthor = authorDao.existsWithPrimaryKey(user.getUserId());
        UserTypeEnum userType = isAuthor ? UserTypeEnum.AUTHOR : UserTypeEnum.VISITOR;
        return LoginResponseDto.builder()
            .success(true)
            .userType(userType.getCode())
            .userInfo(userInfo)
            .build();
    }
}
