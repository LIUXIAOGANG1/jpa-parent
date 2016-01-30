package com.github.lxgang.jpa.persistence.mysql.repositories;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.lxgang.jpa.beans.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class UserRepositoryTest {
	
	@Resource
	UserRepository userRepository;
	
	@Before
	public void setUp() throws Exception {
		assertNotNull(userRepository);
		userRepository.deleteAll();
	}

	@Test
	public void sampleTestCase() {
		User user = new User();
		user.setName("Test");
		user.setAge(18);
		user.setSex("男");
		
		user = userRepository.save(user);
		
		List<User> result = userRepository.findByName("Test");
		assertEquals(result.size(), 1);
//		assertTrue(result.contains(user));
	}
	
	@Test
	public void findByQuery() {
		User user = new User();
		user.setName("Test");
		user.setAge(20);
		user.setSex("男");
		
		user = userRepository.save(user);
		
		User user1 = new User();
		user1.setName("Test");
		user1.setAge(15);
		user1.setSex("男");
		
		user = userRepository.save(user1);
		
		List<User> result = userRepository.findByQuery("Test");
		assertEquals(result.size(), 1);
//		assertTrue(result.contains(user));
	}
	
	@Test
	public void findPageByAge() throws InterruptedException {
		int sampleNumber = 5;
		String name = "Test";
		
		for(int i = 0; i < sampleNumber; i++){
			User user = new User();
			user.setName(name);
			user.setAge(sampleNumber + i);
			user.setSex("男");
			
			userRepository.save(user);
		}
		
		Pageable pageable = new PageRequest(0, 2);
		Page<User> page = userRepository.findByName(name, pageable);
		assertEquals(2, page.getSize());
		
		pageable = new PageRequest(0, 2, Direction.DESC, "age");
		page = userRepository.findByName(name, pageable);
		assertEquals(2, page.getSize());
		
		List<User> results = page.getContent();
		for(int i = 0; i < results.size(); i++){
			User user = results.get(i);
			System.out.println(user.toString());
		}
		
		pageable = new PageRequest(0, 2, Direction.ASC, "age");
		page = userRepository.findByName(name, pageable);
		assertEquals(2, page.getSize());
		
		results = page.getContent();
		for(int i = 0; i < results.size(); i++){
			User user = results.get(i);
			System.out.println(user.toString());
		}
	}
}
