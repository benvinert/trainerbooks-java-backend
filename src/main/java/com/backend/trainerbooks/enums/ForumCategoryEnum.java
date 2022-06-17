package com.backend.trainerbooks.enums;

public enum ForumCategoryEnum {
    FOOD_AND_NUTRITION(1),
    RECIPES(2),
    FITNESS_AND_EXERCISES(3),
    GETTING_STARTED(4),
    CHALLENGES(5),
    MOTIVATION(6);

    private int value;

    ForumCategoryEnum(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }

    public static ForumCategoryEnum getByValue(int enumVal) {
        return ForumCategoryEnum.values()[ enumVal - 1];
    }

}
