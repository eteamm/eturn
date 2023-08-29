package com.eturn.controller;


import com.eturn.domain.Turn;
import com.eturn.domain.User;
import com.eturn.repo.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("user")
public class UsersController {
    private final UsersRepo usersRepo;
    private final MembersRepo membersRepo;
    private final PositionsRepo positionsRepo;
    private final TurnsRepo turnsRepo;
    private final AllowGroupsRepo allowGroupsRepo;

    public UsersController(UsersRepo usersRepo, MembersRepo membersRepo, PositionsRepo positionsRepo,
                           TurnsRepo turnsRepo, AllowGroupsRepo allowGroupsRepo) {

        this.usersRepo = usersRepo;
        this.membersRepo=membersRepo;
        this.positionsRepo=positionsRepo;
        this.turnsRepo=turnsRepo;
        this.allowGroupsRepo=allowGroupsRepo;
    }

    @GetMapping
    public List<User> getUserList(){return usersRepo.findAll();}

    @PostMapping
    public User create(@RequestBody User user){
        return usersRepo.save(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") User user){
        membersRepo.deleteByIdUser(user.getId());
        positionsRepo.deleteByIdUser(user.getId());
        List<Turn> turns=turnsRepo.findByIdUser(user.getId());
        turns.forEach(new Consumer<Turn>(){
            @Override
            public void accept(Turn turn){
                allowGroupsRepo.deleteByIdTurn(turn.getId());
                membersRepo.deleteByIdTurn(turn.getId());
                positionsRepo.deleteByIdTurn(turn.getId());
                turnsRepo.delete(turn);
            }

        });


        usersRepo.delete(user);
    }



}
