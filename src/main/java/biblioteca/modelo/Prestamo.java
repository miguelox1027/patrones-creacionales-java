package biblioteca.modelo;

public class Prestamo {

    private Libro libro;
    private Usuario usuario;
    private int diasPermitidos;
    private int diasUsados;
    private boolean devuelto;

    public Prestamo(Libro libro, Usuario usuario, int diasPermitidos) {
        this.libro = libro;
        this.usuario = usuario;
        this.diasPermitidos = diasPermitidos;
    }

    public void registrarDevolucion(int diasUsados) {
        this.diasUsados = diasUsados;
        this.devuelto = true;
    }

    public double calcularMulta() {
        int diasRetraso = diasUsados - diasPermitidos;
        if (diasRetraso <= 0) {
            return 0;
        }
        ConfiguracionBiblioteca config = ConfiguracionBiblioteca.getInstance();
        double multaPorDia = config.getValorBase() * config.getPorcentajeMulta() / 100;
        return diasRetraso * multaPorDia;
    }

    public Libro getLibro() { return libro; }
    public Usuario getUsuario() { return usuario; }
    public int getDiasPermitidos() { return diasPermitidos; }
    public int getDiasUsados() { return diasUsados; }
    public boolean isDevuelto() { return devuelto; }
}