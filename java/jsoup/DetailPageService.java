package com.aora.bookmark.downloadapk.apk360.service.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.aora.bookmark.downloadapk.apk360.domain.APKInfo;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

class MyHtmlUnitDriver extends HtmlUnitDriver {
	
	public MyHtmlUnitDriver(BrowserVersion version){
		super(version);
	}
	
	@Override
	protected WebClient modifyWebClient(WebClient client) {
		WebClient modifiedClient = super.modifyWebClient(client);

		modifiedClient.getOptions().setThrowExceptionOnScriptError(false);
		return modifiedClient;
	}
	
}

public class DetailPageService {
	
	private Document doc;
	private APKInfo apkInfo;
	
	public DetailPageService(){
		apkInfo = new APKInfo();
	}
	
	public DetailPageService(Document doc){
		this.doc = doc;
		apkInfo = new APKInfo();
	}
	
	public void setAPKDetailURL(String url){
		apkInfo.setDetailURL(url);
	}
	
	public APKInfo getAPKInfo(){
		return apkInfo;
	}
	
	public String getAPKDownLoadURL(){
		Element element = doc.select("div.main div.ndtlcon.clearfix div.ndlft div.nsinf dl").first();
		
		Element link = element.getElementsByTag("a").first();
		String href = link.attr("href").split("url=")[1];
		
		apkInfo.setDownloadURL(href);
		
		return href;
	}
	
	public String getIconURL(){
		String iconURL = doc.select("div#app-info-panel div.product.btn_type1 dl.clearfix dt img").first().attr("src");
		
		apkInfo.setIconURL(iconURL);
		return iconURL;
	}
	
	public String getName(){
		String name = doc.select("h2#app-name").first().getElementsByTag("span").get(0).attr("title");
		
		apkInfo.setName(name);
		return name;
	}
	
	public String getAPKSize(){
        String sizeStr = doc.select("div#app-info-panel div.product.btn_type1 dl.clearfix dd div.pf").first().getElementsByTag("span").get(4).text();
		String size = sizeStr.replaceAll("[a-zA-Z]+$", "");
		//从页面抓取到的文件大小都是MB, 入库的时候转换为字节
		Float mb = Float.parseFloat(size);
		Float b = mb * 1024 * 1024;
		
		apkInfo.setApkSize(b.intValue() + "");	
		return b.intValue() + "";
	}
	
	public String getDescription(){
		String desc = doc.select("div.main-left.fl div.infors div.mod-info div.infors-txt").text().trim();		
		apkInfo.setDescription(desc);
		return desc;
	}
	
	public String getDeveloper(){
		Element tr = doc.select("div.infors-txt div.base-info table tbody tr td").get(0);
		String dev = tr.text().split("：")[1].trim();
		
		apkInfo.setDeveloper(dev);
		return dev;
	}
	
	public String getUpdateTime(){
		Element element = doc.select("div.infors-txt div.base-info table tbody tr td").get(1);
		String lastUpdateTime = element.text().split("：")[1].trim();
		apkInfo.setUpdateTime(lastUpdateTime);
		return lastUpdateTime;
	}
	
	public String getAPKVersion(){
		String apkVersion = doc.select("div.infors-txt div.base-info table tbody tr").get(1).getElementsByTag("td").get(0).text();
		apkVersion = apkVersion.split("：")[1].trim();
		
		apkInfo.setVersion(apkVersion);
		return apkVersion;
	}
	
	public String getLanguage(){
		String language = doc.select("div.infors-txt div.base-info table tbody tr").get(2).getElementsByTag("td").get(0).text();
        language = language.split("：")[1].trim();
		apkInfo.setLanguage(language);
		return language;
	}
	
	
	public String getSystemVersion(){
		String sysVersion = doc.select("div.infors-txt div.base-info table tbody tr").get(1).getElementsByTag("td").get(1).text();
		sysVersion = sysVersion.split("：")[1].trim();
		
		apkInfo.setSystemVersion(sysVersion);
		return sysVersion;
	}
	
	public String getPreViews(){
		StringBuilder build = new StringBuilder();
		Elements ele = doc.select("div#scrollbar");
		
		//如果ele为空, 说明该app没有预览
		if (ele.isEmpty()){
		    return null;
		}
		
		String multURLs = doc.select("div#scrollbar").first().attr("data-snaps");
		
		for (String url : multURLs.split(",") ){
			build.append(url).append("@@@@@");
		}
		
		String url = build.toString().replaceAll("@@@@@$", "");
		
		apkInfo.setPreviewURL(url);
		
		return url;
	}
	
}
