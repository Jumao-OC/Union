package com.zj.union.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.union.entity.User;

public interface registerService extends IService<User> {
    int register(User user);

}
