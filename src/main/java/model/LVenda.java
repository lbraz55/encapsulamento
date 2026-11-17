package model;

import java.util.ArrayList;
import java.util.List;

public class LVenda {
    private List<Venda> vendas;

    public LVenda() {
        this.vendas = new ArrayList<>();
    }

    public void adicionarVenda(Venda venda) {
        vendas.add(venda);
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    @Override
    public String toString() {
        return "LVenda{" +
                "vendas=" + vendas +
                '}';
    }
}
