package com.ieds.gis.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.ieds.gis.base.BaseApp;
import com.ieds.gis.base.R;
import com.ieds.gis.base.bo.FileBo;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.exception.NullArgumentException;
import com.lidroid.xutils.util.FileUtil;

/**
 * 
 * @author lihx
 * 
 */
public class GisHttpUtils {
	private static final String UTF_8 = "UTF-8";
	public static final String DATA_SERVICE_ERROR = "任务服务器访问失败！";
	private static final String FILE_SERVICE_ERROR = "文件服务器访问失败！";

	private static final String DATA_SERVICE_EXC = "任务服务器正忙，请稍后再试！";
	private static final String FILE_SERVICE_EXC = "文件服务器正忙，请稍后再试！";

	private static final String DATA_EXC = "无法连接任务服务器，请检查网络！";
	private static final String FILE_EXC = "无法连接到文件服务器，请检查网络！";
	private static final int TIMEOUT_C = 30 * 1000;

	private static final int TIMEOUT_R() {
		int rn = SettingUtil.getInstance().getRequest_number();
		return rn * 60 * 1000;
	}

	/**
	 * 
	 * Desc: 文件上传处理
	 * 
	 * @param serverURL
	 * @param params
	 * @param files
	 * @return subDir服务端存放上传文件的根目录
	 * @throws IOException
	 * @version 1.0
	 * @author dongxiaoping@ieds.com.cn
	 * @update 2013-6-6 上午10:32:43
	 */
	public static List<FileBo> postFile(String serverURL,
			Map<String, File> files, String subDir) throws HttpException {
		if (!isNetWorkAvailable()) {
			throw new HttpException(BaseApp.getmContext().getString(
					R.string.unable_to_get_map_info));
		}
		HttpURLConnection conn = null;
		DataOutputStream outStream = null;
		BufferedReader br = null;
		StringBuffer sb2 = null;
		try {
			String BOUNDARY = java.util.UUID.randomUUID().toString();
			String PREFIX = "--", LINEND = "\r\n";
			String MULTIPART_FROM_DATA = "multipart/form-data";
			String CHARSET = UTF_8;
			URL uri = new URL(serverURL);
			conn = (HttpURLConnection) uri.openConnection();
			/*** 网慢会造成上传失败 ****/
			if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
				conn.setRequestProperty("Connection", "close");
			}
			/*** end 网慢会造成上传失败 ****/
			conn.setConnectTimeout(TIMEOUT_C);
			conn.setReadTimeout(TIMEOUT_R()); // 缓存的最长时间
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Charset", UTF_8);
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
					+ ";boundary=" + BOUNDARY);

			outStream = new DataOutputStream(conn.getOutputStream());
			outStream.writeBytes(PREFIX + BOUNDARY + LINEND);
			outStream
					.writeBytes("Content-Disposition: form-data; name=\"subdir\""
							+ LINEND);
			outStream.writeBytes(LINEND);
			outStream.writeBytes(subDir);
			outStream.writeBytes(LINEND);

			if (files != null) {
				for (Map.Entry<String, File> file : files.entrySet()) {
					StringBuilder sb1 = new StringBuilder();
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINEND);
					sb1.append("Content-Disposition: form-data; name=\"upload\"; filename=\""
							+ file.getKey() + "\"" + LINEND);
					sb1.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + LINEND);
					sb1.append(LINEND);
					outStream.write(sb1.toString().getBytes());

