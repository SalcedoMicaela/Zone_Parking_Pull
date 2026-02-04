package ec.edu.espe.ms_clientes.utils;

public class CedulaRucValidator {


    public static boolean validarCedula(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) return false;

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        int tercerDigito = Character.getNumericValue(cedula.charAt(2));

        if (provincia < 1 || provincia > 24) return false;
        if (tercerDigito < 0 || tercerDigito > 5) return false;

        int suma = 0;

        for (int i = 0; i < 9; i++) {

            int digito = Character.getNumericValue(cedula.charAt(i));

            if (i % 2 == 0) {
                digito *= 2;
                if (digito > 9) digito -= 9;
            }

            suma += digito;
        }

        int decenaSuperior = ((suma + 9) / 10) * 10;
        int digitoVerificador = decenaSuperior - suma;

        if (digitoVerificador == 10) digitoVerificador = 0;

        return digitoVerificador == Character.getNumericValue(cedula.charAt(9));
    }


    public static boolean validarRuc(String ruc) {
        if (ruc == null || !ruc.matches("\\d{13}")) return false;

        int tercerDigito = Character.getNumericValue(ruc.charAt(2));

        // Persona natural (cedula + 001)
        if (tercerDigito >= 0 && tercerDigito <= 5) {
            return ruc.endsWith("001")
                    && validarCedula(ruc.substring(0, 10));
        }

        // Entidad pública (tercer dígito 6)
        if (tercerDigito == 6) {
            int[] coef = {3, 2, 7, 6, 5, 4, 3, 2};
            int suma = 0;

            for (int i = 0; i < 8; i++) {
                suma += Character.getNumericValue(ruc.charAt(i)) * coef[i];
            }

            int modulo = suma % 11;
            int dv = modulo == 0 ? 0 : 11 - modulo;

            return dv == Character.getNumericValue(ruc.charAt(8))
                    && ruc.endsWith("0001");
        }

        // Sociedad privada (tercer dígito 9)
        if (tercerDigito == 9) {
            int[] coef = {4, 3, 2, 7, 6, 5, 4, 3, 2};
            int suma = 0;

            for (int i = 0; i < 9; i++) {
                suma += Character.getNumericValue(ruc.charAt(i)) * coef[i];
            }

            int modulo = suma % 11;
            int dv = modulo == 0 ? 0 : 11 - modulo;

            return dv == Character.getNumericValue(ruc.charAt(9))
                    && ruc.endsWith("001");
        }

        return false;
    }
}

