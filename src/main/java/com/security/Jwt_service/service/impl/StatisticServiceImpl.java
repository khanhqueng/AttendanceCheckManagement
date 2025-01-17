package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.response.classroom.ClassroomWithMostAbsentStudent;
import com.security.Jwt_service.dto.response.statistic.StatisticForManager;
import com.security.Jwt_service.dto.response.statistic.StatisticForTeacher;
import com.security.Jwt_service.entity.attendance.Attendance;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.entity.session.Session;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.exception.ResourceNotFoundException;
import com.security.Jwt_service.mapper.classroom.ClassroomMapper;
import com.security.Jwt_service.repository.AttendanceRepository;
import com.security.Jwt_service.repository.ClassroomRepository;
import com.security.Jwt_service.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticServiceImpl implements StatisticService {
    private final AttendanceRepository attendanceRepository;
    private final ClassroomMapper classroomMapper;
    private final ClassroomRepository classroomRepository;
    @Override
    public StatisticForManager statisticForManager(String timeDigit) {
        LocalDate today = LocalDate.now();
        int absentWithPermission= 0;
        int lateForClass= 0;
        int absentWithoutPermission= 0;
        int onTime= 0;
        switch (timeDigit){
            case "Week": {
                LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDateTime startOfWeekTime = LocalDateTime.of(startOfWeek, LocalTime.MIN);
                LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                LocalDateTime endOfWeekTime = LocalDateTime.of(endOfWeek, LocalTime.MAX);
                List<Attendance> attendances= attendanceRepository.findAllAttendancesByTimeRange(startOfWeekTime, endOfWeekTime);
                for (Attendance attendance: attendances){
                    if (attendance.getStatus().equals("Vang ko phep"))
                        absentWithoutPermission++;
                    else if (attendance.getStatus().equals("Vang co phep")) {
                        absentWithPermission++;
                    } else if (attendance.getStatus().startsWith("Di tre")) {
                        lateForClass++;
                    }
                    else onTime++;
                }
                return new StatisticForManager(
                        absentWithPermission,
                        lateForClass,
                        absentWithoutPermission,
                        onTime,
                        aggregateForManager(attendances)
                        );
            }
            case "Month":{
                LocalDate startOfMonth = today.withDayOfMonth(1);
                LocalDateTime startMonthTime = LocalDateTime.of(startOfMonth, LocalTime.MIN);
                LocalDate endOfMonth = today.withDayOfMonth(YearMonth.from(today).lengthOfMonth());
                LocalDateTime endMonthTime = LocalDateTime.of(endOfMonth, LocalTime.MAX);
                List<Attendance> attendances= attendanceRepository.findAllAttendancesByTimeRange(startMonthTime, endMonthTime);

                for (Attendance attendance: attendances){
                    if (attendance.getStatus().equals("Vang ko phep"))
                        absentWithoutPermission++;
                    else if (attendance.getStatus().equals("Vang co phep")) {
                        absentWithPermission++;
                    } else if (attendance.getStatus().startsWith("Di tre")) {
                        lateForClass++;
                    }
                    else onTime++;
                }
                return new StatisticForManager(absentWithPermission,
                        lateForClass,
                        absentWithoutPermission,
                        onTime,
                        aggregateForManager(attendances)
                );
            }
            case "Year": {
                LocalDate startOfYear = today.withDayOfYear(1);
                LocalDateTime startYearTime = LocalDateTime.of(startOfYear, LocalTime.MIN);
                LocalDate endOfYear = today.withDayOfYear(today.lengthOfYear());
                LocalDateTime endYearTime = LocalDateTime.of(endOfYear, LocalTime.MAX);
                List<Attendance> attendances= attendanceRepository.findAllAttendancesByTimeRange(startYearTime, endYearTime);

                for (Attendance attendance: attendances){
                    if (attendance.getStatus().equals("Vang ko phep"))
                        absentWithoutPermission++;
                    else if (attendance.getStatus().equals("Vang co phep")) {
                        absentWithPermission++;
                    } else if (attendance.getStatus().startsWith("Di tre")) {
                        lateForClass++;
                    }
                    else onTime++;
                }
                return new StatisticForManager(absentWithPermission,
                        lateForClass,
                        absentWithoutPermission,
                        onTime,
                        aggregateForManager(attendances)
                );
            }
        }
        throw new AppApiException(HttpStatus.BAD_REQUEST, "Time digit is invalid, time digit is one of Week, Month, Year");

    }

    @Override
    public StatisticForTeacher statisticForTeacher(Long classroomId) {
        Classroom classroom= classroomRepository.findById(classroomId).orElseThrow(
                ()-> new ResourceNotFoundException("Classroom", "id", classroomId)
        );
        int respondReceived =0;
        double totalEfficient =0.0;
        Integer late=0;
        Integer absentWithPermission=0;
        Integer absentWithoutPermission=0;
        Integer onTime=0;
        Integer wellUnderstand=0;
        Integer normalUnderstand=0;
        Integer notWellUnderStand=0;
        Integer badUnderstand=0;
        for(Session session : classroom.getSessions()){
            Set<Attendance> attendances = session.getAttendances();
            for(Attendance attendance: attendances){
                if(attendance.getEfficientRate()!=null &&attendance.getUnderStandingRate()!=null){
                    respondReceived++;
                    totalEfficient+=attendance.getEfficientRate().doubleValue();

                    if(attendance.getUnderStandingRate().doubleValue() >= 4.0 && attendance.getUnderStandingRate().doubleValue() <= 5.0)
                        wellUnderstand++;
                    else if(attendance.getUnderStandingRate().doubleValue() >= 2.5 && attendance.getUnderStandingRate().doubleValue() < 4.0)
                        normalUnderstand++;
                    else if(attendance.getUnderStandingRate().doubleValue() >= 1.5 && attendance.getUnderStandingRate().doubleValue() < 2.5)
                        notWellUnderStand++;
                    else
                        badUnderstand++;
                }
                if(attendance.getStatus().equals("Vang ko phep")) absentWithoutPermission++;
                else if(attendance.getStatus().equals("Vang co phep")) absentWithPermission++;
                else if(attendance.getStatus().equals("Dung gio")) onTime++;
                if(attendance.getStatus().startsWith("Di tre")) late++;
            }
        }
        return StatisticForTeacher.builder()
                .respondReceived(respondReceived)
                .efficientOfLesson(totalEfficient/respondReceived)
                .late(late)
                .absentWithPermission(absentWithPermission)
                .absentWithoutPermission(absentWithoutPermission)
                .onTime(onTime)
                .wellUnderstand(wellUnderstand)
                .normalUnderstand(normalUnderstand)
                .badUnderstand(badUnderstand)
                .notWellUnderStand(notWellUnderStand)
                .build();
    }

    private Map<Classroom, Integer> aggregateAbsentStudent(List<Attendance> attendances){
        Map<Classroom, Integer> classWithAbsentStudent= new HashMap<>();
        for(Attendance attendance: attendances){
            if(attendance.getStatus().equals("Vang ko phep")){
                Classroom classroom= attendance.getSession().getClassroom();
                classWithAbsentStudent.put(classroom, classWithAbsentStudent.getOrDefault(classroom,0)+1 );
            }
        }
        List<Map.Entry<Classroom, Integer>> sortedEntry = new ArrayList<>(classWithAbsentStudent.entrySet());
        sortedEntry.sort(Map.Entry.comparingByValue());
        Map<Classroom, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Classroom, Integer> entry : sortedEntry) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    private List<ClassroomWithMostAbsentStudent> aggregateForManager(List<Attendance> attendances){
        Map<Classroom, Integer> aggregatedClass = aggregateAbsentStudent(attendances);
        return aggregatedClass.keySet().stream().map(
                classroom -> {
                    ClassroomWithMostAbsentStudent classroomWithMostAbsentStudent = classroomMapper.fromEntityToClassWithMostAbsent(classroom);
                    classroomWithMostAbsentStudent.setNumAbsentStudent(aggregatedClass.get(classroom));
                    return classroomWithMostAbsentStudent;
                }
        ).limit(5).toList();
    }
}
