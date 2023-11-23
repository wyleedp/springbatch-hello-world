package com.github.wyleedp.spring.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * <pre>
 * [2023.11.22]
 *   - 청크기반의 ItemReader, ItemWriter 기본 잡
 * </pre>
 */
@EnableBatchProcessing
@SpringBootApplication
public class HelloWorldChunkBaseJobApplication {

	private Logger logger = LoggerFactory.getLogger(HelloWorldChunkBaseJobApplication.class);
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("chunkBaseJob")
				.start(chunkStep())
				.build();
	}
	
	@Bean
	public Step chunkStep() {
		return this.stepBuilderFactory.get("chunkStep")
			.<String, String>chunk(completionPolicy())
			.reader(itemReader())
			.writer(itemWriter())
			.build();
	}
	
	@Bean
	public ListItemReader<String> itemReader(){
		List<String> items = new ArrayList<>(1000);
		
		for(int i=0; i<1000; i++) {
			items.add(UUID.randomUUID().toString()); 
		}
		
		return new ListItemReader<>(items);
	}
	
	@Bean
	public ItemWriter<String> itemWriter(){
		return items -> {
			for(String item : items) {
				logger.info(">> current item = {}", item);
			}
		};
	}
	
	@Bean
	public CompletionPolicy completionPolicy() {
		CompositeCompletionPolicy policy = new CompositeCompletionPolicy();
		
		policy.setPolicies(new CompletionPolicy[] {
				new TimeoutTerminationPolicy(3),
				new SimpleCompletionPolicy(100)
		});
		
		return policy;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HelloWorldChunkBaseJobApplication.class, args);
	}

}
