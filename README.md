# 📊 Projeto de Ordenação — Pesquisa e Ordenação

> Trabalho acadêmico de Programação Orientada com foco na implementação de **algoritmos de ordenação** em arquivos binários e listas encadeadas, medindo comparações, movimentações e tempo de execução.

---

## 📁 Estrutura do Projeto

/src │ ├── Arquivo/ # Métodos de ordenação em arquivo binário │ ├── Arquivo.java │ └── Registro.java │ ├── Lista/ # Métodos com lista encadeada │ ├── Lista.java │ └── No.java │ └── Main.java # Classe principal: geração de arquivos e execução dos testes


---

## 🚀 Funcionalidades

✅ Implementação de 17 algoritmos de ordenação:  
- Inserção Direta / Binária  
- Seleção Direta  
- Bubble, Shake, Shell  
- Heap  
- QuickSort (com e sem pivô)  
- Merge (duas versões)  
- Counting, Bucket, Radix  
- Comb, Gnome, TimSort  

✅ Suporte a:
- Arquivos binários com 1024 registros (ordenado, reverso e randômico)
- Registro com campo `numero` e `lixo` (preenchimento fixo)
- Comparações e movimentações programadas (reais)
- Equações teóricas de complexidade
- Tempo de execução (em milissegundos)

✅ Geração automática da tabela `resultado_tabela.txt` com todos os dados.

---

## 📊 Exemplo da Tabela Gerada

```txt
Metodo                  Tempo(ms)   CompProg  CompEq    MovProg   MovEq
--------------------------------------------------------------------------
insercaoDireta (Ord)        1121        127       8128        254      16256
bubble (Rev)              103797       8256       8256      16512      24768
quickSPivo (Rand)           10325       1187          -        610          -
🧠 Objetivos Didáticos
Compreender o funcionamento interno de algoritmos de ordenação

Aprender a manipular arquivos binários em Java

Avaliar desempenho empírico versus teórico

Trabalhar com listas encadeadas e simular acesso posicional

🛠️ Tecnologias
💻 Java 11+

🗃️ Arquivos binários (RandomAccessFile)

⏱️ System.currentTimeMillis() para cronometragem

📄 Escrita de arquivo .txt com PrintWriter

🤝 Feito por
Desenvolvido como parte do 1º Bimestre da disciplina de Pesquisa e Ordenação —
Professor Francisco Assis da Silva

Alunos:

Lucas Alexandre da silva

Beatriz de Oliveira

📌 Como Executar
Compile com o javac

Rode Main.java

Veja o resultado no arquivo resultado_tabela.txt

📎 Licença
Projeto acadêmico. Uso livre para fins educacionais.
