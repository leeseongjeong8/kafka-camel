package com.example.project.route;

import java.io.File;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project.config.CSVFileConfig;
import com.example.project.config.JsonFileConfig;
import com.example.project.processor.ErrorProcessor;
import com.example.project.util.CreateFileUtil;


@Component
public class ErrorSaveRoute extends EndpointRouteBuilder{
	
	@Autowired
	private ErrorProcessor errorProcessor;
	
	@Autowired
	private JsonFileConfig jsonFileConfig;

	@Autowired
	private CSVFileConfig csvFileConfig;
	
	@Autowired
	private CreateFileUtil createFileUtil;
	

	public static final String ERROR_SAVE_ROUTE = ErrorSaveRoute.class.getSimpleName();


	@SuppressWarnings("static-access")
	@Override
	public void configure() throws Exception {
		
		//Exchange에 FAILURE_ENDPOINT 정보가 담겨있다.
		
		String jsonDirectory = jsonFileConfig.getDataFileDir();
		
		from(direct(ERROR_SAVE_ROUTE)).routeId(ERROR_SAVE_ROUTE)
		.process(e->{
			
			 if((e.getProperty(e.FAILURE_ROUTE_ID,String.class)).equals("ATypeReceiveRoute")) {
				 
				 e.getIn().setHeader("jsonFileName" ,jsonFileConfig.getDataAFileName());
				
				 
			 }else if((e.getProperty(e.FAILURE_ROUTE_ID,String.class)).equals("BTypeReceiveRoute")) {
				  
				 e.getIn().setHeader("jsonFileName" ,jsonFileConfig.getDataBFileName());
					  
			}else {
					  
				 e.getIn().setHeader("jsonFileName" ,"otherErrorFile");
				 
			}//if~else
					 
			
			createFileUtil.createFile(jsonDirectory, (String)e.getMessage().getHeader("jsonFileName"));
			
		})
		.marshal().json(JsonLibrary.Jackson)
		.to(file(jsonDirectory + "?fileName=${headers.jsonFileName}&fileExist=Append"))
		.process(errorProcessor)
		.marshal(new CsvDataFormat().setDelimiter(';'))
		//.to(file(csvFileConfig.getErrorfileDir()+"?fileName="+csvFileConfig.getErrorFileName()+"&fileExist=Append"))
		.log("에러정보 -> csv 성공");
		
		
		
	}
	

	
	
}
