package org.joe.ocw.service.oss.service;

import java.io.InputStream;
import java.util.List;

public interface FileService {

    /**
     * 阿里云oss文件上传
     * @param inputStream 输入流
     * @param module oss文件夹名称
     * @param originalFileName 文件原始名称
     * @return 文件在oss服务器上的url地址
     */
    String upload(InputStream inputStream, String module, String originalFileName);

    /**
     * 阿里云oss文件删除
     * @param url 文件的url地址
     */
    void removeFile(String url);

    void removeListFiles(List<String> urlList);

}
