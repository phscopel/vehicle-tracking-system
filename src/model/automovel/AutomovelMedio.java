package model.automovel;

import java.time.LocalDate;

/**
 * Automóvel da categoria médio.
 * <p>
 * Aplica redução de 5% ao ano sobre o valor base da diária,
 * com limite máximo de 15% de desconto.
 */
public class AutomovelMedio extends Automovel {

    /**
     * Cria um automóvel médio com os dados fornecidos.
     *
     * @param placa           placa de identificação do veículo
     * @param anoModelo       ano de fabricação do modelo
     * @param valorBaseDiaria valor base da diária antes de descontos
     */
    public AutomovelMedio(String placa, int anoModelo, Double valorBaseDiaria) {
        super(placa, anoModelo, valorBaseDiaria);
    }

    /**
     * Calcula o valor da diária aplicando depreciação de 5% ao ano,
     * limitada a no máximo 15%.
     *
     * @param dataReferencia data usada para calcular a idade do modelo
     * @return valor da diária com desconto aplicado
     */
    @Override
    public double calcularValorDiariaAtual(LocalDate dataReferencia) {
        return valorBaseDiaria * (1- Math.min(calculaidadeModelo(dataReferencia)* 0.05, 0.15));
    }
}