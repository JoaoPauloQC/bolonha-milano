package bolonhaemilano;

import java.util.Scanner;

/**
 * Controla o laco principal do jogo: exibe as opcoes, le e valida a escolha
 * do jogador e delega o trabalho para as outras classes.
 */
public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;
    private Governo governo;

    public Menu() {
        scanner = new Scanner(System.in);
        emExecucao = true;
        mercado = new Mercado();
        agricultura = new Agricultura();
        governo = new Governo();
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
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private int lerOpcao() {
        if (!scanner.hasNextLine()) {
            return 0; // fim da entrada: encerra o jogo / volta
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
                abrirMercado();
                break;
            case 4:
                abrirAgricultura();
                break;
            case 5:
                abrirGoverno();
                break;
            case 6:
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

    // ===================== MERCADO (Etapa 3) =====================

    private void abrirMercado() {
        if (cidade == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return;
        }
        boolean noMercado = true;
        while (noMercado) {
            System.out.println();
            System.out.println("--------------- MERCADO ---------------");
            System.out.println("Preco do grao: " + mercado.getPrecoGrao() + " florins | Preco da terra: "
                    + mercado.getPrecoTerra() + " florim/hectare");
            System.out.println("Florins: " + cidade.getFlorins() + " | Graos: " + cidade.getGraos()
                    + " | Terras: " + cidade.getTerras() + " ha");
            System.out.println("1 - Comprar graos");
            System.out.println("2 - Vender graos");
            System.out.println("3 - Comprar terras");
            System.out.println("4 - Vender terras");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    comprarGraos();
                    break;
                case 2:
                    venderGraos();
                    break;
                case 3:
                    comprarTerras();
                    break;
                case 4:
                    venderTerras();
                    break;
                case 0:
                    noMercado = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
            }
        }
    }

    private int lerQuantidade() {
        System.out.print("Quantidade: ");
        if (!scanner.hasNextLine()) {
            return -1;
        }
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void comprarGraos() {
        int quantidade = lerQuantidade();
        if (mercado.comprarGraos(cidade, quantidade)) {
            System.out.println("Compra realizada: +" + quantidade + " graos.");
        } else {
            System.out.println("Compra recusada: quantidade invalida ou florins insuficientes.");
        }
    }

    private void venderGraos() {
        int quantidade = lerQuantidade();
        if (mercado.venderGraos(cidade, quantidade)) {
            System.out.println("Venda realizada: -" + quantidade + " graos.");
        } else {
            System.out.println("Venda recusada: quantidade invalida ou graos insuficientes.");
        }
    }

    private void comprarTerras() {
        int quantidade = lerQuantidade();
        if (mercado.comprarTerras(cidade, quantidade)) {
            System.out.println("Compra realizada: +" + quantidade + " hectares.");
        } else {
            System.out.println("Compra recusada: quantidade invalida ou florins insuficientes.");
        }
    }

    private void venderTerras() {
        int quantidade = lerQuantidade();
        if (mercado.venderTerras(cidade, quantidade)) {
            System.out.println("Venda realizada: -" + quantidade + " hectares.");
        } else {
            System.out.println("Venda recusada: quantidade invalida ou terras insuficientes.");
        }
    }

    // ===================== AGRICULTURA (Etapa 4) =====================

    private void abrirAgricultura() {
        if (cidade == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return;
        }
        boolean naAgricultura = true;
        while (naAgricultura) {
            System.out.println();
            System.out.println("------------- AGRICULTURA -------------");
            System.out.println("Terras: " + cidade.getTerras() + " ha | Servos: " + cidade.getServos()
                    + " | Graos: " + cidade.getGraos());
            System.out.println("1 - Colher (processar safra)");
            System.out.println("2 - Distribuir alimentos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    colher();
                    break;
                case 2:
                    distribuirAlimentos();
                    break;
                case 0:
                    naAgricultura = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
            }
        }
    }

    private void colher() {
        int hectares = agricultura.processarColheita(cidade);
        System.out.println("Colheita feita: " + hectares + " hectares cultivados. Graos em estoque: "
                + cidade.getGraos());
    }

    private void distribuirAlimentos() {
        System.out.print("Percentual dos graos a distribuir (20 a 80): ");
        int percentual = lerOpcao();
        int servosAntes = cidade.getServos();
        if (agricultura.distribuirAlimentos(cidade, percentual)) {
            System.out.println("Alimentos distribuidos. Servos: " + servosAntes + " -> " + cidade.getServos()
                    + " | Graos restantes: " + cidade.getGraos());
        } else {
            System.out.println("Distribuicao recusada: o percentual deve ficar entre 20 e 80.");
        }
    }

    // ===================== GOVERNO (Etapa 5) =====================

    private void abrirGoverno() {
        if (cidade == null) {
            System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro.");
            return;
        }
        boolean noGoverno = true;
        while (noGoverno) {
            System.out.println();
            System.out.println("--------------- GOVERNO ---------------");
            System.out.println("Florins: " + cidade.getFlorins() + " | Taxa de imposto: " + cidade.getTaxaImposto()
                    + "% | Justica: " + cidade.getNivelJustica());
            System.out.println("1 - Arrecadar impostos");
            System.out.println("2 - Definir taxa de imposto");
            System.out.println("3 - Definir nivel de justica");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            int opcao = lerOpcao();
            switch (opcao) {
                case 1:
                    arrecadarImpostos();
                    break;
                case 2:
                    definirTaxaImposto();
                    break;
                case 3:
                    definirJustica();
                    break;
                case 0:
                    noGoverno = false;
                    break;
                default:
                    System.out.println("Opcao invalida. Tente novamente.");
                    break;
            }
        }
    }

    private void arrecadarImpostos() {
        int arrecadado = governo.arrecadarImpostos(cidade);
        System.out.println("Impostos arrecadados: " + arrecadado + " florins. Florins: " + cidade.getFlorins());
    }

    private void definirTaxaImposto() {
        System.out.print("Nova taxa de imposto (0 a 50): ");
        int taxa = lerOpcao();
        if (governo.definirTaxaImposto(cidade, taxa)) {
            System.out.println("Taxa de imposto definida em " + taxa + "%.");
        } else {
            System.out.println("Taxa recusada: deve ficar entre 0 e 50.");
        }
    }

    private void definirJustica() {
        System.out.println("1 - Justa (arrecadacao x0,8)");
        System.out.println("2 - Moderada (arrecadacao x1,0)");
        System.out.println("3 - Severa (arrecadacao x1,3)");
        System.out.print("Escolha o nivel de justica: ");
        int opcao = lerOpcao();
        String nivel;
        switch (opcao) {
            case 1:
                nivel = "Justa";
                break;
            case 2:
                nivel = "Moderada";
                break;
            case 3:
                nivel = "Severa";
                break;
            default:
                nivel = "";
                break;
        }
        if (governo.definirJustica(cidade, nivel)) {
            System.out.println("Nivel de justica definido: " + nivel + ".");
        } else {
            System.out.println("Opcao invalida: o nivel de justica nao foi alterado.");
        }
    }

    private void exibirSobre() {
        System.out.println();
        System.out.println("Bolonha e Milano e um jogo de estrategia economica e politica");
        System.out.println("ambientado na Italia medieval. Cada jogador governa uma");
        System.out.println("cidade-Estado e decide como usar seus recursos para desenvolver");
        System.out.println("o territorio e conquistar titulos de nobreza, ate Rei ou Rainha.");
    }
}
