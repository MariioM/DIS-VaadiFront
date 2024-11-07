package org.dis.back;

public class EmpleadoBR {

    public double calculaSalarioBruto( String tipoEmpleado, double ventasMes, double horasExtra) throws BRException{
        if (tipoEmpleado=="" || ventasMes < 0 || horasExtra < 0) {
            throw new BRException("Valores de entrada no válidos");
        }
        double total_salario = 0;
        if (tipoEmpleado == TipoEmpleado.VENDEDOR) {
            total_salario += TipoEmpleado.SUELDO_BASE_VENDEDOR;
        } else if (tipoEmpleado == TipoEmpleado.ENCARGADO) {
            total_salario += TipoEmpleado.SUELDO_BASE_ENCARGADO;
        }
        if (ventasMes >= 1000 && ventasMes < 1500) {
            total_salario += 100;
        } else if (ventasMes >= 1500) {
            total_salario += 200;
        }
        double salario_extra = horasExtra * TipoEmpleado.PRECIO_HORA;
        total_salario += salario_extra;
        return total_salario;
    }

    public double calculaSalarioNeto(double salarioBruto) throws BRException{
        if (salarioBruto < 0) {
            throw new BRException("Salario neto total menor que 0");
        }
        double salarioNeto = 0.0;
        if (salarioBruto < 1000.0) {
            salarioNeto = salarioBruto;
        } else if (salarioBruto >= 1000 && salarioBruto < 1500) {
            salarioNeto = salarioBruto * (1 - 0.16);
        } else if (salarioBruto >= 1500){
            salarioNeto = salarioBruto * (1 - 0.18);
        }
        return Math.round(salarioNeto * 10000.0) / 10000.0; //para que redondee a 4
    }
}
