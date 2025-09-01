package pedidos.controller;

import pedidos.model.Cliente;
import pedidos.repository.ClienteRepository;

public class PedidoController {
    // O Controller agora "tem um" Repository, em vez de "ser um" Cliente
    private ClienteRepository clienteRepository;

    public PedidoController() {
        this.clienteRepository = new ClienteRepository();
    }

    // Método para realizar o login
    public boolean realizarLogin(String email, String senha) {
        System.out.println("Tentando autenticar usuário...");
        return clienteRepository.autenticar(email, senha);
    }
    public void cadastrarUsuario(String email, String senha){
        System.out.println("Tentando cadastrar usuário");
        Cliente cliente = new Cliente(email,senha);
        clienteRepository.salvar(cliente);
    }
}
