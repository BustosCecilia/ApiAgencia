package Main;
import  Enum.CriterioOrden;
import Agency.Agency;
import Agency.AgencyException;
import Agency.AgencyImpl;
import Agency.AgencyService;
import Operador.Operador;
import StandardResponse.StandardResponse;
import StandardResponse.StatusResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

import static Enum.CriterioOrden.*;
import static spark.Spark.get;
import static spark.Spark.port;

public class Apimain {
    public static void main(String[] args) {
        AgencyService agencyService = new AgencyImpl();
        port(8082);

        get("/agencias", (request, response) -> {
            String site_id = request.queryParams("site_id");
            String payment_method_id = request.queryParams("payment_method_id");
            String near_to = request.queryParams("near_to");
            String limit = request.queryParams("limit");
            String offset = request.queryParams("offset");
            String orden = request.queryParams("orden");
            Agency[] agencies = null;

            String urlArmada = armarUrl(site_id, payment_method_id, near_to, limit, offset);
            System.out.println("url es " + urlArmada);
            System.out.println("orden elegido: " + orden);
            try {
                String data = readUrl(urlArmada);
                //System.out.println(data);
                //Array de agencias
                JsonParser jsonparser = new JsonParser();
                JsonObject jsonObject = jsonparser.parse(data).getAsJsonObject();
                agencies = new Gson().fromJson(jsonObject.get("results"), Agency[].class);

            } catch (Exception e) {
                System.out.println("error de obj a json");
                e.printStackTrace();
            }
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(ordenarAgencias(agencies, orden))));
        });

    }

    static String armarUrl(String site_id, String payment_method_id, String near_to,
                           String limit, String offset) throws AgencyException {
        // seteo url
        String url = "https://api.mercadolibre.com/sites/";
        // me fijo si exiten los parametros

        if (site_id.isEmpty()) {
            throw new AgencyException("El site_id no puede ser nulo");
        } else {
            url = url + site_id + "/payment_methods/";
            if (payment_method_id.isEmpty()) {
                throw new AgencyException("El payment_methods no puede ser nulo");
            } else {
                url = url + payment_method_id + "/agencies?";
                if (near_to != null) {
                    url = url + "near_to=" + near_to;
                    if (limit != null) {
                        url = url + "&limit=" + limit;
                        if (offset != null) {
                            url = "&offset=" + offset;
                        }
                    }
                }
                return url;
            }
        }
    }

    /* quiero que me devuelva el resultado de una url */
    static String readUrl(String urlString) throws IOException { //Malforme esta dentro
        URL url = new URL(urlString);
        BufferedReader reader = null;
        // me lo convierte en un objeto url
        try {

            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder buffer = new StringBuilder();
            char[] chars = new char[1024];
            int read = 0; // leo la cantidad de caracteres
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }


        }
    }

    static Agency[] ordenarAgencias(Agency[] agencies, String orden) {
        switch (orden) {
            case "address_line":
                System.out.println(ADDRESS_LINE);
                setOrden(ADDRESS_LINE,agencies);
                Operador.ordenar(agencies);
                break;
            case "agency_code":
                System.out.println(AGENCY_CODE);
                setOrden(AGENCY_CODE,agencies);
                Operador.ordenar(agencies);
                break;
            case "distance":
                System.out.println(DISTANCE);
                setOrden(DISTANCE, agencies);
                Operador.ordenar(agencies);
                break;
            default:
                throw new IllegalStateException("Unexpected value: ");
        }


        return agencies;
    }

    static void setOrden(CriterioOrden criterioOrden,Agency[] agencies) {
        for (Agency agency : agencies) {
            agency.setCriterioOrden(criterioOrden);
        }
    }
}