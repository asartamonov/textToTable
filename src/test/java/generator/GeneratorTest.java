package generator;

import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class GeneratorTest {

    @Test
    public void testReadData() throws Exception {
        Generator generator = new Generator();
        Path rawDataPath = null;
        try {
            rawDataPath = Paths.get(getClass()
                    .getResource("/source-data.tsv")
                    .toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        generator.setRawData(generator.readData(rawDataPath));
        assertEquals(generator.getRawData().size(), 5);
    }

    @Test
    public void testMain() throws Exception {
        String[] args = {
                "target/test-classes/settings.xml",
                "target/test-classes/source-data.tsv",
                "generator-rapport.txt"
        };
        String settingsFileName = args[0];
        String sourceFileName = args[1];
        String reportFileName = args[2];
        System.out.println("Reading settings.. " + settingsFileName);
        System.out.println("Reading source.. " + sourceFileName);
        Path settingsPath = Paths.get(settingsFileName);
        Path sourcePath = Paths.get(sourceFileName);
        Path reportPath = Paths.get(reportFileName);
        Generator generator = new Generator();
        generator.generateReport(settingsPath, sourcePath, reportPath);
    }
}