import spark.Spark;

import static spark.Spark.get;

public class Hola {
    public static void main(String[] args) {
       get("/helloworld", (req, res) -> "Hello World1");
    }
}
