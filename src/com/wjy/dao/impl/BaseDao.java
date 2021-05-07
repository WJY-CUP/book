package com.wjy.dao.impl;

// import com.wjy.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public abstract class BaseDao {

    //使用DbUtils操作数据库
    // private QueryRunner queryRunner = new QueryRunner();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * update() 方法用来执行：Insert\Update\Delete语句
     *
     * @return 如果返回-1,说明执行失败<br/>返回其他表示影响的行数
     */
    public int update(String sql, Object... args) {
        /*
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.update(connection, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
//            此处可以捕获，但必须抛出异常，否则jdbcUtils无法捕获到异常，也就无法正常提交事务或者回滚
            throw new RuntimeException(e);
        }
//        此处不需要再关闭连接，否则service方法调用的其他方法无法获取到连接
//        finally {
//            JdbcUtils.close(connection);
//        }
         */
        return jdbcTemplate.update(sql,args);

    }

    /**
     * 查询返回一个javaBean的sql语句
     *
     * @param type 返回的对象类型
     * @param sql  执行的sql语句
     * @param args sql对应的参数值
     * @param <T>  返回的类型的泛型
     * @return
     */
    public <T> T queryForOne(Class<T> type, String sql, Object... args) {

        /*
        Connection con = JdbcUtils.getConnection();
        try {
            return queryRunner.query(con, sql, new BeanHandler<T>(type), args);
        } catch (SQLException e) {
            e.printStackTrace();
//            此处可以捕获，但必须抛出异常，否则jdbcUtils无法捕获到异常，也就无法正常提交事务或者回滚
            throw new RuntimeException(e);
        }
//        此处不需要再关闭连接，否则service方法调用的其他方法无法获取到连接
//        finally {
//            JdbcUtils.close(connection);
//        }
         */

        T object = null;
        try {
            object = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(type), args);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return object;


    }

    /**
     * 查询返回多个javaBean的sql语句
     *
     * @param type 返回的对象类型
     * @param sql  执行的sql语句
     * @param args sql对应的参数值
     * @param <T>  返回的类型的泛型
     * @return
     */
    public <T> List<T> queryForList(Class<T> type, String sql, Object... args) {
        /*
        Connection con = JdbcUtils.getConnection();
        try {
            return queryRunner.query(con, sql, new BeanListHandler<T>(type), args);
        } catch (SQLException e) {
            e.printStackTrace();
//            此处可以捕获，但必须抛出异常，否则jdbcUtils无法捕获到异常，也就无法正常提交事务或者回滚
            throw new RuntimeException(e);
        }
        */
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(type), args);
//        此处不需要再关闭连接，否则service方法调用的其他方法无法获取到连接
//        finally {
//            JdbcUtils.close(connection);
//        }
    }

    /**
     * 执行返回一行一列的sql语句
     * @param sql   执行的sql语句
     * @param args  sql对应的参数值
     * @return
     */
    public Object queryForSingleValue(String sql, Object... args){

        /*
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.query(conn, sql, new ScalarHandler(), args);
        } catch (Exception e) {
            e.printStackTrace();
//            此处可以捕获，但必须抛出异常，否则jdbcUtils无法捕获到异常，也就无法正常提交事务或者回滚
            throw new RuntimeException(e);
        }
        */
//        此处不需要再关闭连接，否则service方法调用的其他方法无法获取到连接
//        finally {
//            JdbcUtils.close(connection);
//        }

        return jdbcTemplate.queryForObject(sql, Object.class, args);

    }

}
