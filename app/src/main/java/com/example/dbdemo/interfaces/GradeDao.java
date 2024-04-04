package com.example.dbdemo.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dbdemo.model.Grade;

import java.util.List;

@Dao
public interface GradeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOneGrade(Grade grade);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGradesFromList(List<Grade> grades);

    @Query("SELECT * from grades")
    List<Grade> GetAllGrades();

    @Query("UPDATE grades SET studgrade=studgrade*1.1 WHERE studentid = :StudId")
    int IncreaseGradeForOneStudent(String StudId);

    @Query("UPDATE grades SET studgrade=studgrade*1.1 WHERE studentid IN (:StudIds) AND courseid = :CourseId")
    int IncreaseGradesForStudentsInCourse(List<String> StudIds,String CourseId);
}
