package com.zj.union.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.union.entity.Animal;
import com.zj.union.entity.Declare;

public interface AnimalService extends IService<Animal> {
    Boolean saveDeclare(Declare declare);
}
