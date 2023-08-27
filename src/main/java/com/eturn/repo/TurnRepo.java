package com.eturn.repo;

import com.eturn.domain.Turn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnRepo extends JpaRepository<Turn,Long> {

}
