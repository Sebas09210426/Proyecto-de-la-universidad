public class Reptil extends Mascota{

    //Atributos
    private enum habitatRequerido{terrestre, acuatico, mixto};
    private int temperaturaOptima;
    private enum nivelPeligrosidad{inofensivo, medio, alto};

    public Reptil(String nombre,String especie, String raza, int edad, double peso, String identificacion, String nombrePropietario, String numeroPropietario, int temperaturaOptima) {
        super(nombre, especie, raza, edad, peso, identificacion, nombrePropietario, numeroPropietario);
    }


    /* Gets y sets */

    public int  getTemperaturaOptima() {
        return temperaturaOptima;
    }
    public void setTemperaturaOptima(int temperaturaOptima){
        this.temperaturaOptima = temperaturaOptima;
    }


}

