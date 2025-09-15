package pedidos.model;

public class Pagamento {
    private String formaPagamento;
    private String status;

    public Pagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public boolean autorizar() {
        if ("Cartao".equalsIgnoreCase(formaPagamento) && Math.random() < 0.1) { // 10% de chance de falha
            this.status = "Recusado";
            return false;
        }
        this.status = "Aprovado";
        return true;
    }

    public String getStatus() { return status; }
}