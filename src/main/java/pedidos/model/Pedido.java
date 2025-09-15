package pedidos.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Pedido {
    private String numeroPedido;
    private LocalDate dataCriacao;
    private double valorTotal;
    private String status;
    private Cliente cliente;
    private Endereco endereco;
    private List<Produto> produtos;

    public Pedido(Cliente cliente, List<Produto> produtos, Endereco endereco) {
        this.numeroPedido = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.cliente = cliente;
        this.produtos = produtos;
        this.endereco = endereco;
        this.dataCriacao = LocalDate.now();
        this.valorTotal = calcularValorTotal();
        this.status = "Aguardando Pagamento";
    }

    private double calcularValorTotal() {
        return this.produtos.stream().mapToDouble(Produto::getPreco).sum();
    }

    public void confirmarPagamento() {
        this.status = "Pagamento Aprovado";
    }

    public String getNumeroPedido() { return numeroPedido; }

    @Override
    public String toString() {
        String idsProdutos = produtos.stream()
                .map(Produto::getId)
                .collect(Collectors.joining(";"));
        return String.format("%s,%s,%s,%.2f,\"%s\",%s",
                numeroPedido, dataCriacao, cliente.getEmail(), valorTotal, endereco.toString(), idsProdutos);
    }
}