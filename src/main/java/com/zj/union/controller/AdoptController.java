package com.zj.union.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zj.union.entity.Adopt;
import com.zj.union.entity.ResponseResult;
import com.zj.union.entity.Vo.Orders;
import com.zj.union.service.AdoptService;
import com.zj.union.utils.RedisUtil;
import com.zj.union.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jiejoe499@gmail.com
 * @since 2023-08-17
 */
@RestController
@RequestMapping("/t-adopt")
/*@PreAuthorize("hasAuthority('system:normal:list')")*/
public class AdoptController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UUIDUtil uuidUtil;
    @Autowired
    private AdoptService adoptService;


    //生成订单
    @PostMapping("/UnAdopt/detail/adopt")
    public ResponseResult Adopt(@RequestBody Orders orders) {
        String adoptId = uuidUtil.getUUID32();
        Map<String, Orders> orderMap = new HashMap<String, Orders>();
        orderMap.put(adoptId,orders);
        redisUtil.set("Orders",orderMap,2400000);
        //接收到用户发送的信息,生成订单,存入内存数据库,并存入sql
        Adopt adoptOrder = new Adopt();
        if(StringUtils.isNotBlank(orders.getPhone()) && !StringUtils.equals("null",orders.getPhone())) {
            adoptOrder.setUserId(orders.getUid());
            adoptOrder.setPhone(orders.getPhone());
            adoptOrder.setAnimal(orders.getA_id());
            adoptOrder.setReturnVisit(0);
            adoptOrder.setType(0);
            adoptOrder.setGeneraDate(LocalDateTime.now());

            if (adoptService.save(adoptOrder)) {
                return new ResponseResult(200,"申请成功");
            }
            return new ResponseResult(500,"申请失败");
        }
        return new ResponseResult(420,"输入为空");
    }


    //查询订单列表
    @GetMapping("/ordersList")
    public ResponseResult Orders(@RequestParam("user_id") Integer user_id) {

        try {
            QueryWrapper ordersWrapper = new QueryWrapper();
            ordersWrapper.eq("user_id",user_id);
            List<Adopt> adoptList = (List<Adopt>) adoptService.list(ordersWrapper);
            if (adoptList != null) {

                    return new ResponseResult(200,"执行成功",adoptList);

            }return new ResponseResult(410,"未找到订单信息");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult(500,"请稍后重试");
        }

    }

    //修改订单领养信息
    //根据订单ID找到对应订单信息
    @PostMapping("/updateOrders")
    public ResponseResult updateOrders(@RequestParam("OrdersId") Long OrdersId,@RequestParam("phone") String phone) {
        if (OrdersId != null && StringUtils.isNotBlank(phone)) {
            try {
                adoptService.updateOrder(OrdersId,phone);
                return new ResponseResult(200,"执行成功");
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseResult(410,"修改失败");
            }
        }
        return new ResponseResult(420,"信息为空");
    }
}
