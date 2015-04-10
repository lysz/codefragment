package com.aora.wifi.util;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Date;

import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;


import org.apache.log4j.PropertyConfigurator;

public class Log {
	

	

	
	public static void info(String msg,String type){

		sqllog.info(msg);

	}
	
	public static void info(Object msg){
		
			sqllog.info(msg);
		
	}

	
	public static void debug(Object msg){		
		if (isWrite){
			sqllog.debug(msg);
		}
	}
	
	public static void error(Object msg,String type){

		sqllog.error(msg);

	}
	
	public static void error(Object msg){
		//if (isWrite){
		sqllog.error(msg);
		//}
	}
	

	
	public static void setIsWrite(boolean boolvalue){
		isWrite = boolvalue;
	}

	
	
	private final static Logger sqllog = Logger.getLogger("sqllog");

	private static boolean isWrite = false;
	
	static{
		String log4jFile = "/log4j.properties";
		URL url = Log.class.getResource(log4jFile);
		PropertyConfigurator.configure(url);		
	}
	
	public static void main(String[] arg){
		sqllog.info("sdfsfsdf");
	}
	
}
