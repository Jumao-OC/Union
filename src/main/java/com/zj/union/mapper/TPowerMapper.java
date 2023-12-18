package com.zj.union.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.union.entity.Power;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-08-17
 */
public interface TPowerMapper extends BaseMapper<Power> {
    List<String> selectPermsByUserId(Long id);

}
