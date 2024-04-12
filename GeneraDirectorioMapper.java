import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class GeneraDirectorioMapper {

    public void generaDirectorioMapper(String nombreDirectorioPrincipal, String nombreDirectorioMapper, String nombreTablaValidada, String tablaCamelCase, Integer tipoSo) throws IOException, ClassNotFoundException, SQLException {
        File directorioMapper = new File(nombreDirectorioPrincipal+"/"+nombreDirectorioMapper);
        
        Utilitarios utilitarios = new Utilitarios();
        String nombreTablaMayusculaInicial =  utilitarios.generaMayusculaInicial(tablaCamelCase);
                
        if (directorioMapper.mkdir()) {
            System.out.println("   Directorio " + nombreDirectorioMapper + "/mapper creado satisfactoriamente.");

            Properties config = new Properties();
            InputStream configInput = null;

            if (tipoSo == 0){
                configInput = new FileInputStream("resources/config.properties");
            } else{
                configInput = new FileInputStream("C:\\config.properties");
            }
    

            
            config.load(configInput);

            String dataSourceUrl = config.getProperty("datasource.url");
            String dataSourceUsername = config.getProperty("datasource.username");
            String dataSourcePassword = config.getProperty("datasource.password");
            String schema = config.getProperty("schema");
            String paquete = config.getProperty("paquete");

            

            System.out.println("CREANDO EL ARCHIVO MAPPER... ");

            
           









            try (FileWriter fw = new FileWriter(nombreDirectorioPrincipal+"/"+nombreDirectorioMapper + "/"+nombreTablaMayusculaInicial+"Mapper.java", true);
                
               
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {

                        out.println("package " + paquete + "." + nombreTablaValidada+ ".entity.mapper;");
                        out.println("");
                        out.println("import org.mapstruct.Mapper;");
                        out.println("import org.mapstruct.Mapping;");
                        out.println("");
                        out.println("import " + paquete + "." + nombreTablaValidada+ ".entity."+nombreTablaMayusculaInicial+";");
                        out.println("import " + paquete + "." + nombreTablaValidada+ ".entity.dtos."+nombreTablaMayusculaInicial+"DTO;");
                        out.println("");
                        out.println("@Mapper(componentModel = \"spring\")");
                        out.println("public interface " + nombreTablaMayusculaInicial + "Mapper {");
                        out.println("");
                        out.println("   //@Mapping(target = \"campoEnDto\", source = \"campoEnJson\"");
                        out.println("   "+nombreTablaMayusculaInicial + "DTO toDto("+nombreTablaMayusculaInicial+" entity);");
                        out.println("");
    
                       
                    
                        out.println("}");
            
                        System.out.println("      Archivo " + nombreTablaMayusculaInicial + "Mapper.java creado satisfactoriamente.");  

                   

                   

            } catch (IOException e) {
                // exception handling left as an exercise for the reader
            }
        }

    }

}
