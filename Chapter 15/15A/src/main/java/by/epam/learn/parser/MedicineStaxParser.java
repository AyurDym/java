package main.java.by.epam.learn.parser;

import main.java.by.epam.learn.entity.*;
import javax.xml.stream.*;
import java.io.FileInputStream;
import java.util.*;

public class MedicineStaxParser {

    public static List<Medicine> parse(String filePath) throws Exception {
        List<Medicine> medicines = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(filePath));

        Medicine medicine = null;
        Version version = null;
        Certificate certificate = null;
        MedPackage medPackage = null;
        Dosage dosage = null;
        String currentElement = null;

        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                currentElement = reader.getLocalName();
                switch (currentElement) {
                    case "medicine":
                        medicine = new Medicine();
                        medicine.setId(reader.getAttributeValue(null, "id"));
                        break;
                    case "version":
                        version = new Version();
                        version.setType(reader.getAttributeValue(null, "type"));
                        break;
                    case "certificate":
                        certificate = new Certificate();
                        break;
                    case "package":
                        medPackage = new MedPackage();
                        break;
                    case "dosage":
                        dosage = new Dosage();
                        break;
                }
            } else if (event == XMLStreamConstants.CHARACTERS) {
                String text = reader.getText().trim();
                if (!text.isEmpty() && currentElement != null) {
                    switch (currentElement) {
                        case "name": if (medicine != null) medicine.setName(text); break;
                        case "pharm": if (medicine != null) medicine.setPharm(text); break;
                        case "group": if (medicine != null) medicine.setGroup(text); break;
                        case "analogs": if (medicine != null) medicine.addAnalog(text); break;
                        case "number": if (certificate != null) certificate.setNumber(text); break;
                        case "issueDate": if (certificate != null) certificate.setIssueDate(text); break;
                        case "expiryDate": if (certificate != null) certificate.setExpiryDate(text); break;
                        case "registrar": if (certificate != null) certificate.setRegistrar(text); break;
                        case "type": if (medPackage != null) medPackage.setType(text); break;
                        case "quantity": if (medPackage != null) medPackage.setQuantity(Integer.parseInt(text)); break;
                        case "price": if (medPackage != null) medPackage.setPrice(Double.parseDouble(text)); break;
                        case "amount": if (dosage != null) dosage.setAmount(Double.parseDouble(text)); break;
                        case "unit": if (dosage != null) dosage.setUnit(text); break;
                        case "frequency": if (dosage != null) dosage.setFrequency(text); break;
                    }
                }
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                String end = reader.getLocalName();
                switch (end) {
                    case "version":
                        if (medicine != null && version != null) {
                            medicine.addVersion(version);
                        }
                        break;
                    case "certificate":
                        if (version != null) version.setCertificate(certificate);
                        break;
                    case "package":
                        if (version != null) version.setPkg(medPackage);
                        break;
                    case "dosage":
                        if (version != null) version.setDosage(dosage);
                        break;
                    case "medicine":
                        if (medicine != null) medicines.add(medicine);
                        break;
                }
            }
        }
        reader.close();
        return medicines;
    }
}