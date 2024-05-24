package com.example.waggle.domain.schedule.application.schedule;

import com.example.waggle.domain.member.persistence.entity.Member;
import com.example.waggle.domain.schedule.presentation.dto.schedule.ScheduleRequest;

public interface ScheduleCommandService {

    Long createSchedule(Long teamId, ScheduleRequest createScheduleRequest, Member member);

    Long updateSchedule(Long scheduleId, ScheduleRequest updateScheduleRequest, Member member);

    void deleteSchedule(Long scheduleId, Member member);

    void deleteScheduleWithRelations(Long scheduleId, Member member);

    void deleteScheduleByAdmin(Long scheduleId, Member member);

    void deleteScheduleForHardReset(Long scheduleId);

    Long addMemberSchedule(Long scheduleId, Member member);

    void deleteMemberSchedule(Long scheduleId, Member member);

}
