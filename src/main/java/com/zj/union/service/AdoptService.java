package com.zj.union.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.union.entity.Adopt;

public interface AdoptService extends IService<Adopt> {

    Boolean updateOrder(Long ordersId, String phone);
}
