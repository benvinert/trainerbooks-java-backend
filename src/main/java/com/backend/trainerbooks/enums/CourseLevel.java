package com.backend.trainerbooks.enums;

public enum CourseLevel {
    ALL_LEVELS(0),
    BEGINNER(1),
    INTERMEDIATE(2),
    EXPERT(3);

    private int level;

    CourseLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }


}
