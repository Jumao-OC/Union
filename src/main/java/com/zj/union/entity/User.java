package com.zj.union.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2023-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("userName")
    private String userName;
    @TableField("password")
    private String password;
    @TableField("realName")
    private String realName;
    @TableField("headPicture")
    private String headPicture;
    @TableField("email")
    private String email;

    /**
     * 电话号
     */
    @TableField("phone")
    private String phone;

    /**
     * 1:普通用户,2:管理员
     */@TableField("type")
    private Integer type;
    @TableField("addDate")
    private LocalDate addDate;
    @TableField("updateDate")
    private LocalDate updateDate;

    /**
     * 1：正常
2：冻结
3：删除
     */
    @TableField("state")
    private Integer state;


}
