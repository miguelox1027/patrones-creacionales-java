package biblioteca.modelo;

public class ConfiguracionBiblioteca {

    private static ConfiguracionBiblioteca instancia;

    private String nombre;
    private String direccion;
    private double porcentajeMulta;

    private ConfiguracionBiblioteca() {
        this.nombre = "Biblioteca Universitaria";
        this.direccion = "Sin dirección";
        this.porcentajeMulta = 5.0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPorcentajeMulta() {
        return porcentajeMulta;
    }

    public void setPorcentajeMulta(double porcentajeMulta) {
        this.porcentajeMulta = porcentajeMulta;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public static ConfiguracionBiblioteca getInstance() {
        if (instancia == null) {
            instancia = new ConfiguracionBiblioteca();
        }
        return instancia;
    }
}
