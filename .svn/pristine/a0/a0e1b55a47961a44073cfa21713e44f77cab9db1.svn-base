package priv.zhouhuayi.restaurant.util.aliyun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;

public class OSSTest {
	public static void main(String[] args) {
		// endpoint以杭州为例，其它region请按实际情况填写
		String endpoint = "oss-cn-hangzhou.aliyuncs.com";
		// accessKey请登录https://ak-console.aliyun.com/#/查看
		String accessKeyId = "LTAIPcg7ZlHirzKJ";
		String accessKeySecret = "1pYh42rm1QYq25rc4D4PiI0lPSudSp";
		
		String bucketName = "zhouhuayi1";
		String key = "testObj";
		
		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();
		// 设置自定义元信息name的值为my-data
		meta.addUserMetadata("property", "property-value");
		
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 使用访问OSS
		
        String dir = "user-dir";
        String host = "http://" + bucketName + "." + endpoint;
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try { 	
        	long expireTime = 30;
        	long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);
            
            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessKeyId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            //respMap.put("expire", formatISO8601Date(expiration));
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            JSONObject ja1 = JSONObject.fromObject(respMap);
            System.out.println(ja1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
		// 列举bucket
		List<Bucket> buckets = ossClient.listBuckets();
		for (Bucket bucket : buckets) {
		    System.out.println(" - " + bucket.getName());
		}
		
		// 新建一个Bucket
//		ossClient.createBucket(bucketName);
		
		/* 上传字符串流 */
//		String contentStr = "Hello OSS";
//		ossClient.putObject(bucketName, key, new ByteArrayInputStream(contentStr.getBytes()));
		
		try {
			// 上传网络流
//			InputStream urlInputStream = new URL("https://www.aliyun.com/").openStream();
//			ossClient.putObject(bucketName, key, urlInputStream);
			
			// 上传文件流
//			InputStream inputStream = new FileInputStream("pom.xml");
//			ossClient.putObject(bucketName, "localFileStream", inputStream, meta);
			
			// 上传文件
//			ossClient.putObject(bucketName, "localFile", new File("pom.xml"));
//			String str = "test append";
//			
//			ossClient.deleteObject(bucketName, "appendFile");
//			AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, "appendFile",
//					new ByteArrayInputStream(str.getBytes()));
//			
//			// 第一次追加
//			appendObjectRequest.setPosition(0L);
//			AppendObjectResult appendObjectResult = ossClient.appendObject(appendObjectRequest);
//			appendObjectRequest.setInputStream(new ByteArrayInputStream(str.getBytes()));
//			
//			// 第二次追加
//			appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
//			appendObjectRequest.setInputStream(new ByteArrayInputStream("aaa".getBytes()));
//			appendObjectResult = ossClient.appendObject(appendObjectRequest);
			
//			ossClient.deleteObject(bucketName, "uploadFileUtil");
//			Long startTimes = new Date().getTime();
//			// 设置断点续传请求
//			UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, "uploadFileUtil");
//			System.out.println(uploadFileRequest.getUploadFile());
//			// 指定上传的本地文件
//			uploadFileRequest.setUploadFile("e:/zhy资料文件夹/redis-desktop-manager-0.8.8.384.exe");
//			// 指定上传并发线程数
//			uploadFileRequest.setTaskNum(5);
//			// 指定上传的分片大小
//			uploadFileRequest.setPartSize(1 * 1024 * 1024);
//			// 开启断点续传
//			uploadFileRequest.setEnableCheckpoint(true);
//			// 断点续传上传
//			ossClient.uploadFile(uploadFileRequest);
//			
//			Long endTimes = new Date().getTime();
//			
//			System.out.println(endTimes - startTimes);
			
			/* 读取oss文件 */
			OSSObject ossObject = ossClient.getObject(bucketName, "appendFile");
			InputStream contentIo = ossObject.getObjectContent();
			if (contentIo != null) {
			    BufferedReader reader = new BufferedReader(new InputStreamReader(contentIo));
			    while (true) {
			        String line = reader.readLine();
			        if (line == null) break;
			        System.out.println("\n" + line);
			    }
			    contentIo.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		/* 列举Object */
		ObjectListing objectListing = ossClient.listObjects(bucketName);
		for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
		    System.out.println(" - " + objectSummary.getKey() + "  " + 
		            "(size = " + objectSummary.getSize() + ")");
		}
		
		//删除Object
		ossClient.deleteObject(bucketName, key);
		
		// 关闭client
		ossClient.shutdown();
	}
}
