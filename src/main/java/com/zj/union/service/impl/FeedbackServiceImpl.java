package com.zj.union.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.union.entity.Feedback;
import com.zj.union.mapper.TFeedbackMapper;
import com.zj.union.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2023-11-10
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<TFeedbackMapper, Feedback> implements FeedbackService {
@Autowired
private TFeedbackMapper feedbackMapper;
    @Override
    public Boolean saveFeedback(Feedback feedback) {
        return feedbackMapper.insertFeedback(feedback);
    }
}
