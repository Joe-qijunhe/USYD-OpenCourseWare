package org.joe.ocw.feign.fallback;

import org.joe.ocw.common.base.result.R;
import org.joe.ocw.feign.OssFileService;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * 容错类
 * 实现了远程调用方法的本地实现，如果远程调用失败，会调用这里的方法
 */
@Service
public class OssFileServiceFallback implements OssFileService {

    @Override
    public R removeFile(String url) {
        // 如果oss出现问题删除不了头像，会熔断保护，然后降级成这个方法
        // 返回error，不抛异常，让edu里的方法继续执行，继续删除讲师数据
        return R.error();
    }

    @Override
    public R removeFilesByUrlList(List<String> noteUrlList) {
        return R.error();
    }

}
