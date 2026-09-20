package biblioteca.vista;

import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class BibliotecaView {

    private static final String ESTILO_TARJETA =
            "-fx-background-color: white; -fx-border-color: #D3D1C7; "
                    + "-fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 10;";
    private static final String ESTILO_MENSAJE =
            "-fx-padding: 6 12; -fx-background-radius: 6; -fx-font-weight: bold; ";

    private VBox raiz = new VBox(8);


    private Label lblTitulo = new Label();
    private Label lblResumen = new Label();
    private Label lblMensaje = new Label();


    private TextField txtCodigo = new TextField();
    private TextField txtTitulo = new TextField();
    private TextField txtAutor = new TextField();
    private TextField txtCategoria = new TextField();
    private TextField txtEditorial = new TextField();
    private TextField txtAnio = new TextField();
    private Button btnRegistrar = new Button("Registrar libro");
    private Button btnClonar = new Button("Clonar libro seleccionado");


    private Label lblSeleccion = new Label();
    private TextField txtUsuarioId = new TextField();
    private TextField txtUsuarioNombre = new TextField();
    private TextField txtDiasUsados = new TextField();
    private Button btnPrestar = new Button("Prestar");
    private Button btnDevolver = new Button("Devolver");


    private TableView<Libro> tablaLibros = new TableView<>();
    private TableView<Prestamo> tablaPrestamos = new TableView<>();

    public BibliotecaView() {
        raiz.setPadding(new Insets(10));
        raiz.setStyle("-fx-background-color: #F1EFE8;");

        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        lblResumen.setStyle("-fx-text-fill: #5F5E5A;");
        HBox encabezado = new HBox(20, lblTitulo, lblResumen);
        encabezado.setAlignment(Pos.CENTER_LEFT);

        Label ayuda = new Label("¿Cómo se usa?   1) Registra libros.   "
                + "2) Haz clic en un libro de la tabla para seleccionarlo.   "
                + "3) Usa Clonar, Prestar o Devolver sobre el libro seleccionado.");
        ayuda.setWrapText(true);
        ayuda.setStyle("-fx-text-fill: #3D3D3A;");

        lblMensaje.setWrapText(true);
        lblMensaje.setMaxWidth(Double.MAX_VALUE);
        mostrarMensaje("Bienvenido. Empieza registrando un libro.");


        VBox registro = crearTarjetaRegistro();
        registro.setPrefWidth(520);
        VBox libros = crearTarjetaLibros();
        HBox.setHgrow(libros, Priority.ALWAYS);
        HBox filaSuperior = new HBox(10, registro, libros);


        VBox prestamos = crearTarjetaPrestamos();
        prestamos.setPrefWidth(520);
        VBox historial = crearTarjetaHistorial();
        HBox.setHgrow(historial, Priority.ALWAYS);
        HBox filaInferior = new HBox(10, prestamos, historial);

        raiz.getChildren().addAll(encabezado, ayuda, lblMensaje, filaSuperior, filaInferior);
    }


    private VBox crearTarjetaRegistro() {
        txtCodigo.setPromptText("Ej: L001");
        txtTitulo.setPromptText("Título del libro");
        txtAutor.setPromptText("Autor");
        txtCategoria.setPromptText("Categoría");
        txtEditorial.setPromptText("Opcional");
        txtAnio.setPromptText("Opcional");

        GridPane formulario = new GridPane();
        formulario.setHgap(8);
        formulario.setVgap(6);
        par(formulario, 0, 0, "Código *", txtCodigo);
        par(formulario, 0, 1, "Título *", txtTitulo);
        par(formulario, 1, 0, "Autor *", txtAutor);
        par(formulario, 1, 1, "Categoría *", txtCategoria);
        par(formulario, 2, 0, "Editorial", txtEditorial);
        par(formulario, 2, 1, "Año", txtAnio);

        estilizar(btnRegistrar, "#185FA5");

        return tarjeta(
                titulo("1. Registrar un libro"),
                nota("Los campos con * son obligatorios. Editorial y año son opcionales."),
                formulario,
                btnRegistrar);
    }

    private VBox crearTarjetaLibros() {
        configurarTablaLibros();
        tablaLibros.setPrefHeight(115);
        tablaLibros.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, nuevo) -> actualizarEtiquetaSeleccion());

        estilizar(btnClonar, "#534AB7");

        return tarjeta(
                titulo("2. Libros registrados"),
                nota("Haz clic en un libro para seleccionarlo. Clonar crea una copia con otro código."),
                tablaLibros,
                btnClonar);
    }

    private VBox crearTarjetaPrestamos() {
        lblSeleccion.setStyle("-fx-font-weight: bold; -fx-text-fill: #185FA5;");
        lblSeleccion.setWrapText(true);
        actualizarEtiquetaSeleccion();

        txtUsuarioId.setPromptText("Ej: 1");
        txtUsuarioId.setPrefWidth(90);
        txtUsuarioNombre.setPromptText("Quien se lleva el libro");
        HBox.setHgrow(txtUsuarioNombre, Priority.ALWAYS);
        txtDiasUsados.setPromptText("Ej: 5");
        txtDiasUsados.setPrefWidth(90);

        estilizar(btnPrestar, "#0F6E56");
        estilizar(btnDevolver, "#BA7517");

        HBox filaPrestar = new HBox(8, new Label("ID"), txtUsuarioId,
                new Label("Nombre"), txtUsuarioNombre, btnPrestar);
        filaPrestar.setAlignment(Pos.CENTER_LEFT);

        HBox filaDevolver = new HBox(8, new Label("Días usados"), txtDiasUsados, btnDevolver);
        filaDevolver.setAlignment(Pos.CENTER_LEFT);

        return tarjeta(
                titulo("3. Prestar y devolver"),
                lblSeleccion,
                nota("Prestar: escribe los datos del usuario y pulsa Prestar."),
                filaPrestar,
                nota("Devolver: escribe cuántos días tuvo el libro. Si pasa del plazo se cobra multa."),
                filaDevolver);
    }

    private VBox crearTarjetaHistorial() {
        configurarTablaPrestamos();
        tablaPrestamos.setPrefHeight(115);
        return tarjeta(titulo("Historial de préstamos"), tablaPrestamos);
    }


    private void configurarTablaLibros() {
        tablaLibros.getColumns().add(columnaLibro("Código", Libro::getCodigo));
        tablaLibros.getColumns().add(columnaLibro("Título", Libro::getTitulo));
        tablaLibros.getColumns().add(columnaLibro("Autor", Libro::getAutor));
        tablaLibros.getColumns().add(columnaLibro("Categoría", Libro::getCategoria));
        tablaLibros.getColumns().add(columnaLibro("Editorial", l -> texto(l.getEditorial())));
        tablaLibros.getColumns().add(columnaLibro("Año", l -> texto(l.getAnioPublicacion())));


        TableColumn<Libro, String> colEstado = columnaLibro("Estado", l -> l.getEstado().toString());
        colEstado.setCellFactory(columna -> new TableCell<Libro, String>() {
            @Override
            protected void updateItem(String estado, boolean vacio) {
                super.updateItem(estado, vacio);
                if (vacio || estado == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estado);
                    String color = estado.equals("DISPONIBLE") ? "#0F6E56" : "#BA7517";
                    setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
                }
            }
        });
        tablaLibros.getColumns().add(colEstado);

        tablaLibros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void configurarTablaPrestamos() {
        tablaPrestamos.getColumns().add(columnaPrestamo("Libro",
                p -> p.getLibro().getCodigo() + " - " + p.getLibro().getTitulo()));
        tablaPrestamos.getColumns().add(columnaPrestamo("Usuario", p -> p.getUsuario().getNombre()));
        tablaPrestamos.getColumns().add(columnaPrestamo("Plazo (días)", p -> String.valueOf(p.getDiasPermitidos())));
        tablaPrestamos.getColumns().add(columnaPrestamo("Días usados",
                p -> p.isDevuelto() ? String.valueOf(p.getDiasUsados()) : "-"));
        tablaPrestamos.getColumns().add(columnaPrestamo("Multa",
                p -> p.isDevuelto() ? String.valueOf(p.calcularMulta()) : "-"));
        tablaPrestamos.getColumns().add(columnaPrestamo("Estado",
                p -> p.isDevuelto() ? "Devuelto" : "En préstamo"));
        tablaPrestamos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private TableColumn<Libro, String> columnaLibro(String nombre, Function<Libro, String> valor) {
        TableColumn<Libro, String> columna = new TableColumn<>(nombre);
        columna.setCellValueFactory(c -> new SimpleStringProperty(valor.apply(c.getValue())));
        return columna;
    }

    private TableColumn<Prestamo, String> columnaPrestamo(String nombre, Function<Prestamo, String> valor) {
        TableColumn<Prestamo, String> columna = new TableColumn<>(nombre);
        columna.setCellValueFactory(c -> new SimpleStringProperty(valor.apply(c.getValue())));
        return columna;
    }



    private VBox tarjeta(Node... contenido) {
        VBox caja = new VBox(6, contenido);
        caja.setStyle(ESTILO_TARJETA);
        return caja;
    }

    private Label titulo(String texto) {
        Label etiqueta = new Label(texto);
        etiqueta.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        return etiqueta;
    }

    private Label nota(String texto) {
        Label etiqueta = new Label(texto);
        etiqueta.setWrapText(true);
        etiqueta.setStyle("-fx-text-fill: #5F5E5A; -fx-font-size: 11px;");
        return etiqueta;
    }


    private void par(GridPane rejilla, int numeroFila, int columna, String etiqueta, TextField campo) {
        rejilla.add(new Label(etiqueta), columna * 2, numeroFila);
        rejilla.add(campo, columna * 2 + 1, numeroFila);
        GridPane.setHgrow(campo, Priority.ALWAYS);
    }

    private void estilizar(Button boton, String color) {
        boton.setMaxWidth(Double.MAX_VALUE);
        boton.setStyle("-fx-background-color: " + color
                + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 14;");
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString();
    }

    private void actualizarEtiquetaSeleccion() {
        Libro libro = getLibroSeleccionado();
        if (libro == null) {
            lblSeleccion.setText("Libro seleccionado: ninguno (haz clic en uno de la tabla)");
        } else {
            lblSeleccion.setText("Libro seleccionado: " + libro.getCodigo() + " - "
                    + libro.getTitulo() + " (" + libro.getEstado() + ")");
        }
    }



    public Parent getRaiz() {
        ScrollPane desplazable = new ScrollPane(raiz);
        desplazable.setFitToWidth(true);
        desplazable.setStyle("-fx-background: #F1EFE8;");
        return desplazable;
    }

    public Button getBtnRegistrar() { return btnRegistrar; }
    public Button getBtnClonar() { return btnClonar; }
    public Button getBtnPrestar() { return btnPrestar; }
    public Button getBtnDevolver() { return btnDevolver; }

    public String getCodigo() { return txtCodigo.getText().trim(); }
    public String getTitulo() { return txtTitulo.getText().trim(); }
    public String getAutor() { return txtAutor.getText().trim(); }
    public String getCategoria() { return txtCategoria.getText().trim(); }
    public String getEditorial() { return txtEditorial.getText().trim(); }
    public String getAnio() { return txtAnio.getText().trim(); }
    public String getUsuarioId() { return txtUsuarioId.getText().trim(); }
    public String getUsuarioNombre() { return txtUsuarioNombre.getText().trim(); }
    public String getDiasUsados() { return txtDiasUsados.getText().trim(); }

    public Libro getLibroSeleccionado() {
        return tablaLibros.getSelectionModel().getSelectedItem();
    }

    public void mostrarBiblioteca(String texto) {
        lblTitulo.setText(texto);
    }

    public void mostrarResumen(String texto) {
        lblResumen.setText(texto);
    }

    public void mostrarLibros(List<Libro> libros) {
        Libro seleccionado = getLibroSeleccionado();
        tablaLibros.getItems().setAll(libros);
        if (seleccionado != null) {
            tablaLibros.getSelectionModel().select(seleccionado);
        }
        tablaLibros.refresh();
        actualizarEtiquetaSeleccion();
    }

    public void mostrarPrestamos(List<Prestamo> prestamos) {
        tablaPrestamos.getItems().setAll(prestamos);
        tablaPrestamos.refresh();
    }

    public void limpiarFormulario() {
        txtCodigo.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtCategoria.clear();
        txtEditorial.clear();
        txtAnio.clear();
    }

    public void mostrarMensaje(String texto) {
        lblMensaje.setStyle(ESTILO_MENSAJE + "-fx-background-color: #E1F5EE; -fx-text-fill: #085041;");
        lblMensaje.setText(texto);
    }

    public void mostrarError(String texto) {
        lblMensaje.setStyle(ESTILO_MENSAJE + "-fx-background-color: #FCEBEB; -fx-text-fill: #791F1F;");
        lblMensaje.setText(texto);

        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atención");
        alerta.setHeaderText(null);
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    public String pedirTexto(String titulo, String mensaje, String valorInicial) {
        TextInputDialog dialogo = new TextInputDialog(valorInicial);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(mensaje);
        Optional<String> resultado = dialogo.showAndWait();
        return resultado.orElse(null);
    }
}
