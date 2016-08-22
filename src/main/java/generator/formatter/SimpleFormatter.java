package generator.formatter;

import generator.formatter.fsettings.Column;
import generator.formatter.fsettings.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleFormatter implements Formatter {
    private Settings settings;
    private List<String> rawData;

    public SimpleFormatter(Settings settings) {
        this.settings = settings;
    }

    @Override
    public List<String> format(List<String> rawData) {
        this.rawData = rawData;
        ArrayList<String> strings = new ArrayList<>();
        Map<Integer, Integer> columnWidths = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Column c : this.settings.getColumns().getColumnList()) {
            columnWidths.put(i, c.getWidth()); // ширины колонок, переданные в настройках
            sb.append(c.getTitle()).append(Formatter.tabSign); // названия колонок, переданные в настройках
            i++;
        }
        sb.delete(sb.length() - 1, sb.length());
        strings.add(sb.toString());
        for (String s : this.rawData)
            strings.add(s);
        ArrayList<ArrayList<String>> stringsWithCols =
                TableUtils.chopStringsToCols(strings, Formatter.tabSign);
        ArrayList<ArrayList<String>> stringsWithColsFormatted =
                TableUtils.wrapTextInCols(stringsWithCols, columnWidths, Formatter.newLineSign, true);
        ArrayList<ArrayList<ArrayList<String>>> stringsWithColsFormattedNormalized =
                TableUtils.normalizeColsByHeight(stringsWithColsFormatted);
        ArrayList<ArrayList<ArrayList<String>>> stringsWithColsDelimited = TableUtils.formatColumns(
                stringsWithColsFormattedNormalized, columnWidths, Formatter.columnDelimiter);
        ArrayList<String> onlyStrings = TableUtils.mergeRows(stringsWithColsDelimited);
        ArrayList<String> paginated = TableUtils.paginate(onlyStrings, this.settings.getPage().getHeight());
        return paginated;
    }
}

