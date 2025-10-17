package HealthTrackerProject.GUI;

// Import ส่วนที่เกี่ยวกับ Swing สำหรับสร้าง GUI
import javax.swing.*;
// Import สำหรับการจัดวาง layout และเหตุการณ์ของ GUI
import java.awt.*;
import java.awt.event.*;

// สำหรับจัดการวันที่
import java.time.LocalDate;

// Import service และ model ที่เกี่ยวข้อง
import HealthTrackerProject.Service.DataManager;
import HealthTrackerProject.Service.HealthGoal;
import HealthTrackerProject.Service.HealthGoal.GoalType;
import HealthTrackerProject.Model.UserProfile;

// คลาสสำหรับกรอกเป้าหมายด้านสุขภาพ
public class HealthGoalForm extends JFrame {

    // ประกาศตัวแปร UI components
    private JTextField targetWeightField, noteField, startDateField, endDateField;
    private JComboBox<GoalType> goalTypeCombo;
    private JButton saveButton;

    // ตัวแปรเก็บ user ที่กำลังแก้ไขข้อมูล
    private UserProfile user;

    // Constructor ที่รับ user มาจากภายนอก
    public HealthGoalForm(UserProfile user) {
        this.user = user; // เก็บไว้ใช้ในคลาสนี้

        // ตั้งค่าหน้าต่าง
        setTitle("Set Health Goal");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ปิดเฉพาะหน้านี้ ไม่ปิดโปรแกรมหลัก
        setLocationRelativeTo(null); // ให้หน้าต่างอยู่กลางจอ
        setLayout(new GridLayout(6, 2, 5, 5)); // GridLayout แบ่งเป็น 6 แถว 2 คอลัมน์

        // ---------- UI Components เริ่มต้น ----------
        add(new JLabel("Goal Type:")); // ประเภทของเป้าหมาย
        goalTypeCombo = new JComboBox<>(GoalType.values()); // Dropdown เลือกประเภทเป้าหมาย
        add(goalTypeCombo);

        add(new JLabel("Target Weight (kg):")); // น้ำหนักเป้าหมาย
        targetWeightField = new JTextField(); // กล่องรับค่าน้ำหนักเป้าหมาย
        add(targetWeightField);

        add(new JLabel("Note:")); // หมายเหตุ
        noteField = new JTextField(); // กล่องหมายเหตุ
        add(noteField);

        add(new JLabel("Start Date (yyyy-mm-dd):")); // วันเริ่มต้น
        startDateField = new JTextField(LocalDate.now().toString()); // ค่าเริ่มต้นเป็นวันนี้
        add(startDateField);

        add(new JLabel("End Date (yyyy-mm-dd):")); // วันสิ้นสุด
        endDateField = new JTextField(LocalDate.now().plusMonths(1).toString()); // ค่าเริ่มต้นคืออีก 1 เดือน
        add(endDateField);

        // ปุ่มบันทึกเป้าหมาย
        saveButton = new JButton("Save Goal");
        add(saveButton);

        add(new JLabel()); // ช่องว่าง (เพื่อจัด layout)

        // ---------- เมื่อกดปุ่ม Save Goal ----------
        saveButton.addActionListener(e -> {
            try {
                // อ่านค่าจากฟอร์ม
                GoalType type = (GoalType) goalTypeCombo.getSelectedItem(); // ประเภทเป้าหมาย
                double targetWeight = Double.parseDouble(targetWeightField.getText()); // น้ำหนักเป้าหมาย
                String note = noteField.getText(); // หมายเหตุ
                LocalDate start = LocalDate.parse(startDateField.getText()); // วันเริ่ม
                LocalDate end = LocalDate.parse(endDateField.getText()); // วันจบ

                // สร้าง HealthGoal ใหม่
                HealthGoal goal = new HealthGoal(targetWeight, note, start, end);
                goal.setGoalType(type); // ตั้งค่าประเภท
                goal.setCurrentWeight(user.getWeight()); // ตั้งค่าน้ำหนักปัจจุบันจากโปรไฟล์

                // ตั้งค่า goal ลงใน user แล้วบันทึกลงไฟล์
                user.setHealthGoal(goal);
                DataManager.saveUserProfile(user); // เซฟลงไฟล์ JSON

                // แจ้งเตือนผู้ใช้ว่าเซฟสำเร็จ
                JOptionPane.showMessageDialog(this, "Goal saved.");
                dispose(); // ปิดหน้าต่างนี้
            } catch (Exception ex) {
                // ถ้ามีข้อผิดพลาด เช่น input ผิดพลาด
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        });

        // แสดงหน้าต่าง
        setVisible(true);
    }
}
