
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Enumeration;


/**
 * create with PACKAGE_NAME
 * USER: husterfox
 */
public class QueryRegisterElecValue extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration<String> parameterList = req.getParameterNames();
        while (parameterList.hasMoreElements()) {
            System.out.println(parameterList.nextElement());
        }
        System.out.println(req.getQueryString());
        System.out.println(req.getParameter("roomnum"));
        PrintWriter out = resp.getWriter();
        out.write("HelloWorld");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type", "text/json");
        String location = URLDecoder.decode(req.getParameter("location"),"UTF-8");
        String building = req.getParameter("building");
        String roomnum = req.getParameter("roomnum");
        String warning = req.getParameter("warning");
        String email = req.getParameter("email");
        System.out.println();
        String elecValue = GetElecFee.getElecFee(location, building, roomnum);
        boolean resultValue = false;
        if (email != null && warning != null) {
            UserInfo userInfo = new UserInfo();
            userInfo.setAll(location, building, roomnum, warning, email);
            resultValue = UserInfoOp.add(userInfo);
        }
        ResponseInfo responseInfo = new ResponseInfo();
        responseInfo.setElecValue(elecValue);
        responseInfo.setRegisterResult(resultValue);
        String info = JSON.toJSONString(responseInfo);
        PrintWriter out = resp.getWriter();
        out.write(info);
        out.flush();
    }
}
