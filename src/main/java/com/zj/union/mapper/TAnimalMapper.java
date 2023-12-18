package com.zj.union.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.union.entity.Animal;
import com.zj.union.entity.Declare;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-09-24
 */
public interface TAnimalMapper extends BaseMapper<Animal> {

    Boolean saveDeclare(Declare declare);

}
