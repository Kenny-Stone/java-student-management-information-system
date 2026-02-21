package com.studentinformationmanagementsystem;

public class Course {
    private final String courseName;
    private final String courseCode;
    private final String courseCredit;
    /* TODO: change this to a class lecturer and
        add an event listener to make it a link to get the lecturer details
     */
    private final String lecturerName;

    public Course(String courseName, String courseCode, String courseCredit, String lecturerName) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseCredit = courseCredit;
        this.lecturerName = lecturerName;
    }

    // Getter methods
    public String getCourseName() {return courseName;}
    public String getCourseCode() {return courseCode;}
    public String getCourseCredit() {return courseCredit;}
    public String getLecturerName() {return lecturerName;}
}
