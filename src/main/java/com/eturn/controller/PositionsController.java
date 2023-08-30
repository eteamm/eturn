package com.eturn.controller;

import com.eturn.domain.Position;
import com.eturn.domain.User;
import com.eturn.repo.PositionsRepo;
import com.eturn.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("position")
public class PositionsController {

    private final PositionsRepo positionRepo;
    private final UsersRepo usersRepo;
    @Autowired
    public PositionsController(PositionsRepo positionRepo, UsersRepo usersRepo) {
        this.positionRepo = positionRepo;
        this.usersRepo = usersRepo;
    }
//    @GetMapping
//    public List<Position> getPositionsList(){
//        return positionRepo.findAll();
//    }
//    positions?id_user=1&id_turn=1
//    @GetMapping
//    public List<Position> getPositions(
//            @RequestParam(value = "id_turn", required = false) Long id_turn,
//            @RequestParam(value = "id_user", required = false) Long id_user){
//
//        return positionRepo.findByIdTurnAndIdUser(id_turn,id_user);
//    }
    @GetMapping()
    public List<User> getPositions(@RequestParam(value = "id_turn", required = false) Long id_turn){
        List<Position> positions = positionRepo.findByIdTurn(id_turn);
        List<User> users = new ArrayList<User>();
        positions.forEach(new Consumer<Position>() {
            @Override
            public void accept(Position position) {
                User user = usersRepo.getById(position.getIdUser());
                users.add(user);
            }
        });
        return users;
    }


    @PostMapping
    public Position create(@RequestBody Position position){
        position.setCreationDate(LocalDateTime.now());
        return positionRepo.save(position);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Position position) {
        positionRepo.delete(position);
    }
}
