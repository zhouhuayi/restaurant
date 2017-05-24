package priv.zhouhuayi.restaurant.util.aliyun.http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class UrlUtil {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, ?> paramMap) {
    	 String result="";
         BufferedReader in = null;// 读取响应输入流  
         StringBuffer sb = new StringBuffer();// 存储参数  
         String params = "";// 编码之后的参数
         try {
             // 编码请求参数  
             if(paramMap.size()==1){
                 for(String name:paramMap.keySet()){
                     sb.append(name).append("=").append(
                             java.net.URLEncoder.encode(paramMap.get(name).toString(),  
                             "UTF-8"));
                 }
                 params=sb.toString();
             }else{
                 for (String name : paramMap.keySet()) {  
                     sb.append(name).append("=").append(  
                     java.net.URLEncoder.encode(paramMap.get(name).toString(), "UTF-8")).append("&");  
                 }  
                 String temp_params = sb.toString();  
                 params = temp_params.substring(0, temp_params.length() - 1);  
             }
             String full_url = url + "?" + params; 
             System.out.println(full_url); 
             // 创建URL对象  
             java.net.URL connURL = new java.net.URL(full_url);  
             // 打开URL连接  
             java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                     .openConnection();  
             // 设置通用属性  
             httpConn.setRequestProperty("Accept", "*/*");  
             httpConn.setRequestProperty("Connection", "Keep-Alive");  
             httpConn.setRequestProperty("User-Agent",  
                     "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
             // 建立实际的连接  
             httpConn.connect();  
             // 响应头部获取  
             Map<String, List<String>> headers = httpConn.getHeaderFields();  
             // 遍历所有的响应头字段  
             for (String key : headers.keySet()) {  
                 System.out.println(key + "\t：\t" + headers.get(key));  
             }  
             // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
             in = new BufferedReader(new InputStreamReader(httpConn  
                     .getInputStream(), "UTF-8"));  
             String line;  
             // 读取返回的内容  
             while ((line = in.readLine()) != null) {  
                 result += line;  
             }  
         } catch (Exception e) {
             e.printStackTrace();
         }finally{
             try {  
                 if (in != null) {  
                     in.close();  
                 }  
             } catch (IOException ex) {  
                 ex.printStackTrace();  
             }  
         }
         return result;
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
//            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result = line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, ?> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        
		String paramStringMap = JSONObject.fromObject(paramMap).toString();
		
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(100000);
            conn.setReadTimeout(100000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(paramStringMap);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(100000);
            conn.setReadTimeout(100000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
