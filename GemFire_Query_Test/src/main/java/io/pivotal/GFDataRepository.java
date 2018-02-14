package io.pivotal;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.pdx.PdxInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by 505007855 on 3/8/2017.
 */

@Repository
public class GFDataRepository {

    @Autowired
    QueryConfig queryConfig;

    public List<String> readOmnitracsFlatFile( String inputPath) {

        List<String> unit_numbers = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Paths.get(inputPath), Charset.defaultCharset())){
            System.out.println("Omnitracs Flat File Path is ---> " + inputPath);

            String unit_number = "";
            for (String line : (Iterable<String>) lines::iterator){

                //   System.out.println(line);
                String[] fileds = line.split(",");

                if(fileds.length > 16) {
                    if ( fileds[7].equals("LEASE") &&  fileds[16].equals("WEGMANS FOOD MARKETS INC")) {
                        unit_number = fileds[1];
                        //   System.out.println(unit_number);
                        unit_numbers.add(unit_number);
                    }
                }
            }

        }catch (FileNotFoundException var1){
            System.out.println("Source File doesn't exist: " + var1);
        }catch (Exception var2){
            System.out.println("Error occurs when read flat file ------>: " + var2);
        }

        return unit_numbers;
    }


    public List<String> readUNITINFO_DWHFlatFile( String inputPath) {

        List<String> unit_numbers = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Paths.get(inputPath), Charset.defaultCharset())){
            System.out.println("UNITINFO_DWH Flat File Path is ---> " + inputPath);

            String unit_number = "";
            for (String line : (Iterable<String>) lines::iterator){

                //   System.out.println(line);
                String[] fileds = line.split("\\|");

                if(fileds.length > 16) {
                    if (  fileds[16].equals("WEGMANS FOOD MARKETS INC")) {
                        unit_number = fileds[1];
                        //   System.out.println(unit_number);
                        unit_numbers.add(unit_number);
                    }
                }
            }

        }catch (FileNotFoundException var1){
            System.out.println("Source File doesn't exist: " + var1);
        }catch (Exception var2){
            System.out.println("Error occurs when read flat file ------>: " + var2);
        }

        return unit_numbers;
    }

    public Map<String, String> getCommonWegmansVins(String ominitracsRegionFile, String UnitInfoRegionFile){

        Map<String, String> commonWegamans = new HashMap<>();

        Map<String, String> ominitracsRegion = new HashMap<>();

        Map<String, String> UnitInfoRegion = new HashMap<>();

        try {

            Stream<String> lines = Files.lines(Paths.get(ominitracsRegionFile), Charset.forName("Cp1252")/*Charset.defaultCharset()*/);
            System.out.println("ominitracs1 Region Flat File Path is ---> " + ominitracsRegionFile);

            /*for (String line : (Iterable<String>) lines::iterator){
                String[] fileds = line.split(",");
                ominitracsRegion.put(fileds[1], fileds[9]);
            }*/

            for (String line : (Iterable<String>) lines::iterator){
                String[] fileds = line.split(",");
                ominitracsRegion.put(fileds[0], fileds[1]);
            }

            System.out.println("ominitracsRegion size is:" + ominitracsRegion.size());

            Stream<String> lines1 = Files.lines(Paths.get(UnitInfoRegionFile), Charset.forName("Cp1252")/*Charset.defaultCharset()*/);
            System.out.println("UnitInfo1 Region Flat File Path is ---> " + UnitInfoRegionFile);

           /* for (String line : (Iterable<String>) lines1::iterator){
                String[] fileds = line.split("\\|");
                UnitInfoRegion.put(fileds[1], fileds[9]);
            }*/

            for (String line : (Iterable<String>) lines1::iterator){
                String[] fileds = line.split(",");
                UnitInfoRegion.put(fileds[0], fileds[1]);
            }
            System.out.println("UnitInfoRegion1 size is:" + UnitInfoRegion.size());

        }catch (FileNotFoundException var1){
            System.out.println("Source File doesn't exist: " + var1);
        }catch (Exception var2){
            System.out.println("Error occurs when read flat file ------>: " + var2);
        }

        for (Map.Entry<String, String> entry : ominitracsRegion.entrySet())
        {
            if((UnitInfoRegion.get(entry.getKey())) == null
                     /*|| UnitInfoRegion.get(entry.getKey()).length() == 0*/){
                System.out.println(entry);

                continue;
            }/*else if(UnitInfoRegion.get(entry.getKey()).equals(entry.getValue())){
               commonWegamans.put(entry.getKey(), entry.getValue());
                System.out.println(entry.getKey() + "," + entry.getValue());
            }*/
        }


       /* for (Map.Entry<String, String> entry : UnitInfoRegion.entrySet())
        {
            if(ominitracsRegion.get(entry.getKey())== null){
                commonWegamans.put(entry.getKey(), entry.getValue());
                System.out.println(entry.getKey() + "," + entry.getValue());
            }else if(UnitInfoRegion.get(entry.getKey()).equals(entry.getValue())){
                continue;
            }
        }*/

        return commonWegamans;

    }



    public Map<String, String> getCommonWegmansVins1(String ominitracsRegionFile, String UnitInfoRegionFile){

        Map<String, String> commonWegamans = new HashMap<>();

        Map<String, String> ominitracsRegion = new HashMap<>();

        Map<String, String> UnitInfoRegion = new HashMap<>();

        try {

            Stream<String> lines = Files.lines(Paths.get(ominitracsRegionFile), Charset.forName("Cp1252")/*Charset.defaultCharset()*/);
            System.out.println("ominitracs1 Region Flat File Path is ---> " + ominitracsRegionFile);

            /*for (String line : (Iterable<String>) lines::iterator){
                String[] fileds = line.split(",");
                ominitracsRegion.put(fileds[1], fileds[9]);
            }*/

            for (String line : (Iterable<String>) lines::iterator){
                String[] fileds = line.split(",");
                ominitracsRegion.put(fileds[0], fileds[1]);
            }

            System.out.println("ominitracsRegion size is:" + ominitracsRegion.size());

            Stream<String> lines1 = Files.lines(Paths.get(UnitInfoRegionFile), Charset.forName("Cp1252")/*Charset.defaultCharset()*/);
            System.out.println("UnitInfo1 Region Flat File Path is ---> " + UnitInfoRegionFile);

           /* for (String line : (Iterable<String>) lines1::iterator){
                String[] fileds = line.split("\\|");
                UnitInfoRegion.put(fileds[1], fileds[9]);
            }*/

            for (String line : (Iterable<String>) lines1::iterator){
                String[] fileds = line.split(",");
                UnitInfoRegion.put(fileds[0], fileds[1]);
            }
            System.out.println("UnitInfoRegion1 size is:" + UnitInfoRegion.size());

        }catch (FileNotFoundException var1){
            System.out.println("Source File doesn't exist: " + var1);
        }catch (Exception var2){
            System.out.println("Error occurs when read flat file ------>: " + var2);
        }

        for (Map.Entry<String, String> entry : ominitracsRegion.entrySet())
        {
            if((UnitInfoRegion.get(entry.getKey())) == null
                     /*|| UnitInfoRegion.get(entry.getKey()).length() == 0*/){
                System.out.println(entry);

                continue;
            }/*else if(UnitInfoRegion.get(entry.getKey()).equals(entry.getValue())){
               commonWegamans.put(entry.getKey(), entry.getValue());
                System.out.println(entry.getKey() + "," + entry.getValue());
            }*/
        }


       /* for (Map.Entry<String, String> entry : UnitInfoRegion.entrySet())
        {
            if(ominitracsRegion.get(entry.getKey())== null){
                commonWegamans.put(entry.getKey(), entry.getValue());
                System.out.println(entry.getKey() + "," + entry.getValue());
            }else if(UnitInfoRegion.get(entry.getKey()).equals(entry.getValue())){
                continue;
            }
        }*/

        return commonWegamans;

    }


    public void getValidFileds(String inputFile){

        try{
        Stream<String> lines = Files.lines(Paths.get(inputFile), Charset.forName("Cp1252")/*Charset.defaultCharset()*/);
        System.out.println("Input Flat File Path is ---> " + inputFile);


        for (String line : (Iterable<String>) lines::iterator){

            String[] fileds = line.split(",");


            if(
                    !fileds[1].trim().equals("Employee services".trim())
                            && !fileds[1].trim().equals("Fuel truck".trim())
                            && !fileds[1].trim().equals("spare unit".trim())
                            && !fileds[1].trim().equals("Yard horse".trim())
                            && !fileds[1].trim().equals("These are not ours".trim())
                            && !fileds[1].trim().equals("don’t have vin".trim())
                    ){

                System.out.println( /*"Unit_Number is: " + */fileds[0] + ","
                        /*+ " Vin_Number is: " */+ fileds[1]);
            }
        }
        System.out.println("Done validate input files");

    } catch (Exception e ) {
        System.out.println(e);
    }
}








    public void  writeOmnitracsVinNumbers(Map<String, String> OmnitracsVinNumbers, String FILE_NAME){
        File outputFile = new File(FILE_NAME);
        FileWriter writer = null;
        try {
            writer = new FileWriter(outputFile);
            System.out.println("Writing OmnitracsVinNumbers to " + FILE_NAME);

            long start = System.currentTimeMillis();

            for (Map.Entry<String, String> entry : OmnitracsVinNumbers.entrySet())
            {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n" );
            }

            writer.flush();
            long end = System.currentTimeMillis();
            System.out.println(OmnitracsVinNumbers.size() + " records finished in " + (end - start) / 1000f + " seconds");

        }catch(IOException var5) {
            System.err.println("Caught IOException: " +  var5.getMessage());
        }catch(Exception var6){
            System.err.println("Exception happened when write file: " +  var6.getMessage());
        } finally {
            if (writer != null) {
                try{
                    System.out.println("Closing FileWriter");
                    writer.close();
                }catch (IOException var7){
                    System.err.println("Caught IOException: " +  var7.getMessage());
                }
            }
        }
    }

    public void writeUNITINFO_DWHunit_nums(List<String> unit_numbers, String FileName){

        File outputFile = new File(FileName);
        FileWriter writer = null;
        try {
            writer = new FileWriter(outputFile);
            System.out.println("Writing UNITINFO_DWHunit_nums to " + FileName);

            long start = System.currentTimeMillis();

            String line = "";

            for (String unit_num : unit_numbers)
            {
                writer.write(unit_num );
                writer.write("\n");
            }

            writer.flush();
            long end = System.currentTimeMillis();
            System.out.println(unit_numbers.size() + " records finished in " + (end - start) / 1000f + " seconds");

        }catch(IOException var5) {
            System.err.println("Caught IOException: " +  var5.getMessage());
        }catch(Exception var6){
            System.err.println("Exception happened when write file: " +  var6.getMessage());
        } finally {
            if (writer != null) {
                try{
                    System.out.println("Closing FileWriter");
                    writer.close();
                }catch (IOException var7){
                    System.err.println("Caught IOException: " +  var7.getMessage());
                }
            }
        }
    }


    public Map<String, String> getUnitSummaryWegmans(Map<String, String> commonWegmans ){
        Map<String, String> unitSummaryWegmans = new HashMap<>();

        RestTemplateBuilder builder = new RestTemplateBuilder();

        RestTemplate restTemplate = builder.build();

        String baseUrl = "https://aue1lxsgf001ptl.penske.com:8080/gemfire-api/v1/UTVINSummary/";
        String requestUrl = "";


        for (Map.Entry<String, String> entry : commonWegmans.entrySet())
        {
            if(entry.getValue().isEmpty() || entry.getValue().length() == 0){
                continue;
            }else {
                requestUrl = baseUrl + entry.getValue();

                try{
                    String VinNumber = restTemplate.getForObject(requestUrl,String.class);
                    if(VinNumber.contains("does not exist for region (UTVINSummary) in cache")){
                        continue;
                    }else{
                        unitSummaryWegmans.put(entry.getKey(), entry.getValue());
                        System.out.println(entry.getKey() + "," + entry.getValue());
                    }

                }catch(Exception e){
                    System.out.println("Exception occured while get unitSummaryWegmans" + e.getMessage());
                }
            }
        }

        return unitSummaryWegmans;

    }




    public void getDataFromGPDB(){

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            Properties props = new Properties();
            props.setProperty("password","ConsumerAnalytic#17");
            props.setProperty("user", "bcginsight");

            /*props.setProperty("proxy_type", "4"); // SSL Tunneling
            props.setProperty("proxy_host", "sl-ams-01-guido.stat");
            props.setProperty("proxy_port", "1080");
            props.setProperty("proxy_user", "statica3445");
            props.setProperty("proxy_password", "0963ea201a52e4cc");*/

            /*props.setProperty("proxy_type", "4"); // SSL Tunneling
            props.setProperty("proxy_host", "sl-ams-01-guido.stat");
            props.setProperty("proxy_port", "1080");
            props.setProperty("proxy_user", "statica3445");
            props.setProperty("proxy_password", "0963ea201a52e4cc");*/

            conn = DriverManager.getConnection("jdbc:postgresql://aue1lxsgp001ptl.penske.com:5432/cf_stg", props /*"bcginsight","ConsumerAnalytic#17" */);
          //  conn = DriverManager.getConnection("jdbc:postgresql://stg-gp-elb-373893188.us-east-1.elb.amazonaws.com:5432/cf_stg", props /*"bcginsight","ConsumerAnalytic#17" */);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select NOW()");
            rs.next();
            System.out.println("Data----> " + rs.getString(1));
            rs.close();
            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }




    public void getWegmansFromOmnitracsVinNumbers(String inputFile){

      try {

          Stream<String> lines = Files.lines(Paths.get(inputFile), Charset.forName("Cp1252")/*Charset.defaultCharset()*/);
          System.out.println("Input Flat File Path is ---> " + inputFile);

          RestTemplateBuilder builder = new RestTemplateBuilder();
          RestTemplate restTemplate = builder.build();

          String baseUrl = "https://aue1lxpgf001ptl.penske.com:8080/gemfire-api/v1/OmnitracsVinNumbers/";
          String requestUrl = "";

          for (String line : (Iterable<String>) lines::iterator) {

              String[] fileds = line.split(",");
              requestUrl = baseUrl + fileds[0];

              try {
                  String VinNumber = restTemplate.getForObject(requestUrl, String.class);
                  if (VinNumber.contains("does not exist for region (UTVINSummary) in cache")) {
                      System.out.println("-------------------->" + fileds[0] + "," + fileds[1]);
                  } else {
                      System.out.println("=====>" + fileds[0] + "," + VinNumber);
                      System.out.println(fileds[0] + "," + fileds[1]);
                  }
              }catch (Exception e){
                  System.out.println("-------------------->" + fileds[0] + "," + fileds[1]);
              }


          }
      }catch (Exception e){
          e.printStackTrace();
      }

    }


    public void getOnbordingCustomer(String tspName){

        // setPenskeProxcy();

        RestTemplate restTemplate = createRestTemplate();

        String requestUrl = "https://penske-customer-onboard-dms-stg.cfapps.io/customer/tsp/" + tspName;

        System.out.println("---- before call -----");
       // ResponseEntity<CustomerO[]> responseObject = restTemplate.getForEntity(requestUrl, CustomerO[].class);

        System.out.println("---- call Object-----" );

      ResponseEntity<List<CustomerO>> responseEntity =
                restTemplate.exchange(requestUrl,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<CustomerO>>() {
                        });


        System.out.println("---- After call -----");

        List<CustomerO> customers = responseEntity.getBody();
        for (CustomerO c: customers ) {
            System.out.println(c.toString());
     }
    }


    public void saveOnbordingCustomer(String saveTspName){

        CustomerO customer = new CustomerO();

        /* id=30002, tsp='Geotabgroup', name='gt1',
        username='login@penske.com', password='password1234',
        dbname='penske_leasing', updated_by='null',
        updated_on=0, active_status_id=0, app_status_id=0,
        customer_name='null', customer_number=null,
        entered_by='null', entered_on='null',
        active_status_description='In Active',
        app_status_description='Test'

        '10086', 'TestJoin', '2068', '2068',
        '20pen68', NULL, NULL, '505007855',
        '1497551171166', '1', '0', NULL,
        NULL, NULL, NULL


        */
        customer.setId(10093L);
        customer.setTsp(saveTspName);
        customer.setName("2068");
        customer.setUsername("2068");
        customer.setPassword("20pen68");
        customer.setDbname(null);
        customer.setUpdated_by("505007855");
        customer.setUpdated_on(null);
        customer.setActive_status_id(1);
        customer.setActive_status_description(null);
        customer.setApp_status_id(2);
        customer.setApp_status_description(null);
        customer.setCustomer_name(null);
        customer.setCustomer_number(null);
        customer.setEntered_by(null);
        customer.setEntered_on(null);

        RestTemplate restTemplate = createRestTemplate();
        String requestUrl = "https://penske-customer-onboard-dms-stg.cfapps.io/customer/tsp/" + saveTspName + "/save";

        System.out.println("---- before call -----");
      /*  ResponseEntity<CustomerO> responseEntity =
                restTemplate.exchange(requestUrl,
                        HttpMethod.POST, null, new ParameterizedTypeReference<CustomerO>() {
                        });*/


        HttpEntity<CustomerO> request = new HttpEntity<>(customer);
        ResponseEntity<CustomerO> responseEntity = restTemplate
                .exchange(requestUrl, HttpMethod.POST, request, CustomerO.class);

        System.out.println("---- After call -----");

        System.out.println("The save/update method result: " + responseEntity.getStatusCode()
        + " ----> " + responseEntity.getBody());
    }


    public RestTemplate createRestTemplate(){
        BasicCredentialsProvider credentialsProvider =  new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("4D183BB4-36FA-2398-9744-6964D5F22D44-93F5FADB-00D6-490B-A107-966C0D6EABCD",
                        "5E1A56D7-A3C8-3451-8EF8-FB64A55E19D0-5FB60E36-56D6-4941-A960-70CF29DAABCD"));
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider);
        HttpClient httpClient = httpClientBuilder.build();
        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    public void setPenskeProxcy(){

        Properties props = new Properties(System.getProperties());
        props.put("http.proxyHost","web-proxy.penske.com");
        props.put("http.proxyPort","80");
        props.put("https.proxyHost","web-proxy.penske.com");
        props.put("https.proxyPort","80");
        System.setProperties(props);


    }

    public void getVinDataFromOmnitracsVinNumbersFomGF() throws Exception {

        System.out.println("Get all Vins from Gemfire DB");

        List<String> omnitracs = new ArrayList<>();

        ClientCache cache = new ClientCacheFactory()
                .set("cache-xml-file", "config/clientCache_prod.xml")
                .set("security-properties-file", "gemfire.properties")
                .set("log-level", "error").create();

        QueryService queryService = cache.getQueryService();

        try {
            Collection<?> collection = getAllQueryResult(queryService, "");

            Iterator<?> iter = collection.iterator();

            while(iter.hasNext()) {

                omnitracs.add((String) iter.next());
            }

        }catch (InterruptedException ie) {
            System.out.println("Failed to get data from VIN block queue. The Reason is: " + ie);
        }catch(Exception e){
            System.out.println("Exception occured while get Vin " + e.getMessage());
        }

        cache.close();

        System.out.println(omnitracs);

        File file = new File("C:/Workspace/ZZ_DataService/OmniWegmans_GF_Result.out");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for(String s : omnitracs) {
                writer.write(s + "\n");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Query successfully");
    }


    public Map<String, String> getVinDataFromUNITINFO_DWHGF(List<String> unit_numbers) throws Exception {

        Map<String, String> UNITINFO_DWHNumbers = new HashMap<>();

        ClientCache cache = new ClientCacheFactory()
                .set("cache-xml-file", "config/clientCache.xml")
                .set("security-properties-file", "gemfire.properties")
                .set("log-level", "error").create();


        QueryService queryService = cache.getQueryService();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // String condition = "WEGMANS FOOD MARKETS INC";

        for (String unit_number : unit_numbers) {

            String condition = unit_number;

            try{
                //  Collection<?> collection = getAllQueryResult(queryService);

                Collection<?> collection = getSpecificQueryResult(queryService, condition);

                Iterator<?> iter = collection.iterator();

                while(iter.hasNext()) {

                    PdxInstance pair = (PdxInstance) iter.next();

                    String vinNumber = pair.getField("vin").toString();

                        UNITINFO_DWHNumbers.put(condition, vinNumber);

                        System.out.println(condition + "-->" + vinNumber);

                }

            }catch (InterruptedException ie) {
                System.out.println("Failed to get data from VIN block queue. The Reason is: " + ie);
            }catch(Exception e){
                System.out.println("Exception occured while get customer information" + e.getMessage());
            }

        }

        cache.close();

        System.out.println("Totally  customers");
        return UNITINFO_DWHNumbers;
    }


    public void getDataFromGF() throws Exception {

        ClientCache cache = new ClientCacheFactory()
                .set("cache-xml-file", "config/clientCache.xml") // Staging properties
               // .set("cache-xml-file", "clientCache.xml") // Acceptance properties
                .set("security-properties-file", "gemfire.properties")
                .set("log-level", "error").create();

        QueryService queryService = cache.getQueryService();

        Object[] gfData = getSepcificFieldsFromGF(queryService).toArray();
        System.out.println(gfData.length);
       // File file = new File("C:/Users/505007855/Desktop/------------DOWNLOAD-------------/GFQueryResultFields_PROACTIVE.txt");

       File file = new File("C:/Users/505007855/Desktop/------------DOWNLOAD-------------/GFQueryResultFields_UnitInfo.txt");

      //  File file = new File("C:/Users/505007855/Desktop/------------DOWNLOAD-------------/MissingVINSinQlik.txt");

      //  File file = new File("/var/tmp/GFQueryResultFields_UnitInfo.txt");
        StringBuilder sb = new StringBuilder();

        // ----------- fetch all data --------------

       for(Object gData : gfData)
        {
            System.out.println(gData);
            sb.append(gData + "\n");
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(sb.toString().replace("struct(", "").replace(")", "").replace("value:PDX[31,__GEMFIRE_JSON]{", "").replace("}", ""));
        }

      // ---------------------------------

      // ----------- Fetch Different time data -----------


     /*   sb.append(gfData[0] + "\n");
        for(int i = 1; i < gfData.length; i++){

            int index = i;
            int previous_index = i-1;

            String[] fields = gfData[index].toString().replace("struct(", "").replace(")", "").split(",");
            String[] fields_before = gfData[previous_index].toString().replace("struct(", "").replace(")", "").split(",");

            String current_event = fields[4].replace("event_datetime:", "").replace("Z", "");
            current_event = current_event.split("\\.")[0];
            String previous_event = fields_before[4].replace("event_datetime:", "").replace("Z", "");

            previous_event = previous_event.split("\\.")[0];

            Long difference = null;

            Long min = null;

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            difference = (format.parse(current_event).getTime() - format.parse(previous_event).getTime());

            min = (Math.abs(difference) / 1000 / 60);

            System.out.println(gfData[i] + ", Different:" + min );

            String previous_id = "";
            String current_id = "";

            if( min <= 15){

                previous_id = gfData[previous_index].toString().split(",")[5].split("-T")[0];
                current_id = gfData[index].toString().split(",")[5].split("-T")[0];

                System.out.println("previous_id is: " + previous_id + " --- Current id is" + current_id);

                if(previous_id.equalsIgnoreCase(current_id)) {
                    sb.append(gfData[previous_index] + ", Different:" + min + "\n");
                    sb.append(gfData[index] + ", Different:" + min + "\n");
                }
            }

        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(sb.toString().replace("struct(", "").replace(")", "").replace("value:PDX[31,__GEMFIRE_JSON]{", "").replace("}", ""));
        }

        */

        // ---------------

    }



    public GFDTO getVinDataFromGF() throws Exception {

        GFDTO gfdto = new GFDTO();

        ClientCache cache = new ClientCacheFactory()
                .set("cache-xml-file", "config/clientCache.xml") // Staging properties
               // .set("cache-xml-file", "clientCache.xml") // Acceptance properties
                .set("security-properties-file", "gemfire.properties")
                .set("log-level", "error").create();


        final Region<String, PdxInstance> UTRegion = cache.getRegion("UnitTelemetry"/*"UTVINSummary"*/);

        Collection map = new HashSet();
        System.out.println("---------The Region size is: "
                + UTRegion.size() + " Full path: " + UTRegion.getFullPath()
                + " " + UTRegion.values()
                + " Cache name is: " + cache.getName()
        );


        QueryService queryService = cache.getQueryService();

        String[] conditions = {

                "1FUJGLD17BLBA7267",
                "1FUJGLD17BLBA7267",
                "3AKJGEDV7FSGB7269",
                "3AKJGEDV9JSJH2216",
                "3AKJGEDV0JSJH2217",
                "3AKJGEDV1JSJH2212",
                "3AKJGEDV4JSJH2222",
                "3AKJGEDV7JSJH2215",
                "3HSDJAPR4EN778008",
                "3AKJGEDVXJSJH2211",
                "3AKJGEDV2JSJH2221",
                "1FUJGBDV8JLJT1249",
                "1FUJGEDR6CSBM8986",
                "1FUJGEDV6BSAZ1135",
                "1FUJGEDV8CSBM1017",
                "1FUJGLD10DLFA8274",
                "1FUJGLD12DLFA8275",
                "1FUJGLD13DLFA8270",
                "1FUJGLD15DLFA8271",
                "1FUJGLD17DLFA8269",
                "1FUJGLD17DLFA8272",
                "1FUJGLD19DLFA8273",
                "1HSHXAHR27J375097",
                "1HTWYAHT27J439702",
                "3AKJGBDV4JDJU9241",
                "3AKJGBDV9JSJS4797",
                "3AKJGED55FSGB7044",
                "3AKJGEDV5GSHS7128",
                "3HSDJSJR3DN155136",
                "4V4MC9GH77N478829",
                "3HSDJSJR3DN303785",
                "3HSDJSJR0DN303792",
                "3HSDJSJR1DN155099",
                "3HSDJSJR5DN303769",
                "3HSDJSJR8DN303765",
                "3HSDJSJR0DN303789",
                "3HSDJSJR2DN303762",
                "3HSDJSJR3DN303771",
                "3HSDJSJR6EN498492",
                "3HSDJSJR9DN155139",
                "3HSDJSJR9DN148448",
                "3HSDJSJR6DN155132",
                "3HSDJSJRXDN155134",
                "3HSDJSJR4DN148454",
                "3HSDJSJR8DN148456",
                "3HSDJSJR9DN303791",
                "3HSDJSJR7DN303787",
                "3HSDJSJR1DN303784",
                "3HSDJSJR1DN148458"

               // "4V4N39EG3GN951835",
               /* "1S12E9488BE524544",
                 "1M1AP01Y97N001259",
                "1FUBGBDV6FLGH3332",
                "1FVACWDT0HHHU2566",
                "1FUBGBDV5HLHU9680",
                "1DW1A5327CS331107"*/
               // "2017-04-25%"
              /*
                "1FUJGEDR0CLBN6236",
                "1XKAD49X7DJ360676",
                "1XKAD49X9DJ360677",
                "1XKAD49X0DJ360678",
                "1XKAD49X5DJ360689",
                "1XKAD49X1DJ360690"
               */
        };

        StringBuilder sb = new StringBuilder();

        for (String condition: conditions) {

          try {
             //  Collection<?> collection = getAllQueryResult(queryService, condition);

               Collection<?> collection = getSpecificQueryResult(queryService, condition);

                Iterator<?> iter = collection.iterator();


                while (iter.hasNext()) {

                    PdxInstance pair = (PdxInstance) iter.next();

                    List<String> fields = pair.getFieldNames();

                    System.out.println(fields);


                    // Get time difference between event_datetime and read_datetime
                    /*
                    String event_time = "";
                    Date event_datetime = null;
                    String read_time = "";
                    Date read_datetime = null;
                    Long difference = null;

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                   // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");

                    */

                    for (int i = 0; i < fields.size(); i++) {

                        String fieldName = fields.get(i);

                   /*
                        if(fieldName.equalsIgnoreCase("event_datetime")){
                            event_time = pair.getField(fieldName).toString()*//*.replace("Z", "")*//*;
                            event_datetime = format.parse(event_time);
                        }

                        if(fieldName.equalsIgnoreCase("read_datetime")){
                            read_time = pair.getField(fieldName).toString()*//*.replace("Z", "")*//*;
                            read_datetime = format1.parse(read_time);
                        }

                        */

                        sb.append(pair.getField(fieldName).toString().replaceAll("\\n", " ").replaceAll("\\r","") + "\t");

                        System.out.print(pair.getField(fieldName).toString().replaceAll("\\n", " ").replaceAll("\\r","") + "\t");

                    }

                  /*
                    difference =  (read_datetime.getTime()-event_datetime.getTime())/1000;

                    System.out.print(difference);

                    sb.append(difference +" Second" + "\t" + difference/60 + " min");
                  */

                    sb.append("\n");
                  //  System.out.println();
                }


            } catch (InterruptedException ie) {
                System.out.println("Failed to get data from VIN block queue. The Reason is: " + ie);
            } catch (Exception e) {
                System.out.println("Exception occured while get customer information" + e.getMessage());
            }

        }

        cache.close();

        File file = new File("C:/Users/505007855/Desktop/------------DOWNLOAD-------------/GFQueryResult.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(sb.toString());
        }
        System.out.println("Query successfully");
        return gfdto;
    }



    public Collection<?> getAllQueryResult(QueryService queryService, String condition) throws Exception {

      //  Query query = queryService.newQuery("select * from /UnitInfo limit 1"); // limit 10

      //  Query query = queryService.newQuery("select * from /UnitTelemetry limit 1");

      //  Query query = queryService.newQuery("select * from /UnitTelemetry where tsp_provider LIKE '%omnitracs%' AND capture_datetime LIKE '2017-04-04T14%' limit 10");

      //  Query query = queryService.newQuery("select distinct * from /UnitTelemetry where tsp_provider = 'randmcnally' order by capture_datetime desc limit 10");

      //  Query query = queryService.newQuery("select * from /UnitTelemetry limit 10");
      //  Query query = queryService.newQuery("select * from /UnitTelemetry limit 10");

      // Query query = queryService.newQuery("select * from /OmnitracsVinNumbers limit 15");

        //  Query query = queryService.newQuery("select distinct * from /UnitTelemetry where tsp_provider = 'cyntrx' AND capture_datetime LIKE '" + condition + "' order by capture_datetime desc limit 10");

        //  Query query = queryService.newQuery("select distinct * from /UnitFaultCodeHistory where tsp_provider = 'volvo' order by read_datetime desc limit 1000");

       //  Query query = queryService.newQuery("select distinct * from /UnitTelemetry where tsp_provider = 'peoplenet' order by read_datetime desc limit 20");

        /*vin,spn,fmi,event_datetime,id,Proactive_read_datetime*/
       // Query query = queryService.newQuery("select distinct * from /FCProactive order by vin,spn,fmi,event_datetime desc limit 10");

       // Query query = queryService.newQuery("select distinct * from /UnitFaultCodeHistory where tsp_provider = 'zonar' order by read_datetime desc limit 20");
      //  Query query = queryService.newQuery("select distinct * from /UnitTelemetry where tsp_provider = 'zonar' order by read_datetime desc limit 20");

        // 'peoplenet' , 'omnitracs', 'cyntrx', 'geotab', 'telogis', 'volvo', 'xrs', 'zonar'

       // Query query = queryService.newQuery("select engine_model_year from /UnitInfo");

        Query query = queryService.newQuery("select distinct * from /UnitTelemetry where vin = '4V4N39EG3GN951835' order by read_datetime desc");



        Object result = query.execute();

        return ((SelectResults<?>) result).asList();
    }

    public Collection<?> getSpecificQueryResult(QueryService queryService, String condition) throws Exception{

     //   Query query = queryService.newQuery("select * from /UnitInfo where vin='"+ condition +"'");

    //   Query query = queryService.newQuery("select * from /UnitInfo where customer_name='" + condition +"' limit 10");

     //  Query query = queryService.newQuery("select * from /UnitInfo where unit_number='" + condition +"' limit 10");

      //  Query query = queryService.newQuery("select * from /UnitTelemetry where vin='" + condition + "' AND capture_datetime LIKE '2017-03-29%'"); //
      //  Query query = queryService.newQuery("select * from /UnitTelemetry where capture_datetime LIKE '2017-04-03%' limit 10"); //

     //  Query query = queryService.newQuery("select * from /UnitTelemetry where vin='" + condition + "' limit 1");

     //   Query query = queryService.newQuery("select * from /UnitTelemetry where vin='" + condition + "' ");

    //  Query query = queryService.newQuery("select distinct * from /UnitTelemetry where tsp_provider = 'randmcnally' AND vin='" + condition + "' order by capture_datetime desc limit 10");

     //   Query query = queryService.newQuery("select distinct * from /UnitTelemetry where tsp_provider = 'peoplenetrestapi' order by capture_datetime desc limit 10");
     //   Query query = queryService.newQuery("select distinct * from /UnitFaultCodeHistory where tsp_provider = 'randmcnally' AND vin='" + condition + "' order by capture_datetime desc limit 10");

        //   Query query = queryService.newQuery("select * from /UnitTelemetry where unit_number='" + condition + "' limit 1");

     //   Query query = queryService.newQuery("select * from /UnitFaultCodeHistory where tsp_provider = 'omnitracs' AND" + /*vin='" + condition + "' AND*/  " capture_datetime LIKE '2017-03-17%' limit 10");

        //   Query query = queryService.newQuery("select * from /OmnitracsVinNumbers where unit_number='" + condition +"' limit 10");

      //  Query query = queryService.newQuery("SELECT * FROM /OmnitracsVinNumbers.entrySet");

      //  Query query = queryService.newQuery("select * from /UnitInfo where customer_name='WEGMANS FOOD MARKETS INC' and vehicle_use='LEASE'");

      //  Query query = queryService.newQuery("select * from /UnitTelemetry where tsp_provider='zonar' AND read_datetime LIKE '2017-06-01T18:2%' AND capture_datetime LIKE '2017-06-01T18:2%'");

      //  Query query = queryService.newQuery("select * from /UnitTelemetry where vin='" + condition + "' limit 10");

      //  Query query = queryService.newQuery("select * from /UnitVINMapping where vin='" + condition + "' limit 10");

        Query query = queryService.newQuery("select * from /UnitInfo where vin='" + condition + "'");

       // 'peoplenet' , 'omnitracs', 'cyntrx', 'geotab', 'telogis', 'volvo', 'xrs', 'zonar'


        Object result = null;
        try {
            result = query.execute();
        } catch (Exception e) {
            System.out.println("Failed to query key and values for VIN: " + condition + ". The Reason is: " + e);
        }

        return  ((SelectResults<?>) result).asList();
    }

    public Collection<?> getSepcificFieldsFromGF(QueryService queryService){

        // Query query = queryService.newQuery("select distinct vin,spn,fmi,event_datetime,id,Proactive_read_datetime from /FCProactive order by Proactive_read_datetime, vin, spn, fmi, event_datetime desc");

       // Query query = queryService.newQuery("select distinct vin,spn,fmi,event_datetime,id,Proactive_read_datetime from /FCProactive order by vin, spn, fmi, event_datetime, Proactive_read_datetime desc");

       // Query query = queryService.newQuery("select distinct vin,spn,fmi,Proactive_read_datetime,event_datetime,id from /FCProactive order by vin, spn, fmi, event_datetime desc");

      //   Query query = queryService.newQuery("SELECT e.key, e.value FROM /FCProactive.entrySet e"); // ORDER BY e.key.ID desc, e.key.pkid desc

      //  Query query = queryService.newQuery("select distinct * from /UnitFaultCodeHistory where tsp_provider = 'volvo' order by read_datetime desc limit 10");

      //  Query query = queryService.newQuery("SELECT e.key, e.value FROM /XRSVINSidMapping.entrySet e"); // ORDER BY e.key.ID desc, e.key.pkid desc

        Query query = queryService.newQuery("select engine_model_year from /UnitInfo limit 100");

        Object result = null;
        try {
            result = query.execute();
        } catch (Exception e) {
            System.out.println("Failed to query key and values for VIN:  The Reason is: " + e);
        }

        return  ((SelectResults<?>) result).asList();
    }
}
