package com.zj.union.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zj.union.entity.ResponseResult;
import com.zj.union.entity.User;
import com.zj.union.entity.Vo.UserActivity;
import com.zj.union.service.UserService;
import com.zj.union.service.loginService;
import com.zj.union.service.registerService;
import com.zj.union.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    private loginService loginService;
    @Autowired
    private registerService registerService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user, HttpServletResponse response) throws JsonProcessingException {
        return loginService.login(user,response);
    }



    @GetMapping("/PhoneLogin")
    public ResponseResult getPhoneLogin() {
        return new ResponseResult(200,"进入成功");
    }




    @GetMapping(value = "/send")
    public ResponseResult code(@RequestParam("phone") String phone) {

        //判断手机号是否已经注册
        try {
            QueryWrapper phoneWrapper = new QueryWrapper();
            phoneWrapper.eq("phone",phone);
            if ( userService.getOne(phoneWrapper) != null){
                return new ResponseResult(800,"手机号已注册");
            }
        }catch (Exception e){
            return new ResponseResult(500,"未获取到信息");
        }

        //从redis中获取验证码，如果获取到就直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) return new ResponseResult(800,"验证码还未失效");

        try {
            //2、如果获取不到，就进行阿里云发送
            code = RandomUtil.getFourBitRandom();//生成验证码的随机值
            Map<String,Object> param = new HashMap<>();
            param.put("code", code);
            //调用方法
            boolean isSend = loginService.send(param,phone);
            if(isSend) {
                //往redis中设置数据
                redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            }
            return new ResponseResult(200,"发送成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult(500,"验证码发送失败");
        }

    }


    @PostMapping("/PhoneLogin")
    public ResponseResult CheckPhoneLogin(@RequestBody UserActivity userActivity,HttpServletResponse response) throws JsonProcessingException {
        //编写测试用户输入的验证码接口

        String userPhone = userActivity.getPhone();
        String verifyCode = userActivity.getVerifyCode();
        //获取对应内存的验证码
        String redisVerify = redisTemplate.opsForValue().get(userPhone);
//这里需要进行对是否已经注册的用户进行区分 如果是已经注册的用户那么此次便是登录需求,如果是未注册用户此次登录便是注册需求
        //登录需求后端传数据:已经注册:前端根据这个值跳转到密码登录页面
        if (verifyCode.equals(redisVerify)) {
            User user = new User();
            user.setUserName(userPhone);

            user.setPhone(userPhone);
            if(loginService.findUserByName(user.getUserName()) != null && loginService.findUserByName(user.getUserName())){
                return loginService.login(user,response);
            }else {
                return new ResponseResult(810,"该账号未注册，请前往注册");
            }

        }
        return new ResponseResult(800,"验证码错误");
    }


    @RequestMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
