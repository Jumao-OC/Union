package com.zj.union.entity.Vo;

//订单 只存于redis
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    //订单id,用户id,用户手机号,领养动物id
    private Long oid;
    private Long uid;
    private String phone;
    private Long a_id;

}
