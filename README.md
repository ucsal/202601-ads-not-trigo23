---

## Funcionalidades

* Cadastro de participantes
* Cadastro de provas
* Cadastro de questões com alternativas (A–E)
* Aplicação de provas
* Registro de tentativas
* Cálculo de nota por acertos
* Listagem de tentativas realizadas

---

## Estrutura do projeto

O código foi organizado em camadas simples:

* **Entidades (Model)** → representam os dados
* **Services** → concentram as regras de negócio
* **BancoMemoria** → simula persistência em memória
* **App** → responsável pela interação com o usuário

---

## Aplicação dos princípios SOLID

Durante o desenvolvimento, foram considerados os princípios SOLID para melhorar a organização e manutenção do código.

### SRP — Single Responsibility Principle

Cada classe possui uma única responsabilidade:

* Entidades → apenas dados
* Services → regras de negócio

---

### OCP — Open/Closed Principle

O sistema foi estruturado para permitir extensões sem alterar código existente.

Exemplo:

* É possível criar novas formas de cálculo de nota sem modificar as classes atuais.

---

### LSP — Liskov Substitution Principle

As implementações respeitam os contratos definidos, permitindo substituição sem quebrar o sistema.

---

### ISP — Interface Segregation Principle

Interfaces foram mantidas simples e específicas, evitando obrigar classes a implementar métodos desnecessários.

---

### DIP — Dependency Inversion Principle

As dependências são feitas com base em abstrações, não implementações concretas.

Exemplo:

* Uso de uma interface para cálculo de notas (`CalculadoraNota`)

---

## Ajustes realizados

Algumas correções importantes foram feitas durante o desenvolvimento:

* Inclusão de getters e setters nas entidades
* Correção de construtores inexistentes
* Geração automática de IDs
* Validação de alternativas (A–E)
* Inicialização correta de listas
* Correção de chamadas de métodos inexistentes
* Organização da lógica de cálculo de notas

---
S — Single Responsibility Principle (Responsabilidade Única)

Cada classe possui uma única responsabilidade bem definida:

Participante, Prova, Questao, Tentativa, Resposta
→ Responsáveis apenas por armazenar dados
ParticipanteService
→ Responsável pelo cadastro e validação de participantes
ProvaService
→ Gerencia criação de provas
QuestaoService
→ Cadastro e busca de questões
TentativaService
→ Controle de tentativas de prova
CalculadoraNotas
→ Responsável apenas pelo cálculo da nota
App
→ Interface com o usuário (menu e fluxo do sistema)
 O — Open/Closed Principle (Aberto/Fechado)

O sistema permite extensão sem necessidade de modificar código existente.

Exemplo:

A classe CalculadoraNotas pode ser substituída por outra estratégia de cálculo sem alterar o restante do sistema.
 L — Liskov Substitution Principle

As classes respeitam seus contratos, permitindo substituição sem quebrar o funcionamento.

Exemplo:

Qualquer classe que implemente a lógica de cálculo pode substituir a atual sem afetar o sistema.
 I — Interface Segregation Principle

Interfaces são específicas e não obrigam implementação de métodos desnecessários.

Exemplo:

IQuestao define apenas o necessário para comportamento de questão.
 D — Dependency Inversion Principle

As classes dependem de abstrações e recebem dependências externamente.

Exemplo:

Services recebem o BancoMemoria via construtor:
public ParticipanteService(BancoMemoria banco)

Isso reduz acoplamento e facilita testes.

Correções e melhorias realizadas

Durante o desenvolvimento, alguns problemas foram identificados e corrigidos:
 Correções estruturais
Adição de getters e setters nas entidades
Criação de construtores ausentes
Inicialização de listas (ex: respostas em Tentativa)
 Correções de lógica
Ajuste no cadastro de participante (ID agora automático)
Correção na busca de questões por prova
Validação de entrada do usuário
Tratamento de erros com try/catch
 Correções na classe App
Remoção de código desnecessário (ID manual de participante)
Substituição de var por tipos explícitos
Melhor validação na escolha de participante e prova
Evitado erro com método inexistente (getFenInicial)
Melhor organização do fluxo da aplicação
 Validação de alternativas
Implementação de método para garantir respostas entre A–E
 Estrutura do sistema
App (interface)
│
├── Services (regras de negócio)
│   ├── ParticipanteService
│   ├── ProvaService
│   ├── QuestaoService
│   └── TentativaService
│
├── Entidades (dados)
│   ├── Participante
│   ├── Prova
│   ├── Questao
│   ├── Tentativa
│   └── Resposta
│
└── Infra
    └── BancoMemoria
▶️ Execução

Compilar:

javac *.java

Executar:

java App


Observação : Esqueci de commitar enquanto criava e refatorava dai tive que fazer no final o que me gerou muito retrabalho perdão
---