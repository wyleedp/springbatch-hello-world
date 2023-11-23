package com.github.wyleedp.spring.batch.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

/**
 * 파라미터의 유효성을 체크하는 Validator Class  
 */
public class ParameterValidator implements JobParametersValidator{

	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {
		String fileName = parameters.getString("fileName");
		
		if(!StringUtils.hasText(fileName)) {
			throw new JobParametersInvalidException("fileName 파일명은 필수입력사항");
		}else if(!StringUtils.endsWithIgnoreCase(fileName, "csv")) {
			throw new JobParametersInvalidException("fileName - csv 파일만 가능");
		}
		
	}
	
}
