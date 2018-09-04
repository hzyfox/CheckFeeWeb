

/**
 * create with checkfee
 * USER: husterfox
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class UserInfoOp {
    private static boolean initialize = false;
    private static Logger log = LoggerFactory.getLogger(UserInfoOp.class);
    private static Connection conn = null;
    private static Statement statement = null;

    private static Connection getCon() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String user = "fee";
            String pwd = "19960818";
            String url = "jdbc:mysql://localhost:3306/feedb";
            conn = DriverManager.getConnection(url, user, pwd);
            statement = conn.createStatement();
        } catch (Exception e) {
            log.debug("getCon 失败: {}", e.getMessage());
        }
        try {
            conn.isReadOnly();//to toggle null exception if conn is null
        } catch (SQLException e) {
            log.debug("getCon 失败");
        }
        return conn;
    }

    private static void terminate() {
        if (conn != null && statement != null) {
            try {
                statement.close();
                conn.close();
            } catch (SQLException e) {
                log.debug("close null fialed: {}", e.getMessage());
            }
        }
    }

    static boolean add(UserInfo userInfo) {
        if (!initialize) {
            initializeUserInfoTable();
        }
        getCon();
        String insertUserInfo = "insert into userinfo(location, building, roomnum, warning, email) values(?,?,?,?,?)";
        String queryUserInfo = "select * from userinfo where location=? and building=? and roomnum=? and warning=? and email=?";
        try {
            PreparedStatement query = setStatement(conn.prepareStatement(queryUserInfo), userInfo);
            //防止重复添加
            if (!query.executeQuery().next()) {
                PreparedStatement insert = setStatement(conn.prepareStatement(insertUserInfo), userInfo);
                insert.executeUpdate();
                return true;
            }
            terminate();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            log.debug("insert userinfo {} 出错 {}", userInfo, e.getMessage());
            return false;
        }
    }

    private static PreparedStatement setStatement(PreparedStatement statement, UserInfo info) {
        try {
            statement.setString(1, info.getLocation());
            statement.setString(2, info.getBuilding());
            statement.setString(3, info.getRoomNum());
            statement.setString(4, info.getWarning());
            statement.setString(5, info.getEmail());
            return statement;
        } catch (SQLException e) {
            log.debug("set Statement error: {}", e.getMessage());
            return statement;
        }
    }

    private static boolean initializeUserInfoTable() {
        //feedb不存在
        getCon();
        try {
            if (!conn.getMetaData().getTables(null, null, "userinfo", null).next()) {
                String createUserInfoTable = "CREATE TABLE userinfo("
                        + "userId INT(4) primary key not null AUTO_INCREMENT,"
                        + "location varchar(30) not null,"
                        + "building varchar(30) not null,"
                        + "roomnum varchar(10) not null,"
                        + "warning varchar(10) not null,"
                        + "email varchar(50) not null"
                        + ")charset=utf8;";
                if (0 == statement.executeLargeUpdate(createUserInfoTable)) {
                    log.debug("创建userinfo table 成功");
                    initialize = true;
                    terminate();
                    return true;
                } else {
                    log.debug("创建userinfo table 失败");
                    terminate();
                    return false;
                }
            } else {
                initialize = true;
                terminate();
                return true;
            }
        } catch (SQLException e) {
            log.debug("创建table:userinfo出错{}", e.getMessage());
            return false;
        }
    }

    public static boolean deleteUserInfoTable() {
        getCon();
        try {
            if (conn.getMetaData().getTables(null, null, "userinfo", null).next()) {
                String deleteUserInfoTable = "drop table userinfo;";
                statement.executeUpdate(deleteUserInfoTable);
                terminate();
                return true;
            } else {
                terminate();
                return true;
            }
        } catch (SQLException e) {
            log.debug("关闭conn时出错{}", e.getMessage());
            return false;
        }
    }
}
