package com.zj.union.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.union.entity.User;
import com.zj.union.mapper.TUserMapper;
import com.zj.union.service.registerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


@Repository
@Service
public class registerServiceImp extends ServiceImpl<TUserMapper, User> implements registerService {

    @Autowired
    private TUserMapper userMapper;
    @Override
    public int register(User user) {
        // 获取到前端的表单信息 经过Security加密放到数据库 然后返回到登录页面

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 密码加密
        String newPassword = passwordEncoder.encode(user.getPassword());

        //获取当前时间
        LocalDate toDay = LocalDate.now();

        User user1 = new User();
            user1.setUserName(user.getUserName());
            user1.setPassword(newPassword);
            user1.setEmail(user.getEmail());
            user1.setAddDate(toDay);
            if (user.getPhone() != null){
                user1.setPhone(user.getPhone());
            }


        return userMapper.insert(user1);
    }//用户名注册
}
