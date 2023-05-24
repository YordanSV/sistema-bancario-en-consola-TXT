import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;

// Programa de explicación básica en Java

public class Usuario {
    public String nombre;
    public String apellido;
    public String clave;
    public int identificacion;
    public int edad;
    public boolean esAdministrador;

    public Usuario(String nombre, String apellido, int identificacion, String clave, int edad,
            boolean esAdministrador) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.clave = clave;
        this.identificacion = identificacion;
        this.edad = edad;
        this.esAdministrador = esAdministrador;
    }

    public Usuario() {
    }

    public String getClave() { // requestUser Solicitar usuario
        return clave;
    }

    public boolean esAdministrador() { // requestUser Solicitar usuario
        return esAdministrador;
    }

    // ==================== Iniciar Sesion ====================
    public static Usuario singIn() { // Iniciar sesion
        Scanner scanner = new Scanner(System.in);
        Usuario usuario = null;
        while (true) {
            System.out.println("=== Iniciar sesion ===");
            System.out.println("Ingrese su identificacion: ");
            int identificacion = scanner.nextInt();
            scanner.nextLine();
            usuario = getUser(identificacion);
            if (usuario == null) {
                System.out.print("\033[H\033[2J"); // Código para limpiar la terminal
                System.out.flush();
                System.out.println("El usuario no existe intente de nuevo");
                continue;
            }
            System.out.println("Ingrese su clave: ");
            String clave = scanner.nextLine();
            if (!(usuario.getClave().equals(clave))) { // Si las cadenas son iguales
                System.out.print("\033[H\033[2J"); // Código para limpiar la terminal
                System.out.flush();
                System.out.println("clave incorrecta intente de nuevo");
                continue;
            }
            break;
        }
        return usuario;
    }

    // ==================== Crear usuario ====================
    public static void createUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese el apellido: ");
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
        System.out.println("1. Si");
        System.out.println("2. No");
        int esAdministrador = scanner.nextInt();
        scanner.nextLine();
        saveUser(nombre, apellido, identificacion, clave, edad, (esAdministrador == 1) ? true : false);
        Cuenta cuenta =  new Cuenta(identificacion);
        cuenta.saveCuenta();
    }

    // ==================== Guardar usuario ====================
    public static void saveUser(String nombre, String apellido, int identificacion, String clave, int edad,boolean esAdministrador) {
        String filePath = "USUARIO.txt";
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

            contenido.append(nombre).append(",");
            contenido.append(apellido).append(",");
            contenido.append(identificacion).append(",");
            contenido.append(clave).append(",");
            contenido.append(edad).append(",");
            contenido.append(esAdministrador).append("\n");
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

    public static void deleteUser() {
        File archivoIn = new File("USUARIO.txt");
        File archivoOut = new File("NuevoUSUARIO.txt");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el numero de identidad");
        int identificacion = scanner.nextInt();
        scanner.nextLine();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoIn));
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivoOut))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split(","); // Separar atributos por coma
    
                boolean eliminarLinea = false;
                if (Integer.parseInt(atributos[2]) == identificacion) {
                    eliminarLinea = true;
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
        System.out.println("Se ha eliminado correctamente");
    }    

    public static void modifyUser() {
        File archivoIn = new File("USUARIO.txt");
        File archivoOut = new File("USUARIO_temp.txt");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el numero de identidad");
        int identificacion = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Que desea modificar");
        System.out.println("1.Nombre");
        System.out.println("2.Apellido");
        System.out.println("3.Numero de identificacion");
        System.out.println("4.Clave");
        System.out.println("5.Edad");
        int posicion = scanner.nextInt() - 1;
        scanner.nextLine();
        System.out.println("Ingrese el cambio");
        String cambio = scanner.nextLine();
    
        try (BufferedReader br = new BufferedReader(new FileReader(archivoIn));
             BufferedWriter bw = new BufferedWriter(new FileWriter(archivoOut))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split(",");
                if (Integer.parseInt(atributos[2]) == identificacion) {
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
    

    public static void mostrarMenuAdministrador() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Administrador ===");
        System.out.println("=== Desea ===");
        System.out.println("1. Crear usuario");
        System.out.println("2. Borrar usuario");
        System.out.println("3. Modificar usuario");
        System.out.print("Selecciona una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion) {
            case 1:
                createUser();
                break;
            case 2:
                deleteUser();
            default:
                modifyUser();
                break;
        }

    }

    public static void mostrarMenuCuentas() {
        System.out.println("=== Desea ===");
        System.out.println("1. Crear");
        System.out.println("2. Borrar");
        System.out.println("3. Modificar");
        System.out.println("4. Ver reporte de cuentas");
        System.out.print("Selecciona una opción: ");
    }

    // ==================== Obtener usuario ====================
    public static Usuario getUser(int identificacion) { // Iniciar sesion
        String nombreArchivo = "USUARIO.txt";
        Usuario usuario = null;
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] atributos = linea.split(",");
                if (identificacion == Integer.parseInt(atributos[2])) {
                    usuario = new Usuario(atributos[0], atributos[1], Integer.parseInt((atributos[2])), atributos[3],
                            Integer.parseInt((atributos[4])), Boolean.parseBoolean(atributos[5]));
                    return usuario;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return usuario;
    }

}
