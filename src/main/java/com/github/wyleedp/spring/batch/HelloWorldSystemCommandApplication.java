package com.github.wyleedp.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * <pre>
 * [2023.11.02]
 *   - 파라미터 값을 타임스탬프로 지정
 *   - before, after를 실행하여 주는 잡리스너 추가
 * </pre>
 */
@EnableBatchProcessing
@SpringBootApplication
public class HelloWorldSystemCommandApplication {

	private Logger logger = LoggerFactory.getLogger(HelloWorldSystemCommandApplication.class);
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("systemCommandJob")
				.start(step1())
				.build();
	}
	
	@Bean
	public Step step1() {
		return this.stepBuilderFactory.get("step1")
			.tasklet(systemCommandTasklet())
			.build();
	}
	
	@Bean
	public SystemCommandTasklet systemCommandTasklet() {
		logger.info("SystemCommandTasklet 시작");
		
		SystemCommandTasklet systemCommandTasklet = new SystemCommandTasklet();
		
		// C:/Users/wylee/1.txt 파일생성
		systemCommandTasklet.setWorkingDirectory("C:/Users/wylee");
		systemCommandTasklet.setCommand("fsutil file createnew 1.txt 0");
		systemCommandTasklet.setTimeout(500);
		systemCommandTasklet.setInterruptOnCancel(true);
		
		logger.info("SystemCommandTasklet 종료");
		
		return systemCommandTasklet;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HelloWorldSystemCommandApplication.class, args);
	}

}
