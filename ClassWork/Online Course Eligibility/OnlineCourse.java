package com.yogesh.courseeligibility;

public class OnlineCourse extends Course {
    private String platform;
    private int duration; 

    public OnlineCourse(String courseName, String instructor, int credits, String platform, int duration) {
        super(courseName, instructor, credits);
        this.platform = platform;
        this.duration = duration;
    }

    @Override
    public void displayCourseDetails() {
        super.displayCourseDetails();
        System.out.println("Platform: " + platform);
        System.out.println("Duration: " + duration + " hours");
    }

    public boolean isEligibleForCertificate() {
        return duration >= 10;
    }

    public String getPlatform() {
        return platform;
    }

    public int getDuration() {
        return duration;
    }
}
