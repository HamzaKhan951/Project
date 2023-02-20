/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author Hamza
 */
public class users {

    String Email;
    String Password;
    String Country;
    int age;
    DB_Connection db;
    boolean Success = false;

    public users(String a, String b, String c, int age) {
        db = new DB_Connection();
        Success = db.add_users(a, b, c, age);
        if(Success == true){
            this.Email = a;
            this.Password = b;
            this.Country = c;
            this.age = age;
        }
        

    }

    public users(String a, String b) {
        db = new DB_Connection();
        users usr = null;
        usr = db.login(a, b);
        try{
            if (!db.err.equals("no data")) {
                Success = true;
                this.Email = usr.Email;
                this.Country = usr.Country;
                this.Password = usr.Password;
                this.age = usr.age;
            }
            else{
                Success = false;
                db.err = "incorrect email or password";
            }
        }catch(Exception e){
            Success = false;
            db.err = db.err +  "incorrect email or password" + e.getMessage();
        }
    }

    boolean changePassword(String a, String b, String c) {
        if (c.length() < 4 || c.length() > 10) {
            return false;
        }
        if (Email.equals(a)) {
            if (Password.equals(b)) {
                Password = c;

                return true;
            }
        }
        return false;
    }

    boolean changeEmail(String a, String b, String c) {
        if (c.length() > 20) {
            return false;
        }
        if (Email.equals(a)) {
            if (Password.equals(b)) {
                Email = c;
                return true;
            }
        }
        return false;
    }

    users successful() {
        return this;
    }
}

class DB_Connection {

    Connection con;
    Statement st;
    String err = "";

    /*String encrypt(String a){
        StringBuffer s = new StringBuffer(a);
        s.reverse();
        return s.toString();
    }*/
    DB_Connection() {

        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            //con = DriverManager.getConnection("jdbc:odbc:Database1");
            con = DriverManager.getConnection("jdbc:ucanaccess://D:\\FAST\\Spring 2021\\Advanced Programming\\Project\\RecipeGenerator\\food_app_db.accdb");
            st = con.createStatement();//D:\FAST\Spring 2021\Advanced Programming\Project\foodapp_jsp
            err = "connected to db";
            String sql = "Select * from login_table";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {

                //err = err + rs.getString(1)+ "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\n";
                System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("failed to connect to db");
            err = e.getMessage();
        }
    }

    boolean add_users(String a, String b, String c, int age) {
        if (a.length() > 20 || b.length() < 4 || b.length() > 10) {
            return false;
        }

        try {
            String sql = "INSERT INTO login_table (Email, Password, Country, Age) VALUES ('" + a + "', '" + b + "', '" + c + "', '" + Integer.toString(age) + "');";
            System.out.println(sql);
            err = err + "</br>" + sql + "</br>";
            boolean rs = st.execute(sql);
            err = err + "insert successful</br>";
            return true;
        } catch (Exception e) {
            System.out.println(e);
            err = err + e.getMessage();
            return false;
        }
    }

    users login(String a, String b) {

        users usr = null;
        try {
            String sql = "SELECT * FROM login_table WHERE Email = '" + a + "' AND Password = '" + b + "';";
            err = sql;
            err = err + "</br>";
            ResultSet rs = st.executeQuery(sql);
            //err = err + "statement executed";
            //err = err + rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\n";
            if (rs.next()) {
                usr = new users(rs.getString(1), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)));
                err = err + "login record found";
            } else {
                err = err + "no data";
            }
            
            //usr = new users(rs.getString(1), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)));
            //err = err + "login record found";
            return usr;

        } catch (Exception e) {
            System.out.println(e);
            err = err + "</br>";
            err = err + e.getMessage();

            return usr;
        }

    }

}
