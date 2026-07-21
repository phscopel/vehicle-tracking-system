package model.automovel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;

/**
 * Classe abstrata que representa um automóvel no sistema de locadora.
 * <p>
 * Define os atributos comuns a todos os veículos e declara o método abstrato
 * de cálculo de diária, que deve ser implementado por cada subclasse.
 */
public abstract class Automovel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String placa;
    protected int anoModelo;
    protected Double valorBaseDiaria;
    private boolean disponivel;

    /**
     * Cria um automóvel com os dados fornecidos, marcado como disponível por padrão.
     *
     * @param placa          placa de identificação do veículo
     * @param anoModelo      ano de fabricação do modelo
     * @param valorBaseDiaria valor base da diária antes de descontos
     */
    public Automovel(String placa, int anoModelo, Double valorBaseDiaria) {
        this.placa = placa;
        this.anoModelo = anoModelo;
        this.valorBaseDiaria = valorBaseDiaria;
        this.disponivel = true;
    }

    public String getPlaca() {
        return placa;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    /**
     * Marca o automóvel como indisponível.
     */
    public void ocupar() {
        this.disponivel = false;
    }

    /**
     * Marca o automóvel como disponível.
     */
    public void desocupar() {
        this.disponivel = true;
    }

    /**
     * Metodo abstrato que calcula o valor da diária na data de referência, aplicando as regras de
     * depreciação específicas de cada categoria.
     *
     * @param dataReferencia data usada para calcular a idade do modelo
     * @return valor da diária com desconto aplicado
     */
    public abstract double calcularValorDiariaAtual(LocalDate dataReferencia);

    /**
     * Calcula a idade do modelo em anos completos em relação à data de referência.
     *
     * @param dataReferencia data de referência para o cálculo
     * @return quantidade de anos desde o ano do modelo
     */
    protected int calculaidadeModelo(LocalDate dataReferencia){
        return dataReferencia.getYear() - anoModelo;
    }


    @Override
    public String toString() {
        return String.format("[%s] Placa: %s | Ano: %d | %s",
                getClass().getSimpleName(), placa, anoModelo,
                disponivel ? "Disponível" : "Indisponível");
    }
}