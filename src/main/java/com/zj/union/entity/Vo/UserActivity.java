package com.zj.union.entity.Vo;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long uid;


    private String userName;


    private String phone;


    private String password;


    private Integer type;


    private String email;


    private String verifyCode;

    private String codeId;
}
