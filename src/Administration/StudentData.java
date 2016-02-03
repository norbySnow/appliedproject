/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administration;

/**
 *
 * @author Jacque
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 *
 * @author Norbert
 * @version 1.0
 * @since 2014-03-31
 */
public class StudentData {

    java.sql.Connection conn = null;
    PreparedStatement p = null;

    //this method sets up the connection to the DB
    public void initialize() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/applied_project?user=root&password=");

        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(0);
        }
        System.out.println("Connection established");
    }

    public StudentData() {
        initialize();
    }

    //this method adds a Taxi to the database
    public void addStudent(Student neo) {
        String firstName = neo.getFirstName();
        String lastName = neo.getLastName();
        String grade = neo.getGrade();
        String gender = neo.getGender();
        String nationality = neo.getNationality();
        int imageID = neo.getImageID();
        String birthDate = neo.getBirthDate();
        String fathersName = neo.getFathersName();
        int fPhone = neo.getfPhone();
        String mothersName = neo.getMothersName();
        int mPhone = neo.getmPhone();
        String poBox = neo.getPoBox();
        String resAddr = neo.getResAddr();
        String dateEnrolled = neo.getDateEnrolled();
        int guard1ID = neo.getGuard1ID();
        int guard2ID = neo.getGuard2ID();

        try {
            String stm = "INSERT INTO students(firstname,lastname,grade,gender,imageID,birthDate,fathersname,fphone,mothersname,mphone,pobox,resAddr,dateEnrolled,guard1ID,guard2ID,nationality)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            p = conn.prepareStatement(stm);
            p.setString(1, firstName);
            p.setString(2, lastName);
            p.setString(3, grade);
            p.setString(4, gender);
            p.setInt(5, imageID);
            p.setString(6, birthDate);
            p.setString(7, fathersName);
            p.setInt(8, fPhone);
            p.setString(9, mothersName);
            p.setInt(10, mPhone);
            p.setString(11, poBox);
            p.setString(12, resAddr);
            p.setString(13, dateEnrolled);
            p.setInt(14, guard1ID);
            p.setInt(15, guard2ID);
            p.setString(16, nationality);
            p.executeUpdate();

            System.out.println("New Student Added");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //this method updates a Taxi in the database
    public void updateStudent(Student neo, int id) {
        String firstName = neo.getFirstName();
        String lastName = neo.getLastName();
        String grade = neo.getGrade();
        String gender = neo.getGender();
        String nationality = neo.getNationality();
        int imageID = neo.getImageID();
        String birthDate = neo.getBirthDate();
        String fathersName = neo.getFathersName();
        int fPhone = neo.getfPhone();
        String mothersName = neo.getMothersName();
        int mPhone = neo.getmPhone();
        String poBox = neo.getPoBox();
        String resAddr = neo.getResAddr();
        String dateEnrolled = neo.getDateEnrolled();
        int guard1ID = neo.getGuard1ID();
        int guard2ID = neo.getGuard2ID();
        try {
            String stm = "update students set firstname = ?,lastname = ?,grade = ?,gender = ?,imageID = ?,birthDate = ?,fathersname =?,fphone = ?,mothersname = ?,mphone = ?,pobox,resAddr = ?,dateEnrolled = ?,guard1ID = ?,guard2ID =?,nationality=? where studentID=?";
            p = conn.prepareStatement(stm);
            p.setString(1, firstName);
            p.setString(2, lastName);
            p.setString(3, grade);
            p.setString(4, gender);
            p.setInt(5, imageID);
            p.setString(6, birthDate);
            p.setString(7, fathersName);
            p.setInt(8, fPhone);
            p.setString(9, mothersName);
            p.setInt(10, mPhone);
            p.setString(11, poBox);
            p.setString(12, resAddr);
            p.setString(13, dateEnrolled);
            p.setInt(14, guard1ID);
            p.setInt(15, guard2ID);
            p.setString(16, dateEnrolled);
            p.setInt(17, id);
            p.executeUpdate();
            System.out.println("Student Data Updated");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    //this method deletes a Student in the database
    public void deleteStudent(int studentID) {
        try {
            PreparedStatement r = conn.prepareStatement("DELETE FROM students WHERE studentID=?");
            r.setInt(1, studentID);
            r.execute();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

// this method searches for a row with the given registration number and stores it in an arrayList
    public ArrayList<String> getSearchbyID(int studentID) {
        ResultSet u = null;
        ArrayList<String> array = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            String query = "SELECT * FROM students WHERE studentID = '" + studentID + "'";
            u = s.executeQuery(query);
            if (u.next()) {
                array.add(Integer.toString(u.getInt("studentID")));
                array.add(u.getString("firstname"));
                array.add(u.getString("grade"));
                array.add(u.getString("gender"));
                array.add(u.getString("imageID"));
                array.add(u.getString("birthDate"));
                array.add(u.getString("fathersName"));
                array.add(Integer.toString(u.getInt("fPhone")));
                array.add(u.getString("mothersName"));
                array.add(Integer.toString(u.getInt("mPhone")));
                array.add(u.getString("poBox"));
                array.add(u.getString("resAddr"));
                array.add(u.getString("dateEnrolled"));
                array.add(Integer.toString(u.getInt("guard1ID")));
                array.add(Integer.toString(u.getInt("guard2ID")));

            } else {
                JOptionPane.showMessageDialog(null, "No student with that ID number exist!");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(0);
        }
        return array;
    }

    //This method stores all the needed info about all taxi in a text file
    public ResultSet saveToFile() {
        ResultSet u = null;
        ResultSet x = null;
        try {
            java.sql.Statement s = conn.createStatement();
            String q1 = "SELECT * FROM students ";
            u = s.executeQuery(q1);
            x = u;

            PrintWriter writer = new PrintWriter(new File("Students.txt"));

            while (u.next()) {
                writer.println(u.getString("StudentID"));
                writer.println(u.getString("firstname"));
                writer.println(u.getString("grade"));
                writer.println(u.getString("gender"));
                writer.println(u.getString("imageID"));
                writer.println(u.getString("gender"));
                writer.println(u.getString("birthDate"));
                writer.println(u.getString("fathersName"));
                writer.println(Integer.toString(u.getInt("fPhone")));
                writer.println(u.getString("mothersName"));
                writer.println(Integer.toString(u.getInt("mPhone")));
                writer.println(u.getString("poBox"));
                writer.println(u.getString("resAddr"));
                writer.println(u.getString("dateEnrolled"));
                writer.println(Integer.toString(u.getInt("guard1ID")));
                writer.println(Integer.toString(u.getInt("guard2ID")));

            }

            writer.close();
            System.out.println("saved");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return x;
    }

    // this method just gets info about all taxi drivers available 
    public ResultSet getAllStudents() {
        java.sql.ResultSet u = null;

        try {
            java.sql.Statement s = conn.createStatement();
            String query = "SELECT * FROM students ";
            u = s.executeQuery(query);

        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(0);
        }
        return u;
    }

    public static void main(String[] args) {
        StudentData x = new StudentData();
        ResultSet y = x.saveToFile();
    }

}
