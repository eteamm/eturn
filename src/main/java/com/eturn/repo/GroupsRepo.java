package com.eturn.repo;

import com.eturn.domain.Group;
import com.eturn.domain.Member;
import com.eturn.domain.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupsRepo extends JpaRepository<Group,Long> {
    boolean existsByNumber(int numGroup);


}
