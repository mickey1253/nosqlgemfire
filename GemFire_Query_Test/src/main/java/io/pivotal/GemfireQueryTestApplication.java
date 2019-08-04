package io.pivotal;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.query.Query;
import com.gemstone.gemfire.cache.query.QueryService;
import com.gemstone.gemfire.cache.query.SelectResults;
import com.gemstone.gemfire.pdx.PdxInstance;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.joda.time.DateTime;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by 505007855 on 3/8/2017.
 */

@SpringBootApplication
public class GemfireQueryTestApplication<K,V> implements CommandLineRunner {

    @Autowired
    GFDataRepository gfDataRepository;

   // @Autowired
  //  Java8Repository java8Repository;

    @Autowired
    RestCallRepository restCallRepository;

    @Autowired
    XMLConvertRepository xmlConvertRepository;

    public static void main(String[] args) {

      //  setupAuthenticator();
        SpringApplication.run(GemfireQueryTestApplication.class, args);

    }


    @Override
    public void run(String... strings) throws Exception {

      //  List<String> unit_numbers = getOmnitracsUnitNumber();

     //   List<String> unit_numbers = getUNITINFOUnitNumber();

      //  List<String> unit_numbers_UNITINFO = getUNITINFOUnitNumber();

      /*  String UNITINFO_DWHOutput = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/OminiTracs_Unit_Numbers.csv";

        gfDataRepository.writeUNITINFO_DWHunit_nums(unit_numbers, UNITINFO_DWHOutput);*/

     /*   Map<String, String> OmnitracsVinNumbers = gfDataRepository.getVinDataFromOmnitracsVinNumbersGF(unit_numbers);

        String OmnVinOutput = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/zzz_OmniWegmans.csv";
        gfDataRepository.writeOmnitracsVinNumbers(OmnitracsVinNumbers, OmnVinOutput);*/



       /* Map<String, String> UNITINFO_DWHNumbers = gfDataRepository.getVinDataFromUNITINFO_DWHGF(unit_numbers);

        String UNITINFO_DWHWegmansOutput = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/UNITINFO_DWHWegmans.csv";

        gfDataRepository.writeOmnitracsVinNumbers(UNITINFO_DWHNumbers, UNITINFO_DWHWegmansOutput);*/


     //  Map<String,String> commonWegmans= getCommonWegmans();

     //  writeDifferentUnitVins(commonWegmans);

     //  Map<String, String> UTVINSummaryWegmans = gfDataRepository.getUnitSummaryWegmans(commonWegmans);


     //   gfDataRepository.getValidFileds("C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Original_Wegmans_Unit_Vins_Mapping.csv");

      //  gfDataRepository.getWegmansFromOmnitracsVinNumbers("C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Original_Valid_Wegmans_Unit_Vins_Mapping.csv");
     //   getDiffUnitNumUNITandCFLEET();


    //  convertXmlFileForUnitNum();

     //   gfDataRepository.getDataFromGPDB();

     //   testCurrentTime();



   // String tspName = "Geotabgroup";
   // gfDataRepository.getOnbordingCustomer(tspName);

  // String saveTspName = "TestJoin";
  // gfDataRepository.saveOnbordingCustomer(saveTspName);


  /* java8Repository.helloworld();
   java8Repository.lambdaTest();
   java8Repository.referenceMethod();
   java8Repository.newFunctionTest();
   java8Repository.defultMethod();
   java8Repository.streamTest();*/

  //java8Repository.optionalTest();

 // restCallRepository.getVehiclesInfo("503588|PenskeWebAPI", "TdE3Gs9k8T");

  //  xmlConvertRepository.extractSidAndVin();

        testJodetime();

        // select *
   GFDTO gfdto = gfDataRepository.getVinDataFromGF();

  // select fields
  // gfDataRepository.getDataFromGF();

    }

