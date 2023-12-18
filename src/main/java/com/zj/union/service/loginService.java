package com.zj.union.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zj.union.entity.ResponseResult;
import com.zj.union.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface loginService {
    ResponseResult login(User user, HttpServletResponse response) throws JsonProcessingException;

    ResponseResult logout();

    //发送验证码
    boolean send(Map<String, Object> param, String phone);
    boolean phoneLogin(String phone);



    Boolean findUserByName(String username);
}
