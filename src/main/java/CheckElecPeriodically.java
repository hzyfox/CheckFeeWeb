import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.*;

/**
 * create with PACKAGE_NAME
 * USER: husterfox
 */
public class CheckElecPeriodically {
    private static Logger log = LoggerFactory.getLogger(CheckElecPeriodically.class);
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

    public static void check() {
        String check = "select location, building, roomnum, warning, email from userinfo";
        getCon();
        try {
            ResultSet resultSet = statement.executeQuery(check);
            while (resultSet.next()) {
                String location = resultSet.getString("location");
                String building = resultSet.getString("building");
                String roomnum = resultSet.getString("roomnum");
                String warning = resultSet.getString("warning");
                String email = resultSet.getString("email");
                log.debug("check location: {} building: {} roomnum: {} warning: {} email: {}",
                        location, building, roomnum, warning, email);
                float warnValue = Float.valueOf(warning);
                String currentValue = GetElecFee.getElecFee(location, building, roomnum);
                if (Float.valueOf(currentValue) <= warnValue) {
                    SendEmail.sendMessage(email, currentValue, roomnum);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.debug("定时查询发生错误 {}", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        terminate();

    }


}
