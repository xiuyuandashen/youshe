package com.youshe.mcp.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/05/10/11:13
 * @Description:
 */
public interface OssService {
    /**
    * @Description: 上传文件到阿里云oss
    * @Param: [file]
    * @return: java.lang.String
    * @Author: zlf
    * @Date: 2021/5/10
    */
    String uploadFileAvatar(MultipartFile file);
}
