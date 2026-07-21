package model.automovel;

/**
 * Enumeração das categorias de automóveis disponíveis no sistema.
 * <p>
 * Cada categoria aplica uma taxa de depreciação e um limite máximo de
 * desconto diferentes no cálculo da diária.
 */
public enum Categoria {

    /** Categoria popular: desconto de 7% ao ano, limite de 21%. */
    POPULAR,

    /** Categoria médio: desconto de 5% ao ano, limite de 15%. */
    MEDIO,

    /** Categoria grande: desconto de 2% ao ano, limite de 8%. */
    GRANDE
}