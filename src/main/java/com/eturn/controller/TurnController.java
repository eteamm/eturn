package com.eturn.controller;

import com.eturn.domain.Member;
import com.eturn.domain.Turn;
import com.eturn.domain.User;
import com.eturn.repo.MemberRepo;
import com.eturn.repo.TurnRepo;
import com.eturn.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Consumer;

@RestController
@RequestMapping("turn")
public class TurnController {

    private final TurnRepo turnRepo;
    private final MemberRepo memberRepo;

    private final UserRepo userRepo;

    @Autowired
    public TurnController(TurnRepo turnRepo, MemberRepo memberRepo, UserRepo userRepo)
    {
        this.turnRepo=turnRepo;
        this.memberRepo = memberRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("yours/{id_user}")
    public List<Turn> getYourTurns(@PathVariable("id_user") Long id_user){
        List<Member> members = memberRepo.findByIdUser(id_user);
        if (members.isEmpty()) return null;
        else{
            List<Turn> turns = new ArrayList<Turn>();
            members.forEach(new Consumer<Member>() {
                @Override
                public void accept(Member member) {
                    Long id_turn = member.getIdTurn();
                    Turn currentTurn;
                    currentTurn = turnRepo.getById(id_turn);
                    User creator = userRepo.getById(currentTurn.getIdUser());
                    turns.add(currentTurn);
                }
            });
            return turns;
        }

    }
}
