import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/BDTest";
            con = DriverManager.getConnection(url, "root", "");
            System.out.println("Connexion MySQL établie !");
        } catch (Exception e) {
            System.out.println("Erreur MySQL");
            e.printStackTrace();
        }
        return con;
    }
}
