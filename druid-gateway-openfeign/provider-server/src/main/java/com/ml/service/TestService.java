package com.ml.service;

import com.ml.IdWorkerUtils;
import com.ml.util.MysqlBaseUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.*;

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

    /**
     * get test list
     *
     * @return  list
     */
    public List<Map<String, Object>> listGetTest() throws SQLException {
        String querySql = "SELECT id,name FROM tb_test;";
        return mysqlBaseUtils.getList(querySql, new ArrayList<>());
    }

    /**
     * add test
     *
     * @param name  name
     * @return  int
     * @throws SQLException SQLException
     */
    public int addTest(String name) throws SQLException {
        String addSql = "INSERT INTO tb_test (id,name) VALUES (?,?);";
        return mysqlBaseUtils.updateSql(addSql, Arrays.asList(IdWorkerUtils.nextId(), name));
    }

    /**
     * update test
     *
     * @param id    id
     * @param name  name
     * @return  int
     * @throws SQLException SQLException
     */
    public int updateTest(Long id, String name) throws SQLException {
        String updateSql = "UPDATE tb_test SET name = ? WHERE id = ?;";
        return mysqlBaseUtils.updateSql(updateSql, Arrays.asList(name, id));
    }

    /**
     * delete test
     *
     * @param id    id
     * @return  int
     * @throws SQLException SQLException
     */
    public int deleteTest(Long id) throws SQLException {
        String deleteSql = "DELETE FROM tb_test WHERE id = ?;";
        return mysqlBaseUtils.updateSql(deleteSql, Collections.singletonList(id));
    }

}
