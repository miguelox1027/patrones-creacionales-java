package biblioteca.modelo;

public class ConfiguracionBiblioteca {

    private String nombre;
    private String direccion;
    private double porcentajeMulta;

    private ConfiguracionBiblioteca() {
        this.nombre = "Biblioteca Universitaria";
        this.direccion = "Sin dirección";
        this.porcentajeMulta = 5.0;
    }
}
