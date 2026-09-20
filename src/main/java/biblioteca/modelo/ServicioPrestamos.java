package biblioteca.modelo;

import java.util.ArrayList;
import java.util.List;

public class ServicioPrestamos {

    private static final int DIAS_PERMITIDOS = 7;

    private List<Prestamo> prestamos = new ArrayList<>();

    public Prestamo prestar(Libro libro, Usuario usuario) {
        if (libro.getEstado() != EstadoLibro.DISPONIBLE) {
            throw new IllegalStateException("El libro no está disponible");
        }
        Prestamo prestamo = new Prestamo(libro, usuario, DIAS_PERMITIDOS);
        libro.setEstado(EstadoLibro.PRESTADO);
        prestamos.add(prestamo);
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

    public Prestamo buscarUltimoPrestamo(Libro libro) {
        for (int i = prestamos.size() - 1; i >= 0; i--) {
            if (prestamos.get(i).getLibro() == libro) {
                return prestamos.get(i);
            }
        }
        return null;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
}