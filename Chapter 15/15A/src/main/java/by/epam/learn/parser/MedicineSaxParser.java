package main.java.by.epam.learn.parser;

import main.java.by.epam.learn.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

public class MedicineSaxParser {

    public static List<Medicine> parse(String filePath) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        MedicineHandler handler = new MedicineHandler();
        parser.parse(filePath, handler);
        return handler.getMedicines();
    }

    static class MedicineHandler extends DefaultHandler {
        private List<Medicine> medicines = new ArrayList<>();
        private Medicine currentMedicine;
        private Version currentVersion;
        private Certificate currentCertificate;
        private MedPackage currentPackage;
        private Dosage currentDosage;
        private StringBuilder content = new StringBuilder();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attrs) {
            content.setLength(0);
            switch (qName) {
                case "medicine":
                    currentMedicine = new Medicine();
                    currentMedicine.setId(attrs.getValue("id"));
                    break;
                case "version":
                    currentVersion = new Version();
                    currentVersion.setType(attrs.getValue("type"));
                    break;
                case "certificate":
                    currentCertificate = new Certificate();
                    break;
                case "package":
                    currentPackage = new MedPackage();
                    break;
                case "dosage":
                    currentDosage = new Dosage();
                    break;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            content.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            String value = content.toString().trim();
            switch (qName) {
                case "name": currentMedicine.setName(value); break;
                case "pharm": currentMedicine.setPharm(value); break;
                case "group": currentMedicine.setGroup(value); break;
                case "analogs": currentMedicine.addAnalog(value); break;
                case "number": currentCertificate.setNumber(value); break;
                case "issueDate": currentCertificate.setIssueDate(value); break;
                case "expiryDate": currentCertificate.setExpiryDate(value); break;
                case "registrar": currentCertificate.setRegistrar(value); break;
                case "type": if (currentPackage != null) currentPackage.setType(value); break;
                case "quantity": if (currentPackage != null) currentPackage.setQuantity(Integer.parseInt(value)); break;
                case "price": if (currentPackage != null) currentPackage.setPrice(Double.parseDouble(value)); break;
                case "amount": currentDosage.setAmount(Double.parseDouble(value)); break;
                case "unit": currentDosage.setUnit(value); break;
                case "frequency": currentDosage.setFrequency(value); break;
                case "dosage": currentVersion.setDosage(currentDosage); break;
                case "package": currentVersion.setPkg(currentPackage); break;
                case "certificate": currentVersion.setCertificate(currentCertificate); break;
                case "version": currentMedicine.addVersion(currentVersion); break;
                case "medicine": medicines.add(currentMedicine); break;
            }
        }

        public List<Medicine> getMedicines() { return medicines; }
    }
}