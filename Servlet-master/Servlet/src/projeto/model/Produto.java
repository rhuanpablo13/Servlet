package projeto.model;

public class Produto {
	
	private int cdProduto;
	private String nmProduto;
	private double vlProduto;
	
	
	public Produto() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Produto(int cdProduto, String nmProduto, double vlProduto) {
		// TODO Auto-generated constructor stub
		this.cdProduto = cdProduto;
		this.nmProduto = nmProduto;
		this.vlProduto = vlProduto;
	}
	
	
	
	
	@Override
	public String toString() {
		return "\tProduto [\tcdProduto=" + cdProduto + ", \tnmProduto=" + nmProduto
				+ ", \tvlProduto=" + vlProduto + "]";
	}


	public int getCdProduto() {
		return cdProduto;
	}
	public void setCdProduto(int cdProduto) {
		this.cdProduto = cdProduto;
	}
	public String getNmProduto() {
		return nmProduto;
	}
	public void setNmProduto(String nmProduto) {
		this.nmProduto = nmProduto;
	}
	public double getVlProduto() {
		return vlProduto;
	}
	public void setVlProduto(double vlProduto) {
		this.vlProduto = vlProduto;
	}
	
	
}
