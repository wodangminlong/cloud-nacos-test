package com.ml.serviceimpl;

import com.ml.mapper.master.SystemLogMapper;
import com.ml.model.SystemLogModel;
import com.ml.service.SystemLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dml
 * @date 2021/12/7 11:10
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Resource
    private SystemLogMapper systemLogMapper;

    @Override
    public void addSystemLog(SystemLogModel systemLogModel) {
        systemLogMapper.insert(systemLogModel);
    }
}
