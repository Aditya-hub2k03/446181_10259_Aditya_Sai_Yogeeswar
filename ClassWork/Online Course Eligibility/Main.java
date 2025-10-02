package com.yogesh.courseeligibility;

public class Main {
    public static void main(String[] args) {
        OnlineCourse onlineCourse = new OnlineCourse("Introduction to Java Programming", "ASYP", 3, "TMF", 12);

        onlineCourse.displayCourseDetails();

        if (onlineCourse.isEligibleForCertificate()) {
            System.out.println("This course is eligible for a certificate.");
        } else {
            System.out.println("This course is not eligible for a certificate.");
        }
    }  
}
