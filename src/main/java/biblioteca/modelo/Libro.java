package biblioteca.modelo;

public class Libro implements Copiable {

    // obligatorios
    private String codigo;
    private String titulo;
    private String autor;
    private String categoria;
    private EstadoLibro estado;
    // opcionales
    private String editorial;
    private Integer anioPublicacion;

    private Libro(String codigo, String titulo, String autor, String categoria,
                  EstadoLibro estado, String editorial, Integer anioPublicacion) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.estado = estado;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
    }


    public static LibroBuilder builder() {
        return new LibroBuilder();
    }

    public static class LibroBuilder {

        private String codigo;
        private String titulo;
        private String autor;
        private String categoria;
        private String editorial;
        private Integer anioPublicacion;

        private LibroBuilder() {}

        public LibroBuilder codigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public LibroBuilder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public LibroBuilder autor(String autor) {
            this.autor = autor;
            return this;
        }

        public LibroBuilder categoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public LibroBuilder editorial(String editorial) {
            this.editorial = editorial;
            return this;
        }

        public LibroBuilder anioPublicacion(Integer anioPublicacion) {
            this.anioPublicacion = anioPublicacion;
            return this;
        }

        public Libro build() {
            return new Libro(codigo, titulo, autor, categoria,
                    EstadoLibro.DISPONIBLE, editorial, anioPublicacion);
        }
    }



    @Override
    public Libro clonar() {
        return new Libro(codigo, titulo, autor, categoria,
                EstadoLibro.DISPONIBLE, editorial, anioPublicacion);
    }


    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getCategoria() { return categoria; }
    public EstadoLibro getEstado() { return estado; }
    public String getEditorial() { return editorial; }
    public Integer getAnioPublicacion() { return anioPublicacion; }



    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setEstado(EstadoLibro estado) { this.estado = estado; }
}