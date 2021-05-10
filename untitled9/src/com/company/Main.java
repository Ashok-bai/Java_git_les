package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Connection con;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection();
        while (true){
            System.out.println("[1] Add user \n " +
                    "[2] List users \n" +
                    "[3] Edit user \n" +
                    "[4] Delete user \n " +
                    "[0] Exit");
            int choose = sc.nextInt();
            if(choose==1){
                System.out.println("insert name:");
                String name =sc.next();
                System.out.println("insert surname:");
                String surname =sc.next();
                System.out.println("insert age:");
                int age = sc.nextInt();

                User user = new User(null,name,surname,age);
                addUser(user);

            }else  if(choose==2){
                ArrayList<User> arrListUser = getUser();
                for(User u :arrListUser){
                    System.out.println(u);
                }

            }else  if(choose==3){
                System.out.println("insert id:");
                Long id = sc.nextLong();
                System.out.println("insert name:");
                String name =sc.next();
                System.out.println("insert surname:");
                String surname =sc.next();
                System.out.println("insert age:");
                int age = sc.nextInt();

                User user = new User(id,name,surname,age);

                editUser(user);
            }else  if(choose==4){
                System.out.println("insert id:");
                int id = sc.nextInt();
                deleteUser(id);

            }else  if(choose==0){
             break;
            }
        }

    }

    public static void Connection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_les?useUnicode=true&serverTimezone=UTC","root","");
            System.out.println("connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getUser(){
        ArrayList<User> arrayList = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("select * from users;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id =rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                int age = rs.getInt("age");

                arrayList.add(new User(id,name,surname,age));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public  static void addUser(User u){
        try {
            PreparedStatement ps = con.prepareStatement("insert into users(name,surname,age) values(?,?,?) ");
            ps.setString(1,u.getName());
            ps.setString(2, u.getSurname());
            ps.setInt(3,u.getAge());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editUser(User u2){
        try {
            PreparedStatement ps = con.prepareStatement("update users set name =? ,surname = ?,age = ? where id =?");
            ps.setString(1,u2.getName());
            ps.setString(2, u2.getSurname());
            ps.setInt(3,u2.getAge());
            ps.setLong(4,u2.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(int id){

        try {
            PreparedStatement ps = con.prepareStatement("delete from users where id = ?");
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
