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

    @PostMapping
    public Member create(@RequestBody Member member){
        return membersRepo.save(member);
    }


    //Добавить изменение прав

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Member member) {membersRepo.delete(member);};




}
