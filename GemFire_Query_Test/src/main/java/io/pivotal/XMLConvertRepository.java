package io.pivotal;

import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by 505007855 on 7/12/2017.
 */

@Repository
public class XMLConvertRepository {

    public void extractSidAndVin(){

        String vehicleInfo = "<Vehicle xmlns=\"http://schemas.datacontract.org/2004/07/Xata.Ignition.WebServiceAPI.Contracts.DataContract.Entities\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"><AllowTrailerInspection>true</AllowTrailerInspection><Auxiliary>None</Auxiliary><CompanyName>Penske Logistics LLC</CompanyName><CompanySID>1687</CompanySID><Country>USA</Country><FuelDrawCapacity>0</FuelDrawCapacity><GrossVehicleWeight>0</GrossVehicleWeight><HP>0</HP><HUT>false</HUT><HasBerth>true</HasBerth><HasElectronicEngine>true</HasElectronicEngine><HosExempt>false</HosExempt><IFTA>true</IFTA><InstallDate>2017-05-06T00:00:00</InstallDate><LicensePlate>R145398</LicensePlate><Manufacture></Manufacture><ManufactureDate>1900-01-01T00:00:00</ManufactureDate><Model></Model><ModifiedBy>6588</ModifiedBy><ModifiedDate>2017-06-24T03:25:31.769645</ModifiedDate><OBCType>XRSRelay</OBCType><Odometer>0</Odometer><OdometerDate>2017-05-02T05:00:00</OdometerDate><OrganizationID>511300</OrganizationID><OrganizationName>Dormae-Lockhart (511300)</OrganizationName><OrganizationSID>276</OrganizationSID><OwnerOperator>false</OwnerOperator><PowerAxles>3</PowerAxles><SID>3873</SID><StateProvince>Texas</StateProvince><Status>Active</Status><StraightTruck>false</StraightTruck><TGTNumber>380421</TGTNumber><TransmissionMfg></TransmissionMfg><TransmissionType></TransmissionType><Type>Tractor</Type><UserDefinedField1></UserDefinedField1><UserDefinedField2></UserDefinedField2><UserDefinedField3></UserDefinedField3><UserDefinedField4></UserDefinedField4><UserDefinedField5></UserDefinedField5><VIN>1FUJGLDR6CSBS4692</VIN><VehicleName>635152</VehicleName><Year></Year></Vehicle>";
        // vehicles.add(vehicle);

        String sid = "";
        String VIN = "";

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse( new InputSource(new StringReader(vehicleInfo)));
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Vehicle");
            System.out.println(nList.getLength());

            NodeList nsid = doc.getElementsByTagName("SID");

            System.out.println("SID is: " + nsid.item(0).getTextContent());

            NodeList nVid = doc.getElementsByTagName("VIN");

            System.out.println("VIN is: " + nVid.item(0).getTextContent());

        }catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
