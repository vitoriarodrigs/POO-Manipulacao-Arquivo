import java.io.FileReader;
import java.util.Scanner;
import java.io.*;

public class Gabarito {
    private String respostas;
    private String localizacao;

    public String getRespostas() {
        return respostas;
    }

    public void setRespostas(String respostas) {
        this.respostas = respostas;
    }

    public void buscarGabarito(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a localização do gabarito: ");
        localizacao = scanner.next();
    }

    //Esse metodo é para caso o usuário tenha criado o gabarito manualmente
    public void cadastrarGabarito(){
        try{
            buscarGabarito();
            FileReader gabarito = new FileReader(localizacao);
            BufferedReader br = new BufferedReader(gabarito);
            respostas = br.readLine();
            setRespostas(respostas);

            br.close();
            gabarito.close();
        }catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
        }catch (IOException e){
            System.out.println("Erro ao ler o arquivo");
        }
    }

    //Este metodo e para gerar um gabarito em tempo de execucao
    public void criarGabarito(String nomeArquivo) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite as 10 respostas (V ou F):");
        String respostas = scanner.nextLine().toUpperCase();

        // Validação das respostas
        if (respostas.length() != 10 || !respostas.matches("[VF]+")) {
            System.out.println("Erro: O gabarito deve conter exatamente 10 respostas (V ou F)");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write(respostas);
            System.out.println("Gabarito criado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de gabarito: " + e.getMessage());
        }
    }

}
