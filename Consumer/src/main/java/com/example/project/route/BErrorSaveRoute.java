package com.example.project.route;

import java.io.File;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.config.CSVFileConfig;
import com.example.project.config.JsonFileConfig;
import com.example.project.processor.ErrorProcessor;
import com.example.project.util.CreateFileUtil;


@Component
public class BErrorSaveRoute extends EndpointRouteBuilder{
	
	@Autowired
	private ErrorProcessor errorProcessor;
	
	@Autowired
	private JsonFileConfig jsonFileConfig;

	@Autowired
	private CSVFileConfig csvFileConfig;
	
	@Autowired
	private CreateFileUtil createFileUtil;

	public static final String ERROR_SAVE_ROUTE = BErrorSaveRoute.class.getSimpleName();
	public String jsonFileName = "";
	

	@Override
	public void configure() throws Exception {
		
		//Exchange에 FAILURE_ENDPOINT 정보가 담겨있다.
		
		String jsonDirectory = jsonFileConfig.getDataFileDir();
		createFileUtil.createFile(jsonDirectory, jsonFileName);

		from(direct(ERROR_SAVE_ROUTE)).routeId(ERROR_SAVE_ROUTE)
		.marshal().json(JsonLibrary.Jackson)
		.process(e->{
	
			
			if((e.getProperty(e.FAILURE_ROUTE_ID, String.class)).equals("AErrorSaveRoute")) {
				jsonFileName  = jsonFileConfig.getDataAFileName();
			
			}else if((e.getProperty(e.FAILURE_ROUTE_ID, String.class)).equals("BErrorSaveRoute")) {
				jsonFileName  = jsonFileConfig.getDataBFileName();
			}
	
		})
		.to(file(jsonDirectory + "?fileName=" + jsonFileName + "&fileExist=Append"))
		.process(errorProcessor)
		.marshal(new CsvDataFormat().setDelimiter(';'))
		.to(file(csvFileConfig.getErrorfileDir()+"?fileName="+csvFileConfig.getErrorFileName()+"&fileExist=Append"))
		.log("B의 에러정보 -> csv 성공");
		

		
	}
	
	
}
