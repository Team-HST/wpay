package com.hst.wpay.user.repository;

import com.hst.wpay.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author dlgusrb0808@gmail.com
 */
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findById(String id);

}
