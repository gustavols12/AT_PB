// package pedidos;

import io.javalin.Javalin;
import pedidos.controller.ClienteController;
import pedidos.controller.PedidoController;
import pedidos.model.Cliente;
import pedidos.model.Endereco;
import pedidos.model.Pedido;
import pedidos.model.Produto;
import pedidos.repository.ClienteRepository;
import pedidos.repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiJavalin {

    public record LoginRequest(String email, String senha) {}
    public record PedidoRequest(String emailCliente, List<String> idsProdutos, Endereco endereco, String formaPagamento) {}

    public static void main(String[] args) {
        ClienteController clienteController = new ClienteController();
        PedidoController pedidoController = new PedidoController();
        ClienteRepository clienteRepository = new ClienteRepository();
        ProdutoRepository produtoRepository = new ProdutoRepository();

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new io.javalin.json.JavalinJackson());
            config.http.defaultContentType = "application/json; charset=utf-8";
        }).start(8000);

        System.out.println("API de Pedidos rodando na porta 8000!");

        app.post("/login", ctx -> {
            LoginRequest loginReq = ctx.bodyAsClass(LoginRequest.class);
            Cliente cliente = clienteController.realizarLogin(loginReq.email(), loginReq.senha());
            if (cliente != null) {
                ctx.json(Map.of("message", "Login bem-sucedido!", "usuario", cliente.getEmail()));
            } else {
                ctx.status(401).json(Map.of("message", "Credenciais inválidas."));
            }
        });

        app.post("/clientes", ctx -> {
            LoginRequest novoClienteReq = ctx.bodyAsClass(LoginRequest.class);
            clienteController.cadastrarNovoCliente(novoClienteReq.email(), novoClienteReq.senha());
            ctx.status(201).json(Map.of("message", "Cliente cadastrado com sucesso!"));
        });

        app.get("/pedidos", ctx -> {
            String email = ctx.queryParam("email");
            if (email == null || email.isBlank()) {
                ctx.status(400).json(Map.of("error", "O parâmetro 'email' é obrigatório."));
                return;
            }
            List<String> pedidos = pedidoController.consultarPedidosPorEmail(email);
            ctx.json(pedidos);
        });

        app.post("/pedidos", ctx -> {
            PedidoRequest pedidoReq = ctx.bodyAsClass(PedidoRequest.class);

            Cliente cliente = clienteRepository.buscarPorEmail(pedidoReq.emailCliente());
            if (cliente == null) {
                ctx.status(404).json(Map.of("error", "Cliente com e-mail '" + pedidoReq.emailCliente() + "' não encontrado."));
                return;
            }

            List<Produto> produtos = new ArrayList<>();
            for (String produtoId : pedidoReq.idsProdutos()) {
                Produto produto = produtoRepository.buscarPorId(produtoId);
                if (produto != null) {
                    produtos.add(produto);
                } else {
                    ctx.status(404).json(Map.of("error", "Produto com ID '" + produtoId + "' não encontrado."));
                    return;
                }
            }

            Pedido novoPedido = pedidoController.criarNovoPedido(
                    cliente,
                    produtos,
                    pedidoReq.endereco(),
                    pedidoReq.formaPagamento()
            );

            if (novoPedido != null) {
                ctx.status(201).json(Map.of(
                        "message", "Pedido criado com sucesso!",
                        "numeroPedido", novoPedido.getNumeroPedido()
                ));
            } else {
                ctx.status(400).json(Map.of("error", "Falha ao criar o pedido. Verifique os dados de pagamento."));
            }
        });
    }
}