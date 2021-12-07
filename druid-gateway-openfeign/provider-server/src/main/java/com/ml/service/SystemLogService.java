package com.ml.service;

import com.ml.IdWorkerUtils;
import com.ml.model.SystemLog;
import com.ml.util.MysqlBaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author dml
 * @date 2021/12/7 9:33
 */
@Slf4j
@Component
public class SystemLogService {

    @Resource
    private MysqlBaseUtils mysqlBaseUtils;

    public void addSystemLog(SystemLog systemLog) {
        String addSql = "INSERT INTO tb_system_log (id,remark,log_type,method,params,request_ip,duration,username," +
                "address,browser,system_name,error,create_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,NOW(3));";
        List<Object> paramsList = Arrays.asList(IdWorkerUtils.nextId(), systemLog.getRemark(), systemLog.getLogType(),
                systemLog.getMethod(), systemLog.getParams(), systemLog.getRequestIp(), systemLog.getDuration(),
                systemLog.getUsername(), systemLog.getAddress(), systemLog.getBrowser(), systemLog.getSystemName(),
                systemLog.getError());
        try {
            mysqlBaseUtils.updateSql(addSql, paramsList);
        } catch (SQLException e) {
            log.error("addSystemLog has error: ", e);
        }
    }

}
