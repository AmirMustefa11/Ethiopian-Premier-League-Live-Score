
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;


public class Server extends UnicastRemoteObject implements FTable, STable, RTable, LogIn, Reg, NTable{
    Connection conn= null;
    String conString = "jdbc:mysql://localhost:3306/ethiopianpremierleague?zeroDateTimeBehavior=convertToNull";
    String username = "root";
    String password = "";
    public boolean register(String userN, String email, String passW) {
        String sql="INSERT INTO userreg(username,email,password) VALUES('"+userN+"','"+email+"','"+passW+"')";
        
        try{
            //get connection
            Connection con=DriverManager.getConnection(conString, username, password);
            
            //prepared statement
            Statement s = con.prepareStatement(sql);
            s.execute(sql);
            return true;
        }catch(Exception ex){
            System.out.println(ex);
            ex.printStackTrace();
        }
        return false;
    }
    @Override
    public int getLogin(String user, String pass)  throws RemoteException{
        
       
     
        try {
           Connection connection = DriverManager.getConnection("conString, username, password");
           PreparedStatement ps = connection.prepareStatement("SELECT `username`, `password` FROM `userreg` WHERE `username` = ? AND `password` = ?");
            ps.setString(1, user);
            ps.setString(2,pass);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                return 2; }
            else {
                return 3;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return 2;
    }
    @Override
     public DefaultTableModel getResult() throws Exception{
            DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("Date and Time");
        dm.addColumn("Team1");
        dm.addColumn("Result");
        dm.addColumn("Team2");
        
        //sqk statement
        String sql = "SELECT * FROM result";
        
        try{
            Connection con = DriverManager.getConnection(conString, username, password);
            
            //prepared statement
            Statement s = con.prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            
            //loop through getting all values
            while(rs.next()){
                //get values
                String date_time = rs.getString(1);
                String team1 = rs.getString(2);
                String result = rs.getString(3);
                String team2 = rs.getString(4);
                
                dm.addRow(new String[]{date_time,team1,result,team2});
                
            }
            
            return dm;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
   
    @Override
    public DefaultTableModel getNews() throws Exception{
            DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("Date and Time");
        dm.addColumn("Category");
        dm.addColumn("News");
       
        
        //sqk statement
        String sql = "SELECT * FROM news";
        
        try{
            Connection con = DriverManager.getConnection(conString, username, password);
            
            //prepared statement
            Statement s = con.prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            
            //loop through getting all values
            while(rs.next()){
                //get values
                String date_time = rs.getString(1);
                String category = rs.getString(2);
                String news = rs.getString(3);
                
                
                dm.addRow(new String[]{date_time,category,news});
                
            }
            
            return dm;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
   
    public Server() throws RemoteException{
        super();
    }
    @Override
    public DefaultTableModel getFixture() throws Exception{
        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("Date and Time");
        dm.addColumn("Team1");
        dm.addColumn("vs");
        dm.addColumn("Team2");
      
        
        //sqk statement
        String sql = "SELECT * FROM fixtures";
        
        try{
            Connection con = DriverManager.getConnection(conString, username, password);
            
            //prepared statement
            Statement s = con.prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            
            //loop through getting all values
            while(rs.next()){
                //get values
                String date_time = rs.getString(1);
                String team1 = rs.getString(2);
                String vs = rs.getString(3);
                String team2 = rs.getString(4);
                
                dm.addRow(new String[]{date_time,team1,vs,team2});
                
            }
            
            return dm;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args){
        try{
            Registry reg = LocateRegistry.createRegistry(8000);
            reg.rebind("EthioLeague", new Server());
            System.out.println("Server started");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public DefaultTableModel getStanding() throws Exception{
          //add columns to table model
        DefaultTableModel dm = new DefaultTableModel();
        dm.addColumn("Position");
        dm.addColumn("Team");
        dm.addColumn("Played");
        dm.addColumn("Win");
        dm.addColumn("Draw");
        dm.addColumn("Lose");
        dm.addColumn("Goal");
        dm.addColumn("Point");
        dm.addColumn("Form");
        //sqk statement
        String sql = "SELECT * FROM standings";
        
        try{
            Connection con = DriverManager.getConnection(conString, username, password);
            
            //prepared statement
            Statement s = con.prepareStatement(sql);
            ResultSet rs = s.executeQuery(sql);
            
            //loop through getting all values
            while(rs.next()){
                //get values
                String Position= rs.getString(1);
                String Team= rs.getString(2);
                String Played= rs.getString(3);
                String Win= rs.getString(4);
                String Draw= rs.getString(5);
                String Lose= rs.getString(6);
                String Goal= rs.getString(7);
                String Point= rs.getString(8);
                String Form= rs.getString(9);
                
                dm.addRow(new String[]{Position, Team, Played, Win, Draw, Lose, Goal, Point, Form});
                
            }
            
            return dm;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
