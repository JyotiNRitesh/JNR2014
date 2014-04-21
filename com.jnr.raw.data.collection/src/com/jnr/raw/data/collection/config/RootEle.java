package com.jnr.raw.data.collection.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class RootEle {
	
	private List<CollectionEle> collections = new ArrayList<CollectionEle>();

	@XmlElement(name = "collection")
	public List<CollectionEle> getCollections() {
		return collections;
	}

	public void setCollections(List<CollectionEle> collections) {
		this.collections = collections;
	}
	
	

}
