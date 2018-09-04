
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create with checkfee
 * USER: husterfox
 */
public class GetElecFee {
    private static Logger log = LoggerFactory.getLogger(GetElecFee.class);
    public static String getElecFee(String location, String building, String room) throws IOException {
        String url = "http://202.114.18.218/main.aspx";
        final String USER_AGENT = "Mozilla/5.0";
        URL obj = new URL(url);
        sun.net.www.protocol.http.HttpURLConnection con = (sun.net.www.protocol.http.HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");

        String programId = URLEncoder.encode(location, "UTF-8");
        String txtyq = URLEncoder.encode(building, "UTF-8");
        log.debug("programId/location: {}, after encode : {}", location, programId);
        log.debug("txtyq/building: {}  after encode: {} ", building, txtyq);
        log.debug("room: {}", room);
        String urlParameters = "__EVENTTARGET=&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=%2FwEPDwULLTE4NDE5OTM2MDEPZBYCAgMPZBYIAgEPEA8WBh4NRGF0YVRleHRGaWVsZAUM5qW85qCL5Yy65Z%2BfHg5EYXRhVmFsdWVGaWVsZAUM5qW85qCL5Yy65Z%2BfHgtfIURhdGFCb3VuZGdkEBUGBuS4nOWMugnnlZnlrabnlJ8G6KW%2F5Yy6BumfteiLkQbntKvoj5gLLeivt%2BmAieaLqS0VBgbkuJzljLoJ55WZ5a2m55SfBuilv%2BWMugbpn7Xoi5EG57Sr6I%2BYAi0xFCsDBmdnZ2dnZxYBZmQCBQ8QDxYGHwAFBualvOWPtx8BBQbmpbzlj7cfAmdkEBUUB%2BS4nDHoiI0H5LicMuiIjQfkuJwz6IiNB%2BS4nDToiI0H5LicNeiIjQfkuJw26IiNB%2BS4nDfoiI0H5LicOOiIjQzpmYTkuK3kuLvmpbwH5pWZN%2BiIjQfmlZk46IiNB%2BWNlzHoiI0H5Y2XMuiIjQfljZcz6IiNC%2BaygeiLkTEw6IiNC%2BaygeiLkTEx6IiNC%2BaygeiLkTEy6IiNC%2BaygeiLkTEz6IiNCuaygeiLkTnoiI0LLeivt%2BmAieaLqS0VFAfkuJwx6IiNB%2BS4nDLoiI0H5LicM%2BiIjQfkuJw06IiNB%2BS4nDXoiI0H5LicNuiIjQfkuJw36IiNB%2BS4nDjoiI0M6ZmE5Lit5Li75qW8B%2BaVmTfoiI0H5pWZOOiIjQfljZcx6IiNB%2BWNlzLoiI0H5Y2XM%2BiIjQvmsoHoi5ExMOiIjQvmsoHoi5ExMeiIjQvmsoHoi5ExMuiIjQvmsoHoi5ExM%2BiIjQrmsoHoi5E56IiNAi0xFCsDFGdnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZGQCEw88KwANAGQCFQ88KwANAGQYAwUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgIFDEltYWdlQnV0dG9uMQUMSW1hZ2VCdXR0b24yBQlHcmlkVmlldzEPZ2QFCUdyaWRWaWV3Mg9nZLHWUKnsT4U5B01iOoW0cLHinyY9&__EVENTVALIDATION=%2FwEWIgLS%2Bp%2FfDgLorceeCQLc1sToBgL%2BzpWoBQK50MfoBgKj5aPiDQLtuMzrDQLrwqHzBQKX%2B9a3BALahLK2BQLahLa2BQLahIq2BQLahI62BQLahIK2BQLahIa2BQLahJq2BQLahN61BQL4w577DwKH0Zq2BQKH0d61BQKVrbK2BQKVrba2BQKVrYq2BQKY14SVBQKY1%2BjwDAKY1%2FzbCwKY18CmAwLr76OiDwKUlLDaCAL61dqrBgLSwpnTCALSwtXkAgLs0fbZDALs0Yq1BYiiagV69FGjEwsWCICpCTfoshaE" +
                "&programId=" + programId +
                "&txtyq=" + txtyq +
                "&Txtroom=" + room +
                "&ImageButton1.x=37&ImageButton1.y=2&TextBox2=&TextBox3=";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        log.debug("Sending 'POST' request to URL {} ", url);
        log.debug("Post parameters : {} ", urlParameters);
        log.debug("Response Code : {}", responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        // 在字符串中，两个反斜杠才相当于一个正则的反斜杠。
        String elecValuePattern = ".*value=\"(\\d+\\.\\d)\".*";
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.trim().startsWith("<input name=\"TextBox3\"")) {
                Pattern pattern = Pattern.compile(elecValuePattern);
                Matcher matcher = pattern.matcher(inputLine);
                if (matcher.find()) {
                    inputLine = matcher.group(1);
                }
                break;
            }
        }
        in.close();

        //print result

        return inputLine;
    }
}
