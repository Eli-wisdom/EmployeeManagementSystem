/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author #Eli
 */
import java.sql.*;
import javax.swing.*;

public class connector {
    
     Connection conn = null;
    
    public static Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Empdb","root","");
            //JOptionPane.showMessageDialog(null,"CONNECTION SUCCESSFUL");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
            System.out.println("An error occure");
        }
            return null;
    }
   
}
