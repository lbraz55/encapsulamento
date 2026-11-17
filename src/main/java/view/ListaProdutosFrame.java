package view;

import controller.Controlador;
import model.Produto;
import javax.swing.*;

public class ListaProdutosFrame extends JFrame {
    private JList<String> produtosList;
    private Controlador controlador;

    public ListaProdutosFrame(Controlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setTitle("Lista de Produtos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        produtosList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(produtosList);
        scrollPane.setBounds(30, 30, 330, 200);
        add(scrollPane);

        refreshList();
    }

    public void refreshList() {
        Produto[] produtos = controlador.produtosCadastrados();
        String[] nomesProdutos = new String[produtos.length];
        for (int i = 0; i < produtos.length; i++) {
            nomesProdutos[i] = produtos[i].getNome()
                    + " | " + Formato.moeda(produtos[i].getPreco())
                    + " | estoque: " + produtos[i].getQuantidade();
        }
        produtosList.setListData(nomesProdutos);
    }
}
