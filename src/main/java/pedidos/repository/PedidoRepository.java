package pedidos.repository;

import pedidos.model.Pedido;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {
    private static final String ARQUIVO_PEDIDOS = "pedidos.csv";

    public void salvar(Pedido pedido) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_PEDIDOS, true))) {
            writer.write(pedido.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar o pedido: " + e.getMessage());
        }
    }
    public List<String> buscarPorEmail(String email) {
        List<String> pedidosEncontrados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_PEDIDOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length > 2 && partes[2].equalsIgnoreCase(email)) {
                    pedidosEncontrados.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar os pedidos: " + e.getMessage());
        }
        return pedidosEncontrados;
    }


}