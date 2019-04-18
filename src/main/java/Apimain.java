import static spark.Spark.get;
import static spark.Spark.port;

public class Apimain {
    public static void main(String[] args) {
        port(8081);
        get("/agencia1", (req, res) -> "Nombre de la agencia " + req.queryParams("name"));

    }

    void armarUrl(){

    }
}

