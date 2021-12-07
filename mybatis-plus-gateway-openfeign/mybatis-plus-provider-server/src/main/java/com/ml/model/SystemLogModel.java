package com.ml.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author dml
 * @date 2021/12/7 11:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "tb_system_log")
public class SystemLogModel {

    @TableId(type = IdType.ASSIGN_ID)
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
