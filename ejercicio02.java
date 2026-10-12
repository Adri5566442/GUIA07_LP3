import java.io.*;
import java.util.*;

class Personaje implements Serializable {
    String nombre;
    int vida, ataque, defensa, alcance, nivel;

    public Personaje(String nombre, int vida, int ataque, int defensa, int alcance) {
        this.nombre = nombre;
        this.vida = Math.max(1, vida);
        this.ataque = Math.max(1, ataque);
        this.defensa = Math.max(1, defensa);
        this.alcance = Math.max(1, alcance);
        this.nivel = 1;
    }
    
    public void subirNivel() {
        this.nivel++;
        this.vida += 2;
        this.ataque += 2;
        this.defensa += 2;
        this.alcance += 1;
    }

    @Override
    public String toString() {
        return nombre + " [Nivel=" + nivel + ", Vida=" + vida + ", Ataque=" + ataque + ", Defensa=" + defensa + ", Alcance=" + alcance + "]";
    }
}

class Gestor {
    private List<Personaje> personajes;
    private final String archivo = "personajes_ampliado.txt";

    public Gestor() {
        personajes = new ArrayList<>();
        cargarDatos();
        cargarAleatorios();
    }
    
    private void cargarAleatorios() {
        if (personajes.isEmpty()) {
            añadir(new Personaje("Caballero", 4, 2, 4, 2));
            añadir(new Personaje("Guerrero", 2, 4, 2, 4));
            añadir(new Personaje("Arquero", 2, 4, 1, 8));
        }
    }

    public void añadir(Personaje p) {
        if (buscar(p.nombre) == null) {
            personajes.add(p);
            guardarDatos();
        }
    }

    public Personaje buscar(String nombre) {
        return personajes.stream().filter(p -> p.nombre.equalsIgnoreCase(nombre)).findFirst().orElse(null);
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
    
    public void actualizarAtributoIndividual(String nombre, String atributo, int valor) {
        Personaje p = buscar(nombre);
        if (p != null) {
            valor = Math.max(1, valor);
            switch(atributo.toLowerCase()) {
                case "vida": p.vida = valor; break;
                case "ataque": p.ataque = valor; break;
                case "defensa": p.defensa = valor; break;
                case "alcance": p.alcance = valor; break;
            }
            guardarDatos();
        }
    }
    
    public void filtrarPorAtributos(String atributo) {
        List<Personaje> ordenados = new ArrayList<>(personajes);
        switch(atributo.toLowerCase()) {
            case "vida": ordenados.sort(Comparator.comparingInt(p -> p.vida)); break;
            case "ataque": ordenados.sort(Comparator.comparingInt(p -> p.ataque)); break;
            case "defensa": ordenados.sort(Comparator.comparingInt(p -> p.defensa)); break;
            case "alcance": ordenados.sort(Comparator.comparingInt(p -> p.alcance)); break;
        }
        ordenados.forEach(System.out::println);
    }
    
    public void mostrarEstadisticas() {
        if (personajes.isEmpty()) return;
        System.out.println("Total de personajes: " + personajes.size());
        double promVida = personajes.stream().mapToInt(p -> p.vida).average().orElse(0);
        double promAtaque = personajes.stream().mapToInt(p -> p.ataque).average().orElse(0);
        double promDefensa = personajes.stream().mapToInt(p -> p.defensa).average().orElse(0);
        double promAlcance = personajes.stream().mapToInt(p -> p.alcance).average().orElse(0);
        System.out.printf("Promedios -> Vida: %.2f | Ataque: %.2f | Defensa: %.2f | Alcance: %.2f\n", promVida, promAtaque, promDefensa, promAlcance);
    }
    
    public void importarPersonajesDesdeCSV(String rutaCSV) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    añadir(new Personaje(datos[0], Integer.parseInt(datos[1]), Integer.parseInt(datos[2]), Integer.parseInt(datos[3]), Integer.parseInt(datos[4])));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al importar desde el archivo.");
        }
    }

    public void subirNivelPersonaje(String nombre) {
        Personaje p = buscar(nombre);
        if (p != null) {
            p.subirNivel();
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

public class ejercicio02 {
    public static void main(String[] args) {
        Gestor gestor = new Gestor();
        gestor.mostrarEstadisticas();
        gestor.mostrar();
    }
}