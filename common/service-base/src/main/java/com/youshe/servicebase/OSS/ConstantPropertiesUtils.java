package com.youshe.servicebase.OSS;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: zlf
 * @Date: 2021/05/10/11:11
 * @Description:
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {
    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    public static String END_POINT;

    public static String KEY_ID;

    public static String KEY_SECRET;

    public static String BUCKET_NAME;

    /**
     * @Description: 属性赋值后，会执行该方法初始化静态成员
     * @Param: []
     * @return: void
     * @Author: zlf
     * @Date: 2021/3/22
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endPoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }

}
