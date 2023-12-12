package com.example.waggle.domain.schedule.repository;

import com.example.waggle.domain.schedule.domain.Team;
import com.example.waggle.domain.schedule.domain.TeamMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByTeamMembers(TeamMember teamMember);

    @Query("SELECT tm FROM TeamMember tm JOIN tm.member m WHERE m.username IN :usernames")
    List<TeamMember> findAllTeamMembersByUsername(@Param("usernames") List<String> usernames);

    Page<Team> findByTeamMembers_Member_Username(String username, Pageable pageable);

}