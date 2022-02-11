package org.joe.ocw.service.vod.service;

import com.aliyuncs.exceptions.ClientException;

import java.io.InputStream;
import java.util.List;

public interface VideoService {

    /**
     *
     * @param inputStream
     * @param originalFilename
     * @return 云视频id
     */
    String uploadVideo(InputStream inputStream, String originalFilename);

    void removeVideo(String videoId) throws ClientException;

    void removeVideoByIdList(List<String> videoIdList) throws ClientException;

    String getPlayAuth(String videoSourceId) throws ClientException;
}
