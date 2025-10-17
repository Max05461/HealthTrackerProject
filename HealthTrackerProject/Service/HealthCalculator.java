package HealthTrackerProject.Service; //ระบุว่าไฟล์นี้อยู่ในแพ็กเกจ Service ซึ่งทำหน้าที่ประมวลผล เช่น คำนวณสุขภาพ

import HealthTrackerProject.Model.UserProfile; //นำเข้าคลาส UserProfile สำหรับใช้ดึงข้อมูลผู้ใช้

public class HealthCalculator { //คลาสนี้รวมเมธอดที่ใช้คำนวณด้านสุขภาพ เช่น BMI, แคลอรี่, และเป้าหมายสุขภาพ
    
    public double calculateBMI(UserProfile user){
        double height = user.getHeight(); //ดึงส่วนสูง (เซนติเมตร)
        double weight = user.getWeight(); //ดึงน้ำหนัก (กิโลกรัม)

        double heightInMeters = height/100; //แปลงส่วนสูงจากเซนติเมตรเป็นเมตร

        double BMI = weight/Math.pow(heightInMeters,2);  // สูตรคำนวณ BMI = weight / (height^2)

        return BMI; // ส่งค่าผลลัพธ์ BMI กลับไป
    }

    public String evaluateBMI(double BMI){
        if (BMI < 18.5){
            return "UnderWeight";
        } else if (BMI <24.9) {
            return "Average";
        } else if (BMI < 29.9){
            return "Overwieght";
        } else  {
            return "Obese";
        }
    }

    public String evaluateUserGoal(UserProfile user){
        HealthGoal goal = user.getHealthGoal(); // ดึงเป้าหมายสุขภาพของผู้ใช้
        if (goal == null) {
            return "No health goal set."; // ถ้ายังไม่ตั้งเป้าหมาย
        }
        return goal.evaluateProgress(); // ถ้ามีเป้าหมายแล้ว ให้เรียกเมธอดประเมินความคืบหน้า
    }

    public int calculateDailyCalorieNeed(UserProfile user) {
        double baseCalories = user.getWeight() * 30; //แต่ละกิโลกรัมของน้ำหนักตัว ใช้พลังงาน ~30 kcal/วัน
        HealthGoal goal = user.getHealthGoal(); 

        if(goal != null && goal.getGoalType() != null){
            switch (goal.getGoalType()){
                case GAIN_WEIGHT: // ถ้าเป้าหมายคือเพิ่มน้ำหนัก
                    baseCalories += 500; // เพิ่มพลังงาน 500 kcal/วัน
                    break;
                case LOSE_WEIGHT:  // ถ้าเป้าหมายคือลดน้ำหนัก
                    baseCalories -=500;  // ลดพลังงานลง 500 kcal/วัน
                    break;
                default:
                    break;  // กรณี Maintain หรือยังไม่กำหนด ไม่ต้องเปลี่ยนค่า
            }
        }
        return (int) baseCalories;  // แปลงเป็น int และส่งกลับ
    }

}
