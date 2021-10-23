package com.ml.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * druid client utils
 *
 * @author Administrator
 * @date 2021/10/23 10:39
 */
@Slf4j
@Component
public class DruidUtils {

    /**
     * data source
     */
    private DataSource dataSource = null;

    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;

    /**
     * init pool
     *
     * @return dataSource
     * @throws Exception exception
     */
    public synchronized DataSource initPool() throws Exception {
        if (dataSource != null) {
            return dataSource;
        }
        Map<String, String> druidMap = new HashMap<>(30);
        druidMap.put(DruidDataSourceFactory.PROP_USERNAME, username);
        druidMap.put(DruidDataSourceFactory.PROP_PASSWORD, password);
        druidMap.put(DruidDataSourceFactory.PROP_URL, url);
        druidMap.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, "com.mysql.cj.jdbc.Driver");
        druidMap.put(DruidDataSourceFactory.PROP_INITIALSIZE, "10");
        druidMap.put(DruidDataSourceFactory.PROP_MINIDLE, "5");
        druidMap.put(DruidDataSourceFactory.PROP_MAXACTIVE, "300");
        druidMap.put(DruidDataSourceFactory.PROP_MAXWAIT, "60000");
        druidMap.put(DruidDataSourceFactory.PROP_TIMEBETWEENEVICTIONRUNSMILLIS, "60000");
        druidMap.put(DruidDataSourceFactory.PROP_MINEVICTABLEIDLETIMEMILLIS, "30000");
        druidMap.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY, "SELECT 'x'");
        druidMap.put(DruidDataSourceFactory.PROP_TESTWHILEIDLE, "true");
        druidMap.put(DruidDataSourceFactory.PROP_TESTONBORROW, "false");
        druidMap.put(DruidDataSourceFactory.PROP_TESTONRETURN, "false");
        druidMap.put(DruidDataSourceFactory.PROP_POOLPREPAREDSTATEMENTS, "true");
        druidMap.put(DruidDataSourceFactory.PROP_FILTERS, "stat");
        dataSource = DruidDataSourceFactory.createDataSource(druidMap);
        return dataSource;
    }

    /**
     * get connection
     *
     * @return  connection
     */
    Connection getConnection() {
        try {
            return initPool().getConnection();
        }catch (Exception e){
            log.error("DruidUtils getConnection has error: ", e);
        }
        return null;
    }
}
