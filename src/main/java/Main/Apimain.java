package Main;

import Agency.AgencyImpl;
import Agency.AgencyService;

import java.io.IOException;
import java.net.MalformedURLException;

import static spark.Spark.get;
import static spark.Spark.port;

public class Apimain {
    public static void main(String[] args) {
        AgencyService agencyService = new AgencyImpl();

        port(8081);
        get("/agencias", (request, response) -> {
            String site_id = request.queryParams("site_id");
            String payment_method_id = request.queryParams("payment_method_id");
            String near_to = request.queryParams("near_to");
            String limit = request.queryParams("limit");
            String offset = request.queryParams("offset");
            String urlArmada = armarUrl(site_id, payment_method_id, near_to, limit, offset);
            response.status(200);
            response.type("application/json");
            return agencyService.getAgency(urlArmada);

        });

    }

    static String armarUrl(String site_id, String payment_method_id, String near_to, String limit, String offset)
            throws IOException {

        // seteo url
        String url = "https://api.mercadolibre.com/sites/";
        // me fijo si exiten los parametros
        if (site_id == null) {
            throw new MalformedURLException("El site_id es nulo!");
        } else {
            url = url + site_id + "/payment_methods/";
            if (payment_method_id == null) {
                throw new MalformedURLException("El payment_method_id es nulo");
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
                // imprimo mi url formada
                System.out.println("url es " + url);

                return url;
            }
        }
    }
}