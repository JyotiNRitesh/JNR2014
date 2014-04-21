package com.jnr.common.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class HttpCollectorUtil {
	// Constants
	/**  HTTP Request method - "GET"  */
	public static String METHOD_GET 	= "GET";
	/**  HTTP Request method - "POST"  */
	public static String METHOD_POST 	= "POST";
	/**  HTTP Request method - "HEAD"  */
	public static String METHOD_HEAD 	= "HEAD";
	/**  HTTP Request method - "OPTIONS"  */
	public static String METHOD_OPTIONS	= "OPTIONS";
	/**  HTTP Request method - "PUT"  */
	public static String METHOD_PUT 	= "PUT";
	/**  HTTP Request method - "DELETE"  */
	public static String METHOD_DELETE 	= "DELETE";
	/**  HTTP Request method - "TRACE"  */
	public static String METHOD_TRACE 	= "TRACE";

	/** The common flag for Pattern parsing. */
	public static int PATTERN_GENERAL_FLAG = Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE;
	
	/**  HTTP Request default timeout (in ms)  */
	private static int READ_TIME_OUT	= 5000;
	/** Default encoding */
	private static String DEFAULT_ENCODING 	= "UTF-8";
	
	/**
	 * Create a {@link URLConnection} object for the given (http) resource URL.
	 * @param {@link String} resource
	 * @return {@link URLConnection}
	 */
	public static URLConnection connect(String resource) {
		URLConnection conn = null;
		URL url;
		try {
			url = new URL(resource);
			conn = url.openConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * default method - GET
	 * @param {@link String} resourceUrl
	 * @return {@link String}
	 */
	public static String readResponse(String resourceUrl) {
		return readResponse(resourceUrl, METHOD_GET, null);
	}
	/**
	 * 
	 * @param {@link String} resourceUrl
	 * @param {@link String}[][] params
	 * @return {@link String}
	 */
	public static String readPostResponse(String resourceUrl, String[][] params) {
		return readResponse(resourceUrl, METHOD_POST, params);
	}
	
	/**
	 * 
	 * @param {@link String} resourceUrl
	 * @param {@link String} method. One of the following - {@link URLConnector#METHOD_GET}, {@link URLConnector#METHOD_POST},{@link URLConnector#METHOD_HEAD}, {@link URLConnector#METHOD_OPTIONS}, {@link URLConnector#METHOD_PUT}, {@link URLConnector#METHOD_DELETE}, {@link URLConnector#METHOD_TRACE},  
	 * @param {@link String}[][] params
	 * @return {@link String}
	 */
	public static String readResponse(String resourceUrl, String method, String[][] params) {
		StringBuffer response = new StringBuffer();
		String data = null;
		if(params != null){
			for(int i=0; i<params.length; i++){
				
				String key = null;
				String val = null; 
				try {
					key = params[i][0];
					val = params[i][1];
					if(i == 0){
						data = new String();
					}
					data = data + key + "=" + URLEncoder.encode(val, DEFAULT_ENCODING)  + "&" ;
				} catch (UnsupportedEncodingException e) {
					System.err.println("Cannot encode - {key - " + key + " : value - " +  val + "}");
				}
			}
		}
		if(METHOD_GET.equalsIgnoreCase(method)){
			resourceUrl = resourceUrl + "?" + data;
		}
		HttpURLConnection conn = (HttpURLConnection)connect(resourceUrl);
		if (conn != null) {
			try {
				conn.setReadTimeout(READ_TIME_OUT);
				conn.setRequestMethod(method);
				if(data != null){
					conn.setDoOutput(true);
					OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream()); 
					 
				    writer.write(data); 
				    writer.flush(); 
				  }
				conn.connect();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine).append("\n");
				}
				in.close();
			} catch (IOException e) {
				System.err.println("Error reading response for - " + resourceUrl + " :: with inputs - " + data);
			}
		}
		return response.toString();
	}

	/**
	 * Local method to test routines.
	 * @param args An array of VM arguments
	 */
	public static void main(String[] args) {
		String URL = "http://www.indianrail.gov.in/cgi_bin/inet_trnnum_cgi.cgi";
		String[][] params = {{"lccp_trnname", "01"}};
		String response = readPostResponse(URL, params);
		System.out.println(URL + "\n\n" + response);
	}


}
