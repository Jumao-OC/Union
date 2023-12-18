package com.zj.union.service.impl;

import com.zj.union.entity.ResponseResult;
import com.zj.union.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ImageServiceImp implements ImageService {

    @Override
    public Object upload(MultipartFile fileUpload) {
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        //指定本地文件夹存储图片，写到需要保存的目录下
        String filePath = "E:\\";
        try {
            //将图片保存到static文件夹里
            fileUpload.transferTo(new File(filePath+fileName));
            //返回提示信息
            return new ResponseResult(200,"上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(500,"上传失败");
        }
    }
}
