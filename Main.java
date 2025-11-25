import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
}
        System.out.println("--- Sistema de Controle de Estoque ---");
        System.out.println("1 - Cadastrar produto");
        System.out.println("2 - Listar produtos");
        System.out.println("3 - Filtrar por categoria");
        System.out.println("4 - Ordenar por nome");
        System.out.println("5 - Remover produto por índice");
        System.out.println("6 - Subtotal por categoria");
        System.out.println("7 - Listar com subtotal por categoria");
        System.out.println("8 - Total geral do estoque");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ControleEstoque controle = new ControleEstoque();
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        while (true) {
            mostrarMenu();
            String linha = sc.hasNextLine() ? sc.nextLine().trim() : "";
            if (linha.isEmpty()) {
                continue;
            }
            int opcao;
            try {
                opcao = Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine().trim();
                    System.out.print("Descrição: ");
                    String desc = sc.nextLine().trim();
                    System.out.print("Quantidade: ");
                    int qtd = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Preço unitário (use ponto ou vírgula): ");
                    String precoStr = sc.nextLine().trim().replace(',', '.');
                    double preco = Double.parseDouble(precoStr);
                    System.out.print("Categoria: ");
                    String cat = sc.nextLine().trim();
                    System.out.print("Quantidade mínima: ");
                    int qtdMin = Integer.parseInt(sc.nextLine().trim());
                    Produto p = new Produto(nome, desc, qtd, preco, cat, qtdMin);
                    controle.adicionarProduto(p);
                    System.out.println("Produto cadastrado com sucesso!\n");
                    break;
                case 2:
                    System.out.println("--- Lista de Produtos ---");
                    int i = 0;
                    for (Produto prod : controle.listarProdutos()) {
                        System.out.println(i + ": " + prod);
                        i++;
                    }
                    System.out.println();
                    break;
                case 3:
                    System.out.print("Categoria para filtrar: ");
                    String filtro = sc.nextLine().trim();
                    System.out.println("--- Produtos na categoria: " + filtro + " ---");
                    int j = 0;
                    for (Produto prod : controle.filtrarPorCategoria(filtro)) {
                        System.out.println(j + ": " + prod);
                        j++;
                    }
                    System.out.println();
                    break;
                case 4:
                    controle.ordenarPorNome();
                    System.out.println("Produtos ordenados por nome.\n");
                    break;
                case 5:
                    System.out.print("Índice do produto a remover: ");
                    int idx = Integer.parseInt(sc.nextLine().trim());
                    if (controle.removerPorIndice(idx))
                        System.out.println("Produto removido com sucesso.\n");
                    else
                        System.out.println("Índice inválido.\n");
                    break;
                case 6:
                    System.out.println("--- Subtotal por Categoria ---");
                    controle.subtotalPorCategoria().forEach((k, v) -> System.out.println(k + ": " + nf.format(v)));
                    System.out.println();
                    break;
                case 7:
                    controle.listarComSubtotalPorCategoria();
                    break;
                case 8:
                    System.out.println("Total geral do estoque: " + nf.format(controle.totalGeral()));
                    System.out.println();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    sc.close();
                    return;
                default:
                    System.out.println("Opção desconhecida.\n");
            }
        }
    }
}
