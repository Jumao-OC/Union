package com.zj.union.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.union.entity.LoginUser;
import com.zj.union.entity.User;
import com.zj.union.mapper.TPowerMapper;
import com.zj.union.mapper.TUserMapper;
import com.zj.union.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class UserDetailsServiceImpl extends ServiceImpl<TUserMapper,User> implements UserDetailsService, UserService {

    @Autowired
    private TUserMapper userMapper;
    @Autowired
    private TPowerMapper tPowerMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        User user =userMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        // 根据用户查询权限信息 添加到LoginUser中
        //测试写法List<String> persissionList = new ArrayList<>(Arrays.asList("test"));

        List<String> permissionList = tPowerMapper.selectPermsByUserId(user.getId());
        //封装成UserDetails对象返回
        return new LoginUser(user,permissionList);
    }


    @Override
    public Boolean loadImage(Long id,String headPicture) {

        return userMapper.uploadImage(id,headPicture);
    }

    @Override
    public Boolean activityUser(Long userid) {

        return userMapper.activityUser(userid);
    }
}