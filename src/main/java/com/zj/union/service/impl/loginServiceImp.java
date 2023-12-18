package com.zj.union.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zj.union.entity.LoginUser;
import com.zj.union.entity.ResponseResult;
import com.zj.union.entity.User;
import com.zj.union.mapper.TUserMapper;
import com.zj.union.service.loginService;
import com.zj.union.utils.JwtUtil;
import com.zj.union.utils.RedisCache;
import com.zj.union.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class loginServiceImp extends ServiceImpl<TUserMapper, User> implements loginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private TUserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

//todo 电话号登录功能接口的实现,参考下面所写实现相同功能
    @Override
    public boolean phoneLogin(String phone) {
        return false;
    }

    @Override
    public ResponseResult login(User user, HttpServletResponse response) throws JsonProcessingException {
        //AuthenticationManger authenticate进行用户认证(提供令牌 并交给认证)
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //认证会调用UserDetailsService接口进行认证 而UserDetailsService我们进行了自定义UserDetailsServiceImp

        //如果认证没通过给出相应的提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        ////如果认证通过,使用userid生成一个jwt jwt存入ResponseResult  使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        ////把完整的用户信息存入redis userid作为key  authenticate存入redis
        /*redisCache.setCacheObject("login:"+userId,loginUser);*/
        redisUtil.set("login:"+userId,loginUser,259200);

        // 把token以及用户数据响应给前端
            Map<String,String> tokenMap = new HashMap<>();
                tokenMap.put("token",jwt);
        //这里为了方便先使用uid当作token
        return new ResponseResult(200,"登陆成功",tokenMap);
    }

    @Override
    public ResponseResult logout() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//获取到登录后给予的认证信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();//认证信息是LogingUser对象
        Long userid = loginUser.getUser().getId();//认证信息内的userid是Long类型
        redisCache.deleteObject("login:"+userid);
        return new ResponseResult(200,"退出成功");
    }


    /**
     * 发送验证码
     * @param param     验证码
     * @param phone     手机号
     * @return
     */
    @Override
    public boolean send(Map<String, Object> param, String phone) {

        if(StringUtils.isEmpty(phone)) return false;

        //default 地域节点，默认就好  后面是 阿里云的 id和秘钥（这里记得去阿里云复制自己的id和秘钥哦）
        DefaultProfile profile = DefaultProfile.getProfile("default",
                "LTAI5tMr5uKfkNQB9HbdJEyJ",
                "sGKFEyGn76Kim6HdYwrCFqDpoGnGVk");
        IAcsClient client = new DefaultAcsClient(profile);

        //这里不能修改
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", phone);                    //手机号
        request.putQueryParameter("SignName", "阿里云短信测试");    //申请阿里云 签名名称（暂时用阿里云测试的，自己还不能注册签名）
        request.putQueryParameter("TemplateCode", "SMS_154950909"); //申请阿里云 模板code（用的也是阿里云测试的）
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public Boolean findUserByName(String username) {

        return userMapper.selectByName(username);
    }
}
