package baidu.speech.restapi.ttsdemo;



import com.baidu.aip.auth.DevAuth;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.AipClientConfiguration;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.HashMap;

public class AI2 {
    public static final String APP_ID = "16206017";
    public static final String API_KEY = "lG0djrbMqMZYQh3Tl2W3CDMT";
    public static final String SECRET_KEY = "0eR4xU2a9D1F2sXDi0vlw5TgDq1eTkh9";
    public static final String access_token = "24.26a2198e0f4651e754a28e2809d1c134.2592000.1560603529.282335-16206017";

    static AipSpeech client;
    static{
        // 初始化一个AipSpeech
        client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(200000);
        client.setSocketTimeoutInMillis(60000);
    }

    //语言合成
    public static void TTS(String text,String path){
        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("tok", access_token);
        // 调用接口
        TtsResponse res = client.synthesis(text, "zh", 1, options);

        AipClientConfiguration config = new  AipClientConfiguration();
        config.setConnectionTimeoutMillis(20000);
        config.setSocketTimeoutMillis(60000);
        JSONObject r = DevAuth.oauth(API_KEY, SECRET_KEY,config);
        System.out.println("access_token is null:"+r.isNull("access_token"));
        System.out.println("access_token is null:"+r.get("access_token"));
        byte[] data = res.getData();
        System.out.println("data:"+data);
        JSONObject res1 = res.getResult();
        System.out.println("res1:"+res1);
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data,path );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (res1 != null) {
            System.out.println("res1.toString:");
            System.out.println(res1.toString(2));
        }
        if(data==null){
            TTS(text,path);
        }

    }




}