					InputStream is = new FileInputStream(file.getValue());
					byte[] buffer = new byte[FileUtil.UPDATE_SIZE];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						outStream.write(buffer, 0, len);
					}

					is.close();
					outStream.write(LINEND.getBytes());
				}
			}
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();
			int statusCode = conn.getResponseCode();
			if (statusCode == 0) {
				throw new HttpException(DATA_SERVICE_EXC);
			} else if (statusCode != HttpStatus.SC_OK) {
				throw new HttpException(statusCode, FILE_SERVICE_ERROR);
			}
			sb2 = new StringBuffer();
			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				sb2.append(tmp);
			}
			List<FileBo> mList = new ArrayList<FileBo>();
			JSONArray array;
			array = new JSONArray(sb2.toString());

			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);

				if (object == null) {
					continue;
				}
				FileBo bo = JsonUtil.getJson(FileBo.class, object.toString());
				mList.add(bo);
			}

			return mList;
		} catch (NullArgumentException e) {
			throw new HttpException(e.getMessage());
		} catch (JSONException e) {
			throw new HttpException("组装JSON数据异常\n" + sb2.toString(), e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(FILE_EXC + "\n", e);
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
				}
				if (br != null) {
					br.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 字符串压缩
	 * 
	 * @param str
	 *            ：压缩目标字符串
	 * @return
	 * @throws IOException
	 */
	public static String compress(String str) {
		if (null == str || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String result = null;
		try {
			GZIPOutputStream gzip;
			gzip = new GZIPOutputStream(out);
			OutputStreamWriter isr = new OutputStreamWriter(gzip, "UTF-8");
			BufferedWriter in = new BufferedWriter(isr);
			in.write(str);
			in.close();
			result = out.toString("ISO-8859-1");
		} catch (IOException e) {
			e.printStackTrace();
			result = "字符串压缩异常";
		}
		return result;
	}

	/**
	 * 解压被gizp压缩过的字符串
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static String unCompress(String str) {
		if (null == str || str.length() == 0) {
			return str;
		}
		String result = "";
		ByteArrayInputStream inWrite;
		try {
			inWrite = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
			GZIPInputStream gzip = new GZIPInputStream(inWrite);
			InputStreamReader isr = new InputStreamReader(gzip, "UTF-8");
			BufferedReader in = new BufferedReader(isr);
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 带gzip的上传数据
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws HttpException
	 */
	public static String postHttpData(String url, String json)
			throws HttpException {
		if (!isNetWorkAvailable()) {
			throw new HttpException(BaseApp.getmContext().getString(
					R.string.unable_to_get_map_info));
		}
		DefaultHttpClient httpclient = null;
		HttpPost post = null;
		HttpResponse response = null;
		BufferedReader in = null;
		String result = "";
		try {
			httpclient = getHttpClient();
			post = getHttpPost(url);
			ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("json", compress(json)));
			post.setEntity(new UrlEncodedFormEntity(nvp, HTTP.UTF_8));
			response = httpclient.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 0) {
				throw new HttpException(DATA_SERVICE_EXC);
			} else if (statusCode != HttpStatus.SC_OK) {
				throw new HttpException(statusCode, FILE_SERVICE_ERROR);
			}
			InputStream is = response.getEntity().getContent();

			Header contentEncoding = response
					.getFirstHeader("Content-Encoding");
			if (contentEncoding != null
					&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
				is = new GZIPInputStream(new BufferedInputStream(is));
			}
			InputStreamReader isr = new InputStreamReader(is, UTF_8);
			in = new BufferedReader(isr);

			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println(result.length());
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(DATA_EXC + "\n", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			httpclient.getConnectionManager().shutdown();
		}

	}

	private static HttpPost getHttpPost(String url) {
		HttpPost httpPost = new HttpPost(url);
		// 设置 请求超时时间
		httpPost.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,
				TIMEOUT_R());
		httpPost.setHeader("Connection", "Keep-Alive");
		httpPost.addHeader("Accept-Encoding", "gzip");
		// httpPost.setHeader("Content-Encoding", "gzip");

		return httpPost;
	}

	private static DefaultHttpClient getHttpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		// 设置 连接超时时间
		httpClient.getParams().setParameter(
				HttpConnectionParams.CONNECTION_TIMEOUT, TIMEOUT_C);
		// 设置 读数据超时时间
		httpClient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,
				TIMEOUT_R());
		// 设置 字符集
		httpClient.getParams().setParameter("http.protocol.content-charset",
				UTF_8);
		return httpClient;
	}

	/**
	 * 按照类型判断带或者不带gzip下载数据
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws HttpException
	 */
	public static String getHttpData(String url, Map<String, Object> params)
			throws HttpException {
		if (!isNetWorkAvailable()) {
			throw new HttpException(BaseApp.getmContext().getString(
					R.string.unable_to_get_map_info));
		}
		DefaultHttpClient httpclient = null;
		HttpPost post = null;
		HttpResponse response = null;
		StringBuilder sb = null;
		StringEntity stringEntity = null;
		BufferedReader in = null;
		String result = "";
		try {
			httpclient = getHttpClient();
			post = getHttpPost(url);
			sb = new StringBuilder();
			if (params != null && !params.isEmpty()) {
				for (Entry<String, Object> entry : params.entrySet()) {
					sb.append(entry.getKey())
							.append("=")
							.append(URLEncoder.encode(entry.getValue()
									.toString(), HTTP.UTF_8)).append("&");
				}
				sb.deleteCharAt(sb.lastIndexOf("&"));
				stringEntity = new StringEntity(sb.toString());
				stringEntity
						.setContentType("application/x-www-form-urlencoded");
				post.setEntity(stringEntity);
			}

			response = httpclient.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 0) {
				throw new HttpException(DATA_SERVICE_EXC);
			} else if (statusCode != HttpStatus.SC_OK) {
				throw new HttpException(statusCode, FILE_SERVICE_ERROR);
			}
			InputStream is = response.getEntity().getContent();

			Header contentEncoding = response
					.getFirstHeader("Content-Encoding");
			if (contentEncoding != null
					&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
				is = new GZIPInputStream(new BufferedInputStream(is));
			}
			InputStreamReader isr = new InputStreamReader(is, UTF_8);
			in = new BufferedReader(isr);

			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println(result.length());
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(DATA_EXC + "\n", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			httpclient.getConnectionManager().shutdown();
		}

	}

	/**
	 * 文件下载
	 * 
	 * @update 2014-8-25 上午9:43:02<br>
	 * @author <a href="mailto:gaohaiyanghy@126.com">高海燕</a>
	 * @param downUrl
	 * @param savePath
	 * @return
	 */
	public static void downFile(String downUrl, String savePath)
			throws HttpException {
		if (!isNetWorkAvailable()) {
			throw new HttpException(BaseApp.getmContext().getString(
					R.string.unable_to_get_map_info));
		}
		File file = new File(savePath);
		if (!new File(file.getParent()).exists()) {
			new File(file.getParent()).mkdirs();
		}
		HttpURLConnection conn = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			URL imgUrl = new URL(downUrl.replace('\\', '/'));
			conn = (HttpURLConnection) imgUrl.openConnection();
			if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
				conn.setRequestProperty("Connection", "close");
			}
			conn.setConnectTimeout(TIMEOUT_C);
			conn.setReadTimeout(TIMEOUT_R()); // 缓存的最长时间
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "APPLICATION/OCTET-STREAM");
			conn.setRequestProperty("Charset", UTF_8);
			int statusCode = conn.getResponseCode();
			if (statusCode == 0) {
				throw new HttpException(DATA_SERVICE_EXC);
			} else if (statusCode != HttpStatus.SC_OK) {
				throw new HttpException(statusCode, FILE_SERVICE_ERROR);
			}
			bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			byte[] buffer = new byte[FileUtil.UPDATE_SIZE];
			int num = 0;
			while ((num = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, num);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException(FILE_EXC + "\n", e);
		} finally {
			if (bos != null) {
				try {
					bos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static boolean isNetWorkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) BaseApp.getmContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = cm.getActiveNetworkInfo();

		if (mNetworkInfo == null || !mNetworkInfo.isAvailable()) {
			return false;
		}
		return true;
	}

}
