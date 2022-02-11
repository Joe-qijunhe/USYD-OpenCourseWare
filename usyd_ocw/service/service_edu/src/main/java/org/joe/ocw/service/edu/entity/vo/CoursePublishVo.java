package org.joe.ocw.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoursePublishVo  implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectParentTitle;
    private String subjectTitle;
    private String teacherName;
}
