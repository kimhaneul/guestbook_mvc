package kr.ar.sungkyul.guestbook.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ar.sungkyul.guestbook.dao.GuestbookDao;
import kr.ar.sungkyul.guestbook.vo.GuestbookVo;

@WebServlet("/ws")
public class guestbookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");

		if ("index".equals(actionName)) {
			GuestbookDao dao = new GuestbookDao();
			List<GuestbookVo> list = dao.getList();

			// request 범위(scope)에 list 객체를 저장
			request.setAttribute("list", list);

			// forwarding
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(request, response);

		} else if ("delete".equals(actionName)) {

			String no = request.getParameter("no");
			String password = request.getParameter("password");

			GuestbookDao dao = new GuestbookDao();

			dao.delete(no, password);
			response.sendRedirect("/guestbook_mvc/ws?a=index");

		} else if ("deleteform".equals(actionName)) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
			rd.forward(request, response);
		} else if ("add".equals(actionName)) {
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");

			GuestbookDao dao = new GuestbookDao();
			GuestbookVo vo = new GuestbookVo();

			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);

			dao.insert(vo);
			
			response.sendRedirect( "/guestbook_mvc/ws?a=index" );
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
