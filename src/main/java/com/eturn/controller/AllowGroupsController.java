package com.eturn.controller;

import com.eturn.domain.AllowGroup;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("allowGroup")
public class AllowGroupsController {

    private final com.eturn.repo.AllowGroupsRepo AllowGroupsRepo;

    public AllowGroupsController(com.eturn.repo.AllowGroupsRepo allowGroupRepo) {
        this.AllowGroupsRepo = allowGroupRepo;
    }


    @GetMapping
    public List<AllowGroup> getAllowGroupList(){
        return AllowGroupsRepo.findAll();
    }

    @PostMapping
    public AllowGroup create(@RequestBody AllowGroup AllowGroup){
        return AllowGroupsRepo.save(AllowGroup);

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") AllowGroup AllowGroup) {AllowGroupsRepo.delete(AllowGroup);}
    //тут нужно удалить все: members, positions.


}
