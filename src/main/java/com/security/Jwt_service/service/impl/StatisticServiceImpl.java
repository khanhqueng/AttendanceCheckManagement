package com.security.Jwt_service.service.impl;

import com.security.Jwt_service.dto.response.classroom.ClassroomWithMostAbsentStudent;
import com.security.Jwt_service.dto.response.statistic.StatisticForManager;
import com.security.Jwt_service.entity.attendance.Attendance;
import com.security.Jwt_service.entity.classroom.Classroom;
import com.security.Jwt_service.exception.AppApiException;
import com.security.Jwt_service.mapper.classroom.ClassroomMapper;
import com.security.Jwt_service.repository.AttendanceRepository;
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
    @Override
    public StatisticForManager statisticForManager(String timeDigit) {
        LocalDate today = LocalDate.of(2024,12,27);
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
