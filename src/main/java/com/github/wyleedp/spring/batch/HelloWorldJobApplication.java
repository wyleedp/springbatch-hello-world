package com.github.wyleedp.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * [2023.10.18] basicJob
 *   - 파라미터 값 구하기
 */
@EnableBatchProcessing
@SpringBootApplication
public class HelloWorldJobApplication {

	private Logger logger = LoggerFactory.getLogger(HelloWorldJobApplication.class);
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("basicJob")
				.start(step1())
				.build();
	}
	
	@Bean
	public Step step1() {
		return this.stepBuilderFactory.get("step1")
			.tasklet((contribution, chunkContext) -> {
				logger.info("HelloWorldJobApplication - Hello World!!!");
				
				// 파라미터 값 가져오기
				String name = (String)chunkContext.getStepContext().getJobParameters().get("name");
				logger.info("Parameter name : {}", name);
				
				return RepeatStatus.FINISHED;
			}).build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HelloWorldJobApplication.class, args);
	}

}
