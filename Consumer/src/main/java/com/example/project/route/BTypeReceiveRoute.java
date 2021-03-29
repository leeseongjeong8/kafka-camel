package com.example.project.route;

import java.io.File;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.example.project.config.CSVFileConfig;
import com.example.project.config.ConsumerGroupConfig;
import com.example.project.config.TopicConfig;
import com.example.project.util.CreateFileUtil;

@Component
public class BTypeReceiveRoute extends EndpointRouteBuilder {

	public static final String ROUTE_ID = BTypeReceiveRoute.class.getSimpleName();
	public static final String HISTORY_WRITE_ROUTE = HistoryWriteRoute.class.getSimpleName();
	//public static final String ERROR_SAVE_ROUTE = BErrorSaveRoute.class.getSimpleName();
	public static final String ERROR_SAVE_ROUTE = ErrorSaveRoute.class.getSimpleName();

	@Autowired
	private TopicConfig topic;

	@Autowired
	private CSVFileConfig csvFileConfig;
	
	@Autowired
	private ConsumerGroupConfig consumerGroupConfig;
	
	@Autowired
	private CreateFileUtil createFileUtil;

	@Override
	public void configure() throws Exception {



		errorHandler(deadLetterChannel("direct:"+ERROR_SAVE_ROUTE).maximumRedeliveries(5).maximumRedeliveryDelay(5000));
	
		String directory = csvFileConfig.getFileDir();
		String name = csvFileConfig.getFileName();
		createFileUtil.createFile(directory, name);
		
		CsvDataFormat csvDataFormat = new CsvDataFormat();

		from(kafka(topic.getbRoute()).groupId(consumerGroupConfig.getbRouteGroupId())).log("consumerB 수신완료")
		.routeId(ROUTE_ID)
		.unmarshal().jacksonxml().marshal(csvDataFormat)
		.to(file(directory + "?fileName=" + name + "&fileExist=Append"))
		.process(e -> {
			e.getIn().setHeader("type", "receive");
		}).to(direct(HISTORY_WRITE_ROUTE)).log("B type은 history Write에 도달")
		.log("BRoute의 ConsumerId는 " + consumerGroupConfig.getbRouteGroupId());

	}// configure()
	


}
