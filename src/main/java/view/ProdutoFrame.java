package view;

import controller.Controlador;
import model.Produto;
import javax.swing.*;

public class ProdutoFrame extends JFrame {
    private JTextField nomeTextField;
    private JTextField precoTextField;
    private JTextField quantidadeTextField;
    private JButton salvarButton;
    private Controlador controlador;

    public ProdutoFrame(Controlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setTitle("Cadastrar Produto");
        setSize(300, 240);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(30, 30, 85, 25);
        add(nomeLabel);

        nomeTextField = new JTextField();
        nomeTextField.setBounds(115, 30, 140, 25);
        add(nomeTextField);

        JLabel precoLabel = new JLabel("Preço:");
        precoLabel.setBounds(30, 70, 85, 25);
        add(precoLabel);

        precoTextField = new JTextField();
        precoTextField.setBounds(115, 70, 140, 25);
        add(precoTextField);

        JLabel quantidadeLabel = new JLabel("Quantidade:");
        quantidadeLabel.setBounds(30, 110, 85, 25);
        add(quantidadeLabel);

        quantidadeTextField = new JTextField("0");
        quantidadeTextField.setBounds(115, 110, 140, 25);
        add(quantidadeTextField);

        salvarButton = new JButton("Salvar");
        salvarButton.setBounds(115, 150, 140, 30);
        add(salvarButton);

        salvarButton.addActionListener(e -> salvar());
    }

    private void salvar() {
        try {
            String nome = nomeTextField.getText().trim();
            double preco = Formato.lerDecimal(precoTextField.getText());
            int quantidade = Integer.parseInt(quantidadeTextField.getText().trim());

            Produto produto = new Produto(nome, preco);
            controlador.cadastrarProduto(produto);
            // O id do produto é a sua posição na lista: o recém-cadastrado é o último
            controlador.postQuantidade(controlador.produtosCadastrados().length - 1, quantidade);

            limparCampos();
            setVisible(false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço ou quantidade inválidos.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        nomeTextField.setText("");
        precoTextField.setText("");
        quantidadeTextField.setText("0");
    }
}
