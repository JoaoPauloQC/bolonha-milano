# Bolonha e Milano

Projeto do 4º bimestre de SPOTDES (IFSP): o jogo **Bolonha e Milano** — estratégia econômica medieval —
implementado em Java, passo a passo, em 8 etapas incrementais.

## Como compilar e executar

Requisitos: Java 17 ou superior (sem build tool externo). A partir da pasta do projeto:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
```

## Estado atual (Etapa 2)

- **Etapa 1:** menu principal em laço; entradas inválidas não travam o programa.
- **Etapa 2:** classes `Jogador` (começa como Cavaleiro) e `Cidade` (começa em 1400).
  "Novo jogo" pergunta os nomes e cria os objetos; "Ver status da cidade" mostra os dados
  (ou avisa que nenhum jogo foi iniciado).

Menu: `1` Novo jogo · `2` Ver status da cidade · `3` Sobre o jogo · `0` Sair.

## Organização das pastas

| Pasta | Conteúdo |
|-------|----------|
| `src/bolonhaemilano/` | Código-fonte Java (pacote `bolonhaemilano`) |
| `uml/` | Diagrama de classes (`.puml` + imagem `.png`) |
| `casos-de-uso/` | Diagrama de casos de uso (`.puml` + imagem `.png`) |
