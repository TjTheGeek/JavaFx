package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DBMySQL {

    private static DBMySQL firstInstance = null;
    private static Connection con;
    public static Statement stmt;

    final private static String user = "root";
    final private static String pwd = "moneyWay0805";
    final private String db_name = "project";
    final private String connection = "jdbc:mysql://localhost:3306/"+db_name;

    //prevents users from Instaniation
    private DBMySQL() {
    }

    protected static DBMySQL getInstance() {
        if (firstInstance == null) {
            firstInstance = new DBMySQL();
        }
        return firstInstance;
    }

    protected Connection getConnection() throws SQLException {

        if (con == null || con.isClosed()) {
            try {

                Properties props = new Properties();
                props.put("user", user);
                props.put("password", pwd);
                props.put("useUnicode", "true");
                props.put("useServerPrepStmts", "false"); // use client-side prepared statement
                props.put("characterEncoding", "UTF-8"); // ensure charset is utf8 here

                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(connection, props);
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
        }
        return con;
    }
}