package com.eturn.controller;


import com.eturn.domain.Member;
import com.eturn.domain.User;
import com.eturn.repo.MembersRepo;
import com.eturn.repo.PositionsRepo;
import com.eturn.repo.UsersRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("member")
public class MembersController {

    private final MembersRepo membersRepo;
    private final PositionsRepo positionRepo;

    private final UsersRepo usersRepo;


    public MembersController(MembersRepo membersRepo, PositionsRepo positionsRepo, UsersRepo usersRepo) {
        this.membersRepo = membersRepo;
        this.positionRepo=positionsRepo;
        this.usersRepo = usersRepo;
    }

    @GetMapping
    public List<User> getMemberList(
            @RequestParam(value = "id_turn", required = false) Long id_turn,
            @RequestParam(value = "admin", required = false)boolean isAdmin){
        List<Member> members;
        if (isAdmin){
            members = membersRepo.findByIdTurnAndRootNot(id_turn, 0);
        }
        else{
            members = membersRepo.findByIdTurnAndRoot(id_turn, 0);
        }
        List<User> users = new ArrayList<User>();
        members.forEach(new Consumer<Member>() {
            @Override
            public void accept(Member member) {
                users.add(usersRepo.getById(member.getIdUser()));
            }
        });
        return users;
    }

    @GetMapping("root")
    public Member checkRootUser(
            @RequestParam(value = "id_user", required = false) Long id_user,
            @RequestParam(value = "id_turn", required = false) Long id_turn){
        return membersRepo.getByIdUserAndIdTurn(id_user, id_turn);
    }

    @PostMapping
    public Member create(@RequestBody Member member){
        member.setRoot(0);
        return membersRepo.save(member);
    }
    @PutMapping()
    public Member update(
            @RequestBody int root,
            @RequestParam(value = "id_user", required = false) Long id_user_for,
            @RequestParam(value = "id_turn", required = false) Long id_turn,
            @RequestParam(value = "id_user_change", required = false) Long id_user){
        Member admin = membersRepo.getByIdUserAndIdTurn(id_user_for,id_turn);
        Member memberFromDb =membersRepo.getByIdUserAndIdTurn(id_user,id_turn);
        if (admin.getRoot()==2){
            if (root==0 || root==1) {
                memberFromDb.setRoot(root);
                return memberFromDb;
            }
        }
        return memberFromDb;
    }



    @DeleteMapping()
    public void delete(@RequestParam(value = "id_turn", required = false) Long id_turn,
                       @RequestParam(value = "id_user_delete", required = false) Long id_user_delete,
                       @RequestParam(value = "id_user", required = false) Long id_user) {
        if (membersRepo.getByIdUserAndIdTurn(id_user,id_turn).getRoot()==2 || membersRepo.getByIdUserAndIdTurn(id_user,id_turn).getRoot()==1) {
            Member member = membersRepo.getByIdUserAndIdTurn(id_user, id_turn);
            positionRepo.deleteByIdUserAndIdTurn(member.getIdUser(), member.getIdTurn());
            membersRepo.delete(member);
        }

    }




}
