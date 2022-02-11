package org.joe.ocw.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.joe.ocw.service.edu.entity.Subject;
import org.joe.ocw.service.edu.entity.excel.ExcelSubjectData;
import org.joe.ocw.service.edu.mapper.SubjectMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 无参构造函数
@AllArgsConstructor // 全参构造函数
public class ExcelSubjectDataListener extends AnalysisEventListener<ExcelSubjectData> {

    // ExcelSubjectDataListener是运行时new出来的，不被Spring管理，不能使用autowired
    private SubjectMapper subjectMapper;

    /**
     *遍历每一行的记录
     * @param data
     * @param context
     */
    @Override
    public void invoke(ExcelSubjectData data, AnalysisContext context) {
        // 处理读取出来的数据
        String levelOneTitle = data.getLevelOneTitle();
        String levelTwoTitle = data.getLevelTwoTitle();

        // 判断数据是否存在
        Subject subjectLevelOne = getByTitle(levelOneTitle);
        String parentId = null;
        if (subjectLevelOne == null) {
            // 组装一级类别
            Subject subject = new Subject();
            subject.setParentId("0");
            subject.setTitle(levelOneTitle);
            // 存入数据库
            subjectMapper.insert(subject);
            parentId = subject.getId();
        } else {
            parentId = subjectLevelOne.getId();
        }

        // 判断数据是否存在
        Subject subjectLevelTwo = getSubByTitle(levelTwoTitle, parentId);
        if (subjectLevelTwo == null) {
            // 组装二级类别
            Subject subject = new Subject();
            subject.setParentId(parentId);
            subject.setTitle(levelTwoTitle);
            // 存入数据库
            subjectMapper.insert(subject);
        }
    }

    /**
     * 所有数据读取之后的收尾工作
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    /**
     * 根据一级分类的名称查询数据是否存在
     * @param title
     * @return
     */
    private Subject getByTitle(String title) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        // 一级分类
        wrapper.eq("parent_id", 0);
        return subjectMapper.selectOne(wrapper);
    }

    /**
     * 根据分类的名称和父id查询数据是否存在
     * @param title
     * @param parentId
     * @return
     */
    private Subject getSubByTitle(String title, String parentId) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title);
        // 二级分类
        wrapper.eq("parent_id", parentId);
        return subjectMapper.selectOne(wrapper);
    }
}
