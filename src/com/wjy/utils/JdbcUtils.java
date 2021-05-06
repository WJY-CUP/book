package com.wjy.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Deprecated
public class JdbcUtils {

    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

    {
        System.out.println("JdbcUtils的非静态代码块");
    }

//    静态代码块随着类的加载而执行，而且只执行一次
//    非静态代码块在生成对象实例时执行，new一个执行一次
    static {
        try {
            System.out.println("调用JdbcUtils静态代码进行数据库连接");
            Properties properties = new Properties();
            // 读取 jdbc.properties属性配置文件
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            // 从流中加载数据
            properties.load(inputStream);
            // 创建数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 获取数据库连接池中的连接
     * @return 如果返回null,说明获取连接失败<br/>有值就是获取连接成功
     */
    public static Connection getConnection(){
        Connection connection = conns.get();
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
//                保存在threadLocal对象中
                conns.set(connection);
//                设置事物手动提交
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * @Description: 如果事务没有异常，提交事务并关闭连接
     * @param: [connection]
     * @return: void
     */
    public static void commitAndClose() {

        Connection connection = conns.get();
//        如果不为空，说明之前使用过连接操作过数据库
        if (connection != null) {
            try {
//                事务提交
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
//                不管事务是否提交正常都要关闭连接
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
//        一定要remove，因为Tomcat底层使用的是线程池技术，否则会出错
        conns.remove();

    }

    /**
     * @Description: 如果事务有异常，回滚事务并关闭连接
     * @param: [connection]
     * @return: void
     */
    public static void rollbackAndClose() {

        Connection connection = conns.get();
//        如果不为空，说明之前使用过连接操作过数据库
        if (connection != null) {
            try {
//                事务回滚
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
//                不管事务是否提交正常都要关闭连接
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //        一定要remove，因为Tomcat底层使用的是线程池技术，否则会出错
        conns.remove();

    }

//    /**
//     * 关闭连接，放回数据库连接池
//     * @param conn
//     */
//    public static void close(Connection conn){
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
