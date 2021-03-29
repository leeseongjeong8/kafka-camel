package com.example.project.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="eips.json")
public class JsonFileConfig {

	private String dataFileDir;
	private String dataAFileName;
	private String dataBFileName;
	
	

	public String getDataFileDir() {
		return dataFileDir;
	}
	public void setDataFileDir(String dataFileDir) {
		this.dataFileDir = dataFileDir;
	}
	public String getDataAFileName() {
		return dataAFileName;
	}
	public void setDataAFileName(String dataAFileName) {
		this.dataAFileName = dataAFileName;
	}
	public String getDataBFileName() {
		return dataBFileName;
	}
	public void setDataBFileName(String dataBFileName) {
		this.dataBFileName = dataBFileName;
	}
	
	
}
