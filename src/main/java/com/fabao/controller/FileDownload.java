package com.fabao.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FileDownload {
	@RequestMapping("/file/{name.rp}")
	public ResponseEntity<byte[]> fileDownLoad(@PathVariable("name.rp") String name, HttpServletRequest request,
			HttpServletResponse response) {
		// @PathVariable String name,
		// @RequestParam("name")String name,
		// System.out.println("<name>"+name);
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		ResponseEntity<byte[]> re = null;
		try {
			/**
			 * css,js,json,gif,png,bmp,jpg,ico,doc,docx,xls,xlsx,txt,swf,pdf
			 **/
			// 下载防止静态加载干扰
			//Feelutile f = new Feelutile();
			//name = f.getfileformat(name);
			
			//读文件
			String pathString = name.replaceAll("\\|", "/");
			File file = new File(pathString);
			byte[] by = FileUtils.readFileToByteArray(file);
			
			//写文件
			String fileName=pathString.substring(pathString.lastIndexOf("/")+1);
			fileName = new String(fileName.getBytes("utf-8"), "utf-8");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentLength(by.length);
			re = new ResponseEntity<byte[]>(by, headers, HttpStatus.CREATED);
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				request.getRequestDispatcher("/view/error.html").forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return re;
	}
}
