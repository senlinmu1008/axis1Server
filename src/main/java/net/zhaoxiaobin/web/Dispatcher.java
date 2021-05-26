/**
 * Copyright (C), 2015-2019
 */
package net.zhaoxiaobin.web;

import com.alibaba.fastjson.JSON;
import net.zhaoxiaobin.domain.CommonDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * webService入口
 * @author zhaoxb
 * @create 2019-10-27 10:30
 */

@Slf4j
public class Dispatcher {
    // 简单类型调用
    public String sum(String num1, String num2) {
        log.info("参数1:[{}]", num1);
        log.info("参数2:[{}]", num2);

        return Integer.parseInt(num1) + Integer.parseInt(num2) + "";
    }

    // 复杂类型调用
    public CommonDTO acceptInfo(CommonDTO commonDTO) {
        log.info(JSON.toJSONString(commonDTO, true));

        commonDTO.setServerFlag(true);
        return commonDTO;
    }
}