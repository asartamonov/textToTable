package generator.formatter.fsettings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@XmlRootElement(name = "settings")
public class Settings {
    private Page page;
    private Columns columns;

    public Page getPage() {
        return page;
    }

    @XmlElement(name = "page")
    public void setPage(Page page) {
        this.page = page;
    }

    public Columns getColumns() {
        return columns;
    }

    @XmlElement(name = "columns")
    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    public Settings initFromFile(Path path) {
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Settings settings = (Settings) jaxbUnmarshaller.unmarshal(reader);
            this.setColumns(settings.getColumns());
            this.setPage((settings.getPage()));
            for (int i = 0; i < columns.getColumnList().size(); i++) {
                Column c = columns.getColumnList().get(i);
                if (c.getWidth() <= 0)
                    throw new IllegalArgumentException("Column can't be less or equal to zero");
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Can't read setting file and initiate Generator");
            System.out.println("be sure you are calling Genearator as (example)");
            System.out.println("java -jar Generator.jar settings_filename.xml source-data_filename.tsv report_filename.txt");
            System.out.println("with proper filenames in arguments.");
            System.out.println("Be sure to place settings and resource files to same folder as jar archive");
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Can't read files and initiate Generator");
            System.out.println("be sure you are calling Genearator as (example)");
            System.out.println("java -jar Generator.jar settings_filename.xml source-data_filename.tsv report_filename.txt");
            System.out.println("with proper filenames in arguments.");
            System.out.println("Be sure to place settings and resource files to same folder as jar archive");
            //e.printStackTrace();
        }
        return this;
    }

    @Override
    public String toString() {
        return "page " + page + " columns " + columns;
    }
}
