package com.eturn.repo;

import com.eturn.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembersRepo extends JpaRepository<Member,Long> {

    List<Member> findByIdUser(Long id_user);
    List<Member> findByIdTurnAndRootNot(Long id_turn, int root);
    List<Member> findByIdTurnAndRoot(Long id_turn, int root);

    void deleteByIdTurn(Long idTurn);
    void deleteByIdUser(Long idUser);






    Member getByIdUser(Long idUser);
    Member getByIdUserAndIdTurn(Long id_user, Long id_turn);

    boolean existsByIdUserAndIdTurn(Long id_user, Long id_group);

}
