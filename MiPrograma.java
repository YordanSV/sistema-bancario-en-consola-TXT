import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.midi.VoiceStatus;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

public class MiPrograma {

    public static void main(String[] args) {
        // Código para limpiar la terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Usuario usuario = new Usuario();
        usuario = usuario.singIn();
        mostrarMenuPrincipal(usuario);
    }

    public static void mostrarMenuPrincipal(Usuario usuario) {
        while (true) {
            if (usuario.esAdministrador()) {
                usuario.mostrarMenuAdministrador();
            } else {
                clienteMenu(usuario.identificacion);
            }
        }
    }

    public static void clienteMenu(int identificacionUsuario) { // identificacion de quien realiza consulta
        Scanner scanner = new Scanner(System.in);
        System.out.println("===Menu Cliente==");
        System.out.println("1) Consulta");
        System.out.println("2) Retiro");
        System.out.println("3) Deposito");
        System.out.println("4) Traslado");
        System.out.println("5) Ver reporte");
        System.out.println("Elige una opcion");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        Transaccion transaccion = new Transaccion();
        switch (opcion) {
            case 1:
                transaccion.consulta(identificacionUsuario);
                break;
            case 2:
                transaccion.retiro(identificacionUsuario);
                break;
            case 3:
                transaccion.deposito(identificacionUsuario);
                break;
            case 4:
                transaccion.traslado(identificacionUsuario);
                break;
            case 5:
                transaccion.reporte(identificacionUsuario, "TRANSACCION.txt");
                transaccion.reporte(identificacionUsuario, "TRASLADO.txt");
                break;
            default:
                break;
        }
    }
}
