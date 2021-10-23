package com.ml.service;

import com.ml.util.MysqlBaseUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Collections;

/**
 * @author Administrator
 * @date 2021/10/23 11:00
 */
@Component
public class TestService {

    @Resource
    private MysqlBaseUtils mysqlBaseUtils;

    /**
     * get test name by id
     *
     * @param id    id
     * @return  string
     * @throws SQLException SQLException
     */
    public String getTestNameById(int id) throws SQLException {
        String querySql = "SELECT name FROM tb_test WHERE id = ?;";
        return mysqlBaseUtils.getOneColumnByRow(querySql, Collections.singletonList(id));
    }

}
