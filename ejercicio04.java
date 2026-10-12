import javax.swing.*;
import java.io.*;
import java.util.*;

public class ejercicio04 {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            procesarArchivo(file);
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }

    private static void procesarArchivo(File file) {
        int lineas = 0, palabrasCount = 0, caracteres = 0;
        Map<String, Integer> frecuencia = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas++;
                caracteres += linea.length();
                
                String[] palabras = linea.split("[^a-zA-Z0-9]+");
                for (String p : palabras) {
                    if (!p.isEmpty() && Character.isLetterOrDigit(p.charAt(0))) {
                        palabrasCount++;
                        String pMin = p.toLowerCase();
                        frecuencia.put(pMin, frecuencia.getOrDefault(pMin, 0) + 1);
                    }
                }
            }
            
            System.out.println("Total de líneas: " + lineas);
            System.out.println("Total de palabras: " + palabrasCount);
            System.out.println("Total de caracteres: " + caracteres);
            System.out.println("Promedio de palabras por línea: " + (lineas == 0 ? 0 : (double) palabrasCount / lineas));
            
            System.out.println("\nPalabras más frecuentes:");
            frecuencia.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

        } catch (FileNotFoundException e) {
            System.out.println("Error: El archivo no existe o es inválido.");
        } catch (IOException e) {
            System.out.println("Error de lectura: " + e.getMessage());
        }
    }
}
