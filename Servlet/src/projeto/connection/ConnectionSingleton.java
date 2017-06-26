package projeto.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
	
	private static ConnectionSingleton instance;
	private Connection connection = null;
	private static final String DB = "mercado";
	private static final String USER = "root";
	private static final String PASS = "root";
	
	private ConnectionSingleton() {
		
		if (this.connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String con = "jdbc:mysql://localhost/" + DB;
				this.connection = DriverManager.getConnection(con, USER, PASS);
			} catch (ClassNotFoundException | SQLException e) {			
				e.printStackTrace();
				System.out.println("Erro ao abrir conexão com banco de dados!");
			}
		}
	}
	
	public static ConnectionSingleton getInstance() {		
		if (instance == null) {
			instance = new ConnectionSingleton();
		}		
		return instance;
	}
	
	public Connection getConnection() {			
		return connection;
	}
}
