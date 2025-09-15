package pedidos.controller;

import pedidos.model.Produto;
import pedidos.repository.ProdutoRepository;
import java.util.List;

public class ProdutoController {
    private ProdutoRepository produtoRepository;

    public ProdutoController() {
        this.produtoRepository = new ProdutoRepository();
    }

    public List<Produto> getProdutosDisponiveis() {
        return produtoRepository.listarTodos();
    }
}
