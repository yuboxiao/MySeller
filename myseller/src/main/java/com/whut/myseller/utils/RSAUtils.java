package com.whut.myseller.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Decoder.BASE64Decoder;

/**
 * Created by root on 16-3-21.
 */
public class RSAUtils {
    private static final String DEFAULT_PUBLIC_KEY=
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJE0R8nFKv4pqMIQz28EzeUQCn" + "\r" +
                    "taNWn7GzxoiQkgpKkFQaM7W+kLvfgDjT57R5ovFungPwfS8gt9yLEwrGnDminOFj" + "\r" +
                    "QyubstQTMIemaQLaiId4LhSt1Yyb6ilDwnY7Zdl34uWbrGi7EoWE8mLvxdNvYNAZ" + "\r" +
                    "cBhYwZ4niZJ8j7FiZQIDAQAB" + "\r";

    private static final String DEFAULT_PRIVATE_KEY=
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMkTRHycUq/imowh" + "\r" +
                    "DPbwTN5RAKe1o1afsbPGiJCSCkqQVBoztb6Qu9+AONPntHmi8W6eA/B9LyC33IsT" + "\r" +
                    "CsacOaKc4WNDK5uy1BMwh6ZpAtqIh3guFK3VjJvqKUPCdjtl2Xfi5ZusaLsShYTy" + "\r" +
                    "Yu/F029g0BlwGFjBnieJknyPsWJlAgMBAAECgYAEe4Z6VK3PN/kECLSWSLMd/aZL" + "\r" +
                    "FtSGWNcIT/O5bVgHY4JMdzyk++sER4J8ztyKHaTON32U/eVwEEk9XHZQCSmLuV/x" + "\r" +
                    "HlPhLWa/mm3zkCjGCWI0lkHpDtLDXzpLPMccEyXqQT6hNJoPVURubQGZZw4aDG3Y" + "\r" +
                    "p/AL23ZfDISdJsOTAQJBAOOknMAmhZulogziX+ezAH7zEUjmxyG0iuEY08SOy3J2" + "\r" +
                    "JDPA9UsXeX6ajwIhIRPoZDqMpTKRMLLkkcvwFIHyY8ECQQDiH21GvmIWflezZ/Ln" + "\r" +
                    "lTSZkfQ3+O3CEYFaxsM1vTwKKy9il1d9TnbJwJxSuknv28fyPagk364DeHzg6ceo" + "\r" +
                    "sNelAkEA4bnDuQE5sNnvsyjxglXL4xXxCMHVqUVWBNhSfHA/qlMdmLf4QyqWzYRC" + "\r" +
                    "1BC3tP7WC/yUfmzbxjfBnmCEZUpNwQJAMmUqp4+asMSrqxJJso8wT+GVejwCUTQx" + "\r" +
                    "jheqPfnUvbduMhzkpMqt8rz4mf9mpwxmfXh8Vut7Ds71lfLESrZO+QJBANW5qJJB" + "\r" +
                    "h8rq2v9T0Fa4/9ilV3HfSVtN9BhtDLxJgKHHhhALazYaMv86Py22nvvztkLWEG2g" + "\r" +
                    "gUOjoHCPgKvroy8=" + "\r";

    /**
     * 私钥
     */
    private RSAPrivateKey privateKey;

    /**
     * 公钥
     */
    private RSAPublicKey publicKey;

    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * 获取私钥
     * @return 当前的私钥对象
     */
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * 获取公钥
     * @return 当前的公钥对象
     */
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * 随机生成密钥对
     */
    public void genKeyPair(){
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * 从文件中输入流中加载公钥
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public void loadPublicKey(InputStream in) throws Exception{
        try {
            BufferedReader br= new BufferedReader(new InputStreamReader(in));
            String readLine= null;
            StringBuilder sb= new StringBuilder();
            while((readLine= br.readLine())!=null){
                if(readLine.charAt(0)=='-'){
                    continue;
                }else{
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            loadPublicKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }


    /**
     * 从字符串中加载公钥
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public void loadPublicKey(String publicKeyStr) throws Exception{
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            this.publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从文件中加载私钥
     * @return 是否成功
     * @throws Exception
     */
    public void loadPrivateKey(InputStream in) throws Exception{
        try {
            BufferedReader br= new BufferedReader(new InputStreamReader(in));
            String readLine= null;
            StringBuilder sb= new StringBuilder();
            while((readLine= br.readLine())!=null){
                if(readLine.charAt(0)=='-'){
                    continue;
                }else{
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            loadPrivateKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    public void loadPrivateKey(String privateKeyStr) throws Exception{
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (IOException e) {
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 加密过程
     * @param publicKey 公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{
        if(publicKey== null){
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher= null;
        try {
            cipher= Cipher.getInstance("RSA/ECB/PKCS1PADDING", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output= cipher.doFinal(plainTextData);
//            return Base64.encode(output);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 解密过程
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception{
        if (privateKey== null){
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher= null;
        try {
            cipher= Cipher.getInstance("RSA/ECB/PKCS1PADDING", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output= cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 字节数据转十六进制字符串
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String byteArrayToString(byte[] data){
        StringBuilder stringBuilder= new StringBuilder();
        for (int i=0; i<data.length; i++){
            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0)>>> 4]);
            //取出字节的低四位 作为索引得到相应的十六进制标识符
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            if (i<data.length-1){
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }


//    public static void main(String[] args){
//        RSAUtils RSAUtils= new RSAUtils();
//        //RSAUtils.genKeyPair();
//
//        //加载公钥
//        try {
//            RSAUtils.loadPublicKey(RSAUtils.DEFAULT_PUBLIC_KEY);
//            System.out.println("加载公钥成功");
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//            System.err.println("加载公钥失败");
//        }
//
//        //加载私钥
//        try {
//            RSAUtils.loadPrivateKey(RSAUtils.DEFAULT_PRIVATE_KEY);
//            System.out.println("加载私钥成功");
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//            System.err.println("加载私钥失败");
//        }
//
//        //测试字符串
//        String encryptStr= "111|222|333";
//
//        try {
//            //加密
//            byte[] cipher = RSAUtils.encrypt(RSAUtils.getPublicKey(), encryptStr.getBytes());
//            //String str = "TESTING.";
//            //cipher = str.getBytes();
//            Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
//            Method mainMethod = clazz.getMethod("encode", byte[].class);
//            mainMethod.setAccessible(true);
//            Object retObj = mainMethod.invoke(null, new Object[]{cipher});
//            System.out.println((String)retObj);
//
//            //解密
//            byte[] plainText = RSAUtils.decrypt(RSAUtils.getPrivateKey(), cipher);
//            System.out.println("密文长度:"+ cipher.length);
//            System.out.println(RSAUtils.byteArrayToString(cipher));
//            System.out.println("明文长度:"+ plainText.length);
//            System.out.println(RSAUtils.byteArrayToString(plainText));
//            System.out.println(new String(plainText));
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//    }
}
