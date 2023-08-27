package com.eturn.repo;

import com.eturn.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepo extends JpaRepository<Member,Long> {

    List<Member> findByIdUser(Long id_user);
}
