package com.eturn.controller;

import com.eturn.domain.AllowGroup;
import com.eturn.domain.Group;
import com.eturn.repo.AllowGroupsRepo;
import com.eturn.repo.GroupsRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;


@RestController
@RequestMapping("allowGroup")
public class AllowGroupsController {

    private final AllowGroupsRepo allowGroupsRepo;
    private final GroupsRepo groupsRepo;


    public AllowGroupsController(com.eturn.repo.AllowGroupsRepo allowGroupRepo, GroupsRepo groupsRepo) {
        this.allowGroupsRepo = allowGroupRepo;
        this.groupsRepo = groupsRepo;
    }


    @GetMapping
    public List<AllowGroup> getAllowGroupList(){
        return allowGroupsRepo.findAll();
    }

    @PostMapping
    public List<AllowGroup> create(@RequestBody List<AllowGroup> allowGroups){
        allowGroups.forEach(new Consumer<AllowGroup>() {
            @Override
            public void accept(AllowGroup allowGroup) {
                Long id_group_illusion = allowGroup.getIdGroup();
                int number = id_group_illusion.intValue();
                if (groupsRepo.existsByNumber(number)){
                    Group group = groupsRepo.getByNumber(number);
                    allowGroup.setIdGroup(group.getId());
                    allowGroupsRepo.save(allowGroup);
                }
                else{
                    Group group = new Group();
                    group.setNumber(number);
                    Group createdGroup = groupsRepo.save(group);
                    allowGroup.setIdGroup(createdGroup.getId());
                    allowGroupsRepo.save(allowGroup);
                }
            }
        });
        return allowGroups;

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") AllowGroup AllowGroup) {allowGroupsRepo.delete(AllowGroup);}
    //тут нужно удалить все: members, positions.


}
