 package pedidos.repository;

import pedidos.model.Cliente;
import java.io.*;

 public class ClienteRepository {
     private static final String ARQUIVO_CSV = "usuarios.csv";

     public void salvar(Cliente cliente) {
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CSV, true))) {
             writer.write(cliente.getEmail() + "," + cliente.getSenha());
             writer.newLine();
             System.out.println("Usuário cadastrado com sucesso!");
         } catch (IOException e) {
             System.out.println("Erro ao salvar o usuário: " + e.getMessage());
         }
     }

     public Cliente autenticar(String email, String senha) {
         try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CSV))) {
             String linha;
             while ((linha = reader.readLine()) != null) {
                 String[] partes = linha.split(",");
                 if (partes.length == 2 && partes[0].equalsIgnoreCase(email) && partes[1].equals(senha)) {
                     return new Cliente(partes[0], partes[1]);
                 }
             }
         } catch (IOException e) {
             System.out.println("Erro ao ler o arquivo de usuários: " + e.getMessage());
         }
         return null;
     }

     public Cliente buscarPorEmail(String email) {
         try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CSV))) {
             String linha;
             while ((linha = reader.readLine()) != null) {
                 String[] partes = linha.split(",");
                 if (partes.length >= 1 && partes[0].equalsIgnoreCase(email)) {
                     // Retorna o cliente com a senha (se houver) ou vazia.
                     return new Cliente(partes[0], partes.length > 1 ? partes[1] : "");
                 }
             }
         } catch (IOException e) {
             System.out.println("Erro ao ler o arquivo de usuários: " + e.getMessage());
         }
         return null; // Retorna null se não encontrar
     }
 }