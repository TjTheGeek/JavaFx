package persistence;

import Model.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class StaffDAO {

    public static boolean validUser(String name, String password) {
        boolean status = false;


        try {
            Connection con = persistence.DBMySQL.getInstance().getConnection();
            String select = "select * from Staff where UserName=? and UserPass=?";
            PreparedStatement selectStatement = con.prepareStatement(select);
            selectStatement.setString(1,name);
            selectStatement.setString(2,password);
            ResultSet rs = selectStatement.executeQuery();
            status=rs.next();
            con.close();
        } catch (Exception e) {
        } catch (SQLException e) {
            System.out.println(e);
        }

        return status;
    }//if username 'AND' password are in the db
    public static boolean checkIfExists(String userName) {
        boolean status = false;

        try {
            Connection con = persistence.DBMySQL.getInstance().getConnection();
            String select = "select * from Staff where UserName=?";
            PreparedStatement st = con.prepareStatement(select);
            st.setString(1, userName);
            status = st.execute();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return status;

    }//checks if username is in the database

    private static int lockAttempts(String userName) throws SQLException {
       int attempts=0;
        Connection con = persistence.DBMySQL.getInstance().getConnection();
        if(con==null){
            System.out.println("Error");
        }
       try {
            String selectSt="Select * from Staff where UserName=?";
            PreparedStatement st = con.prepareStatement(selectSt);
            st.setString(1,userName);
            ResultSet rs=st.executeQuery();
            attempts=rs.getInt("LockAttempt");//this denies them access
            con.close();
            System.out.println(attempts);
        }finally {
           try {
               con.close();
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
       }
     return attempts;
    }//number of attempts

    public static boolean isLockAccount(String username){
        boolean accountStatus=true;
        String lockedState="LOCKED";
        try{
            Connection con = persistence.DBMySQL.getInstance().getConnection();
            String select = "select * from Staff where UserName=? and Locked=?";
            PreparedStatement selectStatement = con.prepareStatement(select);
            selectStatement.setString(1,username);
            selectStatement.setString(2,lockedState);
            ResultSet rs = selectStatement.executeQuery();
            accountStatus=rs.next();
           //if it returns a user then its locked
            con.close();

        }catch (Exception e){
                System.out.println(e);
        }
      /*  try{
            if(LockAttempts(username)>0){
                accountStatus=false;
            }
            else{
                lockedState="LOCKED";
                Connection con = persistence.DBMySQL.getInstance().getConnection();
                String update = "UPDATE Staff set Locked=? where UserName=?";
                PreparedStatement updateStatement = con.prepareStatement(update);
                updateStatement.setString(1,lockedState);
                updateStatement.setString(2,username);
                updateStatement.executeUpdate();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Updateing Data");
        }*/
        return accountStatus;
    }//Locked account

    public static String updateLockedData(String userName, String lockedState){
        String msg;
        try{
            Connection con = persistence.DBMySQL.getInstance().getConnection();
            String updateSt= "UPDATE Staff set Locked=? where UserName=?";
            PreparedStatement st = con.prepareStatement(updateSt);
            st.setString(1,lockedState);
            st.setString(2,userName);
            st.execute();

            if(lockedState.equals("UNLOCKED")){
                int LockAttempt =10;
                updateSt = "UPDATE Staff set LockAttempt=? where UserName=?";
                st = con.prepareStatement(updateSt);
                st.setInt(1,LockAttempt);
                st.setString(2,userName);
            }
            con.close();
            msg = "Database updated";
            System.out.println("dbupdated");
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Updateing Data");
            msg = "Error on Updating Database";
        }
        return msg;
    }

    public static String deleteUser(String userName){
        String msg;
        try{
            Connection con = persistence.DBMySQL.getInstance().getConnection();
            String updateSt= "delete staff from staff where UserName=?";
            PreparedStatement st = con.prepareStatement(updateSt);
            st.setString(1,userName);
            st.execute();
            msg ="Database updated";
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Error on Updateing Data");
            msg = "Error on Updating Database";
        }
        return msg;
    }

    public static String updateAttempts(String username){
        String msg="";
        if (!isLockAccount(username)){
            try{
                msg="Password is wrong";
                Connection con = persistence.DBMySQL.getInstance().getConnection();
                String update = "UPDATE Staff set LockAttempt=LockAttempt-1 where UserName=?";
                PreparedStatement selectStatement = con.prepareStatement(update);
                selectStatement.setString(1,username);
                selectStatement.executeUpdate();
                if(lockAttempts(username)<1){
                    updateLockedData(username,"LOCKED");
                    System.out.println("Lock ran");
                }
                con.close();
            }
            catch(Exception e){
                e.printStackTrace();
                msg="Error on Updateing Data";
            }
            }
        else {
            msg="Account Locked please contact admin";
        }
        return  msg;
    }//decrements the attempts 10>>0


    public static String updateRoleData(String username, String role){
        String msg;
        try {
            Connection con = persistence.DBMySQL.getInstance().getConnection();
            String sql1 = "UPDATE Staff set role=? where UserName=?";
            PreparedStatement st = con.prepareStatement(sql1);
            st.setString(1, role);
            st.setString(2, username);
            st.executeUpdate(sql1);
            msg = "Database updated";
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Error on Updateing Data");
            msg = "Error on Updating Database";
        }
        return msg;
    }

















    public static String getRole(String name, String password) {
        String role = "";
        System.out.println("UserName: "+name);
        try {
            Connection con = persistence.DBMySQL.getInstance().getConnection();
            String select = "select * from Staff where UserName=? and UserPass=?";
            PreparedStatement selectStatement = con.prepareStatement(select);
            selectStatement.setString(1,name);
            selectStatement.setString(2,password);
            ResultSet rs = selectStatement.executeQuery();
            rs.next();
            role = rs.getString("Role");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
            role = "Exception";
        }
        return role;

    }


    public static int addUser(String userName, String userPass, String userPin, String userEmail) {
        int status=2;
        try {
            String role = "User";
            Connection con = persistence.DBMySQL.getInstance().getConnection();
            String st="insert into Staff(UserName,UserPass,Role,Pin,Email) values(?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(st);
            ps.setString(1, userName);
            ps.setString(2, userPass);
            ps.setString(3, role);
            ps.setString(4, userPin);
            ps.setString(5, userEmail);
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;

    }//used to create a new user object

    /**/
    public static ObservableList<Staff> getAllStaff(){
        ObservableList<Staff> data= FXCollections.observableArrayList();//array holds the objects from the database
        try{
            String SQL = "Select * from staff";
            Connection con = persistence.DBMySQL.getInstance().getConnection();
            ResultSet rs = con.createStatement().executeQuery(SQL);
            while(rs.next()){
                String UserName = rs.getString("UserName");
                String Role = rs.getString("Role");
                String Locked = rs.getString("Locked");
                String LockAttempt = String.valueOf(rs.getInt("LockAttempt"));
                Staff cm = new Staff(UserName,Role,Locked,LockAttempt);
                data.add(cm);//add the data user into the array list
            }

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }finally {
            return data;//sent out that arraylist
        }
    }//all the staff in the database in an array



    /*public static String deleteUser(String userName, String clause) {
        String status = "";
        try (Connection con =persistence.DBMySQL.getInstance().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("delete from Staff where UserName='?'")) {
                ps.setString(1, clause);
                ps.setString(2, userName);
                ps.executeUpdate();
                status = "Database updated";
                System.out.println("database should update");
            } catch (SQLException e) {
                //  e.printStackTrace();
                e.getErrorCode();
                System.out.println("error");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error on Updateing Data");
            status= "Error on Updating Database";
        }
        return status;
    }
*/
}
