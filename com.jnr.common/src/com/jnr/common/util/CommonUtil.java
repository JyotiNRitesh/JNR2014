package com.jnr.common.util;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class CommonUtil {
	
	public static List<List<String>> cartesianProduct(List<List<String>> lists) {
		  List<List<String>> resultLists = new ArrayList<List<String>>();
		  if (lists.size() == 0) {
		    resultLists.add(new ArrayList<String>());
		    return resultLists;
		  } else {
		    List<String> firstList = lists.get(0);
		    List<List<String>> remainingLists = cartesianProduct(lists.subList(1, lists.size()));
		    for (String condition : firstList) {
		      for (List<String> remainingList : remainingLists) {
		        ArrayList<String> resultList = new ArrayList<String>();
		        resultList.add(condition);
		        resultList.addAll(remainingList);
		        resultLists.add(resultList);
		      }
		    }
		  }
		  return resultLists;
		}
	
	public static Object loadFromXml(String xml, Class clazz){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object obj = unmarshaller.unmarshal(new FileReader(xml));
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
