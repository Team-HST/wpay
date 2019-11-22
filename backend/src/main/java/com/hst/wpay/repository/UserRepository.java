package com.hst.wpay.repository;

import com.hst.wpay.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dlgusrb0808@gmail.com
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
