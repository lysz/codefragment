package com.aora.wifi.action;
import java.awt.*;
import java.awt.Image.*;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.*;
import javax.imageio.*;
import javax.servlet.http.HttpSession;



public class SystemRandomPicAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	public void getRandomPic(){
		
		HttpSession session = request.getSession();   

		try{
		 	String flag = request.getParameter("flag")!=null?request.getParameter("flag"):"";
		    int iWidth = 50, iHeight = 20;
		    BufferedImage image = new BufferedImage(iWidth, iHeight,BufferedImage.TYPE_INT_RGB); 
		    Graphics g = image.getGraphics();
		    g.setColor(Color.white);
		    g.fillRect(0, 0, iWidth, iHeight); 
		    g.setColor(Color.black);
		    g.drawRect(0, 0, iWidth - 1, iHeight - 1); 
		    Random random = new Random();
		    String temp="" ;    
		    int k = 0;    
		    for(int i=0;i<4;)
		    {
		   	   k=random.nextInt(10);
		       if(k <=9 && k>=0){
		       temp =temp+random.nextInt(10);
		       i++;
		       }       
		    }    
/*		    if(flag.equals("ty"))
		    {
			    session.setAttribute("ty",temp);  
			    g.setColor(Color.black);    
			    g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			    g.drawString(temp, 10, 15);     
			    for (int iIndex = 0; iIndex < 88; iIndex++) 
			    {
			        int x = random.nextInt(iWidth);
			        int y = random.nextInt(iHeight);
			        g.drawLine(x, y, x, y);        
			    }
			    g.dispose(); 
			    ImageIO.write(image, "JPEG", response.getOutputStream());    
			  }
			if(flag.equals("yd"))
		    {
			    session.setAttribute("yd",temp);  
			    g.setColor(Color.black);    
			    g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			    g.drawString(temp, 10, 15);     
			    for (int iIndex = 0; iIndex < 88; iIndex++) 
			    {
			        int x = random.nextInt(iWidth);
			        int y = random.nextInt(iHeight);
			        g.drawLine(x, y, x, y);        
			    }
			    g.dispose(); 
			    ImageIO.write(image, "JPEG", response.getOutputStream());    
			  }
*/			  
			  	session.setAttribute("validateCode",temp);  
			    g.setColor(Color.black);    
			    g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			    g.drawString(temp, 10, 15);     
			    for (int iIndex = 0; iIndex < 88; iIndex++) 
			    {
			        int x = random.nextInt(iWidth);
			        int y = random.nextInt(iHeight);
			        g.drawLine(x, y, x, y);        
			    }
			    g.dispose(); 
				
			    response.setHeader("Cache-Control","no-store");
				response.setHeader("Pragrma","no-cache");
				response.setDateHeader("Expires",0);
			    response.setContentType("image/jpeg");
			    ImageIO.write(image, "JPEG", response.getOutputStream()); 
				response.getOutputStream().flush();
				response.getOutputStream().close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ;
	}
	

}
