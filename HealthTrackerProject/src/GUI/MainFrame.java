package GUI;

// Import Swing สำหรับสร้าง GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Import Model และ Service ที่จำเป็น
import Model.UserProfile;
import Service.*;

// คลาสหน้าหลักของโปรแกรม Health Tracker
public class MainFrame extends JFrame {

    // โหลดโปรไฟล์ผู้ใช้จากไฟล์ (หากไม่มีจะเป็น null)
    private UserProfile user = DataManager.loadUserProfile();

    // Constructor ของ MainFrame
    public MainFrame() {
        // ตั้งค่าหน้าต่าง
        setTitle("Health Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // เปิดหน้าต่างกลางจอ

        // สร้างปุ่มต่าง ๆ
        JButton profileButton = new JButton("User Profile");
        JButton goalButton = new JButton("Health goal");
        JButton bmiButton = new JButton("Calculate BMI");
        JButton mealButton = new JButton(" Meal log");
        JButton summaryButton = new JButton("Result");

        // ตั้งค่า Layout เป็น GridLayout มี 5 แถว 1 คอลัมน์
        setLayout(new GridLayout(5, 1, 10, 10));
        add(profileButton);
        add(goalButton);
        add(bmiButton);
        add(mealButton);
        add(summaryButton);

        // ======================= ปุ่มเปิดหน้า User Profile =======================
        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProfileForm form = new ProfileForm(); // เปิดฟอร์มให้กรอกข้อมูลโปรไฟล์

                // เพิ่ม listener เพื่อดึงข้อมูลเมื่อหน้าต่างปิด
                form.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent we) {
                        user = form.getUserProfile(); // ดึง UserProfile กลับมา
                        if (user != null) {
                            JOptionPane.showMessageDialog(null, "Profile saved for: " + user.getName());
                        }
                    }
                });
            }
        });

        // ======================= ปุ่มคำนวณ BMI =======================
        bmiButton.addActionListener(e -> {
            if (user != null) {
                HealthCalculator calculator = new HealthCalculator();
                double bmi = calculator.calculateBMI(user); // คำนวณ BMI
                String category = calculator.evaluateBMI(bmi); // ประเมินผลตาม BMI

                // แสดงผลลัพธ์
                String message = String.format("BMI: %.2f\nCategory: %s", bmi, category);
                JOptionPane.showMessageDialog(null, message);
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in user profile first.");
            }
        });

        // ======================= ปุ่มกำหนดเป้าหมายสุขภาพ =======================
        goalButton.addActionListener(e -> {
            if (user != null) {
                new HealthGoalForm(user); // เปิดหน้ากรอกเป้าหมายสุขภาพ
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in user profile first.");
            }
        });

        // ======================= ปุ่มบันทึกมื้ออาหาร =======================
        mealButton.addActionListener(e -> {
            if (user != null) {
                new MealLogForm(user); // เปิดหน้าบันทึกมื้ออาหาร
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in user profile first.");
            }
        });

        // ======================= ปุ่มสรุปผลรวมทั้งหมด =======================
        summaryButton.addActionListener(e -> {
            if (user != null) {
                StringBuilder summary = new StringBuilder(); // ใช้ StringBuilder เพื่อรวมข้อความ
                HealthCalculator calculator = new HealthCalculator();
                double bmi = calculator.calculateBMI(user); // คำนวณ BMI
                int calories = calculator.calculateDailyCalorieNeed(user); // คำนวณความต้องการพลังงานต่อวัน
                String goalStatus = calculator.evaluateUserGoal(user); // ประเมินสถานะเป้าหมาย

                // สร้างข้อความสรุป
                summary.append("User: ").append(user.getName()).append("\n");
                summary.append("BMI: ").append(String.format("%.2f", bmi)).append("\n");
                summary.append("Daily Calorie Need: ").append(calories).append(" kcal\n");
                summary.append("Goal Status: ").append(goalStatus).append("\n");

                // คำนวณพลังงานรวมที่กินทั้งหมดจาก MealLog
                int total = 0;
                for (var log : user.getMealLog()) {
                    total += log.getTotalCalories();
                }
                summary.append("Total Calories Consumed: ").append(total).append(" kcal\n");

                // แสดงผลลัพธ์
                JOptionPane.showMessageDialog(null, summary.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in user profile first.");
            }
        });
    }

    // เมธอด main สำหรับรันโปรแกรม
    public static void main(String[] args) {
        // เรียกใช้ GUI บน Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
