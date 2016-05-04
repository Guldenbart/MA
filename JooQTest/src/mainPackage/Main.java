package mainPackage;

//For convenience, always static import your generated tables and
//jOOQ functions to decrease verbosity:
import static jooq.generated.Tables.*;
import java.sql.*;

import org.jooq.*;
import org.jooq.impl.*;

import jooq.generated.tables.Author;
import jooq.generated.tables.Book;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String userName = "root";
		String password = "r00t$toGrow";
		String url = "jdbc:mysql://localhost:3306/library";
		
		// Connection is the only JDBC resource that we need
		// PreparedStatement and ResultSet are handled by jOOQ, internally
		try (Connection conn = DriverManager.getConnection(url, userName, password)) {
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL); // Typ von create: 'DefaultDSLContext'
			
			/*
			 * select: org.jooq.impl.DefaultDSLContext.java
			 * 		line. 1430
			 * from: 	SelectIntoStep extends SelectFromStep
			 * 			SelectDistinctOnStep extends SelectIntoStep
			 * 			SelectSelectStep extends SelectDistinctOnStep
			 * 			
			 */
			Result<Record> result1 = create.select().from(AUTHOR).fetch();
		
			for (Record r : result1) {
				Integer id = r.getValue(AUTHOR.ID);
				String firstName = r.getValue(AUTHOR.FIRST_NAME);
				String lastName = r.getValue(AUTHOR.LAST_NAME);
		
				System.out.println("ID: " + id + " first name: " + firstName + ", last name: " + lastName);
			}
			
			Author a = AUTHOR.as("a");
			Book b = BOOK.as("b");
			
			Result<Record1<String>> result2 = create.select(BOOK.TITLE)
					.from(BOOK)
					.join(AUTHOR).using(BOOK.ID)
					.where(AUTHOR.LAST_NAME.equal("Ende"))
					.fetch();
			
			for (Record r : result2) {
				String title = r.getValue(BOOK.TITLE);
		
				System.out.println(" Titel: " + title);
			}
		} 
		
		// For the sake of this tutorial, let's keep exception handling simple
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}