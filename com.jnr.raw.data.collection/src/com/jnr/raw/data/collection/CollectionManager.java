package com.jnr.raw.data.collection;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jnr.common.util.http.HttpCollectorUtil;
import com.jnr.raw.data.collection.config.CollectionEle;
import com.jnr.raw.data.collection.config.ConfigManager;
import com.jnr.raw.data.collection.config.DataEle;
import com.jnr.raw.data.collection.config.DataGroupEle;
import com.jnr.raw.data.collection.config.RootEle;

public class CollectionManager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CollectionManager.collect();

	}

	public static void collect() {
		ConfigManager cfgMgr = ConfigManager.getInstance();
		
		Map<String, RootEle> cfgMap = cfgMgr.getConfigMap();
		for(String cfgFile: cfgMap.keySet()){
			System.out.println("Running collections based on config - " + cfgFile);
			RootEle cfgRoot = cfgMap.get(cfgFile);
			for(CollectionEle collectionCfg: cfgRoot.getCollections()){
				System.out.println("Base URL - " + collectionCfg.getBaseUrl());
				if(collectionCfg.getRequestParams() != null && collectionCfg.getRequestParams().size() > 0){
					for(String[][] params: collectionCfg.getParamsList()){
						collect(collectionCfg, params);
					}
				} else {
					collect(collectionCfg, null);
				}
			}
		}
	}

	private static void collect(CollectionEle collectionCfg, String[][] params) {
		String response = HttpCollectorUtil.readResponse(collectionCfg.getBaseUrl(), collectionCfg.getMethod(), params);
		System.out.println("Responce received. Parsing data");
		for(DataEle dataEle: collectionCfg.getDataList()){
			Pattern dataPattern = Pattern.compile(dataEle.getPattern().trim(), HttpCollectorUtil.PATTERN_GENERAL_FLAG);
			Matcher matcher = dataPattern.matcher(response);
			
			String multiplicity = dataEle.getMultiplicity();
			int multi = Integer.MAX_VALUE;
			try {
				multi = Integer.parseInt(multiplicity);
			} catch (NumberFormatException e) {}	
			int index = 1;
			while(index <= multi && matcher.find()){
				int groupCnt = matcher.groupCount();
				for(DataGroupEle groupEle: dataEle.getDataGroups()){
					if(groupEle.getIndex() <= groupCnt){
						System.out.println(groupEle.getLabel() + " : " + matcher.group(groupEle.getIndex()));
					}
				}
				index++;
			}
		}
	}

}
