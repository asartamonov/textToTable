package generator.formatter;

import java.util.List;

public interface Formatter {
    String lineDelimiter = "-";
    String pageDelimiter = "~";
    String columnDelimiter = "|";
    String tabSign = "\u0009";
    String newLineSign = System.getProperty("line.separator").toString();

    List<String> format(List<String> rawData);
}
