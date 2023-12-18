package com.zj.union.controller;


import com.zj.union.entity.ResponseResult;
import com.zj.union.entity.User;
import com.zj.union.service.UserService;
import com.zj.union.service.loginService;
import com.zj.union.service.registerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegisterController {
    @Autowired
    private registerService registerService;
    @Autowired
    private loginService loginService;
    @Autowired
    private UserService userService;

    @GetMapping("register")
    public ResponseResult register(){

        return new ResponseResult(200,"进入成功");
    }

    @PostMapping("user/register")
    public ResponseResult postRegister(@RequestBody User user){
        try {
            //检测账号是否已存在
            String username = user.getUserName();

            if (loginService.findUserByName(username) != null && loginService.findUserByName(username)) {
                return new ResponseResult(810,"账号已注册");
            }
            //录入信息
            if(registerService.register(user) == 0){
                return new ResponseResult(800,"注册失败,请重试");
            }
            //这里对前端用户输入的验证码的校验交给前端解决,页面接收到验证码验证成功的消息后 才可以点击注册按钮否则不能进行注册
            //两次密码的输入验证工作也因交给前端进行设计

            return new ResponseResult(200,"注册成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult(500,"稍后重试");
        }

    }
//就按上面的注册接口来,前端页面设计好访问接口,再接跳转登录即可
}
