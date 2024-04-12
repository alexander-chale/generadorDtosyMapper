import java.io.File;

public class Utilitarios {
    // Todas estas validaciones deben ser mas robustas, esa es la idea, luego
    // las arreglo mejor

    public String validaPalabra(String palabra) {
        // Le quita todos los caracteres especiales incluidos los numeros.
        // System.out.println("validaPalabra " + palabra);
        return palabra.replaceAll("[^A-Za-z]", "");
    }

    public Boolean validaNombreTabla(String nombreTabla){
        // hacer logica de validacion del nombre de la tabla
        return true;
    }

    public String generaTipoJava(String tipo) {
        String[] parts = tipo.split("\\.");
                    String tipoJava = parts[2]; // java0.lang1.String2
                   return tipoJava;
    }


    public String generaMayusculaInicial(String palabra) {
        return palabra.toUpperCase().charAt(0)
                + palabra.substring(1, palabra.length());
    }

    public String generaMinusculaInicial(String palabra) {
        return palabra.toLowerCase().charAt(0)
                + palabra.substring(1, palabra.length());
    }

    public Integer valida0o1(Integer numero) {
        // Aqui aun no se que mas validar
        if ((numero == 0) || (numero == 1)) {
            return numero;
        } else {
            return 1000;
        }

    }

    public String camelCase(String s) {
        StringBuffer out = new StringBuffer();
        boolean doUppercase = false;
        for(int i = 0; i<s.length();i++) {
           char c = s.charAt(i);
           // append if appropriate
           if(c != '_') {
              out.append(doUppercase ? Character.toUpperCase(c) : c);
           }
           // set capitalisation for next iteration
           doUppercase = ( c == '_' || Character.isDigit(c));
        }
        return out.toString();
     }


    public void deleteFile(File file) {
        // Elimina archivos y careptas creados previamente
        if (file.exists()) {
            if (file.isFile())
                file.delete();
            else {
                File f[] = file.listFiles();
                for (int i = 0; i < f.length; i++) {
                    deleteFile(f[i]);
                }
                file.delete();
            }
        } else
            // System.out.println("el archivo no existe");
            ;

    }

}
