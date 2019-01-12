public class StringDBTest {
	static java.io.PrintStream orgout;
	static RecordingOutputStream record;
	@org.junit.BeforeClass
	public static void setUpStdOut() {
		orgout = System.out;
		record = new RecordingOutputStream(orgout);
		System.setOut(new java.io.PrintStream(record));
	}
	@org.junit.AfterClass
	public static void tearDownStdOut() {
		System.setOut(orgout);
		record = null;
	}
	@org.junit.Test
	public void test01() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE courses ( coursename varchar ) ;");
		org.junit.Assert.assertEquals("CREATE TABLE\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test02() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE teachers ( name varchar , username varchar ) ;");
		org.junit.Assert.assertEquals("CREATE TABLE\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test03() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE customers ( ID varchar , CustomerName varchar , ContactName varchar , Address varchar , City varchar , PostalCode varchar , Country varchar ) ;");
		org.junit.Assert.assertEquals("CREATE TABLE\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test04() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE orders ( OrderID varchar , CustomerID varchar , EmployeeID varchar , OrderDate varchar , ShipperID varchar ) ;");
		org.junit.Assert.assertEquals("CREATE TABLE\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test05() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE teachers ( name varchar , name varchar ) ;");
		org.junit.Assert.assertEquals("ERROR:  column \"name\" specified more than once\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test06() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE orders ( OrderID varchar , CustomerID varchar , EmployeeID varchar , OrderDate varchar , OrderID varchar , ShipperID varchar ) ;");
		org.junit.Assert.assertEquals("ERROR:  column \"OrderID\" specified more than once\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test07() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE teachers ( name varchar , username varchar ) ;");
		db.execute("DROP TABLE teachers ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nDROP TABLE\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test08() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE courses ( coursename varchar ) ;");
		db.execute("DROP TABLE teachers ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nERROR:  table \"teachers\" does not exist\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test09() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE teachers ( name varchar , username varchar ) ;");
		db.execute("DROP TABLE teachers ;");
		db.execute("DROP TABLE teachers ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nDROP TABLE\nERROR:  table \"teachers\" does not exist\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test10() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE teachers ( name varchar , username varchar ) ;");
		db.execute("SELECT * FROM teachers ;");
		org.junit.Assert.assertEquals("CREATE TABLE\n name | username \n------+----------\n(0 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test11() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE teachers ( name varchar , username varchar ) ;");
		db.execute("INSERT INTO teachers VALUES ( 'Troels Bjerre Lund' , 'trbj' ) ;");
		db.execute("SELECT * FROM teachers ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nINSERT 0 1\n        name        | username \n--------------------+----------\n Troels Bjerre Lund | trbj\n(1 row)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test12() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE ta ( name varchar , username varchar ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Ivan Mladenov' , 'ivml' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Florin Akermann' , 'flak' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Astrid Neumann' , 'astn' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Vlad Limbean' , 'vlli' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Jens Rømer Hesselbjerg' , 'jenr' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Alf Andersen' , 'alfe' ) ;");
		db.execute("SELECT * FROM ta ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\n          name          | username \n------------------------+----------\n Alf Andersen           | alfe\n Astrid Neumann         | astn\n Florin Akermann        | flak\n Ivan Mladenov          | ivml\n Jens Rømer Hesselbjerg | jenr\n Vlad Limbean           | vlli\n(6 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test13() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE games ( ID varchar , GameName varchar , Price varchar , Genre varchar , YearOfRelease varchar , Developer varchar , Publisher varchar ) ;");
		db.execute("INSERT INTO games VALUES ( '1' , 'Super Mario Odyssey' , '80.0' , 'Platformer' , '2017' , 'Nintendo' , 'Nintendo' ) ;");
		db.execute("INSERT INTO games VALUES ( '2' , 'South Park 2' , '60.0' , 'RPG' , '2017' , 'Ubisoft' , 'Ubisoft' ) ;");
		db.execute("INSERT INTO games VALUES ( '3' , 'Cuphead' , '15.0' , 'Platformer' , '2017' , 'Studio MDHR' , 'Studio MDHR' ) ;");
		db.execute("INSERT INTO games VALUES ( '4' , 'Age of Empires 2' , '12.0' , 'RTS' , '1999' , 'Ensemble Studios' , 'Microsoft' ) ;");
		db.execute("INSERT INTO games VALUES ( '5' , 'Company of Heroes' , '12.99' , 'RTS' , '2006' , 'Relic Entertainment' , 'THQ' ) ;");
		db.execute("INSERT INTO games VALUES ( '6' , 'DOTA 2' , '0.0' , 'MOBA' , '2012' , 'Valve' , 'Valve' ) ;");
		db.execute("INSERT INTO games VALUES ( '7' , 'Shenzhen I/O' , '14.99' , 'Puzzle' , '2016' , 'Zach Barth' , 'Zach Barth' ) ;");
		db.execute("INSERT INTO games VALUES ( '8' , 'Papers, Please' , '9.99' , 'Puzzle' , '2013', '3909' , '3909' ) ;");
		db.execute("SELECT * FROM games ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\n ID |      GameName       | Price |   Genre    | YearOfRelease |      Developer      |  Publisher  \n----+---------------------+-------+------------+---------------+---------------------+-------------\n 1  | Super Mario Odyssey | 80.0  | Platformer | 2017          | Nintendo            | Nintendo\n 2  | South Park 2        | 60.0  | RPG        | 2017          | Ubisoft             | Ubisoft\n 3  | Cuphead             | 15.0  | Platformer | 2017          | Studio MDHR         | Studio MDHR\n 4  | Age of Empires 2    | 12.0  | RTS        | 1999          | Ensemble Studios    | Microsoft\n 5  | Company of Heroes   | 12.99 | RTS        | 2006          | Relic Entertainment | THQ\n 6  | DOTA 2              | 0.0   | MOBA       | 2012          | Valve               | Valve\n 7  | Shenzhen I/O        | 14.99 | Puzzle     | 2016          | Zach Barth          | Zach Barth\n 8  | Papers, Please      | 9.99  | Puzzle     | 2013          | 3909                | 3909\n(8 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test14() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE ta ( name varchar , username varchar , course varchar ) ;");
		db.execute("CREATE TABLE teaching ( teachername varchar , coursename varchar ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Ivan Mladenov' , 'ivml' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Florin Akermann' , 'flak' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Astrid Neumann' , 'astn' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Vlad Limbean' , 'vlli' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Jens Rømer Hesselbjerg' , 'jenr' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Alf Andersen' , 'alfe' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Jacob Harder' , 'jhar' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Mads Frederik Madsen' , 'mfrm' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Roman Novosad' , 'romn' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO teaching VALUES ( 'Troels Bjerre Lund' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO teaching VALUES ( 'Patrick Bahr' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO teaching VALUES ( 'Jens Kötters' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO teaching VALUES ( 'Bassel Mannaa' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO teaching VALUES ( 'Agata Murawska' , 'Discrete Mathematics' ) ;");
		db.execute("SELECT * FROM ta ;");
		db.execute("SELECT * FROM teaching ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nCREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\n          name          | username |          course          \n------------------------+----------+--------------------------\n Alf Andersen           | alfe     | Introductory Programming\n Astrid Neumann         | astn     | Introductory Programming\n Florin Akermann        | flak     | Introductory Programming\n Ivan Mladenov          | ivml     | Introductory Programming\n Jacob Harder           | jhar     | Discrete Mathematics\n Jens Rømer Hesselbjerg | jenr     | Introductory Programming\n Mads Frederik Madsen   | mfrm     | Discrete Mathematics\n Roman Novosad          | romn     | Discrete Mathematics\n Vlad Limbean           | vlli     | Introductory Programming\n(9 rows)\n\n    teachername     |        coursename        \n--------------------+--------------------------\n Agata Murawska     | Discrete Mathematics\n Bassel Mannaa      | Discrete Mathematics\n Jens Kötters       | Discrete Mathematics\n Patrick Bahr       | Discrete Mathematics\n Troels Bjerre Lund | Introductory Programming\n(5 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test15() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE ta ( name varchar , username varchar , course varchar ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Ivan Mladenov' , 'ivml' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Florin Akermann' , 'flak' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Astrid Neumann' , 'astn' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Vlad Limbean' , 'vlli' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Jens Rømer Hesselbjerg' , 'jenr' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Alf Andersen' , 'alfe' , 'Introductory Programming' ) ;");
		db.execute("SELECT * FROM ta ;");
		db.execute("SELECT username AS secret_email , name AS alias INTO superheroes FROM ta ;");
		db.execute("SELECT * FROM superheroes ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\n          name          | username |          course          \n------------------------+----------+--------------------------\n Alf Andersen           | alfe     | Introductory Programming\n Astrid Neumann         | astn     | Introductory Programming\n Florin Akermann        | flak     | Introductory Programming\n Ivan Mladenov          | ivml     | Introductory Programming\n Jens Rømer Hesselbjerg | jenr     | Introductory Programming\n Vlad Limbean           | vlli     | Introductory Programming\n(6 rows)\n\nSELECT 6\n secret_email |         alias          \n--------------+------------------------\n alfe         | Alf Andersen\n astn         | Astrid Neumann\n flak         | Florin Akermann\n ivml         | Ivan Mladenov\n jenr         | Jens Rømer Hesselbjerg\n vlli         | Vlad Limbean\n(6 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test16() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE ta ( name varchar , username varchar ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Ivan Mladenov' , 'ivml' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Florin Akermann' , 'flak' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Astrid Neumann' , 'astn' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Vlad Limbean' , 'vlli' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Jens Rømer Hesselbjerg' , 'jenr' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Alf Andersen' , 'alfe' ) ;");
		db.execute("SELECT name AS alias , username AS secret_email INTO superheroes FROM ta ;");
		db.execute("SELECT * FROM superheroes ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nSELECT 6\n         alias          | secret_email \n------------------------+--------------\n Alf Andersen           | alfe\n Astrid Neumann         | astn\n Florin Akermann        | flak\n Ivan Mladenov          | ivml\n Jens Rømer Hesselbjerg | jenr\n Vlad Limbean           | vlli\n(6 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test17() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE ta ( name varchar , username varchar ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Ivan Mladenov' , 'ivml' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Florin Akermann' , 'flak' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Astrid Neumann' , 'astn' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Vlad Limbean' , 'vlli' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Jens Rømer Hesselbjerg' , 'jenr' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Alf Andersen' , 'alfe' ) ;");
		db.execute("SELECT name AS alias INTO secret_identity FROM ta ;");
		db.execute("SELECT * FROM secret_identity ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nSELECT 6\n         alias          \n------------------------\n Alf Andersen\n Astrid Neumann\n Florin Akermann\n Ivan Mladenov\n Jens Rømer Hesselbjerg\n Vlad Limbean\n(6 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test18() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE left ( A varchar , leftkey varchar ) ;");
		db.execute("CREATE TABLE right ( rightkey varchar , B varchar ) ;");
		db.execute("INSERT INTO left VALUES ( 'foo', '1' ) ;");
		db.execute("INSERT INTO right VALUES ( '1' , 'bar' ) ;");
		db.execute("SELECT * INTO joined FROM left INNER JOIN right ON leftkey = rightkey ;");
		db.execute("SELECT * from joined ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nCREATE TABLE\nINSERT 0 1\nINSERT 0 1\nSELECT 1\n  A  | leftkey | rightkey |  B  \n-----+---------+----------+-----\n foo | 1       | 1        | bar\n(1 row)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test19() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE left ( A varchar , leftkey varchar ) ;");
		db.execute("CREATE TABLE right ( rightkey varchar , B varchar ) ;");
		db.execute("INSERT INTO left VALUES ( 'foo', '1' ) ;");
		db.execute("INSERT INTO left VALUES ( 'bar', '1' ) ;");
		db.execute("INSERT INTO right VALUES ( '1' , 'baz' ) ;");
		db.execute("INSERT INTO right VALUES ( '1' , 'qux' ) ;");
		db.execute("SELECT * from left ;");
		db.execute("SELECT * from right ;");
		db.execute("SELECT * INTO joined FROM left INNER JOIN right ON leftkey = rightkey ;");
		db.execute("SELECT * from joined ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nCREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\n  A  | leftkey \n-----+---------\n bar | 1\n foo | 1\n(2 rows)\n\n rightkey |  B  \n----------+-----\n 1        | baz\n 1        | qux\n(2 rows)\n\nSELECT 4\n  A  | leftkey | rightkey |  B  \n-----+---------+----------+-----\n bar | 1       | 1        | baz\n bar | 1       | 1        | qux\n foo | 1       | 1        | baz\n foo | 1       | 1        | qux\n(4 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test20() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE left ( A varchar , leftkey varchar ) ;");
		db.execute("CREATE TABLE right ( rightkey varchar , B varchar ) ;");
		db.execute("INSERT INTO left VALUES ( 'foo', '1' ) ;");
		db.execute("INSERT INTO right VALUES ( '2' , 'bar' ) ;");
		db.execute("SELECT * INTO joined FROM left INNER JOIN right ON leftkey = rightkey ;");
		db.execute("SELECT * from joined ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nCREATE TABLE\nINSERT 0 1\nINSERT 0 1\nSELECT 0\n A | leftkey | rightkey | B \n---+---------+----------+---\n(0 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test21() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE left ( A varchar , leftkey varchar ) ;");
		db.execute("CREATE TABLE right ( rightkey varchar , B varchar ) ;");
		db.execute("INSERT INTO left VALUES ( 'foo', '1' ) ;");
		db.execute("INSERT INTO left VALUES ( 'bar', '1' ) ;");
		db.execute("INSERT INTO left VALUES ( 'baz', '1' ) ;");
		db.execute("INSERT INTO right VALUES ( '1' , 'qux' ) ;");
		db.execute("INSERT INTO right VALUES ( '1' , 'quux' ) ;");
		db.execute("INSERT INTO right VALUES ( '1' , 'quuz' ) ;");
		db.execute("SELECT * INTO joined FROM left INNER JOIN right ON leftkey = rightkey ;");
		db.execute("SELECT * from joined ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nCREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nSELECT 9\n  A  | leftkey | rightkey |  B   \n-----+---------+----------+------\n bar | 1       | 1        | quux\n bar | 1       | 1        | quuz\n bar | 1       | 1        | qux\n baz | 1       | 1        | quux\n baz | 1       | 1        | quuz\n baz | 1       | 1        | qux\n foo | 1       | 1        | quux\n foo | 1       | 1        | quuz\n foo | 1       | 1        | qux\n(9 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test22() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE left ( A varchar , leftkey varchar ) ;");
		db.execute("CREATE TABLE right ( rightkey varchar , B varchar ) ;");
		db.execute("INSERT INTO left VALUES ( 'foo', '1' ) ;");
		db.execute("INSERT INTO left VALUES ( 'bar', '2' ) ;");
		db.execute("INSERT INTO left VALUES ( 'baz', '3' ) ;");
		db.execute("INSERT INTO right VALUES ( '1' , 'qux' ) ;");
		db.execute("INSERT INTO right VALUES ( '2' , 'quux' ) ;");
		db.execute("INSERT INTO right VALUES ( '3' , 'quuz' ) ;");
		db.execute("SELECT * INTO joined FROM left INNER JOIN right ON leftkey = rightkey ;");
		db.execute("SELECT * from joined ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nCREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nSELECT 3\n  A  | leftkey | rightkey |  B   \n-----+---------+----------+------\n bar | 2       | 2        | quux\n baz | 3       | 3        | quuz\n foo | 1       | 1        | qux\n(3 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test23() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE left ( A varchar , leftkey varchar ) ;");
		db.execute("CREATE TABLE right ( rightkey varchar , B varchar ) ;");
		db.execute("INSERT INTO left VALUES ( 'foo', '1' ) ;");
		db.execute("INSERT INTO left VALUES ( 'bar', '1' ) ;");
		db.execute("INSERT INTO left VALUES ( 'baz', '3' ) ;");
		db.execute("INSERT INTO right VALUES ( '1' , 'qux' ) ;");
		db.execute("INSERT INTO right VALUES ( '2' , 'quux' ) ;");
		db.execute("INSERT INTO right VALUES ( '3' , 'quuz' ) ;");
		db.execute("SELECT * FROM left ;");
		db.execute("SELECT * FROM right ;");
		db.execute("SELECT * INTO joined FROM left INNER JOIN right ON leftkey = rightkey ;");
		db.execute("SELECT * from joined ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nCREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\n  A  | leftkey \n-----+---------\n bar | 1\n baz | 3\n foo | 1\n(3 rows)\n\n rightkey |  B   \n----------+------\n 1        | qux\n 2        | quux\n 3        | quuz\n(3 rows)\n\nSELECT 3\n  A  | leftkey | rightkey |  B   \n-----+---------+----------+------\n bar | 1       | 1        | qux\n baz | 3       | 3        | quuz\n foo | 1       | 1        | qux\n(3 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test24() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE ta ( taname varchar , username varchar , course varchar ) ;");
		db.execute("CREATE TABLE teaching ( teachername varchar , coursename varchar ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Ivan Mladenov' , 'ivml' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Florin Akermann' , 'flak' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Astrid Neumann' , 'astn' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Vlad Limbean' , 'vlli' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Jens Rømer Hesselbjerg' , 'jenr' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Alf Andersen' , 'alfe' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Jacob Harder' , 'jhar' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Mads Frederik Madsen' , 'mfrm' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO ta VALUES ( 'Roman Novosad' , 'romn' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO teaching VALUES ( 'Troels Bjerre Lund' , 'Introductory Programming' ) ;");
		db.execute("INSERT INTO teaching VALUES ( 'Patrick Bahr' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO teaching VALUES ( 'Jens Kötters' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO teaching VALUES ( 'Bassel Mannaa' , 'Discrete Mathematics' ) ;");
		db.execute("INSERT INTO teaching VALUES ( 'Agata Murawska' , 'Discrete Mathematics' ) ;");
		db.execute("SELECT * FROM ta ;");
		db.execute("SELECT * FROM teaching ;");
		db.execute("SELECT * INTO joined FROM teaching INNER JOIN ta ON coursename = course ;");
		db.execute("SELECT teachername AS Gru , taname AS minion INTO despicableme FROM joined ;");
		db.execute("SELECT * from despicableme ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nCREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\n         taname         | username |          course          \n------------------------+----------+--------------------------\n Alf Andersen           | alfe     | Introductory Programming\n Astrid Neumann         | astn     | Introductory Programming\n Florin Akermann        | flak     | Introductory Programming\n Ivan Mladenov          | ivml     | Introductory Programming\n Jacob Harder           | jhar     | Discrete Mathematics\n Jens Rømer Hesselbjerg | jenr     | Introductory Programming\n Mads Frederik Madsen   | mfrm     | Discrete Mathematics\n Roman Novosad          | romn     | Discrete Mathematics\n Vlad Limbean           | vlli     | Introductory Programming\n(9 rows)\n\n    teachername     |        coursename        \n--------------------+--------------------------\n Agata Murawska     | Discrete Mathematics\n Bassel Mannaa      | Discrete Mathematics\n Jens Kötters       | Discrete Mathematics\n Patrick Bahr       | Discrete Mathematics\n Troels Bjerre Lund | Introductory Programming\n(5 rows)\n\nSELECT 18\nSELECT 18\n        Gru         |         minion         \n--------------------+------------------------\n Agata Murawska     | Jacob Harder\n Agata Murawska     | Mads Frederik Madsen\n Agata Murawska     | Roman Novosad\n Bassel Mannaa      | Jacob Harder\n Bassel Mannaa      | Mads Frederik Madsen\n Bassel Mannaa      | Roman Novosad\n Jens Kötters       | Jacob Harder\n Jens Kötters       | Mads Frederik Madsen\n Jens Kötters       | Roman Novosad\n Patrick Bahr       | Jacob Harder\n Patrick Bahr       | Mads Frederik Madsen\n Patrick Bahr       | Roman Novosad\n Troels Bjerre Lund | Alf Andersen\n Troels Bjerre Lund | Astrid Neumann\n Troels Bjerre Lund | Florin Akermann\n Troels Bjerre Lund | Ivan Mladenov\n Troels Bjerre Lund | Jens Rømer Hesselbjerg\n Troels Bjerre Lund | Vlad Limbean\n(18 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test25() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE customers ( ID varchar, CustomerName varchar, ContactName varchar, Address varchar, City varchar, PostalCode varchar, Country varchar ) ;");
		db.execute("INSERT INTO customers VALUES ( '1' , 'Alfreds Futterkiste' , 'Maria Anders' , 'Obere Str. 57' , 'Berlin' , '12209' , 'Germany' ) ;");
		db.execute("INSERT INTO customers VALUES ( '2' , 'Ana Trujillo Emparedados y helados' , 'Ana Trujillo' , 'Avda. de la Constitución 2222' , 'México D.F.' , '05021' , 'Mexico' ) ;");
		db.execute("INSERT INTO customers VALUES ( '3' , 'Antonio Moreno Taquería' , 'Antonio Moreno' , 'Mataderos 2312' , 'México D.F.' , '05023' , 'Mexico' ) ;");
		db.execute("SELECT ID AS ID , CustomerName AS Customer INTO temp FROM customers ;");
		db.execute("SELECT * FROM temp ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nSELECT 3\n ID |              Customer              \n----+------------------------------------\n 1  | Alfreds Futterkiste\n 2  | Ana Trujillo Emparedados y helados\n 3  | Antonio Moreno Taquería\n(3 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test26() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE customers ( ID varchar, CustomerName varchar, ContactName varchar, Address varchar, City varchar, PostalCode varchar, Country varchar ) ;");
		db.execute("INSERT INTO customers VALUES ( '1' , 'Alfreds Futterkiste' , 'Maria Anders' , 'Obere Str. 57' , 'Berlin' , '12209' , 'Germany' ) ;");
		db.execute("INSERT INTO customers VALUES ( '2' , 'Ana Trujillo Emparedados y helados' , 'Ana Trujillo' , 'Avda. de la Constitución 2222' , 'México D.F.' , '05021' , 'Mexico' ) ;");
		db.execute("INSERT INTO customers VALUES ( '3' , 'Antonio Moreno Taquería' , 'Antonio Moreno' , 'Mataderos 2312' , 'México D.F.' , '05023' , 'Mexico' ) ;");
		db.execute("CREATE TABLE orders ( OrderID varchar, CustomerID varchar, EmployeeID varchar, OrderDate varchar, ShipperID varchar ) ;");
		db.execute("INSERT INTO orders VALUES ( '10308' , '2' , '7' , '1996-09-18' , '3' ) ;");
		db.execute("INSERT INTO orders VALUES ( '10309' , '37' , '3' , '1996-09-19' , '1' ) ;");
		db.execute("INSERT INTO orders VALUES ( '10310' , '77' , '8' , '1996-09-20' , '2' ) ;");
		db.execute("SELECT * FROM orders ;");
		db.execute("SELECT * FROM customers ;");
		db.execute("SELECT * INTO joined FROM orders INNER JOIN customers ON CustomerID = ID ;");
		db.execute("SELECT * FROM joined ;");
		db.execute("SELECT OrderID AS OrderID , CustomerName AS CustomerName INTO result FROM joined ;");
		db.execute("SELECT * FROM result ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nCREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\n OrderID | CustomerID | EmployeeID | OrderDate  | ShipperID \n---------+------------+------------+------------+-----------\n 10308   | 2          | 7          | 1996-09-18 | 3\n 10309   | 37         | 3          | 1996-09-19 | 1\n 10310   | 77         | 8          | 1996-09-20 | 2\n(3 rows)\n\n ID |            CustomerName            |  ContactName   |            Address            |    City     | PostalCode | Country \n----+------------------------------------+----------------+-------------------------------+-------------+------------+---------\n 1  | Alfreds Futterkiste                | Maria Anders   | Obere Str. 57                 | Berlin      | 12209      | Germany\n 2  | Ana Trujillo Emparedados y helados | Ana Trujillo   | Avda. de la Constitución 2222 | México D.F. | 05021      | Mexico\n 3  | Antonio Moreno Taquería            | Antonio Moreno | Mataderos 2312                | México D.F. | 05023      | Mexico\n(3 rows)\n\nSELECT 1\n OrderID | CustomerID | EmployeeID | OrderDate  | ShipperID | ID |            CustomerName            | ContactName  |            Address            |    City     | PostalCode | Country \n---------+------------+------------+------------+-----------+----+------------------------------------+--------------+-------------------------------+-------------+------------+---------\n 10308   | 2          | 7          | 1996-09-18 | 3         | 2  | Ana Trujillo Emparedados y helados | Ana Trujillo | Avda. de la Constitución 2222 | México D.F. | 05021      | Mexico\n(1 row)\n\nSELECT 1\n OrderID |            CustomerName            \n---------+------------------------------------\n 10308   | Ana Trujillo Emparedados y helados\n(1 row)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test27() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE games ( ID varchar , GameName varchar , Price varchar , Genre varchar , YearOfRelease varchar , Developer varchar , Publisher varchar ) ;");
		db.execute("INSERT INTO games VALUES ( '1' , 'Wolfenstein 2: The New Colossus' , '60.0' , 'FPS' , '2017' , 'MachineGames' , 'Bethesda Softworks' ) ;");
		db.execute("INSERT INTO games VALUES ( '2' , 'Super Mario Odyssey' , '80.0' , 'Platformer' , '2017' , 'Nintendo' , 'Nintendo' ) ;");
		db.execute("INSERT INTO games VALUES ( '3' , 'South Park 2' , '60.0' , 'RPG' , '2017' , 'Ubisoft' , 'Ubisoft' ) ;");
		db.execute("INSERT INTO games VALUES ( '4' , 'Cuphead' , '15.0' , 'Platformer' , '2017' , 'Studio MDHR' , 'Studio MDHR' ) ;");
		db.execute("INSERT INTO games VALUES ( '5' , 'Age of Empires 2' , '12.0' , 'RTS' , '1999' , 'Ensemble Studios' , 'Microsoft' ) ;");
		db.execute("INSERT INTO games VALUES ( '6' , 'Company of Heroes' , '12.99' , 'RTS' , '2006' , 'Relic Entertainment' , 'THQ' ) ;");
		db.execute("INSERT INTO games VALUES ( '7' , 'Legend of Zelda: Breath of the Wild' , '60.0' , 'Adventure' , '2017' , 'Nintendo' , 'Nintendo' ) ;");
		db.execute("INSERT INTO games VALUES ( '8' , 'DOTA 2' , '0.0' , 'MOBA' , '2012' , 'Valve' , 'Valve' ) ;");
		db.execute("INSERT INTO games VALUES ( '9' , 'Shenzhen I/O' , '14.99' , 'Puzzle' , '2016' , 'Zach Barth' , 'Zach Barth' ) ;");
		db.execute("INSERT INTO games VALUES ( '10' , 'Papers, Please' , '9.99' , 'Puzzle' , '2013', '3909' , '3909' ) ;");
		db.execute("SELECT * FROM games ;");
		db.execute("CREATE TABLE sales ( SaleID varchar , GameID varchar , NewPrice varchar , FromDate varchar , ToDate varchar ) ;");
		db.execute("INSERT INTO sales VALUES ( '1' , '5' , '5.0' , '2017-20-10' , '2017-31-10' ) ;");
		db.execute("INSERT INTO sales VALUES ( '2' , '6' , '10.0' , '2017-01-10' , '2017-01-12' ) ;");
		db.execute("INSERT INTO sales VALUES ( '3' , '9' , '4.99' , '2017-10-10' , '2017-12-10' ) ;");
		db.execute("INSERT INTO sales VALUES ( '4' , '10' , '4.99' , '2017-24-12' , '2017-31-12' ) ;");
		db.execute("SELECT * FROM sales ;");
		db.execute("SELECT * INTO joined FROM sales INNER JOIN games ON GameID = ID ;");
		db.execute("SELECT * FROM joined ;");
		db.execute("SELECT GameName AS Name , NewPrice AS Price , FromDate AS From , ToDate AS To , Genre as Genre , YearOfRelease AS Year , Developer as Developer , Publisher as Publisher INTO gameSales FROM joined ;");
		db.execute("SELECT * FROM gameSales ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\n ID |              GameName               | Price |   Genre    | YearOfRelease |      Developer      |     Publisher      \n----+-------------------------------------+-------+------------+---------------+---------------------+--------------------\n 1  | Wolfenstein 2: The New Colossus     | 60.0  | FPS        | 2017          | MachineGames        | Bethesda Softworks\n 10 | Papers, Please                      | 9.99  | Puzzle     | 2013          | 3909                | 3909\n 2  | Super Mario Odyssey                 | 80.0  | Platformer | 2017          | Nintendo            | Nintendo\n 3  | South Park 2                        | 60.0  | RPG        | 2017          | Ubisoft             | Ubisoft\n 4  | Cuphead                             | 15.0  | Platformer | 2017          | Studio MDHR         | Studio MDHR\n 5  | Age of Empires 2                    | 12.0  | RTS        | 1999          | Ensemble Studios    | Microsoft\n 6  | Company of Heroes                   | 12.99 | RTS        | 2006          | Relic Entertainment | THQ\n 7  | Legend of Zelda: Breath of the Wild | 60.0  | Adventure  | 2017          | Nintendo            | Nintendo\n 8  | DOTA 2                              | 0.0   | MOBA       | 2012          | Valve               | Valve\n 9  | Shenzhen I/O                        | 14.99 | Puzzle     | 2016          | Zach Barth          | Zach Barth\n(10 rows)\n\nCREATE TABLE\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\n SaleID | GameID | NewPrice |  FromDate  |   ToDate   \n--------+--------+----------+------------+------------\n 1      | 5      | 5.0      | 2017-20-10 | 2017-31-10\n 2      | 6      | 10.0     | 2017-01-10 | 2017-01-12\n 3      | 9      | 4.99     | 2017-10-10 | 2017-12-10\n 4      | 10     | 4.99     | 2017-24-12 | 2017-31-12\n(4 rows)\n\nSELECT 4\n SaleID | GameID | NewPrice |  FromDate  |   ToDate   | ID |     GameName      | Price | Genre  | YearOfRelease |      Developer      | Publisher  \n--------+--------+----------+------------+------------+----+-------------------+-------+--------+---------------+---------------------+------------\n 1      | 5      | 5.0      | 2017-20-10 | 2017-31-10 | 5  | Age of Empires 2  | 12.0  | RTS    | 1999          | Ensemble Studios    | Microsoft\n 2      | 6      | 10.0     | 2017-01-10 | 2017-01-12 | 6  | Company of Heroes | 12.99 | RTS    | 2006          | Relic Entertainment | THQ\n 3      | 9      | 4.99     | 2017-10-10 | 2017-12-10 | 9  | Shenzhen I/O      | 14.99 | Puzzle | 2016          | Zach Barth          | Zach Barth\n 4      | 10     | 4.99     | 2017-24-12 | 2017-31-12 | 10 | Papers, Please    | 9.99  | Puzzle | 2013          | 3909                | 3909\n(4 rows)\n\nSELECT 4\n       Name        | Price |    From    |     To     | Genre  | Year |      Developer      | Publisher  \n-------------------+-------+------------+------------+--------+------+---------------------+------------\n Age of Empires 2  | 5.0   | 2017-20-10 | 2017-31-10 | RTS    | 1999 | Ensemble Studios    | Microsoft\n Company of Heroes | 10.0  | 2017-01-10 | 2017-01-12 | RTS    | 2006 | Relic Entertainment | THQ\n Papers, Please    | 4.99  | 2017-24-12 | 2017-31-12 | Puzzle | 2013 | 3909                | 3909\n Shenzhen I/O      | 4.99  | 2017-10-10 | 2017-12-10 | Puzzle | 2016 | Zach Barth          | Zach Barth\n(4 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	@org.junit.Test
	public void test28() {
		record.start();
		StringDB db;
		db = new StringDB();
		db.execute("CREATE TABLE test ( ID varchar , Test varchar ) ;");
		db.execute("INSERT INTO test VALUES ( '1' , 'Something is happening here' ) ;");
		db.execute("INSERT INTO test VALUES ( '2' , 'Something is happening here as well' ) ;");
		db.execute("SELECT * FROM test ;");
		db.execute("INSERT INTO test VALUES ( '3' , 'Oh my god what is happening' ) ;");
		db.execute("INSERT INTO test VALUES ( 'potato' , 'potato' ) ;");
		db.execute("INSERT INTO test VALUES ( 'I hope you learned' , 'to not use varchar for number ids' ) ;");
		db.execute("SELECT * FROM test ;");
		db.execute("SELECT Test as Note INTO result FROM test ;");
		db.execute("SELECT * FROM result ;");
		db.execute("DROP TABLE test ;");
		db.execute("SELECT * FROM result ;");
		org.junit.Assert.assertEquals("CREATE TABLE\nINSERT 0 1\nINSERT 0 1\n ID |                Test                 \n----+-------------------------------------\n 1  | Something is happening here\n 2  | Something is happening here as well\n(2 rows)\n\nINSERT 0 1\nINSERT 0 1\nINSERT 0 1\n         ID         |                Test                 \n--------------------+-------------------------------------\n 1                  | Something is happening here\n 2                  | Something is happening here as well\n 3                  | Oh my god what is happening\n I hope you learned | to not use varchar for number ids\n potato             | potato\n(5 rows)\n\nSELECT 5\n                Note                 \n-------------------------------------\n Oh my god what is happening\n Something is happening here\n Something is happening here as well\n potato\n to not use varchar for number ids\n(5 rows)\n\nDROP TABLE\n                Note                 \n-------------------------------------\n Oh my god what is happening\n Something is happening here\n Something is happening here as well\n potato\n to not use varchar for number ids\n(5 rows)\n\n", record.stop().replaceAll("\r",""));
	}
	static class RecordingOutputStream extends java.io.OutputStream {
		private final java.io.OutputStream out;
		private byte[] buffer = new byte[1 << 10];
		private int size = -1;

		public RecordingOutputStream(java.io.OutputStream out) {
			this.out = out;
		}

		@Override
		public void write(int b) throws java.io.IOException {
			if (size >= 0) {
				if (size == buffer.length) {
					buffer = java.util.Arrays.copyOf(buffer, size * 2);
				}
				buffer[size++] = (byte) b;
			} else {
				out.write(b);
			}
		}

		public void start() {
			size = 0;
		}

		public String stop() {
			String retval = new String(buffer, 0, size);
			size = -1;
			return retval;
		}
	}
	public static void main(String[] args) {
		org.junit.runner.Result result = org.junit.runner.JUnitCore.runClasses(StringDBTest.class);
		for (org.junit.runner.notification.Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
			break;
		}
		System.out.printf("Tests run: %d,  Failures: %d\n", result.getRunCount(), result.getFailureCount());
	}
}
