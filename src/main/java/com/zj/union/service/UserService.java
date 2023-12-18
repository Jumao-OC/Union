package com.zj.union.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.union.entity.User;

public interface UserService extends IService<User>{
    public Boolean loadImage(Long id,String headPicture);
    public Boolean activityUser(Long userid);
}
