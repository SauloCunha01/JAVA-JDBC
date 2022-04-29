/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import db.DB;
import db.DBException;
import db.DBIntegrityException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author Saulo
 */
public class Program {
    public static Connection conn = null;
    public static Statement st = null;
    public static PreparedStatement ps = null;
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
    
    public static void inserirDados(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
       conn = DB.getConncetion();
       //Preaparando SQL
       ps = conn.prepareStatement(
               "insert into seller"
               +"(Name, Email, BirthDate, BaseSalary, DepartmentId)"
               +"Values"
               +"(?,?,?,?,?)");
        //Adicionando mais um parametro na linha anterior ?), Statement.RETURN_GENERATED_KEYS); 
       //Povoando o caractere ? cada qual com seu tipo
       ps.setString(1, "Saulo Cunha");
       ps.setString(2, "saulocunha01@hotmail.com");
       ps.setDate(3, new java.sql.Date(sdf.parse("22/04/1995").getTime()));
       ps.setDouble(4, 3000.0);
       ps.setInt(5, 4);
       // ps.executeUpdate ao ser executada retorna um valor inteiro
       int rowsAffected = ps.executeUpdate();
       System.out.println("Rows Affected: "+rowsAffected);
       
        }catch(SQLException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }finally{
            DB.closeStatement(ps);
            DB.closeConnection();
        }
    }
    
    public static void atualizarDados(){
        try{
            conn  = DB.getConncetion();
            ps = conn.prepareStatement("UPDATE seller SET BaseSalary = BaseSalary + ? WHERE DepartmentId = ?");
            ps.setDouble(1, 200);
            ps.setInt(2,2);
           
            int rowsAffected = ps.executeUpdate();
            System.out.println("Done! Rows Affected: "+rowsAffected);
            
            
                
        }catch(SQLException e){
        e.printStackTrace();
    }finally{
            DB.closeStatement(ps);
            DB.closeConnection();
        }
        
    }
    
    public static void excluirDados(){
        /*falahas de integridade Inicial,  só vou poder apagar registros de
        departamentos que não estão em uso como chave estrageira*/
         try{
            conn  = DB.getConncetion();
            ps = conn.prepareStatement("Delete from department "
                    + "where "
                    + "Id= ?");
           ps.setInt(1,2);
           
            int rowsAffected = ps.executeUpdate();
            System.out.println("Done! Rows Affected: "+rowsAffected);
            
            
                
        }catch(SQLException e){
            throw new DBIntegrityException(e.getMessage());
    }finally{
            DB.closeStatement(ps);
            DB.closeConnection();
        }
    }
    
    public static void updateComIntegridade(){
        try {
			conn  = DB.getConncetion();
	
			conn.setAutoCommit(false);

			st = conn.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");

                        /*cauando uma excessão no meio da transação
			int x = 1;
                        if (x < 2) {
			throw new SQLException("Fake error");
			}
			*/
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			conn.commit();
			
			System.out.println("rows1 = " + rows1);
			System.out.println("rows2 = " + rows2);
		}
		catch (SQLException e) {
			try {
                            //caso ocorra algum erro de transação, vai voltar para a integridade inicial dos dados
				conn.rollback();
				throw new DBException("Transaction rolled back! Caused by: " + e.getMessage());
			} 
			catch (SQLException e1) {
				throw new DBException("Error trying to rollback! Caused by: " + e1.getMessage());
			}
		} 
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
    }
    
    public static void main(String[] args) {
    
      
        //excluirDados();
        updateComIntegridade();
    }
    
}
