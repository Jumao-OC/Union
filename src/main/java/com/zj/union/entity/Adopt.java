package com.zj.union.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2023-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_adopt")
public class Adopt implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收养表id
     */
    @TableId(value = "OrdersId", type = IdType.AUTO)
    private Long OrdersId;

    /**
     * 订单申请人id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 领养人电话号
     */
    @TableField("phone")
    private String phone;

    /**
     * 领养动物id
     */
    @TableField("animal")
    private Long animal;

    /**
     * 回访次数
     */
    @TableField("return_visit")
    private Integer returnVisit;

    /**
     * 0:待完成收养，1：完成收养
     */
    @TableField("type")
    private Integer type;

    /**
     * 订单生成时间
     */
    @TableField("Genera_date")
    private LocalDateTime generaDate;

    /**
     * 最后修改时间
     */
    @TableField("updata_date")
    private LocalDateTime updataDate;


}
