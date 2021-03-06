package com.ast.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangpeng on 2018/7/5.
 */
@CrossOrigin(origins="http://127.0.0.1:8088",maxAge=3600)
@RestController
@EnableAutoConfiguration
public class UploadPic {
    /**
     * 图片文件上传
     */
    class picData {
        public int errno;
        public List<String> data;
    };
    String LoaclUrl = "*";
    String FileDir = "/usr/share/nginx/html/";
    String PicUrl = "http://funmaker.tech:8080/";
    @RequestMapping(value = "/uploadPic",method = RequestMethod.POST)
    public picData uploadPic(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", LoaclUrl);
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String ,MultipartFile> fileList = multipartRequest.getFileMap();

        picData PicData = new picData();
        PicData.data = new ArrayList<String>();
        PicData.errno = 0;
        for (Map.Entry<String ,MultipartFile> entry : fileList.entrySet())
        {
            try {
                System.out.println("Value = " + entry.getValue().getName());
                String fileName = entry.getValue().getOriginalFilename();
                Date day=new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                fileName = df.format(day) + fileName;
                PicData.data.add(PicUrl + fileName);
                FileOutputStream fos = new FileOutputStream(FileDir+ fileName);//打开FileOutStrean流
                IOUtils.copy(entry.getValue().getInputStream(), fos);//将MultipartFile file转成二进制流并输入到FileOutStrean
                fos.close();
            } catch (Exception error) {
                System.out.print(error);
            }
        }
        return PicData;
    }
}
