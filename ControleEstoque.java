import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Produto {
    private String nome;
    private int qtdEstoque;
    private double precoUnitario;
    private String categoria;
    private int qtdMinima;

    public Produto(String nome, int qtdEstoque, double precoUnitario, String categoria, int qtdMinima) {
        this.nome = nome;
        this.qtdEstoque = qtdEstoque;
        this.precoUnitario = precoUnitario;
        this.categoria = categoria;
        this.qtdMinima = qtdMinima;
    }

    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }
    public int getQtdEstoque() { return qtdEstoque; }
    
    public double getValorTotalEstoque() {
        return qtdEstoque * precoUnitario;
    }

    @Override
    public String toString() {
        return String.format("%-20s | Qtd: %-5d | $ Unit: %-8.2f | Cat: %s", 
                nome, qtdEstoque, precoUnitario, categoria);
    }
}

public class ControleEstoque {
    static ArrayList<Produto> produtos = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n=== CONTROLE DE ESTOQUE ===");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar");
            System.out.println("3 - Filtrar p/ categoria");
            System.out.println("4 - Ordenar (por nome)");
            System.out.println("5 - Remover elemento");
            System.out.println("6 - Atualizar preço");
            System.out.println("7 - Relatório por Categoria (Subtotais)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); 
            } else {
                opcao = 0; 
                scanner.next(); 
            }

            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> listarProdutos();
                case 3 -> filtrarPorCategoria();
                case 4 -> ordenarProdutos();
                case 5 -> removerProduto();
                case 6 -> atualizarPreco();
                case 7 -> relatorioSubtotais();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static void cadastrarProduto() {
        System.out.print("Nome/Descrição: ");
        String nome = scanner.nextLine();
        System.out.print("Qtd em Estoque: ");
        int qtd = scanner.nextInt();
        System.out.print("Preço Unitário: ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.print("Categoria: ");
        String cat = scanner.nextLine();
        System.out.print("Qtd Mínima: ");
        int min = scanner.nextInt();

        produtos.add(new Produto(nome, qtd, preco, cat, min));
        System.out.println("Produto cadastrado com sucesso!");
    }

    static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Estoque vazio.");
            return;
        }
        System.out.println("--- Lista Geral ---");
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }

    static void filtrarPorCategoria() {
        System.out.print("Digite a categoria para filtrar: ");
        String busca = scanner.nextLine();
        boolean encontrou = false;
        for (Produto p : produtos) {
            if (p.getCategoria().equalsIgnoreCase(busca)) {
                System.out.println(p);
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhum produto nesta categoria.");
    }

    static void ordenarProdutos() {
        Collections.sort(produtos, Comparator.comparing(Produto::getNome));
        System.out.println("Estoque ordenado por nome!");
        listarProdutos();
    }

    static void removerProduto() {
        System.out.print("Nome do produto para remover: ");
        String nome = scanner.nextLine();
        boolean removeu = produtos.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
        
        if (removeu) System.out.println("Produto removido.");
        else System.out.println("Produto não encontrado.");
    }

    static void atualizarPreco() {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                System.out.print("Novo preço: ");
                double novoPreco = scanner.nextDouble();
                p.setPrecoUnitario(novoPreco);
                System.out.println("Preço atualizado!");
                return;
            }
        }
        System.out.println("Produto não encontrado.");
    }

    static void relatorioSubtotais() {
        if (produtos.isEmpty()) {
            System.out.println("Estoque vazio.");
            return;
        }

        produtos.sort(Comparator.comparing(Produto::getCategoria));

        String categoriaAtual = "";
        double subtotal = 0;
        double totalGeral = 0;

        System.out.println("\n=== RELATÓRIO DE VALORES POR CATEGORIA ===");

        for (Produto p : produtos) {
            if (!p.getCategoria().equalsIgnoreCase(categoriaAtual)) {
                if (!categoriaAtual.isEmpty()) {
                    System.out.printf("   >> Subtotal %s: R$ %.2f\n\n", categoriaAtual, subtotal);
                }
                categoriaAtual = p.getCategoria();
                subtotal = 0;
                System.out.println("Categoria: " + categoriaAtual);
            }

            System.out.printf(" - %-15s (Qtd: %d) = R$ %.2f\n", p.getNome(), p.getQtdEstoque(), p.getValorTotalEstoque());
            subtotal += p.getValorTotalEstoque();
            totalGeral += p.getValorTotalEstoque();
        }

        if (!categoriaAtual.isEmpty()) {
            System.out.printf("   >> Subtotal %s: R$ %.2f\n", categoriaAtual, subtotal);
        }

        System.out.println("------------------------------------------");
        System.out.printf("TOTAL GERAL DO ESTOQUE: R$ %.2f\n", totalGeral);
    }
}