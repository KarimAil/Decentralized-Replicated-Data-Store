/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peer;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Karim
 */
class sqlConnection {

    private Connection con;
    private Statement stat;

    public sqlConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/M7vF5aLqw7", "M7vF5aLqw7", "w1d2IQCWpZ");
            stat = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }
    public ArrayList<String> getIps(String col , boolean flag) throws SQLException {
        ResultSet rs = null;
        ArrayList<String> reslt = new ArrayList<String>();
        String dat = "";
        try {
            String query = "select * from IPs";
            rs = stat.executeQuery(query);
            while (rs.next()) {
                dat = rs.getString("Col");
                reslt.add(dat);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        if(flag){
            try {
                stat.executeUpdate("UPDATE IPs SET new='" + "1" + "' ");
                stat.executeUpdate("INSERT INTO IPs (Col , new)" + "VALUES ('" + col + "','" + "0" + "')");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        con.close();
        return reslt;
    }

    public int uptodate(String col) throws SQLException {
        int bol =  0 ;
        try {
            String query = "select * from IPs where Col='"+col+"'";
            ResultSet rs = stat.executeQuery(query);
            rs.next();
            bol = Integer.valueOf(rs.getString("new"));
            if (bol == 1)
                stat.executeUpdate("UPDATE IPs SET new='" + "0" +"' WHERE Col ='"+col+"' ");
        } catch (Exception ex) {
            System.out.println(ex);
        }
        con.close();
        return bol ;
    }
}
