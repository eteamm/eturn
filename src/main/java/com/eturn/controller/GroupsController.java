package com.eturn.controller;

import com.eturn.domain.Group;
import com.eturn.repo.GroupsRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("group")
public class GroupsController {
    private final GroupsRepo groupsRepo;

    public GroupsController(GroupsRepo groupsRepo) {
        this.groupsRepo = groupsRepo;
    }


    @GetMapping
    public List<Group> getGroupList(){
        return groupsRepo.findAll();
    }

    @PostMapping
    public Group create(@RequestBody Group group){
        //
        //Ниже проверяем есть ли такая группа уже в репозитории
        //
        if (groupsRepo.existsByNumber(group.getNumber())) {return null;}
        return groupsRepo.save(group);


    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Group group) {groupsRepo.delete(group);}
}
