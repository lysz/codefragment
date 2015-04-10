package com.aora.bookmark.downloadapk.apk360.service.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.aora.bookmark.downloadapk.apk360.domain.APKCategoryInfo;
import com.aora.bookmark.downloadapk.apk360.domain.APKInfo;
import com.aora.bookmark.downloadapk.apk360.service.GetAPKInfoService;
import com.aora.bookmark.service.Qihu360ApkService;
import com.aora.bookmark.tools.PropertyInfo;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

public class GetAPKInfoServiceImpl implements GetAPKInfoService {
	
	static{
	    LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	    
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF); 
	    java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(java.util.logging.Level.OFF);
		//java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
//		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
//		java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(java.util.logging.Level.OFF);
//	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	    System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	}
	
	private final Object lock = new Object();
	
	//未下载的APP
	private final List<APKInfo> unDownloadAPKCache = new ArrayList<APKInfo>();
	//下载次数超过5次APP, 如果下载次数超过5次, 将不会再下载
	private final Map<String, Integer> downloadFiledCache = new HashMap<String, Integer>();
	
	private String dateStr = null;
	
	private static  Logger logger = Logger.getLogger(GetAPKInfoServiceImpl.class);
	
	private Qihu360ApkService qihu360ApkService;
	
	public Qihu360ApkService getQihu360ApkService() {
		return qihu360ApkService;
	}

	public void setQihu360ApkService(Qihu360ApkService qihu360ApkService) {
		this.qihu360ApkService = qihu360ApkService;
	}
	
	
	private final String gameCategoryURL;
	private final String domainURL;
	private final String softCategoryURL;
	private final String getAPKDownloadBySID;
	
	
	private final int maxPageSize;
	private final int maxSizeAPK;
	
	
	public GetAPKInfoServiceImpl(){
		maxSizeAPK = Integer.valueOf(PropertyInfo.getValue("gbobal.maxsizeapk", null));
		maxPageSize = Integer.valueOf(PropertyInfo.getValue("global.pagesize", null));
		domainURL = PropertyInfo.getValue("360.zhushou.domain.url", null);
		gameCategoryURL = PropertyInfo.getValue("360.game.category.url", null);
		softCategoryURL = PropertyInfo.getValue("360.soft.category.url", null);
		getAPKDownloadBySID = PropertyInfo.getValue("360.zhushou.apk.download.sid", null);
	}
	
	
	public void init(){
	    
	    if (null == dateStr){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            
            //因为 dateStr 方法是成员变量, 每一次任务调度都要修改dateStr, 为了安全起见, 这里加了同步
            synchronized(lock){             
                dateStr = sdf.format(new Date());
            }
	    }	    
	}

	@Override
	public List<APKCategoryInfo> getAllCategoryFromPage(){
		//分类的链接
		Map<String, String> allCategoryLinks = getAllCategoryLinks();
		
		List<APKCategoryInfo> categoryInfoList = new ArrayList<APKCategoryInfo>();
		
		for (Map.Entry<String, String> entry : allCategoryLinks.entrySet()){
			String categoryName = entry.getKey();
			String categoryLink = entry.getValue();
			String fullLink = domainURL + categoryLink;
			
			logger.info(categoryName + " url: " + fullLink);
			
			APKCategoryInfo categoryInfo = new APKCategoryInfo();
			categoryInfo.setCategoryName(categoryName);
			categoryInfo.setUrl(fullLink);
			
			categoryInfoList.add(categoryInfo);
		}
		
		return categoryInfoList;
	}
	
	
	
	/**
	 * 抓取页面上各个分类的超链接
	 * @return 已经抓取的各个分类的超链接
	 * @throws Exception
	 */
	private Map<String, String> getAllCategoryLinks() {
		
		if (StringUtils.isEmpty(gameCategoryURL) && StringUtils.isEmpty(domainURL)){
			logger.error("The [gameCategoryURL] or [domainURL] arg are empty.");
			throw new IllegalArgumentException("The [gameCategoryURL] or [domainURL] arg are empty.");
		}
		
		Document gameCategoryDoc = getPageDocumenteByURL(gameCategoryURL);
		Document softCategoryDoc = getPageDocumenteByURL(softCategoryURL);
		
		Map<String, String> allCategoryLinks = new HashMap<String, String>(10);
	
        parseDocumentAndSaveLink(gameCategoryDoc, allCategoryLinks);
        parseDocumentAndSaveLink(softCategoryDoc, allCategoryLinks);
		
		return allCategoryLinks;
	}
    

