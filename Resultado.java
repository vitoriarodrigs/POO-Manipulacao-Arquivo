import java.io.*;
import java.util.*;

public class Resultado {

    //Este metodo pega um arquivo de disciplina e seu gabarito e compara-os
    public void compararArquivos() {
        Scanner scanner = new Scanner(System.in);

        // Pede a localizacao do arquivo da disciplina
        System.out.print("Digite a localização do arquivo da disciplina: ");
        String arquivoDisciplina = scanner.nextLine();

        // Pede a localizacao do arquivo do gabarito
        System.out.print("Digite a localização do arquivo do gabarito: ");
        String arquivoGabarito = scanner.nextLine();

        // Lê o gabarito
        String gabarito = lerGabarito(arquivoGabarito);
        if (gabarito == null) return;

        // Cria uma lista para armazenar os resultados
        List<ResultadoAluno> resultados = new ArrayList<>();

        // Comeca a ler o arquivo da disciplina
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoDisciplina))) {
            String linha;
            while ((linha = reader.readLine()) != null) {

                // Separa a linha em 2 partes utilizando o tab como separador, a primeira sera a resposta do aluno
                // A segunda sera o nome dele
                String[] partes = linha.split("\t");
                if (partes.length != 2) continue;

                String respostasAluno = partes[0];
                String nomeAluno = partes[1];

                // Com as respostas na variavel, compara com as do gabarito
                // A partir disso gera a nota
                int nota = calcularNota(respostasAluno, gabarito);


                // Adiciona a nota na lista de resultados com o respectivo aluno
                resultados.add(new ResultadoAluno(nomeAluno, nota));
            }

            // Ordenar resultados por nome (Ordem alfabetica)
            Collections.sort(resultados);

            // Gera o arquivo de resultados
            String nomeArquivoResultado = "resultado_" + new File(arquivoDisciplina).getName();
            gerarArquivoResultado(nomeArquivoResultado, resultados, gabarito);

        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo da disciplina: " + e.getMessage());
        }
    }

    // Metodo para auxiliar na leitura do gabarito
    private String lerGabarito(String arquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de gabarito: " + e.getMessage());
            return null;
        }
    }

    // Metodo para calcular notas
    private int calcularNota(String respostasAluno, String gabarito) {
        int nota = 0;

        // Aqui ele percorre a string da resposta do gabarito e do aluno
        for (int i = 0; i < gabarito.length() && i < respostasAluno.length(); i++) {

            // Para cada index ele pega o caractere (V ou F) e verifica se é igual dos dois
            if (respostasAluno.charAt(i) == gabarito.charAt(i)) {
                nota++;
            }
        }
        return nota;
    }

    // Metodo para gerar o arquivo de resultados
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

    // Aqui é criado uma classe interna, mas funciona como se fosse uma Struct
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