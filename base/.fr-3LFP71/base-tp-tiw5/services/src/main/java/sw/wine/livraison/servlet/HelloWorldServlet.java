package sw.wine.livraison.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void saveToSession(HttpServletRequest request) {

		request.getSession().setAttribute("testAttribute",
				request.getParameter("testparam"));

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String username = request.getParameter("username");

		response.getWriter().write(username + ":Hello World!");

	}

	public boolean authenticate() {

		return true;

	}

}
