package generator;

import generator.formatter.Formatter;
import generator.formatter.SimpleFormatter;
import generator.formatter.fsettings.Settings;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    private Formatter formatter;
    private Settings settings;
    private List<String> rawData;
    private List<String> formattedData;

    public static void main(String[] args) {
        // пример вызова java -jar Generator.jar settings.xml source-data.tsv example-report.txt
        Generator generator = new Generator();
        String settingsFileName = args[0];
        String sourceFileName = args[1];
        String reportFileName = args[2];
        System.out.println("Reading files from: " + Paths.get("").toAbsolutePath());
        System.out.println("Reading settings.. " + settingsFileName);
        System.out.println("Reading source.. " + sourceFileName);
        Path settingsPath = Paths.get(settingsFileName);
        Path sourcePath = Paths.get(sourceFileName);
        Path reportPath = Paths.get(reportFileName);
        generator.generateReport(settingsPath, sourcePath, reportPath);
    }

    /**
     * Метод получает на вход пути к XML файлу настрок, сырым данным, разделённым знаком табуляции
     * и путь, в который будет произведена запись сгенерированного отчёта.
     *
     * @param settingsPath - path XML файла из которого будут взяты настройки.
     * @param sourcePath   - path файла с сырыми данным разделёнными на колонки знаками табуляции
     * @param reportPath   -  path файла в который будет произведена запись готового отчёта
     */
    public void generateReport(Path settingsPath, Path sourcePath, Path reportPath) {

        // read raw data for report
        this.setRawData(this.readData(sourcePath));

        //parse XML and set settings
        this.setSettings(new Settings().initFromFile(settingsPath));

        // set formatter
        this.setFormatter(new SimpleFormatter(this.getSettings()));

        // perform formatting raw data to formatted report
        this.setFormattedData(this.getFormatter()
                .format(this.getRawData()));

        //write report to file
        this.writeFormattedData(reportPath, this.getFormattedData());
    }

    // read raw data from file
    public List<String> readData(Path path) {
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_16);
        } catch (IOException e) {
            System.out.println("Can't read source files and initiate Generator");
            System.out.println("be sure you are calling Genearator as (example)");
            System.out.println("java -jar Generator.jar settings_filename.xml source-data_filename.tsv report_filename.txt");
            System.out.println("with proper filenames in arguments.");
            System.out.println("Be sure to place settings and resource files to same folder for convenience");
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // write formatted data to file
    private void writeFormattedData(Path reportPath, List<String> formattedData) {
        try {
            Files.write(reportPath, formattedData, StandardCharsets.UTF_16);
            System.out.println("");
            System.out.println("Report preview:");
            System.out.println("");
            for (String s : formattedData) {
                System.out.println(s);
            }
            System.out.println("");
            System.out.println("Writing report file to " + reportPath);
        } catch (IOException e) {
            System.out.println("Can't write files");
            System.out.println("be sure you are writing to a writable source and calling Genearator as (example)");
            System.out.println("java -jar Generator.jar settings_filename.xml source-data_filename.tsv report_filename.txt");
            System.out.println("with proper filenames in arguments.");
            System.out.println("Be sure to place settings and resource files to same folder for convenience");
        }
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public List<String> getFormattedData() {
        return formattedData;
    }

    public void setFormattedData(List<String> formattedData) {
        this.formattedData = formattedData;
    }

    public List<String> getRawData() {
        return rawData;
    }

    public void setRawData(List<String> rawData) {
        this.rawData = rawData;
    }

}
