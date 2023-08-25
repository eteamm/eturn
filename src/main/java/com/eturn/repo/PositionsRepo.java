package com.eturn.repo;

import com.eturn.domain.Positions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionsRepo extends JpaRepository<Positions,Long> {

    List<Positions> findByIdTurn(Long id_turn);
}
