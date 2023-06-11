
package PostFeature;

import Display.Display;
import MainProgram.MainProgram;
import TraceBack.TraceBack;
import java.awt.AWTException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class PostFeature extends TraceBack{
    PostLinkedList<String> post = new PostLinkedList<String>();
    
    
    public TraceBack Main() throws InterruptedException, AWTException
    {
            String user = MainProgram.GlobalDataStore.username;
            makePostTable();
            PostLinkedList<String> p = randomFeed(user);
            String getInt = "";
            if(this.post.getFirst() == null)
                            this.post.insertPostList(p);
            bigLoop: while(true)
            {
                Display.displayWelcomeLines("Feed/Post Page", "Feed Page", " user");
                Display.displayUserOption("Display your Own Posts", "Post Something", "See What Other People are Up to");
                switch(getInt = MainProgram.sc.nextLine())
                {

                    case "1":
                        clearConsole();
                        Display.displayPostCaption(user);
                        System.out.println("\t\t\t\t\tHere are your posts!");
                        displayPostForUser(user);
                        Display.displayUserOption("Go Back","","");
                        String done = MainProgram.sc.nextLine();
                        while(true)
                        {
                            if(done.equalsIgnoreCase("0"))
                            {
                                Thread.sleep(250);
                                clearConsole();
                                break;
                            }
                            Display.displayUserOption("Go Back","","");
                        }
                        break;
                    case "2":
                        clearConsole();
                        Display.displayPostCaption(user);
                        System.out.println("Pleas Enter The Post: ");
                        String postContent = MainProgram.sc.nextLine();
                        addNewPost(user,postContent);
                        System.out.println("Post Has Been Successfully Made! Redirecting..");
                        Thread.sleep(250);
                        clearConsole();
                        break;
                    case "3":
                        clearConsole();
                        Display.displayPostCaption(user);
                        System.out.println("Here are Three Posts Other Users Posted:");
                        System.out.println("");
                        
                        
                        this.post.printPosts();

                        loop: while(true){
                            System.out.println("Display more?");
                            Display.displayUserOption("Yes", "Refresh the Feed", "");
                            System.out.println("\n\t\t\t\t\t-------------------------------------");
                            boolean flag = true;
                            switch(MainProgram.sc.nextLine()){
                                case "1": 
                                   this.post.printPosts();
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
                        System.out.println("\n\n");
                        System.out.println("--------------------------------------");
                        System.out.println("  Select an appropiate choice please !");
                        System.out.println("--------------------------------------");
                        Thread.sleep(250);
                        clearConsole();
                        break;
                    }
                }

            }
     
    
    
    public boolean searchPostTable(String row)
    {
        try{
            Statement stmt = MainProgram.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT post FROM Post,User WHERE Post.Username = User.Username AND User.Username ='"+ row +"'");

            if (rs.next()) 
                return true;
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void addNewPost(String username , String post)
    {
        String q = String.format("INSERT INTO Post (Username, post) VALUES ('%s', '%s')" , username, post);
        if(!MainProgram.db.searchTable("Username", username))
        {
            System.out.println("No such user is here");
            return;
        }
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
    
    public void displayPostForUser(String user)
    {
        try{
            Statement stmt = MainProgram.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Post where Username='"+user+"'");

            while (rs.next()) {
                String name = rs.getString("Username");
                String content = rs.getString("post");
                if(content == null)
                    continue;
                System.out.println(name);
                System.out.println("   "+content);
                System.out.println("--");
                System.out.println("");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public PostLinkedList<String> randomFeed(String user)
    {
        PostLinkedList<String> p = new PostLinkedList<String>();
        try{
            Statement stmt = MainProgram.connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Username, post FROM Post WHERE Username <> '"+ user +"'");

            while (rs.next()) {
                String name = rs.getString("Username");
                String content = rs.getString("post");
                if(content == null)
                    continue;
                p.addFirst(name, content);
            }
            p.shuffle();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return p;
    }
    
    public void makePostTable()
    {
        String q = "CREATE TABLE IF NOT EXISTS Post (Username varchar(255), Content MEDIUMTEXT);";
        boolean flag = true;
        try(PreparedStatement stmt = MainProgram.connection.prepareStatement(q)) 
        {
            int affected = stmt.executeUpdate(q);
        } 
        catch (SQLException e) {
            System.out.println("Erorr: "+e.getMessage());
        }
    }
    
    }
    
    

    
    
    
    
    