public class Personaje {
    String nombre;
    int salud;
    int ataque;
    int defensa;
    int vida;

    //constructor de mi clase Personaje
    public Personaje(String nombre, int salud, int ataque, int defensa) {
        this.nombre = nombre;
        this.salud = salud;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vida = salud*10;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    //------------------------------------------
    public boolean estaVivo() {
        return vida > 0;
    }


    @Override
    public String toString() {
        return "Personaje{" +
                "nombre='" + nombre + '\'' +
                ", salud=" + salud +
                ", ataque=" + ataque +
                ", defensa=" + defensa +
                '}';
    }
}

