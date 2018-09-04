
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * create with checkfee
 * USER: husterfox
 */
public class SendEmail {
    private static Logger log = LoggerFactory.getLogger(SendEmail.class);
    private static final String authKey = "hquaufqcypcbbjcf";
    private static final String USER = "husterfox@qq.com";

    //参考链接https://blog.csdn.net/miaoch/article/details/53172743
    public static void sendMessage(String toMail, String elecValue, String roomNumberValue) throws MessagingException, UnsupportedEncodingException {
        final Properties properties = new Properties();
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.user", USER);
        properties.put("mail.password", authKey);
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = properties.getProperty("mail.user");
                String password = properties.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        Session mailSession = Session.getInstance(properties, authenticator);
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress from = new InternetAddress(
                properties.getProperty("mail.user"));
        message.setFrom(from);
        if (toMail != null) {
            InternetAddress[] iaToList = InternetAddress.parse(toMail);
            // 收件人
            message.setRecipients(Message.RecipientType.TO, iaToList);
        }
        message.setSentDate(new Date());
        //message.setSubject(new String("寝室电费不足提醒".getBytes("UTF-8"),"UTF-8")); // 主题
        message.setSubject("寝室电费不足提醒");
        message.setText("您的寝室 " + roomNumberValue + "电量只有 " + elecValue + "度啦，请您尽快充值");
        Transport.send(message);
    }
}
