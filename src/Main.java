import controller.ContactoController;
import view.ContactoView;

public class Main {
    public static void main(String[] args) {
        // Crear instancia de la vista
        ContactoView vista = new ContactoView();

        // Crear instancia del controlador, pasando la vista
        ContactoController controlador = new ContactoController(vista);

        // Hacer visible la ventana
        vista.setVisible(true);
    }
}