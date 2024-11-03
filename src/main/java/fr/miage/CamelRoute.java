package fr.miage;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.sjms2.Sjms2Component;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class CamelRoute extends RouteBuilder {
    
    @Override
    public void configure() throws Exception {
        from("file:data/input?noop=true")
            .to("sjms2:queue:INF2");

        from("sjms2:queue:INF2")
            .to("file:data/output");
    }

    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

        ActiveMQConnectionFactory activeMqConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Sjms2Component component = new Sjms2Component();
        component.setConnectionFactory(activeMqConnectionFactory);
        context.addComponent("sjms2", component);

        context.addRoutes(new CamelRoute());

        context.start();
        Thread.sleep(10000);
        context.stop();
    }
}
