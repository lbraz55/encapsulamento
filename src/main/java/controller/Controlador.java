package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.LVenda;
import model.Produto;
import model.ProdutoVendido;
import model.Venda;
import view.ListaProdutosFrame;
import view.LojaFrame;
import view.ProdutoFrame;
import view.VendaFrame;

public class Controlador {
    // Instâncias das classes de modelo (armazenamento em memória)
    private final List<Produto> produtos;
    private final LVenda vendas;
    private int proximoIdVenda;

    // Instâncias das classes de visualização
    private LojaFrame lojaFrame;
    private ProdutoFrame produtoFrame;
    private ListaProdutosFrame listaProdutosFrame;
    private VendaFrame vendaFrame;

    // Construtor
    public Controlador() {
        this.produtos = new ArrayList<>();
        this.vendas = new LVenda();
        this.proximoIdVenda = 1;
    }

    // Métodos do controlador
    public Produto[] estoque() {
        return produtos.toArray(new Produto[0]);
    }

    /**
     * Define a quantidade em estoque de um produto. O id do produto é a sua
     * posição na lista de produtos cadastrados.
     */
    public void postQuantidade(int idProduto, int quantidade) {
        if (idProduto < 0 || idProduto >= produtos.size()) {
            throw new IllegalArgumentException("Produto inexistente: " + idProduto);
        }
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa.");
        }
        produtos.get(idProduto).setQuantidade(quantidade);
    }

    /**
     * Registra a venda e dá baixa no estoque. A venda só é aceita se houver
     * estoque suficiente para todos os itens; caso contrário, nada é alterado.
     */
    public void venderProduto(Venda venda) {
        List<ProdutoVendido> itens = venda.getProdutosVendidos();
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("A venda precisa ter pelo menos um item.");
        }

        // O mesmo produto pode aparecer em mais de um item, então soma antes de validar
        Map<Produto, Integer> totalPorProduto = new HashMap<>();
        for (ProdutoVendido item : itens) {
            if (item.getQuantidadeVendida() <= 0) {
                throw new IllegalArgumentException("A quantidade vendida deve ser maior que zero.");
            }
            totalPorProduto.merge(item.getProduto(), item.getQuantidadeVendida(), Integer::sum);
        }
        for (Map.Entry<Produto, Integer> entrada : totalPorProduto.entrySet()) {
            Produto produto = entrada.getKey();
            if (entrada.getValue() > produto.getQuantidade()) {
                throw new IllegalStateException("Estoque insuficiente para " + produto.getNome()
                        + " (disponível: " + produto.getQuantidade() + ").");
            }
        }

        for (Map.Entry<Produto, Integer> entrada : totalPorProduto.entrySet()) {
            Produto produto = entrada.getKey();
            produto.setQuantidade(produto.getQuantidade() - entrada.getValue());
        }
        venda.setId(proximoIdVenda++);
        vendas.adicionarVenda(venda);
    }

    public void cadastrarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        produtos.add(produto);
    }

    public Produto[] produtosCadastrados() {
        return estoque();
    }

    public Venda[] vendasRealizadas() {
        return vendas.getVendas().toArray(new Venda[0]);
    }

    public void iniciarLoja() {
        // As telas são criadas aqui, e não no construtor, para que a lógica
        // possa ser usada (e testada) sem abrir nenhuma janela.
        this.lojaFrame = new LojaFrame(this);
        this.produtoFrame = new ProdutoFrame(this);
        this.listaProdutosFrame = new ListaProdutosFrame(this);
        this.vendaFrame = new VendaFrame(this);

        lojaFrame.setVisible(true);
    }

    // Métodos para abrir outras interfaces gráficas
    public void abrirProdutoFrame() {
        produtoFrame.setVisible(true);
    }

    public void abrirListaProdutosFrame() {
        listaProdutosFrame.refreshList();
        listaProdutosFrame.setVisible(true);
    }

    public void abrirVendaFrame() {
        vendaFrame.novaVenda();
        vendaFrame.setVisible(true);
    }
}
