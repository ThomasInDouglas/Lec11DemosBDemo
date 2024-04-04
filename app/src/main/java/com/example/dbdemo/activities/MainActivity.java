package com.example.dbdemo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dbdemo.R;
import com.example.dbdemo.adapters.StudentAdapter;
import com.example.dbdemo.databases.CollegeDatabase;
import com.example.dbdemo.databinding.ActivityMainBinding;
import com.example.dbdemo.model.Grade;
import com.example.dbdemo.model.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    List<Student> Students = new ArrayList<>();
    CollegeDatabase cdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding
                = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ReadStudentsCSV();
        List<Grade> GradesList = ReadGrades();
        Log.d("DBDEMO", Students.size() + " Items");
        Log.d("DBDEMO",GradesList.size() + " Grades read from file");
        //set up adapter for listview
        StudentAdapter studentAdapter = new StudentAdapter(Students);
        //set adapter on listview
        //binding.listViewStudents.setAdapter(studentAdapter);
        cdb = Room
                .databaseBuilder(getApplicationContext(),
                        CollegeDatabase.class,"college.db").build();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                cdb.studentDao().insertStudentsFromList(Students);
                List<Student> StudentsDB = cdb.studentDao().GetAllStudents();
                cdb.gradeDao().insertGradesFromList(GradesList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding
                                .listViewStudents.setAdapter(new StudentAdapter(StudentsDB));
                    }
                });
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                                            NextActivity.class));
            }
        });

    }
    private List<Grade> ReadGrades(){
        List<Grade> Grades = new ArrayList<>();
        InputStream inputStream =
                getResources().openRawResource(R.raw.grades);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(inputStream));
        String gradeLine;
        try {
            if ((gradeLine = reader.readLine()) != null){
                //process header line stored in gradeLine
            }
            while((gradeLine = reader.readLine()) != null){
                String[] gradeFields = gradeLine.split(",");
                double grade = Double.parseDouble(gradeFields[2]);
                Grade eachGrade = new Grade(gradeFields[0],gradeFields[1],grade);
                Grades.add(eachGrade);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Grades;
    }
    private void ReadStudentsCSV(){
        //reads students from students.csv
        Students = new ArrayList<>();
        InputStream inputStream =
                getResources().openRawResource(R.raw.students);
        BufferedReader reader
                = new BufferedReader(new InputStreamReader(inputStream));
        //rest of the file read logic next class
        String studentLine;
       try {
           if ((studentLine = reader.readLine()) != null){
               //header line is contained in studentLine
           }
           while ((studentLine = reader.readLine()) != null){
               String[] eachStudFields = studentLine.split(",");
               //any parsing has to be done if needed
               Student eachStudent =
                       new Student(eachStudFields[0], eachStudFields[1],eachStudFields[2]);
               Students.add(eachStudent);
           }
       } catch (IOException ex){
           ex.printStackTrace();
       } finally{
           try {
               inputStream.close();
           } catch (IOException ex){

           }
       }
    }
}