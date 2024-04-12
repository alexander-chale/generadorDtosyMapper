import java.io.File;

public class GeneraDirectorioPrincipal {

    public void generaDirectorioPrincipal(String nombreEntidad) {
        Utilitarios utilitarios = new Utilitarios();

        File archivo = new File(nombreEntidad);
        // eliminamos la carpeta nombreEntidad con todos sus subcarpetas
        utilitarios.deleteFile(archivo);

        File directorioPrincipal = new File(nombreEntidad);

        if (directorioPrincipal.mkdir()) {
            System.out.println("   Directorio " + nombreEntidad + " creado satisfactoriamente.");
        }

    }
}
