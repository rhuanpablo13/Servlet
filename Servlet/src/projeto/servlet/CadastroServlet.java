package projeto.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import projeto.connection.ConnectionSingleton;
import projeto.dao.ProdutoDAO;
import projeto.model.Produto;
import projeto.utils.UtilsMessages;


/** Página register*/
public class CadastroServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static Map<String, String> session = new HashMap<>();

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		out.append("<html>");
		out.append("<body>");
		out.append("<head>");
		out.append("<style>");
		
		out.append("table {border-collapse: collapse; width='100%'; }");
		out.append("td {border: 0px solid #dddddd;text-align: left;padding: 15px;}");
		out.append("label {text-align: left;padding: 8px; font-weight: bold; }");
		out.append("</style>");
		out.append("</head>");
		
		out.append("<form action='register' method='post'>");
		out.append("<table align='center'>");
		out.append("<tr align='center'>");
		out.append("<th colspan='3'>Cadastro</th>");
		out.append("</tr>");
		
		out.append("<tr>");
		out.append("<td>");
		out.append("<label>Código: </label>");
		out.append("</td>");
		out.append("<td>");
		out.append("<input type='text' name='codigo' size='5' required='true'/>");
		out.append("</td>");
		out.append("</tr>");
		
		out.append("<tr>");
		out.append("<td>");
		out.append("<label>Nome: </label>");
		out.append("</td>");
		out.append("<td>");
		out.append("<input type='text' name='nome' size='30' required='true'/>");
		out.append("</td>");
		out.append("</tr>");
				
		out.append("<tr>");
		out.append("<td>");		
		out.append("<label>Preço: </label>");
		out.append("</td>");
		out.append("<td>");
		out.append("<input type='number' min='0.01' step='0.01' name='preco' required='true'/>");
		out.append("</td>");
		out.append("</tr>");
		
		out.append("<tr align='right'>");		
		out.append("<th colspan='2'><button><a href='index'>Listagem</a></button>");
		out.append("<th colspan='3'><input type='submit'name='cadastro' value='Cadastar' /></th>");
		out.append("</tr>");
		
		out.append("</table>");
		out.append("</form>");
		out.append("</table>");

		if (session != null && session.get("erro") != null)
			out.append("<div style='color:red; text-align-last:center; '>" + session.get("erro") + "</div>");
		else if (session != null && session.get("sucesso") != null)
			out.append("<div style='color:green; text-align-last: center; '>" + session.get("sucesso") + "</div>");
		
		out.append("</body>");
		out.append("</html>");
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		Produto prod = new Produto();
		try {
			prod.setCdProduto(Integer.parseInt(request.getParameter("codigo")));
			prod.setNmProduto(request.getParameter("nome"));
			prod.setVlProduto(Double.parseDouble(request.getParameter("preco")));
			
			try {
				if (cadastrar(prod)) {
					UtilsMessages.message(session, "erro", "sucesso", "Produto cadastrado com sucesso!");					
				}
			} catch (Exception e) {
				UtilsMessages.message(session, "sucesso", "erro", e.getMessage());
			}
			
		} catch (NumberFormatException e) {
			UtilsMessages.message(session, "sucesso", "erro", "Ops! Verifique se todos os campos estão corretos!");						
		}		
		response.sendRedirect("register");
	}
	
	
	private boolean cadastrar(Produto produto) throws Exception {
		Connection con = ConnectionSingleton.getInstance().getConnection();
		ProdutoDAO dao = new ProdutoDAO(con);		
		if (dao.insert(produto)) {
			return true;
		}
		return false;
	}
	
	

}
