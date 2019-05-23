package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Room;
import bean.User;
import dao.RUDao;
import dao.RoomDao;
import dao.UserDao;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao udao = new UserDao();
	RoomDao rdao = new RoomDao();
	RUDao rudao = new RUDao();
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		if("login".equals(method)) {
			login(req, resp);
		}else if("show".equals(method)) {
			show(req, resp);
		}else if("add".equals(method)) {
			add(req, resp);
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
		}else if("addUser".equals(method)) {
			addUser(req, resp);
		}else if("delUser".equals(method)) {
			delUser(req, resp);
		}
	}
	
	//登录 ok
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String username = req.getParameter("username");
		String pwd = req.getParameter("pwd");
		User us =  udao.getUserByName(username);
		
		if(us == null || us.getPrivilege() == 0 || !us.getPwd().equals(pwd)) {
			req.setAttribute("message", "登录失败.原因可能是：没有权限,账号或者密码错误");
			req.getRequestDispatcher("admin/login.jsp").forward(req, resp);
		}
		else{
			HttpSession session = req.getSession();
			session.setAttribute("admin", username);
			resp.sendRedirect("admin/admin.jsp");
		}
	}
	
	//增 ok
	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String rname = req.getParameter("rname");
		String cT = req.getParameter("cT");
		Room rm = rdao.getRoomByName(rname);
		
		if(rm != null) {
			req.setAttribute("message", "该房间名已被占用");
		}else {
			rm = new Room(rname, cT);
			if(rdao.add(rm)) {
				req.setAttribute("message", "添加成功");
			}else {
				req.setAttribute("message", "添加失败");
			}
		}
		req.getRequestDispatcher("AdminServlet?method=show&p=1").forward(req, resp);
	}
	//添加用户到房间,ajax
	private void addUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String input = req.getParameter("input");
		User us = null;
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		
		if(input.charAt(0) >='0' && input.charAt(0)<='9') {
			us = udao.getUserById(Integer.valueOf(input)); 
		}else {
			us = udao.getUserByName(input);
		}
		
		if(us == null) {
			out.print("用户不存在");
		}else {
			int rid = Integer.valueOf(req.getParameter("rid"));
			if(rudao.add(us.getId(), rid)) {
				out.println("添加成功");
			}else {
				out.println("添加失败");
			}
		}
	}
	
	//删 ok
	private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		int p = Integer.valueOf(req.getParameter("p"));
		
		if(rdao.delete(id)) {
			req.setAttribute("message", "删除成功");
		}else {
			req.setAttribute("message", "删除失败");
		}
		req.getRequestDispatcher("AdminServlet?method=show&p="+p).forward(req, resp);
	}
	
	//删除用户到房间,ajax
	private void delUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String input = req.getParameter("input");
		User us = null;
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		
		if(input.charAt(0) >='0' && input.charAt(0)<='9') {
			us = udao.getUserById(Integer.valueOf(input)); 
		}else {
			us = udao.getUserByName(input);
		}
		
		if(us == null) {
			out.print("用户不存在");
		}else {
			int rid = Integer.valueOf(req.getParameter("rid"));
			if(rudao.delete(us.getId(), rid)) {
				out.println("删除成功");
			}else {
				out.println("删除失败 或 房间没有该用户");
			}
		}
	}
	
	//改
	private void getOneById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		Room rm = rdao.getRoomById(id);
		
		req.setAttribute("rm", rm);

		req.getRequestDispatcher("admin/update.jsp").forward(req, resp);
	}
	
	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		String rname = req.getParameter("rname");
		String cT = req.getParameter("cT");
		Room rm = new Room(id, rname, cT);
		
		if(rdao.update(rm)) {
			req.setAttribute("message", "修改成功");
		}else {
			req.setAttribute("message", "修改失败");
		}
		
		req.getRequestDispatcher("AdminServlet?method=show&p=1").forward(req, resp);
	}
	
	//--查
	//显示某一页 ok
	private void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		int p = Integer.valueOf(req.getParameter("p"));
		int pc = (rdao.getCount()-1)/RoomDao.countOfOnePage + 1;
		List<Room> rm = rdao.list(p);
		
		req.setAttribute("page", p);
		req.setAttribute("pageCount", pc);
		req.setAttribute("rm", rm);
		req.setAttribute("method", "show");
		
		req.getRequestDispatcher("admin/show.jsp").forward(req, resp);
	}
	
	//根据 完整ID 搜索
	private void searchId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		int id = Integer.valueOf(req.getParameter("id"));
		List<Room> rm = new ArrayList<>();
		Room rm0 = rdao.getRoomById(id);
		
		if(rm0!=null) {
			rm.add(rm0);
			req.setAttribute("page", 1);
			req.setAttribute("pageCount", 1);
			req.setAttribute("rm", rm);
			
			req.getRequestDispatcher("admin/show.jsp").forward(req, resp);
		}else {
			req.setAttribute("message", "该用户不存在");
			req.getRequestDispatcher("AdminServlet?method=show&p=1").forward(req, resp);
		}
	}
	
	//根据 用户名 模糊搜索
	private void searchName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String rname = req.getParameter("name");
		int p = Integer.valueOf(req.getParameter("p"));
		int pc = (rdao.getSearchCount(rname)-1)/RoomDao.countOfOnePage + 1;
		List<Room> rm = rdao.search(rname, p);
		
		if(rm.size() == 0) {
			req.setAttribute("message", "该用户不存在");
			req.getRequestDispatcher("AdminServlet?method=show&p=1").forward(req, resp);
		}else {
			req.setAttribute("page", p);
			req.setAttribute("pageCount", pc);
			req.setAttribute("method", "searchName");
			req.setAttribute("rname", rname);
			req.setAttribute("rm", rm);
			
			req.getRequestDispatcher("admin/show.jsp").forward(req, resp);
		}
	}

}
