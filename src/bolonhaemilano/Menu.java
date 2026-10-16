package bolonhaemilano;

import java.util.Scanner;

/**
 * Controla o laco principal do jogo: exibe as opcoes, le e valida a escolha
 * do jogador e decide o que fazer com ela.
 */
public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;

    public Menu() {
        scanner = new Scanner(System.in);
        emExecucao = true;
    }

    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }
        scanner.close();
    }

    private void exibirOpcoes() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("             BOLONHA E MILANO");
        System.out.println("  Um jogo de estrategia economica medieval");
        System.out.println("==========================================");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private int lerOpcao() {
        if (!scanner.hasNextLine()) {
            return 0; // fim da entrada: encerra o jogo
        }
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                iniciarNovoJogo();
                break;
            case 2:
                exibirStatus();
                break;
            case 3:
                exibirSobre();
                break;
            case 0:
                System.out.println("Ate a proxima!");
                emExecucao = false;
                break;
            default:
                System.out.println("Opcao invalida. Tente novamente.");
                break;
        }
    }

    private void iniciarNovoJogo() {
        System.out.print("Digite o nome do jogador: ");
        String nomeJogador = scanner.nextLine().trim();
        if (nomeJogador.isEmpty()) {
            nomeJogador = "Jogador 1";
        }
        System.out.print("Digite o nome da cidade: ");
        String nomeCidade = scanner.nextLine().trim();
        if (nomeCidade.isEmpty()) {
            nomeCidade = "Bolonha";
        }

        jogador = new Jogador(nomeJogador);
        cidade = new Cidade(nomeCidade, jogador);
        System.out.println("Novo jogo iniciado!");
        cidade.exibirStatus();
    }

    private void exibirStatus() {
        if (cidade == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return;
        }
        cidade.exibirStatus();
    }

    private void exibirSobre() {
        System.out.println();
        System.out.println("Bolonha e Milano e um jogo de estrategia economica e politica");
        System.out.println("ambientado na Italia medieval. Cada jogador governa uma");
        System.out.println("cidade-Estado e decide como usar seus recursos para desenvolver");
        System.out.println("o territorio e conquistar titulos de nobreza, ate Rei ou Rainha.");
    }
}
