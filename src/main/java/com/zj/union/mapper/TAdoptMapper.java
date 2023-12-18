package com.zj.union.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.union.entity.Adopt;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-08-17
 */
public interface TAdoptMapper extends BaseMapper<Adopt> {

    Boolean updateOrder(Long ordersId, String phone);

}
