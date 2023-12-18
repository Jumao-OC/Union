package com.zj.union.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author author
 * @since 2023-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * del_flag
     */
    private Integer delFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;


}
