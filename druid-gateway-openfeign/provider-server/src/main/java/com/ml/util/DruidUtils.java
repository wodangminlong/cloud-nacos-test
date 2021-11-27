package com.ml.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import io.seata.rm.datasource.DataSourceProxy;
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
     * master data source
     */
    private DataSource masterDataSource = null;
    /**
     * slave data source
     */
    private DataSource slaveDataSource = null;

    @Value("${spring.datasource.master.username}")
    private String masterUsername;
    @Value("${spring.datasource.master.password}")
    private String masterPassword;
    @Value("${spring.datasource.master.url}")
    private String masterUrl;

    @Value("${spring.datasource.slave.username}")
    private String slaveUsername;
    @Value("${spring.datasource.slave.password}")
    private String slavePassword;
    @Value("${spring.datasource.slave.url}")
    private String slaveUrl;

    /**
     * init pool
     *
     * @param isMaster isMaster true: master false: slave
     * @return dataSource
     * @throws Exception exception
     */
    public synchronized DataSource initPool(boolean isMaster) throws Exception {
        String username;
        String password;
        String url;
        if (isMaster) {
            if (masterDataSource != null) {
                return masterDataSource;
            }
            username = masterUsername;
            password = masterPassword;
            url = masterUrl;
        } else {
            if (slaveDataSource != null) {
                return slaveDataSource;
            }
            username = slaveUsername;
            password = slavePassword;
            url = slaveUrl;
        }
        Map<String, String> druidMap = new HashMap<>(22);
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
        if (isMaster) {
            masterDataSource = new DataSourceProxy(DruidDataSourceFactory.createDataSource(druidMap));
            return masterDataSource;
        }
        slaveDataSource = new DataSourceProxy(DruidDataSourceFactory.createDataSource(druidMap));
        return slaveDataSource;
    }

    /**
     * get connection
     *
     * @param isMaster isMaster true: master false: slave
     * @return  connection
     */
    Connection getConnection(boolean isMaster) {
        try {
            return initPool(isMaster).getConnection();
        }catch (Exception e){
            log.error("DruidUtils getConnection has error: ", e);
        }
        return null;
    }
}
