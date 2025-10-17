package HealthTrackerProject.Model; // ชุดของคลาส Model (ข้อมูลหลักในระบบ)

// ======= import คลาสที่จำเป็น =======
import HealthTrackerProject.Service.HealthGoal;   // ใช้เก็บเป้าหมายด้านสุขภาพ
import HealthTrackerProject.Service.MealLog;      // ใช้บันทึกการกินแต่ละวัน
import java.util.List;                            // ใช้สำหรับกำหนด List ของ MealLog
import java.io.Serializable;                      // ใช้ทำให้คลาสนี้ถูกบันทึกเป็นไฟล์ได้ (serialize)
import java.util.ArrayList;                       // ใช้สร้างลิสต์เปล่าสำหรับ mealLog

// ======= คลาส UserProfile (โมเดลผู้ใช้งาน) =======
public class UserProfile implements Serializable {

    // ======= ฟิลด์ (ข้อมูลของผู้ใช้งาน) =======
    private String name;                // ชื่อของผู้ใช้
    private double height;              // ส่วนสูงของผู้ใช้ (เซนติเมตร)
    private double weight;              // น้ำหนักของผู้ใช้ (กิโลกรัม)
    private HealthGoal healthGoal;      // เป้าหมายด้านสุขภาพ (ลด/เพิ่มน้ำหนัก)
    private List<MealLog> mealLog = new ArrayList<>(); // ลิสต์ของรายการอาหารที่กินในแต่ละวัน

    // ======= Constructor สำหรับสร้าง UserProfile ใหม่ =======
    public UserProfile(String name, double height, double weight){
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    // ======= Getter: ดึงค่าข้อมูลส่วนตัว =======
    public String getName(){
        return name;
    }

    public double getHeight(){
        return height;
    }

    public double getWeight(){
        return weight;
    }

    public HealthGoal getHealthGoal(){
        return healthGoal;
    }

    // ======= Setter: กำหนดค่าข้อมูลส่วนตัว =======
    public void setName(String name){
        this.name = name;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public void setHealthGoal(HealthGoal healthGoal){
        this.healthGoal = healthGoal;
    }

    // ======= จัดการ MealLog =======
    public void addMealLog(MealLog log){
        mealLog.add(log); // เพิ่มรายการอาหารของแต่ละวันเข้าไปในลิสต์
    }

    public List<MealLog> getMealLog(){
        return mealLog; // ดึงลิสต์ของอาหารทั้งหมดที่เคยบันทึกไว้
    }
}
