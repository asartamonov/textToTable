package generator.formatter.fsettings;

import generator.formatter.fsettings.Settings;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class SettingsTest {
    private static String testXmlString = "" +
            "<settings>\n" +
            "\t<page>\n" +
            "\t\t<width>32</width>\n" +
            "\t\t<height>12</height>\n" +
            "\t</page>\n" +
            "\t<columns>\n" +
            "\t\t<column>\n" +
            "\t\t\t<title>Номер</title>\n" +
            "\t\t\t<width>8</width>\n" +
            "\t\t</column>\n" +
            "\t\t<column>\n" +
            "\t\t\t<title>Дата</title>\n" +
            "\t\t\t<width>7</width>\n" +
            "\t\t</column>\n" +
            "\t\t<column>\n" +
            "\t\t\t<title>ФИО</title>\n" +
            "\t\t\t<width>7</width>\n" +
            "\t\t</column>\n" +
            "\t</columns>\n" +
            "</settings>";

    @Test
    public void XmlParseTest() {
        Settings settings = null;
        try {
            InputStream stream = new ByteArrayInputStream(testXmlString.getBytes(StandardCharsets.UTF_16));
            JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            settings = (Settings) jaxbUnmarshaller.unmarshal(stream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        assertEquals(settings.getColumns().getColumnList().size(), 3);
        assertEquals(settings.getPage().getHeight(), 12);
        assertEquals(settings.getPage().getWidth(), 32);
    }

    @Test
    public void XmlFileReadTest(){
        Settings settings = new Settings();
        Path settPath = null;
        try {
            settPath = Paths.get(getClass()
                    .getResource("/settings.xml")
                    .toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        settings.initFromFile(settPath);
        assertEquals(settings.getColumns().getColumnList().size(), 3);
        assertEquals(settings.getPage().getHeight(), 12);
        assertEquals(settings.getPage().getWidth(), 32);
    }
}