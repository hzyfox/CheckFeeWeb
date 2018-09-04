<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>
        电费查询提醒系统
    </title>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <script type="text/javascript" src="./jquery-3.3.1.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#checking").hide();
        });

        function checkInput() {
            var locationObj = document.getElementById("location");
            var index = locationObj.selectedIndex;
            var locationValue = locationObj[index].value;
            console.log("locationValue is " + locationValue);

            var buildingObj = document.getElementById("building");
            index = buildingObj.selectedIndex;
            var buildingValue = buildingObj[index].value;
            console.log("buildingValue is " + buildingValue);

            var roomObj = document.getElementById("roomnum");
            var roomnum = roomObj.value;
            console.log("roomnum is " + roomnum);

            var warningObj = document.getElementById("warning");
            var warning = warningObj.value;
            console.log("warning is " + warning);

            var emailObj = document.getElementById("email");
            var email = emailObj.value;
            console.log("email is " + email);

            if (locationValue == "-1" || buildingValue == "-1") {
                alert("楼栋区域或楼号不能为空")
                return false;
            }
            if (roomnum == "") {
                alert("房间号不能为空");
                return false;
            }
            if (email == "" && warning != "") {
                alert("您已经填了警告值，请填写接收电费通知的邮箱");
                return false;
            }
            if (email != "" && warning == "") {
                alert("您已经填写了邮箱, 请填写低于多少度电时我们将发送信息到您邮箱");
                return false;
            }
            return true;
        }

        function postForm0() {
            if (!checkInput()) {
                return
            }
            var form0 = new FormData(document.getElementById("form0"));
            $("#checking").show();
            $.ajax({
                url: "/register.action",
                type: "POST",
                dataType: "json",
                data: $("#form0").serialize(),
                success: function (data) {
                    var obj = eval(data);
                    $("#elecBox").show();
                    $("#elecValue").attr("value", obj.elecValue);
                    $("#checking").hide();
                    if (obj.registerResult) {
                        var emailObj = document.getElementById("email");
                        var email = emailObj.value;
                        var warningObj = document.getElementById("warning");
                        var warning = warningObj.value;
                        alert("注册成功,我们将会发送邮件至" + email + "当您当电量低于" + warning + "度");
                    } else {
                        alert("注册失败");
                    }
                    //$("#elecValue").text(data["elecValue"].toString());这种方式赋值不生效. 父元素为display:none
                },
                error: function (e) {
                    $("#checking").hide();
                    alert(e);
                }
            })
        }
    </script>
<body>
<form name="form0" id="form0">
    <div align="center">
        <table align="center" valign="center">
            <tr>
                <td align="right">楼栋区域:</td>
                <td align="left" valign="middle">
                    <select name="location" id="location" title="区域选择">
                        <option value="东区">东区</option>
                        <%--<option value = "留学生">留学生</option>--%>
                        <%--<option value = "西区">西区</option>--%>
                        <%--<option value = "韵苑">韵苑</option>--%>
                        <%--<option value = "紫菘">紫菘</option>--%>
                        <option selected="selected" value="-1">-请选择-</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">楼号:</td>
                <td align="left" valign="middle">
                    <select name="building" id="building" title="楼栋选择">
                        <option value="沁苑9舍">沁苑9舍</option>
                        <option value="沁苑10舍">沁苑10舍</option>
                        <option value="沁苑11舍">沁苑11舍</option>
                        <option value="沁苑12舍">沁苑12舍</option>
                        <option value="沁苑13舍">沁苑13舍</option>
                        <option selected="selected" value="-1">-请选择-</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">输入房间号：</td>
                <td align="left" valign="middle">
                    <input type="text" maxlength="7" name="roomnum" id="roomnum" style="width:120px;">
                </td>
            </tr>
            <tr>
                <td align="right">输入email：</td>
                <td align="left" valign="middle">
                    <input type="text" maxlength="30" name="email" id="email" style="width:120px;">
                </td>
            </tr>

            <tr>
                <td align="right">警告值：</td>
                <td align="left" valign="middle">
                    <input type="text" maxlength="30" name="warning" id="warning" style="width:120px;">
                </td>
            </tr>

            <tr id="elecBox" style="display: none">
                <td align="right">剩余电量：</td>
                <td align="left" valign="middle">
                    <input type="text" maxlength="30" name="elecValue" id="elecValue" style="width:120px;">
                </td>
            </tr>

            <tr>
                <td align="center">
                    <input align="center" type="button" id="submit" value="注册查询" onclick="postForm0();">
                </td>
            </tr>

        </table>
    </div>
    <div align="center" id="checking">正在查询...</div>


</form>

</body>
</html>
