package com.eturn.controller;

import com.eturn.domain.AllowGroup;
import com.eturn.domain.Member;
import com.eturn.domain.Turn;
import com.eturn.domain.User;
import com.eturn.repo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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

    @GetMapping
    public List<Turn> getTurnsList(Model model){return turnsRepo.findAll();}

    @GetMapping("{id_turn}")
    public Turn getTurn(@PathVariable("id_turn") Long id_turn){
        return turnsRepo.getById(id_turn);
    }
    @GetMapping("yours")
    public List<Turn> getYourTurns(@RequestParam(value = "id_user", required = false) Long id_user){
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

    @GetMapping("allow")
    public List<Turn> getAllowTurns(@RequestParam(value = "id_user", required = false) Long id_user){
        User currentUser = usersRepo.getById(id_user);
        List<AllowGroup> allowGroups = allowGroupsRepo.findByIdGroup(currentUser.getIdGroup());
        List<Turn> turns = new ArrayList<Turn>();
        allowGroups.forEach(new Consumer<AllowGroup>() {
            @Override
            public void accept(AllowGroup allowGroup) {
                if (!membersRepo.existsByIdUserAndIdTurn(id_user, allowGroup.getIdTurn())){

                Long id_turn = allowGroup.getIdTurn();
                Turn currentTurn;
                currentTurn = turnsRepo.getById(id_turn);
                User creator = usersRepo.getById(currentTurn.getIdUser());
                currentTurn.setNameCreator(creator.getName());
                turns.add(currentTurn);

                }
            }
        });
        return turns;
    }

    @PostMapping
    public Turn create(@RequestBody Turn turn){
        if (turn.getDescription().length()<=255 && turn.getName().length()<=50) {
            Turn createdTurn = turnsRepo.save(turn);

            Long idTurn = createdTurn.getId();
            Long idUser = createdTurn.getIdUser();
            Member memberCreator = new Member();
            memberCreator.setRoot(2);
            memberCreator.setIdTurn(idTurn);
            memberCreator.setIdUser(idUser);
            membersRepo.save(memberCreator);
            return createdTurn;
        }
        return turn;
    }

    @PutMapping("{id_turn}")
    public Turn update(
            @PathVariable("id_turn") Turn turnFromDb,
            @RequestBody Turn turn,
            @RequestParam(value = "id_user", required = false) Long id_user
    ){
        Member member = membersRepo.getByIdUserAndIdTurn(id_user,turnFromDb.getId());
        if (member.getRoot()==2 || member.getRoot()==1){
            BeanUtils.copyProperties(turn, turnFromDb,"id");
            return turnFromDb;
        } else return turn;
    }

    @DeleteMapping("{id}")
    public void delete(
            @PathVariable("id") Turn turn,
            @RequestParam(value = "id_user", required = false) Long id_user)
    {
        if (Objects.equals(id_user, turn.getIdUser())){
            membersRepo.deleteByIdTurn(turn.getId());
            positionsRepo.deleteByIdTurn((turn.getId()));
            allowGroupsRepo.deleteByIdTurn(turn.getId());
            turnsRepo.delete(turn);
        }
    }


}
