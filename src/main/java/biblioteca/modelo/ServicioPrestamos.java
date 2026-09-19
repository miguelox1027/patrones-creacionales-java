package biblioteca.modelo;

public class ServicioPrestamos {

    private static final int DIAS_PERMITIDOS = 7;

    public Prestamo prestar(Libro libro, Usuario usuario) {
        if (libro.getEstado() != EstadoLibro.DISPONIBLE) {
            throw new IllegalStateException("El libro no está disponible");
        }
        Prestamo prestamo = new Prestamo(libro, usuario, DIAS_PERMITIDOS);
        libro.setEstado(EstadoLibro.PRESTADO);
        return prestamo;
    }

    public double devolver(Prestamo prestamo, int diasUsados) {
        Libro libro = prestamo.getLibro();
        if (libro.getEstado() != EstadoLibro.PRESTADO) {
            throw new IllegalStateException("Este préstamo ya fue devuelto");
        }
        prestamo.registrarDevolucion(diasUsados);
        libro.setEstado(EstadoLibro.DISPONIBLE);
        return prestamo.calcularMulta();
    }
}
