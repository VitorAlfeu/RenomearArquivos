package renomearArquivos;

import java.io.File;
import java.util.Scanner;

public class RenomearArquivos {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String continuar = "n";
        String ultimoDiretorio = "";

        do {
            // Solicita os valores ao usu�rio
            System.out.println("Digite parte ou o nome do arquivo inteiro que deseja mudar (exemplo: antigo_nome): ");
            String valorAntigo = scanner.nextLine();

            System.out.println("Digite a parte que substituir� o texto digitado acima no nome do arquivo (exemplo: novo_nome): ");
            String valorNovo = scanner.nextLine();

            System.out.println("Digite a extens�o dos arquivos (exemplo: .txt) ou deixe em branco para incluir todas as extens�es: ");
            String extensao = scanner.nextLine().trim();
            
            if (extensao.isEmpty()) {
                extensao = null;
            }

            System.out.println("Digite o diret�rio (exemplo: C:\\Usuarios\\SeuUsuario\\Documentos\\Arquivos) ou pressione Enter para usar o �ltimo diret�rio utilizado: ");
            String diretorio = scanner.nextLine().trim();
            
            if (diretorio.isEmpty() && !ultimoDiretorio.isEmpty()) {
                diretorio = ultimoDiretorio;
            } else {
                diretorio = diretorio.replace("\\", "\\\\");
                ultimoDiretorio = diretorio;
            }

            File pasta = new File(diretorio);

            // Verifica��es iniciais
            if (!pasta.exists() || !pasta.isDirectory()) {
                System.out.println("Diret�rio inv�lido. Por favor, verifique o caminho e tente novamente.");
                continue;
            }

            if (extensao != null && !extensao.startsWith(".")) {
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
                } else if (arquivo.getName().contains(valorAntigo) && (extensao == null || arquivo.getName().endsWith(extensao))) {
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
                } else if (arquivo.getName().contains(valorAntigo) && (extensao == null || arquivo.getName().endsWith(extensao))) {
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