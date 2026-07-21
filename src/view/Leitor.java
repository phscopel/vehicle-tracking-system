package view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
/**
 * Utilitário de leitura de entradas do console.
 * <p>
 * Serve para receber dados do usuário usando scanner.nextLine() para evitar problemas de buffer.
 * Todos os métodos repetem a leitura em loop até receber uma entrada válida.
 */

public class Leitor {

    private final Scanner scanner;

    /**
     * Cria um leitor relacionado ao scanner fornecido.
     *
     * @param scanner scanner de entrada do console
     */
    public Leitor(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Lê uma string do console.
     * <p>
     * Repete a leitura enquanto o campo estiver vazio.
     *
     * @param mensagem texto exibido ao usuário antes da leitura
     * @return string fornecida pelo usuário
     */
    public String lerString(String mensagem) {
        String valor;
        do {
            System.out.print(mensagem);
            valor = scanner.nextLine().trim();
            if (valor.isEmpty()) System.out.println("Campo obrigatório.");
        } while (valor.isEmpty());
        return valor;
    }

    /**
     * Lê um número inteiro do console.
     * <p>
     * Repete a leitura enquanto a entrada não for um inteiro válido.
     *
     * @param mensagem texto exibido ao usuário antes da leitura
     * @return valor inteiro fornecido pelo usuário
     */
    public int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    /**
     * Lê um número decimal positivo do console.
     * <p>
     * Repete a leitura enquanto a entrada não for um decimal válido.
     *
     * @param mensagem texto exibido ao usuário antes da leitura
     * @return valor decimal fornecido pelo usuário
     */
    public double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());
                if (valor <= 0) {
                    System.out.println("O valor deve ser positivo.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número (ex: 150.00).");
            }
        }
    }

    /**
     * Lê uma data do console no formato AAAA-MM-DD.
     * <p>
     * Repete a leitura enquanto a entrada não for uma data válida.
     *
     * @param mensagem texto exibido ao usuário antes da leitura
     * @return data fornecida pelo usuário como LocalDate
     */
    public LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Use o formato AAAA-MM-DD (ex: 2025-06-10).");
            }
        }
    }
}