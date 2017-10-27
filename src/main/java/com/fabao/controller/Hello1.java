package com.fabao.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Hello1 {
	@Autowired
    private ApplicationContext applicationContext;
	
	@RequestMapping("/test1")
	private String test1(){
		String path=this.getClass().getResource("/").toString();
		path=path.substring(6);
		if(path.endsWith("/")==false) path=path+"/";
		path=path+"../../files";
		
		return path ;
	}
	@RequestMapping("/test2")
	private ResponseEntity<byte[]>  test2(){
		ResponseEntity<byte[]> re = null;
		 CloseableHttpClient httpClient = null;    
         CloseableHttpResponse response = null;    
         HttpEntity entity = null;    
         byte[] by = null;    
         try {    
             // 创建默认的httpClient实例.    
             httpClient = HttpClients.createDefault();    
             HttpPost httpPost=new HttpPost("http://localhost:8080/tools/file/c:%7Ctmp%7Cff.txt");
             httpClient.execute(httpPost);
             entity=httpPost.getEntity();
             // 执行请求    
             response = httpClient.execute(httpPost);    
             entity = response.getEntity();   
             by = EntityUtils.toByteArray(entity); 

            HttpHeaders headers = new HttpHeaders();
 			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
 			headers.setContentDispositionFormData("attachment", "ff.txt");
 			headers.setContentLength(by.length);
 			re = new ResponseEntity<byte[]>(by, headers, HttpStatus.CREATED);
         } catch (Exception e) {    
             e.printStackTrace();    
         } finally {    
             try {    
                 // 关闭连接,释放资源    
                 if (response != null) {    
                     response.close();    
                 }    
                 if (httpClient != null) {    
                     httpClient.close();    
                 }    
             } catch (IOException e) {    
                 e.printStackTrace();    
             }    
         }    
		return re;
		
	}	
	
	@RequestMapping("/test3")
	private ResponseEntity<byte[]>  test3(){
		String message;
		ResponseEntity<byte[]> re = null;
		 CloseableHttpClient httpClient = null;    
         CloseableHttpResponse response = null;    
         HttpEntity entity = null;    
         byte[] by = null;    
         try {    
             // 创建默认的httpClient实例.    
             httpClient = HttpClients.createDefault();    
             //HttpPost httpPost=new HttpPost("http://localhost:8080/tools/file/c:%7Ctmp%7Cff.txt");
             HttpPost httpPost=new HttpPost("http://localhost:7210/o2o_settle/fileDown/toSendExcel");
             //httpClient.execute(httpPost);
             entity=httpPost.getEntity();
             // 执行请求    
             response = httpClient.execute(httpPost);    
             entity = response.getEntity();   
             by = EntityUtils.toByteArray(entity); 
             
             HttpHeaders headers = new HttpHeaders();
 			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
 			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
 			headers.setContentDispositionFormData("attachment", "settleToSend"+df.format(new Date())+".xlsx");
 			headers.setContentLength(by.length);
 			re = new ResponseEntity<byte[]>(by, headers, HttpStatus.CREATED);
 			
//             FileOutputStream fos = new FileOutputStream("c:\\B.xlsx");
//             fos.write(by);
//             fos.close();
             
             message="finish";
             
//             HttpHeaders headers = new HttpHeaders();
// 			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
// 			headers.setContentDispositionFormData("attachment", "ff.txt");
// 			headers.setContentLength(by.length);
// 			re = new ResponseEntity<byte[]>(by, headers, HttpStatus.CREATED);
            // responseContent = EntityUtils.toString(entity, "UTF-8");  //如果需要返回字符串改这里就行了  
         } catch (Exception e) {    
             e.printStackTrace();
             message="error"+e.getMessage();
         } finally {    
             try {    
                 // 关闭连接,释放资源    
                 if (response != null) {    
                     response.close();    
                 }    
                 if (httpClient != null) {    
                     httpClient.close();    
                 }    
             } catch (IOException e) {    
                 e.printStackTrace();    
             }    
         } 
         return re;
		//return message;
		
	}	
}
