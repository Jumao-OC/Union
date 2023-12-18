package com.zj.union.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zj.union.entity.Animal;
import com.zj.union.entity.ResponseResult;
import com.zj.union.entity.User;
import com.zj.union.service.UserService;
import com.zj.union.utils.RedisCache;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.channels.MulticastChannel;
import java.time.LocalDate;
import java.util.UUID;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiejoe499@gmail.com
 * @since 2023-08-17
 */
@RestController
@RequestMapping("/t-user")
@PreAuthorize("hasAuthority('system:normal:list')")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisCache redisCache;


    @GetMapping("/userInfo")
    public ResponseResult getUserinfo(@RequestParam("uid") Integer uid) {




        if (uid != null) {
            //拿到用户id 查到该id的信息,返回该信息给前端
            try {
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("id",uid);

                return new ResponseResult(200,"查询成功",userService.getOne(queryWrapper));
            }catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult<>(500,"查询错误");
            }

        }

        return new ResponseResult(420,"输入为空");
    }


    @PostMapping("/userInfo/upHeadImage")
    public ResponseResult upHeadImage(@RequestParam("id") Long id,
                                      @RequestParam(value = "headPicture") MultipartFile fileUpload) {

        //取出用户提交的图片,获得地址,
        //获取文件名
        if (id != null && fileUpload != null) {
            //取出用户提交的图片,获得地址,
            //获取文件名
            String fileName = fileUpload.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //重新生成文件名
            fileName = UUID.randomUUID()+suffixName;
            //指定本地文件夹存储图片，写到需要保存的目录下
            String filePath = "E:\\study-program\\JAVA\\ideaProject\\union\\image\\user-headImage\\";
            try {
                userService.loadImage(id,filePath+fileName);
                //将图片保存到static文件夹里
                fileUpload.transferTo(new File(filePath+fileName));
                return new ResponseResult(200,"上传成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult<>(500,"上传失败");
            }
        }


        return new ResponseResult(500,"请求错误");
    }


    @PostMapping("/userInfo/update")
    public ResponseResult update(@RequestBody User user) {


        //前端从cookie中获取用户id存到表单中提交,此处前端也可以解析jwt来获取到当前用户的id
        if (user != null) {

            try {

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                passwordEncoder.encode(user.getPassword());
                User putUser = new User();
                putUser.setId(user.getId());
                putUser.setPassword( passwordEncoder.encode(user.getPassword()));
                putUser.setPhone(user.getPhone());
                putUser.setUserName(user.getUserName());
                putUser.setUpdateDate(LocalDate.now());
                putUser.setEmail(user.getEmail());
                userService.updateById(putUser);
                return new ResponseResult(200,"修改成功");
            }catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult<>(500,"修改失败");
            }

        }
        return new ResponseResult(420,"不可为空");
    }


}
