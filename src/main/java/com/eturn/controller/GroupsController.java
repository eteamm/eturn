package com.eturn.controller;

import com.eturn.domain.Group;
import com.eturn.domain.Member;
import com.eturn.repo.GroupsRepo;
import com.eturn.repo.MembersRepo;
import com.eturn.repo.PositionsRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("group")
public class GroupsController {
    private final GroupsRepo groupsRepo;
    private final MembersRepo membersRepo;
    private final PositionsRepo positionRepo;

    public GroupsController(GroupsRepo groupsRepo,MembersRepo membersRepo,PositionsRepo positionsRepo) {
        this.groupsRepo = groupsRepo;
        this.membersRepo=membersRepo;
        this.positionRepo=positionsRepo;
    }


    @GetMapping
    public List<Group> getGroupList(){
        return groupsRepo.findAll();
    }

    @PostMapping
    public Group create(@RequestBody Group group){
        //
        //Ниже проверяем: есть ли такая группа уже в репозитории
        //
        if (groupsRepo.existsByNumber(group.getNumber())) {return null;}
        return groupsRepo.save(group);


    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Group group) {
        List<Member> members =membersRepo.findByIdGroup(group.getId());
        if (!members.isEmpty())
        {
            members.forEach(new Consumer<Member>() {
                @Override
                public void accept(Member member){
                    positionRepo.deleteByIdUser(member.getIdUser());

                }

            });
            membersRepo.deleteByIdGroup(group.getId());

        }
        groupsRepo.delete(group);

    }//удалить еще: Users, allowGroups, turns.
}
