package com.zj.union.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.union.entity.Feedback;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2023-11-10
 */
public interface FeedbackService extends IService<Feedback> {
    Boolean saveFeedback(Feedback feedback);
}
