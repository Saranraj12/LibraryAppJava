package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.LibraryDao;
import database.Util;

@WebServlet("/v1/*")
public class SingleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pathInfo = request.getPathInfo();
		PrintWriter out = response.getWriter();
		Map<String, Object> data = null;
		String id = null;
		LibraryDao.addDueAmount();
		switch (pathInfo) {
		case "/book":
			id = request.getParameter("isbn");
			if (id != null) {
				out.write(gson.toJson(LibraryDao.select("book", Integer.parseInt(id))));
			} else
				out.write(gson.toJson(LibraryDao.selectAll("book")));
			break;

		case "/book/issues":

			List<Map<String, Object>> allBooks = LibraryDao.selectAll("book");

			allBooks.removeIf(book -> book.get("issueinfo") == null);
			out.write(gson.toJson(allBooks));
			break;
		case "/book/publisher":

			String pid = request.getParameter("publisherid");
			data = new LinkedHashMap<>();
			if (pid != null) {
				data.put("publisherid", Integer.parseInt(pid));
				out.write(gson.toJson(LibraryDao.selectBasedCondition("book", data)));
			}
			break;
		case "/book/publisher/reader":
			String pubid = request.getParameter("publisherid");
			System.out.println(pubid);
			if (pubid != null) {
				String readers = gson.toJson(LibraryDao.getReadersForParticularPublisher(Integer.parseInt(pubid)));
				out.write(readers);
			}
			break;
		case "/users":
			id = request.getParameter("id");
			String role = request.getParameter("role");
			data = new LinkedHashMap<String, Object>();
			if (id != null) {
				String readers = gson.toJson(LibraryDao.select("users", Integer.parseInt(id)));
				out.write(readers);
			} else if (role != null) {
				data.put("role", Integer.parseInt(role));
				data.put("isbanned", false);
				String readers = gson.toJson(LibraryDao.selectBasedCondition("users", data));
				out.write(readers);
			}
			break;
		case "/users/bannedreaders":
			data = new LinkedHashMap<String, Object>();
			data.put("isbanned", true);
			data.put("role", 2);
			String readers = gson.toJson(LibraryDao.selectBasedCondition("users", data));
			out.write(readers);
			break;
		case "/report":
			out.write(gson.toJson(LibraryDao.selectAll("report")));
			break;
		case "/emailcheck":
			String email = request.getParameter("email");
			data = new LinkedHashMap<String, Object>();
			if (email != null) {
				data.put("email", email);
				List<Map<String, Object>> list = LibraryDao.selectBasedCondition("users", data);
				out.write(list.size() == 0 ? "true" : "false");
			}
			break;
		case "/getpublisherids":
			data = new LinkedHashMap<String, Object>();
			data.put("role", 1);
			List<Map<String, Object>> publishers = LibraryDao.selectBasedCondition("users", data);
			List<Integer> pids = new ArrayList<>();
			for (Map<String, Object> publisher : publishers) {
				pids.add((int) publisher.get("id"));
			}
			out.write(pids.toString());
			break;
		case "/getreservedbooks":
			id = request.getParameter("id");
			if (id != null) {
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("readerid", Integer.parseInt(id));
				map.put("isreturned", false);
				System.out.println(gson.toJson(LibraryDao.selectBasedCondition("report", map)));
				out.write(gson.toJson(LibraryDao.selectBasedCondition("report", map)));
			}
			break;

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String pathInfo = request.getPathInfo();
		System.out.println(pathInfo);
		String isbn, id;
		LibraryDao.addDueAmount();
		switch (pathInfo) {

		case "/login":
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if (username != null && password != null) {
				int[] result = Util.login(username, password);
				if (result != null) {
					out.write(gson.toJson(LibraryDao.select("users", result[0])));
				} else {
					out.write("null");
				}
			} else {
				out.write("null");
			}
			break;
		case "/users":
		case "/book":
		case "/report":
			System.out.println("In signup");
			Map<String, String[]> query = request.getParameterMap();
			if (query != null) {
				Map<String, Object> data = Util.convertQueryMapToHashMap(query);
				LibraryDao.insert(pathInfo.substring(1), data);
				out.write("success");
			} else {
				out.write("null");
			}
			break;
		case "/users/reserve":
			isbn = request.getParameter("isbn");
			id = request.getParameter("id");
			if (isbn != null && id != null) {
				LibraryDao.reserveBook(Integer.parseInt(isbn), Integer.parseInt(id));
				out.write("success");
			} else {
				out.write("failure");
			}
			break;
		case "/users/return":
			isbn = request.getParameter("isbn");
			id = request.getParameter("id");
			if (isbn != null && id != null) {
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("isbn", Integer.parseInt(isbn));
				map.put("readerid", Integer.parseInt(id));
				map.put("isreturned", false);
				List<Map<String, Object>> data = LibraryDao.selectBasedCondition("report", map);
				System.out.println(data);
				if (data.size() > 0) {
					int reportid = (int) data.get(0).get("reportid");
					LibraryDao.returnBook(reportid);
					out.write("success");
				} else {
					out.write("failure");
				}
			}
			break;

		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LibraryDao.addDueAmount();
		if (request.getParameter("isbn") != null) {
			Map<String, Object> map = Util.convertQueryMapToHashMap(request.getParameterMap());
			LibraryDao.update("book", Integer.parseInt(request.getParameter("isbn")), map);
		}
		else {
			System.out.println("isbn value is null");
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pathInfo = request.getPathInfo();
		LibraryDao.addDueAmount();
		switch (pathInfo) {
		case "/book":
		case "/users":
			if (request.getParameter("id") != null)
				LibraryDao.delete(pathInfo.substring(1), Integer.parseInt(request.getParameter("id")));
			if (request.getParameter("isbn") != null)
				LibraryDao.delete(pathInfo.substring(1), Integer.parseInt(request.getParameter("isbn")));
			break;
		}
	}

}
