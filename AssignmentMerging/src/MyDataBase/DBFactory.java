/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyDataBase;

import java.sql.*;
import MainProgram.MainProgram;

/**
 * 
 * @author HuSSon
 */
public class DBFactory {
    
    public void columnAdder(String nameOfColumn, String typeOfTable)
    {
        if(checkTable(nameOfColumn))
        {
            return;
        }
        String q = "ALTER TABLE User ADD " + nameOfColumn + " " + typeOfTable ;
        try(PreparedStatement stmt = MainProgram.connection.prepareStatement(q)) 
        {
            
            int affected = stmt.executeUpdate(q);
            // System.out.println(affected);  debugging
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }           
    }
    
    // checks if the table has a specified column
    
    public boolean checkTable(String nameOfColumn)
    {
        
        boolean flag = false;
        try{
            DatabaseMetaData md = MainProgram.connection.getMetaData();
            ResultSet rs = md.getColumns(null, null, "User", nameOfColumn);
            if (rs.next()) {
                flag = true;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return flag;
    }
    
    
    // this function to reduce redudency in the setup account class - can be used in other classes if wanted.
    public void updateTable(String column ,String updated, String username)
    {
        
        String q = "UPDATE User SET " +column +  " = '" + updated + "' WHERE Username = '" + username +"'";
        try(PreparedStatement stmt = MainProgram.connection.prepareStatement(q)) 
        {
            int affected = stmt.executeUpdate(q);
            if(affected < 1)
            {
                System.out.println("Nothing was updated, Check your username and reEnter!");
            }
            
        } 
        catch (SQLException e) {
            System.out.println("Erorr: "+e.getMessage());
        }
    }
    
    // Overloadin function for types integer.
    public void updateTable(String column ,int updated, String username)
    {
        
        String q = "UPDATE User SET " + column +  " = '" + updated + "' WHERE Username = '" + username +"'";
        try(PreparedStatement stmt = MainProgram.connection.prepareStatement(q)) 
        {
            int affected = stmt.executeUpdate(q);
            if(affected < 1)
            {
                System.out.println("Nothing was updated, Check your username and reEnter!");
            }
            
        } 
        catch (SQLException e) {
            System.out.println("Erorr: "+e.getMessage());
        }
    }
    
    public boolean searchTable(String column, String row)
    {
        try{
            Statement stmt = MainProgram.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User where Username='"+row+"'");

            if (rs.next()) {
                return true;
            }
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    
    public void addNewUser(String username)
    {
        String q = String.format("INSERT INTO User (Username) VALUES ('%s')" , username);
        try(PreparedStatement stmt = MainProgram.connection.prepareStatement(q)) 
        {
            int affected = stmt.executeUpdate(q);
            if(affected < 1)
            {
                System.out.println("Nothing was updated, Check your username and reEnter!");
            }
            
        } 
        catch (SQLException e) {
            System.out.println("Erorr: "+e.getMessage());
        }
    }
   
}
