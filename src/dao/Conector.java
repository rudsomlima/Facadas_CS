package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Conector {
	
	public Statement stm;
	//private String driver;
	static String url = "jdbc:mysql://localhost/CS";
	static String usuario = "root";
	static String senha = "";
	static Connection con;
	
	public static Connection ConexaoBD() throws SQLException {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			if(con == null) {
				con = DriverManager.getConnection(url, usuario, senha);
			}
			//JOptionPane.showMessageDialog(null, "Conexão feita com sucesso!");
			return con;
		}catch(ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
			}
	}
	
	public static void Desconecta(){
		try {
			con.close();
			JOptionPane.showMessageDialog(null, "Banco de dados desconetado com sucesso!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