	/**
	 * 解析html保存各个分类的超链接
	 * @param doc
	 * @param categoryLinks
	 */
	private void parseDocumentAndSaveLink(Document doc, Map<String, String> categoryLinks){
		
	    if (null == doc){
	        return;
	    }
	    
	    try{
	        //直接定位到类型的li元素
	        Element allCategoryElements = doc.select("div.main > div.clsIx > ul.select > li").first();
	        Elements links = allCategoryElements.getElementsByTag("a");
	        
	        for (Element link : links){
	            String href = link.attr("href");
	            String text = link.text();
	            
	            if ("全部".equals(text)){
	                continue;
	            }
	            
	            categoryLinks.put(text, href);
	        }
	        
	    }catch(Exception e){
	        logger.error("parse category link failed.", e);
	    }
	}

	/**
	 * apk的信息入库
	 * @param 要入库的app信息
	 */
	@Override
	public void saveOrUpdateAPKInfo(List<APKInfo> apkInfos){
		getQihu360ApkService().saveOrUpdateAPKInfo(apkInfos);
	}

	/**
	 * 保存分类的URL等信息
	 * @param 要入库的app分类信息
	 */
	@Override
	public void saveCategory(List<APKCategoryInfo> categorys){
		
		getQihu360ApkService().saveAPKCategoryInfo(categorys);		
	}

	
	/**
	 * 遍历所有的分类，每个分类都取前50页的数据
	 * @param allCategorys
	 * @return
	 * @throws Exception
	 */
	public void collectionAppInfo(List<APKCategoryInfo> allCategorys){

		List<APKInfo> apks = new ArrayList<APKInfo>();
		
		//遍历所有的分类
		for (APKCategoryInfo apkCategoryInfo : allCategorys){
			
			logger.info("start to download [" + apkCategoryInfo.getCategoryName() + "] category.");
						
			//遍历分类的每一页
			for (int i = 1; i <= maxPageSize; i++){ 
				
				logger.info("The [" + i + "] page of [" + apkCategoryInfo.getCategoryName() + "] will begin.");
				
				apks.clear();
				
				String url = apkCategoryInfo.getUrl() + "?page=" + i;
				
				try{				    
				    parsePageByURL(url, apks, apkCategoryInfo);
				}catch(Exception  e){
				    logger.error("load every page of category failed. the url is:" + url, e);
				}
				
			}
		}
	}
	
	
	private void collectionPage(){
	    try{
	        //从页面抓取分类的URL等信息
	        List<APKCategoryInfo> categorys = getAllCategoryFromPage();

	        //分类信息入库
	        saveCategory(categorys);
	        
	        List<APKCategoryInfo> list = getQihu360ApkService().findAllAPKCategory();
	        
	        //抓取app
	        collectionAppInfo(list);
	    }catch(Exception e){
	        logger.error("collection page failed.", e);
	    }        
	}
	
	private void parsePageByURL(String url, List<APKInfo> apks, APKCategoryInfo apkCategoryInfo){
	    
	    logger.info("beginning to load every page of category, url is:" + url);
	    
        Document doc = getPageDocumenteByURL(url);
        
        if (null == doc){
            logger.error("Load page of category failed, url is:" + url);
            return;
        }
        
        //遍历页面上的每个app
        for (Iterator<Element> iter = doc.select("ul#iconList > li").iterator(); iter.hasNext();){
            
            Element element = iter.next();
            Element link = element.child(0);
            
            String href = link.attr("href");
            String sid = link.attr("sid");
            
            String fullLink = domainURL + href;
            
            APKInfo apk = null;
            
            try{
                //根据 detailURL 判断该app在数据库是否己存在
                APKInfo apkInDB = getQihu360ApkService().getAPKInfoByURL(fullLink);
                //数据库己存在
                if (null != apkInDB){
                    continue;
                }
                
                //单个app详细信息的采集
                apk = getAPKInfo(fullLink, apkCategoryInfo.getId());                
            }catch(Exception e){
                logger.error("load apk detail page failed. the url is:" + fullLink, e);
            }
            
            if (apk == null){
                continue;
            }
            
            String downloadURL = getAPKDownLoadURLBySID(sid);
            if (null != downloadURL){                
                apk.setDownloadURL(downloadURL);
            }
            
            apks.add(apk);
            
            if (apks.size() == 3){
                //每采集3个app就入库一次
                saveOrUpdateAPKInfo(apks);
                apks.clear();
            }
        }
        
        //再次入库.
        //之所以这样做是因为前面采取的策略是: 每3条数据入库一次, 如果不是3的倍数, 则会漏掉. 所以在这里再入库一次
        if (!apks.isEmpty()){                   
            saveOrUpdateAPKInfo(apks);
        }
	}
	
