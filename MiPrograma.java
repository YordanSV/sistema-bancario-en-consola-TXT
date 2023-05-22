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


    public static void mostrarMenuPrincipal(Usuario usuario){
        if(usuario.esAdministrador()){
            usuario.mostrarMenuAdmin();
        }else{
            
        }
    }


}
