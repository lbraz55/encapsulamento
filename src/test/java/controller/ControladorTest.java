package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Produto;
import model.ProdutoVendido;
import model.Venda;

class ControladorTest {

    private Controlador controlador;
    private Produto caneta;

    @BeforeEach
    void setUp() {
        controlador = new Controlador();
        caneta = new Produto("Caneta", 2.50);
        controlador.cadastrarProduto(caneta);
        controlador.postQuantidade(0, 10);
    }

    @Test
    void cadastraProdutoEDefineEstoque() {
        assertEquals(1, controlador.produtosCadastrados().length);
        assertEquals(10, controlador.estoque()[0].getQuantidade());
    }

    @Test
    void vendaDaBaixaNoEstoqueERecebeId() {
        Venda venda = new Venda(0, List.of(new ProdutoVendido(caneta, 4)), 10.0);

        controlador.venderProduto(venda);

        assertEquals(6, caneta.getQuantidade());
        assertEquals(1, venda.getId());
        assertEquals(1, controlador.vendasRealizadas().length);
    }

    @Test
    void idsDasVendasSaoSequenciais() {
        controlador.venderProduto(new Venda(0, List.of(new ProdutoVendido(caneta, 1)), 2.5));
        Venda segunda = new Venda(0, List.of(new ProdutoVendido(caneta, 1)), 2.5);
        controlador.venderProduto(segunda);

        assertEquals(2, segunda.getId());
    }

    @Test
    void vendaComEstoqueInsuficienteNaoAlteraNada() {
        // Dois itens do mesmo produto que, somados, passam do estoque
        Venda venda = new Venda(0, List.of(
                new ProdutoVendido(caneta, 6),
                new ProdutoVendido(caneta, 6)), 30.0);

        assertThrows(IllegalStateException.class, () -> controlador.venderProduto(venda));
        assertEquals(10, caneta.getQuantidade());
        assertEquals(0, controlador.vendasRealizadas().length);
    }

    @Test
    void vendaSemItensEhRejeitada() {
        Venda venda = new Venda(0, List.of(), 0);
        assertThrows(IllegalArgumentException.class, () -> controlador.venderProduto(venda));
    }

    @Test
    void postQuantidadeRejeitaProdutoInexistente() {
        assertThrows(IllegalArgumentException.class, () -> controlador.postQuantidade(5, 1));
    }

    @Test
    void cadastroRejeitaNomeVazio() {
        assertThrows(IllegalArgumentException.class,
                () -> controlador.cadastrarProduto(new Produto("  ", 1.0)));
    }
}
