public class Main {

    public static  void main(String[] args){
        //System.out.println("Please write an SQL command to be explained: ");

        StringDB Database = new StringDB();
        /*
        Database.execute("CREATE TABLE ta ( name varchar , username varchar , course varchar ) ;");
        Database.execute("INSERT INTO ta VALUES ( 'Ivan Mladenov' , 'ivml' , 'Introductory Programming' ) ;");
        Database.execute("INSERT INTO ta VALUES ( 'Florin Akermann' , 'flak' , 'Introductory Programming' ) ;");
        Database.execute("INSERT INTO ta VALUES ( 'Astrid Neumann' , 'astn' , 'Introductory Programming' ) ;");
        Database.execute("INSERT INTO ta VALUES ( 'Vlad Limbean' , 'vlli' , 'Introductory Programming' ) ;");
        Database.execute("INSERT INTO ta VALUES ( 'Jens Rømer Hesselbjerg' , 'jenr' , 'Introductory Programming' ) ;");
        Database.execute("INSERT INTO ta VALUES ( 'Alf Andersen' , 'alfe' , 'Introductory Programming' ) ;");
        Database.execute("SELECT * FROM ta ;");
        Database.execute("SELECT username AS secret_email , name AS alias INTO superheroes FROM ta ;");
        Database.execute("SELECT * FROM superheroes ;");
        */
        StringDB db = new StringDB();
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

    }
}

