package com.fabao.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fabao.util.ExcelUtilWithXSSF;
import com.fabao.util.JsonUtil;

@Controller
@RequestMapping("/json/trans")
public class JsonTrans {
	@RequestMapping("")
	private String jsonTrans(@RequestParam Map<String,String> paramMap,Model model){
		setModel(paramMap,model);
		return "jsonTrans";
	}
	@RequestMapping("/excel")
	private ResponseEntity<byte[]> jsonToExcel(@RequestParam Map<String,String> paramMap,HttpServletRequest request,
			HttpServletResponse response){
		
		String[][] content=null;//new String[][]{{"姓名","年龄"},{"sf","33"},{"发","dd"}};
		
		//json to string[][]
		String jsonContext=paramMap.get("jsonContent");
		String jsonListTag=paramMap.get("jsonListTag");
		String jsonHeaderTemp=paramMap.get("jsonHeaderTemp");
		HashMap<String,String> columnMapper=specialMapper(jsonHeaderTemp);
		content=JsonUtil.jsonToArray(jsonContext, jsonListTag,columnMapper);
		
		//excelPath 
		String path=this.getClass().getResource("/").toString();
		path=path.substring(6);
		if(path.endsWith("/")==false) path=path+"/";
		path=path+"../../files/";
		SimpleDateFormat dfl = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String excelPath=path+"jsonToExcel"+dfl.format(new Date())+".xlsx";
		
		//生成Excel文件
		ExcelUtilWithXSSF exu=new ExcelUtilWithXSSF(null);
		try {
			exu.CreateExcel(excelPath,content);
		} catch (Exception e) {
			try{request.getRequestDispatcher("/view/error.html").forward(request, response);}catch(Exception e1){};
		}
		//下载文件
		FileDownload fileDown=new FileDownload();
		return fileDown.fileDownLoad(excelPath, request, response);
	}
	private HashMap<String,String> specialMapper(String special){
		HashMap<String,String> columnMapper=new HashMap<String,String> ();
		if(special!=null && special.equals("settle")){
			columnMapper.put("accDate", "对账日期");
			columnMapper.put("businessType", "业务类型");
			columnMapper.put("dataSources", "业务类型");
			columnMapper.put("orderCouponList.activityId", "活动id");
			columnMapper.put("orderCouponList.activityMsg", "活动描述");
			columnMapper.put("orderCouponList.couponType","优惠类型");
			columnMapper.put("orderCouponList.objDiscountAmt", "对象承担方（折扣折让）");
			columnMapper.put("orderCouponList.objPromotionAmt", "对象承担方（营促销）");
			columnMapper.put("orderCouponList.objSlv", "对象承担运费营促销成本税率");
			columnMapper.put("orderCouponList.subjectAmt", "主体承担优惠");
			columnMapper.put("orderCouponList.subjectSlv", "主体承担运费营促销成本税率");
			columnMapper.put("orderNo", "单据号");
			columnMapper.put("payAmt", "支付金额");
			columnMapper.put("payPoundage", "支付手续费金额");
			columnMapper.put("payPoundageSlv", "支付手续费税率");
			columnMapper.put("payType", "支付手续费类型（1比率，2金额）");
			columnMapper.put("saleAmt", "订单金额");
			columnMapper.put("settleObjNo", "结算对象");
			columnMapper.put("settleRuleNo", "结算规则号");
			columnMapper.put("settleSubjectNo", "结算主体");
			columnMapper.put("slv", "订单金额税率");
			columnMapper.put("yfAmt", "运费");
			columnMapper.put("yfSlv", "运费税率");
			columnMapper.put("yjAmt", "佣金金额");
			columnMapper.put("yjSlv", "佣金税率");
			columnMapper.put("yjType", "佣金类型（1比率，2金额）");
		}
		return columnMapper;
		
	}
//	private void initMap( Map<String,String> paramMap){
//		if (paramMap.get("excelPath")==null){
//			paramMap.put("excelPath","c:/jsonToExcel.xlsx");
//		}
//	}
	private void setModel(Map<String,String> paramMap,Model model){
		for (String key : paramMap.keySet()) { 
			String value=paramMap.get(key);
			model.addAttribute(key, value);
		}
	}
	//@RequestMapping("/mysql")
}
