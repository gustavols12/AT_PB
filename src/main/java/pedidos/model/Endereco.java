package pedidos.model;

public class Endereco {
    private String rua;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco() {
    }

    public Endereco(String rua, String cidade, String estado, String cep) {
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    // O método toString continua o mesmo.
    @Override
    public String toString() {
        return String.format("%s; %s; %s; %s", rua, cidade, estado, cep);
    }
}