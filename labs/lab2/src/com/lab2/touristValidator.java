package com.lab2;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class touristValidator {

    public static boolean validationSuccessful() {
        Source schemaFile = new StreamSource(new File("/home/stasey/IdeaProjects/3 year/lab2/src/com/lab2/tourXSD.xml"));
        Source xmlFile = new StreamSource(new File("/home/stasey/IdeaProjects/3 year/lab2/src/com/lab2/tour.xml"));
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            System.out.println(xmlFile.getSystemId() + " is valid");
            return true;
        } catch (SAXException | IOException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
            return false;
        }
    }

    public static void main(String args[]){
        touristValidator.validationSuccessful();
    }
}
