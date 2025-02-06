public class CriandoArquivo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarDisciplina();
                    break;
                case 2:
                    criarGabarito();
                    break;
                case 3:
                    compararResultados();
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1 - Criar arquivo de disciplina");
        System.out.println("2 - Criar arquivo de gabarito");
        System.out.println("3 - Comparar resultados");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void criarDisciplina() {
        Disciplina disciplina = new Disciplina();
        disciplina.criarDisciplina();
    }

    private static void criarGabarito() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do arquivo de gabarito a ser criado: ");
        String nomeArquivo = scanner.nextLine();

        Gabarito gabarito = new Gabarito();
        gabarito.criarGabarito(nomeArquivo);
    }

    private static void compararResultados() {
        Resultado resultado = new Resultado();
        resultado.compararArquivos();
    }
}
