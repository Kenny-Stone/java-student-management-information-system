package com.studentinformationmanagementsystem;

public class Course {
    private final String courseName;
    private final String courseId;
    private final String courseCredit;
    /* TODO: change this to a class lecturer and
        add an event listener to make it a link to get the lecturer details
     */
    private final String lecturerFirstName;
    private final String lecturerMiddleName;
    private final String lecturerLastName;

    public Course(String courseId, String courseName, String courseCredit,
                  String lecturerFirstName, String lecturerMiddleName, String lecturerLastName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCredit = courseCredit;
        this.lecturerFirstName = lecturerFirstName;
        this.lecturerMiddleName = lecturerMiddleName;
        this.lecturerLastName = lecturerLastName;
    }

    // Getter methods
    public String getCourseName() {
        return courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public String getLecturerFirstName() {
        return lecturerFirstName;
    }
    public String getLecturerMiddleName(){
        return lecturerMiddleName;
    }
    public String getLecturerLastName() {
        return lecturerLastName;
    }
}
