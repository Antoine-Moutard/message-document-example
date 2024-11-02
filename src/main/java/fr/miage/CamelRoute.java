package fr.miage;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

import java.util.Map;

public class CamelRoute extends RouteBuilder {

    public void configure() throws Exception {

        from("file:data/input?noop=true")

            .unmarshal().json()

            .setBody(exchange -> {
                Map<String, Object> user = exchange.getIn().getBody(Map.class);
                user.put("status", "active");
                return user;
            })

            .marshal().json()

            .to("file:data/output");
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new CamelRoute());
        main.run(args);
    }
}
