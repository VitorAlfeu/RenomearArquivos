package renomearArquivos;

import java.io.File;
import java.util.Scanner;

public class RenomearArquivos {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String continuar = "n";

        do {
            // Solicita os valores ao usu�rio
            System.out.println("Digite o valor antigo: ");
            String valorAntigo = scanner.nextLine();

            System.out.println("Digite o valor novo: ");
            String valorNovo = scanner.nextLine();

            System.out.println("Digite a extens�o dos arquivos (por exemplo, .yml): ");
            String extensao = scanner.nextLine();

            System.out.println("Digite o diret�rio (use uma barra inversa): ");
            String diretorio = scanner.nextLine().replace("\\", "\\\\");

            File pasta = new File(diretorio);

            // Verifica��es iniciais
            if (!pasta.exists() || !pasta.isDirectory()) {
                System.out.println("Diret�rio inv�lido. Por favor, verifique o caminho e tente novamente.");
                continue;
            }

            if (!extensao.startsWith(".")) {
                System.out.println("Extens�o inv�lida. Por favor, certifique-se de que a extens�o comece com um ponto.");
                continue;
            }

            // Contagem inicial de arquivos
            int totalArquivos = contarArquivos(pasta, valorAntigo, extensao);
            System.out.println("Total de arquivos encontrados: " + totalArquivos);

            // Renomeia arquivos
            int arquivosAlterados = renomearArquivos(pasta, valorAntigo, valorNovo, extensao);
            System.out.println("Total de arquivos renomeados: " + arquivosAlterados);

            // Pergunta se deseja continuar
            System.out.println("Deseja renomear mais algum item? (s/n)");
            continuar = scanner.nextLine().trim().toLowerCase();
        } while (continuar.equals("s"));

        System.out.println("Programa encerrado.");
        scanner.close();
    }

    public static int contarArquivos(File pasta, String valorAntigo, String extensao) {
        int count = 0;
        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isDirectory()) {
                    count += contarArquivos(arquivo, valorAntigo, extensao);
                } else if (arquivo.getName().contains(valorAntigo) && arquivo.getName().endsWith(extensao)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int renomearArquivos(File pasta, String valorAntigo, String valorNovo, String extensao) {
        int count = 0;
        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isDirectory()) {
                    count += renomearArquivos(arquivo, valorAntigo, valorNovo, extensao);
                } else if (arquivo.getName().contains(valorAntigo) && arquivo.getName().endsWith(extensao)) {
                    String novoNome = arquivo.getName().replace(valorAntigo, valorNovo);
                    File novoArquivo = new File(arquivo.getParent(), novoNome);
                    if (arquivo.renameTo(novoArquivo)) {
                        System.out.println("Renomeado: " + arquivo.getPath() + " para " + novoArquivo.getPath());
                        count++;
                    } else {
                        System.out.println("Falha ao renomear: " + arquivo.getPath());
                    }
                }
            }
        }
        return count;
    }
}