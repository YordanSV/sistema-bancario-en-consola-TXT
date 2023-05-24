
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;

public class Transaccion {
    String tipo;
    int identificacionCliente;
    int monto;

    public Transaccion(String tipo, int identidadCliente, int monto) {
        this.identificacionCliente = identidadCliente;
        this.tipo = tipo;
        this.monto = monto;
    }

    public Transaccion() {
    }

    public void traslado(int identidadOrigen) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el numero de identidad a quien desea transferir");
        int identidadDestino = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el monto a transferir");
        int monto = scanner.nextInt();
        scanner.nextLine();
        Cuenta cuenta = new Cuenta();
        cuenta.aumentarSaldo(identidadDestino, monto);
        cuenta.disminuirSaldo(identidadOrigen, monto);
        this.identificacionCliente = identidadOrigen;
        this.tipo = "Traslado";
        this.monto = monto;
        saveTraslado(identidadDestino);
    }

    public void saveTraslado(int identidadDestino) {
        String filePath = "TRASLADO.txt";
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
            contenido.append(this.tipo).append(",");
            contenido.append(this.identificacionCliente).append(",");
            contenido.append(this.monto).append("\n");
            contenido.append(this.tipo).append(",");
            contenido.append(identidadDestino).append(",");
            contenido.append("-" + this.monto).append("\n");
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

    public static void consulta(int identificacion) {
        File archivo = new File("CUENTA.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split(",");
                if (Integer.parseInt(atributos[1]) == identificacion) {
                    System.out.println("Usted tiene un saldo de " + atributos[2]);
                    Transaccion transaccion = new Transaccion("Consulta", identificacion, 0);
                    transaccion.saveTransaccion();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void retiro(int identificacion) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el monto a retirar");
            int monto = scanner.nextInt();
            scanner.nextLine();
            Cuenta cuenta = new Cuenta();
            cuenta.disminuirSaldo(identificacion, monto);
            Transaccion transaccion = new Transaccion("Retiro", identificacion, monto);
            transaccion.saveTransaccion();
    }

    public static void deposito(int identificacion) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el monto a depositar");
            int monto = scanner.nextInt();
            scanner.nextLine();
            Cuenta cuenta = new Cuenta();
            cuenta.aumentarSaldo(identificacion, monto);
            Transaccion transaccion = new Transaccion("Deposito", identificacion, monto);
            transaccion.saveTransaccion();
    }

    public void saveTransaccion() {
        String filePath = "TRANSACCION.txt";
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
            contenido.append(this.tipo).append(",");
            contenido.append(this.identificacionCliente).append(",");
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

    public static void reporte(int identificacion, String nombreArchivo){  
            try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
                String linea;
    
                while ((linea = br.readLine()) != null) {
                    String[] atributos = linea.split(","); 
    
                    // Comprobar si el primer atributo coincide con la identidad buscada
                    if (atributos.length > 0 && atributos[1].equals(Integer.toString(identificacion))) {
                        System.out.println("Ha realizado:" + atributos[0] + " Con un monto de:" + atributos[2]); 
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            
        }
    }
}
