import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CommandLine {
	public static void main(String[] args) {
		try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
			StringDB db = new StringDB();
			System.out.print("> ");
			input.lines().forEach(command -> {
				db.execute(command);
				System.out.print("> ");
			});
		} catch (IOException e) {
		}
	}
}
