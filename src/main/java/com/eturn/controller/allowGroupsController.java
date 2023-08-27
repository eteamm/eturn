package com.eturn.controller;

import com.eturn.domain.Group;
import com.eturn.domain.allowGroup;
import com.eturn.repo.allowGroupsRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("allowGroup")
public class allowGroupsController {

    private final allowGroupsRepo AllowGroupsRepo;

    public allowGroupsController(allowGroupsRepo allowGroupRepo) {
        this.AllowGroupsRepo = allowGroupRepo;
    }


    @GetMapping
    public List<allowGroup> getAllowGroupList(){
        return AllowGroupsRepo.findAll();
    }

    @PostMapping
    public allowGroup create(@RequestBody allowGroup AllowGroup){
        return AllowGroupsRepo.save(AllowGroup);

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") allowGroup AllowGroup) {AllowGroupsRepo.delete(AllowGroup);}
    //тут нужно удалить все: members, positions.


}
