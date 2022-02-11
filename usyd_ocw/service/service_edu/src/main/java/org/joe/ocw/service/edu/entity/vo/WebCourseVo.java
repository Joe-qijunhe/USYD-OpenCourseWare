package org.joe.ocw.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WebCourseVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private Integer lessonNum;
    private String cover;
    private Long viewCount;
    private String description;
    private String teacherId;
    private String teacherName;
    private String intro;
    private String avatar;
    private String subjectLevelOneId;
    private String subjectLevelOne;
    private String subjectLevelTwoId;
    private String subjectLevelTwo;
}
