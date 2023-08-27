package com.eturn.controller;


import com.eturn.domain.User;
import com.eturn.repo.UsersRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UsersController {
    private final UsersRepo usersRepo;

    public UsersController(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @GetMapping
    public List<User> getUserList(){return usersRepo.findAll();}

    @PostMapping
    public User create(@RequestBody User user){
        return usersRepo.save(user);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") User user){
        usersRepo.delete(user);
        //проверка статутса текущего пользователя
        //если админ то, что будет с turns?
    }
    // тут надо удалить: members, positions, groups, turns


}
