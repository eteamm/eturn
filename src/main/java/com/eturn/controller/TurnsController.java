package com.eturn.controller;

import com.eturn.domain.Member;
import com.eturn.domain.Turn;
import com.eturn.domain.User;
import com.eturn.repo.MembersRepo;
import com.eturn.repo.PositionsRepo;
import com.eturn.repo.TurnsRepo;
import com.eturn.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Consumer;

@RestController
@RequestMapping("turn")
public class TurnsController {

    private final TurnsRepo turnsRepo;
    private final MembersRepo membersRepo;

    private final UsersRepo usersRepo;

    private final PositionsRepo positionsRepo;

    @Autowired
    public TurnsController(TurnsRepo turnsRepo, MembersRepo membersRepo, UsersRepo usersRepo, PositionsRepo positionsRepo)
    {
        this.turnsRepo=turnsRepo;
        this.membersRepo = membersRepo;
        this.usersRepo = usersRepo;
        this.positionsRepo=positionsRepo;
    }

    @GetMapping("yours/{id_user}")
    public List<Turn> getYourTurns(@PathVariable("id_user") Long id_user){
        List<Member> members = membersRepo.findByIdUser(id_user);
        if (members.isEmpty()) return null;
        else{
            List<Turn> turns = new ArrayList<Turn>();
            members.forEach(new Consumer<Member>() {
                @Override
                public void accept(Member member) {
                    Long id_turn = member.getIdTurn();
                    Turn currentTurn;
                    currentTurn = turnsRepo.getById(id_turn);
                    User creator = usersRepo.getById(currentTurn.getIdUser());
                    currentTurn.setNameCreator(creator.getName());
                    turns.add(currentTurn);
                }
            });
            return turns;
        }

    }

    @PostMapping
    public Turn create(@RequestBody Turn turn){return turnsRepo.save(turn);}

    @DeleteMapping({"id"})
    public void delete(@PathVariable("id") Turn turn){
        membersRepo.deleteByIdTurn(turn.getId());
        positionsRepo.deleteByIdTurn((turn.getId()));
        turnsRepo.delete(turn);
    }
    //тут удалить еще allowGroups

}
