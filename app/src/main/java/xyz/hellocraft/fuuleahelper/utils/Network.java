package xyz.hellocraft.fuuleahelper.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Network {

    public static String sendLoginPost(String url, String param) {

        PrintWriter out = null;
        BufferedReader in = null;
        JSONObject ret_json = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
//            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("serial", "3cb7d428");
            conn.setRequestProperty("UUID", "913244f23e739f58");
            conn.setRequestProperty("APP-Version", "1.5.1");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.setRequestProperty("version", "1.7.1");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; Android 7.1.1; Lenovo TB-X504F Build/NMF26F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045411 Safari/537.36");

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            try {
                String sid = null;
                ret_json = new JSONObject(result.toString());
                if (ret_json.getBoolean("authenticated")) {
                    Map<String, List<String>> headers = conn.getHeaderFields();
                    List<String> cookies = headers.get("Set-Cookie");
                    for (String cookie : Objects.requireNonNull(cookies)) {
                        if (cookie.startsWith("sessionid")) {
                            sid = cookie.split(";")[0].split("=")[1];
                        }
                        Logger.d("cookie", cookie);
                    }
                    ret_json.put("sid", sid);
                    result.delete(0,result.length());
                    result.append(ret_json);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            Logger.i("Network", "sendPost: Failed. Target: " + url);
//            BuglyLog.i("HTTP", "sendPost: Failed.");
            e.printStackTrace();
            return null;
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result.toString();
    }

    private static String realSendPost(String url, String param, Map<String, String> map) {

        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
//            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("serial", "3cb7d428");
            conn.setRequestProperty("UUID", "913244f23e739f58");
            conn.setRequestProperty("APP-Version", "1.5.1");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.setRequestProperty("version", "1.7.1");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; Android 7.1.1; Lenovo TB-X504F Build/NMF26F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045411 Safari/537.36");
            //            let cookie = 'sessionid='+config.sid;
//            return {
//                    'Cookie': cookie,
//                    'Content-Type': 'application/json',
//                    'User-Agent': 'Mozilla/5.0 (Linux; Android 7.1.1; Lenovo TB-X504F Build/NMF26F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Crosswalk/23.53.589.4 Safari/537.36',
//                    'serial': 'e1e9d3c4',
//                    'version': '1.7.0',
//                    'UUID': '3f0a96c7c34af990',
//                    'X-Requested-With': 'XMLHttpRequest'
//    }
//            conn.setRequestProperty("user-agent", "okhttp/3.10.0");
            if (map != null) {
                ArrayList<String> arrayList = new ArrayList<>(map.keySet());
                for (String key : arrayList) {
                    conn.setRequestProperty(key, map.get(key));
                }
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            Logger.i("Network", "sendPost: Failed. Target: " + url);
//            BuglyLog.i("HTTP", "sendPost: Failed.");
            e.printStackTrace();
            return null;
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
    private static String realSendGet(String url, Map<String, String> map) {

        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
//            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("serial", "3cb7d428");
            conn.setRequestProperty("UUID", "913244f23e739f58");
            conn.setRequestProperty("APP-Version", "1.5.1");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.setRequestProperty("version", "1.7.1");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; Android 7.1.1; Lenovo TB-X504F Build/NMF26F; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045411 Safari/537.36");
            //            let cookie = 'sessionid='+config.sid;
//            return {
//                    'Cookie': cookie,
//                    'Content-Type': 'application/json',
//                    'User-Agent': 'Mozilla/5.0 (Linux; Android 7.1.1; Lenovo TB-X504F Build/NMF26F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Crosswalk/23.53.589.4 Safari/537.36',
//                    'serial': 'e1e9d3c4',
//                    'version': '1.7.0',
//                    'UUID': '3f0a96c7c34af990',
//                    'X-Requested-With': 'XMLHttpRequest'
//    }
//            conn.setRequestProperty("user-agent", "okhttp/3.10.0");
            if (map != null) {
                ArrayList<String> arrayList = new ArrayList<>(map.keySet());
                for (String key : arrayList) {
                    conn.setRequestProperty(key, map.get(key));
                }
            }
            conn.connect();
            //得到响应码
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                //得到响应流
                InputStream inputStream = conn.getInputStream();
                //将响应流转换成字符串
                BufferedReader bufferedReader = new BufferedReader(new
                     InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                //每次读取一行，若非空则添加至 stringBuilder
                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line);
                }
                //读取所有的数据后，赋值给 response
                result = stringBuilder;
            }

        } catch (Exception e) {
            Logger.i("Network", "sendPost: Failed. Target: " + url);
//            BuglyLog.i("HTTP", "sendPost: Failed.");
            e.printStackTrace();
            return null;
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String sendPost(String url, String param) {
        return sendPost(url, param, null, true);
    }

    public static String sendPost(String url,Map<String, String> map) {
        return sendPost(url, "", map, true);
    }

    public static String sendPost(String url, Boolean autoRetry) {
        return sendPost(url, "", null, autoRetry);
    }

    public static String sendPost(String url, String param, Map<String, String> map) {
        return sendPost(url, param, map, true);
    }

    public static String sendPost(String url, String param, Map<String, String> map, Boolean autoRetry) {
        String ret;
        while (true) {
            ret = realSendPost(url, param, map);
            if (ret != null) {
                break;
            }
            if (!autoRetry) {
                return "";
            }
            Logger.getLogger(null).makeToast("网络请求错误\n2s后自动重试");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static String sendGet(String url, Map<String, String> map) {
        return sendGet(url, map, true);
    }

    public static String sendGet(String s, Map<String, String> map, boolean autoRetry) {
        String ret;
        while (true) {
            ret = realSendGet(s, map);
            if (ret != null) {
                break;
            }
            if (!autoRetry) {
                return "";
            }
            Logger.getLogger(null).makeToast("网络请求错误\n2s后自动重试");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}