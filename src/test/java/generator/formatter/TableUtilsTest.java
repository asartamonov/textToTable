package generator.formatter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TableUtilsTest {

    @Test
    public void testChopStringsToCols() throws Exception {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1" + Formatter.tabSign + "25/11" + Formatter.tabSign + "Павлов Дмитрий");
        String colDelimiter = Formatter.tabSign;
        ArrayList<ArrayList<String>> result = TableUtils.chopStringsToCols(strings, colDelimiter);
        assertEquals(result.get(0).size(), 3); // количество колонок равно установленному числу
    }

    @Test
    public void testWrapTextInCols() throws Exception {
        ArrayList<ArrayList<String>> stringAndCols = new ArrayList<>();
        ArrayList<String> columns = new ArrayList<>();
        Map<Integer, Integer> columnWidths = new HashMap<>();
        columnWidths.put(0, 2);
        columnWidths.put(1, 4);
        columnWidths.put(2, 4);
        columns.addAll(Arrays.asList("1", "25/11", "Павлов Дмитрий Анатольевич"));
        stringAndCols.add(columns);
        ArrayList<ArrayList<String>> stringsAndColsFormatted =
                TableUtils.wrapTextInCols(stringAndCols, columnWidths, Formatter.newLineSign, true);
        assertEquals(stringsAndColsFormatted.get(0).size(), 3);
        ArrayList<String> string = stringsAndColsFormatted.get(0);
        for (int i = 0; i < string.size(); i++) {
            String s = string.get(i);
            String[] substrings = s.split(Formatter.newLineSign);
            for (int i1 = 0; i1 < substrings.length; i1++) {
                String substring = substrings[i1];
                assertTrue(substring.length() <= columnWidths.get(i)); // каждая строка в колонке не длиннее лимита
            }
        }
    }

    @Test
    public void testNormalizeColsByHeight() throws Exception {
        ArrayList<ArrayList<String>> stringAndCols = new ArrayList<>();
        ArrayList<String> columns = new ArrayList<>();
        Map<Integer, Integer> columnWidths = new HashMap<>();
        columnWidths.put(0, 2);
        columnWidths.put(1, 4);
        columnWidths.put(2, 4);
        columns.addAll(Arrays.asList("1", "25/11", "Павлов Дмитрий Анатольевич"));
        stringAndCols.add(columns);
        ArrayList<ArrayList<ArrayList<String>>> result = TableUtils.normalizeColsByHeight(stringAndCols);
        for (int i1 = 0; i1 < result.size(); i1++) {
            ArrayList<ArrayList<String>> oneString = result.get(i1); // элементы списка - строки в колонке
            for (int i2 = 1; i2 < oneString.size(); i2++) {
                assertEquals(oneString.get(i2).size(), oneString.get(i2 - 1).size()); // все высоты равны
            }
        }
    }

    @Test
    public void testFormatColumns() throws Exception {
        ArrayList<ArrayList<ArrayList<String>>> rows = new ArrayList<>();
        ArrayList<ArrayList<String>> columns = new ArrayList<>();
        columns.add(new ArrayList<>());
        columns.add(new ArrayList<>());
        columns.add(new ArrayList<>());
        columns.get(0).add("1");
        columns.get(1).add("2012");
        columns.get(2).add("Полиграф");
        rows.add(columns);

        Map<Integer, Integer> columnWidths = new HashMap<>();
        columnWidths.put(0, 5);
        columnWidths.put(1, 8);
        columnWidths.put(2, 10);

        ArrayList<ArrayList<ArrayList<String>>> string = TableUtils.formatColumns(
                rows, columnWidths, Formatter.columnDelimiter);
        assertEquals(rows.size(), 1);
        for (int i1 = 0; i1 < string.size(); i1++) {
            ArrayList<ArrayList<String>> cols = string.get(i1);
            for (int i2 = 0; i2 < cols.size(); i2++) {
                ArrayList<String> oneCell = cols.get(i2);
                for (int i3 = 0; i3 < oneCell.size(); i3++) {
                    if (i2 == 0) {
                        assertEquals(oneCell.get(i3).length(), columnWidths.get(i2).intValue() + 4);
                    } else {
                        assertEquals(oneCell.get(i3).length(), columnWidths.get(i2).intValue() + 3);
                    }
                }
            }
        }
    }

    @Test
    public void testMergeRows() throws Exception {
        ArrayList<ArrayList<ArrayList<String>>> rows = new ArrayList<>();
        ArrayList<ArrayList<String>> columns = new ArrayList<>();
        columns.add(new ArrayList<>());
        columns.add(new ArrayList<>());
        columns.add(new ArrayList<>());
        columns.get(0).add("| 1     |");
        columns.get(0).add("|       | ");
        columns.get(1).add(" 2012   |");
        columns.get(1).add("       |");
        columns.get(2).add(" Полиграф     |");
        columns.get(2).add(" Полиграфович |");
        rows.add(columns);
        ArrayList<String> merged = TableUtils.mergeRows(rows);
        assertEquals(merged.size(), 3);
        for (String s : merged)
            assertEquals(s.length(),
                    columns.get(0).get(0).length() +
                            columns.get(1).get(0).length() +
                            columns.get(2).get(0).length());
    }

    @Test
    public void testPaginate() throws Exception {
        String rawString =
                "| Номер    | Дата    | ФИО     |\n" +
                        "+----------+---------+---------+\n" +
                        "| 1        | 25/11   | Павлов  |\n" +
                        "|          |         | Дмитрий |\n" +
                        "+----------+---------+---------+\n" +
                        "| 2        | 26/11   | Павлов  |\n" +
                        "|          |         | Констан |\n" +
                        "|          |         | тин     |\n" +
                        "+----------+---------+---------+\n" +
                        "| 3        | 27/11   | Н/Д     |\n" +
                        "+----------+---------+---------+\n" +
                        "| 4        | 28/11   | Ким Чен |\n" +
                        "|          |         | Ир      |\n" +
                        "+----------+---------+---------+\n" +
                        "| 5        | 29/11/2 | Юлианна |\n" +
                        "|          | 009     | -Оксана |\n" +
                        "|          |         | Сухово- |\n" +
                        "|          |         | Кобылин |\n" +
                        "|          |         | а       |\n" +
                        "+----------+---------+---------+";
        ArrayList<String> rawData = new ArrayList<>();
        rawData.addAll(Arrays.asList(rawString.split("\n")));
        ArrayList<String> paginatedData = TableUtils.paginate(rawData, 5);
        assertEquals(paginatedData.size(), 34);
    }
}