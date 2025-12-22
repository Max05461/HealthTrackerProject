package GUI;

// ===== Import library สำหรับ GUI, เวลา และ class ที่จำเป็น =====
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import Model.UserProfile;
import Service.MealLog;
import Service.DataManager;

// ===== คลาส MealLogForm สำหรับกรอกข้อมูลอาหาร =====
public class MealLogForm extends JFrame {
    // ===== ตัวแปร GUI และ Model =====
    private JTextField mealNameField, calorieField;     // กล่องกรอกชื่ออาหารและแคลอรี่
    private JTextArea mealListArea;                     // พื้นที่แสดงรายการอาหารทั้งหมด
    private JLabel totalCaloriesLabel;                  // ป้ายแสดงแคลอรี่รวม
    private MealLog mealLog;                            // บันทึกมื้ออาหารของวันนี้
    private UserProfile user;                           // ผู้ใช้งาน

    // ===== Constructor รับ UserProfile =====
    public MealLogForm(UserProfile user) {
        this.user = user;

        // ตั้งค่าหน้าต่าง
        setTitle("Meal Log");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   // ปิดเฉพาะหน้าต่างนี้ ไม่ปิดทั้งแอป
        setLocationRelativeTo(null);                         // กลางหน้าจอ
        setLayout(new BorderLayout(10, 10));                 // ใช้ BorderLayout

        // ===== ค้นหา MealLog ของวันนี้ =====
        mealLog = null;
        for (MealLog log : user.getMealLog()) {
            if (log.getDate().equals(LocalDate.now())) {
                mealLog = log; // ถ้ามีอยู่แล้วให้ใช้
                break;
            }
        }

        // ถ้ายังไม่มีให้สร้างใหม่และเพิ่มใน user
        if (mealLog == null) {
            mealLog = new MealLog(LocalDate.now());
            user.addMealLog(mealLog);
        }

        // ===== Panel สำหรับกรอกข้อมูลอาหาร =====
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.add(new JLabel("Meal Name:"));            // ป้ายชื่ออาหาร
        mealNameField = new JTextField();                    // ช่องกรอกชื่อ
        inputPanel.add(mealNameField);

        inputPanel.add(new JLabel("Calories:"));             // ป้ายแคลอรี่
        calorieField = new JTextField();                     // ช่องกรอกแคลอรี่
        inputPanel.add(calorieField);

        JButton addButton = new JButton("Add Meal");         // ปุ่มเพิ่มอาหาร
        inputPanel.add(addButton);

        totalCaloriesLabel = new JLabel("Total Calories: 0"); // ป้ายแสดงแคลอรี่รวม
        inputPanel.add(totalCaloriesLabel);

        add(inputPanel, BorderLayout.NORTH);                 // วางไว้ด้านบน

        // ===== พื้นที่แสดงรายการอาหารทั้งหมด =====
        mealListArea = new JTextArea();
        mealListArea.setEditable(false);                     // ไม่ให้พิมพ์
        add(new JScrollPane(mealListArea), BorderLayout.CENTER); // ใส่ใน ScrollPane เผื่อข้อมูลเยอะ

        // ===== เมื่อกดปุ่ม "Add Meal" =====
        addButton.addActionListener(e -> {
            try {
                String meal = mealNameField.getText();             // ดึงชื่ออาหาร
                int cal = Integer.parseInt(calorieField.getText()); // ดึงแคลอรี่ (แปลงเป็นตัวเลข)

                // ตรวจสอบว่ากรอกชื่ออาหารหรือยัง
                if (meal.isEmpty()) {
                    throw new IllegalArgumentException("Meal name is required.");
                }

                // เพิ่มมื้ออาหารลงใน mealLog
                mealLog.addMeal(meal, cal);

                // บันทึกลงไฟล์
                DataManager.saveUserProfile(user);

                // อัปเดตรายการในหน้าจอ
                updateMealList();
                mealNameField.setText("");         // ล้างช่องกรอก
                calorieField.setText("");
            } catch (NumberFormatException ex) {
                // กรณีใส่แคลอรี่ไม่ใช่ตัวเลข
                JOptionPane.showMessageDialog(this, "Calories must be a number.");
            } catch (IllegalArgumentException ex) {
                // กรณีช่องชื่ออาหารว่าง
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        // แสดงรายการอาหารล่าสุดทันทีเมื่อเปิด
        updateMealList();
        setVisible(true); // แสดงหน้าต่าง
    }

    // ===== เมธอดอัปเดตรายการมื้ออาหารบนหน้าจอ =====
    private void updateMealList() {
        StringBuilder builder = new StringBuilder();
        for (var entry : mealLog.getEntries()) {
            builder.append(entry.getMealName()).append(" - ")
                   .append(entry.getCalories()).append(" kcal\n");
        }
        mealListArea.setText(builder.toString());

        // แสดงแคลอรี่รวม
        totalCaloriesLabel.setText("Total Calories: " + mealLog.getTotalCalories());
    }
}
