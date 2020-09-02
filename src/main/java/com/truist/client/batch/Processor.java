package com.truist.client.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.truist.client.entity.Users;
import com.truist.client.entity.UsersVO;

@Component
public class Processor implements ItemProcessor<Users, UsersVO> {

	@Override
	public UsersVO process(Users users) throws Exception {
		UsersVO userVo = new UsersVO();
		userVo.setAccount(users.getAccount());
		userVo.setUserId(users.getUserId());
		userVo.setDept(users.getDept());
		userVo.setName(users.getName());
		return userVo;
	}

}
