package GUI;

// ===== นำเข้า Library ที่จำเป็นสำหรับ GUI และการทำงาน =====
import javax.swing.*;                     // ใช้สำหรับสร้าง GUI (JFrame, JLabel, JTextField, JButton, etc.)
import java.awt.*;                        // ใช้สำหรับ Layout ต่างๆ เช่น GridLayout
import java.awt.event.*;                  // ใช้สำหรับรับเหตุการณ์ เช่นกดปุ่ม
import java.util.function.Consumer;       // ใช้รับ callback function (ยังไม่ใช้ในเวอร์ชันนี้ แต่เตรียมไว้แล้ว)

import Model.UserProfile;     // ดึงคลาส UserProfile จาก Model
import Service.DataManager;   // ดึงคลาส DataManager สำหรับโหลด/บันทึกข้อมูล

// ===== คลาส ProfileForm สำหรับกรอกข้อมูลผู้ใช้งาน =====
public class ProfileForm extends JFrame {
    
    // ===== ตัวแปร GUI และ Model =====
    private JTextField nameField, heightField, weightField;   // ช่องกรอกชื่อ, ส่วนสูง, น้ำหนัก
    private JButton saveButton;                               // ปุ่มบันทึก
    private UserProfile user;                                 // เก็บข้อมูลผู้ใช้งานที่กรอก
    private Consumer<UserProfile> callback;                   // ยังไม่ได้ใช้ในเวอร์ชันนี้

    // ===== Constructor =====
    public ProfileForm() {
        setTitle("Enter Profile");                        // ชื่อหัวหน้าต่าง
        setSize(300, 200);                                // กำหนดขนาด
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ปิดแค่หน้านี้ ไม่ปิดทั้งโปรแกรม
        setLayout(new GridLayout(4, 2, 5, 5));             // Layout แบบ 4 แถว 2 คอลัมน์ ระยะห่าง 5 px
        setLocationRelativeTo(null);                      // ให้อยู่กลางหน้าจอ

        // ===== แถวที่ 1: ช่องกรอกชื่อ =====
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        // ===== แถวที่ 2: ช่องกรอกส่วนสูง =====
        add(new JLabel("Height (cm)"));
        heightField = new JTextField();
        add(heightField);

        // ===== แถวที่ 3: ช่องกรอกน้ำหนัก =====
        add(new JLabel("Weight (kg)"));
        weightField = new JTextField();
        add(weightField);

        // ===== แถวที่ 4: ปุ่มบันทึก =====
        saveButton = new JButton("Save");
        add(saveButton);

        add(new JLabel());  // ช่องว่าง เพื่อจัด GridLayout ให้สวย

        // ===== Event Listener เมื่อกดปุ่ม "Save" =====
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    // ดึงค่าที่กรอกจากช่อง input
                    String name = nameField.getText();
                    double height = Double.parseDouble(heightField.getText());
                    double weight = Double.parseDouble(weightField.getText());

                    // โหลดข้อมูลเดิมจากไฟล์ (ถ้ามี)
                    UserProfile oldUser = DataManager.loadUserProfile();

                    // สร้าง user ใหม่จากค่าที่กรอก
                    user = new UserProfile(name, height, weight);

                    // ถ้า user เดิมมี HealthGoal อยู่ และชื่อเดียวกัน ให้ copy goal มาใส่ user ใหม่
                    if (oldUser != null && oldUser.getName().equals(name) && oldUser.getHealthGoal() != null) {
                        user.setHealthGoal(oldUser.getHealthGoal());
                    }

                    // บันทึกข้อมูล user ใหม่ลงไฟล์
                    DataManager.saveUserProfile(user);

                    // แจ้งเตือนว่าบันทึกเรียบร้อย
                    JOptionPane.showMessageDialog(null, "Profile saved.");

                    // ปิดหน้าต่าง
                    dispose();
                } catch (NumberFormatException ex) {
                    // ถ้าส่วนสูงหรือน้ำหนักไม่ใช่ตัวเลข
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers for height and weight.");
                }
            }
        });

        // แสดงหน้าต่าง
        setVisible(true);
    }

    // ===== Getter สำหรับเรียก user ที่สร้างเสร็จ =====
    public UserProfile getUserProfile() {
        return user;
    }
}
