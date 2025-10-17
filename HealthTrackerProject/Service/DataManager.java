package HealthTrackerProject.Service; //ระบุว่าไฟล์นี้อยู่ในแพ็กเกจ Service (บริการเบื้องหลัง เช่น จัดการข้อมูล)

import java.io.*; //นำเข้าคลาสที่เกี่ยวข้องกับการอ่านและเขียนไฟล์ เช่น FileInputStream, ObjectInputStream, IOException

import HealthTrackerProject.Model.UserProfile; //นำเข้า UserProfile ที่เป็นข้อมูลผู้ใช้ที่เราจะบันทึกหรือโหลด

public class DataManager { //คลาส DataManager ทำหน้าที่จัดการการบันทึก/โหลดข้อมูลของ UserProfile
        public static void saveUserProfile(UserProfile user) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("userprofile.dat"))) { //สร้าง ObjectOutputStream เพื่อเขียน object ลงไฟล์ชื่อ userprofile.dat
            out.writeObject(user); //เขียน object `user` ลงไฟล์ (ต้อง implements Serializable ไว้ที่ UserProfile ด้วย)
        } catch (IOException e) {
            e.printStackTrace(); // แสดงข้อผิดพลาดถ้าเกิดปัญหาในการเขียนไฟล์
        }
    }

    public static UserProfile loadUserProfile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("userprofile.dat"))) { //เปิดไฟล์ userprofile.dat เพื่ออ่าน object
            return (UserProfile) in.readObject(); //โหลดข้อมูล object แล้ว cast กลับเป็น UserProfile
        } catch (IOException | ClassNotFoundException e) {
            return null; //ถ้าไม่มีไฟล์ หรือโหลดไม่ได้ จะคืนค่า null แทน
        }
    }
}
