package com.eturn.repo;

import com.eturn.domain.AllowGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllowGroupsRepo extends JpaRepository<AllowGroup,Long> {
    List<AllowGroup> findByIdGroup(Long idGroup);
    void deleteByIdTurn(Long idTurn);
    void deleteByIdGroup(Long idGroup);

    AllowGroup getByIdTurnAndIdGroup(Long id_turn, Long id_group);
}
