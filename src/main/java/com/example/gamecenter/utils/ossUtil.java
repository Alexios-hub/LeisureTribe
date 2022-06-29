package com.example.gamecenter.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.*;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.StorageClass;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ossUtil {
    private OSS ossClient;
    //https://game-center-headportrait.oss-cn-hangzhou.aliyuncs.com/fileurl


    public JSONObject upload(MultipartFile multipartFile,String dir,String email){
        try {

            // 创建OSSClient实例。
            String endPoint = "oss-cn-hangzhou.aliyuncs.com";
            String accessKeySecret = "qUYWQMzXq7uRAbKjGUJ0UXx0ySrysO";
            String accessKeyId = "LTAI5tMTqs7GRTXUehvmh6wg";
            ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

            //判断桶是否存在，不存在则创建桶
            String bucketName = "game-center-headportrait";
            if(!ossClient.doesBucketExist(bucketName)){
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：读与写
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
            }
            //获取文件上传的流
            InputStream inputStream = null;
            try {
                inputStream = multipartFile.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //构建日期目录
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//            String datePath = dateFormat.format(new Date());
            //获取文件名
            String originname = multipartFile.getOriginalFilename();
//            System.out.println(originname);
//            System.out.println(originname.lastIndexOf("."));
//            System.out.println(originname.substring(originname.lastIndexOf(".")+1));
//            String fileUrl=dir+"/"+email+"headportrait"+originname.substring(originname.lastIndexOf("."));
            String fileUrl=dir+"/"+email+"headportrait"+".jpg";
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpg");


//            String fileUrl = dir + "/" + datePath + "/" + originname;
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //文件上传到云服务器
//            String fileName = multipartFile.getOriginalFilename();
           // 获取文件后缀名
//            String suffixName = fileName.substring(fileName.lastIndexOf("."));
           // 最后上传生成的文件名
//            String finalFileName = System.currentTimeMillis() + "" + new SecureRandom().nextInt(0x0400) + suffixName;
           // oss中的文件夹名
//            String objectName = sdf.format(new Date()) + "/" + finalFileName;
          // 创建上传文件的元信息，可以通过文件元信息设置HTTP header(设置了才能通过返回的链接直接访问)。
            ossClient.putObject(bucketName, fileUrl, inputStream,objectMetadata);
//            Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
//            String url = ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
            ossClient.shutdown();

//            Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
//            String url = ossClient.generatePresignedUrl(bucketName, fileUrl, expiration).toString();
            JSONObject result=new JSONObject();
            result.put("status",200);
            result.put("msg","ok");
            result.put("URL","https://game-center-headportrait.oss-cn-hangzhou.aliyuncs.com/"+fileUrl);
            return result;

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        JSONObject result=new JSONObject();
        result.put("status",411);
        result.put("msg","unkown error");
        return result;
    }

    }

