import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
// Programa de explicación básica en Java

public class Usuario {
    public String nombre;
    public String apellido;
    public String clave;
    public int identificacion;
    public int edad;
    public boolean administrador;

    public Usuario(String nombre, String apellido, int identificacion, String clave, int edad, boolean administrador) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.clave = clave;
        this.identificacion = identificacion;
        this.edad = edad;
        this.administrador = administrador;
    }

    public Usuario() {
    }

    public static boolean esAdministrador(boolean administrador) { // requestUser Solicitar usuario
        return administrador;
    }

    public static void main(String[] args) {
        /*
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        while (opcion != 4) {
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            switch (opcion) {
                case 1:
                    
                    break;
            }
        }
        */
        singIn();
        createUser();
    }
    public static void singIn() { // Iniciar sesion
        String nombreArchivo = "USUARIO.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public static void createUser() { // crear usuario
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese su nombre: ");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese su apellido: ");
        String apellido = scanner.nextLine();

        System.out.println("Ingrese su identificacion: ");
        int identificacion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el búfer después de leer un entero

        System.out.println("Ingrese su clave: ");
        String clave = scanner.nextLine();

        System.out.println("Ingrese su edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Desea que sea administrador? ");
        System.out.println("1. Sí");
        System.out.println("2. No");
        int administrador = scanner.nextInt();
        scanner.nextLine();
        if(administrador == 1){
            saveUser(nombre, apellido, identificacion,clave, edad, true);
        }else{
            saveUser(nombre, apellido, identificacion, clave, edad, false);
        }
        

        // Llamar al método para escribir en el archivo
        scanner.close();
    }

    public static void saveUser(String nombre, String apellido, int identificacion, String clave, int edad, boolean esAdministrador) {
        try {
            FileWriter fileWriter = new FileWriter("USUARIO.txt");
            fileWriter.write(nombre + "\n");
            fileWriter.write(apellido + "\n");
            fileWriter.write(clave + "\n");
            fileWriter.write(identificacion + "\n");
            fileWriter.write(edad + "\n");
            fileWriter.write(esAdministrador + "\n");
            fileWriter.close();
            System.out.println("Los datos se han guardado correctamente en el archivo.");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al escribir en el archivo.");
            e.printStackTrace();
        }
    }

    public static void administradorMenu() {
        System.out.println("=== Administrador ===");
        System.out.println("=== Desea ingresar a: ===");
        System.out.println("1. Usuarios");
        System.out.println("2. Cuentas");
        System.out.print("Selecciona una opción: ");
    }
    
    public static void cuentasMenu() {
        System.out.println("=== MENÚ ===");
        System.out.println("1. Desea crear");
        System.out.println("2. Desea borrar");
        System.out.println("3. Desea modificar");
        System.out.println("4. Ver reporte de cuentas");
        System.out.print("Selecciona una opción: ");
    }

}
