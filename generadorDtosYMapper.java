import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class generadorDtosYMapper {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

        @SuppressWarnings("resource")
        Scanner so = new Scanner(System.in);
        Integer sistemaOperativo = null;
        Integer tipoSo = null;
        Utilitarios utilitarios = new Utilitarios();

        Boolean isLinux = true;
        while (isLinux) {

            System.out.println("Ingrese 0 = Si es Linux");
            System.out.println("Ingrese 1 = Para Windows");
            sistemaOperativo = so.nextInt();

            tipoSo = utilitarios.valida0o1(sistemaOperativo);

            if ((tipoSo == 0) || (tipoSo == 1)) {
                isLinux = false;
            } else {
                isLinux = true;
            }
        }

        if (tipoSo == 0) {
            System.out.println("Si ejecuta desde Linux, no se necesita ninguna configuración, disfrute!!!");
        } else {
            System.out.println(
                    "Si ejecuta desde Windows, debe colocar el archivo 'config.properties' en la raiz c: de windows");
        }

        @SuppressWarnings("resource")
        Scanner tipoConsulta = new Scanner(System.in);
        String nombreTabla = null;
        String tablaCamelCase = null;
        Boolean tipoQuery = true;
        Boolean tablaValidada = null;
        while (tipoQuery) {

            System.out.println("Ingrese Nombre de la tabla como se encuentra en la base de datos");
            nombreTabla = tipoConsulta.nextLine();
            tablaValidada = utilitarios.validaNombreTabla(nombreTabla);

            if ((tablaValidada == true)) {
                tipoQuery = false;
                tablaCamelCase = utilitarios.camelCase(nombreTabla);
            } else {
                tipoQuery = true;
            }


        } Properties config = new Properties();
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
        String nombreDeAplicacion = paquete;

     

        String nombreDirectorioPrincipal = "DtosyMapper";
        String nombreDirectorioDtos = "dtos";
        String nombreDirectorioMapper = "mapper";
        String nombreDirectorioService = "service";
        String nombreDirectorioServiceImpl = "service/impl";
        String nombreDirectorioController = "controller";
        int tipoId = 0; //esto hay que pedirlo por consola


        GeneraDirectorioPrincipal generaDirectorioPrincipal = new GeneraDirectorioPrincipal();
        generaDirectorioPrincipal.generaDirectorioPrincipal(nombreDirectorioPrincipal);

        GeneraDirectorioDtos generaDirectorioDtos = new GeneraDirectorioDtos();
        generaDirectorioDtos.generaDirectorioDtos(nombreDirectorioPrincipal, nombreDirectorioDtos, nombreTabla,
                tablaCamelCase, tipoSo);
        
        GeneraDirectorioMapper generaDirectorioMapper = new GeneraDirectorioMapper();
               
                generaDirectorioMapper.generaDirectorioMapper(nombreDirectorioPrincipal, nombreDirectorioMapper, nombreTabla, tablaCamelCase, tipoSo);

        String tablaMayusculaInicial = utilitarios.generaMayusculaInicial(tablaCamelCase);
        GeneraDirectorioService generaDirectorioService = new GeneraDirectorioService();
        generaDirectorioService.generaDirectorioService(nombreDirectorioPrincipal, nombreDirectorioService, tablaCamelCase,tablaMayusculaInicial, nombreDeAplicacion);
                                                 
        GeneraDirectorioServiceImpl generaDirectorioServiceImpl = new GeneraDirectorioServiceImpl();
        generaDirectorioServiceImpl.generaDirectorioServiceImpl(nombreDirectorioPrincipal, nombreDirectorioServiceImpl, nombreTabla, tablaMayusculaInicial, nombreDeAplicacion, tipoId);
                        

       GeneraDirectorioController generaDirectorioController = new GeneraDirectorioController();
       generaDirectorioController.generaDirectorioController(nombreDirectorioPrincipal, nombreDirectorioController, nombreTabla, tablaMayusculaInicial, nombreDeAplicacion, tipoId);


   

    }

}
