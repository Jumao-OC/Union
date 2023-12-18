package com.zj.union.controller;

import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.union.entity.Adopt;
import com.zj.union.entity.Animal;
import com.zj.union.entity.Declare;
import com.zj.union.entity.ResponseResult;
import com.zj.union.entity.Vo.Orders;
import com.zj.union.service.AnimalService;
import com.zj.union.service.ImageService;
import com.zj.union.utils.RedisUtil;
import com.zj.union.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2023-08-17
 */
@RestController
@RequestMapping("/t-animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private FileStorageService fileStorageService;//注入实列
    @Autowired
    private UUIDUtil uuidUtil;
    @Autowired
    private ImageService imageService;


    //进入首页接口,这里只用于前端,查询.
    @GetMapping("/index")
    public ResponseResult index() {

        //对内存数据库进行查询,看是否有数据,是否过期,没有过期就从数据库内查询
        // 过期了就从数据库查询,所有的数据查询都执行这种模式.
        //根据点赞数进行正序排列


        if (redisUtil.get("animalAll") == null) {

            QueryWrapper<Animal> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("state",0).orderBy(true,false,"come_date");

            //不返回总记录数 设置false
            Page<Animal> page = new Page<>(1, 2, true);
            IPage<Animal> iPage = animalService.page(page,queryWrapper);

            //将查到的数据添加到redis
            redisUtil.set("animalAll",iPage,1200);
            if (iPage == null) {
                return new ResponseResult(410,"未查到信息");
            }

            //将数据传到前端显示
            return new ResponseResult(200,"加载成功",iPage);
        }
        return new ResponseResult(200,"成功",redisUtil.get("animalAll"));
    }



    @GetMapping("/rank")
    public ResponseResult rank(){
        try{
            //获得数据,进行排序 传给前端
            QueryWrapper<Animal> rankWrapper = new QueryWrapper<>();
            rankWrapper.eq("audit",1).eq("state",0)
                    .orderBy(true,false,"a_like");
            //不返回总记录数 设置false
            Page<Animal> page = new Page<>(1, 2, true);
            IPage<Animal> iPage = animalService.page(page,rankWrapper);
            if (iPage == null) {
                return new ResponseResult(410,"未查到信息");
            }
            //将数据传到前端显示
            return new ResponseResult(200,"加载成功",iPage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseResult(500,"网络错误,请稍后重试");
    }



    //详细页功能设计 获取到前端传入的id
    @GetMapping("/AnimalDetail")
    public ResponseResult AnimalDetail(@RequestParam Integer a_id) {


        if (redisUtil.get("animalDetail") ==  null) {
            QueryWrapper<Animal> animalQueryWrapper = new QueryWrapper<>();
            animalQueryWrapper.eq("a_id",a_id);
            Animal animal = animalService
                    .getOne(animalQueryWrapper);
            if (animal == null) {
                return new ResponseResult(410,"为空");
            }

            Map animalDetailMap = new HashMap();
            try {
                animalDetailMap.put(a_id,animal);
                redisUtil.set("animalDetail",animalDetailMap);
                return new ResponseResult(200,"成功",animal);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseResult(500,"存入错误");
            }


        }
        return new ResponseResult(200,"成功",redisUtil.get("animalDetail"));

    }


    //待领养显示接口,申请领养功能也在此接口下接口编写,这里只应用于前端,所以用户只进行一次点击,所以另写接口
    @GetMapping("/UnAdopt")
    public ResponseResult UnAdopt(){


        if(redisUtil.get("unAdopt") == null) {
            //查到数据
            QueryWrapper<Animal> unAoptWrapper = new QueryWrapper<>();
            unAoptWrapper.eq("adopt",0);
            Page<Animal> page = new Page<>(1, 2, false);
            IPage<Animal> iPage = animalService.page(page,unAoptWrapper);

            if(iPage == null) {
                return new ResponseResult(410,"未查到数据");
            }

            //存储到redis
            try {
                redisUtil.set("unAdopt",unAoptWrapper,120);
                return new ResponseResult(200,"成功",iPage);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseResult(500,"存入内存数据库失败");
            }
        }

        return new ResponseResult(200,"成功",redisUtil.get("unAdopt"));
    }

    //申报接口,需要重新创建一个bean对象
    @PostMapping("/declare")
 /*   @PreAuthorize("hasAuthority('system:normal:list')")*/
    public ResponseResult Declare(@RequestBody Declare declare) {
        //这里判空主要交给前端进行
        if (declare != null) {
            try {

                Declare declare2 = new Declare();

                declare2.setFindDate(declare.getFindDate());
                declare2.setFindArea(declare.getFindArea());
                declare2.setFindTime(declare.getFindTime());
                declare2.setPostscript(declare.getPostscript());
                declare2.setFindImg(declare.getFindImg());

                animalService.saveDeclare(declare2);
                //返回提示信息
                return new ResponseResult(200,"提交成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseResult<>(500,"提交失败,稍后重试");
            }
        }
        return new ResponseResult(420,"不可为空");
    }

}
