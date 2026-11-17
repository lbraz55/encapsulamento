package model;

public class ProdutoVendido {
    private Produto produto;
    private int quantidadeVendida;
    private double precoTotal;

    public ProdutoVendido(Produto produto, int quantidadeVendida) {
        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
        this.precoTotal = produto.getPreco() * quantidadeVendida;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    @Override
    public String toString() {
        return "ProdutoVendido{" +
                "produto=" + produto +
                ", quantidadeVendida=" + quantidadeVendida +
                ", precoTotal=" + precoTotal +
                '}';
    }
}
