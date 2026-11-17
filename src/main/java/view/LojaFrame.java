package view;

import controller.Controlador;
import model.Venda;
import javax.swing.*;

public class LojaFrame extends JFrame {
    private JButton fechaCaixaButton;
    private JButton listaProdutosButton;
    private JButton novoProdutoButton;
    private JButton novaVendaButton;
    private Controlador controlador;

    public LojaFrame(Controlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setTitle("Loja");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        fechaCaixaButton = new JButton("Fechar Caixa");
        fechaCaixaButton.setBounds(50, 50, 150, 30);
        add(fechaCaixaButton);

        listaProdutosButton = new JButton("Listar Produtos");
        listaProdutosButton.setBounds(50, 100, 150, 30);
        add(listaProdutosButton);

        novoProdutoButton = new JButton("Novo Produto");
        novoProdutoButton.setBounds(50, 150, 150, 30);
        add(novoProdutoButton);

        novaVendaButton = new JButton("Nova Venda");
        novaVendaButton.setBounds(50, 200, 150, 30);
        add(novaVendaButton);

        fechaCaixaButton.addActionListener(e -> fecharCaixa());
        listaProdutosButton.addActionListener(e -> controlador.abrirListaProdutosFrame());
        novoProdutoButton.addActionListener(e -> controlador.abrirProdutoFrame());
        novaVendaButton.addActionListener(e -> controlador.abrirVendaFrame());
    }

    private void fecharCaixa() {
        Venda[] vendas = controlador.vendasRealizadas();
        double total = 0;
        for (Venda venda : vendas) {
            total += venda.getTotal();
        }
        JOptionPane.showMessageDialog(this,
                "Vendas realizadas: " + vendas.length + "\nTotal vendido: " + Formato.moeda(total),
                "Fechamento de caixa", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
