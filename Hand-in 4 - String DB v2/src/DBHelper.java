import java.io.*;
import java.util.*;
import java.util.stream.*;

public class DBHelper {
	public static void printTable(Iterable<String> header, Iterable<? extends Iterable<String>> rows) {
		List<Integer> columnWidth = new ArrayList<>();
		for (String s : header) {
			columnWidth.add(s.length());
		}
		for (Iterable<String> row : rows) {
			int col = 0;
			for (String entry : row) {
				columnWidth.set(col, Math.max(entry.length(), columnWidth.get(col)));
				col++;
			}
		}

		int col = 0;
		for (String s : header) {
			if (col > 0) System.out.print("|");
			int width = columnWidth.get(col) + 2;
			int left = (width - s.length()) / 2;
			int right = width - left - s.length();
			System.out.printf("%" + left + "s%s%" + right + "s", "", s, "");
			col++;
		}
		System.out.println();
		int totalWidth = columnWidth.stream().mapToInt(i->i+3).sum()-1;
		char[] hline = new char[totalWidth];
		Arrays.fill(hline, '-');
		col = -1;
		for (int w : columnWidth) {
			if (col > 0)
				hline[col] = '+';
			col += w + 3;
		}
		System.out.println(new String(hline));
		List<String> rowLines = new ArrayList<>();
		for (Iterable<String> row : rows) {
			col = 0;
			StringBuilder sb = new StringBuilder();
			String overflow = " ";
			for (String s : row) {
				// if (col > 0) sb.append("|");
				sb.append(overflow);
				// sb.append(String.format(" %-" + columnWidth.get(col) + "s ", s));
				sb.append(s);
				overflow = String.format("%" + (columnWidth.get(col) - s.length() + 3) + "s", " | ");
				col++;
			}
			rowLines.add(sb.toString());
		}
		rowLines.sort(String::compareTo);
		rowLines.forEach(System.out::println);
		System.out.printf("(%d row%s)\n\n", rowLines.size(), rowLines.size() == 1 ? "" : "s");
	}

	public static void createTableFromFile(StringDB db, String tablename, String filename) {
		try (BufferedReader input = new BufferedReader(new FileReader(filename))) {
			List<String> header = Arrays.asList(input.readLine().split(","));
			db.execute(header.stream().collect(Collectors.joining(" varchar, ", "CREATE TABLE " + tablename + " ( ", " varchar );")));
			input.lines().forEach(line -> {
				List<String> row = Arrays.asList(line.split(","));
				if (row.size() != header.size()) {
					System.out.println("Error: Row has the wrong number of entries, skipped: " + line);
				} else {
					db.execute(row.stream().collect(Collectors.joining("' , '", "INSERT INTO " + tablename + " VALUES ( '", "' );")));
				}
			});
		} catch (IOException e) {
			System.out.println("Cannot read file: " + filename);
		}
	}
}
