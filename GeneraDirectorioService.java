import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GeneraDirectorioService {

    public void generaDirectorioService(String nombreDirectorioPrincipal, String directorioServicio, String nombreEntidad, String entidadMayusculaInicial,
            String nombreDeAplicacion) {

        File directorioService = new File(nombreDirectorioPrincipal+"/"+directorioServicio);
        System.out.println("este es el directorio "+ directorioService);

        if (directorioService.mkdir()) {
            System.out.println("   Directorio " + nombreEntidad + "/service creado satisfactoriamente.");
            System.out.println("esto es lo que crea "+nombreDirectorioPrincipal+"/"+directorioServicio + "/"+entidadMayusculaInicial+"Service.java");
            try (FileWriter fw = new FileWriter(nombreDirectorioPrincipal+"/"+directorioServicio + "/"+entidadMayusculaInicial+"Service.java", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.println("package " + nombreDeAplicacion + "." + nombreEntidad + ".service;");
                out.println("");

                out.println(
                        "import " + nombreDeAplicacion + "." + nombreEntidad + ".entity." + entidadMayusculaInicial
                                + ";");
                
                out.println(
                                    "import " + nombreDeAplicacion + "." + nombreEntidad + ".entity.dtos." + entidadMayusculaInicial
                                            + "DTO;");
                out.println("");
                out.println("import java.util.List;");  
                out.println("");        
                out.println("import com.bcv.cusg.bases.services.BaseService;");
                out.println("");
              
                    out.println("public interface " + entidadMayusculaInicial + "Service extends BaseService<"
                            + entidadMayusculaInicial + ", Long> {");
             

                out.println("");
                out.println("      public List<EmpresaDTO> findALLEmpresaDto() throws Exception;");
                out.println("");
                out.println("}");
              
                    System.out.println("      Archivo " + entidadMayusculaInicial
                            + "Service.java con Id Long creado satisfactoriamente.");
               

            } catch (IOException e) {
                // exception handling left as an exercise for the reader
            }
        }
    }

}
