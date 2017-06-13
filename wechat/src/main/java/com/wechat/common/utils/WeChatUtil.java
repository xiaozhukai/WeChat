package com.wechat.common.utils;

import com.wechat.common.pojo.AccessToken;
import com.wechat.common.pojo.menu.Button;
import com.wechat.common.pojo.menu.ClickButton;
import com.wechat.common.pojo.menu.Menu;
import com.wechat.common.pojo.menu.ViewButton;
import com.wechat.common.pojo.trans.Data;
import com.wechat.common.pojo.trans.Parts;
import com.wechat.common.pojo.trans.Symbols;
import com.wechat.common.pojo.trans.TransResult;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.Map;

/**
 * Created by 30825 on 2017/5/13.
 */
public class WeChatUtil {
    private static final String APPID = "wx06d3924c93f401fd";
    private static final String APPSECRET = "019d49ee012289ffc65f60814d9ae9dd";

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //上传url
    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    //创建按钮
    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //查询按钮url
    private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    //删除按钮url
    private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";


    /**
     * doGetStr url 传输
     * @param url
     * @return
     */
    public static JSONObject doGetStr(String url) throws ParseException, IOException{
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        HttpResponse httpResponse = client.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        if(entity != null){
            String result = EntityUtils.toString(entity,"UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }
        return jsonObject;
    }

    /**
     * doPostStr url 传输
     * @param url
     * @return
     */
    public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpost = new HttpPost(url);
        JSONObject jsonObject = null;
        httpost.setEntity(new StringEntity(outStr,"UTF-8"));
        HttpResponse response = client.execute(httpost);
        String result = EntityUtils.toString(response.getEntity(),"UTF-8");
        jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }

    public static AccessToken getAccessToken() throws IOException {
        AccessToken token = new AccessToken();
        //替换url里面的APPID和APPSECRET
        String url = ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        //使用doGetStr方法传输
        JSONObject jsonObject = doGetStr(url);

        //判断jsonObject不为空
        if(jsonObject != null){
            token.setToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return token;
    }

    /**
     * 文件上传
     * @param filePath
     * @param accessToken
     * @param type
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);

        URL urlObj = new URL(url);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        //设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文部分
        //把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObj = JSONObject.fromObject(result);
        System.out.println(jsonObj);
        String typeName = "media_id";
        //如果type不是image类型加上_media_id
        if(!"image".equals(type)){
            typeName = type + "_media_id";
        }
        String mediaId = jsonObj.getString(typeName);
        return mediaId;
    }

    /**
     * 组装菜单
     * @return
     */
    public static Menu initMenu(){
        Menu menu = new Menu();
        ClickButton button11 = new ClickButton();
        button11.setName("click菜单");
        button11.setType("click");
        button11.setKey("11");

        ViewButton button21 = new ViewButton();
        button21.setName("view菜单");
        button21.setType("view");
        button21.setUrl("http://www.baidu.com");

        ClickButton button31 = new ClickButton();
        button31.setName("扫码事件");
        button31.setType("scancode_push");
        button31.setKey("31");

        ClickButton button32 = new ClickButton();
        button32.setName("地理位置");
        button32.setType("location_select");
        button32.setKey("32");

        Button button = new Button();
        button.setName("菜单");
        button.setSub_button(new Button[]{button31,button32});

        menu.setButton(new Button[]{button11,button21,button});
        return menu;
    }

    public static int createMenu(String token,String menu) throws ParseException, IOException{
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doPostStr(url, menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    public static JSONObject queryMenu(String token) throws ParseException, IOException{
        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doGetStr(url);
        return jsonObject;
    }

    public static int deleteMenu(String token) throws ParseException, IOException{
        String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = doGetStr(url);
        int result = 0;
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    public static String translate(String source) throws ParseException, IOException{
        String url = "http://openapi.baidu.com/public/2.0/translate/dict/simple?client_id=jNg0LPSBe691Il0CG5MwDupw&q=KEYWORD&from=auto&to=auto";
        url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));
        JSONObject jsonObject = doGetStr(url);
        String errno = jsonObject.getString("errno");
        Object obj = jsonObject.get("data");
        StringBuffer dst = new StringBuffer();
        if("0".equals(errno) && !"[]".equals(obj.toString())){
            TransResult transResult = (TransResult) JSONObject.toBean(jsonObject, TransResult.class);
            Data data = transResult.getData();
            Symbols symbols = data.getSymbols()[0];
            String phzh = symbols.getPh_zh()==null ? "" : "中文拼音："+symbols.getPh_zh()+"\n";
            String phen = symbols.getPh_en()==null ? "" : "英式英标："+symbols.getPh_en()+"\n";
            String pham = symbols.getPh_am()==null ? "" : "美式英标："+symbols.getPh_am()+"\n";
            dst.append(phzh+phen+pham);

            Parts[] parts = symbols.getParts();
            String pat = null;
            for(Parts part : parts){
                pat = (part.getPart()!=null && !"".equals(part.getPart())) ? "["+part.getPart()+"]" : "";
                String[] means = part.getMeans();
                dst.append(pat);
                for(String mean : means){
                    dst.append(mean+";");
                }
            }
        }else{
            dst.append(translateFull(source));
        }
        return dst.toString();
    }

    public static String translateFull(String source) throws ParseException, IOException{
        String url = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=jNg0LPSBe691Il0CG5MwDupw&q=KEYWORD&from=auto&to=auto";
        url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));
        JSONObject jsonObject = doGetStr(url);
        StringBuffer dst = new StringBuffer();
        List<Map> list = (List<Map>) jsonObject.get("trans_result");
        for(Map map : list){
            dst.append(map.get("dst"));
        }
        return dst.toString();
    }
}
