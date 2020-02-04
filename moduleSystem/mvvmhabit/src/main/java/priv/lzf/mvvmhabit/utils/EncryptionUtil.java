package priv.lzf.mvvmhabit.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5. 加密
 */
public class EncryptionUtil {

    /**
     * MD5加密
     * @param data 要转换的值
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String MD5(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for(int i=0;i<bits.length;i++){
            int a = bits[i];
            if(a<0) a+=256;
            if(a<16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    /**
     * SHA1加密
     * @param data 要转换的值
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String SHA1(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for(int i=0;i<bits.length;i++){
            int a = bits[i];
            if(a<0) a+=256;
            if(a<16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }


    /**
     * 签名
     * @param appSecret
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String signatureString(String appSecret, String timestamp, String nonce) {
        String[] ArrTmp = { appSecret, timestamp, nonce };
        Arrays.sort(ArrTmp);
        String tmpStr = ArrTmp[0]+ArrTmp[1]+ArrTmp[2];
        tmpStr = SHA1(tmpStr);
        return tmpStr.toLowerCase();
    }

    /**
     * UUID随机数
     * @return
     */
    public static String getNonce(){
        return UUID.randomUUID().toString();
    }

    /**
     * 获取10位的时间戳
     * @return
     */
    public static String getTimestamp(){
        String timestamp=String.valueOf(System.currentTimeMillis());
        return timestamp.substring(0,10);
    }
}
