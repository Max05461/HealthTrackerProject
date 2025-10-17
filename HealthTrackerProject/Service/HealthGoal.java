package HealthTrackerProject.Service;

import java.io.Serializable;
import java.time.LocalDate;


// คลาส HealthGoal เก็บข้อมูลเกี่ยวกับเป้าหมายด้านสุขภาพของผู้ใช้
public class HealthGoal implements Serializable{

    public enum GoalStatus { // Enum แสดงสถานะของเป้าหมาย (กำลังทำ, สำเร็จ, ล้มเหลว)
    IN_PROGRESS,
    COMPLETED,
    FAILED
}

    public enum GoalType { // Enum แสดงประเภทของเป้าหมาย (เพิ่มน้ำหนักหรือลดน้ำหนัก)
        GAIN_WEIGHT,
        LOSE_WEIGHT
    }

    private double targetWeight;
    private String note;
    private LocalDate startDate;
    private LocalDate endDate;
    private GoalStatus status;
    private double currentWeight;
    private GoalType goalType;

    public HealthGoal(double targetWeight, String note, LocalDate startDate, LocalDate endDate){
        this.targetWeight = targetWeight;
        this.note = note;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public double getTargetWeight(){
        return targetWeight;
    }

    public String getNote(){
        return note;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public LocalDate getEndDate(){
        return endDate;
    }

    public GoalStatus getStatus(){
        return status;
    }

    public GoalType getGoalType(){
        return goalType;
    }

    public double getCurrentWeight(){
        return currentWeight;
    }

    public void setCurrentWeight(double weight){
        this.currentWeight =weight;
    }

    public void setStatus(GoalStatus status){
        this.status = status;
    }

    public void setGoalType(GoalType goalType){
        this.goalType = goalType;
    }

    public String evaluateProgress(){ // เมธอดประเมินความคืบหน้าและสถานะของเป้าหมาย
        LocalDate today = LocalDate.now();// ดึงวันที่ปัจจุบัน

        this.status = GoalStatus.IN_PROGRESS;// ตั้งค่าเริ่มต้นเป็นกำลังทำ

        if (goalType == null) {
            return "Error : Goal type has not been set."; // ตรวจสอบว่า goalType ถูกตั้งค่าหรือยัง
        }

        if (currentWeight == 0.0){
            return "Error : Current weight has not been set."; // ตรวจสอบว่ามีน้ำหนักปัจจุบันหรือยัง
        }

        if(today.isAfter(endDate)){ // ถ้าวันนี้เลย endDate ไปแล้ว → เช็คว่าบรรลุเป้าหมายหรือไม่
            if (goalType == GoalType.LOSE_WEIGHT && currentWeight <= targetWeight){
                status = GoalStatus.COMPLETED;
                return "Goal Completed: You reached your weight loss target!";
            }   else if (goalType == GoalType.GAIN_WEIGHT && currentWeight >= targetWeight) {
                status = GoalStatus.COMPLETED;
                return "Goal Completed: You reached your weight gain target!";
            }   else {
                status = GoalStatus.FAILED;
                return "Goal Failed: Target not achieved in time.";
            }   
        } else {
            status = GoalStatus.IN_PROGRESS; // ถ้ายังไม่ครบกำหนด → สถานะยังเป็น In Progress
            return "Goal In Progress: Keep going you can do it!!!";
        }
    }

    public String toString(){  // Override toString(): ใช้แสดงรายละเอียดเป้าหมายในรูปแบบข้อความ
        return "GoalType: " + goalType +
                ", TargetWeight: " + targetWeight +
                ", CurrentWeight: " + currentWeight +
                ", Status: " + status +
                ", Note: " + note +
                ", Period: " + startDate + " to " + endDate;
    }

}
