package ru.ashemchuk;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import ru.ashemchuk.config.PizzeriaConfig;

public class Main {
    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File json = new File("src/main/resources/config.json");
            PizzeriaConfig cfg = mapper.readValue(json, PizzeriaConfig.class);
            Pizzeria p = new Pizzeria(cfg);
            p.configure();
            p.startSimulate();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
