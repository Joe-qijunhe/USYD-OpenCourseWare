package org.joe.ocw.service.cms.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdVo implements Serializable {

    private static final long serialVersionUID=1L;
    private String id;
    // 广告标题
    private String title;
    // 广告排序
    private Integer sort;
    // 广告位
    private String type;
}
