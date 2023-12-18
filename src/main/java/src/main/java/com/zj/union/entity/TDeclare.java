package src.main.java.com.zj.union.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_declare")
public class TDeclare implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 申报id
     */
    @TableId(value = "did", type = IdType.AUTO)
    private Integer did;

    /**
     * 目击时间
     */
    @TableField("findDate")
    private LocalDateTime findDate;

    /**
     * 发现区域
     */
    @TableField("findArea")
    private String findArea;

    /**
     * 现场照片
     */
    @TableField("findImg")
    private String findImg;

    /**
     * 发现次数
     */
    @TableField("findTime")
    private Integer findTime;


}
