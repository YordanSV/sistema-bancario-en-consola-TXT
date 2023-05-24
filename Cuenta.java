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
    int saldo = 0; // 0 por defecto

    public Cuenta(int identidadCliente) {
        Random random = new Random();
        this.numCuenta = random.nextInt(10000); // numero aleatorio
        this.identidadCliente = identidadCliente;
    }
    public Cuenta() {}

    
    public static void mostrarMenuCuentas() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Desea ===");
        System.out.println("1. Crear");
        System.out.println("2. Borrar");
        System.out.println("3. Modificar");
        System.out.print("Selecciona una opción: ");
        int opcion = scanner.nextInt();
        System.out.print("Ingrese el numero de identificacion");
        int identificacion = scanner.nextInt();
        Cuenta cuenta = new Cuenta(identificacion);
        scanner.nextLine();
        switch (opcion) {
            case 1:
                cuenta.saveCuenta();
                break;
            case 2:
                cuenta.deleteCuenta();
            default:
                cuenta.modifyCuenta();
                break;
        }

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
            contenido.append(this.saldo).append("\n");
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

    public static void aumentarSaldo(int identificacion, int monto) {
        File archivoIn = new File("CUENTA.txt");
        File archivoOut = new File("CUENTA_temp.txt");
          
        try (BufferedReader br = new BufferedReader(new FileReader(archivoIn));
             BufferedWriter bw = new BufferedWriter(new FileWriter(archivoOut))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split(",");
                if (Integer.parseInt(atributos[1]) == identificacion) {
                    int valorActual = Integer.parseInt(atributos[2]);
                    int resultado = valorActual + monto;
                    atributos[2] = String.valueOf(resultado);                    
                    linea = String.join(",", atributos);
                }
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        archivoIn.delete();
        archivoOut.renameTo(new File("CUENTA.txt"));
        System.out.println("Se ha modificado correctamente.");
    }
    
    public static void disminuirSaldo(int identificacion, int monto) {
        File archivoIn = new File("CUENTA.txt");
        File archivoOut = new File("CUENTA_temp.txt");
          
        try (BufferedReader br = new BufferedReader(new FileReader(archivoIn));
             BufferedWriter bw = new BufferedWriter(new FileWriter(archivoOut))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split(",");
                if (Integer.parseInt(atributos[1]) == identificacion) {
                    int valorActual = Integer.parseInt(atributos[2]);
                    int resultado = valorActual - monto;
                    atributos[2] = String.valueOf(resultado);
                    linea = String.join(",", atributos);
                }
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        archivoIn.delete();
        archivoOut.renameTo(new File("CUENTA.txt"));
        System.out.println("Se ha modificado correctamente.");
    }

    public void deleteCuenta() {
        File archivoIn = new File("CUENTA.txt");
        File archivoOut = new File("NuevoCUENTA.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(archivoIn));
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivoOut))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split(","); // Separar atributos por coma

                boolean eliminarLinea = false;
                for (String atributo : atributos) {
                    if (Integer.parseInt(atributo) == this.identidadCliente) {
                        eliminarLinea = true;
                        break;
                    }
                }
                if (!eliminarLinea) {
                    bw.write(linea); // Escribir la línea en el archivo nuevo
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        archivoIn.delete();
        archivoOut.renameTo(archivoIn);
        System.out.println("Se ha eliminado corectamente");
    }


    public void modifyCuenta() {
        File archivoIn = new File("CUENTA.txt");
        File archivoOut = new File("CUENTA_temp.txt");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("Que desea modificar");
        System.out.println("1. Numero de cuenta");
        System.out.println("2. Identificacion");
        System.out.println("3. Saldo");
        System.out.println("Selecciona una opcion:");
        int posicion = scanner.nextInt() - 1;
        scanner.nextLine();
        System.out.println("Ingrese el cambio");
        String cambio = scanner.nextLine();
    
        try (BufferedReader br = new BufferedReader(new FileReader(archivoIn));
             BufferedWriter bw = new BufferedWriter(new FileWriter(archivoOut))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split(",");
                if (Integer.parseInt(atributos[1]) == this.identidadCliente) {
                    for (int i = 0; i <= 4; i++) {
                        if (i == posicion) {
                            atributos[i] = cambio;
                        }
                    }
                    linea = String.join(",", atributos);
                }
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        archivoIn.delete();
        archivoOut.renameTo(new File("USUARIO.txt"));
        System.out.println("Se ha modificado correctamente.");
    }
    
}
