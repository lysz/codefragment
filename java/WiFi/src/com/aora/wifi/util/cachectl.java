package com.aora.wifi.util;

import com.whirlycott.*;
import com.whirlycott.cache.*;
import java.io.*;
import java.util.*;

public class cachectl {
	
	private static com.whirlycott.cache.Cache cache;
	private static com.whirlycott.cache.CacheManager cacheManager;
		
	
	static{
		init();
	}
	
	private synchronized static void init(){
		try{
			
			 if(cache!=null){
				 cache.clear(); 
				 cache=null;
			 }
			 
			 if (cacheManager == null){
				 cacheManager = cacheManager.getInstance();
			 }
			 cache = cacheManager.getCache();
			 			
			 
		}catch(Exception e){e.printStackTrace();}
	}

	  public synchronized static void addcontent(String key,Object content){
		  	// default expired time
		  	if(cache==null){init();}
		  	//System.out.println(System.currentTimeMillis()+":load content to cache and  the key is "+key );
		    cache.store(key,content,900*1000);
		  }

	  public synchronized static void addcontent(String key,Object content,Long expiredtime){
		  if(cache==null){init();}
		  	cache.store(key,content,expiredtime);
		  }
	  
	  
	  public synchronized static void removecontent(String key){
		  cache.remove(key);
	  }
	  
	  public synchronized static void removeallcontent(){
		  cache.clear();		  
	  }
	  
	  public static int numOfCacheItem(){
		  return cache.size();
	  }
	  
	  public synchronized  static boolean isexistcontent(String key){
		  if(cache==null){init();}		  
		  if(cache.retrieve(key)==null){
			  return false;
		    }	
	    	else{
		      return true;
		    }
		  }

	  public   static Object getcontent(String key){
		 
		  if(cache==null){init();}
		  return cache.retrieve(key);
	}
	

	  
//	  public synchronized static void addcontent(Content obj){
//		  if(cache==null){init();}
//		  //Long expiredtime=900000L;
//	  	//System.out.println(System.currentTimeMillis()+":load content to cache and  the key is "+obj.getKey() );						  
//		  //addcontent(obj.getKey(),obj,900000L);
//
//	  }
	
}
