import java.text.NumberFormat;
import java.util.Locale;

import java.text.NumberFormat;
import java.util.Locale;

public class Produto {
    private String nome;
    private String descricao;
    private int quantidade;
    private double precoUnitario;
    private String categoria;
    private int quantidadeMinima;

    public Produto(String nome, String descricao, int quantidade, double precoUnitario, String categoria, int quantidadeMinima) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.categoria = categoria;
        this.quantidadeMinima = quantidadeMinima;
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getQuantidade() { return quantidade; }
    public double getPrecoUnitario() { return precoUnitario; }
    public String getCategoria() { return categoria; }
    public int getQuantidadeMinima() { return quantidadeMinima; }

    public double getSubtotal() {
        return quantidade * precoUnitario;
    }

    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return String.format("%s - %s | Qtde: %d | Preço: %s | Cat: %s | QtdeMín: %d | Subtotal: %s",
                nome,
                descricao,
                quantidade,
                nf.format(precoUnitario),
                categoria,
                quantidadeMinima,
                nf.format(getSubtotal())
        );
    }
}
