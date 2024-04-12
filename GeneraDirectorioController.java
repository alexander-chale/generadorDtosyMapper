import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GeneraDirectorioController {
                                                                                                                                     
    public void generaDirectorioController(String nombreDirectorioPrincipal,String nombreDirectorioController, String nombreEntidad, String entidadMayusculaInicial, String nombreDeAplicacion, int tipoId) {
        File directorioController = new File(nombreDirectorioPrincipal+"/"+nombreDirectorioController);
            if (directorioController.mkdir()) {
                System.out.println("   Directorio " + nombreEntidad + "/controller creado satisfactoriamente.");

                try (FileWriter fw = new FileWriter(
                    nombreDirectorioPrincipal+"/"+nombreDirectorioController + "/" + entidadMayusculaInicial + "Controller.java",
                        true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw)) {
                    out.println("package " + nombreDeAplicacion + "." + nombreEntidad + ".controller;");
                    out.println("");
                    out.println("import java.util.List;");
                    out.println("import org.springframework.beans.factory.annotation.Autowired;");
                    out.println("import org.springframework.http.HttpStatus;");
                    out.println("import org.springframework.http.ResponseEntity;");
                    out.println("import org.springframework.web.bind.annotation.CrossOrigin;");
                    out.println("import org.springframework.web.bind.annotation.GetMapping;");
                    out.println("import org.springframework.web.bind.annotation.PathVariable;");
                    out.println("import org.springframework.web.bind.annotation.PostMapping;");
                    out.println("import org.springframework.web.bind.annotation.RequestBody;");
                    out.println("import org.springframework.web.bind.annotation.RequestMapping;");
                    out.println("import org.springframework.web.bind.annotation.RestController;");
                    out.println("");
                    if (0 == tipoId) {
                        out.println("import " + nombreDeAplicacion + ".bases.controllers.BaseControllerIdLongImpl;");
                    }

                    else {
                        out.println("import " + nombreDeAplicacion + ".bases.controllers.BaseControllerIdStringImpl;");

                    }

                    out.println(
                            "import " + nombreDeAplicacion + "." + nombreEntidad + ".entity." + entidadMayusculaInicial
                                    + ";");
                    out.println("import " + nombreDeAplicacion + "." + nombreEntidad + ".service.impl."
                            + entidadMayusculaInicial + "ServiceImpl;");

                    out.println("import com.bcv.cusg.empresa.entity.dtos.EmpresaDTO;");
                    out.println("");
                    out.println("@RestController");
                    out.println("@CrossOrigin(origins = \"*\")");
                    out.println("@RequestMapping(path = \"api/v1/" + nombreEntidad + "\")");
                    if (0 == tipoId) {
                        out.println(
                                "public class " + entidadMayusculaInicial
                                        + "Controller extends BaseControllerIdLongImpl<"
                                        + entidadMayusculaInicial + "," + entidadMayusculaInicial + "ServiceImpl> {");
                    } else {
                        out.println(
                                "public class " + entidadMayusculaInicial
                                        + "Controller extends BaseControllerIdStringImpl<"
                                        + entidadMayusculaInicial + "," + entidadMayusculaInicial + "ServiceImpl> {");

                    }
                    out.println("");
                    out.println("   @Autowired");
                    out.println("   protected " + entidadMayusculaInicial + "Service " + nombreEntidad + "Service;");
                    out.println("");

                    out.println("   @GetMapping(\"/findALL"+entidadMayusculaInicial+"Dto\")");
                    out.println("   public ResponseEntity<?> findALL"+entidadMayusculaInicial+"Dto() throws Exception {");
                    out.println("      try {");
                    out.println("        return ResponseEntity.status(HttpStatus.OK).body(" + nombreEntidad + "Service.findALL"+entidadMayusculaInicial+"Dto());");
                    out.println("      } catch (Exception e) {");
                    out.println("      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());");
        	        out.println("    }");
                    out.println("  }");


                    out.println("");
                    out.println("}");

                    if (0 == tipoId) {
                        System.out.println("      Archivo " + entidadMayusculaInicial
                                + "Controller.java con Id Long creado satisfactoriamente.");
                    }

                    else {
                        System.out.println("      Archivo " + entidadMayusculaInicial
                                + "Controller.java con Id String creado satisfactoriamente.");

                    }

                } catch (IOException e) {
                    // exception handling left as an exercise for the reader
                }
            }
        }


       
}
