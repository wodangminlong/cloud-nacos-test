package com.ml.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mysql db visit utils
 *
 * @author Administrator
 * @date 2021/10/23 10:39
 */
@Slf4j
@Component
public class MysqlBaseUtils {

    @Resource
    private DruidUtils druidUtils;

    /**
     * get list
     *
     * @param sql    sql
     * @param params params are object array
     * @return list
     * @throws SQLException SQLException
     */
    public List<Map<String, Object>> getList(String sql, List<Object> params) throws SQLException {
        return getList(sql, params, false);
    }

    /**
     * get one column by row
     *
     * @param sql        sql
     * @param params     params are object array
     * @return string
     * @throws SQLException SQLException
     */
    public String getOneColumnByRow(String sql, List<Object> params) throws SQLException {
        return getOneColumnByRow(sql, params, false);
    }

    /**
     * get list
     *
     * @param sql        sql
     * @param params     params are object array
     * @param isMaster isMaster true: master false: slave
     * @return list
     * @throws SQLException SQLException
     */
    public List<Map<String, Object>> getList(String sql, List<Object> params, boolean isMaster) throws SQLException {
        getPreparedSql(sql, params);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = druidUtils.getConnection(isMaster);
            if (null != conn) {
                ps = conn.prepareStatement(sql);
                params2ps(params, ps);
                rs = ps.executeQuery();
                return getRows(rs);
            }
        } finally {
            safeCloseResultSet(rs);
            safeCloseStat(ps);
            closeConnection(conn);
        }
        return new ArrayList<>();
    }

    /**
     * get one column by row
     *
     * @param sql        sql
     * @param params     params are object array
     * @param isMaster isMaster true: master false: slave
     * @return string
     * @throws SQLException SQLException
     */
    public String getOneColumnByRow(String sql, List<Object> params, boolean isMaster) throws SQLException {
        getPreparedSql(sql, params);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = druidUtils.getConnection(isMaster);
            if (null != conn) {
                ps = conn.prepareStatement(sql);
                params2ps(params, ps);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
            return null;
        } finally {
            safeCloseResultSet(rs);
            safeCloseStat(ps);
            closeConnection(conn);
        }
    }

    /**
     * execute insert、update、delete and return the number of rows affected
     * insert、update、delete must be used by master db
     *
     * @param sql        sql
     * @param params     params are object array
     * @return int
     * @throws SQLException SQLException
     */
    public int updateSql(String sql, List<Object> params) throws SQLException {
        getPreparedSql(sql, params);
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = druidUtils.getConnection(true);
            if (null != conn) {
                ps = conn.prepareStatement(sql);
                params2ps(params, ps);
                return ps.executeUpdate();
            }
        } finally {
            safeCloseStat(ps);
            closeConnection(conn);
        }
        return -1;
    }

    /**
     * get sql
     *
     * @param sql    sql
     * @param params params are object array
     */
    private static void getPreparedSql(String sql, List<Object> params) {
        if (CollectionUtils.isEmpty(params)) {
            if (log.isDebugEnabled()) {
                log.debug(sql);
            }
            return;
        }
        StringBuilder returnSql = new StringBuilder();
        String[] subSql = sql.split("\\?");
        for (int i = 0; i < params.size(); i++) {
            if (params.get(i) instanceof String) {
                returnSql.append(subSql[i]).append("'").append(params.get(i)).append("'");
            } else {
                returnSql.append(subSql[i]).append(params.get(i));
            }
        }
        if (subSql.length > params.size()) {
            returnSql.append(subSql[subSql.length - 1]);
        }
        if (log.isDebugEnabled()) {
            log.debug(returnSql.toString());
        }
    }

    /**
     * get ps by params
     *
     * @param params params are object array
     * @param ps     ps
     * @throws SQLException SQLException
     */
    static void params2ps(List<Object> params, PreparedStatement ps) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object paramsObject = params.get(i);
            int parameterIndex = i + 1;
            if (paramsObject instanceof String) {
                ps.setString(parameterIndex, paramsObject.toString());
            } else if (paramsObject instanceof Integer) {
                ps.setInt(parameterIndex, (int) paramsObject);
            } else if (paramsObject instanceof Long) {
                ps.setLong(parameterIndex, (long) paramsObject);
            } else if (paramsObject instanceof Double) {
                ps.setDouble(parameterIndex, (double) paramsObject);
            } else if (paramsObject instanceof Float) {
                ps.setFloat(parameterIndex, (float) paramsObject);
            } else if (paramsObject == null) {
                ps.setNull(parameterIndex, Types.NULL);
            } else {
                ps.setString(parameterIndex, paramsObject.toString());
            }
        }
    }

    /**
     * get rows by resultSet
     *
     * @param rs rs
     * @return list
     * @throws SQLException SQLException
     */
    private static List<Map<String, Object>> getRows(ResultSet rs) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        while (rs.next()) {
            rows.add(getRowMap(rs));
        }
        return rows;
    }

    /**
     * get rowMap by resultSet
     *
     * @param rs rs
     * @return map
     * @throws SQLException SQLException
     */
    private static Map<String, Object> getRowMap(ResultSet rs) throws SQLException {
        ResultSetMetaData rSmd = rs.getMetaData();
        Map<String, Object> row = new HashMap<>(16);
        for (int i = 1; i <= rSmd.getColumnCount(); i++) {
            String colName = rSmd.getColumnLabel(i);
            Object colValue = rs.getObject(i);
            row.put(colName, colValue);
        }
        return row;
    }

    /**
     * safe close resultSet
     *
     * @param rs rs
     */
    private static void safeCloseResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            log.error("Close ResultSet has error", e);
        }
    }

    /**
     * safe close PreparedStatement
     *
     * @param stat stat
     */
    private static void safeCloseStat(PreparedStatement stat) {
        try {
            if (stat != null) {
                stat.close();
            }
        } catch (Exception e) {
            log.error("Close ResultSet has error", e);
        }
    }

    /**
     * safe close connection
     *
     * @param connection connection
     */
    private static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log.error("closeConnection has error", e);
        }
    }

}
