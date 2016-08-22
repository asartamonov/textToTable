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

<div class="highlight highlight-source-shell"><pre>+--------------+--------------+--------------+--------------+-------------+
<span class="pl-k">|</span>NAME          <span class="pl-k">|</span>GENDER        <span class="pl-k">|</span>MARRIED       <span class="pl-k">|</span>           AGE<span class="pl-k">|</span>    SALARY($)<span class="pl-k">|</span>
+--------------+--------------+--------------+--------------+-------------+
<span class="pl-k">|</span>Eddy          <span class="pl-k">|</span>Male          <span class="pl-k">|</span>No            <span class="pl-k">|</span>            23<span class="pl-k">|</span>      1200.27<span class="pl-k">|</span>
<span class="pl-k">|</span>Libby         <span class="pl-k">|</span>Male          <span class="pl-k">|</span>No            <span class="pl-k">|</span>            17<span class="pl-k">|</span>       800.50<span class="pl-k">|</span>
<span class="pl-k">|</span>Rea           <span class="pl-k">|</span>Female        <span class="pl-k">|</span>No            <span class="pl-k">|</span>            30<span class="pl-k">|</span>     10000.00<span class="pl-k">|</span>
<span class="pl-k">|</span>Deandre       <span class="pl-k">|</span>Female        <span class="pl-k">|</span>No            <span class="pl-k">|</span>            19<span class="pl-k">|</span>     18000.50<span class="pl-k">|</span>
<span class="pl-k">|</span>Alice         <span class="pl-k">|</span>Male          <span class="pl-k">|</span>Yes           <span class="pl-k">|</span>            29<span class="pl-k">|</span>       580.40<span class="pl-k">|</span>
<span class="pl-k">|</span>Alyse         <span class="pl-k">|</span>Female        <span class="pl-k">|</span>No            <span class="pl-k">|</span>            26<span class="pl-k">|</span>      7000.89<span class="pl-k">|</span>
<span class="pl-k">|</span>Venessa       <span class="pl-k">|</span>Female        <span class="pl-k">|</span>No            <span class="pl-k">|</span>            22<span class="pl-k">|</span>    100700.50<span class="pl-k">|</span>
+--------------+--------------+--------------+--------------+-------------+</pre></div>