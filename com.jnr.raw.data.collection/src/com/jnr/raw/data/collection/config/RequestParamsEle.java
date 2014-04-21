package com.jnr.raw.data.collection.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jnr.common.util.CommonUtil;

@XmlRootElement(name = "request-params")
public class RequestParamsEle {
	private List<ParamEle> params = new ArrayList<ParamEle>();
	
	@XmlElement(name = "param")
	public List<ParamEle> getParams() {
		return params;
	}

	public void setParams(List<ParamEle> params) {
		this.params = params;
	}

	
	public List<String[][]> getParamsList(){
		//1. prepare a list of value lists of each param
		List<List<String>> valuesList = new ArrayList<List<String>>();
		for(int i=0; i<params.size(); i++){
			valuesList.add(params.get(i).getValues());
		}
		// 2. get the cartesian product of values.
		// each entry here has one value from each param in the same order as params are listed
		List<List<String>> product = CommonUtil.cartesianProduct(valuesList);
		
		// 3. prepare the param list to be set for each url.
		List<String[][]> paramList = new ArrayList<String[][]>();
		for(List<String> vals: product){
			String[][] paramData = new String[params.size()][2];
			// prepare the pair of key-value of the param...to be used with base url
			for(int i=0; i<params.size(); i++){
				paramData[i][0] = params.get(i).getKey();
				paramData[i][1] = vals.get(i);
			}	
			paramList.add(paramData);
		}
		return paramList;
	}
	
}