    private void testJodetime(){


    //1508223721000-1508356200000.
    //    Date is: 2017-10-18T15:57:07.697-04:00
    //    Date is: 2017-10-18T15:58:29.816-04:00
        String strLastExeDateTime = new DateTime(/*1516286502796*/ 1514389734339L).toString();

        System.out.println(strLastExeDateTime);

      /*  DateTimeFormatter xrsFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

        Long now = System.currentTimeMillis();

        System.out.println(now);*/


        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Calendar cal2 = Calendar.getInstance();
        System.out.println("Time zone id is:"+cal.getTimeZone().getID()+";time in millisec:"+cal.getTimeInMillis());
        System.out.println("Time zone id is:"+cal2.getTimeZone().getID()+";time in millisec:"+cal2.getTimeInMillis());
        System.out.println("Time is: " + System.currentTimeMillis());
        DateTime date=new DateTime(System.currentTimeMillis());
        System.out.println("Date is: " + date);


        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateTimeFormatter formatter1 = DateTimeFormat.forPattern("yyy-MM-dd'T'HH:mm:ss'Z'");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateFormat format1 = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss'Z'");

        String pattern1 = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]+Z";
        String pattern2 = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}Z";
        Pattern r = Pattern.compile(pattern1);
        Pattern r1 = Pattern.compile(pattern2);

       // String s = "2017-10-06T16:12:22.307Z";
        String s = "2017-10-06T16:12:22.21657Z";

        Matcher matcher = r1.matcher(s);

        System.out.println("Matcher: " + matcher.find());


        // DateTimeFormatter formatter1 =
        // DateTimeFormat.forPattern("yyy-MM-dd'T'HH:mm:ss'Z'");
        // "2017-10-06T16:12:22.307Z"
        DateTime dt_GF = null;
       // dt_GF = formatter.parseDateTime(gf_event_datetime);
        System.out.println("dt_GF = " + s.split("\\.")[0].replace("Z", "").concat("Z"));
        dt_GF = formatter1.parseDateTime(s.split("\\.")[0].replace("Z", "").concat("Z"));
        System.out.println(dt_GF);
        System.out.println(dt_GF.getMinuteOfDay());

    }

    private void testCurrentTime(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = /*new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")*/  new SimpleDateFormat();
        String formatted = "_" + format1.format(cal.getTime());

        System.out.println("Start to create Excel File ......" + formatted);
     //   System.out.println("Time is: " + DateFormat.getTimeInstance(DateFormat.LONG).format(new Date()));

        System.out.println( new Date(System.currentTimeMillis()/1000));

        try {
            // 2017-04-05T00:17:46.384Z
            long epoch = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("1970-01-01 00:00:00").getTime() / 1000;
            System.out.println("----->" + epoch);
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    private List<String> getOmnitracsUnitNumber(){

       // String filePath = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/UNITINFO_DWH.txt";
        String filePath = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/R_UNIT_CFLEET.csv";
        List<String> unit_numbers = gfDataRepository.readOmnitracsFlatFile(filePath);

        System.out.println("The size of Vin_Number List is: "
                + unit_numbers.size());

        return unit_numbers;
    }

    private List<String> getUNITINFOUnitNumber(){

        String filePath = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/UNITINFO_DWH.txt";
        List<String> unit_numbers = gfDataRepository.readUNITINFO_DWHFlatFile(filePath);

        System.out.println("The size of Vin_Number List is: "
                + unit_numbers.size());

        return unit_numbers;
    }



    private Map<String, String> getCommonWegmans(){

        Map<String, String> commonWegmans = new HashMap<>();

        // String ominitracsRegionFile = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Wegmans_Pair_From_UNITINFO_DWH_OmnitracsVinNumbers_Region.csv";

      //  String ominitracsRegionFile = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Wegmans_Pair_In_UTVINSummary_Region.csv";

      //  String UnitInfoRegionFile = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Wegmans_Pair_From_UNITINFO_DWH_UnitInfo_Region.csv";

    //  String ominitracsRegionFile = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/R_UNIT_CFLEET.csv";

        /*String ominitracsRegionFile = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Wegmans_unit_Vin_Mapping.csv";
        String UnitInfoRegionFile = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/UNITINFO_DWH.txt";
*/

        String ominitracsRegionFile1 = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/ZZZ_Valid_Omnitracs_New_Wegmens1.csv";
        String UnitInfoRegionFile1 = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/ZZZ_OmniWegmans_In_both_excel_and_OmnitracsNumRegion1.csv";

        commonWegmans = gfDataRepository.getCommonWegmansVins1(ominitracsRegionFile1, UnitInfoRegionFile1);

        return commonWegmans;

    }


    private void convertXmlFileForUnitNum(){

        List<String> equipmentIds = new ArrayList<>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File("C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Omnitracs-2017-04-04T17_56_51Z"));
            NodeList nodeList = document.getElementsByTagName("equipment");
            for (int x = 0, size = nodeList.getLength(); x < size; x++) {
                String equipmentId = nodeList.item(x).getAttributes().getNamedItem("ID").getNodeValue();
                equipmentIds.add(equipmentId);
                System.out.println(equipmentId);
            }
        }catch (Exception e){

        }

        writeLatestUnitNumVins(equipmentIds);
    }


    private void writeLatestUnitNumVins(List<String> equipmentIds){

        Map<String, String> unit_numbers = new HashMap<>();

        Map<String, String> OutputEquipmentIds = new HashMap<>();

       // String inputfileName = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Z_Unit_Numbers_Vins_From_UNITINFO_DWH.csv";
     //  String inputfileName = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/UNITINFO_DWH.txt";
     //  String inputfileName = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Wegmans_Pair_different_In_UTVINSummary_And_UnitInfo.csv";
      //  String inputfileName = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Wegmans_Pair_In_UTVINSummary_Region.csv";
       String inputfileName = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Z_Z_Different_from_CFLEET_AND_UNITINFO.csv";


        String outputFileName = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Z_Latest_Omnitracs_Equipment.csv";

        try (Stream<String> lines = Files.lines(Paths.get(inputfileName), Charset.defaultCharset())) {
            System.out.println("UNITINFO_DWH Flat File Path is ---> " + inputfileName);

           /* for (String line : (Iterable<String>) lines::iterator) {

                //   System.out.println(line);
                 String[] fileds = line.split("\\|");
               // String[] fileds = line.split(",");

                if (fileds.length > 9) {
                    unit_numbers.put(fileds[1], fileds[9]);
                }
            }*/

            for (String line : (Iterable<String>) lines::iterator) {

                //   System.out.println(line);
                // String[] fileds = line.split("\\|");
                String[] fileds = line.split(",");

                    unit_numbers.put(fileds[0], fileds[1]);
            }

            for (String equipmentId : equipmentIds) {

                String VinNum = unit_numbers.get(equipmentId);

                if (!(VinNum == null)) {
                    OutputEquipmentIds.put(equipmentId, VinNum);
                }
            }


            File outputFile = new File(outputFileName);
            FileWriter writer = null;
            writer = new FileWriter(outputFile);
            System.out.println("Writing OmnitracsVinNumbers to " + outputFileName);

            long start = System.currentTimeMillis();

            for (Map.Entry<String, String> entry : OutputEquipmentIds.entrySet()) {
                    writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
            writer.flush();
                long end = System.currentTimeMillis();
                System.out.println(OutputEquipmentIds.size() + " records finished in " + (end - start) / 1000f + " seconds");


            } catch (FileNotFoundException var1) {
                System.out.println("Source File doesn't exist: " + var1);
            } catch (Exception var2) {
                System.out.println("Error occurs when read flat file ------>: " + var2);
            }


        }


        private void writeDifferentUnitVins(Map<String, String> commonWegmans){

            String outputFileName = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/Z_Z_Z_Z.csv";

            try  {

                File outputFile = new File(outputFileName);
                FileWriter writer = null;
                writer = new FileWriter(outputFile);
                System.out.println("Writing DifferentVinNumbers to " + outputFileName);

                long start = System.currentTimeMillis();

                for (Map.Entry<String, String> entry : commonWegmans.entrySet()) {
                    writer.write(entry.getKey() + "," + entry.getValue() + "\n");
                }
                writer.flush();
                long end = System.currentTimeMillis();
                System.out.println(commonWegmans.size() + " records finished in " + (end - start) / 1000f + " seconds");


            } catch (FileNotFoundException var1) {
                System.out.println("Source File doesn't exist: " + var1);
            } catch (Exception var2) {
                System.out.println("Error occurs when read flat file ------>: " + var2);
            }

        }


        private void getDiffUnitNumUNITandCFLEET(){

            String inputCFLEET = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/R_UNIT_CFLEET.csv";

            String inputUNIT = "C:/Users/505007855/Task_Backup/Task9_OmnitracsVins/UNITINFO_DWH.txt";



        }


    public static void setupAuthenticator() {
        System.setProperty("socksProxyHost", "true");
        System.setProperty("java.net.socks.username", "user");
        System.setProperty("java.net.socks.password", "password");
        System.setProperty("socksProxyHost", "abcdefg");
        System.setProperty("socksProxyPort", "9493");

        if(System.getProperties().containsKey("socksProxyHost")) {
            final String username = System.getProperty("java.net.socks.username");
            final String password = System.getProperty("java.net.socks.password");

            if(username != null && password != null) {
                Authenticator.setDefault(new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new
                                PasswordAuthentication(username, password.toCharArray());
                    }});
            }

        }
    }

}
