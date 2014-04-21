package com.jnr.raw.data.collection.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "data")
public class DataEle {

	private String pattern = null;
	private List<DataGroupEle> dataGroups = new ArrayList<DataGroupEle>();
	private String multiplicity = "*";
	
	@XmlElement(name = "pattern")
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	@XmlElement(name = "group")
	public List<DataGroupEle> getDataGroups() {
		return dataGroups;
	}
	public void setDataGroups(List<DataGroupEle> dataGroups) {
		this.dataGroups = dataGroups;
	}
	
	@XmlAttribute(name = "multiplicity")
	public String getMultiplicity() {
		return multiplicity;
	}
	public void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity;
	}
}
