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
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2023-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_animal")
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "a_id", type = IdType.AUTO)
    private Long aId;

    /**
     * 名字
     */
    @TableField("a_name")
    private String aName;

    /**
     * 写真
     */
    private String img;

    /**
     * 毛色
     */
    private String skin;

    /**
     * 性别(0:公,1:母)
     */
    private Integer sex;

    /**
     * 绝育情况(0:未绝育,1:已绝育)
     */
    private Integer neuter;

    /**
     * 性格
     */
    private String nature;

    /**
     * 该对象状况(详细)
     */
    private String detailed;

    /**
     * 1:在校,2:已送养,3:失踪,4:离世
     */
    private Integer type;

    /**
     * 1:猫,2:其他
     */
    private Integer species;

    /**
     * 狂犬疫苗情况0:未注射,1:已注射
     */
    private Integer vaccine;

    /**
     * 来蓝星时间/发现时间
     */
    @TableField("come_date")
    private LocalDateTime come_date;

    /**
     * 失踪时间
     */
    @TableField("miss_date")
    private LocalDateTime missDate;

    /**
     * 离世时间
     */
    @TableField("dead_date")
    private LocalDateTime deadDate;

    /**
     * 送养时间
     */
    @TableField("feed_date")
    private LocalDateTime feedDate;

    /**
     * 0:正常,1:删除
     */
    private Integer state;

    /**
     * 0:待审核,1:审核通过
     */
    private Integer audit;

    /**
     * 点赞数
     */
    @TableField("a_like")
    private Integer aLike;

    /**
     * 领养状态:0:未领养,1:已领养
     */
    private Integer adopt;


    public Animal(String aName, String s, String skin, Integer sex, Integer neuter, String nature, String detailed, int i, Integer species, int i1, LocalDateTime comeDate, Object o, Object o1, Object o2, int i2, int i3, int i4, int i5) {
    }


}
