package projeto.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import projeto.connection.ConnectionSingleton;
import projeto.dao.ProdutoDAO;
import projeto.model.Produto;
import projeto.utils.UtilsMessages;


/** Página index*/
public class ControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static Map<String, String> session;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init - ControllerServlet");
		session = new HashMap<>();
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		List<Produto> produtos = getProdutos();
		
		
		out.append("<html>");
		out.append("<head>");
		
		out.append("<style>");
		
		out.append("body{font-family: arial, sans-serif;}");
		out.append("table {border-collapse: collapse; width='100%'; }");
		out.append("td, th {border: 1px solid #dddddd; text-align: left;  padding: 8px;}");
		out.append("tr:nth-child(even) {background-color: #dddddd;}");
		out.append("</style>");
		
		out.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>\n");
		out.append("<script>\n");
		out.append("$(document).ready(function(){ \n");
		out.append("     setTimeout(function(){$('#msg').html('');}, 2500)");
		out.append("});\n");
		out.append("</script>");
		
		out.append("</head>");
		
		out.append("<body>");
		
		out.append("<h2 align='center'>Supermecado</h2>");
		out.append("<table align='center'>");
		out.append("<tr>");
		out.append("<th colspan='3'>Lista de Produtos</th>");
		out.append("</tr>");
		
		out.append("<tr>");
		out.append("<th>Código</th>");
		out.append("<th>Nome</th>");
		out.append("<th>Preço</th>");
		out.append("<th>Editar</th>");
		out.append("<th>Excluir</th>");
		out.append("</tr>");
		 
		if (!produtos.isEmpty()) {
			for (Produto produto : produtos) {
				
				out.append("<tr>");
				out.append("<td>" + produto.getCdProduto() + "</td>");
				out.append("<td>" + produto.getNmProduto() + "</td>");
				out.append("<td>" + produto.getVlProduto() + "</td>");
				out.append("<td><button><a href='edit?codigo=" + produto.getCdProduto() +" '>Editar</a></button></td>");
				out.append("<td><button><a href='delete?codigo=" + produto.getCdProduto() +" '>Deletar</a></button></td>");
				out.append("</tr>");
			
			}
		}		
		
		out.append("<form action='register' method='get'>");
		out.append("<tr>");
		out.append("<td colspan='3'><input type='submit' value='cadastrar'/></td>");
		out.append("</tr>");
		out.append("<form>");
		
		out.append("</table>");
		
		out.append(UtilsMessages.showMessage(session));
		session = new HashMap<>();
		
		out.append("</body>");
		out.append("</html>");
			
	}
	
	
	
	
	/**
	 * Método responsável por recuperar a lista de produtos cadastrados no banco
	 */
	private List<Produto> getProdutos() {
		
		Connection c = ConnectionSingleton.getInstance().getConnection();
		if(c == null){
			UtilsMessages.message(session, "sucesso", "erro", "Erro ao conectar com banco de dados!");
			return null;
		}
		
		ProdutoDAO dao = new ProdutoDAO(c);
		List<Produto> produtos = new ArrayList<>();
		try {
			produtos = dao.selectList();
		} catch (Exception e) {
			e.getMessage();
		}	
		return produtos;
	}
	
	
}
