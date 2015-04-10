package com.aora.wifi.util;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.aora.wifi.servlet.SysParameter;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesTools {

	
	
	public DesTools(){
		//System.out.println("init DesTools...");
	}
	

	
    private  BASE64Encoder base64 = new BASE64Encoder();
    private final byte[] myIV = { 0x32, 0x30, 0x31, 0x30, 0x30, 0x35, 0x31, 0x38 };
    //private static String strkey = "W9qPIzjaVGKUp7CKRk/qpCkg/SCMkQRu"; // 字节数必须是8的倍数
    //private  String strkey = "01234567890123456789012345678912";
    //private  String strkey = "";
    //private static String KEYDESC="$X53P;H8KGO:U,F>A>)2VBcX$U>%K)9dDQN7-b[)^K7E_X`P<5-7:2XdX_STI0@O>_-d@aIS`PZ(bH'@A/RHLI*K'JV/9:U1XGI[1;,<5P@EM)7Jcb(@'G,4bJd8CSMG";    
    public  synchronized String desEncrypt(String input) throws Exception 
    { 
        
        BASE64Decoder base64d = new BASE64Decoder();
        DESedeKeySpec p8ksp = null;
        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(SysParameter.getInstatnce().getStrkey()));
        Key key = null;
        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);
        
        input = padding(input);
        byte[] plainBytes = (byte[])null;
        Cipher cipher = null;
        byte[] cipherText = (byte[])null;
        
        plainBytes = input.getBytes("UTF8");
        cipher = Cipher.getInstance("DESede/CBC/NoPadding");
        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
        IvParameterSpec ivspec = new IvParameterSpec(myIV);
        cipher.init(1, myKey, ivspec);
       
        cipherText = cipher.doFinal(plainBytes);
        return removeBR(base64.encode(cipherText));
        
    } 
     
    public synchronized  String desDecrypt(String cipherText) throws Exception 
    { 
        
        BASE64Decoder base64d = new BASE64Decoder();
        DESedeKeySpec p8ksp = null;
        p8ksp = new DESedeKeySpec(base64d.decodeBuffer(SysParameter.getInstatnce().getStrkey()));
        Key key = null;
        key = SecretKeyFactory.getInstance("DESede").generateSecret(p8ksp);
        
        Cipher cipher = null;
        byte[] inPut = base64d.decodeBuffer(cipherText);
        cipher = Cipher.getInstance("DESede/CBC/NoPadding");
        SecretKeySpec myKey = new SecretKeySpec(key.getEncoded(), "DESede");
        IvParameterSpec ivspec = new IvParameterSpec(myIV);
        cipher.init(2, myKey, ivspec);
        byte[] output = removePadding(cipher.doFinal(inPut));

        return new String(output, "UTF8");
        
    } 
     	
	
    private  String removeBR(String str) {
        StringBuffer sf = new StringBuffer(str);

        for (int i = 0; i < sf.length(); ++i)
        {
          if (sf.charAt(i) == '\n')
          {
            sf = sf.deleteCharAt(i);
          }
        }
        for (int i = 0; i < sf.length(); ++i)
          if (sf.charAt(i) == '\r')
          {
            sf = sf.deleteCharAt(i);
          }

        return sf.toString();
      }

      private  String padding(String str)
      {
        byte[] oldByteArray;
        try
        {
            oldByteArray = str.getBytes("UTF8");
            int numberToPad = 8 - oldByteArray.length % 8;
            byte[] newByteArray = new byte[oldByteArray.length + numberToPad];
            System.arraycopy(oldByteArray, 0, newByteArray, 0, oldByteArray.length);
            for (int i = oldByteArray.length; i < newByteArray.length; ++i)
            {
                newByteArray[i] = 0;
            }
            return new String(newByteArray, "UTF8");
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println("Crypter.padding UnsupportedEncodingException"); 
        }
        return null;
      }

      private  byte[] removePadding(byte[] oldByteArray)
      {
        int numberPaded = 0;
        for (int i = oldByteArray.length; i >= 0; --i)
        {
          if (oldByteArray[(i - 1)] != 0)
          {
            numberPaded = oldByteArray.length - i;
            break;
          }
        }

        byte[] newByteArray = new byte[oldByteArray.length - numberPaded];
        System.arraycopy(oldByteArray, 0, newByteArray, 0, newByteArray.length);

        return newByteArray;
      }



      
      	
}
