package tools;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fabao.util.ExcelUtilWithXSSF;


//@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
//@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  

public class Test1 {
	@Test
	public void t1(){
		ExcelUtilWithXSSF exu=new ExcelUtilWithXSSF(null);
		System.out.println("df");
		String path="d:/tmp/hello.xlsx";
		String[][] c=new String[][]{{"姓名","年龄"},{"sf","33"},{"发","dd"}};
		try {
			exu.CreateExcel(path,c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finish");
	}

}
