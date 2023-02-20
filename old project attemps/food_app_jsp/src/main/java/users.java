
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
        this.Email = a;
        this.Password = b;
        this.Country = c;
        this.age = age;
        db = new DB_Connection();
        Success = db.add_users(a, b, c, age);
    }

    public users(String a, String b) {
        db = new DB_Connection();
        users usr = null;
        usr = db.login(a, b);
        if (usr != null) {
            Success = true;
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
            con = DriverManager.getConnection("jdbc:ucanaccess://D:\\FAST\\Spring 2021\\Advanced Programming\\Project\\foodapp_jsp\\foodapp_jsp_db");
            st = con.createStatement();//D:\FAST\Spring 2021\Advanced Programming\Project\foodapp_jsp

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    boolean add_users(String a, String b, String c, int age) {
        if (a.length() > 20 || b.length() < 4 || b.length() > 10) {
            return false;
        }

        try {
            String sql = "INSERT INTO login_table (Email, Password, Country, Age) VALUES ('" + a + "', '" + b + "', '" + c + "', '" + Integer.toString(age) + "');";
            System.out.println(sql);
            boolean rs = st.execute(sql);
            if (rs == true) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    users login(String a, String b) {

        users usr = null;
        try {
            String sql = "Select * from login_table WHERE email= '" + a + "', '" + b + "');";
            ResultSet rs = st.executeQuery(sql);

            int ag = Integer.parseInt(rs.getString(4));
            usr = new users(rs.getString(1), rs.getString(2), rs.getString(3), ag);
            return usr;

        } catch (Exception e) {
            System.out.println(e);
            return usr;
        }

    }

}
