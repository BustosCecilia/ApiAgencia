package Agency;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.Arrays.asList;

public class AgencyImpl implements AgencyService {

    public static Collection<Agency> agencies = new LinkedList<>();

    public AgencyImpl(){}

    @Override
    public Collection<Agency> getAgency(String url)  {
        // imprimo mi url formada
        System.out.println("url es " + url);
        try {
            String data = readUrl(url);
            System.out.println(data);
            //Array de agencias
            JsonParser jsonparser = new JsonParser();
            JsonObject jsonObject = jsonparser.parse(data).getAsJsonObject();
            Agency[] agencies = new Gson().fromJson(jsonObject.get("results"), Agency[].class);
        } catch (Exception e) {
            System.out.println("error de obj a json");
            e.printStackTrace();
        }
        return agencies;
    }

    /* quiero que me devuelva el resultado de una url */
    private String readUrl(String urlString) throws IOException { //Malforme esta dentro
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
            System.out.println(buffer.toString());
            return buffer.toString();
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }


}
