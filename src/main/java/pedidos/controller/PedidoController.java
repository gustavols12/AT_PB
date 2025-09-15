package pedidos.controller;

import pedidos.model.*;
import pedidos.repository.PedidoRepository;
import java.util.List;

public class PedidoController {
    private PedidoRepository pedidoRepository;

    public PedidoController() {
        this.pedidoRepository = new PedidoRepository();
    }

    public Pedido criarNovoPedido(Cliente cliente, List<Produto> produtos, Endereco endereco, String formaPagamento) {
        if (produtos == null || produtos.isEmpty()) {
            System.out.println("Não é possível criar um pedido sem produtos.");
            return null;
        }

        Pagamento pagamento = new Pagamento(formaPagamento);
        boolean pagamentoAutorizado = pagamento.autorizar();

        if (pagamentoAutorizado) {
            Pedido novoPedido = new Pedido(cliente, produtos, endereco);
            novoPedido.confirmarPagamento();
            pedidoRepository.salvar(novoPedido);
            System.out.println("Pagamento aprovado! Pedido criado com sucesso.");
            return novoPedido;
        } else {
            System.out.println("Pagamento recusado. O pedido não foi criado.");
            return null;
        }
    }

    public List<String> consultarPedidosPorEmail(String email) {
        return pedidoRepository.buscarPorEmail(email);
    }
}
