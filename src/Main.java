import model.locadora.Locadora;
import repository.LocadoraDAO;
import repository.LocadoraSerializableDAO;
import view.Menu;

/**
 * Ponto de entrada do sistema.
 * <P>
 * Responsável por inicializar a persistência, carregar os dados,
 * executar o menu e salvar o estado ao encerrar.
 */
public class Main {

    /**
     * Método principal da aplicação.
     * <p>
     * Carrega os dados persistidos, executa o menu e salva o estado ao sair.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {

        LocadoraDAO dao = new LocadoraSerializableDAO();

        Locadora locadora = dao.carregar();

        Menu menu = new Menu(locadora);

        menu.executar();

        dao.salvar(locadora);

    }
}