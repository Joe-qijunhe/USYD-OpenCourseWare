package org.joe.ocw.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.joe.ocw.service.edu.entity.Subject;
import org.joe.ocw.service.edu.entity.excel.ExcelSubjectData;
import org.joe.ocw.service.edu.entity.vo.SubjectVo;
import org.joe.ocw.service.edu.listener.ExcelSubjectDataListener;
import org.joe.ocw.service.edu.mapper.SubjectMapper;
import org.joe.ocw.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void batchImport(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelSubjectData.class, new ExcelSubjectDataListener(baseMapper))
                .excelType(ExcelTypeEnum.XLS)
                .sheet()
                .doRead();
    }

    @Override
    public List<SubjectVo> nestedList() {
        return baseMapper.selectNestedListByParentId("0");
    }
}
