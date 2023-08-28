package com.eturn.controller;


import com.eturn.domain.Member;
import com.eturn.repo.MembersRepo;
import com.eturn.repo.PositionsRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("member")
public class MembersController {

    private final MembersRepo membersRepo;
    private final PositionsRepo positionRepo;


    public MembersController(MembersRepo membersRepo, PositionsRepo positionsRepo) {
        this.membersRepo = membersRepo;
        this.positionRepo=positionsRepo;
    }

    @GetMapping
    public List<Member> getMemberList(){
        return membersRepo.findAll();
    }

    @GetMapping("root/{id_turn}/{id_user}")
    public Member checkRootUser(@PathVariable("id_user") Long id_user, @PathVariable("id_turn") Long id_turn){
        return membersRepo.getByIdUserAndIdTurn(id_user, id_turn);
    }

    @PostMapping
    public Member create(@RequestBody Member member){
        member.setRoot(0);
        return membersRepo.save(member);
    }


    //Добавить изменение прав

    @DeleteMapping("{id_turn}/{id_user}")
    public void delete(@PathVariable("id_user") Long id_user, @PathVariable("id_turn") Long id_turn) {
        Member member = membersRepo.getByIdUserAndIdTurn(id_user,id_turn);
        positionRepo.deleteByIdUserAndIdTurn(member.getIdUser(), member.getIdTurn());
        membersRepo.delete(member);
    };




}
