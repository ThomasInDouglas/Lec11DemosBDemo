package com.example.dbdemo.interfaces;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.dbdemo.model.NameCourseGradeTuple;

import java.util.List;

@Dao
public interface StudentGradeDao {
    @Query("SELECT studentname, courseid, studgrade " +
            "FROM students " +
            "INNER JOIN grades " +
            "ON students.studentid = grades.studentid")
    List<NameCourseGradeTuple> getStudentNameCourseAndGrade();

    @Query("SELECT studentname, courseid, studgrade " +
            "FROM students " +
            "INNER JOIN grades " +
            "WHERE students.studentid = grades.studentid " +
            "AND studgrade > :gradeThreshold")
    List<NameCourseGradeTuple> getStudentNameCourseAndHighGrade(double gradeThreshold);
}
