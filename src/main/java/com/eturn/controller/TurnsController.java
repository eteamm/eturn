package com.eturn.controller;

import com.eturn.domain.AllowGroup;
import com.eturn.domain.Member;
import com.eturn.domain.Turn;
import com.eturn.domain.User;
import com.eturn.repo.*;
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

    private final AllowGroupsRepo allowGroupsRepo;

    @Autowired
    public TurnsController(TurnsRepo turnsRepo, MembersRepo membersRepo, UsersRepo usersRepo, PositionsRepo positionsRepo, AllowGroupsRepo allowGroupsRepo)
    {
        this.turnsRepo=turnsRepo;
        this.membersRepo = membersRepo;
        this.usersRepo = usersRepo;
        this.positionsRepo=positionsRepo;
        this.allowGroupsRepo = allowGroupsRepo;
    }

    @GetMapping("{id_turn}")
    public Turn getTurn(@PathVariable("id_turn") Long id_turn){
        return turnsRepo.getById(id_turn);
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

    @GetMapping("allow/{id_user}")
    public List<Turn> getAllowTurns(@PathVariable("id_user") Long id_user){
        User currentUser = usersRepo.getById(id_user);
        List<AllowGroup> allowGroups = allowGroupsRepo.findByIdGroup(currentUser.getIdGroup());
        List<Turn> turns = new ArrayList<Turn>();
        allowGroups.forEach(new Consumer<AllowGroup>() {
            @Override
            public void accept(AllowGroup allowGroup) {
                Long id_turn = allowGroup.getIdTurn();
                Turn currentTurn;
                currentTurn = turnsRepo.getById(id_turn);
                User creator = usersRepo.getById(currentTurn.getIdUser());
                currentTurn.setNameCreator(creator.getName());
                turns.add(currentTurn);
            }
        });
        return turns;
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
