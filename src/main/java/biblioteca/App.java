package biblioteca;

import biblioteca.controlador.BibliotecaController;
import biblioteca.modelo.Catalogo;
import biblioteca.modelo.ServicioPrestamos;
import biblioteca.vista.BibliotecaView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Catalogo catalogo = new Catalogo();
        ServicioPrestamos servicio = new ServicioPrestamos();
        BibliotecaView vista = new BibliotecaView();
        BibliotecaController controlador = new BibliotecaController(catalogo, servicio, vista);

        Rectangle2D pantalla = Screen.getPrimary().getVisualBounds();
        double ancho = Math.min(1150, pantalla.getWidth() - 20);
        double alto = Math.min(600, pantalla.getHeight() - 70);

        stage.setScene(new Scene(vista.getRaiz(), ancho, alto));
        stage.setTitle("Biblioteca");
        stage.centerOnScreen();
        stage.show();
    }
}
