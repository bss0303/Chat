package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Message;
import bean.Room;
import bean.User;
import dao.MessDao;
import dao.RoomDao;
import dao.UserDao;
import util.StringUtil;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao udao = new UserDao();
	RoomDao rdao = new RoomDao();
	MessDao mdao = new MessDao();
	
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String method = req.getParameter("method");
		if("login".equals(method)) {
			login(req, resp);
		}else if("list".equals(method)) {
			list(req, resp);
		}else if("send".equals(method)) {
			send(req, resp);
		}else if("get".equals(method)) {
			get(req, resp);
		}else if("change".equals(method)) {
			change(req, resp);
		}
	}
	
	//登录 ok
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String username = req.getParameter("username");
		String pwd = req.getParameter("pwd");
		User us =  udao.getUserByName(username);
		
		if(us == null || us.getPrivilege() != 0 || !us.getPwd().equals(pwd)) {
			req.setAttribute("message", "账号或者密码错误");
			req.getRequestDispatcher("user/login.jsp").forward(req, resp);
		}
		else{
			HttpSession session = req.getSession();
			session.setAttribute("user", us);
			resp.sendRedirect("user/user.jsp");
		}
	}
	
	//显示所有房间
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8;");
		int id = Integer.valueOf(req.getParameter("id"));
		List<Room> rm = rdao.getRoomsByUserId(id);
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		
		for(int i=0; i<rm.size(); i++) {
			String name = rm.get(i).getRname();
			
			out.println("<div class=\"roms\" onclick=\"getNowRoom(this)\">");
			out.println("<span>"+ name +"</span>");
			out.println("</div>");
			session.setAttribute(name, 0);
		}
	}
	
	//发送消息
	//增 ok
	private void send(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		String rname = req.getParameter("rname");
		int rid = rdao.getRoomByName(rname).getRid();
		String time = req.getParameter("time");
 		int id = Integer.valueOf(req.getParameter("id"));
		String input = StringUtil.filterHtml(req.getParameter("input"));
		Message mess = new Message(rid, id, time, input);

		PrintWriter out = resp.getWriter();
		if(mdao.add(mess)) {
			out.print("发送成功");
		}else {
			out.print("发送失败");
		}
	}
	
	//询问新消息
	private void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		HttpSession session = req.getSession();
		
		String rname = req.getParameter("rname");
		int rid = rdao.getRoomByName(rname).getRid();
		int lastreid = (int)session.getAttribute(rname);
		int reid = mdao.getCount(rid);
		
		if(reid > lastreid) {
			session.setAttribute(rname, reid);
			PrintWriter out = resp.getWriter();
			
			List<Message> mess = mdao.getAll(rid, lastreid);
			for(int i=0; i<mess.size(); i++) {
				String time = mess.get(i).getTime();
				String username = udao.getUserById((mess.get(i).getId())).getUsername();
				String input = mess.get(i).getMessage();
				out.print("<div class=\"m1\">");
				out.print("<span class=\"time\">"+time+"</span> | <span class=\"name\">"+username+"</span>");
				out.print("<div class=\"m2\"><span class=\"mess\">"+input+"</span></div></div>");
			}
		}
	}
	
	//修改room的session为初始值
	private void change(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");

		String rname = String.valueOf(req.getParameter("rname"));
		HttpSession session = req.getSession();
		session.setAttribute(rname, 0);
	}
}
