package com.fabao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilWithXSSF {
	private HashMap<String,String> columnMapper;
	//列名说明集合
	
	/**
	 * 创建Excel，并写入内容
	 * @throws Exception 
	 */
	public  void CreateExcel(String path,String[][] content) throws Exception{
		//创建Excel工作薄对象
		Workbook wb = new XSSFWorkbook();
		//创建Excel工作表对象
		CreationHelper createHelper = wb.getCreationHelper();
	    Sheet sheet1 = wb.createSheet("sheet1");
	    for(int i=0;i<content.length;i++)
	    {
	    	String[] rowContent=content[i];
	    	Row row = sheet1.createRow((short)i);
	    	String columnValue;
	    	for(int j=0;j<rowContent.length;j++){
	    		columnValue=rowContent[j];
	    		if(i==0){
	    			if(columnMapper!=null && columnMapper.containsKey(columnValue)) {
	    				columnValue=columnMapper.get(columnValue);
	    			}
	    		}
	    		row.createCell(j).setCellValue(columnValue);
	    	}
		    //Cell cell = row.createCell(0);
		    //row.createCell(2).setCellValue( createHelper.createRichTextString("This is a string"));
	    }
	    //保存
	    FileOutputStream fileOut = new FileOutputStream(path);
	    wb.write(fileOut);
	    fileOut.close();
	}
	
	public ExcelUtilWithXSSF(HashMap columnMapper) {
		super();
		columnMapper = columnMapper;
	}

	public HashMap getColumnMapper() {
		return columnMapper;
	}

	public void setColumnMapper(HashMap columnMapper) {
		columnMapper = columnMapper;
	}

	/**
	 * 得到Excel，并解析内容  对2007及以上版本 使用XSSF解析
	 * @param file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidFormatException 
	 */
	public static void getExcelAsFile(String file) throws FileNotFoundException, IOException, InvalidFormatException{
//		//1.得到Excel常用对象
//		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("d:/FTP/new1.xls"));
//		//2.得到Excel工作簿对象
//		HSSFWorkbook wb = new HSSFWorkbook(fs);
		
		InputStream ins = null;   
        Workbook wb = null;   
            ins=new FileInputStream(new File(file));   
            //ins= ExcelService.class.getClassLoader().getResourceAsStream(filePath);   
            wb = WorkbookFactory.create(ins);   
            ins.close();   
		
		
		//3.得到Excel工作表对象
		Sheet sheet = wb.getSheetAt(0);
		//总行数
		int trLength = sheet.getLastRowNum();
		//4.得到Excel工作表的行
		Row row = sheet.getRow(0);
		//总列数
		int tdLength = row.getLastCellNum();
		//5.得到Excel工作表指定行的单元格
		Cell cell = row.getCell((short)1);
		//6.得到单元格样式
		CellStyle cellStyle = cell.getCellStyle();

		for(int i=5;i<trLength;i++){
			//得到Excel工作表的行
			Row row1 = sheet.getRow(i);
			for(int j=0;j<tdLength;j++){
			//得到Excel工作表指定行的单元格
			Cell cell1 = row1.getCell(j);
			/**
			 * 为了处理：Excel异常Cannot get a text value from a numeric cell
			 * 将所有列中的内容都设置成String类型格式
			 */
			if(cell1!=null){
		          cell1.setCellType(Cell.CELL_TYPE_STRING);
		     }
			
			if(j==5&&i<=10){
				cell1.setCellValue("1000");
			}
			
			//获得每一列中的值
			System.out.print(cell1+"                   ");
			}
			System.out.println();
		}
		
		//将修改后的数据保存
		OutputStream out = new FileOutputStream(file);
                wb.write(out);
	}
	
	
}

