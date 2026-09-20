package biblioteca.controlador;

import biblioteca.modelo.Catalogo;
import biblioteca.modelo.ConfiguracionBiblioteca;
import biblioteca.modelo.EstadoLibro;
import biblioteca.modelo.Libro;
import biblioteca.modelo.Prestamo;
import biblioteca.modelo.ServicioPrestamos;
import biblioteca.modelo.Usuario;
import biblioteca.vista.BibliotecaView;

public class BibliotecaController {

    private Catalogo catalogo;
    private ServicioPrestamos servicio;
    private BibliotecaView vista;

    public BibliotecaController(Catalogo catalogo, ServicioPrestamos servicio, BibliotecaView vista) {
        this.catalogo = catalogo;
        this.servicio = servicio;
        this.vista = vista;

        vista.getBtnRegistrar().setOnAction(e -> registrar());
        vista.getBtnClonar().setOnAction(e -> clonar());
        vista.getBtnPrestar().setOnAction(e -> prestar());
        vista.getBtnDevolver().setOnAction(e -> devolver());

        ConfiguracionBiblioteca config = ConfiguracionBiblioteca.getInstance();
        vista.mostrarBiblioteca(config.getNombre() + " - " + config.getDireccion());
        actualizar();
    }


    private void registrar() {
        String codigo = vista.getCodigo();
        String titulo = vista.getTitulo();
        String autor = vista.getAutor();
        String categoria = vista.getCategoria();

        if (codigo.isEmpty() || titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty()) {
            vista.mostrarError("Código, título, autor y categoría son obligatorios");
            return;
        }
        if (catalogo.buscarPorCodigo(codigo) != null) {
            vista.mostrarError("Ya existe un libro con ese código");
            return;
        }

        Libro.LibroBuilder builder = Libro.builder()
                .codigo(codigo)
                .titulo(titulo)
                .autor(autor)
                .categoria(categoria);

        if (!vista.getEditorial().isEmpty()) {
            builder.editorial(vista.getEditorial());
        }
        if (!vista.getAnio().isEmpty()) {
            try {
                builder.anioPublicacion(Integer.parseInt(vista.getAnio()));
            } catch (NumberFormatException e) {
                vista.mostrarError("El año debe ser un número entero");
                return;
            }
        }

        catalogo.agregar(builder.build());
        vista.limpiarFormulario();
        actualizar();
        vista.mostrarMensaje("Libro registrado: " + titulo);
    }

    private void clonar() {
        Libro original = vista.getLibroSeleccionado();
        if (original == null) {
            vista.mostrarError("Selecciona en la tabla el libro que quieres clonar");
            return;
        }

        String nuevoCodigo = vista.pedirTexto("Clonar libro", "Código del nuevo libro:", "");
        if (nuevoCodigo == null) {
            return; // el usuario canceló
        }
        nuevoCodigo = nuevoCodigo.trim();
        if (nuevoCodigo.isEmpty()) {
            vista.mostrarError("El código del nuevo libro no puede estar vacío");
            return;
        }
        if (catalogo.buscarPorCodigo(nuevoCodigo) != null) {
            vista.mostrarError("Ya existe un libro con ese código");
            return;
        }

        String nuevoTitulo = vista.pedirTexto("Clonar libro", "Título del nuevo libro:", original.getTitulo());

        Libro copia = original.clonar();
        copia.setCodigo(nuevoCodigo);
        if (nuevoTitulo != null && !nuevoTitulo.isBlank()) {
            copia.setTitulo(nuevoTitulo.trim());
        }

        catalogo.agregar(copia);
        actualizar();
        vista.mostrarMensaje("Libro clonado con el código " + nuevoCodigo);
    }

    private void prestar() {
        Libro libro = vista.getLibroSeleccionado();
        if (libro == null) {
            vista.mostrarError("Selecciona en la tabla el libro que quieres prestar");
            return;
        }
        if (vista.getUsuarioId().isEmpty() || vista.getUsuarioNombre().isEmpty()) {
            vista.mostrarError("Escribe el ID y el nombre del usuario");
            return;
        }

        try {
            Usuario usuario = new Usuario(vista.getUsuarioId(), vista.getUsuarioNombre());
            servicio.prestar(libro, usuario);
            actualizar();
            vista.mostrarMensaje("Libro prestado a " + usuario.getNombre());
        } catch (IllegalStateException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    private void devolver() {
        Libro libro = vista.getLibroSeleccionado();
        if (libro == null) {
            vista.mostrarError("Selecciona en la tabla el libro que quieres devolver");
            return;
        }
        if (libro.getEstado() != EstadoLibro.PRESTADO) {
            vista.mostrarError("Ese libro no está prestado");
            return;
        }

        int dias;
        try {
            dias = Integer.parseInt(vista.getDiasUsados());
        } catch (NumberFormatException e) {
            vista.mostrarError("Escribe los días usados (número entero)");
            return;
        }
        if (dias < 0) {
            vista.mostrarError("Los días usados no pueden ser negativos");
            return;
        }

        Prestamo prestamo = servicio.buscarUltimoPrestamo(libro);
        if (prestamo == null) {
            vista.mostrarError("No se encontró el préstamo de ese libro");
            return;
        }

        try {
            double multa = servicio.devolver(prestamo, dias);
            actualizar();
            if (multa > 0) {
                vista.mostrarMensaje("Libro devuelto con retraso. Multa: " + multa);
            } else {
                vista.mostrarMensaje("Libro devuelto a tiempo. Sin multa");
            }
        } catch (IllegalStateException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    private void actualizar() {
        vista.mostrarLibros(catalogo.getLibros());
        vista.mostrarPrestamos(servicio.getPrestamos());

        int total = catalogo.getLibros().size();
        int prestados = 0;
        for (Libro libro : catalogo.getLibros()) {
            if (libro.getEstado() == EstadoLibro.PRESTADO) {
                prestados++;
            }
        }
        vista.mostrarResumen("Libros registrados: " + total
                + "   |   Disponibles: " + (total - prestados)
                + "   |   Prestados: " + prestados);
    }
}