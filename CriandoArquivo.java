import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CriandoArquivo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nome da disciplina: ");
        String disciplina = scanner.nextLine();
        String nomeArquivo = disciplina + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo,true))){
        while (true) {
            System.out.print("Respostas do aluno: ");
            String respostas = scanner.next();
            System.out.print("Nome do aluno: ");
            scanner.nextLine();
            String nome = scanner.nextLine();

            writer.write(respostas + "\t" + nome);
            writer.newLine();

            System.out.print("Adicionar outro aluno? (SIM/NÃO): ");
            if (!scanner.next().equalsIgnoreCase("SIM")) {
                break;
            }
        }
            System.out.println("Arquivo salvo como " + nomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
