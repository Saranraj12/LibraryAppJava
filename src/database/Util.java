//$Id$
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Util {

	public static int[] login(String username, String password) {
		String query = "SELECT * FROM users WHERE username=? and password=? and isbanned='false'";
		Connection connection = LibraryDao.getConnection();
		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			ResultSet result = preparedStatement.executeQuery();

			if (result.next()) {
				return new int[] { result.getInt("id"), result.getInt("role") };
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean validatePassword(String password) {
		if (password.length() >= 8 && password.length() <= 15) {
			boolean uppercase = false, lowercase = false, number = false,
					specialChar = false;
			for (int i = 0; i < password.length(); i++) {
				if (65 <= password.charAt(i) && password.charAt(i) <= 90)
					uppercase = true;
				else if (97 <= password.charAt(i) && password.charAt(i) <= 122)
					lowercase = true;
				else if (48 <= password.charAt(i) && password.charAt(i) <= 57)
					number = true;
				else
					specialChar = true;
			}
			return uppercase && lowercase && number && specialChar;
		}
		return false;
	}

	public static Map<String, Object> convertStringToHashMap(String queryString) {

		if (queryString == null)
			return null;
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String data[] = null;

		if (queryString.indexOf("&") == -1) {
			data = queryString.split("=");
			if (checkInteger(data[1]))
				map.put(data[0], Integer.parseInt(data[1]));
			else
				map.put(data[0], data[1]);
			return map;
		}
		for (String str : queryString.split("&")) {
			data = str.split("=");
			if (checkInteger(data[1]))
				map.put(data[0], Integer.parseInt(data[1]));
			else
				map.put(data[0], data[1]);
		}
		return map;
	}

	public static Map<String, Object> convertQueryMapToHashMap(Map<String, String[]> queryString) {

		if (queryString == null)
			return null;
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		for(Map.Entry<String, String[]> data : queryString.entrySet()) {
			if (checkInteger(data.getValue()[0]))
				map.put(data.getKey(),Integer.parseInt(data.getValue()[0]));
			else
				map.put(data.getKey(),data.getValue()[0]);
		}

		
		return map;
	}

	public static boolean checkInteger(String str) {
		if (str == null)
			return false;

		try {
			Integer.parseInt(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
