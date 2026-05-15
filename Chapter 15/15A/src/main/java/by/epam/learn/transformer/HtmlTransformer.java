package main.java.by.epam.learn.transformer;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class HtmlTransformer {

    public static void transform(String xmlPath, String xslPath, String htmlPath) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(new File(xslPath)));
        transformer.transform(new StreamSource(new File(xmlPath)), new StreamResult(new File(htmlPath)));
        System.out.println("✓ HTML создан: " + htmlPath);
    }
}
