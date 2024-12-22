package com.sip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sip.entities.User;

public interface userRepository extends JpaRepository<User, Long> {

}
