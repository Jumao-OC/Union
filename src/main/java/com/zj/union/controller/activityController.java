package com.zj.union.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zj.union.entity.ResponseResult;
import com.zj.union.entity.User;
import com.zj.union.entity.Vo.UserActivity;
import com.zj.union.service.MailService;
import com.zj.union.service.UserService;
import com.zj.union.utils.MailUtil;
import com.zj.union.utils.RandomUtil;
import com.zj.union.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
public class activityController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    @GetMapping("/activity")
    public ResponseResult getactivity(){
        return new ResponseResult(200,"进入成功");
    }

    @PostMapping("/activity/sendVerify")//用户点击发送验证码,根据
    public ResponseResult sendVerify( @RequestBody UserActivity userActivity){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("email").eq("email", userActivity.getEmail());


        if(userActivity == null || userService.getOne(queryWrapper) == null){

            return new ResponseResult(410,"信息不可为空或邮箱未注册");
        }


        // 获取到邮箱,生成验证码
        String email = userActivity.getEmail();
        String emailCode = RandomUtil.getFourBitRandom();

        //向邮箱发送验证码,并将验证码存入内存数据库

        try {
            mailService.sendSimpleMail("1773192042@qq.com",
                    email,
                    "激活用户",
                    "您的验证码如下:" + emailCode);

            //生成一个uuid作为存入内存数据库的key
            String codeId = UUID.randomUUID().toString().replaceAll("-", "");
            Map<String, String> codeMap = new HashMap<String, String>();
            codeMap.put(codeId,emailCode);

            redisUtil.set("code",codeMap,120);
            return new ResponseResult(200,"发送成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(500,"发送失败,请稍后重试");
        }

    }

    @PostMapping("/activity/checkVerify")
    public ResponseResult checkVerify(@RequestBody UserActivity userActivity){
        //获取到用户输入的验证码,以及验证码ID(验证码id用来获取内存数据库的值,因为前面以此作为key),并与内存数据库验证
        String codeId = userActivity.getCodeId();
        String code = userActivity.getVerifyCode();
        String userName = userActivity.getUserName();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id").eq("userName", userName);
        User id = userService.getOne(queryWrapper);



        Map map = (Map) redisUtil.get("code");


        //若一致则返回成功,不一致则返回验证码错误
        if(code.equals(map.get(codeId))) {

                    if(userService.activityUser(id.getId())) {
                        return  new ResponseResult(200,"激活成功");
                    }
                    return new ResponseResult(500,"失败,稍后重试");

        }

        return new ResponseResult(800,"验证码错误");
    }
}
