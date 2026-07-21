package model.locadora;

import model.automovel.Automovel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Representa uma locação de automóvel realizada por um cliente.
 * <p>
 * Armazena as datas de locação e devolução, calcula os valores
 * devidos e exibe o resumo financeiro ao encerrar a locação.
 * Ao ser criada, marca o automóvel como indisponível automaticamente.
 */
public class Locacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Cliente cliente;
    private Automovel automovel;
    private LocalDate dataLocacao;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataRealDevolucao;

    /**
     * Cria uma locação e marca o automóvel como indisponível.
     *
     * @param cliente               cliente que está realizando a locação
     * @param automovel             automóvel a ser locado
     * @param dataLocacao           data de início da locação
     * @param dataPrevistaDevolucao data prevista para a devolução
     */
    public Locacao(Cliente cliente, Automovel automovel, LocalDate dataLocacao, LocalDate dataPrevistaDevolucao) {
        this.cliente = cliente;
        this.automovel = automovel;
        this.dataLocacao = dataLocacao;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataRealDevolucao = null;
        this.automovel.ocupar();
    }

    public LocalDate getDataLocacao() {
        return dataLocacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    /**
     * Registra a devolução do automóvel e o marca como disponível.
     *
     * @param dataDevolucao data em que o automóvel foi efetivamente devolvido
     */
    public void registrarDevolucao(LocalDate dataDevolucao) {
        this.dataRealDevolucao = dataDevolucao;
        automovel.desocupar();
    }

    /**
     * Calcula a quantidade de dias locados com base na data real de devolucao
     *
     * @return quantidade de dias locados
     */
    private int calcularDiasLocados() {
        return (int) ChronoUnit.DAYS.between(dataLocacao, dataRealDevolucao);
    }

    /**
     * Calcula a quantidade de dias de atraso com base na data real de devolucao
     *
     * @return quantidade de dias de atraso
     */
    public int calcularDiasAtraso() {
        return Math.max(
                (int) ChronoUnit.DAYS.between(dataPrevistaDevolucao, dataRealDevolucao),
                0
        );
    }

    private double calcularValorBase() {
        return calcularDiasLocados() * automovel.calcularValorDiariaAtual(dataLocacao);
    }

    /**
     * Calcula o valor da multa de atraso com base na data real de devolucao
     *
     * @return valor da multa a pagar
     */
    public double calcularMultaAtraso() {
        return calcularDiasAtraso() * automovel.calcularValorDiariaAtual(dataLocacao) * 0.1;
    }

    /**
     * Calcula o valor total da locação, somando o valor base e a multa por atraso.
     *
     * @return valor total a ser cobrado do cliente
     */
    public double calcularValorTotal() {
        return calcularValorBase() + calcularMultaAtraso();
    }

}