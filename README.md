# Bolonha e Milano

Projeto do 4º bimestre de SPOTDES (IFSP): o jogo **Bolonha e Milano** — estratégia econômica medieval —
implementado em Java, passo a passo, em 8 etapas incrementais.

## Como compilar e executar

Requisitos: Java 17 ou superior (sem build tool externo). A partir da pasta do projeto:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
```

## Estado atual (Etapa 6)

- **Etapa 1:** menu principal em laço; entradas inválidas não travam o programa.
- **Etapa 2:** classes `Jogador` (começa como Cavaleiro) e `Cidade` (começa em 1400).
  "Novo jogo" pergunta os nomes e cria os objetos; "Ver status da cidade" mostra os dados
  (ou avisa que nenhum jogo foi iniciado).
- **Etapa 3:** a cidade ganha florins (1000), terras (10000 ha) e grãos (500). Nova classe
  `Mercado`: compra/venda de grãos (2 florins cada) e terras (1 florim/ha). Nenhum recurso
  fica negativo.
- **Etapa 4:** a cidade ganha servos (200). Nova classe `Agricultura`: colher
  (hectares = min(terras, servos × 5); sementes = hectares / 2; produção = hectares × 2) e
  distribuir alimentos (20% a 80% dos grãos; comida suficiente → servos +5%, senão perde o déficit).
- **Etapa 5:** a cidade ganha taxa de imposto (10%) e nível de justiça (Moderada). Nova classe
  `Governo`: arrecadar (servos × taxa / 100 × 0,8 Justa / 1,0 Moderada / 1,3 Severa), definir
  taxa (0 a 50) e definir justiça.
- **Etapa 6:** a cidade ganha soldados e construções (0). Nova classe `Construcoes`:
  recrutar soldados (5 florins + 1 servo cada, validando os dois recursos antes de debitar)
  e construir (20 florins cada).

Menu: `1` Novo jogo · `2` Ver status · `3` Mercado · `4` Agricultura · `5` Governo ·
`6` Construções · `7` Sobre o jogo · `0` Sair.

## Organização das pastas

| Pasta | Conteúdo |
|-------|----------|
| `src/bolonhaemilano/` | Código-fonte Java (pacote `bolonhaemilano`) |
| `uml/` | Diagrama de classes (`.puml` + imagem `.png`) |
| `casos-de-uso/` | Diagrama de casos de uso (`.puml` + imagem `.png`) |
