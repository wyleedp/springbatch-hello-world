package com.github.wyleedp.spring.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * <pre>
 * [2023.11.02] 배치실행시 이전/이후 작업을 실행하여주는 리스너
 * </pre>
 */
public class JobLoggerListener implements JobExecutionListener{

	private Logger logger = LoggerFactory.getLogger(JobLoggerListener.class);
	
	private static String START_MESSAGE = "%s 실행 이전";
	private static String END_MESSAGE = "%s 실행 후 - 상태 : %s";
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info(String.format(START_MESSAGE, jobExecution.getJobInstance().getJobName()));
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info(String.format(END_MESSAGE, jobExecution.getJobInstance().getJobName(), jobExecution.getStatus()));
	}

}
