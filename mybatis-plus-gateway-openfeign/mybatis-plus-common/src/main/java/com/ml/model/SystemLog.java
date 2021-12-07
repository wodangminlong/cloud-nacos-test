package com.ml.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author dml
 * @date 2021/12/7 11:03
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SystemLog {

    private Long id;

    private String remark;

    private String logType;

    private String method;

    private String params;

    private String requestIp;

    private Long duration;

    private String username;

    private String address;

    private String browser;

    private String systemName;

    private String error;

    private LocalDateTime createTime;

}
