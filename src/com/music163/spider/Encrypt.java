package com.music163.spider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Encoder;

public class Encrypt {
	static String crawlAjaxUrl(String Id, String BASE_URL,String POST_URL) {
		//String BASE_URL = "http://music.163.com/user/home?id=109284159";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String first_param = "{uid:\"uid_param\", offset:\"0\", total:\"true\", limit:\"20\", csrf_token:\"\"}";
		first_param = first_param.replace("uid_param", Id + "");
		String strResult;
		try {
			// 参数加密
			// 16位随机字符串，直接FFF
			// String secKey = new BigInteger(100, new SecureRandom()).toString(32).substring(0, 16);
			String secKey = "FFFFFFFFFFFFFFFF";
			// 两遍ASE加密
			String encText = aesEncrypt(aesEncrypt(first_param, "0CoJUm6Qyw8W8jud"), secKey);
			//
			String encSecKey = rsaEncrypt();
            //http://music.163.com/weapi/user/playlist?csrf_token=
			HttpPost httpPost = new HttpPost(POST_URL);
			httpPost.addHeader("Referer", BASE_URL);

			List<NameValuePair> ls = new ArrayList<NameValuePair>();
			ls.add(new BasicNameValuePair("params", encText));
			ls.add(new BasicNameValuePair("encSecKey", encSecKey));

			UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(ls, "utf-8");
			httpPost.setEntity(paramEntity);

			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				strResult = EntityUtils.toString(entity, "utf-8");
				return strResult;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "";
	}

	/**
	 * ASE-128-CBC加密模式可以需要16位
	 *
	 * @param src 加密内容
	 * @param key 密钥
	 * @return
	 */
	private static String aesEncrypt(String src, String key) throws Exception {
		String encodingFormat = "UTF-8";
		String iv = "0102030405060708";

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = key.getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		// 使用CBC模式，需要一个向量vi，增加加密算法强度
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(src.getBytes(encodingFormat));
		return new BASE64Encoder().encode(encrypted);

	}

	private static String rsaEncrypt() {
		String secKey = "257348aecb5e556c066de214e531faadd1c55d814f9be95fd06d6bff9f4c7a41f831f6394d5a3fd2e3881736d94a02ca919d952872e7d0a50ebfa1769a7a62d512f5f1ca21aec60bc3819a9c3ffca5eca9a0dba6d6f7249b06f5965ecfff3695b54e1c28f3f624750ed39e7de08fc8493242e26dbc4484a01c76f739e135637c";
		return secKey;
	}
}
