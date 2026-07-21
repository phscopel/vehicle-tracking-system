Sistema de gerenciamento de uma locadora de veículos, desenvolvido em Java para fins acadêmicos. Permite cadastrar clientes e automóveis, registrar locações e devoluções, e calcular valores de diária com depreciação por categoria e multa por atraso. Os dados são persistidos localmente via serialização Java.

## Índice

- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Estrutura de Pacotes](#estrutura-de-pacotes)
- [Regras de Negócio](#regras-de-negócio)
- [Tratamento de Exceções](#tratamento-de-exceções)
- [Persistência](#persistência)
- [Como Executar](#como-executar)
- [Requisitos](#requisitos)

## Funcionalidades

O sistema é operado via menu de console com as seguintes opções:

1. **Cadastrar Cliente** — nome e CPF (usado como identificador único)
2. **Cadastrar Automóvel** — categoria, placa, ano do modelo e valor base da diária
3. **Listar Clientes** — exibe todos os clientes cadastrados
4. **Listar Automóveis** — exibe todos os automóveis cadastrados, com status de disponibilidade
5. **Registrar Locação** — associa um cliente a um automóvel disponível, com data de locação e data prevista de devolução
6. **Registrar Devolução** — encerra uma locação, calcula o valor total (com multa, se houver) e exibe o resumo
7. **Sair** — salva o estado do sistema e encerra a aplicação

## Arquitetura

O projeto segue uma separação em camadas:

- **model** — entidades de domínio e regras de negócio (`Automovel`, `Locadora`, `Cliente`, `Locacao`)
- **repository** — camada de persistência, abstraída por interface (`LocadoraDAO`)
- **view** — interface de usuário via console (`Menu`, `Leitor`)
- **exception** — exceções de negócio específicas do domínio

A classe `Locadora` centraliza toda a lógica de negócio e é o único ponto de entrada para manipulação de clientes, automóveis e locações, sendo serializada como um objeto único para garantir consistência na persistência (tudo é salvo junto, ou nada é).

## Estrutura de Pacotes

```
src/
├── Main.java
├── exception/
│   ├── AutomovelDuplicadoException.java
│   ├── AutomovelIndisponivelException.java
│   ├── ClienteDuplicadoException.java
│   ├── ClienteNaoEncontradoException.java
│   ├── DataInvalidaException.java
│   └── LocacaoNaoEncontradaException.java
├── model/
│   ├── automovel/
│   │   ├── Automovel.java          (classe abstrata)
│   │   ├── AutomovelPopular.java
│   │   ├── AutomovelMedio.java
│   │   ├── AutomovelGrande.java
│   │   └── Categoria.java          (enum)
│   └── locadora/
│       ├── Locadora.java
│       ├── Cliente.java
│       └── Locacao.java
├── repository/
│   ├── LocadoraDAO.java            (interface)
│   └── LocadoraSerializableDAO.java
└── view/
    ├── Menu.java
    └── Leitor.java
```

## Regras de Negócio

### Categorias de automóvel e depreciação da diária

Cada categoria aplica uma taxa de depreciação anual sobre o valor base da diária, com um teto máximo de desconto:

| Categoria | Desconto ao ano | Desconto máximo |
|-----------|------------------|------------------|
| Popular   | 7%               | 21%              |
| Médio     | 5%               | 15%              |
| Grande    | 2%               | 8%               |

O valor da diária é recalculado com base na idade do modelo em relação à data de referência da locação.

### Locação

- O CPF do cliente deve corresponder a um cliente cadastrado.
- O automóvel deve existir e estar disponível.
- A data de locação não pode ser anterior à data atual.
- A data prevista de devolução deve ser posterior à data de locação.
- Ao ser criada, a locação marca o automóvel como indisponível.

### Devolução

- Deve existir uma locação aberta para a placa informada.
- A data real de devolução deve ser posterior à data de locação.
- O automóvel volta a ficar disponível.
- É calculada uma multa de atraso equivalente a 10% da diária vigente por dia de atraso, somada ao valor base da locação (dias locados × diária vigente na data de locação).

## Tratamento de Exceções

O sistema usa exceções checadas específicas para cada regra de negócio violada, tratadas na camada de view com blocos `catch` multi-tipo:

| Exceção | Quando é lançada |
|---|---|
| `ClienteDuplicadoException` | Cadastro de cliente com CPF já existente |
| `AutomovelDuplicadoException` | Cadastro de automóvel com placa já existente |
| `ClienteNaoEncontradoException` | CPF informado não corresponde a nenhum cliente |
| `AutomovelIndisponivelException` | Placa inexistente ou automóvel já locado |
| `DataInvalidaException` | Datas de locação/devolução que violam as regras de negócio |
| `LocacaoNaoEncontradaException` | Devolução de automóvel sem locação aberta |

## Persistência

Os dados são persistidos por serialização Java em um único arquivo `dados.dat`, gerado no diretório de execução. Toda a `Locadora` (clientes, automóveis, locações abertas e histórico) é salva como um único objeto serializável, garantindo atomicidade: ao carregar, se o arquivo não existir ou ocorrer erro de leitura, o sistema é iniciado com coleções vazias.

## Como Executar


```bash
# Compilar
cd src
javac -d ../out $(find . -name "*.java")

# Executar
cd ../out
java Main
```

Ao encerrar (opção 7 do menu), o estado do sistema é salvo automaticamente em `dados.dat`.

## Requisitos

- Java 17+ (uso de `java.time` e `Serial`)
- Nenhuma dependência externa

## Melhorias Futuras

Algumas possibilidades de evolução para o sistema:

- **Edição e remoção de cliente/automóvel** — atualmente o sistema só permite cadastro e listagem, sem suporte a atualização ou exclusão de registros
- **Busca individual** — consultar cliente por CPF ou automóvel por placa diretamente, sem precisar listar todos os registros
- **Histórico de locações por cliente** — a `Locadora` já mantém um histórico interno de locações encerradas, mas essa informação ainda não é exibida no menu
- **Migração da persistência para banco de dados relacional** — a interface `LocadoraDAO` já foi projetada para permitir trocar a implementação de serialização por outra (JDBC, JPA, etc.) sem alterar a lógica de negócio
- **Testes automatizados (JUnit)** — cobertura de testes unitários para as regras de cálculo de diária, multa e validações de data
- **Interface gráfica** — substituição do menu via console por uma interface Swing ou JavaFX