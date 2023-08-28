package com.eturn.repo;

import com.eturn.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepo extends JpaRepository<User, Long> {
    void deleteById(Long id_user);
    void deleteByIdGroup(Long id_group);
    List<User> findByIdGroup (Long id_group);
}
