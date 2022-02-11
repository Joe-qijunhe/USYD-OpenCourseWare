package org.joe.ocw.feign.fallback;


import lombok.extern.slf4j.Slf4j;
import org.joe.ocw.common.base.result.R;
import org.joe.ocw.feign.VodMediaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VodMediaServiceFallBack implements VodMediaService {

    @Override
    public R removeVideo(String vodId) {
        log.info("熔断保护");
        return R.error();
    }

    @Override
    public R removeVideoByIdList(List<String> videoIdList) {
        log.info("熔断保护");
        return R.error();
    }
}
