import java.io.*;
import java.util.*;

class Personaje implements Serializable {
    String nombre;
    int vida, ataque, defensa, alcance;

    public Personaje(String nombre, int vida, int ataque, int defensa, int alcance) {
        this.nombre = nombre;
        this.vida = Math.max(1, vida);
        this.ataque = Math.max(1, ataque);
        this.defensa = Math.max(1, defensa);
        this.alcance = Math.max(1, alcance);
    }

    @Override
    public String toString() {
        return nombre + " [Vida=" + vida + ", Ataque=" + ataque + ", Defensa=" + defensa + ", Alcance=" + alcance + "]";
    }
}

class Gestor {
    private List<Personaje> personajes;
    private final String archivo = "personajes.txt";

    public Gestor() {
        personajes = new ArrayList<>();
        cargarDatos();
    }

    public void añadir(Personaje p) {
        if (buscar(p.nombre) == null) {
            personajes.add(p);
            guardarDatos();
        }
    }

    public Personaje buscar(String nombre) {
        for (Personaje p : personajes) {
            if (p.nombre.equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }

    public void mostrar() {
        personajes.forEach(System.out::println);
    }

    public void borrar(String nombre) {
        if (personajes.removeIf(p -> p.nombre.equalsIgnoreCase(nombre))) {
            guardarDatos();
        }
    }

    public void modificar(String nombre, int vida, int ataque, int defensa, int alcance) {
        Personaje p = buscar(nombre);
        if (p != null) {
            p.vida = Math.max(1, vida);
            p.ataque = Math.max(1, ataque);
            p.defensa = Math.max(1, defensa);
            p.alcance = Math.max(1, alcance);
            guardarDatos();
        }
    }

    private void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(personajes);
        } catch (IOException e) {
            System.out.println("Error al guardar.");
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            personajes = (List<Personaje>) ois.readObject();
        } catch (Exception e) {
            personajes = new ArrayList<>();
        }
    }
}

public class ejercicio01 {
    public static void main(String[] args) {
        Gestor gestor = new Gestor();
        gestor.añadir(new Personaje("Mago", 5, 8, 3, 6));
        gestor.mostrar();
    }
}