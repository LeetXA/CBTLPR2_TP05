# Trabalho Prático 5 — Java (Threads)

Atividade de LPR2 (Java) — ADS 471.

## O que o programa faz

- Permite informar o valor máximo da contagem.
- Permite escolher a prioridade de cada Thread, de 1 a 10.
- Executa duas Threads simultaneamente.
- Cada Thread preenche sua própria lista com os números de 1 até o valor máximo.
- Exibe o texto **Final** somente quando a respectiva Thread termina.
- O botão **Limpar** apaga as listas e restaura os valores iniciais.


O programa usa `Thread.setPriority()` com os valores escolhidos (1 a 10). A prioridade é uma indicação ao escalonador da JVM/SO; ela não garante que uma Thread terminará antes da outra. O pequeno intervalo entre atualizações ajuda a visualizar as duas contagens.
