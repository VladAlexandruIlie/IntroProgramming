import java.util.*;
import java.util.regex.*;

public class StringDB {
	private static final Pattern CREATETABLE = Pattern.compile(
			"(?i) *CREATE +TABLE +(?<dst>\\w+) *\\( *(?<atts>\\w+ +varchar( *, *\\w+ +varchar)*) *\\) *; *");
	private static final Pattern CREATETABLE_ATTS = Pattern.compile(
			"(?i)(?<att>\\w+) +varchar");
	private static final Pattern INSERTINTO = Pattern.compile(
			"(?i) *INSERT +INTO +(?<dst>\\w+) +VALUES *\\( *(?<vals>'[^']+'( *, *'[^']+')*) *\\) *; *");
	private static final Pattern INSERTINTO_VALUES = Pattern.compile(
			"(?i)'(?<val>[^']+)'");
	private static final Pattern DROPTABLE = Pattern.compile(
			"(?i) *DROP +TABLE +(?<src>\\w+) *; *");
	private static final Pattern SELECTALL = Pattern.compile(
			"(?i) *SELECT +\\* +FROM +(?<src>\\w+) *; *");
	private static final Pattern SELECTAS = Pattern.compile(
			"(?i) *SELECT +(?<aliases>\\w+ +AS +\\w+ *( *, *\\w+ +AS +\\w+)*) +INTO +(?<dst>\\w+) FROM (?<src>\\w+) *; *");
	private static final Pattern SELECTAS_ALIAS = Pattern.compile(
			"(?i)(?<att>\\w+) +AS +(?<alias>\\w+)");
	private static final Pattern INNERJOIN = Pattern.compile(
			"(?i) *SELECT +\\* +INTO +(?<dst>\\w+) +FROM +(?<left>\\w+) +INNER +JOIN +(?<right>\\w+) +ON +(?<leftatt>\\w+) *= *(?<rightatt>\\w+) *; *");

	public void execute(String command) {
		Matcher cmd;
		if ((cmd = CREATETABLE.matcher(command)).matches()) {
			List<String> atts = new ArrayList<>();
			Matcher attMatch = CREATETABLE_ATTS.matcher(cmd.group("atts"));
			while (attMatch.find()) atts.add(attMatch.group("att"));
			System.out.println("It creates a new table called " + cmd.group("dst") +
					"\nThe table has " + atts.size() + " columns, called:\n" + atts);
		} else if ((cmd = INSERTINTO.matcher(command)).matches()) {
			String dst = cmd.group("dst");
			List<String> values = new ArrayList<>();
			Matcher valMatch = INSERTINTO_VALUES.matcher(command);
			while (valMatch.find()) values.add(valMatch.group("val"));
			System.out.println("It inserts a new row into the table called " + cmd.group("dst") +
					"\nThe row has the " + values.size() + " value" + (values.size() > 1 ? "s" : "") + ":\n" + values);
		} else if ((cmd = DROPTABLE.matcher(command)).matches()) {
			System.out.println("It completely removes the table called " + cmd.group("src") + "... be careful");
		} else if ((cmd = SELECTALL.matcher(command)).matches()) {
			System.out.println("It prints everything from the table called " + cmd.group("src"));
		} else if ((cmd = SELECTAS.matcher(command)).matches()) {
			List<String> atts = new ArrayList<>();
			List<String> aliases = new ArrayList<>();
			Matcher aliasMatch = SELECTAS_ALIAS.matcher(cmd.group("aliases"));
			while (aliasMatch.find()) {
				atts.add(aliasMatch.group("att"));
				aliases.add(aliasMatch.group("alias"));
			}
			System.out.println("So... It creates a new table called " + cmd.group("dst") +
					"\nThe table has " + aliases.size() + " columns, called:\n" + aliases +
					"\nThe new table has all the rows from the table called " + cmd.group("src") +
					"\nbut only the " + atts.size() + " columns called:\n" + atts);
		} else if ((cmd = INNERJOIN.matcher(command)).matches()) {
			System.out.println("Wow... It creates a new table called " + cmd.group("dst") +
					"\nThe columns are all those from the table " + cmd.group("left") + " followed by all those from " + cmd.group("right") +
					"\nThe rows will be all the ways to pair a row from " + cmd.group("left") + " with a row from " + cmd.group("right") +
					"\nbut only keeping those where the value of " + cmd.group("leftatt") +
					" is the same as the value of " + cmd.group("rightatt"));
		} else {
			throw new IllegalArgumentException("syntax error");
		}
	}
}
