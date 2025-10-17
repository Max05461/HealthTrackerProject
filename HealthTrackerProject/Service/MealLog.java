package HealthTrackerProject.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;




// คลาส MealLog ใช้เก็บบันทึกมื้ออาหารแต่ละวันของผู้ใช้
public class MealLog implements Serializable{
    private LocalDate date; // วันของบันทึกมื้อนี้
    private List<MealEntry> entries; // รายการมื้ออาหารของวันนั้น

    public MealLog(LocalDate date){ // รายการมื้ออาหารของวันนั้น
        this.date = date;
        this.entries = new ArrayList<>();
    }

    public void addMeal(String name,int calories){     // เมธอดเพิ่มมื้ออาหารใหม่เข้ารายการ
        entries.add(new MealEntry(name, calories));
    }

    public int getTotalCalories(){ // คำนวณแคลอรี่รวมของมื้ออาหารทั้งหมดในวันนั้น
        int total = 0;
        for (MealEntry entry : entries ){
            total += entry.getCalories(); // บวกแคลอรี่ของแต่ละมื้อ
        }
        return total;
    }

    public LocalDate getDate(){  // คืนค่าวันที่ของบันทึกมื้อนี้
        return date;
    }

    public List<MealEntry> getEntries(){  // คืนค่ารายการมื้ออาหารทั้งหมดในวันนั้น
        return entries;
    }

    
public static class MealEntry implements Serializable{ // คลาสย่อย MealEntry สำหรับเก็บข้อมูลมื้ออาหารแต่ละมื้อ
    private String mealName; // ชื่อมื้ออาหาร เช่น Breakfast, Lunch
    private int calories; // ปริมาณแคลอรี่ของมื้อนั้น
 
    public MealEntry(String mealName, int calories){ // Constructor: รับชื่อและแคลอรี่ของมื้ออาหาร
        this.mealName = mealName;
        this.calories = calories;
    }

    public String getMealName(){ // คืนชื่อของมื้ออาหาร
        return mealName;
    }

    public int getCalories(){ // คืนค่าแคลอรี่ของมื้อนั้น
        return calories;
    }
}

    
}

