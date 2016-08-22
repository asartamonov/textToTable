package generator.formatter;

import generator.formatter.fsettings.Settings;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimpleFormatterTest {

    @Test
    public void testFormat() throws Exception {
        Settings settings = new Settings();
        Formatter formatter = new SimpleFormatter(settings);
        Path settPath = null;
        try {
            settPath = Paths.get(getClass()
                    .getResource("/settings.xml")
                    .toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        settings.initFromFile(settPath);
        List<String> data = new ArrayList<>();
        data.add("1\t25/11\tПавлов Дмитрий");
        data.add("2\t26/11\tПавлов Константин");
        data.add("3\t27/11\tН/Д");
        data.add("4\t28/11\tКим Чен Ир");
        data.add("5\t29/11/2009\tЮлианна-Оксана Сухово-Кобылина");
        List<String> formatted = formatter.format(data);
        int initStrLen = formatted.get(0).length();
        for (int i = 1; i < formatted.size(); i++) {
            String s = formatted.get(i);
            if (!s.equals(Formatter.pageDelimiter)) {
                assertEquals(s.length(), initStrLen);
            }
        }
    }
}