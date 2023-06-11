/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Scanner;
import com.mycompany.project.DB.*;
import java.awt.AWTException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *
 * @author HuSSon
 */
public class PostFeature extends TraceBack{
    PostLinkedList<String> post = new PostLinkedList<String>();

    
    public TraceBack Main() throws InterruptedException, AWTException
    {
            String user = MainProgram.GlobalDataStore.username;
            PostFeature postFeature = new PostFeature();
            postFeature.makePostTable();
            Scanner i = new Scanner(System.in);
            
            DBFactory db = new DBFactory();
            //for testing purposes use Ahmed for now as user
            PostLinkedList<String> p = randomFeed(user);
            String getInt = "";
            bigLoop: while(true)
            {
                System.out.println("****************");
                System.out.println("*   MockBook   *      " + user);
                System.out.println("****************\n\n");
                System.out.println("\t\t\t\t\t-------------------------------------\t\t");
                System.out.println("\t\t\t\t\t|              Main Page            |");
                System.out.println("\t\t\t\t\t-------------------------------------\t\t");
                System.out.println("\n\t\t This is The Feed Page! You Will be Presented With Mulitiple Options you May Choose From Them!");
                System.out.println("\n\t\t\t\t\t-------------------------------------\t\t");
                System.out.println("Type 1 to Display your own posts");
                System.out.println("Type 2 to What's on your mind? Post something!");
                System.out.println("Type 3 to See What other people are up to (Global)");
                System.out.println("Type 0 to Exit Feed and Go Back to The Main Page");
                System.out.println("\t\t\t\t\t-------------------------------------\t\t");
                boolean secondFlag = false;
                
                switch(getInt = i.nextLine())
                {

                    case "1":
                        clearConsole();
                        System.out.println("****************");
                        System.out.println("*   MockBook   *      " + user);
                        System.out.println("****************\n\n");
                        System.out.println("\t\t\t\t\t-------------------------------------\t\t\n");
                        System.out.println("\t\t\t\t\tHere are your posts!");
                        displayPostForUser(user);
                        System.out.println("Finished Reading? Press 1 to Go Back!");
                        String done = i.nextLine();
                        while(true)
                        {
                            if(done.equalsIgnoreCase("1"))
                            {
                                Thread.sleep(250);
                                clearConsole();
                                break;
                            }
                            System.out.println("Press 1 To Go Back!");
                        }
                        break;
                    case "2":
                        clearConsole();
                        System.out.println("****************");
                        System.out.println("*   MockBook   *      " + user);
                        System.out.println("****************\n\n");
                        System.out.println("\t\t\t\t\t-------------------------------------");
                        System.out.println("Pleas Enter The Post: ");
                        String postContent = i.nextLine();
                        addNewPost(user,postContent);
                        System.out.println("Post Has Been Successfully Made! Redirecting..");
                        Thread.sleep(250);
                        clearConsole();
                        break;
                    case "3":
                        clearConsole();
                        System.out.println("****************");
                        System.out.println("*   MockBook   *      " + user);
                        System.out.println("****************\n\n");
                        System.out.println("\t\t\t\t\t-------------------------------------");
                        System.out.println("Here are Three Posts Other Users Posted");
                        System.out.println("");
                        if(this.post.getFirst() == null)
                            this.post.insertPostList(p);
                        
                        this.post.printfirst();

                        loop: while(true){
                            System.out.println("Display more?");
                            System.out.println("1: Yes");
                            System.out.println("2: Refresh the Feed");
                            System.out.println("0: No Go back");
                            System.out.println("\n\t\t\t\t\t-------------------------------------");
                            boolean flag = true;
                            switch(i.nextLine()){
                                case "1": 
                                   this.post.printthree(3);
                                   break;
                                case "2":
                                    this.post.clear();
                                    if(this.post.getFirst() == null)
                                        this.post.insertPostList(p);
                                case "0":
                                    flag = false;
                                    break;
                                default:
                                    System.out.println("This is NOT One of The Choices Given! Type Again :D");
                                    break;
                            }
                            if(flag == false){
                                clearConsole();
                                break;

                            }
                        }
                            break;

                    case "0":
                        clearConsole();
                        this.isPrevious = true;
                        return this.previous;
                    default:
                        clearConsole();
                        System.out.println("\n\n\n");
                        System.out.println("--------------------------------------");
                        System.out.println("  Select an appropiate choice please !");
                        System.out.println("--------------------------------------");
                        break;
                    }
                }

            }
     
    
    
    public boolean searchPostTable(String row)
    {
        Connection connection = MyDataBase.establishConnection();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT post FROM Post,User WHERE Post.Username = User.Username AND User.Username ='"+ row +"'");

            if (rs.next()) 
                return true;
            connection.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public void addNewUserToPost(String username)
    {
        Connection connection = MyDataBase.establishConnection();
        String q = String.format("INSERT INTO Post (Username) VALUES ('%s')" , username);
        try(PreparedStatement stmt = connection.prepareStatement(q)) 
        {
            int affected = stmt.executeUpdate(q);
            if(affected < 1)
            {
                System.out.println("Nothing was updated, Check your username and reEnter!");
            }
            connection.close();
        } 
        catch (SQLException e) {
            System.out.println("Erorr: "+e.getMessage());
        }
    }
    
    public void addNewPost(String username , String post)
    {
        DBFactory db = new DBFactory();
        Connection connection = MyDataBase.establishConnection();
        String q = String.format("INSERT INTO Post (Username, post) VALUES ('%s', '%s')" , username, post);
        if(!db.searchTable("Username", username))
        {
            System.out.println("No such user is here");
            return;
        }
        try(PreparedStatement stmt = connection.prepareStatement(q)) 
        {
            int affected = stmt.executeUpdate(q);
            if(affected < 1)
            {
                System.out.println("Nothing was updated, Check your username and reEnter!");
            }
            connection.close();
        } 
        catch (SQLException e) {
            System.out.println("Erorr: "+e.getMessage());
        }
    }
    
    public void displayPostForUser(String user)
    {
        Connection connection = MyDataBase.establishConnection();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Post where Username='"+user+"'");

            while (rs.next()) {
                String name = rs.getString("Username");
                String content = rs.getString("post");
                if(content == null)
                    continue;
                System.out.println("*****");
                System.out.println(name);
                System.out.println(content);
                System.out.println("*****");
                System.out.println("");
            }
            connection.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public PostLinkedList<String> randomFeed(String user)
    {
        Connection connection = MyDataBase.establishConnection();
        PostLinkedList<String> p = new PostLinkedList<String>();
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Username, post FROM Post WHERE Username <> '"+ user +"'");

            while (rs.next()) {
                String name = rs.getString("Username");
                String content = rs.getString("post");
                if(content == null)
                    continue;
                p.addFirst(name, content);
            }
            p.shuffle();
            connection.close();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return p;
    }
    
    public void makePostTable()
    {
        Connection connection = MyDataBase.establishConnection();
        String q = "CREATE TABLE IF NOT EXISTS Post (Username varchar(255), Content varchar(2000));";
        boolean flag = true;
        try(PreparedStatement stmt = connection.prepareStatement(q)) 
        {
            int affected = stmt.executeUpdate(q);
            connection.close();
        } 
        catch (SQLException e) {
            System.out.println("Erorr: "+e.getMessage());
        }
    }
    
    }
    
    

    
    
    
    
    
