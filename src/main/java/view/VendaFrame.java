package view;

import java.util.ArrayList;
import java.util.List;

import controller.Controlador;
import model.Produto;
import model.ProdutoVendido;
import model.Venda;
import javax.swing.*;

public class VendaFrame extends JFrame {
    private JComboBox<String> produtoComboBox;
    private JSpinner quantidadeSpinner;
    private JButton adicionarButton;
    private DefaultListModel<String> itensListModel;
    private JTextField totalTextField;
    private JButton okButton;
    private JButton cancelarButton;
    private Controlador controlador;

    // Produtos exibidos no combo (mesma ordem) e itens da venda em andamento
    private Produto[] produtosDisponiveis = new Produto[0];
    private final List<ProdutoVendido> itens = new ArrayList<>();

    public VendaFrame(Controlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setTitle("Registrar Venda");
        setSize(420, 380);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel produtoLabel = new JLabel("Produto:");
        produtoLabel.setBounds(30, 30, 80, 25);
        add(produtoLabel);

        produtoComboBox = new JComboBox<>();
        produtoComboBox.setBounds(110, 30, 270, 25);
        add(produtoComboBox);

        JLabel quantidadeLabel = new JLabel("Quantidade:");
        quantidadeLabel.setBounds(30, 70, 80, 25);
        add(quantidadeLabel);

        quantidadeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        quantidadeSpinner.setBounds(110, 70, 80, 25);
        add(quantidadeSpinner);

        adicionarButton = new JButton("Adicionar item");
        adicionarButton.setBounds(230, 70, 150, 25);
        add(adicionarButton);

        itensListModel = new DefaultListModel<>();
        JScrollPane itensScrollPane = new JScrollPane(new JList<>(itensListModel));
        itensScrollPane.setBounds(30, 110, 350, 130);
        add(itensScrollPane);

        JLabel totalLabel = new JLabel("Total:");
        totalLabel.setBounds(30, 255, 80, 25);
        add(totalLabel);

        totalTextField = new JTextField();
        totalTextField.setEditable(false);
        totalTextField.setBounds(110, 255, 150, 25);
        add(totalTextField);

        okButton = new JButton("OK");
        okButton.setBounds(30, 295, 100, 30);
        add(okButton);

        cancelarButton = new JButton("Cancelar");
        cancelarButton.setBounds(150, 295, 100, 30);
        add(cancelarButton);

        adicionarButton.addActionListener(e -> adicionarItem());
        okButton.addActionListener(e -> finalizarVenda());
        cancelarButton.addActionListener(e -> setVisible(false));
    }

    /** Prepara a tela para uma venda nova, recarregando os produtos cadastrados. */
    public void novaVenda() {
        produtosDisponiveis = controlador.estoque();
        produtoComboBox.removeAllItems();
        for (Produto produto : produtosDisponiveis) {
            produtoComboBox.addItem(produto.getNome() + " (" + Formato.moeda(produto.getPreco()) + ")");
        }
        quantidadeSpinner.setValue(1);
        itens.clear();
        itensListModel.clear();
        atualizarTotal();
    }

    private void adicionarItem() {
        int indice = produtoComboBox.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Cadastre um produto antes de vender.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Produto produto = produtosDisponiveis[indice];
        int quantidade = (Integer) quantidadeSpinner.getValue();

        int jaNoCarrinho = 0;
        for (ProdutoVendido item : itens) {
            if (item.getProduto() == produto) {
                jaNoCarrinho += item.getQuantidadeVendida();
            }
        }
        if (jaNoCarrinho + quantidade > produto.getQuantidade()) {
            JOptionPane.showMessageDialog(this,
                    "Estoque insuficiente. Disponível: " + (produto.getQuantidade() - jaNoCarrinho),
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ProdutoVendido item = new ProdutoVendido(produto, quantidade);
        itens.add(item);
        itensListModel.addElement(quantidade + "x " + produto.getNome()
                + " = " + Formato.moeda(item.getPrecoTotal()));
        atualizarTotal();
    }

    private double calcularTotal() {
        double total = 0;
        for (ProdutoVendido item : itens) {
            total += item.getPrecoTotal();
        }
        return total;
    }

    private void atualizarTotal() {
        totalTextField.setText(Formato.moeda(calcularTotal()));
    }

    private void finalizarVenda() {
        if (itens.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos um item.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            // O id definitivo é atribuído pelo controlador ao registrar a venda
            Venda venda = new Venda(0, new ArrayList<>(itens), calcularTotal());
            controlador.venderProduto(venda);
            JOptionPane.showMessageDialog(this,
                    "Venda #" + venda.getId() + " registrada: " + Formato.moeda(venda.getTotal()));
            setVisible(false);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
