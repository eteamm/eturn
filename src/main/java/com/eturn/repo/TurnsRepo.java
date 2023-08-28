package com.eturn.repo;

import com.eturn.domain.Turn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnsRepo extends JpaRepository<Turn, Long> {
    List<Turn> findByIdUser(Long id_user);
    void deleteByIdUser(Long id_user);

    boolean existsByIdUser(Long id_user);

}
