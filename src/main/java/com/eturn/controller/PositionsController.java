package com.eturn.controller;

import com.eturn.client.PositionToClient;
import com.eturn.domain.*;
import com.eturn.repo.GroupsRepo;
import com.eturn.repo.MembersRepo;
import com.eturn.repo.PositionsRepo;
import com.eturn.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@RestController
@RequestMapping("position")
public class PositionsController {

    private final PositionsRepo positionsRepo;
    private final UsersRepo usersRepo;
    private final MembersRepo membersRepo;

    private final GroupsRepo groupsRepo;
    @Autowired
    public PositionsController(PositionsRepo positionsRepo, UsersRepo usersRepo, MembersRepo membersRepo, GroupsRepo groupsRepo) {
        this.positionsRepo = positionsRepo;
        this.usersRepo = usersRepo;
        this.membersRepo =membersRepo;
        this.groupsRepo = groupsRepo;
    }
    @GetMapping
    public List<Position> getPositionsList(){
        return positionsRepo.findAll();
    }
//    positions?id_user=1&id_turn=1
//    @GetMapping
//    public List<Position> getPositions(
//            @RequestParam(value = "id_turn", required = false) Long id_turn,
//            @RequestParam(value = "id_user", required = false) Long id_user){
//
//        return positionRepo.findByIdTurnAndIdUser(id_turn,id_user);
//    }
    @GetMapping("{id_turn}")
    public List<PositionToClient> getPositions(@PathVariable("id_turn") Long id_turn){
        List<Position> positions = positionsRepo.findByIdTurn(id_turn);
        List<PositionToClient> positionToClients = new ArrayList<>();
        positions.forEach(new Consumer<Position>() {
            @Override
            public void accept(Position position) {
                User user = usersRepo.getById(position.getIdUser());
                PositionToClient positionToClient = new PositionToClient();
                positionToClient.setId(position.getId());
                positionToClient.setName(user.getName());
                Group group = groupsRepo.getById(user.getIdGroup());
                positionToClient.setNumberGroup(group.getNumber());
                positionToClients.add(positionToClient);
            }
        });
        return positionToClients;
    }


    @PostMapping
    public PositionToClient create(@RequestBody Position position){
        PositionToClient positionToClient= new PositionToClient();
        position.setCreationDate(LocalDateTime.now());
        positionsRepo.save(position);
        User user=usersRepo.getById(position.getIdUser());
        Group group = groupsRepo.getById(user.getIdGroup());
        positionToClient.setId(position.getId());
        positionToClient.setNumberGroup(group.getNumber());
        positionToClient.setName(user.getName());
        return positionToClient;


    }

    @DeleteMapping("{id}")
    public void delete(
                        @PathVariable("id") Position position,
                        @RequestParam(value = "id_user", required = false) Long id_user) {

        if (Objects.equals(id_user, position.getIdUser()))
        {
            positionsRepo.delete(position);
            return;
        }
        Member member = membersRepo.getByIdUserAndIdTurn(id_user, position.getIdTurn());
        if (member.getRoot()==2 || member.getRoot()==1)
        {
            positionsRepo.delete(position);
        }
    }
}
