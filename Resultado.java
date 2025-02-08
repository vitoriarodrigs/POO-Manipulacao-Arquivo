import java.io.*;
import java.util.*;

public class Resultado {

    // Este método pega um arquivo de disciplina e seu gabarito e compara-os
    public void compararArquivos() {
        Scanner scanner = new Scanner(System.in);

        // Pede a localização do arquivo da disciplina
        System.out.print("Digite a localização do arquivo da disciplina: ");
        String arquivoDisciplina = scanner.nextLine();

        // Pede a localização do arquivo do gabarito
        System.out.print("Digite a localização do arquivo do gabarito: ");
        String arquivoGabarito = scanner.nextLine();

        // Lê o gabarito
        String gabarito = lerGabarito(arquivoGabarito);
        if (gabarito == null) return;

        // Cria uma lista para armazenar os resultados
        List<ResultadoAluno> resultados = new ArrayList<>();

        // Começa a ler o arquivo da disciplina
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoDisciplina))) {
            String linha;
            while ((linha = reader.readLine()) != null) {

                // Separa a linha em 2 partes utilizando o tab como separador, a primeira será a resposta do aluno
                // A segunda será o nome dele
                String[] partes = linha.split("\t");
                if (partes.length != 2) continue;

                String respostasAluno = partes[0];
                String nomeAluno = partes[1];

                // Com as respostas na variável, compara com as do gabarito
                // A partir disso gera a nota
                int nota = calcularNota(respostasAluno, gabarito);

                // Adiciona a nota na lista de resultados com o respectivo aluno
                resultados.add(new ResultadoAluno(nomeAluno, nota));
            }

            // Ordenar resultados por nome (Ordem alfabética)
            Collections.sort(resultados);

            // Gera o arquivo de resultados em ordem alfabética
            String nomeArquivoResultadoAlfabetica = "resultado_alfabetica_" + new File(arquivoDisciplina).getName();
            gerarArquivoResultado(nomeArquivoResultadoAlfabetica, resultados, gabarito);

            // Ordenar resultados por nota (Ordem decrescente)
            resultados.sort((a, b) -> Integer.compare(b.nota, a.nota));

            // Gera o arquivo de resultados em ordem decrescente de notas
            String nomeArquivoResultadoNota = "resultado_nota_" + new File(arquivoDisciplina).getName();
            gerarArquivoResultadoComMedia(nomeArquivoResultadoNota, resultados, gabarito);

        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo da disciplina: " + e.getMessage());
        }
    }

    // Método para auxiliar na leitura do gabarito
    private String lerGabarito(String arquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de gabarito: " + e.getMessage());
            return null;
        }
    }

    // Método para calcular notas
    private int calcularNota(String respostasAluno, String gabarito) {
        int nota = 0;

	// Verifica se todas as respostas são iguais
        if (respostasAluno.chars().distinct().count() == 1) {
            return 0; // Retorna 0 se todas as respostas forem iguais (todas 'V' ou todas 'F')
        }

        // Aqui ele percorre a string da resposta do gabarito e do aluno
        for (int i = 0; i < gabarito.length() && i < respostasAluno.length(); i++) {

            // Para cada índice ele pega o caractere (V ou F) e verifica se é igual dos dois
            if (respostasAluno.charAt(i) == gabarito.charAt(i)) {
                nota++;
            }
        }
        return nota;
    }

    // Método para gerar o arquivo de resultados
    private void gerarArquivoResultado(String nomeArquivo, List<ResultadoAluno> resultados, String gabarito) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write("Gabarito: " + gabarito);
            writer.newLine();
            writer.newLine();
            writer.write("Resultados (ordenados por nome):");
            writer.newLine();
            writer.write("----------------------------------------");
            writer.newLine();

            for (ResultadoAluno resultado : resultados) {

                //Aqui eu formatei como vai sair os resultados
                writer.write(String.format("%-30s %d/10", resultado.nome, resultado.nota));
                writer.newLine();
            }

            System.out.println("Arquivo de resultados gerado: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao gerar arquivo de resultados: " + e.getMessage());
        }
    }

    // Método para gerar o arquivo de resultados e calcular a média da turma
    private void gerarArquivoResultadoComMedia(String nomeArquivo, List<ResultadoAluno> resultados, String gabarito) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write("Gabarito: " + gabarito);
            writer.newLine();
            writer.newLine();
            writer.write("Resultados (ordenados por nota):");
            writer.newLine();
            writer.write("----------------------------------------");
            writer.newLine();

            int somaNotas = 0;
            for (ResultadoAluno resultado : resultados) {

                //Aqui eu formatei como vai sair os resultados
                writer.write(String.format("%-30s %d/10", resultado.nome, resultado.nota));
                writer.newLine();
                somaNotas += resultado.nota;
            }

            double media = (double) somaNotas / resultados.size();
            writer.newLine();
            writer.write(String.format("Média da turma: %.2f/10", media));

            System.out.println("Arquivo de resultados gerado: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao gerar arquivo de resultados: " + e.getMessage());
        }
    }

    // Aqui é criada uma classe interna, mas funciona como se fosse uma Struct
    // Ela agrupa aluno por sua nota
    private static class ResultadoAluno implements Comparable<ResultadoAluno> {
        String nome;
        int nota;

        public ResultadoAluno(String nome, int nota) {
            this.nome = nome;
            this.nota = nota;
        }

        @Override
        public int compareTo(ResultadoAluno outro) {
            return this.nome.compareToIgnoreCase(outro.nome);
        }
    }
}
