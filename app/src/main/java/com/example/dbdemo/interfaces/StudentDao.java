package com.example.dbdemo.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dbdemo.model.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStudents(Student... students); //... array of Students

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insertStudentsFromList(List<Student> studentList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOneStudent(Student student);
    @Query("SELECT * from students")
    List<Student> GetAllStudents();

    @Query("DELETE from students WHERE studentid=:StudId")
    int deleteOneStudentWithId(String StudId);
}
