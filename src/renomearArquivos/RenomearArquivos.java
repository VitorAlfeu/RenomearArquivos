package renomearArquivos;

import java.io.File;

public class RenomearArquivos {

    public static void main(String[] args) {
        // Define o diret�rio onde os arquivos est�o localizados
        String diretorio = "C:\\Claro\\dev\\residential-orch-serviceorders-v1\\k8s";

        renomearArquivos(new File(diretorio));
    }

    public static void renomearArquivos(File pasta) {
        // Lista todos os arquivos e pastas no diret�rio
        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isDirectory()) {
                    // Se for uma pasta, chama a fun��o recursivamente
                    renomearArquivos(arquivo);
                } else if (arquivo.getName().contains("ms-poc") && (arquivo.getName().endsWith(".yml") 
                		|| arquivo.getName().endsWith(".yaml"))) {
                    // Renomeia arquivos que cont�m "ms-poc" e t�m extens�o ".yml"
                    String novoNome = arquivo.getName().replace("ms-poc", "residential-orch-serviceorders-v1");
                    File novoArquivo = new File(arquivo.getParent(), novoNome);
                    if (arquivo.renameTo(novoArquivo)) {
                        System.out.println("Renomeado: " + arquivo.getPath() + " para " + novoArquivo.getPath());
                    } else {
                        System.out.println("Falha ao renomear: " + arquivo.getPath());
                    }
                }
            }
        }
    }
}