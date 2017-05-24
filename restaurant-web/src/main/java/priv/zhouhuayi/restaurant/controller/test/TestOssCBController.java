package priv.zhouhuayi.restaurant.controller.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import priv.zhouhuayi.restaurant.util.aliyun.OssUtils;
import priv.zhouhuayi.restaurant.util.aliyun.http.UrlUtil;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

@Controller
@RequestMapping("test")
public class TestOssCBController {
	
	Logger log = LoggerFactory.getLogger(TestOssCBController.class);
	
	@RequestMapping("out")
	@ResponseBody
	public String test() {
		return "aa";
	}
	
	public static void main(String[] args) {
		Set<String> strSet = new HashSet<String>();
		strSet.add("a");
		String[] keyArray = new String[strSet.size()];
		strSet.toArray(keyArray);
		
		System.out.println(keyArray[0]);
		
//		String url = "http://local.risk.insobo.com/finance/finance/report/upload";
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		try {
//			InputStream is = new FileInputStream("e://zhy资料文件夹/资产负债表.xlsx");
////			paramMap.put("uploadFile", InputStreamUtils.InputStreamTOString(is, "UTF-8"));
//			paramMap.put("uploadFile", is);
//			System.out.println(UrlUtil.sendPost(url, paramMap));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	@RequestMapping("testOssBack")
	@ResponseBody
	public Map<String, Object> testOssBack(HttpServletRequest request) {
		Enumeration<String> paramName = request.getParameterNames();
		while(paramName.hasMoreElements()) {
			String name = paramName.nextElement();
			String value = request.getParameter(name);
			log.info("参数名："+name);
			log.info("参数值："+value);
		}
		
		String key = request.getParameter("fileNames");
		String[] fileNames = key.split(",");
		for (String fileName : fileNames) {
			log.info(fileName);
		}
		InputStream is = OssUtils.getOssFileSteam(fileNames[0]);
		try {
			if (is != null) {
			    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			    while (true) {
			        String line = reader.readLine();
			        if (line == null) break;
			        log.info("\n" + line);
			    }
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("message", "成功");
		resultMap.put("code", 200);
		return resultMap;
	}
}