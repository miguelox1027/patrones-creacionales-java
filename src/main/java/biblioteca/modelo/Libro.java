package biblioteca.modelo;

public class Libro {

    // Obligatorios
    private String codigo;
    private String titulo;
    private String autor;
    private String categoria;
    private EstadoLibro estado;
    // Opcionales
    private String editorial;
    private Integer anioPublicacion;

    public Libro(String codigo, String titulo, String autor, String categoria,
                 EstadoLibro estado, String editorial, Integer anioPublicacion) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.estado = estado;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
    }
}