package com.example.dbdemo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.example.dbdemo.R;
import com.example.dbdemo.databases.CollegeDatabase;
import com.example.dbdemo.databinding.ActivityNextBinding;
import com.example.dbdemo.model.Grade;
import com.example.dbdemo.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NextActivity extends AppCompatActivity {
    CollegeDatabase cdb;
    ActivityNextBinding binding;

    StringBuilder outputText = new StringBuilder(); //string builder must be non null for append to work
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_next);
        binding = ActivityNextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cdb = Room.databaseBuilder(
                getApplicationContext(),
                CollegeDatabase.class,"college.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                int retValue = cdb.studentDao().deleteOneStudentWithId("312345");
                Log.d("DBDEMO", "Deleted records from students = " + retValue);

                List<Student> StudentList = cdb.studentDao().GetAllStudents();
                outputText.append("Displaying students after delete...\n\n");
                outputText.append(String.format("%-10s%-10s%-10s\n","StudId","StudName","Dept"));

                for (Student student:StudentList){
                    outputText.append(String.format("%-10s%-10s%-10s\n",
                            student.getStudId(),student.getStudName(),student.getStudDept()));
                }
                outputText.append("Increase grade for one student\n");
                int retUpdate = cdb.gradeDao().IncreaseGradeForOneStudent("123567");

                outputText.append("Increase grade for set of students\n");
                List<String> StudIds = Arrays.asList("300234","123567");
                String CourseId = "CSIS3475";

                int retUpdate2
                        = cdb.gradeDao().IncreaseGradesForStudentsInCourse(StudIds,CourseId);
                Log.d("DBDEMO", " Updated number of records = " + retUpdate2);

                outputText.append(
                        String.format("%-10s%-10s%-10s\n","CourseId","StudentId","StudGrade"));
                List<Grade> GradeList = cdb.gradeDao().GetAllGrades();
                for (Grade grade:GradeList){
                    outputText.append(String.format("%-10s%-10s%-10.2f\n",
                            grade.getCourseId(),grade.getStudentId(),grade.getStudGrade()));
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.txtViewSummary.setText(outputText);
                    }
                });

            }
        });
    }
}