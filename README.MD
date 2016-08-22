Purpose
===========
The textToTable tool helps to present raw data in
a formatted table form.

Example
===========

*input raw data file.tsv:*
1	25/11	Павлов Дмитрий
2	26/11	Павлов Константин
3	27/11	Н/Д
4	28/11	Ким Чен Ир
5	29/11/2009	Юлианна-Оксана Сухово-Кобылина


*input settings file settings.xml:*
<?xml version="1.0" encoding="UTF-8"?>

<settings>
	<page>
		<width>32</width>
		<height>12</height>
	</page>
	<columns>
		<column>
			<title>Номер</title>
			<width>8</width>
		</column>
		<column>
			<title>Дата</title>
			<width>7</width>
		</column>
		<column>
			<title>ФИО</title>
			<width>7</width>
		</column>
	</columns>
</settings>

*output:*
| Номер    | Дата    | ФИО     |
+----------+---------+---------+
| 1        | 25/11   | Павлов  |
|          |         | Дмитрий |
+----------+---------+---------+
| 2        | 26/11   | Павлов  |
|          |         | Констан |
|          |         | тин     |
+----------+---------+---------+
| 3        | 27/11   | Н/Д     |
+----------+---------+---------+
| 4        | 28/11   | Ким Чен |
~
| Номер    | Дата    | ФИО     |
+----------+---------+---------+
|          |         | Ир      |
+----------+---------+---------+
| 5        | 29/11/2 | Юлианна |
|          | 009     | -Оксана |
|          |         | Сухово- |
|          |         | Кобылин |
|          |         | а       |
+----------+---------+---------+
