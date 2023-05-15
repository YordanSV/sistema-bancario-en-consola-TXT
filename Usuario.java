import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
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
        // Código para limpiar la terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
       Usuario usuario;
       do{
        usuario = singIn();
       }while(usuario == null);

        //createUser();
    }
    public static Usuario singIn() { // Iniciar sesion
        String nombreArchivo = "USUARIO.txt";
        Scanner scanner = new Scanner(System.in);
        Usuario usuario = null;
        System.out.println("=== Iniciar sesion ===");
        System.out.println("Ingrese su identificacion: ");
        int identificacion = scanner.nextInt();
        scanner.nextLine();
        // Código para limpiar la terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int contador = 0;
            List<String> lista = new ArrayList<>();
            while ((linea = br.readLine()) != null) {
                lista.add(linea);
                if(contador == 5 && Integer.parseInt(lista.get(2)) == identificacion){
                    System.out.println("Ingrese su clave: ");
                    String clave = scanner.nextLine();
                    if(clave.equals(lista.get(3))){
                        System.out.println("Se ha iniciado sesion exitosamente");
                        usuario = new Usuario(lista.get(0), lista.get(1), Integer.parseInt(lista.get(2)), lista.get(3), Integer.parseInt(lista.get(4)), Boolean.parseBoolean(lista.get(5)));
                        break;
                    }else{
                        // Código para limpiar la terminal
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Clave incorrecta intente de nuevo");
                        return null;
                    }
                }else if(contador == 5){
                    lista.clear();
                    contador = 0;
                }else{
                    contador = contador+1;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        if(usuario == null){
            System.out.println("El usuario no existe intente de nuevo");
        }
        scanner.close();
        return usuario;
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
        int esAdministrador = scanner.nextInt();
        scanner.nextLine();
        if(esAdministrador == 1){
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
