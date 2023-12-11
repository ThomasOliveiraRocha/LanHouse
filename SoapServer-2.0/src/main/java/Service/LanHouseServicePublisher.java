
package Service;

import javax.xml.ws.Endpoint;

public class LanHouseServicePublisher {

    private static final String SERVICE_ADDRESS = "http://localhost:8080/lanhouseservice";

    public static void main(String[] args) {
        Endpoint.publish(SERVICE_ADDRESS, new LanHouseService());
        System.out.println("Serviço web da Lan House publicado em: " + SERVICE_ADDRESS);
    }
}
