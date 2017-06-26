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

//página edit
public class EditServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Produto produto = getProdutoPorId(req, resp);
		
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
		
		out.append("<script>\n");
		out.append("	function onClick(){\n");
		out.append("		document.getElementsByName('cancelar').value = 'true';\n");				
		out.append("	}\n");
		out.append("</script>");
		
		
		out.append("</head>");
		
		out.append("<form action='edit' method='post'>");
		out.append("<table align='center'>");
		out.append("<tr align='center'>");
		out.append("<th colspan='3'>Editar</th>");
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
		out.append("<input type='text' name='nome' size='30' required='true' value='"+ produto.getNmProduto() +"'/>");
		out.append("</td>");
		out.append("</tr>");
				
		out.append("<tr>");
		out.append("<td>");		
		out.append("<label>Preço: </label>");
		out.append("</td>");
		out.append("<td>");
		out.append("<input type='number' min='0.01' step='0.01' name='preco' required='true' value='"+ produto.getVlProduto() +"'/>");
		out.append("</td>");
		out.append("</tr>");
		
		out.append("<tr align='right'>");	
		out.append("<th colspan='2'><button><a href='index' name='cancelar' javascipt='onCLick()'>Cancelar</a></button>");
		out.append("<th colspan='3'><input type='submit' value='Atualizar' /></th>");
		out.append("</tr>");
		
		out.append("</table>");
		out.append("</form>");
		out.append("</table>");
		
		out.append("</body>");
		out.append("</html>");
	}
	
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		Produto produto = getProduto(req, resp);	
		if (produto != null) {

			if (atualizar(produto)) {
				UtilsMessages.messageRedirect(ControllerServlet.session, "sucesso", "Atualizado com sucesso!", "erro", "index", resp);
			} else {
				UtilsMessages.messageRedirect(ControllerServlet.session, "erro", "Ops! Ocorreu um erro ao atualizar! Por favor, tente novamente...", "sucesso", "index", resp);				
			}
			
		}
	
	}
	

	/**
	 * Método responsável por montar um objeto do tipo produto a partir dos dados do request
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
    private Produto getProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Produto prod = new Produto();		
		
		try {
			
			prod.setCdProduto(Integer.parseInt(request.getParameter("codigo")));			
			prod.setNmProduto(request.getParameter("nome"));
			prod.setVlProduto(Double.parseDouble(request.getParameter("preco")));			
			
		} catch (NumberFormatException e) {
			prod = null;
			UtilsMessages.messageRedirect(ControllerServlet.session , "erro", "Ops! Ocorreu um erro inesperado!", "sucesso", "index", response);
		}
		return prod;
	}
    

    
	
	/**
	 * Método responsável por recuperar um Produto a partir de um Id
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	private Produto getProdutoPorId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		Produto produto = new Produto();
		ProdutoDAO dao = new ProdutoDAO(ConnectionSingleton.getInstance().getConnection());
		try {
			
				int codigo = Integer.parseInt(req.getParameter("codigo"));
				try {
					produto = dao.searchId(codigo);
					if (produto != null) {
						return produto;
					}
					UtilsMessages.messageRedirect(ControllerServlet.session ,"erro", "Ops! Produto não encontrado!", "sucesso", "index", resp);
					
				} catch (Exception e) {
					UtilsMessages.messageRedirect(ControllerServlet.session ,"erro", e.getMessage(), "sucesso", "index", resp);					
				}
		} catch (NumberFormatException e) {
			UtilsMessages.messageRedirect(ControllerServlet.session ,"erro", "Ops! Um erro de conversão inesperado ocorreu! Tente novamente...", "sucesso", "index", resp);			    
		}	
		return produto;
	}
	
	
	/**
	 * Método responsável por atualizar um novo objeto no banco de dados
	 * @param produto
	 * @return
	 */
	private boolean atualizar(Produto produto){
		
		ProdutoDAO dao = new ProdutoDAO(ConnectionSingleton.getInstance().getConnection());
		try {
			if(dao.update(produto)) {
				return true;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
}
