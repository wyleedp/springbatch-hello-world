package com.github.wyleedp.spring.batch.incrementer;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class DailyJobTimestamper implements JobParametersIncrementer {

	@Override
	public JobParameters getNext(JobParameters parameters) {
		return new JobParametersBuilder(parameters)
			.addDate("currentDate", new Date())
			.addString("execId", RandomStringUtils.randomAlphabetic(5))
			.toJobParameters();
	}
	
}
