package renomearArquivos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

            if (ultimoDiretorio.isEmpty()) {
            	System.out.println("Digite o diret�rio (exemplo: C:\\Usuarios\\SeuUsuario\\Documentos\\Arquivos)");            	
            } else {
            	System.out.println("Digite o diret�rio (exemplo: C:\\Usuarios\\SeuUsuario\\Documentos\\Arquivos) ou pressione Enter para usar o diret�rio " + ultimoDiretorio.replace("\\\\", "\\") + ": ");	
            }
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

            // Exibe altera��es que ser�o feitas
            List<String[]> alteracoes = listarAlteracoes(pasta, valorAntigo, valorNovo, extensao);
            for (String[] alteracao : alteracoes) {
                System.out.println("Renomear: " + alteracao[0] + " para " + alteracao[1]);
            }

            // Pergunta se deseja continuar
            System.out.println("Deseja continuar com essas altera��es? (s/n)");
            String resposta = scanner.nextLine().trim().toLowerCase();

            if (resposta.equals("s")) {
                // Renomear arquivos
                int arquivosAlterados = renomearArquivos(alteracoes);
                System.out.println("Total de arquivos renomeados: " + arquivosAlterados);
            } else {
                System.out.println("Altera��es canceladas.");
            }

            // Perguntar se deseja continuar com novos par�metros
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

    public static List<String[]> listarAlteracoes(File pasta, String valorAntigo, String valorNovo, String extensao) {
        List<String[]> alteracoes = new ArrayList<>();
        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isDirectory()) {
                    alteracoes.addAll(listarAlteracoes(arquivo, valorAntigo, valorNovo, extensao));
                } else if (arquivo.getName().contains(valorAntigo) && (extensao == null || arquivo.getName().endsWith(extensao))) {
                    String novoNome = arquivo.getName().replace(valorAntigo, valorNovo);
                    String[] alteracao = {arquivo.getPath(), new File(arquivo.getParent(), novoNome).getPath()};
                    alteracoes.add(alteracao);
                }
            }
        }
        return alteracoes;
    }

    public static int renomearArquivos(List<String[]> alteracoes) {
        int count = 0;
        for (String[] alteracao : alteracoes) {
            File arquivoAntigo = new File(alteracao[0]);
            File arquivoNovo = new File(alteracao[1]);
            if (arquivoAntigo.renameTo(arquivoNovo)) {
                System.out.println("Renomeado: " + arquivoAntigo.getPath() + " para " + arquivoNovo.getPath());
                count++;
            } else {
                System.out.println("Falha ao renomear: " + arquivoAntigo.getPath());
            }
        }
        return count;
    }
}
