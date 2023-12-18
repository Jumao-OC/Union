package com.zj.union.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.union.entity.Animal;
import com.zj.union.entity.Declare;
import com.zj.union.entity.User;
import com.zj.union.mapper.TAnimalMapper;
import com.zj.union.mapper.TUserMapper;
import com.zj.union.service.AnimalService;
import com.zj.union.service.registerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnimalServiceImp extends ServiceImpl<TAnimalMapper, Animal> implements AnimalService {

    @Autowired
    private TAnimalMapper animalMapper;
    @Override
    public Boolean saveDeclare(Declare declare) {
        return animalMapper.saveDeclare(declare);
    }
}
