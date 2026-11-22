package bolonhaemilano;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * EXTRA: salva e carrega uma Partida em um arquivo de texto.
 *
 * Formato (uma informacao por linha, campos separados por ';'):
 *   BOLONHA_E_MILANO v1
 *   vez=<indice do jogador da vez>
 *   cidade=nome;jogador;nivelTitulo;ano;florins;terras;graos;servos;taxa;justica;
 *          soldados;construcoes;colheitaFeita;impostosArrecadados
 *   historico=<indice da cidade>;ano;titulo;prestigio;florins;graos;servos;soldados;construcoes
 */
public class Persistencia {

    public static final String ARQUIVO_PADRAO = "partida-salva.txt";
    private static final String CABECALHO = "BOLONHA_E_MILANO v1";
    private static final String SEPARADOR = ";";

    /**
     * Grava a partida no arquivo. Devolve false se nao conseguiu gravar.
     */
    public boolean salvar(Partida partida, String caminho) {
        try (PrintWriter saida = new PrintWriter(new FileWriter(caminho))) {
            saida.println(CABECALHO);
            saida.println("vez=" + partida.getIndiceAtual());
            List<Cidade> cidades = partida.getCidades();
            for (Cidade c : cidades) {
                saida.println("cidade=" + String.join(SEPARADOR,
                        limpar(c.getNome()), limpar(c.getDono().getNome()),
                        "" + c.getDono().getNivelTitulo(), "" + c.getAnoAtual(),
                        "" + c.getFlorins(), "" + c.getTerras(), "" + c.getGraos(), "" + c.getServos(),
                        "" + c.getTaxaImposto(), c.getNivelJustica(),
                        "" + c.getSoldados(), "" + c.getConstrucoes(),
                        "" + c.isColheitaFeita(), "" + c.isImpostosArrecadados()));
            }
            for (int i = 0; i < cidades.size(); i++) {
                for (RegistroAnual r : cidades.get(i).getHistorico()) {
                    saida.println("historico=" + String.join(SEPARADOR,
                            "" + i, "" + r.getAno(), r.getTitulo(), "" + r.getPrestigio(),
                            "" + r.getFlorins(), "" + r.getGraos(), "" + r.getServos(),
                            "" + r.getSoldados(), "" + r.getConstrucoes()));
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Le a partida do arquivo. Devolve null se o arquivo nao existe ou esta invalido.
     */
    public Partida carregar(String caminho) {
        try (BufferedReader entrada = new BufferedReader(new FileReader(caminho))) {
            if (!CABECALHO.equals(entrada.readLine())) {
                return null;
            }
            Partida partida = new Partida();
            List<Cidade> lidas = new ArrayList<>();
            int vez = 0;
            String linha;
            while ((linha = entrada.readLine()) != null) {
                if (linha.startsWith("vez=")) {
                    vez = Integer.parseInt(linha.substring(4).trim());
                } else if (linha.startsWith("cidade=")) {
                    Cidade cidade = lerCidade(linha.substring(7).split(SEPARADOR));
                    if (!partida.adicionarCidade(cidade)) {
                        return null;
                    }
                    lidas.add(cidade);
                } else if (linha.startsWith("historico=")) {
                    String[] campos = linha.substring(10).split(SEPARADOR);
                    int indice = Integer.parseInt(campos[0]);
                    lidas.get(indice).registrarAno(new RegistroAnual(
                            Integer.parseInt(campos[1]), campos[2], Integer.parseInt(campos[3]),
                            Integer.parseInt(campos[4]), Integer.parseInt(campos[5]),
                            Integer.parseInt(campos[6]), Integer.parseInt(campos[7]),
                            Integer.parseInt(campos[8])));
                }
            }
            if (lidas.isEmpty() || !partida.restaurarVez(vez)) {
                return null;
            }
            return partida;
        } catch (IOException | RuntimeException e) {
            // arquivo inexistente, numero invalido, campos faltando...
            return null;
        }
    }

    private Cidade lerCidade(String[] campos) {
        Jogador dono = new Jogador(campos[1], Integer.parseInt(campos[2]));
        return new Cidade(campos[0], dono,
                Integer.parseInt(campos[3]), Integer.parseInt(campos[4]), Integer.parseInt(campos[5]),
                Integer.parseInt(campos[6]), Integer.parseInt(campos[7]), Integer.parseInt(campos[8]),
                campos[9], Integer.parseInt(campos[10]), Integer.parseInt(campos[11]),
                Boolean.parseBoolean(campos[12]), Boolean.parseBoolean(campos[13]));
    }

    /**
     * Tira o separador dos nomes digitados, para nao quebrar o arquivo.
     */
    private String limpar(String texto) {
        return texto.replace(SEPARADOR, ",");
    }
}
