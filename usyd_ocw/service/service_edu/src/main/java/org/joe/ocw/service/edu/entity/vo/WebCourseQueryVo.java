package org.joe.ocw.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WebCourseQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String subjectParentId;
    private String subjectId;
    private String gmtCreateSort;
}
