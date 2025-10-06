public class Ave extends Mascota{
    //Atributos
    private String tipoPlumaje;
    private enum capacidadVuelo{Si,No};
    private int cantidadDeImitaciones;

    //Constructor
    public Ave(String nombre,String especie, String raza, int edad, double peso, String identificacion, String nombrePropietario, String numeroPropietario, String tipoPlumaje, int cantidadDeImitaciones) {
        super(nombre, especie, raza, edad, peso, identificacion, nombrePropietario, numeroPropietario);

    }

    //Gets y sets

    public String getTipoPlumaje(){
        return tipoPlumaje;
    }
    public void setTipoPlumaje(String tipoPlumaje){
        this.tipoPlumaje = tipoPlumaje;
    }
    public int getCantidadDeImitaciones(){
        return cantidadDeImitaciones;
    }
    public void setCantidadDeImitaciones(int cantidadDeImitaciones){
        this.cantidadDeImitaciones = cantidadDeImitaciones;
    }

}
