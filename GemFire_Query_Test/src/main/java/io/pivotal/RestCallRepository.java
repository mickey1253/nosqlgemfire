package io.pivotal;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.joda.time.DateTime;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by 505007855 on 7/10/2017.
 */

@Repository
public class RestCallRepository {


    public void getVehiclesInfo(String username, String password) throws URISyntaxException{

        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        System.setProperty("https.cipherSuites", "TLS_RSA_WITH_AES_256_CBC_SHA256");

        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add("User-Agent", "Penske(ConnectedFleet)/2.0.0 Java/1.8.0_20");
        headers.add("Accept", "text/xml");

        ResponseEntity<String> responseEntity = doRequestVehicles(username, password, headers);

        System.out.println(responseEntity.getBody());
    }

    private ResponseEntity<String> doRequestVehicles(String username, String password, MultiValueMap<String, String> headers) throws URISyntaxException {
        RestTemplate template = getAuthRestTemplate(username,password, false);
       String urlString = "http://ws.xataxrs.com/VehicleWebService.svc/vehicles/?IsActive=Both";
       // String urlString = "http://ws.xataxrs.com/VehicleWebService.svc/vehicles";
        RequestEntity<String> requestEntity =
                new RequestEntity<>(headers, HttpMethod.GET, new URI(urlString));

        return template.exchange(requestEntity, String.class);

    }

    protected RestTemplate getAuthRestTemplate(String username, String password, boolean useSystemProperties) {
        BasicCredentialsProvider credentialsProvider =  new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider);
        if(useSystemProperties) {
            httpClientBuilder = httpClientBuilder.useSystemProperties();
        }
        HttpClient httpClient = httpClientBuilder.build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(requestFactory);
    }


}
