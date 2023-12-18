package com.zj.union.controller;


import com.zj.union.entity.Feedback;
import com.zj.union.entity.ResponseResult;
import com.zj.union.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2023-09-20
 */
@RestController
@RequestMapping("/t-feedback")
/*@PreAuthorize("hasAuthority('system:normal:list')")*/
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;


    @GetMapping("/feedback")
    private ResponseResult GetFeedback() {
        return new ResponseResult(200,"成功");
    }

    @PostMapping("/setUp")
    public ResponseResult Feedback(@RequestBody Feedback feedback) {

        try {
            feedbackService.saveFeedback(feedback);
//            feedbackService.save(feedback);
            return new ResponseResult(200,"提交成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult(500,"提交失败稍后重试");
        }

    }

}
