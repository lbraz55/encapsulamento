package model;

import java.util.List;

public class Venda {
    private int id;
    private List<ProdutoVendido> produtosVendidos;
    private double total;

    public Venda(int id, List<ProdutoVendido> produtosVendidos, double total) {
        this.id = id;
        this.produtosVendidos = produtosVendidos;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ProdutoVendido> getProdutosVendidos() {
        return produtosVendidos;
    }

    public void setProdutosVendidos(List<ProdutoVendido> produtosVendidos) {
        this.produtosVendidos = produtosVendidos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", produtosVendidos=" + produtosVendidos +
                ", total=" + total +
                '}';
    }
}
