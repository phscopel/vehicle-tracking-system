package model.automovel;

import java.time.LocalDate;

/**
 * Automóvel da categoria grande.
 * <p>
 * Aplica redução de 2% ao ano sobre o valor base da diária,
 * com limite máximo de 8% de desconto.
 */
public class AutomovelGrande extends Automovel {

    /**
     * Cria um automóvel grande com os dados fornecidos.
     *
     * @param placa           placa de identificação do veículo
     * @param anoModelo       ano de fabricação do modelo
     * @param valorBaseDiaria valor base da diária antes de descontos
     */
    public AutomovelGrande(String placa, int anoModelo, Double valorBaseDiaria) {
        super(placa, anoModelo, valorBaseDiaria);
    }

    /**
     * Calcula o valor da diária aplicando depreciação de 2% ao ano,
     * limitada a no máximo 8%.
     *
     * @param dataReferencia data usada para calcular a idade do modelo
     * @return valor da diária com desconto aplicado
     */
    @Override
    public double calcularValorDiariaAtual(LocalDate dataReferencia) {
        return valorBaseDiaria * (1- Math.min(calculaidadeModelo(dataReferencia)* 0.02, 0.08));
    }
}