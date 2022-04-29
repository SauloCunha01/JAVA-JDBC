/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import db.DB;
import java.sql.Connection;
/**
 *
 * @author Saulo
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      Connection conn = DB.getConncetion();
      DB.closeConnection();
    }
    
}
