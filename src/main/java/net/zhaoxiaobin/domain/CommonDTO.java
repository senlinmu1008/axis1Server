/**
 * Copyright (C), 2015-2019
 */
package net.zhaoxiaobin.domain;

import lombok.Data;

import java.util.List;

/**
 *
 * @author zhaoxb
 * @create 2019-10-30 00:00
 */
@Data
public class CommonDTO {
    private String company;
    private Integer type;
    private List<String> managerList;
    private Boolean serverFlag; // 是否为服务端
}