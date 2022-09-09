//$Id$
package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LibraryDao {

	private static String jdbcURL = "jdbc:postgresql://localhost:5432/librarydb";
	private static String jdbcUsername = "saran-14815";
	private static String jdbcDriver = "org.postgresql.Driver";

	protected static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, "");
			System.out.println("Connection Established Successfully");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public static void insert(String table, Map<String, Object> data) {
		String INSERT = "INSERT INTO table_name columns VALUES ";
		PreparedStatement preparedStatement = null;
		try (Connection connection = getConnection();) {
			INSERT += generateQuestionMark(data.size());
			INSERT = INSERT.replace("table_name", table);

			String columns = "(";
			for (String str : data.keySet()) {
				columns += str + ",";
			}
			columns = columns.substring(0, columns.length() - 1);
			columns += ")";
			INSERT = INSERT.replace("columns", columns);
			int num = 1;
			System.out.println(INSERT);
			preparedStatement = connection.prepareStatement(INSERT);
			for (Object obj : data.values()) {
				if (obj instanceof Integer) {
					preparedStatement.setInt(num, (Integer) obj);

				} else if (obj instanceof String) {
					preparedStatement.setString(num, (String) obj);

				} else if (obj instanceof Date) {
					preparedStatement.setDate(num, (Date) obj);
				} else if (obj instanceof Boolean) {
					preparedStatement.setBoolean(num, (Boolean) obj);
				}
				num++;
			}

			int result = preparedStatement.executeUpdate();
			System.out.println(result > 0 ? "Data inserted Successfully " + table + " " : "Not inserted");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String generateQuestionMark(int size) {
		String result = "(?";
		while (size != 1) {
			result += ",?";
			size--;
		}
		return result + ");";
	}

	public static void update(String table, int id, Map<String, Object> data) {

		String UPDATE = "UPDATE table_name SET updateParams WHERE idParam=?;";
		PreparedStatement preparedStatement = null;
		try (Connection connection = getConnection();) {

			UPDATE = UPDATE.replace("table_name", table);

			String updateParams = "";
			for (String str : data.keySet()) {
				updateParams += str + "=? ,";
			}
			updateParams = updateParams.substring(0, updateParams.length() - 1);
			UPDATE = UPDATE.replace("updateParams", updateParams);
			String idParam = table.toLowerCase().equals("book") ? "isbn" : table.toLowerCase().equals("users") ? "id" : "reportid";
			UPDATE = UPDATE.replace("idParam", idParam);
			System.out.println(UPDATE);
			preparedStatement = connection.prepareStatement(UPDATE);
			int num = 1;
			for (Object obj : data.values()) {
				if (obj instanceof Integer) {
					preparedStatement.setInt(num, (Integer) obj);

				} else if (obj instanceof String) {
					preparedStatement.setString(num, (String) obj);

				} else if (obj instanceof Date) {
					preparedStatement.setDate(num, (Date) obj);
				} else if (obj instanceof Boolean) {
					preparedStatement.setBoolean(num, (Boolean) obj);
				}
				num++;
			}
			System.out.println(num);
			preparedStatement.setInt(num, id);
			int result = preparedStatement.executeUpdate();
			System.out.println(result > 0 ? "Data updated Successfully " + table + " " : "Not updated");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Object> select(String table, int id) {

		String SELECT = "SELECT * FROM table_name WHERE idParam=?";
		Connection connection = getConnection();
		SELECT = SELECT.replace("table_name", table);
		String idParam = table.toLowerCase().equals("book") ? "isbn" : table.toLowerCase().equals("users") ? "id" : "reportid";
		SELECT = SELECT.replace("idParam", idParam);

		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next())
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
					if(!(rsmd.getColumnName(i).contains("username") || rsmd.getColumnName(i).contains("password")))
						result.put(rsmd.getColumnName(i), rs.getObject(i));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void delete(String table, int id) {

		String DELETE = "DELETE FROM table_name WHERE idParam=?;";
		DELETE = DELETE.replace("table_name", table);
		String idParam = table.toLowerCase().equals("book") ? "isbn" : table.toLowerCase().equals("users") ? "id" : "reportid";
		DELETE = DELETE.replace("idParam", idParam);
		PreparedStatement preparedStatement = null;
		try (Connection connection = getConnection();) {

			preparedStatement = connection.prepareStatement(DELETE);
			preparedStatement.setInt(1, id);
			int result = preparedStatement.executeUpdate();
			System.out.println(result > 0 ? "ID: " + id + " Deleted Successfully" : "Error Occured While Deleting");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/*
	 * public static List<?> selectAll(String table, int role){
	 * 
	 * String SELECT_ALL = "SELECT * FROM table_name "; int tableNum = table.equalsIgnoreCase("users") ? 0 : table.equalsIgnoreCase("book") ? 1 : 2; SELECT_ALL = SELECT_ALL.replace("table_name",
	 * table); Connection connection = getConnection();
	 * 
	 * switch(tableNum) { case 0: List<User> users = new ArrayList<User>(); SELECT_ALL += (role != -1) ? "WHERE role=?;" : ";"; try { PreparedStatement preparedStatement =
	 * connection.prepareStatement(SELECT_ALL); if(role != -1) preparedStatement.setInt(1, role); ResultSet rs = preparedStatement.executeQuery(); while(rs.next()) { User user = new User();
	 * user.setId(rs.getInt(1)); user.setFirstname(rs.getString(2)); user.setLastname(rs.getString(3)); user.setPhone(rs.getString(4)); user.setEmail(rs.getString(5));
	 * user.setUsername(rs.getString(6)); user.setPassword(rs.getString(7)); user.setDueAmount(rs.getInt(8)); user.setBanned(rs.getBoolean(9)); user.setRole(role); users.add(user); } return users; }
	 * catch (SQLException e) { e.printStackTrace(); } break; case 1: List<Book> books = new ArrayList<Book>(); SELECT_ALL += ";"; try { PreparedStatement preparedStatement =
	 * connection.prepareStatement(SELECT_ALL); ResultSet rs = preparedStatement.executeQuery(); while(rs.next()) { Book book = new Book(); book.setISBN(rs.getInt(1));
	 * book.setBookTitle(rs.getString(2)); book.setBookPrice(rs.getInt(3)); book.setCategory(rs.getString(4)); book.setEdition(rs.getString(5)); book.setAuthorName(rs.getString(6));
	 * book.setPublisherId(rs.getInt(7)); book.setIssueInfo(rs.getString(8)); book.setPublishedYear(rs.getInt(9)); book.setStatus(rs.getString(10)); books.add(book); } return books; } catch
	 * (SQLException e) { e.printStackTrace(); }
	 * 
	 * break; case 2: List<Report> reports = new ArrayList<Report>(); SELECT_ALL += ";"; try { PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL); ResultSet rs =
	 * preparedStatement.executeQuery(); while(rs.next()) { Report report = new Report(); report.setReportId(rs.getInt(1)); report.setReportId(rs.getInt(2)); report.setReportId(rs.getInt(3));
	 * report.setReserveDate(rs.getDate(4)); report.setReturnDate(rs.getDate(5)); reports.add(report); } return reports; } catch (SQLException e) { e.printStackTrace(); } break; } return null; }
	 */

	public static List<Map<String, Object>> selectAll(String table) {
		String SELECT_ALL = "SELECT * FROM table_name;";
		List<Map<String, Object>> data = new ArrayList<>();
		SELECT_ALL = SELECT_ALL.replace("table_name", table);
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
			ResultSet rs = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				Map<String, Object> row = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
					if(!(rsmd.getColumnName(i).contains("username") || rsmd.getColumnName(i).contains("password")))
						row.put(rsmd.getColumnName(i), rs.getObject(i));
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

//	public static List<Book> noOfBooksPublishedByPublisher(int id) {
//		String query = "SELECT * FROM Book where publisherid=?;";
//		List<Book> booksPublishedByHim = new ArrayList<Book>();
//		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {
//			preparedStatement.setInt(1, id);
//			ResultSet rs = preparedStatement.executeQuery();
//			while (rs.next()) {
//				Book book = new Book();
//				book.setISBN(rs.getInt(1));
//				book.setBookTitle(rs.getString(2));
//				book.setBookPrice(rs.getInt(3));
//				book.setCategory(rs.getString(4));
//				book.setEdition(rs.getString(5));
//				book.setAuthorName(rs.getString(6));
//				book.setPublisherId(rs.getInt(7));
//				book.setIssueInfo(rs.getString(8));
//				book.setPublishedYear(rs.getInt(9));
//				book.setListOfReaders(rs.getString(10));
//				booksPublishedByHim.add(book);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return booksPublishedByHim;
//	}

	public static int getDueAmountOftheReader(int id) {
		int result = -1;
		String query = "SELECT dueamount from users where id=?";
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				result = rs.getInt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<Map<String, Object>> getReadersForParticularPublisher(int id) {
		String query = "SELECT listofreaders FROM book where publisherid=?";
		Set<Integer> set = new HashSet<>(); 
		List<Map<String, Object>> listOfReaders = new ArrayList<Map<String,Object>>();
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String[] list = rs.getString("listofreaders").split(",");
				System.out.println("list : "+ list);
				for(int i = 1;i < list.length;i++) {
					if(Util.checkInteger(list[i]) && set.add(Integer.parseInt(list[i]))) {
						listOfReaders.add(select("users", Integer.parseInt(list[i])));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(listOfReaders);
		return listOfReaders;
	}
	
//	public static List<Map<String, Object>> getReadersForParticularPublisher(int id) {
//		String query = "SELECT listofreaders FROM book where publisherid=?";
//		Set<Integer> set = new HashSet<>(); 
//		List<Map<String, Object>> listOfReaders = new ArrayList<Map<String,Object>>();
//		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//			preparedStatement.setInt(1, id);
//			ResultSet rs = preparedStatement.executeQuery();
//			while (rs.next()) {
//				String[] list = rs.getString("listofreaders").split(",");
//				System.out.println("list : "+ list);
//				for(int i = 1;i < list.length;i++) {
//					if(Util.checkInteger(list[i]) && set.add(Integer.parseInt(list[i]))) {
//						listOfReaders.add(select("users", Integer.parseInt(list[i])));
//					}
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return listOfReaders;
//	}

	// public static List<Map<String, Object>> getBannedReaders(){
	//
	// List<User> bannedReaders = new ArrayList<>();
	// String query = "SELECT * from users where isbanned=true and role=2;";
	// try(Connection connection = getConnection();
	// PreparedStatement preparedStatement = connection.prepareStatement(query)){
	// ResultSet rs = preparedStatement.executeQuery();
	//
	// while(rs.next()) {
	// User user = new User();
	// user.setId(rs.getInt(1));
	// user.setFirstname(rs.getString(2));
	// user.setLastname(rs.getString(3));
	// user.setPhone(rs.getString(4));
	// user.setEmail(rs.getString(5));
	// user.setUsername(rs.getString(6));
	// user.setPassword(rs.getString(7));
	// user.setDueAmount(rs.getInt(8));
	// user.setBanned(rs.getBoolean(9));
	// user.setRole(rs.getInt(10));
	// bannedReaders.add(user);
	//
	// }
	//
	// }
	// catch(SQLException e) {
	// e.printStackTrace();
	// }
	// return bannedReaders;
	// }

	public static void reserveBook(int isbn, int id) {
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("isbn", isbn);
		data.put("readerid", id);

		Date reserveDate = new Date(System.currentTimeMillis());
		data.put("reserve", reserveDate);
		Long duration = 5 * 24 * 60 * 60 * 1000L;
		Date returndate = new Date(System.currentTimeMillis() + duration);
		data.put("return", returndate);
		System.out.println(data);
		insert("report", data);
		data.clear();
		data.put("listofreaders", "CONCAT(listofreaders,\",\"," + id + ")");
		update("book", isbn, data);
		System.out.println("Successfully Reserved a Book");

	}
	
	

	public static void returnBook(int reportid) {
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("isreturned", true);
		update("report", reportid, data);
		System.out.println("Successfully returned a Book");
	}

	public static void banReaders() {
		String query = "UPDATE users set isbanned=true where dueamount>=10000 and role=2 and isbanned=false;";
		try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
			int result = statement.executeUpdate(query);
			if (result > 0) {
				System.out.println("Some readers are banned");
			} else {
				System.out.println("Recently, No one marked as banned");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addDueAmount() {
		String query = "select id,return from users u join report r on u.id = r.readerid where isreturned=false and isbanned=false and return < ?;";
		
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Date date = rs.getDate("return");
				long millis = date.getTime();
				int noOfDays = (int)((System.currentTimeMillis() - millis)/(24 * 60 * 60 * 1000L));
				int dueamount = noOfDays * 1000;
				String updateQuery = "UPDATE users set dueamount=dueamount+? where id=?;";
				PreparedStatement ps = connection.prepareStatement(updateQuery);
				ps.setInt(1, dueamount);
				ps.setInt(2, rs.getInt("id"));
				ps.executeUpdate();
				
			}
			banReaders();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}

	public static List<Map<String, Object>> selectBasedCondition(String table, Map<String, Object> map) {
		String query = "SELECT * FROM table_name WHERE ";
		query = query.replace("table_name", table);
		int size = map.size();
		for (String str : map.keySet()) {
			query += str + "=" + map.get(str);
			size--;
			if (size > 0)
				query += " and ";
		}
		query += ";";
		List<Map<String, Object>> data = new ArrayList<>();
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet rs = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				Map<String, Object> row = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
					if(!(rsmd.getColumnName(i).contains("username") || rsmd.getColumnName(i).contains("password")))
						row.put(rsmd.getColumnName(i), rs.getObject(i));
				data.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;

	}
	
//
//	 public static void main(String[] args) {
//		 
//		 addDueAmount();
//	
//	 String table = "report";
//	
//	 Map<String, Object> map = new Map<String, Object>();
//	 map.put("firstname", "raj");
//	 map.put("lastname", "saran");
//	 map.put("phone", "9876543678");
//	 map.put("isbanned",true);
//	
//	 update(table, 2, map);
//	 Map<String, Object> result = select(table, 1);
//	 delete("book",1);
//	
//	 System.out.println(selectAll(table, -1)
//	 getReadersForParticularPublisher(2);
//	 reserveBook(2,2);
//	 }

}
