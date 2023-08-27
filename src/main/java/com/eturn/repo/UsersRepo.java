package com.eturn.repo;

import com.eturn.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<User, Long> {
}
