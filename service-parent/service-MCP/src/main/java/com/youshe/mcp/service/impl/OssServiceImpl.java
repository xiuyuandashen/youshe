package com.youshe.mcp.service.impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.youshe.mcp.service.OssService;
import com.youshe.servicebase.OSS.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/05/10/11:14
 * @Description:
 */
@Service
public class OssServiceImpl implements OssService {
    
    /**
    * @Description: 上传文件到阿里云oss
    * @Param: [file]
    * @return: java.lang.String
    * @Author: zlf
    * @Date: 2021/5/10
    */
    @Override
    public String uploadFileAvatar(MultipartFile file)  {

        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = file.getInputStream();

            // 获取文件名称
            String fileName = file.getOriginalFilename();
            // 在文件名称中添加一个唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;
            // 按照时间日期进行分类 如 / 2021/03/22/xxx.jpg
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath  +"/"+  fileName;
            // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。以及上传文件的文件流
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传路径返回 (手动拼接返回)

            //模板 https://edu-xiuyuan.oss-cn-beijing.aliyuncs.com/2021/03/20/%E6%96%B0%E6%A1%93%E7%BB%93%E8%A1%A3.png
            String url = "https://"+ bucketName +"."+ endpoint + "/" +fileName;
            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
