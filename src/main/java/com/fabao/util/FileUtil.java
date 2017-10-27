package com.fabao.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
	
	//
	public FileUtil() {
	}
	
	/**
	 * 创建文件
	 * @throws IOException
	 */
	public static File createFile(String dir,String fileName) throws IOException {
		String fullPath=null;
		if(dir==null)
			fullPath=fileName;
		else if(dir.endsWith(File.separator)==false)
			fullPath=dir+File.separator+fileName;
		else
			fullPath=dir+fileName;
		File file = new File(fullPath);
		file.createNewFile();
		return file;
	}

	/**
	 * 创建目录
	 * @param dirName
	 */
	public static File creatDir(String dir) {
		File dirFile = new File(dir);
		dirFile.mkdirs();
		return dirFile;
	}

	/**
	 * 判断文件是否存在
	 */
	public static boolean isFileExist(String fileName) {
		return isFileExist("",fileName);
	}
	public static boolean isFileExist( String dir,String fileName) {
		String fullPath=null;
		if(dir==null || "".equals(dir))
			fullPath=fileName;
		else if(dir.endsWith(File.separator)==false)
			fullPath=dir+File.separator+fileName;
		else
			fullPath=dir+fileName;
		File file = new File(fullPath);
		return file.exists();
	}
	/**
	 * 删除文件
	 */
	public static boolean deleteFile( String dir,String fileName) {
		String fullPath=null;
		if(dir==null)
			fullPath=fileName;
		else if(dir.endsWith(File.separator)==false)
			fullPath=dir+File.separator+fileName;
		else
			fullPath=dir+fileName;
		File file = new File(fullPath);
		if(file.exists())
			return file.delete();
		return true;
	}
	/**
	 * 将字符串数据写入到文件中
	 */
	public static File write2FileFromString(String dir, String fileName,String text,Boolean overWrite) {

		File file = null;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter=null;
		
		try {
			//文件存在
			if(isFileExist(dir,fileName))
			{
				if(overWrite=false)
					return null;
				else
					FileUtil.deleteFile(dir, fileName);
			}
			if (isFileExist(dir,"")==false)  
				creatDir(dir);
			
			//创建文件,写入
			file = createFile(dir,fileName);
			bufferedWriter =new BufferedWriter(new FileWriter(file));
			bufferedWriter.write(text);
			bufferedWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public static String getTextFromFile(String dir, String fileName)
	{
		//全文件名
		String fullPath=null;
		if(dir==null||"".equals(dir))
			fullPath=fileName;
		else if(dir.endsWith(File.separator)==false)
			fullPath=dir+File.separator+fileName;
		else
			fullPath=dir+fileName;
		
		//读取
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		String text="";
		String lineText="";
		try {
			bufferedReader=new BufferedReader(new FileReader(fullPath));
			while((lineText=bufferedReader.readLine())!=null){
				text+=lineText;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (Exception e) {
				//e.printStackTrace();
			}
			try {
				bufferedReader.close();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		
		return text;
	}
	public static String getTextFromFile2(String dir, String fileName)
	{
		//全文件名
		String fullPath=null;
		if(dir==null)
			fullPath=fileName;
		else if(dir.endsWith(File.separator)==false)
			fullPath=dir+File.separator+fileName;
		else
			fullPath=dir+fileName;
		
		//读取
		FileReader fileReader=null;
		
		StringBuilder sb=new StringBuilder();
		char[] buffer=new char[8*1024];
		int len;
		try {
			fileReader=new FileReader(fullPath);
			while((len=fileReader.read(buffer))!=-1){
				sb.append(buffer,0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new String(sb);
	}
	/**
	 * 将一个InputStream里面的数据写入到文件中
	 */
	public static File write2FileFromInput(String dir, String fileName,InputStream input,Boolean overWrite) {

		File file = null;
		OutputStream output = null;
		try {
			//文件存在
			if(isFileExist(dir,fileName))
			{
				if(overWrite=false)
					return null;
				else
					FileUtil.deleteFile(dir, fileName);
			}
			if (isFileExist(dir,"")==false)  
				creatDir(dir);
			//创建文件
			file = createFile(dir,fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int temp;
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	

}