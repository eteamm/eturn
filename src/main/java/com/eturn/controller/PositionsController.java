package com.eturn.controller;

import com.eturn.domain.Position;
import com.eturn.repo.PositionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("position")
public class PositionsController {

    private final PositionsRepo positionRepo;
    @Autowired
    public PositionsController(PositionsRepo positionRepo) {
        this.positionRepo = positionRepo;
    }
    @GetMapping
    public List<Position> getPositionsList(){
        return positionRepo.findAll();
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
    public List<Position> getPositions(@PathVariable("id_turn") Long id_turn){
        return positionRepo.findByIdTurn(id_turn);
    } // нужно вывести всех пользователей


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
