package pedidos.view;

import pedidos.controller.ClienteController;
import pedidos.controller.PedidoController;
import pedidos.controller.ProdutoController;
import pedidos.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClienteController clienteController = new ClienteController();

        System.out.println("--- Sistema de Pedidos ---");
        System.out.println("Digite seu email:");
        String email = sc.nextLine();
        System.out.println("Digite sua senha:");
        String senha = sc.nextLine();

        Cliente clienteLogado = clienteController.realizarLogin(email, senha);

        if (clienteLogado != null) {
            System.out.println("\nLogin realizado com sucesso! Bem-vindo(a), " + clienteLogado.getEmail() + ".");

            while (true) {
                System.out.println("\nO que você deseja fazer?");
                System.out.println("1 - Criar um novo pedido");
                System.out.println("2 - Consultar meus pedidos");
                System.out.println("3 - Sair");
                System.out.print("Escolha uma opção: ");
                String opcao = sc.nextLine();

                if (opcao.equals("1")) {
                    iniciarProcessoDePedido(clienteLogado, sc);
                } else if (opcao.equals("2")) {
                    consultarPedidos(clienteLogado, sc);
                } else if (opcao.equals("3")) {
                    System.out.println("Saindo do sistema. Até logo!");
                    break;
                } else {
                    System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } else {
            System.out.println("Falha no login. E-mail ou senha inválidos.");
        }
        sc.close();
    }

    private static void iniciarProcessoDePedido(Cliente cliente, Scanner sc) {
        ProdutoController produtoController = new ProdutoController();
        PedidoController pedidoController = new PedidoController();
        List<Produto> carrinho = new ArrayList<>();

        List<Produto> produtosDisponiveis = produtoController.getProdutosDisponiveis();
        System.out.println("\n--- Catálogo de Produtos ---");
        for (Produto p : produtosDisponiveis) {
            System.out.printf("ID: %s | Nome: %s | Preço: R$ %.2f%n", p.getId(), p.getNome(), p.getPreco());
        }

        String idSelecionado;
        while (true) {
            System.out.print("\nDigite o ID do produto para adicionar ao carrinho (ou 'fim' para finalizar): ");
            idSelecionado = sc.nextLine();
            if (idSelecionado.equalsIgnoreCase("fim")) {
                break;
            }
            String finalIdSelecionado = idSelecionado;
            produtosDisponiveis.stream()
                    .filter(p -> p.getId().equals(finalIdSelecionado))
                    .findFirst()
                    .ifPresentOrElse(
                            carrinho::add,
                            () -> System.out.println("Produto com ID inválido.")
                    );
            System.out.println("Produtos no carrinho: " + carrinho.size());
        }

        if (carrinho.isEmpty()) {
            System.out.println("\nCarrinho vazio. Compra cancelada.");
            return;
        }

        System.out.println("\n--- Endereço de Entrega ---");
        System.out.print("Rua e Número: ");
        String rua = sc.nextLine();
        System.out.print("Cidade: ");
        String cidade = sc.nextLine();
        System.out.print("Estado (sigla): ");
        String estado = sc.nextLine();
        System.out.print("CEP: ");
        String cep = sc.nextLine();
        Endereco endereco = new Endereco(rua, cidade, estado, cep);

        System.out.print("\nDigite a forma de pagamento (Ex: Cartao, Boleto): ");
        String formaPagamento = sc.nextLine();

        Pedido novoPedido = pedidoController.criarNovoPedido(cliente, carrinho, endereco, formaPagamento);

        if (novoPedido != null) {
            System.out.println("\n--- Resumo do Pedido ---");
            System.out.println("Número do Pedido: " + novoPedido.getNumeroPedido());
            System.out.println("Obrigado por comprar conosco!");
        } else {
            System.out.println("\nNão foi possível concluir a sua compra.");
        }
    }

    private static void consultarPedidos(Cliente cliente, Scanner sc) {
        PedidoController pedidoController = new PedidoController();
        List<String> pedidos = pedidoController.consultarPedidosPorEmail(cliente.getEmail());

        System.out.println("\n--- Meus Pedidos ---");
        if (pedidos.isEmpty()) {
            System.out.println("Você ainda não possui nenhum pedido cadastrado.");
        } else {
            System.out.println("Formato: numero,data,email,valor,\"endereco\",ids_produtos");
            for (String pedidoInfo : pedidos) {
                System.out.println(pedidoInfo);
            }
        }
        System.out.println("--------------------");
    }
}