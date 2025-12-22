package Maind;

import Model.UserProfile;
import Service.*;
import java.time.LocalDate;

public class Mainds {
    public static void main(String[] args) {
        UserProfile user = new UserProfile("Max", 180, 52);

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(2);
        

        HealthGoal goal = new HealthGoal(70, "Gain Weight", start, end);
        goal.setGoalType(HealthGoal.GoalType.GAIN_WEIGHT);
        goal.setCurrentWeight(52);

        user.setHealthGoal(goal);
        

        HealthCalculator calc = new HealthCalculator();
        double BMI = calc.calculateBMI(user); 
        String result = calc.evaluateBMI(BMI);
        String goalStatus = calc.evaluateUserGoal(user);

        MealLog todayLog = new MealLog(LocalDate.now());
        todayLog.addMeal("ข้าวไข่เจียว", 400);
        todayLog.addMeal("นม", 150);
        user.addMealLog(todayLog);

        int recommended = calc.calculateDailyCalorieNeed(user);
        int consumed = todayLog.getTotalCalories();

        
        System.out.println("User: " +user.getName());
        System.out.println("Your BMI is " + BMI + " : " + result);
        System.out.println("Goal status: " + goalStatus);
        System.out.println("Goal detail: " + goal);
        System.out.println("Calories consumed today: " + todayLog.getTotalCalories());

        System.out.println("Recommed cal: " + recommended + " kcal");
        System.out.println("Comsumed cal: " + consumed + " kcal");

        if (consumed < recommended) {
            System.out.println("You consume too little try consume more");
        } else if (consumed > recommended) {
            System.out.println("You consume too much go work out");
        } else {
            System.out.println("You did well! you consume calories exact your goal!");
        }

    }
}

