package com.eturn.controller;

import com.eturn.domain.Group;
import com.eturn.domain.Member;
import com.eturn.domain.Turn;
import com.eturn.domain.User;
import com.eturn.repo.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("group")
public class GroupsController {
    private final GroupsRepo groupsRepo;
    private final MembersRepo membersRepo;
    private final PositionsRepo positionsRepo;
    private final UsersRepo usersRepo;
    private final AllowGroupsRepo allowGroupsRepo;
    private final TurnsRepo turnsRepo;

    public GroupsController(GroupsRepo groupsRepo, MembersRepo membersRepo, PositionsRepo positionsRepo,
                            UsersRepo usersRepo,AllowGroupsRepo allowGroupsRepo, TurnsRepo turnsRepo) {
        this.groupsRepo = groupsRepo;
        this.membersRepo=membersRepo;
        this.positionsRepo=positionsRepo;
        this.usersRepo =usersRepo;
        this.allowGroupsRepo=allowGroupsRepo;
        this.turnsRepo=turnsRepo;
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
        List<User> users= usersRepo.findByIdGroup(group.getId());
        users.forEach(new Consumer<User>(){
            @Override
            public void accept(User user) {
                List<Member> members= membersRepo.findByIdUser(user.getId());
                if (!members.isEmpty())
                {
                    members.forEach(new Consumer<Member>() {
                        @Override
                        public void accept(Member member){
                            List<Turn> currentTurns= turnsRepo.findByIdUser(member.getIdUser());
                            currentTurns.forEach(new Consumer<Turn>() {
                                @Override
                                public void accept(Turn turn) {
                                    membersRepo.deleteByIdTurn(turn.getId());
                                    positionsRepo.deleteByIdTurn((turn.getId()));
                                    allowGroupsRepo.deleteByIdTurn(turn.getId());
                                }
                            });
                            turnsRepo.deleteByIdUser(member.getIdUser());
                            positionsRepo.deleteByIdUser(member.getIdUser());
                            membersRepo.delete(member);
                        }
                    });
                    allowGroupsRepo.deleteByIdGroup(group.getId());
                    usersRepo.deleteByIdGroup(group.getId());

                }
            }
        });


        groupsRepo.delete(group);

    }
}
