package com.github.wyleedp.spring.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

/**
 * <pre>
 * [2023.11.02] 배치실행시 이전/이후 작업을 실행하여주는 리스너 - 어노테이션 방식
 * </pre>
 */
public class JobLoggerAnnotationListener{

	private Logger logger = LoggerFactory.getLogger(JobLoggerAnnotationListener.class);
	
	private static String START_MESSAGE = "%s 실행 이전(어노테이션 방식)";
	private static String END_MESSAGE = "%s 실행 후(어노테이션 방식) - 상태 : %s";
	
	@BeforeJob
	public void beforeJob(JobExecution jobExecution) {
		logger.info(String.format(START_MESSAGE, jobExecution.getJobInstance().getJobName()));
	}

	@AfterJob
	public void afterJob(JobExecution jobExecution) {
		logger.info(String.format(END_MESSAGE, jobExecution.getJobInstance().getJobName(), jobExecution.getStatus()));
	}

}
