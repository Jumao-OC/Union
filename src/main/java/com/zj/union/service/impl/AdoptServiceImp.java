package com.zj.union.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.union.entity.Adopt;
import com.zj.union.mapper.TAdoptMapper;
import com.zj.union.service.AdoptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdoptServiceImp extends ServiceImpl<TAdoptMapper, Adopt> implements AdoptService {
    @Autowired
    private TAdoptMapper adoptMapper;
    @Override
    public Boolean updateOrder(Long ordersId,String phone) {
        return adoptMapper.updateOrder(ordersId,phone);
    }
}
