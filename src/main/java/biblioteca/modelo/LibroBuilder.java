package biblioteca.modelo;

public class LibroBuilder {


    private String codigo;
    private String titulo;
    private String autor;
    private String categoria;

    public LibroBuilder(String codigo, String titulo, String autor, String categoria) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
    }
}