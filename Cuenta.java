import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;

public class Cuenta {
    int numCuenta;
    int identidadCliente;
    int monto = 0; // 0 por defecto

    public Cuenta(int identidadCliente) {
        Random random = new Random();
        this.numCuenta = random.nextInt(10000); // numero aleatorio
        this.identidadCliente = identidadCliente;
    }

    public static int getNumCuenta(int identidadCliente) {
        String nombreArchivo = "CUENTA.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split(",");
                if (identidadCliente == Integer.parseInt(atributos[1])) {
                    return Integer.parseInt(atributos[0]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return 0;
    }

    public void saveCuenta() {
        String filePath = "CUENTA.txt";
        try {
            // Abrir el archivo en modo de escritura
            FileWriter fileWriter = new FileWriter(filePath, true);

            // Leer todas las líneas existentes y almacenarlas
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String linea;
            StringBuilder contenido = new StringBuilder();
            while ((linea = bufferedReader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
            bufferedReader.close();

            contenido.append(this.numCuenta).append(",");
            contenido.append(this.identidadCliente).append(",");
            contenido.append(this.monto).append("\n");
            fileWriter.close();

            fileWriter = new FileWriter(filePath);
            // Escribir todas las líneas en el archivo
            fileWriter.write(contenido.toString());

            fileWriter.close();
            System.out.println("Se ha agregado el contenido correctamente");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
