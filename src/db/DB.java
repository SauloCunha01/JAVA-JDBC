/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;
import java.sql.*;
/**
 *
 * @author Saulo
 */
public class DB {
    
    private static Connection conn = null;
    
    public static Connection getConncetion(){
        if(conn ==null){
           try{
               conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coursejdbc", "root", "");
           }catch(SQLException e){
               throw  new DBException(e.getMessage());
           }  
        }
        return conn;
    }
    
    public static Connection closeConnection(){
        if(conn == null){
            try{
                conn.close();
            }catch(SQLException e){
                throw  new DBException(e.getMessage());
            }
        }
        return conn;
    }
    public static void closeStatement(Statement st){
        if(st != null){
            try{
                 st.close();
            }catch(SQLException e){
                throw new DBException(e.getMessage());
            }
        }
    }
     public static void closeResultSet(ResultSet rs){
        if(rs != null){
            try{
                 rs.close();
            }catch(SQLException e){
                throw new DBException(e.getMessage());
            }
        }
    }
}
