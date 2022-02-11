package org.joe.ocw.service.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectVo {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Integer sort;
    private List<SubjectVo> children = new ArrayList<>();
}
