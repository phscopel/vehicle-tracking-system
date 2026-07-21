package view;

import exception.*;
import model.automovel.Automovel;
import model.automovel.Categoria;
import model.locadora.Cliente;
import model.locadora.Locacao;
import model.locadora.Locadora;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Interface de usuário via console.
 * <p>
 * Exibe o menu principal, recebe as escolhas do usuário e chama as devidas funcões
 */

public class Menu {

    private Locadora sistema;
    private Scanner scanner = new Scanner(System.in);
    private Leitor leitor = new Leitor(scanner);

    /**
     * Cria o menu com à locadora fornecida.
     *
     * @param sistema instância da locadora que será operada pelo menu
     */
    public Menu(Locadora sistema) {
        this.sistema = sistema;
    }

    /**
     * Inicia o loop principal do menu, repetindo até o usuário escolher sair.
     */
    public void executar() {

        int opcao;
        do {
            exibirMenu();
            opcao = leitor.lerInt("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    cadastrarAutomovel();
                    break;
                case 3:
                    listarClientes();
                    break;
                case 4:
                    listarAutomoveis();
                    break;
                case 5:
                    registrarLocacao();
                    break;
                case 6:
                    registrarDevolucao();
                    break;
                case 7:
                    System.out.println("Saindo...");
                    break;
            }
        }
        while (opcao != 7);
    }

    private void exibirMenu() {

        System.out.println("\n===== LOCADORA =====");
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Cadastrar Automóvel");
        System.out.println("3 - Listar Clientes");
        System.out.println("4 - Listar Automóveis");
        System.out.println("5 - Registrar Locação");
        System.out.println("6 - Registrar Devolução");
        System.out.println("7 - Sair");
        System.out.println("====================");
    }

    private void cadastrarCliente(){
        String nome = leitor.lerString("Nome: ");

        String cpf = leitor.lerString("CPF: ");

        try {
            sistema.cadastrarCliente(nome, cpf);
            System.out.println("Cliente cadastrado com sucesso.");
        }
        catch (ClienteDuplicadoException e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }

    private void cadastrarAutomovel(){

        System.out.println("Categoria:");
        System.out.println("1 - Popular");
        System.out.println("2 - Médio");
        System.out.println("3 - Grande");

        int opcao;
        Categoria categoria = null;
        do {
            opcao = leitor.lerInt("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    categoria = Categoria.POPULAR;
                    break;
                case 2:
                    categoria = Categoria.MEDIO;
                    break;
                case 3:
                    categoria = Categoria.GRANDE;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }while(opcao < 1 || opcao > 3);

        String placa = leitor.lerString("Placa: ");

        int anoModelo = leitor.lerInt("Ano do modelo: ");

        double valorBase = leitor.lerDouble("Valor base da diaria: ");

        try {
            sistema.cadastrarAutomovel(categoria, placa, anoModelo, valorBase);
            System.out.println("Automóvel cadastrado com sucesso.");
        }
        catch (AutomovelDuplicadoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarClientes() {
        if (sistema.getClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no sistema.");
            return;
        }

        System.out.println("--- LISTA DE CLIENTES ---");
        for (Cliente cliente : sistema.getClientes().values()) {
            System.out.println(cliente.toString());
        }
    }

    private void listarAutomoveis() {
        if (sistema.getAutomoveis().isEmpty()) {
            System.out.println("Nenhum automóvel cadastrado no sistema.");
            return;
        }

        System.out.println("--- LISTA DE AUTOMÓVEIS ---");
        for (Automovel automovel : sistema.getAutomoveis().values()) {
            System.out.println(automovel.toString());
        }
    }

    private void registrarLocacao() {

        String cpf = leitor.lerString("CPF do cliente: ");

        System.out.println("\nVeículos disponíveis:");

        List<Automovel> disponiveis = sistema.listarAutomoveisDisponiveis();

        if (disponiveis.isEmpty()) {
            System.out.println("Não há automóveis disponíveis para locação no momento.");
            return;
        }

        for (Automovel automovel : disponiveis) {
            System.out.println(automovel);
        }

        String placa = leitor.lerString("Placa do veiculo: ");

        LocalDate dataLocacao = leitor.lerData("Data da locação (AAAA-MM-DD): ");

        LocalDate dataPrevista = leitor.lerData("Data prevista de devolução (AAAA-MM-DD): ");

        try {
            sistema.registrarLocacao(cpf, placa, dataLocacao, dataPrevista);
            System.out.println("Locação registrada.");
        }
        catch (ClienteNaoEncontradoException | AutomovelIndisponivelException | DataInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void registrarDevolucao() {

        String placa = leitor.lerString("Placa do veiculo: ");

        LocalDate dataReal = leitor.lerData("Data real de devolução (AAAA-MM-DD): ");

        try {
            Locacao locacao = sistema.registrarDevolucao(placa, dataReal);

            System.out.println("\n===== RESUMO =====");
            System.out.printf("Cliente  : %s%n", locacao.getCliente().getNome());
            System.out.printf("Veículo  : %s%n", locacao.getAutomovel().getPlaca());
            System.out.printf("Total    : R$ %.2f%n", locacao.calcularValorTotal());
            if (locacao.calcularDiasAtraso() > 0) {
                System.out.printf("Multa    : R$ %.2f%n", locacao.calcularMultaAtraso());
            }
        }
        catch (LocacaoNaoEncontradaException | DataInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}