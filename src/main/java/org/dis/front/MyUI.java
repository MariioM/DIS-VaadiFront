package org.dis.front;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.dis.back.BRException;
import org.dis.back.EmpleadoBR;
import org.dis.back.TipoEmpleado;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {


    private TextField creaLabel(String texto){
        TextField etiqueta = new TextField();
        etiqueta.setCaption(texto);
        return etiqueta;
    }
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        final HorizontalLayout salarioBruto = new HorizontalLayout();
        final HorizontalLayout salarioNeto = new HorizontalLayout();
        final VerticalLayout salarioBrutoContenedor = new VerticalLayout();
        final VerticalLayout salarioNetoContenedor = new VerticalLayout();
        final VerticalLayout contenedorLabelBruto = new VerticalLayout();
        final VerticalLayout contenedorLabelNeto = new VerticalLayout();

        //TextField tipo = creaLabel("Tipo de Empleado");
        ComboBox <String> tipo = new ComboBox<>("Tipo de Empleado",
                Arrays.asList(TipoEmpleado.ENCARGADO, TipoEmpleado.VENDEDOR));
        TextField ventasMes = creaLabel("Ventas de Mes");
        TextField horasExtra = creaLabel("Horas Extra");
        TextField salarioNetoI = creaLabel("Salario Neto");
        salarioBruto.addComponents(tipo, ventasMes, horasExtra);
        salarioNeto.addComponents(salarioNetoI);
        Button botonSalarioBruto = new Button("Calcular Salario Bruto");
        botonSalarioBruto.addClickListener(e -> {
            String tipoEmpleadoIn = tipo.getValue();
            double ventasMesIn = Double.parseDouble(ventasMes.getValue());
            double horasExtraIn = Double.parseDouble(horasExtra.getValue());
            EmpleadoBR empleado = new EmpleadoBR();
            try {
                double resultado = empleado.calculaSalarioBruto(tipoEmpleadoIn, ventasMesIn, horasExtraIn);
                Label labelSalarioBruto = new Label("El salario bruto obtenido es: " + resultado);
                salarioBrutoContenedor.addComponent(labelSalarioBruto);
                contenedorLabelBruto.removeAllComponents();
                contenedorLabelBruto.addComponents(labelSalarioBruto);
            } catch (BRException ex) {
                Label labelSalarioBruto =  new Label(ex.getMessage());
                contenedorLabelBruto.removeAllComponents();
                contenedorLabelBruto.addComponents(labelSalarioBruto);
                throw new RuntimeException(ex);
            }
        });

        Button botonSalarioNeto = new Button("Calcular Salario Neto");
        botonSalarioNeto.addClickListener(e -> {
            double SalarioBrutoIn = Double.parseDouble(salarioNetoI.getValue());
            EmpleadoBR empleado = new EmpleadoBR();
            try {
                double resultado = empleado.calculaSalarioNeto(SalarioBrutoIn);
                Label labelSalarioNeto = new Label("El salario neto obtenido es: " + resultado);
                salarioNetoContenedor.addComponent(labelSalarioNeto);
                contenedorLabelNeto.removeAllComponents();
                contenedorLabelNeto.addComponents(labelSalarioNeto);
            } catch (BRException ex) {
                Label labelSalarioNeto = new Label(ex.getMessage());
                contenedorLabelNeto.removeAllComponents();
                contenedorLabelNeto.addComponents(labelSalarioNeto);
            }
        });

        salarioBrutoContenedor.addComponents(salarioBruto, botonSalarioBruto, contenedorLabelBruto);
        salarioNetoContenedor.addComponents(salarioNeto, botonSalarioNeto, contenedorLabelNeto);

        TabSheet tabs = new TabSheet();
        tabs.addTab(salarioBrutoContenedor).setCaption("Calcular Salario Bruto");
        tabs.addTab(salarioNetoContenedor).setCaption("Calcular Salario Neto");

        layout.addComponents(tabs);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
