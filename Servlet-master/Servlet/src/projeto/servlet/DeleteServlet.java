package projeto.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import projeto.connection.ConnectionSingleton;
import projeto.dao.ProdutoDAO;
import projeto.model.Produto;
import projeto.utils.UtilsMessages;
import projeto.utils.UtilsProdutoDAO;

public class DeleteServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Produto produto = UtilsProdutoDAO.getProdutoPorId(req, resp);
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		out.append("<html>");
		out.append("<body>");
		out.append("<head>");
		out.append("<style>");
		
		out.append("table {border-collapse: collapse; width='100%'; }");
		out.append("td {border: 0px solid #dddddd;text-align: left;padding: 15px;}");
		out.append("label {text-align: left;padding: 8px; font-weight: bold; }");
		out.append("</style>");
		
		out.append("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>\n");
		out.append("<script>\n");
		out.append("	function onClick(){ \n");
		out.append("		var r = confirm('Confirmar exclusão?'); \n");
		out.append("	    if(r == false) { \n");
		out.append("	        window.location.href = 'index'; \n");
		out.append("	        return false;\n");
		out.append("	    } else {\n");
		out.append("	        return true;\n");
		out.append("	    } \n");
		out.append("	}\n");
		out.append("</script>");
		
		
		out.append("</head>");
		
		out.append("<form action='delete' method='post' onsubmit='return onClick()'>");
		out.append("<table align='center'>");
		out.append("<tr align='center'>");
		out.append("<th colspan='3'>Excluir</th>");
		out.append("</tr>");
		
		out.append("<tr>");
		out.append("<td>");
		out.append("<label>Código: </label>");
		out.append("</td>");
		out.append("<td>");
		out.append("<input type='text' name='codigo' size='5' readonly value='"+ produto.getCdProduto() +"' />");
		out.append("</td>");
		out.append("</tr>");
		
		out.append("<tr>");
		out.append("<td>");
		out.append("<label>Nome: </label>");
		out.append("</td>");
		out.append("<td>");
		out.append("<input type='text' name='nome' size='30' readonly value='"+ produto.getNmProduto() +"'/>");
		out.append("</td>");
		out.append("</tr>");
				
		out.append("<tr>");
		out.append("<td>");
		out.append("<label>Preço: </label>");
		out.append("</td>");
		out.append("<td>");
		out.append("<input type='number' min='0.01' step='0.01' name='preco' readonly value='"+ produto.getVlProduto() +"'/>");
		out.append("</td>");
		out.append("</tr>");
		
		out.append("<tr align='right'>");
		
		out.append("<th colspan='2'><button><a href='index' name='cancelar' >Cancelar</a></button>");
		out.append("<th colspan='3'><input type='submit' name='excluir' value='Excluir'/></th>");
		out.append("</tr>");
		
		out.append("</table>");
		out.append("</form>");
		out.append("</table>");
		
		out.append("</body>");
		out.append("</html>");

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Produto produto = UtilsProdutoDAO.getProduto(req, resp);
		ProdutoDAO dao = new ProdutoDAO(ConnectionSingleton.getInstance().getConnection());

		try {
			
			if (dao.delete(produto)) {
				UtilsMessages.messageRedirect(ControllerServlet.session, "sucesso", "Produto excluído com sucesso!", "erro", "index", resp);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			UtilsMessages.messageRedirect(ControllerServlet.session, "erro", "Erro! Um problema ocorreu ao tentar excluir o produto...Tente novamente!", "sucesso", "index", resp);
		}
			
	}	
	
}
