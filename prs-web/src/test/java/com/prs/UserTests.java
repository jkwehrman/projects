package com.prs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.prs.business.User;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserTests {

	@Autowired
	private UserRepository userRepo;

	@Test
	public void testUserGetAll() {
		Iterable<User> users = userRepo.findAll();
		assertNotNull(users);
	}

	@Test
	public void testUserAdd() {
		User u = new User("username", "pwd", "fname", "lname", "phone", "email", true, true);
		assertNotNull(userRepo.save(u));
		//	id = u.getId(); - in booklet, but not in class
		assertEquals("lname", u.getLastName());
		// delete the user
		//userRepo.delete(u);
		// confirm user deletion by getting the user by ID
		// assertFalse(userRepo.findById(u.getId())).isPresent();

	}
}
