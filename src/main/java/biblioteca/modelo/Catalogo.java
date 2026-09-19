package biblioteca.modelo;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {

    private List<Libro> libros = new ArrayList<>();

    public void agregar(Libro libro) {
        libros.add(libro);
    }

    public Libro buscarPorCodigo(String codigo) {
        for (Libro libro : libros) {
            if (libro.getCodigo().equals(codigo)) {
                return libro;
            }
        }
        return null;
    }

    public List<Libro> getLibros() {
        return libros;
    }
}
