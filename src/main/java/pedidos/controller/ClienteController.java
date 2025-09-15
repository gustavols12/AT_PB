package pedidos.controller;


import pedidos.model.Cliente;
import pedidos.repository.ClienteRepository;

public class ClienteController {
    private ClienteRepository clienteRepository;

    public ClienteController() {
        this.clienteRepository = new ClienteRepository();
    }

    public Cliente realizarLogin(String email, String senha) {
        return clienteRepository.autenticar(email, senha);
    }

    public void cadastrarNovoCliente(String email, String senha) {
        Cliente novoCliente = new Cliente(email, senha);
        clienteRepository.salvar(novoCliente);
    }
}
