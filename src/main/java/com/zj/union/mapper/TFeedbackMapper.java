package com.zj.union.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.union.entity.Feedback;
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-11-10
 */
public interface TFeedbackMapper extends BaseMapper<Feedback> {
    Boolean insertFeedback(Feedback feedback);
}
