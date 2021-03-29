package com.example.project.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="eips.csv")
public class CSVFileConfig {

	private String fileDir;
	private String fileName;
	private String errorfileDir;
	private String errorFileName;
	
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getErrorfileDir() {
		return errorfileDir;
	}
	public void setErrorfileDir(String errorfileDir) {
		this.errorfileDir = errorfileDir;
	}
	public String getErrorFileName() {
		return errorFileName;
	}
	public void setErrorFileName(String errorFileName) {
		this.errorFileName = errorFileName;
	}
	
	

	
	
	
	
}
