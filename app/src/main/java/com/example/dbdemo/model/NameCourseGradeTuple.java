package com.example.dbdemo.model;

import androidx.room.ColumnInfo;

public class NameCourseGradeTuple {

    @ColumnInfo(name = "studentname")
    private String StudName;

    @ColumnInfo(name = "courseid")
    private String CourseId;

    @ColumnInfo(name = "studgrade")
    private double StudGrade;

    public String getStudName() {
        return StudName;
    }

    public void setStudName(String studName) {
        StudName = studName;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public double getStudGrade() {
        return StudGrade;
    }

    public void setStudGrade(double studGrade) {
        StudGrade = studGrade;
    }
}
