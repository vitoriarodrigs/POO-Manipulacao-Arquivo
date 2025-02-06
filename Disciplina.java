import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Disciplina {
    private String disciplina;


    //Getters e Setters
    public void setNome(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getNome() {
        return disciplina;
    }

    //Metodo para gerar uma nova disciplina
    public void criarDisciplina() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nome da disciplina: ");
        disciplina = scanner.nextLine();
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
