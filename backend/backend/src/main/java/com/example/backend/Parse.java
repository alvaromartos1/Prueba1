
package com.example.backend;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)

public class Parse {
    Gson gson=new Gson();
    Info info=new Info();

    public Info Extraer_datos(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("template.json")));
        info = gson.fromJson(reader ,Info.class);//creamos el objeto en función de los datos recibidos
        return info;
    }

    public int Guardar_datos(){
        try{
            gson = new GsonBuilder().setPrettyPrinting().create();//gson con prettyprint
            String data= gson.toJson(Controlador.info);

            FileWriter archivo = new FileWriter(String.valueOf(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("template.json"))),false);
            archivo.write(data);
            archivo.close();
            return 1;

        }catch (Exception e){
            System.out.println("HA PETAO");
            return -1;
        }
    }
}
