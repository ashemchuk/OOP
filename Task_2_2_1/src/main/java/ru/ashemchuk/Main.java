package ru.ashemchuk;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import ru.ashemchuk.Config.PizzeriaConfig;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File json  = new File("src/main/resources/config.json");
        PizzeriaConfig cfg = mapper.readValue(json, PizzeriaConfig.class);
        System.out.println(cfg);
    }
}
