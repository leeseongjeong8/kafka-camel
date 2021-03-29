package com.example.project.route;

import java.io.File;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.dataformat.csv.CsvDataFormatConfigurer;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.config.CSVFileConfig;
import com.example.project.config.JsonFileConfig;
import com.example.project.processor.ErrorProcessor;
import com.example.project.util.CreateFileUtil;


@Component
public class AErrorSaveRoute extends EndpointRouteBuilder{
	
	@Autowired
	private ErrorProcessor errorProcessor;
	
	@Autowired
	private JsonFileConfig jsonFileConfig;
	
	@Autowired
	private CSVFileConfig csvFileConfig;
	
	@Autowired
	private CreateFileUtil createFileUtil;

	public static final String ERROR_SAVE_ROUTE = AErrorSaveRoute.class.getSimpleName();
	
	

	@Override
	public void configure() throws Exception {
		

		String jsonDirectory = jsonFileConfig.getDataFileDir();
		String fileName = jsonFileConfig.getDataAFileName();
		createFileUtil.createFile(jsonDirectory, fileName);
		
		from(direct(ERROR_SAVE_ROUTE)).routeId(ERROR_SAVE_ROUTE)
		.marshal().json(JsonLibrary.Jackson)
		.to(file(jsonDirectory + "?fileName=" + fileName + "&fileExist=Append"))
		.process(errorProcessor)
		.marshal(new CsvDataFormat().setDelimiter(';'))
		.to(file(csvFileConfig.getErrorfileDir()+"?fileName="+csvFileConfig.getErrorFileName()+"&fileExist=Append"))
		.log("A의 에러정보 -> csv 성공");
		
		
	}
	
	
}
