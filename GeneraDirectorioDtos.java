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

public class GeneraDirectorioDtos {

    public void generaDirectorioDtos(String nombreDirectorioPrincipal, String nombreDirectorioDtos, String nombreTablaValidada, String tablaCamelCase, Integer tipoSo) throws IOException, ClassNotFoundException, SQLException {
        File directorioDtos = new File(nombreDirectorioPrincipal+"/"+nombreDirectorioDtos);

        System.out.println("este es el directorio dto " + directorioDtos);
        
        Utilitarios utilitarios = new Utilitarios();
        String nombreTablaMayusculaInicial =  utilitarios.generaMayusculaInicial(tablaCamelCase);
                        
        if (directorioDtos.mkdir()) {
            System.out.println("   Directorio " + nombreDirectorioDtos + "/dtos creado satisfactoriamente.");

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

            System.out.println("CREANDO EL ARCHIVO ENTIDAD... ");

            Connection conexion;
            Statement st;

            ResultSetMetaData rsmetadatos;

            Class.forName("org.postgresql.Driver");

            conexion = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword);

            st = conexion.createStatement();

            System.out.println("Obteniendo Informacion sobre una base de datos...");

            System.out.println("\nObteniendo Informacion sobre una consulta con un ResultSet...");

            System.out.println("select * from " + schema + "." + nombreTablaValidada);

            ResultSet rs = st.executeQuery("select * from " + schema + "." + nombreTablaValidada);

           

            rsmetadatos = rs.getMetaData();

            int col = rsmetadatos.getColumnCount();

            ResultSet relaciones = st.executeQuery("SELECT k1.table_schema,\n" + //
                    "       k1.table_name,\n" + //
                    "       k1.column_name,\n" + //
                    "       k2.table_schema AS referenced_table_schema,\n" + //
                    "       k2.table_name AS referenced_table_name,\n" + //
                    "       k2.column_name AS referenced_column_name\n" + //
                    "FROM information_schema.key_column_usage k1\n" + //
                    "JOIN information_schema.referential_constraints fk USING (constraint_schema, constraint_name)\n" + //
                    "JOIN information_schema.key_column_usage k2\n" + //
                    "  ON k2.constraint_schema = fk.unique_constraint_schema\n" + //
                    " AND k2.constraint_name = fk.unique_constraint_name\n" + //
                    " AND k2.ordinal_position = k1.position_in_unique_constraint\n" + //
                    " where k1.table_name ='" + nombreTablaValidada + "';");

            String camelCaseRelacionesCampo = null;
            String camelCaseRelacionesTabla = null;

            System.out.println("Columnas: " + col);









            try (FileWriter fw = new FileWriter(nombreDirectorioPrincipal+"/"+nombreDirectorioDtos + "/"+nombreTablaMayusculaInicial+"DTO.java", true);
                
               
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {

                        out.println("package " + paquete + "." + nombreTablaValidada+ ".entity.dtos;");
                        out.println("");
                        out.println("import java.math.BigDecimal;");
                        out.println("import java.time.LocalDate;");
                        out.println("import java.time.OffsetDateTime;");
                        out.println("import java.util.Set;");
                        out.println("");
                        out.println("import lombok.Data;");
                        out.println("import lombok.Getter;");
                        out.println("import lombok.Setter;");
                        out.println("import lombok.ToString;");
                        out.println("");
  
                        out.println("@Data");
                        out.println("@Getter");
                        out.println("@Setter");
                        out.println("@ToString");
                        out.println("public class " + nombreTablaMayusculaInicial + "DTO {");
                        out.println("");
    
                        for (int i = 1; i <= col; i++) {
    
                            String tipoJava = utilitarios.generaTipoJava(rsmetadatos.getColumnClassName(i));
    
                            String nombreCamelcase = utilitarios.camelCase(rsmetadatos.getColumnName(i));
    

                            switch (tipoJava) {
                                case "Integer":
                                out.println("   private Long " + nombreCamelcase + ";");
                                    break;
                                case "Date":
                                out.println("   private LocalDate " + nombreCamelcase + ";");
                                    break;
                                case "Timestamp":
                                out.println("   private OffsetDateTime " + nombreCamelcase + ";");
                                        break;

                                    
                            
                                default:
                                out.println("   private " + tipoJava + " " + nombreCamelcase + ";");
                                    break;
                            }
    
                            out.println("");
                        }
    
                        while (relaciones.next()) {
    
                            camelCaseRelacionesCampo = utilitarios.camelCase(relaciones.getString(3));
                            camelCaseRelacionesTabla = utilitarios.camelCase(relaciones.getString(5));
    
                           
    
                        
                            out.println("//Esto es una relacion: " + utilitarios.generaMayusculaInicial(camelCaseRelacionesTabla) + " "
                                    + camelCaseRelacionesCampo + ";");
                            out.println("");
    
                        }
                        out.println("}");
            
                        System.out.println("      Archivo " + nombreTablaMayusculaInicial + "DTO.java creado satisfactoriamente.");  

                   

                   

            } catch (IOException e) {
                // exception handling left as an exercise for the reader
            }
        }

    }

}
