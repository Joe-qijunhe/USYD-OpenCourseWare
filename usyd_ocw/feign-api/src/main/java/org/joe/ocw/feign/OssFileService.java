package org.joe.ocw.feign;


import org.joe.ocw.common.base.result.R;
import org.joe.ocw.feign.fallback.OssFileServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service // 不加也可以，加是为了让idea不报错
@FeignClient(value = "service-oss", fallback = OssFileServiceFallback.class)
public interface OssFileService {

    @DeleteMapping("/admin/oss/file/remove")
    R removeFile(@RequestBody String url);

    @DeleteMapping("/admin/oss/file/removeList")
    R removeFilesByUrlList(@RequestBody List<String> noteUrlList);

}
