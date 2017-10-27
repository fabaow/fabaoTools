package com.fabao.task;


import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
@Component
public class TestTask {
	@Autowired 
	DruidDataSource dataSource;
	
	private final static Logger logger = LoggerFactory.getLogger(TestTask.class);
	private final  String pattern1="0 * * * * ?";
	private final  String pattern2="0 0 14 * * ?";
	//@Scheduled(cron=pattern1)
	public void autoGet(){
		autoTask("T_TASK_SYNC_GET","get");
	}
    private void autoTask(String tableName,String taskName){
		//获取计算机名
		InetAddress addr = null;
        String address = "";
        try {
            addr = InetAddress.getLocalHost();//新建一个InetAddress类
            address = addr.getHostName().toString();// 获得本机名称
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(address);
		
		//1.加锁（写），不成功 退出
		Connection conn;
		Statement stat;
		ResultSet rs;
		String sqlUnlock="unlock tables";
		try {
			 conn= dataSource.getConnection();
			 stat=conn.createStatement();
			 stat.setQueryTimeout(5);
		} catch (Exception e) {
			logger.info("不能启动,数据库连接失败."+e.getMessage());
			return;
		}
		int retrytimes=0;
		while (retrytimes<3 && retrytimes>=0){
			//等待随机时间1分钟内
	        java.util.Random random=new java.util.Random();// 定义随机类
	        int rt=random.nextInt(10000);//
	        try {
				Thread.sleep(rt);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			try {
				 stat.executeQuery("lock tables "+tableName+" write");//锁表
				 retrytimes=-1;
			} catch (Exception e) {
				retrytimes=retrytimes+1;
			}
		}
		if(retrytimes>=0){
			logger.info("不能启动,锁表失败.");
				return;
		}
		//2.是否有当天运行，若有，退出，否则新建
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql="select * from "+tableName+" where RunDate='"+df.format(new Date())+"'";
		try{
			rs=stat.executeQuery(sql);
			if(rs.next()){
				logger.info("不能启动，已经运行过");
				stat.executeQuery(sqlUnlock);
				return;
			}
			sql="INSERT INTO T_TASK_SYNC_GET (RunDate, RunIP, RunStatus) VALUES ('"+df.format(new Date())+"', '"+address+"', '0')";
			stat.executeUpdate(sql);
		}catch(Exception e){
			try{stat.executeQuery(sqlUnlock);}catch(Exception e1){}
			return;
		}
		
		//3.运行任务。更新为运行中，结束更新为成功结束，异常更新为异常结束
		try{
			sql="update  T_TASK_SYNC_GET set RunStatus='1'";
			stat.executeUpdate(sql);
			try{
				if(taskName=="get" ){doGetTask();}
				if(taskName=="send" ){doSendTask();}
				
				sql="update  T_TASK_SYNC_GET set RunStatus='2'";
				stat.executeUpdate(sql);
				stat.executeQuery(sqlUnlock);
				logger.info("结束,成功运行.");
			}catch(Exception e){
				sql="update  T_TASK_SYNC_GET set RunStatus='3'";
				stat.executeUpdate(sql);
				stat.executeQuery(sqlUnlock);
				logger.info("结束,异常."+e.getMessage());
			}
		}catch(Exception e){
			try{stat.executeQuery(sqlUnlock);}catch(Exception e1){}
			return;
		}
    }
	private void doGetTask(){
		logger.info("*****************");
		for(int i=0;i<5;i++){
			logger.info("get");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	private void doSendTask(){
		logger.info("*****************");
		for(int i=0;i<5;i++){
			logger.info("send");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
