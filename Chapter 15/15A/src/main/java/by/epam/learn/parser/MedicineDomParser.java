package main.java.by.epam.learn.parser;

import main.java.by.epam.learn.entity.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.*;

public class MedicineDomParser {

    public static List<Medicine> parse(String filePath) throws Exception {
        List<Medicine> medicines = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(filePath);

        NodeList medicineNodes = doc.getElementsByTagName("medicine");
        for (int i = 0; i < medicineNodes.getLength(); i++) {
            Element elem = (Element) medicineNodes.item(i);
            Medicine m = new Medicine();
            m.setId(elem.getAttribute("id"));
            m.setName(getTagValue("name", elem));
            m.setPharm(getTagValue("pharm", elem));
            m.setGroup(getTagValue("group", elem));

            NodeList analogs = elem.getElementsByTagName("analogs");
            for (int j = 0; j < analogs.getLength(); j++) {
                m.addAnalog(analogs.item(j).getTextContent());
            }

            NodeList versionNodes = elem.getElementsByTagName("version");
            for (int j = 0; j < versionNodes.getLength(); j++) {
                Element vElem = (Element) versionNodes.item(j);
                Version v = new Version();
                v.setType(vElem.getAttribute("type"));

                Element certElem = (Element) vElem.getElementsByTagName("certificate").item(0);
                if (certElem != null) {
                    Certificate c = new Certificate();
                    c.setNumber(getTagValue("number", certElem));
                    c.setIssueDate(getTagValue("issueDate", certElem));
                    c.setExpiryDate(getTagValue("expiryDate", certElem));
                    c.setRegistrar(getTagValue("registrar", certElem));
                    v.setCertificate(c);
                }

                Element pkgElem = (Element) vElem.getElementsByTagName("package").item(0);
                if (pkgElem != null) {
                    MedPackage p = new MedPackage();
                    String typeVal = getTagValue("type", pkgElem);
                    String quantityVal = getTagValue("quantity", pkgElem);
                    String priceVal = getTagValue("price", pkgElem);
                    if (!typeVal.isEmpty()) p.setType(typeVal);
                    if (!quantityVal.isEmpty()) p.setQuantity(Integer.parseInt(quantityVal));
                    if (!priceVal.isEmpty()) p.setPrice(Double.parseDouble(priceVal));
                    v.setPkg(p);
                }

                Element dosElem = (Element) vElem.getElementsByTagName("dosage").item(0);
                if (dosElem != null) {
                    Dosage d = new Dosage();
                    String amountVal = getTagValue("amount", dosElem);
                    String unitVal = getTagValue("unit", dosElem);
                    String freqVal = getTagValue("frequency", dosElem);
                    if (!amountVal.isEmpty()) d.setAmount(Double.parseDouble(amountVal));
                    if (!unitVal.isEmpty()) d.setUnit(unitVal);
                    if (!freqVal.isEmpty()) d.setFrequency(freqVal);
                    v.setDosage(d);
                }

                m.addVersion(v);
            }
            medicines.add(m);
        }
        return medicines;
    }

    private static String getTagValue(String tag, Element elem) {
        NodeList list = elem.getElementsByTagName(tag);
        if (list.getLength() == 0) return "";
        return list.item(0).getTextContent();
    }
}