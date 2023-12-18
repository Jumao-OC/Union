package src.main.java.com.zj.union.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2023-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_feedback")
public class TFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "f_id", type = IdType.AUTO)
    private Long fId;

    /**
     * 反馈类型:0:用户类型反馈,1:动物类型反馈,2:其他反馈
     */
    private Integer fbtype;

    /**
     * 反馈标题
     */
    private String title;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 联系方式(邮箱)
     */
    private String email;


}
