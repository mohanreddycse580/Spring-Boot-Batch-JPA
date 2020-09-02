package com.truist.client.batch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import com.truist.client.entity.Users;
import com.truist.client.entity.UsersVO;
import com.truist.client.repository.UsersRepository;

@Configuration
@EnableBatchProcessing
public class SpringJobConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Autowired
	Processor processor;

	@Autowired
	Writer writer;
	
	@Autowired
	private UsersRepository repo;

	@Bean
	public Job importUserJob() {
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).flow(step()).end().build();
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("step").<Users, UsersVO>chunk(10).reader(repositoryItemReader()).processor(processor)
				.writer(writer).build();
	}
	
	@Bean
    public RepositoryItemReader<Users> repositoryItemReader() {

		Map<String, Sort.Direction> map = new HashMap<>();
		map.put("userId", Sort.Direction.DESC);
		RepositoryItemReader<Users> repositoryItemReader = new RepositoryItemReader<>();
		repositoryItemReader.setRepository(repo);
		repositoryItemReader.setPageSize(5);
		repositoryItemReader.setMethodName("findAll");
		repositoryItemReader.setSort(map);
		return repositoryItemReader;

	}
}
