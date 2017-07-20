package projeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import projeto.interfaces.DAO;
import projeto.model.Produto;

public class ProdutoDAO implements DAO<Produto>{

	private Connection connection = null;

	public ProdutoDAO(Connection con) {
		// TODO Auto-generated constructor stub
		this.connection = con;
	}


	@Override
	public boolean insert(Produto produto) throws Exception {
	
		if (produto != null) {
			
			String insert = "INSERT INTO Produto(cdProduto, nmProduto, vlPreco) values (?, ?, ?)";
			
			try {				
				PreparedStatement ps = this.connection.prepareStatement(insert);
				ps.setInt   (1, produto.getCdProduto());
				ps.setString(2, produto.getNmProduto());
				ps.setDouble(3, produto.getVlProduto());				
				if(ps.executeUpdate() == 1){
					ps.close();
					return true;
				}
				ps.close();
				return false;
				
			} catch (SQLException e) {
				if(e.getErrorCode() == 1062) { //MySQLIntegrityConstraintViolationException (duplicate key)
					e.printStackTrace();
					throw new Exception("Código: "+ produto.getCdProduto() +" já cadastrado no banco!");
				}
				e.printStackTrace();
				throw new Exception("Erro no método insert em: " + this.getClass());
			} 
			
		}
		return false;
	}


	
	@Override
	public boolean delete(Produto produto) throws Exception {
		
		if (produto != null) {
			
			String delete = "DELETE FROM Produto WHERE cdProduto = ?";
			try {				
				PreparedStatement ps = this.connection.prepareStatement(delete);
				ps.setInt(1, produto.getCdProduto());				
				if (ps.executeUpdate() == 1) {					
					return true;
				}
				ps.close();
				return false;
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("Erro no método delete em: " + this.getClass());
			}
		}		
		return false;
	}
	
	@Override
	public boolean update(Produto t) throws Exception {
		
		if (t != null) {
			try {
				String update = "UPDATE Produto SET nmProduto=?, vlPreco=? WHERE cdProduto=?";
				PreparedStatement ps = this.connection.prepareStatement(update);
				ps.setInt(3, t.getCdProduto());
				ps.setString(1, t.getNmProduto());
				ps.setDouble(2, t.getVlProduto());
				if (ps.executeUpdate() == 1) {					
					return true;
				}
				ps.close();
				return false;
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("Erro no método update em: " + this.getClass());
			}
		}	
		return false;
	}

	@Override
	public List<Produto> selectList() throws Exception {
		
		Produto p = null;
		List<Produto> produtos = null;
		String select = "SELECT * FROM Produto";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(select);
			ResultSet rs = ps.executeQuery();
			produtos = new ArrayList<>();
			while (rs.next()) {
				p = new Produto();
				p.setCdProduto(rs.getInt("cdProduto"));
				p.setNmProduto(rs.getString("nmProduto"));
				p.setVlProduto(rs.getDouble("vlPreco"));
				produtos.add(p);
			}
			ps.close();
		} catch (SQLException e) {		
			e.printStackTrace();
			throw new Exception("Erro no método selectList em: " + this.getClass());
		}		
		return produtos;
	}

	@Override
	public Produto searchOne(Produto t) throws Exception {

		String select = "SELECT * FROM Produto WHERE cdProduto = ? and nmProduto = ? and vlProduto = ?";
		Produto p = null;
		try {
			PreparedStatement ps = this.connection.prepareStatement(select);
			ps.setInt(1, t.getCdProduto());
			ps.setString(2, t.getNmProduto());
			ps.setDouble(3, t.getVlProduto());
			
			ResultSet rs = ps.executeQuery();			
			while (rs.next()) {
				p = new Produto();
				p.setCdProduto(rs.getInt("cdProduto"));
				p.setNmProduto(rs.getString("nmProduto"));
				p.setVlProduto(rs.getDouble("vlPreco"));
			}
			ps.close();
		} catch (SQLException e) {			
			e.printStackTrace();
			throw new Exception("Erro no método searchOne em: " + this.getClass());
		}
		return p;
	}	
	
	@Override
	public Produto searchId(int id) throws Exception {
		String select = "SELECT * FROM Produto WHERE cdProduto = ?";
		Produto p = null;
		try {
			PreparedStatement ps = this.connection.prepareStatement(select);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				p = new Produto();
				p.setCdProduto(rs.getInt("cdProduto"));
				p.setNmProduto(rs.getString("nmProduto"));
				p.setVlProduto(rs.getDouble("vlPreco"));
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Erro no método searchId em: " + this.getClass());
		}
		return p;
	}

	
}
