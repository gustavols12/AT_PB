package pedidos.repository;

import pedidos.model.Produto;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    private static final String ARQUIVO_PRODUTOS = "produtos.csv";

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_PRODUTOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    String id = partes[0];
                    String nome = partes[1];
                    double preco = Double.parseDouble(partes[2]);
                    produtos.add(new Produto(id, nome, preco));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar o catálogo de produtos: " + e.getMessage());
        }
        return produtos;
    }
    public Produto buscarPorId(String id) {
        return listarTodos().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}