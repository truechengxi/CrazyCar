package com.CrazyCarServer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		JSONObject getJB = new JSONObject();
		getJB = Util.getMsgData(request);

		PrintWriter out = response.getWriter();
		JSONObject outJB = new JSONObject();
		JSONObject userInfoJB = new JSONObject();
		String token = null;
		if (getJB != null && getJB.containsKey("UserName") && getJB.containsKey("Password")
				&& isExistUser(getJB.getString("UserName"))) {
			String userName = getJB.getString("UserName");
			if (isPasswordRight(getJB.getString("UserName"), getJB.getString("Password"))) {
				outJB.put("code", 200);
				token = Util.JWT.createJWTById(Util.getDataByName(userName, "uid"));
				userInfoJB.put("name", getJB.getString("UserName"));
				userInfoJB.put("uid", Util.getDataByName(userName, "uid"));
				userInfoJB.put("aid", Util.getDataByName(userName, "aid"));
				userInfoJB.put("star", Util.getDataByName(userName, "star"));
			} else {
				outJB.put("code", 423);
			}
		} else {
			outJB.put("code", 404);
		}
		JSONObject jb = new JSONObject();
		jb.put("user_info", userInfoJB);
		JSONObject dataJB = new JSONObject();
		dataJB.put("user_info", userInfoJB);
		dataJB.put("token", token);

		outJB.put("data", dataJB);

		out.println(outJB.toString());
		out.flush();
		out.close();
	}

	private boolean isPasswordRight(String userName, String password) {
		String sql = "select user_password from all_user where user_name = " + "\"" + userName + "\";";
		String rs = Util.JDBC.executeSelectString(sql, "user_password");
		if (rs == null) {
			return false;
		} else {
			return rs.equals(password);
		}
	}

	private boolean isExistUser(String userName) {
		String sql = "select user_password from all_user where user_name = " + "\"" + userName + "\";";
		String rs = Util.JDBC.executeSelectString(sql, "user_password");
		return rs != null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
