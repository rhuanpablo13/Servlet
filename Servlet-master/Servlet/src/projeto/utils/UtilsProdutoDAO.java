package projeto.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import projeto.connection.ConnectionSingleton;
import projeto.dao.ProdutoDAO;
import projeto.model.Produto;
import projeto.servlet.ControllerServlet;

public class UtilsProdutoDAO {

	
	/**
	 * Método responsável por montar um objeto do tipo produto a partir dos dados do request
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
    public static Produto getProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Produto prod = new Produto();
		
		try {
			
			prod.setCdProduto(Integer.parseInt(request.getParameter("codigo")));
			prod.setNmProduto(request.getParameter("nome"));
			prod.setVlProduto(Double.parseDouble(request.getParameter("preco")));
			
		} catch (NumberFormatException | NullPointerException e) {	
			UtilsMessages.messageRedirect(ControllerServlet.session , "erro", "Ops! Ocorreu um erro inesperado ao recuperar o produto!", "sucesso", "index", response);
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
	public static Produto getProdutoPorId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
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
	
	
}
