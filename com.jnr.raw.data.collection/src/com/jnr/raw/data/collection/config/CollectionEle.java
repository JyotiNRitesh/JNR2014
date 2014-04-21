package com.jnr.raw.data.collection.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jnr.common.util.http.HttpCollectorUtil;

@XmlRootElement(name="collection")
public class CollectionEle {
	
	// config information
	private String baseUrl = null;
	private String method = HttpCollectorUtil.METHOD_GET;
	private List<RequestParamsEle> requestParams = new ArrayList<RequestParamsEle>();
	private List<DataEle> dataList = new ArrayList<DataEle>();
	
	@XmlAttribute(name="base-url")
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@XmlAttribute(name="method")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@XmlElement(name = "request-params")
	public List<RequestParamsEle> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(List<RequestParamsEle> requestParams) {
		this.requestParams = requestParams;
	}

	
	@XmlElement(name = "data")
	public List<DataEle> getDataList() {
		return dataList;
	}

	public void setDataList(List<DataEle> dataList) {
		this.dataList = dataList;
	}

	// utility methods
	public List<String[][]> getParamsList() {
		List<String[][]> paramsList = new ArrayList<String[][]>();
		for(RequestParamsEle rp: requestParams){
			paramsList.addAll(rp.getParamsList());
		}
		return paramsList;
	}
	

}
