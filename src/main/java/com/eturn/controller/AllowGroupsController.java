package com.eturn.controller;

import com.eturn.domain.AllowGroup;
import com.eturn.domain.Group;
import com.eturn.domain.Member;
import com.eturn.domain.User;
import com.eturn.repo.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;


@RestController
@RequestMapping("allowGroup")
public class AllowGroupsController {

    private final AllowGroupsRepo allowGroupsRepo;
    private final GroupsRepo groupsRepo;
    private final MembersRepo membersRepo;
    private final PositionsRepo positionsRepo;
    private final UsersRepo usersRepo;


    public AllowGroupsController(AllowGroupsRepo allowGroupRepo, GroupsRepo groupsRepo,
                                 MembersRepo membersRepo,PositionsRepo positionsRepo,UsersRepo usersRepo) {
        this.allowGroupsRepo = allowGroupRepo;
        this.groupsRepo = groupsRepo;
        this.membersRepo=membersRepo;
        this.positionsRepo=positionsRepo;
        this.usersRepo=usersRepo;
    }


    @GetMapping
    public List<AllowGroup> getAllowGroupList(){
        return allowGroupsRepo.findAll();
    }

    @GetMapping("{id_turn}")
    public List<AllowGroup> getAllowGroup(@PathVariable("id_turn") Long id_turn){
        return allowGroupsRepo.findByIdTurn(id_turn);
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

    @DeleteMapping("{id_turn}/{number}")
    public void delete(@PathVariable("id_turn") Long id_turn, @PathVariable("number") int number) {
        Group group = groupsRepo.getByNumber(number);
        AllowGroup allowGroup = allowGroupsRepo.getByIdTurnAndIdGroup(id_turn, group.getId());
        List<User> users=usersRepo.findByIdGroup(allowGroup.getIdGroup());
        users.forEach(new Consumer<User>() {
            @Override
            public void accept(User user) {
                Member member= membersRepo.getByIdUserAndIdTurn(user.getId(), allowGroup.getIdTurn());
                positionsRepo.deleteByIdUserAndIdTurn(user.getId(), allowGroup.getIdTurn());
                membersRepo.delete(member);
            }
        });


        allowGroupsRepo.delete(allowGroup);

    }



}
