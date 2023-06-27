
package org.vaadin.example;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Period;
import java.time.LocalDate;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    VerticalLayout añadir;
    VerticalLayout mostrar;
    HttpRequest request;
    HttpClient client = HttpClient.newBuilder().build();
    HttpResponse<String> response;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    private String getZona() {
        try {
            String resource = "http://localhost:8090/data";
            //System.out.println(resource);
            request = HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //System.out.println(response.body());
        } catch (URISyntaxException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    public Producto postnuevo(Producto Nueva){
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8090/products"))
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(gson.toJson(Nueva)))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return gson.fromJson(response.body(), new TypeToken<Producto>(){}.getType());
    }

    public MainView(@Autowired GreetService service) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        String response = getZona();
        Producto[] zonas = gson.fromJson(response, Producto[].class);

        añadir = new VerticalLayout();
        TextField nombre = new TextField("Nombre");
        TextField categoria = new TextField("Categoría");
        TextField precio = new TextField("Precio");
        TextField EAN13 = new TextField("EAN13");

        Button submit = new Button("Submit");

        añadir.add(nombre, categoria, precio, EAN13, submit);
        submit.addClickListener(event -> {
            String nombreValue = nombre.getValue();
            String categoriaValue = categoria.getValue();
            String precioValue = precio.getValue();
            String ean13Value = EAN13.getValue();
            // Do something with the values
            // for example, print them
            System.out.println("Nombre: " + nombreValue);
            System.out.println("Categoría: " + categoriaValue);
            System.out.println("Precio: " + precioValue);
            System.out.println("EAN13: " + ean13Value);

            Producto newprod = new Producto(nombreValue,categoriaValue,Double.parseDouble(precioValue),Long.parseLong(ean13Value));
            postnuevo(newprod);
        });


        mostrar = new VerticalLayout();

        Grid<Producto> grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.NONE);

        grid.setItems(zonas);
        grid.addColumn(Producto::getNombre).setHeader("Nombre");
        grid.addColumn(Producto::getCategoria).setHeader("Categoría");
        grid.addColumn(Producto::getPrecio).setHeader("Precio");
        grid.addColumn(Producto::getEAN13).setHeader("EAN13");




        mostrar.add(grid);



        mostrar.setVisible(false);

        Tab option1 = new Tab("Form");
        Tab option2 = new Tab("Tabla");


        Tabs tabs = new Tabs(option1, option2);
        tabs.addSelectedChangeListener(event -> {
                    this.hideContainers();
                    Tab selectedTab = event.getSelectedTab();
                    if (selectedTab == option1){
                        añadir.setVisible(true);
                    }
                    if (selectedTab == option2){
                        mostrar.setVisible(true);
                    }
                }
                //           setContent(event.getSelectedTab())
        );
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(false);
        //setContent(tabs.getSelectedTab());

        add(tabs, content,añadir,mostrar );


    }

    private void hideContainers(){
        añadir.setVisible(false);
        mostrar.setVisible(false);

    }

}

