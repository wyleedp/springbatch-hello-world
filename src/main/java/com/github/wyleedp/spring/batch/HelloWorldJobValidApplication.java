package com.github.wyleedp.spring.batch;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.wyleedp.spring.batch.validator.ParameterValidator;

/**
 * [2023.10.19]
 *   - 파라미터 값 유효성 검증
 */
@EnableBatchProcessing
@SpringBootApplication
public class HelloWorldJobValidApplication {

	private Logger logger = LoggerFactory.getLogger(HelloWorldJobValidApplication.class);
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public CompositeJobParametersValidator valiator() {
		CompositeJobParametersValidator validator = new CompositeJobParametersValidator();
		
		DefaultJobParametersValidator defaultJobParametersValidator = new DefaultJobParametersValidator(
			  new String[] {"fileName"}
			, new String[] {"name"}
		);
		defaultJobParametersValidator.afterPropertiesSet();
		
		validator.setValidators(Arrays.asList(new ParameterValidator(), defaultJobParametersValidator));
		
		return validator;
	}
	
	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("basicJob")
				.start(step1())
				.validator(valiator())
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
		SpringApplication.run(HelloWorldJobValidApplication.class, args);
	}

}
