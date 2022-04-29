/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import db.DB;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Saulo
 */
public class Program {
    public static Connection conn = null;
    public static Statement st = null;
    public static ResultSet rs = null;
    /**
     * @param args the command line arguments
     */
    public static void exibirDados(){
          try{
          //Recuperar Dados
          conn = DB.getConncetion();
          st = conn.createStatement();//Criando Statemente que vai preparar a SQL          
          rs = st.executeQuery("select * from department"); // rs recebe a execução do sql
          while(rs.next()){
              //enquanto houver linhas na tabela faz...
              System.out.println(rs.getInt("Id")+", "+rs.getString("Name"));
              /*getInt captura referent a uma coluna que contem inteiros
                getString captura referente a uma coluna que contem Strings*/
}
      }catch(SQLException e){
         e.printStackTrace();
      }finally{
          DB.closeResultSet(rs);
          DB.closeStatement(st);
          DB.closeConnection();
      }
    }
    
    
    public static void main(String[] args) {
      exibirDados();
    
    }
    
}
