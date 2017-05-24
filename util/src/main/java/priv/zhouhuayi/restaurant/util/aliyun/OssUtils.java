package priv.zhouhuayi.restaurant.util.aliyun;

import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;

public class OssUtils {
	// endpoint以杭州为例，其它region请按实际情况填写
	private static String endpoint = "oss-cn-hangzhou.aliyuncs.com";
	private static String endpointLocal = "oss-cn-hangzhou-internal.aliyuncs.com";
	
	// accessKey请登录https://ak-console.aliyun.com/#/查看
	private static String accessKeyId = "LTAIPcg7ZlHirzKJ";
	private static String accessKeySecret = "1pYh42rm1QYq25rc4D4PiI0lPSudSp";
	private static String bucketName = "zhouhuayi1";
	
	private static Log log = LogFactory.getLog(OssUtils.class);
	
	/**
	 * 获取OSS签名
	 * 
	 * @author zhy
	 * @return
	 */
	public static JSONObject getOssSign() {
		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
		// 设置自定义元信息name的值为my-data
		meta.addUserMetadata("property", "property-value");
		
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 使用访问OSS
		
        String dir = "user-dir";
        String host = "http://" + bucketName + "." + endpoint;
        JSONObject signJson = null;
        try { 	
        	long expireTime = 30;
        	long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);
            
            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessKeyId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            //respMap.put("expire", formatISO8601Date(expiration));
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            signJson = JSONObject.fromObject(respMap);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        }
        return signJson; 
	}
	
	/**
	 * 读取文件流
	 * 
	 * @author zhy
	 * @param key
	 * @return
	 */
	public static InputStream getOssFileSteam(String key) {
		OSSClient ossClient = new OSSClient(endpointLocal, accessKeyId,accessKeySecret);
		OSSObject ossObject = ossClient.getObject(bucketName, key);
		return ossObject.getObjectContent();
	}
}