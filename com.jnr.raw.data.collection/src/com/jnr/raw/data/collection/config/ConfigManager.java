package com.jnr.raw.data.collection.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import com.jnr.common.util.CommonUtil;


public class ConfigManager {
	
	private String[] configFiles = new String[] {
		"./config/station.xml",
		"./config/zone.xml",
	};

	private static ConfigManager _instance = null;
	private Map<String, RootEle> configMap = new HashMap<String, RootEle>();
	
	private ConfigManager(){
		// load configurations.
		refreshConfig();
	}

	public void loadConfig(String file) {
		try {
			RootEle root = (RootEle)CommonUtil.loadFromXml(file, RootEle.class);
			configMap.put(file, root);
		} catch (Exception e) {
			System.err.println("Unable to load collection config file - " + file);
			e.printStackTrace();
		}
	}
	
	public static ConfigManager getInstance() {
		if(_instance == null){
			synchronized (ConfigManager.class) {
				_instance = new ConfigManager();
			}
		}
		return _instance;
	}
	
	public void refreshConfig(){
		try {
			for(String file: configFiles){
				loadConfig(file);
			}
		} catch (Exception e) {
			System.err.println("Problem in loading Collection configurations");
			e.printStackTrace();
		}
	}

	public Collection<RootEle> getConfigList(){
		return configMap.values();
	}
	
	
	public Map<String, RootEle> getConfigMap() {
		return configMap;
	}

	// test methods
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			createSampleConfigXml();
			
			Collection<RootEle> configs = getInstance().getConfigList();
			for(RootEle root: configs){
				//System.out.println(root.getCollections().size());
				if(root.getCollections().size()> 0){
					List<String[][]> paramsList = root.getCollections().get(0).getParamsList();
					int cnt = 1;
					for(String[][] params: paramsList){
						System.out.println("Param set - " + cnt++);
						for(int i=0; i<params.length; i++){
							System.out.println(params[i][0] + ":" + params[i][1]);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createSampleConfigXml()
			throws JAXBException, PropertyException {
		//create a sample configuration.
		
		RootEle root1 = new RootEle();
		CollectionEle config = new CollectionEle();
		root1.getCollections().add(config);
		config.setBaseUrl("http://yahoo.com");
		config.setMethod("POST");
		RequestParamsEle rp = new RequestParamsEle();
		config.getRequestParams().add(rp);
		ParamEle p1 = new ParamEle();
		rp.getParams().add(p1);
		p1.setKey("P1");
		p1.getValues().add("P1V1");
		p1.getValues().add("P1V2");
		
		ParamEle p2 = new ParamEle();
		rp.getParams().add(p2);
		p2.setKey("P2");
		p2.getValues().add("P2V1");
		
		// write to file.
		Marshaller m = JAXBContext.newInstance(RootEle.class).createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(root1, System.out);
	}

}
