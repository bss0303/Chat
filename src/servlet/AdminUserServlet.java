package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDao;
/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/AdminUserServlet")
public class AdminUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao udao = new UserDao();
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		if("add".equals(method)) {
			add(req, resp);
		}else if("show".equals(method)) {
			show(req, resp);
		}else if("del".equals(method)) {
			del(req, resp);
		}else if("gOBI".equals(method)) {
			getOneById(req, resp);
		}else if("update".equals(method)) {
			update(req, resp);
		}else if("searchId".equals(method)) {
			searchId(req, resp);
		}else if("searchName".equals(method)) {
			searchName(req, resp);
		}
	}
	
	//增 ok
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String username = req.getParameter("username");
		User us = udao.getUserByName(username);
		if(us != null) {
			req.setAttribute("message", "抱歉！该用户名已被占用");
		}else {
			String pwd = req.getParameter("pwd");
			int pri = Integer.valueOf(req.getParameter("pri"));
			us = new User(0, username, pwd, pri);
			if(udao.add(us)) {
				req.setAttribute("message", "添加成功");
			}else {
				req.setAttribute("message", "添加失败");
				
			}
		}
		req.getRequestDispatcher("AdminUserServlet?method=show&p=1").forward(req, resp);
	}

	//删 ok
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		int p = Integer.valueOf(req.getParameter("p"));
		
		if(udao.delete(id)) {
			req.setAttribute("message", "删除成功");
		}else {
			req.setAttribute("message", "删除失败");
		}
		req.getRequestDispatcher("AdminUserServlet?method=show&p="+p).forward(req, resp);
	}
	
	//改
	private void getOneById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		User us = udao.getUserById(id);
		
		req.setAttribute("us", us);

		req.getRequestDispatcher("admin/user/update.jsp").forward(req, resp);
	}
	
	//改
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		String username = req.getParameter("username");

		User us0 = udao.getUserByName(username);
		
		if(us0 != null && us0.getId() != id) {
			req.setAttribute("message", "抱歉！该用户名已被占用");
		}else {
			String pwd = req.getParameter("pwd");
			int pri = Integer.valueOf(req.getParameter("pri"));
			us0 = new User(id, username, pwd, pri);
			if(udao.update(us0)) {
				req.setAttribute("message", "修改成功");
			}else {
				req.setAttribute("message", "修改失败");
			}
		}
		req.getRequestDispatcher("AdminUserServlet?method=show&p=1").forward(req, resp);
	}

	//--查
	//显示某一页 ok
	private void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		int p = Integer.valueOf(req.getParameter("p"));
		int pc = (udao.getCount()-1)/UserDao.countOfOnePage + 1;
		List<User> us = udao.list(p);
		
		req.setAttribute("page", p);
		req.setAttribute("pageCount", pc);
		req.setAttribute("us", us);
		req.setAttribute("method", "show");
		
		req.getRequestDispatcher("admin/user/show.jsp").forward(req, resp);
	}
	
	//根据 完整ID 搜索
	private void searchId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		List<User> us = new ArrayList<>();
		User us0 = udao.getUserById(id);
		
		if(us0!=null) {
			us.add(us0);
			req.setAttribute("page", 1);
			req.setAttribute("pageCount", 1);
			req.setAttribute("us", us);
			
			req.getRequestDispatcher("admin/user/show.jsp").forward(req, resp);
		}else {
			req.setAttribute("message", "该用户不存在");
			req.getRequestDispatcher("AdminUserServlet?method=show&p=1").forward(req, resp);
		}
	}
	
	//根据 用户名 模糊搜索
	private void searchName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String username = req.getParameter("name");
		int p = Integer.valueOf(req.getParameter("p"));
		int pc = (udao.getSearchCount(username)-1)/UserDao.countOfOnePage + 1;
		List<User> us = udao.search(username, p);
		
		if(us.size() == 0) {
			req.setAttribute("message", "该用户不存在");
			req.getRequestDispatcher("AdminUserServlet?method=show&p=1").forward(req, resp);
		}else {
			req.setAttribute("page", p);
			req.setAttribute("pageCount", pc);
			req.setAttribute("method", "searchName");
			req.setAttribute("username", username);
			req.setAttribute("us", us);
			
			req.getRequestDispatcher("admin/user/show.jsp").forward(req, resp);
		}
	}
}
