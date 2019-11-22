package com.hst.wpay.service;

import com.hst.wpay.model.entity.User;
import com.hst.wpay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author dlgusrb0808@gmail.com
 */
@Service
public class GreetingService {

	private final UserRepository userRepository;

	public GreetingService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> greeting() {
		return userRepository.findAll();
	}

	public void create(String name) {
		User user = new User();
		user.setName(name);
		user.setId(UUID.randomUUID().toString());
		user.setPassword("1234");
		userRepository.save(user);
	}

}
