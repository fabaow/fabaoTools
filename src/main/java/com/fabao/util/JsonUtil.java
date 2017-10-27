package com.fabao.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
	public static class ValuePos{
		String value;
		int posMain;
		int posSub;
		public ValuePos(String value, int posMain, int posSub) {
			super();
			this.value = value;
			this.posMain = posMain;
			this.posSub = posSub;
		}
		
	}
	private static void putValuePos(HashMap<String,List<ValuePos>> content,String key,ValuePos valuePos ){
		if(content.containsKey(key)){
			content.get(key).add(valuePos);
		}else{
			ArrayList<ValuePos> list=new ArrayList<ValuePos>();
			list.add(valuePos);
			content.put(key, list);
		}
	}
	public static String[][] jsonToArray(String jsonContext,String jsonListTag,HashMap<String,String> columnMapper){
		HashMap<String,List<ValuePos>> myContent=new HashMap<String,List<ValuePos>>();
		
		//获得Json数组
		JSONObject jobj=null;
		JSONArray jarr=null;
		jobj=JSON.parseObject(jsonContext);
		jarr= JSON.parseArray(jobj.getString(jsonListTag));
		
		ValuePos valuePos;
		//对于Json数组的每一个Json对象,记录序号i
		for(int i=0;i<jarr.size();i++){
			JSONObject jobjMain=jarr.getJSONObject(i);
			//循环key:mainKey
			for(String mainKey:jobjMain.keySet()){
				String sMain=jobjMain.getString(mainKey);
				
				if(sMain.startsWith("[") && sMain.endsWith("]")){
					//若是Json数组 再循环每一个Json对象,记录序号j
					JSONArray jarrSub=JSON.parseArray(sMain);
					for(int j=0;j<jarrSub.size();j++){
						JSONObject jobjSub=jarrSub.getJSONObject(j);
						//循环key:subKey
						for(String subKey:jobjSub.keySet()){
							//valuePos.value=值，valuePos.posMain=i,valuePos.posSub=j 添加到myContent<mainKey.subKey>
							valuePos=new ValuePos(jobjSub.getString(subKey),i,j);
							
							putValuePos(myContent,mainKey+"."+subKey, valuePos);
						}
					}
				}else{
					//若是Json对象，valuePos.value=值，valuePos.posMain=i,valuePos.posSub=0 添加到myContent<mainKey>
					valuePos=new ValuePos(sMain,i,0);
					putValuePos(myContent,mainKey, valuePos);
				}
			}
		}
		
		//循环myContent 得到每个posMain部分的行数
		List<Integer> posMainSubCount=new ArrayList<Integer>();
		for(int i=0;i<jarr.size();i++){
			posMainSubCount.add(0);
		}
		for(String key:myContent.keySet()){
			List<ValuePos> list=myContent.get(key);
			for(int i=0;i<list.size();i++){
				if (posMainSubCount.get(list.get(i).posMain)<list.get(i).posSub+1){
					posMainSubCount.set(list.get(i).posMain,list.get(i).posSub+1);
				}
			}
		}
		
		//累计posMainSubCount 得到每个posMain起始的位置
		List<Integer > posMainStartIndex=new ArrayList<Integer>();
		for(int i=0;i<jarr.size();i++){
			posMainStartIndex.add(0);
		}
		int rowCount=0;
		for(int i=0;i<posMainStartIndex.size();i++)
		{
			for(int j=0;j<i;j++){
				posMainStartIndex.set(i, posMainStartIndex.get(i)+posMainSubCount.get(j));
			}
			rowCount=rowCount+posMainSubCount.get(i);
		}
		
		//定义二维数组 //循环myContent,将值写在二维数组正确位置
		String[][] twoDemArray=new String[rowCount+1][myContent.size()];
		HashMap<String,Integer> columns=new HashMap<String,Integer>();
		List<String> columnList=new ArrayList<String>();
		int c=0;
		for(String key:myContent.keySet()){
			columnList.add(key);
		}
		Collections.sort(columnList);
		for(int i=0;i<columnList.size();i++){
			columns.put(columnList.get(i), i);
		}
		
		for(String key:myContent.keySet()){
			String header=key;
			if(columnMapper!=null && columnMapper.containsKey(header)){
				header=columnMapper.get(header);
			}
			twoDemArray[0][columns.get(key)]=header;
		}

		for(String key:myContent.keySet()){
			List<ValuePos> list=myContent.get(key);
			for(int i=0;i<list.size();i++){
				valuePos=list.get(i);
				twoDemArray[posMainStartIndex.get(valuePos.posMain)+valuePos.posSub+1][columns.get(key)]=valuePos.value;
			}
		}
		return twoDemArray;
	}
}
