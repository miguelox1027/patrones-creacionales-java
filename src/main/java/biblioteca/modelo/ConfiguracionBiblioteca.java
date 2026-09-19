package biblioteca.modelo;

public class ConfiguracionBiblioteca {

    private static ConfiguracionBiblioteca instancia;

    private String nombre;
    private String direccion;
    private double porcentajeMulta;
    private double valorBase;

    private ConfiguracionBiblioteca() {
        this.nombre = "Biblioteca Universitaria";
        this.direccion = "Sin dirección";
        this.porcentajeMulta = 5.0;
        this.valorBase = 1000.0;
    }

    public static ConfiguracionBiblioteca getInstance() {
        if (instancia == null) {
            instancia = new ConfiguracionBiblioteca();
        }
        return instancia;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public double getPorcentajeMulta() { return porcentajeMulta; }
    public void setPorcentajeMulta(double porcentajeMulta) { this.porcentajeMulta = porcentajeMulta; }

    public double getValorBase() { return valorBase; }
    public void setValorBase(double valorBase) { this.valorBase = valorBase; }
}