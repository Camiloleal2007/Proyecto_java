
package gestioncita;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    static String URL = "jdbc:mysql://127.0.0.1:3306/GestionCita";
    static String USUARIO = "Camiloleal000";
    static String CONTRASEÑA = "Camiloleal2007";
    public static Connection conectar() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        } catch (ClassNotFoundException | SQLException e) {
        }
        return con;
    }
}

