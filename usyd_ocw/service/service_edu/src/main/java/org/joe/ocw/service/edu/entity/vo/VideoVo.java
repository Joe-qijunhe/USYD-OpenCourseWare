package org.joe.ocw.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Integer sort;
    private String videoSourceId;
    private String noteUrl;
}