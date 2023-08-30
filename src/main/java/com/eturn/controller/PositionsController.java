package com.eturn.controller;

import com.eturn.domain.Member;
import com.eturn.domain.Position;
import com.eturn.domain.User;
import com.eturn.repo.MembersRepo;
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

    private final PositionsRepo positionsRepo;
    private final UsersRepo usersRepo;
    private final MembersRepo membersRepo;
    @Autowired
    public PositionsController(PositionsRepo positionsRepo, UsersRepo usersRepo, MembersRepo membersRepo) {
        this.positionsRepo = positionsRepo;
        this.usersRepo = usersRepo;
        this.membersRepo =membersRepo;
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
    public List<User> getPositions(@PathVariable("id_turn") Long id_turn){
        List<Position> positions = positionsRepo.findByIdTurn(id_turn);
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
    public User create(@RequestBody Position position){
        position.setCreationDate(LocalDateTime.now());
        Position positionNew = positionsRepo.save(position);
        return usersRepo.getById(positionNew.getIdUser());
    }

    @DeleteMapping()
    public void delete(@RequestParam(value = "id_turn", required = false) Long id_turn,
                       @RequestParam(value = "id_user_delete", required = false) Long id_user_delete,
                       @RequestParam(value = "id_user", required = false) Long id_user) {
        if (id_user==id_user_delete)
        {
            positionsRepo.deleteByIdUserAndIdTurn(id_user_delete,id_turn);
            return;
        }
        Member member = membersRepo.getByIdUserAndIdTurn(id_user,id_turn);
        if (member.getRoot()==2 || member.getRoot()==1)
        {
            positionsRepo.deleteByIdUserAndIdTurn(id_user_delete,id_turn);
        }
    }
}
