package org.joe.ocw.service.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import org.joe.ocw.service.oss.service.FileService;
import org.joe.ocw.service.oss.util.OssProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OssProperties ossProperties;

    @Override
    public String upload(InputStream inputStream, String module, String originalFileName) {
        // 读取配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();

        // 创建OssClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);

        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        if (!ossClient.doesBucketExist(bucketname)) {
            ossClient.createBucket(bucketname);
            // 设置权限为外部可以读
            ossClient.setBucketAcl(bucketname, CannedAccessControlList.PublicRead);
        }

        //构建ObjectName：文件路径 avatar/2019/02/26/文件名
        String folder = new DateTime().toString("yyyy/MM/dd");
        //文件名：uuid.扩展名
        String fileName = UUID.randomUUID().toString();
        // 文件扩展名
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String key = module + "/" + folder + "/" + fileName + fileExtension;

        //文件上传至阿里云
        ossClient.putObject(ossProperties.getBucketname(), key, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
        //返回文件的url地址
        return "https://" + bucketname + "." + endpoint + "/" + key;
    }

    @Override
    public void removeFile(String url) {
        // 读取配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);

        String host = "https://" + bucketname + "." + endpoint + "/";
        String objectName = url.substring(host.length());

        // 删除文件。
        ossClient.deleteObject(bucketname, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public void removeListFiles(List<String> urlList) {
        // 读取配置信息
        String endpoint = ossProperties.getEndpoint();
        String keyid = ossProperties.getKeyid();
        String keysecret = ossProperties.getKeysecret();
        String bucketname = ossProperties.getBucketname();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, keysecret);

        String host = "https://" + bucketname + "." + endpoint + "/";
        for (String url : urlList) {
            String objectName = url.substring(host.length());
            // 删除文件。
            ossClient.deleteObject(bucketname, objectName);
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
