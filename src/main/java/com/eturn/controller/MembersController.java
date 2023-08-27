package com.eturn.controller;


import com.eturn.domain.Member;
import com.eturn.repo.MembersRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("member")
public class MembersController {

    private final MembersRepo membersRepo;


    public MembersController(MembersRepo membersRepo) {this.membersRepo = membersRepo;}

    @GetMapping
    public List<Member> getMemberList(){
        return membersRepo.findAll();
    }

    @GetMapping("root/{id_user}")
    public Member checkRootUser(@PathVariable("id_user") Long id_user){
        return membersRepo.getByIdUser(id_user);
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
        membersRepo.delete(member);
    };




}
