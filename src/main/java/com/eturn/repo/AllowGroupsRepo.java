package com.eturn.repo;

import com.eturn.domain.AllowGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllowGroupsRepo extends JpaRepository<AllowGroup,Long> {
    List<AllowGroup> findByIdGroup(Long idGroup);
}
