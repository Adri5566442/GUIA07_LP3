import java.io.*;
import java.util.*;

class Empleado implements Serializable {
    private int numero;
    private String nombre;
    private double sueldo;

    public Empleado(int numero, String nombre, double sueldo) {
        this.numero = numero; 
        this.nombre = nombre; 
        this.sueldo = sueldo;
    }

    public int getNumero() { 
        return numero; 
    }
    
    @Override
    public String toString() {
        return "N°: " + numero + " | Nombre: " + nombre + " | Sueldo: " + sueldo;
    }
}

class EmpleadoControlador {
    private List<Empleado> empleados;
    private final String archivo = "empleados.dat";

    public EmpleadoControlador() {
        empleados = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public void leerEmpleados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            empleados = (List<Empleado>) ois.readObject();
            if (empleados.isEmpty()) {
                System.out.println("No hay empleados.");
            } else {
                empleados.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Archivo no encontrado o vacío. Se creará al agregar datos.");
        }
    }

    public void agregarEmpleado(Empleado e) {
        empleados.add(e);
        guardar();
        System.out.println("Operación exitosa: Empleado agregado.");
    }

    public void buscarEmpleado(int numero) {
        for (Empleado e : empleados) {
            if (e.getNumero() == numero) {
                System.out.println("Operación exitosa: " + e);
                return;
            }
        }
        System.out.println("Error: Empleado no encontrado.");
    }

    public void eliminarEmpleado(int numero) {
        boolean removido = empleados.removeIf(e -> e.getNumero() == numero);
        if (removido) {
            guardar();
            System.out.println("Operación exitosa: Empleado eliminado.");
        } else {
            System.out.println("Error: Empleado no encontrado.");
        }
    }

    private void guardar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(empleados);
        } catch (IOException ex) {
            System.out.println("Error de escritura en el archivo.");
        }
    }
}

public class ejercicio03 {
    public static void main(String[] args) {
        EmpleadoControlador controlador = new EmpleadoControlador();
        controlador.leerEmpleados();
        controlador.agregarEmpleado(new Empleado(1, "Juan Perez", 2500.50));
        controlador.buscarEmpleado(1);
    }
}
