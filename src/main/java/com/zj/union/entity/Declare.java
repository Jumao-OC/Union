package com.zj.union.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_declare")
public class Declare implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    @TableField("postscript")
    private String postscript;


}
