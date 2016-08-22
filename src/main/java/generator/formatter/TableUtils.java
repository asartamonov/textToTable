package generator.formatter;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Коллекция методов, полезных для форматирования таблиц.
 * Все методы - static, объект этого класса создавать не нужно.
 */
public class TableUtils {
    /**
     * Метод разбивает текст в строках на колонки.
     *
     * @param strings      - список строк, которые разбиваются на колонки
     * @param colDelimiter - строка (символ или несколько) по которым происходит разбиение
     * @return список колонок, вложенный в список строк
     */
    public static ArrayList<ArrayList<String>> chopStringsToCols(ArrayList<String> strings, String colDelimiter) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int i1 = 0; i1 < strings.size(); i1++) {
            result.add(i1, new ArrayList<>());
            result.get(i1).addAll(Arrays.asList(strings.get(i1).split(colDelimiter)));
        }
        return result;
    }

    /**
     * Метод форматирует текст в колонках. Используется библиотека Apache Commons Lang.
     *
     * @param listOfStringsAndCols - список строк со вложенными списками колонок
     * @param columnWidths         - карта ключ - номер колонки, значение - ширина колонки
     * @param newLineStr           - символ новой строки
     * @param wrapLongWords        - разбивать/нет длинные слова на части
     * @return список колонок, вложенный в список строк, текст во всех колонках отформатирован
     * согласно переданным параметрам.
     */
    public static ArrayList<ArrayList<String>> wrapTextInCols(ArrayList<ArrayList<String>> listOfStringsAndCols,
                                                              Map<Integer, Integer> columnWidths,
                                                              String newLineStr,
                                                              boolean wrapLongWords) {
        ArrayList<ArrayList<String>> rawData = new ArrayList<>(listOfStringsAndCols);
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int i1 = 0; i1 < rawData.size(); i1++) { // для каждой строки
            ArrayList<String> cols = (ArrayList<String>) rawData.get(i1).clone(); // clone чтобы не мутировал аргумент !
            for (int i2 = 0; i2 < cols.size(); i2++) { // для каждой колонки в строке
                String col = WordUtils.wrap(cols.get(i2), columnWidths.get(i2), newLineStr, wrapLongWords);
                cols.set(i2, col);
            }
            result.add(cols);
        }
        return result;
    }

    /**
     * Метод получает на вход список в котором находятся списки колонок, а возвращает
     * список в котором находится список строк списком колонок в каждом из которых список
     * строчек в данной колонке, высоты колонок выровнены по высоте.
     *
     * @param stringsWithColsDiffHeight - список в котором находятся списки колонок разной высоты
     * @return - список строк - колонок - строк в колонках, высоты колонок нормализованы по высоте
     */
    public static ArrayList<ArrayList<ArrayList<String>>> normalizeColsByHeight(
            ArrayList<ArrayList<String>> stringsWithColsDiffHeight) {

        ArrayList<ArrayList<ArrayList<String>>> result = new ArrayList<>();

        for (int i1 = 0; i1 < stringsWithColsDiffHeight.size(); i1++) { // для каждой строки
            ArrayList<ArrayList<String>> oneString = new ArrayList<>();
            for (int i2 = 0; i2 < stringsWithColsDiffHeight.get(i1).size(); i2++) { // для каждой колонки
                ArrayList<String> oneColumnStrings = new ArrayList<>();
                String mergedOneColumnString = stringsWithColsDiffHeight.get(i1).get(i2);//одна ячейка
                oneColumnStrings.addAll(Arrays.asList(mergedOneColumnString.split(Formatter.newLineSign)));
                oneString.add(i2, oneColumnStrings); // ок
            }
            result.add(i1, oneString);
        }
        for (int i1 = 0; i1 < result.size(); i1++) { //для каждой строки
            ArrayList<ArrayList<String>> oneString = result.get(i1);
            int maxHeight = 0; // высота самой высокой колонки в строке
            for (ArrayList<String> oneCol : oneString) {
                maxHeight = Math.max(maxHeight, oneCol.size());
            }
            for (int i2 = 0; i2 < oneString.size(); i2++) { // для каждой колонки в строке
                ArrayList<String> oneCol = oneString.get(i2);
                int startColHeight = oneCol.size();
                for (int i3 = 0; i3 < maxHeight - startColHeight; i3++) {
                    oneCol.add(" "); // добавить строк-пробелов до максимума высоты
                }
            }
        }
        return result;
    }

    /**
     * Метод получает на вход список в котором находятся списки колонок, а возвращает
     * список в котором находится список строк со списком колонок в каждом из которых список
     * строчек в данной колонке, каждая строчка отформатирована согласно переданным настройкам и отделена
     * от соседней строчки переданным разделителем.
     * Строчки в колонках выравниваются влево.
     * Метод не разрывает строки, поэтому предварительно количество слов в строках должно быть
     * выровнено вручную или методом wrapTextInCols.
     */
    public static ArrayList<ArrayList<ArrayList<String>>> formatColumns(
            ArrayList<ArrayList<ArrayList<String>>> cells, Map<Integer, Integer> colWidths, String colDelimiter) {
        ArrayList<ArrayList<ArrayList<String>>> rawCells =
                (ArrayList<ArrayList<ArrayList<String>>>) cells.clone();
        for (int i1 = 0; i1 < rawCells.size(); i1++) { //для каждой строки
            for (int i2 = 0; i2 < rawCells.get(i1).size(); i2++) { //для каждой колонки
                ArrayList<String> cell = rawCells.get(i1).get(i2);
                for (int i3 = 0; i3 < cell.size(); i3++) { //для каждой строки в колонке
                    String format;
                    if (i2 == 0) {
                        format = "%s %-" + colWidths.get(i2) + "s %s";
                        cell.set(i3, String.format(format, colDelimiter, cell.get(i3), colDelimiter));
                    } else {
                        format = " %-" + colWidths.get(i2) + "s %s";
                        cell.set(i3, String.format(format, cell.get(i3), colDelimiter));
                    }

                }
            }
        }
        return rawCells;
    }

    /**
     * Метод получает список строк-ячеек-слов, и соединяет их в строки построчно.
     *
     * @param stringsWithColsDelimited - список строк с вложенными списками колонок и слов в ячейке.
     * @return список строк
     */
    public static ArrayList<String> mergeRows(ArrayList<ArrayList<ArrayList<String>>> stringsWithColsDelimited) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Integer> rowCoordinates = new ArrayList<>();
        for (int i = 0; i < stringsWithColsDelimited.size(); i++) {
            ArrayList<ArrayList<String>> rows = stringsWithColsDelimited.get(i);
            if (i != 0) {
                rowCoordinates.add(rowCoordinates.get(i - 1) + rows.get(0).size());
            } else {
                rowCoordinates.add(rows.get(0).size());
            }
        }
        for (int i1 = 0; i1 < stringsWithColsDelimited.size(); i1++) { //для кажой row
            ArrayList<ArrayList<String>> row = stringsWithColsDelimited.get(i1);
            int cellHeight = row.get(0).size();

            for (int k = 0; k < cellHeight; k++) {
                StringBuilder sb = new StringBuilder();
                for (int i2 = 0; i2 < row.size(); i2++) { //для каждой ячейки
                    sb.append(row.get(i2).get(k));
                }
                result.add(sb.toString());
            }
        }
        StringBuilder lineDelimiter = new StringBuilder();
        for (int i = 0; i < result.get(0).length(); i++) {
            if (result.get(0).charAt(i) != Formatter.columnDelimiter.charAt(0)) {
                lineDelimiter.append(Formatter.lineDelimiter);
            } else {
                lineDelimiter.append("+");
            }
        }
        for (int i = rowCoordinates.size() - 1; i >= 0; i--) {
            result.add(rowCoordinates.get(i), lineDelimiter.toString());
        }
        return result;
    }

    /**
     * Метод получает список строк и высоту страницы.
     * Строки разделены символом Formatter.lineDelimiter;
     * Первая строка в получаемом списке трактуется как заголовок.
     * Предполагается, что текст в строках уже отформатирован нужным образом.
     *
     * @param onlyStrings - список строк, который нужно разбить на страницы по высоте.
     * @param -           требуемая высоа страницы.
     * @return список строк, в который вставлены разделители страниц Formatter.pageDelimiter,
     * а заголовок повторен на каждой новой странице.
     */
    public static ArrayList<String> paginate(ArrayList<String> onlyStrings, int pageHeight) {
        ArrayList<String> rawData = (ArrayList<String>) onlyStrings.clone();

        ArrayList<String> header = new ArrayList<>();
        while (rawData.get(0).charAt(0) == Formatter.columnDelimiter.charAt(0)) {
            header.add(rawData.remove(0));
        }
        String delimiter = rawData.remove(0);
        ArrayList<String> result = new ArrayList<>();
        while (rawData.size() > 0) {
            if (!(rawData.size() == 1 && rawData.get(0).equals(delimiter))) {
                for (int i = 0; i < header.size(); i++) {
                    result.add(header.get(i));
                }
                result.add(delimiter);
            }
            if (rawData.get(0).equals(delimiter))
                rawData.remove(0);
            for (int j = 0; j < pageHeight - header.size() - 1; j++) {
                if (rawData.size() > 0) {
                    result.add(rawData.remove(0));
                }
            }
            if (rawData.size() > 0) {
                result.add(Formatter.pageDelimiter);
            }
        }
        return result;
    }
}
