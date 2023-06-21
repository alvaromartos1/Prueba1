package com.example.backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class Controlador {
    static Info info = new Info();
    static Parse parse = new Parse();

    public static Boolean Actualizar(){
        try {

            info = parse.Extraer_datos();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

////////////////////////////////Datos Producto////////////////////////////////////

    @PostMapping(value = "/data",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public static ResponseEntity<ArrayList<Producto>> addUser (@RequestBody Producto NewEle)
    {
        info.data.add(NewEle);
        return new ResponseEntity<>(info.data, HttpStatus.CREATED);
    }

    @PutMapping(value = "/data/codigo_geometria={id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public static ResponseEntity<ArrayList<Producto>> updateUser (@PathVariable("id") String id, @RequestBody Producto newData)
    {
        boolean elementFound = false;
        for (int i = 0; i < info.data.size(); i++) {
            if (info.data.get(i).codigo.equals(id)) {
                info.data.set(i, newData);
                elementFound = true;
                break;
            }
        }

        if (elementFound) {
            return new ResponseEntity<>(info.data, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/data")
    public static ArrayList<Producto> datos_GET(){
        return info.data;
    }

}