	/**
	 * 根据url加载页面, 并返回该页面的Dom
	 * @param url
	 * @return
	 */
	public Document getPageDocumenteByURL(String url){ 
	    
	    String source = null;
	    for (int i = 0; i < 5 && source == null; i++){
	    	source = getPageSource(url);
	    	//抓取失败, 暂停2秒, 再次抓取
	    	try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				
			}
	    }
	    
	    if (null == source){
	    	logger.error("Get page failed by the url:" + url);	    	
	    	return null;
	    }
	    
	    Document doc = null;
	    
	    try{
	        doc = Jsoup.parse(source);
	    }catch(Exception e){
	        logger.error("parse page source failed.", e); 
	    }
	    
		return doc;
	}
	
	/**
	 * 根据url加载页面, 返回页面的source
	 * @param url
	 * @return
	 */
	private String getPageSource(String url){
		
		String source = null;

		try {
		    
			HtmlUnitDriver driver = new MyHtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_10);
			driver.setJavascriptEnabled(true);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // 识别元素时的超时时间
			driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS); // 页面加载时的超时时间
			driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS); // 异步脚本的超时时间

			driver.get(url);
			source = driver.getPageSource();

		} catch (Exception e) {
			logger.error("load page source failed.", e);
			source = null;
		}

		return source;
	}
	
	/**
	 * 采集单个页面的信息
	 * @param url
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	private APKInfo getAPKInfo(String url, int categoryId){
		Document dom = getPageDocumenteByURL(url);
		
		if (null == dom){
		    return null;
		}
		
		DetailPageService service = new DetailPageService(dom);
		logger.info("the apk page of URL[" + url + "] has been load successed.");
		
		APKInfo apk = new APKInfo();
		apk.setName(service.getName());
		
        try{
            //apk有大小限制, 如果超过这个大小, 则不需要下载
            String strSize = service.getAPKSize();
            int apkSize = Integer.parseInt(strSize);
            if (apkSize >= maxSizeAPK){
                
                logger.error("The size of apk[" + apk.getName() + "] is greater than [" + maxSizeAPK + "], so skip.");
                return null;
            }
            
            apk.setApkSize(strSize);
        }catch(Exception e){
            logger.error("Get APK[url:" + url + "] size failed.", e);
        }
		
		apk.setUpdateTime(service.getUpdateTime());
		apk.setApkFlag(0);		
		apk.setDescription(service.getDescription());
		apk.setDetailURL(url);
		apk.setDeveloper(service.getDeveloper());
		//apk.setDownloadURL(service.getAPKDownLoadURL());
		apk.setIconURL(service.getIconURL());
		apk.setLanguage(service.getLanguage());
		apk.setPreviewURL(service.getPreViews());
		apk.setVersion(service.getAPKVersion());
		apk.setSystemVersion(service.getSystemVersion());
		apk.setCategory(categoryId);
		
		
		return apk;
	}
	
	
	
	/**
	 * app下载的入口方法
	 */
	private void downloadApp(){
		logger.info("Beginning to download app.");
				
		//查询所有待下载的数据
		List<APKInfo> list = getQihu360ApkService().getAllAPKOfUnDownload();
		
		if (list.isEmpty()){
			logger.info("There is no data to be downloaded in DB, so return;");			
			return;
		}
		
		//去重,每次取前10条
		List<APKInfo> apks = margin(list); 
		
		logger.info("it will be download are:" + apks);
		
		for (APKInfo apkinfo : apks){
			try{
				logger.info("Beginning to download {name:" + apkinfo.getName() + ", id: " + apkinfo.getId() +", apkFlag: "+ apkinfo.getApkFlag() +"}.");
				
				String tmpStr = null;
	            synchronized(lock){             
	                tmpStr = dateStr;
	            }
				
	            logger.info("apk will be download:" + apkinfo);
	            
				download(apkinfo, tmpStr);
				
				logger.info("The [" + apkinfo.getName() + "] was download end.");
			}catch(Exception e){
				logger.error("The [" + apkinfo.getName() + "] was download failed.", e);
			}
		}
		
	}
	
	/**
	 * 
	 * 将当前线程从数据库查询到的未下载的数据与缓存中的数据对比, 过滤重复, 然后在放入缓存 
	 * 然后返回前10条进行下载
	 * 
	 * 这所以这么做, 是考虑到前一个线程还没下载完, 数据库的状态没有修改, 然后后一个线程又在数据库查询, 此时数据就会重复的, 造成重复下载, 浪费时间,
	 * 因此在这里做了过滤, 
	 * 
	 * @param list
	 */
	private List<APKInfo> margin(List<APKInfo> list){
	    
	    //过滤下载失败超过六次的apk
	    filterAPKDownloadFiled(list);
	    
	    List<APKInfo> apks = marginToUNDownloadAPKCache(list);
	    
	    return apks;
	}
	
	private void filterAPKDownloadFiled(List<APKInfo> list){
	    
	    synchronized(downloadFiledCache){
            //遍历查询到的数据与缓存对比, 过滤重复
            for (Iterator<APKInfo> it = list.iterator(); it.hasNext(); ){
                APKInfo apkDB = it.next();
                
                for (Map.Entry<String, Integer> entry : downloadFiledCache.entrySet()){
                    //做对比, 如果id相同, 则删除
                    if (entry.getKey().equals(apkDB.getDetailURL()) && entry.getValue() > 10){
                        it.remove();
                    }
                }
            }
	    }
	    
	}
	
	private List<APKInfo> marginToUNDownloadAPKCache(List<APKInfo> list){
	    
        List<APKInfo> apks;
        
        synchronized(unDownloadAPKCache){
            
            //遍历查询到的数据与缓存对比, 过滤重复
            for (Iterator<APKInfo> it = list.iterator(); it.hasNext(); ){
                APKInfo apkDB = it.next();
                for (APKInfo apkCache : unDownloadAPKCache){
                    //做对比, 如果id相同, 则删除
                    if (apkCache.getId() == apkDB.getId()){
                        it.remove();
                    }
                }
            }
            
            //再将已经过滤的数据放入缓存,
            if (list.size() > 2000){
                apks = list.subList(0, 2000);
            }else{
                apks = list;
            }
            
            unDownloadAPKCache.addAll(apks);
        }
        
        return apks;
	}
	
	/**
	 * 下载app, 这个方法创建多个下载线程: apk一个下载线程, icon一个下载线程, preview多个下载线程(如果有多个预览图片,则每个图片都创建一个线程)
	 * 
	 * 多个线程同时下载该app所需的资源, 注意:在线程控制的时候用到了join()方法, 等待所有所有线程下载完成,然后更新数据库的状态
	 * 
	 * @param apkinfo
	 * @param apkBaseDir
	 * @param dateStr
	 */
	private void download(APKInfo apkinfo, String dateStr){
		
		Map<String, String> monitor = new HashMap<String, String>();
		List<Thread> threads = new ArrayList<Thread>(6);
		
		String apkIdStr = String.valueOf(apkinfo.getId());
		logger.info("apk[" + apkinfo.getName() + "], id[" + apkIdStr + "]");
		
		//读取配置,取出保存下载资源的位置
		String apkBaseDir = PropertyInfo.getValue("360.apk.download.base.location", null);
		
		//apk
		String apkFile =  apkBaseDir + File.separator + "apk" +  File.separator + dateStr + File.separator + apkIdStr + ".apk";
		
		//icon
        String iconFile = apkBaseDir + File.separator + "icon" + File.separator + dateStr + File.separator + apkIdStr +  getFileSuffix(apkinfo.getIconURL());
        
        logger.info("apk[" + apkinfo.getName() + "], apkFile[" + apkFile + "], iconFile[" + iconFile + "]");
		
        
        //创建apk的下载线程
		if (StringUtils.isEmpty(apkinfo.getApkLocation()) && !StringUtils.isEmpty(apkinfo.getDownloadURL())){
			
		    logger.info("beginning to download apk of [" + apkinfo.getName() + "]");
			
			Thread downloadAPKThread = new DownloadThread(apkIdStr +  "-apk", apkinfo.getDownloadURL(), apkFile, monitor, "apk");
			threads.add(downloadAPKThread);
		}
		
		//创建icon的下载线程
        if (StringUtils.isEmpty(apkinfo.getIconLocation()) && !StringUtils.isEmpty(apkinfo.getIconURL())){
            
            logger.info("beginning to download icon of [" + apkinfo.getName() + "]");
             
            Thread downloadIconThread = new DownloadThread(apkIdStr + "-icon", apkinfo.getIconURL(), iconFile, monitor, "icon");
            threads.add(downloadIconThread);
        }
		
		String previewDir = apkBaseDir + File.separator + "preview" + File.separator + dateStr + File.separator;
		
		//用来记录哪些preview被下载了
		List<String> needDownloadPreview = new ArrayList<String>();
		
		if (!StringUtils.isEmpty(apkinfo.getPreviewLocation()) && !StringUtils.isEmpty(apkinfo.getPreviewURL())){
		    
		    String location = apkinfo.getPreviewLocation().trim().replaceAll("^@*", "").replaceAll("@*$", "");
		    //己下载的preview
			String[] locations = location.split("@@@@@");
			
			
			String url = apkinfo.getPreviewURL().trim().replaceAll("^@*", "").replaceAll("@*$", "");
			//需要下载的preview
			String[] urls = url.split("@@@@@");
			
			//己下载的数量 与 需要下载的数量不一致, 则接着没下载完的继续下载
			if (locations.length != urls.length){
			    
			    logger.info("Do not download [" + apkinfo.getName() +"] preview end.");
			    
				for (int i = 0; i < urls.length; i++){
				    if (StringUtils.isEmpty(urls[i])){
				        continue;
				    }
				    
					//截取url后面的文件名
					String filename = urls[i].substring(urls[i].lastIndexOf("/") + 1, urls[i].length());
					
					//因为保存在数据库的previewLocation的格式为:  filename:previewLocation
					if (apkinfo.getPreviewLocation().contains(filename)){
						continue;
					}
					
					logger.info("preview URL0:" + urls[i]);
					//组装preview的文件名
					String previewFile = previewDir + apkIdStr + "_" + i + getFileSuffix(urls[i]);
					
					String flag = filename + "@@" + previewFile;
					logger.info("beginning to download preview [" + previewFile + "] of the [" + apkinfo.getName() +"]");
					
					//创建下载线程
					Thread downloadPreviewThread = new DownloadThread(apkIdStr + "-preview-" + i, urls[i], previewFile, monitor, flag);
				    
					needDownloadPreview.add(flag);
					threads.add(downloadPreviewThread);
				}
			}
		}
		//如果本地的PreviewLocation为空, 则说明没有被下载. 
		//这里要判断一下previewURL是否为空, 为空说明该URL没有被采集到, 这里就没必要再去下载了
		else if (StringUtils.isEmpty(apkinfo.getPreviewLocation()) && !StringUtils.isEmpty(apkinfo.getPreviewURL())){
			
		    String location = apkinfo.getPreviewURL().replaceAll("^@*", "").replaceAll("@*$", "");
			String[] previewURLs = location.split("@@@@@");
			
			
			//因为preview有多个, 所有创建多个线程同时下载
			for (int i = 0; i < previewURLs.length; i++){
			    
			    if (StringUtils.isEmpty(previewURLs[i])){
			        continue;
			    }
			    
				//截取url后面的文件名
				String filename = previewURLs[i].substring(previewURLs[i].lastIndexOf("/") + 1, previewURLs[i].length());
				
				logger.info("preview URL1:" + previewURLs[i]);
				
				//拼接preview本地全路径文件名
				String previewFile = previewDir + apkIdStr + "_" + i + getFileSuffix(previewURLs[i]);
				
				String flag = filename + "@@" + previewFile;
				
				logger.info("beginning to download preview [" + previewFile + "] of the [" + apkinfo.getName() +"]");
				//创建下载线程
				Thread downloadPreviewThread = new DownloadThread(apkIdStr + "-preview-" + i, previewURLs[i], previewFile, monitor, flag);
				
				needDownloadPreview.add(flag);
				threads.add(downloadPreviewThread);
			}
		}
				
		try {
			//启动下载线程
			for (Thread thread : threads){
				thread.start();
			}
			
			//等待下载完
			for (Thread thread : threads){
				thread.join();
			}
			
			logger.info("wait to download thread.");
			
            boolean isIconDownloadSuccess = false;
            synchronized(monitor){
              //如果从monitor取出icon标记不为空, 说明有线程下载icon
                if (null != monitor.get("icon")){
                    
                    if ("success".equals(monitor.get("icon"))){                     
                        apkinfo.setIconLocation(iconFile);
                        isIconDownloadSuccess = true;
                    }else if ("fail".equals(monitor.get("icon"))){
                        isIconDownloadSuccess = false;
                    }
                }
                else if(StringUtils.isEmpty(apkinfo.getIconURL()) || StringUtils.isEmpty(apkinfo.getIconLocation())){
                    isIconDownloadSuccess = false;
                }else {
                    isIconDownloadSuccess = true;
                }
            }
			
			boolean isAPKDownloadSuccess = false;
			synchronized (monitor) {
				//如果从monitor取出apk标记不为空, 说明有线程下载apk
				if (null != monitor.get("apk")) {
				    
					if ("success".equals(monitor.get("apk"))){						
						apkinfo.setApkLocation(apkFile);
						isAPKDownloadSuccess = true;
					}else if ("fail".equals(monitor.get("apk"))){
						isAPKDownloadSuccess = false;
					}
					
				}
				//如果从monitor取出apk标记为空, 有可能是数据库apkinfo的downloadURL属性为空,因为前面创建下载线程时有做判断,当downloadURL为空,就不会创建apk的下载线程,
				//此时monitor取出apk标记为空肯定为空.因此这种情况不能算是下载成功, 
				//不要惊慌, 当任务调度下一次启动爬虫时, 会再次解析该apd download url, 当解析成功后, 会更新数据库
				else if (StringUtils.isEmpty(apkinfo.getDownloadURL()) || StringUtils.isEmpty(apkinfo.getApkLocation())){
					isAPKDownloadSuccess = false;
				}else{
					isAPKDownloadSuccess = true;
				}
			}
			
			boolean isPreviewDownloadSuccess = true;
			//需要下载的preview不为空
			if (!needDownloadPreview.isEmpty()){
				
				StringBuilder builder = new StringBuilder();
				//判断现有数据库是否己存在preview,(可能是没有完全的下载完)
				if (!StringUtils.isEmpty(apkinfo.getPreviewLocation())){
					builder.append(apkinfo.getPreviewLocation()).append("@@@@@");
				}
				
				synchronized (monitor) {
				    for (String preview : needDownloadPreview){
						if (null != monitor.get(preview)) {
						    
							if ("success".equals(monitor.get(preview))){
								builder.append(preview);
								builder.append("@@@@@");	
							}else if ("fail".equals(monitor.get(preview))){
								isPreviewDownloadSuccess = false;
							}
						} 
					}
				}

				String previewLocation = builder.toString().replaceAll("@@@@@$", "");
				apkinfo.setPreviewLocation(previewLocation);
			}
			
			logger.info(" end of the [" + apkinfo.getName() + "] download.");
			
			//analysisApk(apkinfo);
			
            //只有apk, icon, preview 全部都下载成功, 并且解析packageName成功, 才算成功
            if (isIconDownloadSuccess && isAPKDownloadSuccess && isPreviewDownloadSuccess /**&& !StringUtils.isEmpty(apkinfo.getPackageName())**/){
                logger.info("The [" + apkinfo.getName() + "] was download successed.");
                
                apkinfo.setApkFlag(1);
                
                //只有在icon,apk,preview都下载完成后, 才将preview的标记去掉
                recoverPreviewLocation(apkinfo);
            }
			
			logger.info("id:"+ apkinfo.getId() +", packagename: " +apkinfo.getPackageName()+ ", iconlocation:" + apkinfo.getIconLocation() + ", previewLocation:" + apkinfo.getPreviewLocation() + ",apklocation:" + apkinfo.getApkLocation());
			
			
            if (apkinfo.getApkFlag() == 0 ){
                
                synchronized(downloadFiledCache){
                    
                    if (downloadFiledCache.get(apkinfo.getDetailURL()) == null){
                        downloadFiledCache.put(apkinfo.getDetailURL(), 1);
                    }else{
                        int count = downloadFiledCache.get(apkinfo.getDetailURL());
                        count++;
                        downloadFiledCache.put(apkinfo.getDetailURL(), count);
                    }
                }                
            }
            
			
			//过滤icon路径的问题
			recoverIconLocation(apkinfo);
			recoverAPKLocation(apkinfo);
			
			logger.info("id:"+ apkinfo.getId() +", packagename: " +apkinfo.getPackageName()+ ", iconlocation:" + apkinfo.getIconLocation() + ",previewLocation:" + apkinfo.getPreviewLocation() + ",apklocation:" + apkinfo.getApkLocation());
			
			//下载完成, 更新数据库
			updateAPKInfo(apkinfo);
			
			logger.info("update it to database.");
			
			//某个app全部下载完, 然后将该app的数据从cache中删除
			deleteDataInCache(apkinfo);
			
		} catch (Exception e) {
			logger.error("end of download. download failed", e);	
		}
		
		logger.info("The app [" + apkinfo.getName() + "] was download end.");
	}
	
	/**
	 * 某个app全部下载完, 然后将该app的数据从cache中删除
	 * @param apk
	 */
	private void deleteDataInCache(APKInfo apk){
		synchronized(unDownloadAPKCache){
			for (Iterator<APKInfo> it = unDownloadAPKCache.iterator(); it.hasNext();){
				if (it.next().getId() == apk.getId()){
					it.remove();
				}
			}
		}
	}
	
	private void updateAPKInfo(APKInfo apkinfo){
		getQihu360ApkService().updateLocationAndFlagOfAPKInfo(apkinfo);
	}
	
	/**
	 * 文件下载, 有容错的机制, 允许失败3次
	 * @param url
	 * @param filePath
	 */
	private boolean download(String url, String filePath){
		
		boolean isSuccess = false;
		
		for (int count = 0; count < 3 && !isSuccess; count++){
		}	isSuccess = downloadFile(url, filePath);
		
		return isSuccess;
	}
	
	/**
	 * 具体文的下载, 
	 * @param url  要下载的文件的URL
	 * @param filePath  保存在本地的路径
	 * @return
	 */
	private boolean downloadFile(String url, String filePath){
		
		boolean isSuccess = false;
		
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			
			URL urlObj = new URL(url);

			connection = (HttpURLConnection) urlObj
					.openConnection();

			connection.setConnectTimeout(3000);
			connection.setReadTimeout(3000);

			// 设置是否向connection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.connect();
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				inputStream = connection.getInputStream();
				File aFile = new File(filePath);
				File pFile = aFile.getParentFile();
				
				if (!pFile.exists()) {
					pFile.mkdirs();
				}
				
				byte[] b = new byte[1024 * 100];
				outputStream = new FileOutputStream(aFile);
				int i = 0;
				while ((i = inputStream.read(b)) != -1) {
					outputStream.write(b, 0, i);
				}
			} 
			
			isSuccess = true;
			
		}catch(Exception e){
			isSuccess = false;
			logger.error("the url:[" + url + "] was download failed.", e);			
		} finally{
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (null != outputStream) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (null != connection) {
				connection.disconnect();
			}		
		}
		
		
		return isSuccess;
	}
	
	/**
	 * job的入口
	 */
	public void run(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			//因为 dateStr 方法是成员变量, 每一次任务调度都要修改dateStr, 为了安全起见, 这里加了同步
			synchronized(lock){				
				dateStr = sdf.format(new Date());
			}
			
			collectionPage();
			
			//downloadApp();
			
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * 得出文件的后缀名
	 * @param url
	 * @return
	 */
	public static String getFileSuffix(String url){
	    
	    logger.info("get file suffix of url[" +url+ "]");
	    
		return url.substring(url.lastIndexOf("."), url.length());
	}
	
	/**
	 * 下载线程类
	 * @author Administrator
	 *
	 */
	private class DownloadThread extends Thread{
		private String url, localFile, flag;
		private Map<String, String> moniter;
		
		/**
		 * 
		 * @param name  线程名
		 * @param url   下载的url
		 * @param localFile  保存的文件全路径
		 * @param moniter 线程监视器
		 * @param flag 用来标记当前线程, apk下载线程为: apk , icon下载线程标记为: icon , preview下载线程标记为: preview-$num
		 */
		public DownloadThread(String name, String url, String localFile, Map<String, String> moniter, String flag){
			super(name);
			this.url = url;
			this.localFile = localFile;
			this.moniter = moniter;
			this.flag = flag;
		}
		
		public void run(){
			logger.info("The thread:" + this.getName() + " was start.");
			boolean isSuccess = download(url, localFile);
			synchronized (moniter) {
				//如果没有下载成功, 则修改状态
				if (!isSuccess){
					moniter.put(flag, "fail");
				}else{
					moniter.put(flag, "success");
				}
			}
			
			logger.info("Download End of the thread [" + getName()+ "], it result is [" + isSuccess +  "]." );
		}
	}
	
    private void analysisApk(APKInfo apk) {
        
        logger.info("analysis packagename info of the [" + apk.getName() + "]");
        
        String url = PropertyInfo.getValue("gbobal.analysis.apk.url", null);
        url += "&apkpath=" + apk.getApkLocation() + "&icopath=" + apk.getIconLocation();
        
        String json = getJsonByURL(url);
        
        
        logger.info("result of analy apk is:" + json);
        if (null != json && !"[{}]".equals(json)) {
            extractLocationsFromJsonString(json, apk);
        }
    }
	
	private String getJsonByURL(String url){
	    
	    String json = null;
	    
        HttpURLConnection conn = null;
        DataOutputStream out = null;
        BufferedOutputStream bos = null;
        InputStreamReader streamReader = null;
        BufferedReader reader = null;
        
        try {            
            conn = (HttpURLConnection)new URL(url).openConnection();
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("content-type", "text/html");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(50000);
            conn.connect();
            
            out = new DataOutputStream(conn.getOutputStream());
            bos = new BufferedOutputStream(out);
            bos.flush();

            // 读取响应
            streamReader = new InputStreamReader(conn.getInputStream());
            reader = new BufferedReader(streamReader);
            String lines;
            
            StringBuffer sb = new StringBuffer();
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            
            json = sb.toString();
            
        } catch (Exception e) {
            logger.error("", e);
        }finally{
            try {
                if (null != out)
                    out.close();

                if (null != bos)
                    bos.close();

                if (null != streamReader)
                    streamReader.close();

                if (null != reader)
                    reader.close();

                if (null != conn)
                    conn.disconnect();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        
        return json;
	}
		
	private void extractLocationsFromJsonString(String json, APKInfo apk) {		
		
		JSONObject jo = JSONArray.fromObject(json).getJSONObject(0);
		
        apk.setPackageName(jo.getString("packagename"));
        apk.setAuthor(jo.getString("author"));
        apk.setScreen(jo.getString("screen"));
        apk.setVersion(jo.getString("version1"));
		
		if (null == jo.getString("version_code1") || "".equals(jo.getString("version_code1").trim())){
		    apk.setVersionCode(0);
		}
		else{		    
		    apk.setVersionCode(Integer.valueOf(jo.getString("version_code1")));
		}
		
		apk.setPermission(jo.getString("permission").replaceAll("\\[", "")
				.replaceAll("\\]", "").replace("\"", "").replace(",", "|"));
		
		if (StringUtils.isEmpty(apk.getIconLocation())){		    
		    apk.setIconLocation(jo.getString("iconurl"));
		}
		
		jo = null;
	}
		
	private String getAPKDownLoadURLBySID(String sid){
	    
	    String json = null, downloadURL = null;
	    
	    for (int i = 0; i < 3 && json == null; i++){
	        json = getJsonByURL(getAPKDownloadBySID + sid);
	    }
	    
	    if (null == json){
	        return null;
	    }
	    
	    String[] strArr = json.split("[|]");
	    
	    if (strArr.length == 2){
	        downloadURL = strArr[1];
	    }
	    
	    return downloadURL;
	}
	
	private void recoverPreviewLocation(APKInfo apkinfo){
	    String previewLocation = apkinfo.getPreviewLocation();
	    
	    if (StringUtils.isEmpty(previewLocation)){
	        return;
	    }
	    
	    StringBuilder builder = new StringBuilder();
	    
	    String[] locations = previewLocation.split("@@@@@");
	    for (String location: locations){
	        if (StringUtils.isEmpty(location)){
	            continue;
	        }
	        
	        String tmpString =  location.replaceAll("^.+@{2}", "");
	        
            if (tmpString.startsWith("/data/att/m.goapk.com")){
                tmpString = tmpString.replace("/data/att/m.goapk.com", "");
            }
            
            builder.append(tmpString).append(",");
	    }
	    
	    String location = builder.toString().replaceAll(",$", "");
	    apkinfo.setPreviewLocation(location);
	}
	
	private void recoverIconLocation(APKInfo apkinfo){
	    String iconLocation = apkinfo.getIconLocation();
	    
	    if (StringUtils.isEmpty(iconLocation)){
	        return;
	    }
	    
	    if (iconLocation.startsWith("/data/att/m.goapk.com")){
	        
	        String reg = "^/.+360/icon.+?\\.(png|gif|jpeg|bmp|jpg|icon|jpe)";
	        
	        Pattern pattern = Pattern.compile(reg);
	        Matcher matcher = pattern.matcher(iconLocation);
	        
	        if (matcher.find()){
	            iconLocation = matcher.group();
	        }

	        iconLocation = iconLocation.replace("/data/att/m.goapk.com", "");
	    }
	            
        apkinfo.setIconLocation(iconLocation);
	}
	
	private void recoverAPKLocation(APKInfo apkinfo){
	    String apkLocation = apkinfo.getApkLocation();
	    
        if (StringUtils.isEmpty(apkLocation)){
            return;
        }
        
        if (apkLocation.startsWith("/data/att/m.goapk.com")){
            apkLocation = apkLocation.replace("/data/att/m.goapk.com", "");
        }
        
        apkinfo.setApkLocation(apkLocation);
	}
}
