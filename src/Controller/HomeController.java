package Controller;

import java.awt.SystemColor;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Core.Controller;
import Model.BackInfo;
import Model.ExportInfo;
import Model.GoodsInfo;
import Model.ImportInfo;
import Model.UserInfo;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/HomeController")
public class HomeController extends Controller {
//	private static final long serialVersionUID = 1L;
//	private HttpServletRequest myRequest;
//	private HttpServletResponse myResponse;
//	private HttpSession mySession;
//	private String action;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");                  //request中文转码
		response.setContentType("text/html;charset=utf-8");     //response中文转码
		
		// 初始化常用的参数
		this.myRequest = request;
		this.myResponse = response;
		this.mySession = request.getSession();
		
		System.out.println("进入HomeController成功！");
		
		this.action = request.getParameter("a") == null ? "Index" : request.getParameter("a");
		
		System.out.println("action="+action);
		System.out.println("action End");
		if (action.equals("CheckLogin")) { 
			// 【调用检查登录Action】
			CheckLoginAction();
		}else if(action.equals("Index") && IsLogin()){
			//【用户已经登录成功，直接进来，跳转员工主页】
			try {
				ShowUserInfoAction();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if(action.equals("updateStaffPwd") && IsLogin()){
			// 【调用修改员工密码Action】
			UpdateStaffPwdAction();
		}else if(action.equals("Exit") && IsLogin()){
			Exit();
		}else if(action.equals("UpdateStaffInfo") && IsLogin()){
			// 【调用修改员工基本信息Action】
			System.out.println("进入action.equals('UpdateStaffInfo')成功！");
			UpdateStaffInfoAction();
		}else if(action.equals("EnterGoodsExport") && IsLogin()){
			// 【调用进入商品销售页面Action】
			try {
				EnterGoodsExportAction();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else if(action.equals("SaleGoods") && IsLogin()){
			// 【调用购买商品Action】
			SaleGoodsAction();
		}else if(action.equals("SaleBack") && IsLogin()){
			// 【调用退货Action】
			SaleBackAction();
		}else if(action.equals("QueryExportInfo") && IsLogin()){
			// 【调用查询销售单号Action】
			QueryExportInfoAction();
		}else if(action.equals("EnterGoodsImport") && IsLogin()){
			// 【调用进入入货页面Action】
			try {
				EnterGoodsImportAction();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else if(action.equals("GoodsImport") && IsLogin()){
			// 【调用查询销售单号Action】
			try {
				GoodsImportAction();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else if(action.equals("GoodsBack") && IsLogin()){
			
			System.out.print("2222222222222");
			goodsBackAction();
		}else {
			// 【用户还没有登录直接进来，跳转登录页面】
			response.sendRedirect("./login.html");
			
		}
	}
	
	//退货处理
	public void goodsBackAction() throws ServletException, IOException{
		//跳转到员工主页
		RequestDispatcher dispatcher = myRequest.getRequestDispatcher("WEB-INF/View/goodsBack.jsp");
		dispatcher.forward(myRequest, myResponse);
	}
	
	// 检查登录信息Action
	public void CheckLoginAction() throws IOException {
		System.out.println("进入CheckLoginAction()成功！");
			String username = myRequest.getParameter("username");
			String password = myRequest.getParameter("password");

			String rst = new UserInfo().CheckLogin(username, password);
			System.out.println("数据访问完成！rst返回");
			
			if (rst == "success") {
				mySession.setAttribute("username", username);
				
				myResponse.getWriter().print("success");
			} else {
				myResponse.getWriter().print("error");
			}
		}
	
	// 通过查看session中的username是否为空，检查用户是否登陆
	public Boolean IsLogin() {
			if (mySession.getAttribute("username") == null) {
				return false;
			} else {
				return true;
			}
		}

	//展示员工主页信息Action
	public void ShowUserInfoAction() throws ParseException, ServletException, IOException{
		System.out.println("进入ShowUserInfoAction()成功！");
		String userNum=mySession.getAttribute("username").toString();
		
		UserInfo user=new UserInfo().getUserInfoByStuNum(userNum);
		System.out.println("数据访问成功，返回");
		
		if (user != null) {	
			mySession.setAttribute("truename", user.UserName);

			myRequest.setAttribute("user", user);
			// System.out.print(s.StuName);
		}
		//跳转到员工主页
		RequestDispatcher dispatcher = myRequest.getRequestDispatcher("WEB-INF/View/Home/Index.jsp");
		dispatcher.forward(myRequest, myResponse);
		
	}
    
	//修改员工密码Action
	public void UpdateStaffPwdAction() throws IOException{
		String username=mySession.getAttribute("username").toString();
		String oldPwd=myRequest.getParameter("oldPwd").toString();
		String newPwd=myRequest.getParameter("newPwd").toString();
		System.out.print("进入数据访问层访问数据！");
		String rst=new UserInfo().updateStaffPwd(username, oldPwd, newPwd);
		
		System.out.print("rst:"+rst);
		myResponse.getWriter().print(rst);
	}

	//修改员工基本信息Action
	public void UpdateStaffInfoAction() throws IOException{
		System.out.println("进入UpdateStaffInfoAction()成功！");
		
		String userName=myRequest.getParameter("UserName");
		String Gender=myRequest.getParameter("Gender");
		String Email=myRequest.getParameter("Email");
		String Phone=myRequest.getParameter("Phone");
		String userNum=mySession.getAttribute("username").toString();
		
		System.out.println(userName+Gender+Email+Phone+userNum);
		String rst=new UserInfo().updateStaffInfo(userName, Gender, Email, Phone, userNum);
		
		myResponse.getWriter().print(rst);
	}

	//进入商品销售页面Action
	public void EnterGoodsExportAction() throws ServletException, IOException, SQLException{
		System.out.println("进入EnterGoodsExportAction()成功！");
		ArrayList<GoodsInfo> goodsInfoArr=new GoodsInfo().getAllGoodsInfo();
		System.out.println("数据访问成功，返回");
		myRequest.setAttribute("goodsInfoArr", goodsInfoArr);
		//跳转到商品销售页
		RequestDispatcher dispatcher = myRequest.getRequestDispatcher("WEB-INF/View/goodsExport123.jsp");
		dispatcher.forward(myRequest, myResponse);
	}

	// 购买商品Action
	public void SaleGoodsAction(){
		System.out.println("进入SaleGoodsAction()成功！");
		
		String goodsNum = myRequest.getParameter("goodsNum");
		String goodsName = myRequest.getParameter("goodsName");
		String number =myRequest.getParameter("saleNumber");
	    String price = myRequest.getParameter("price");
		String userNum = (String) mySession.getAttribute("username");
		String userName = (String) mySession.getAttribute("truename");
		
		String rst=new ExportInfo().SaleGoods(userNum, userName, goodsNum, goodsName, number, price);
		System.out.println("数据访问成功，返回");
		try {
			myResponse.getWriter().print(rst);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	// 退货Action
	public void SaleBackAction(){
		System.out.println("进入SaleBackAction()成功！");
	
		String exportId = myRequest.getParameter("exportId");
		String goodsNum = myRequest.getParameter("goodsNum");
		String goodsName = myRequest.getParameter("goodsName");
		String customerPhone = myRequest.getParameter("customerPhone");
		String customerName = myRequest.getParameter("customerName");
		String description = myRequest.getParameter("description");
		
		String userNum = (String) mySession.getAttribute("username");
		String userName = (String) mySession.getAttribute("truename");
		
		String rst=new BackInfo().SaleBack(exportId, goodsNum, goodsName, customerName, customerPhone, description, userNum, userName);
		System.out.println("数据访问成功，返回");
		try {
			myResponse.getWriter().print(rst); //返回js
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	//查询销售单号Action
	public void QueryExportInfoAction() throws IOException{
		System.out.println("进入QueryExportInfo()成功！");
		String exportId = myRequest.getParameter("exportId");
		
		ExportInfo exportInfo=new ExportInfo().QueryExportInfoByExportId(exportId);
		
		System.out.println("数据访问成功，返回");
		if (exportInfo != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append("\"Total\"" + ":\"" + 1 + "\"");
			sb.append(",\"Row\":[");
			
			sb.append("{");
			sb.append("\"Id\"" + ":\"" + exportInfo.Id + "\",");
			sb.append("\"GoodsNum\"" + ":\"" + exportInfo.GoodsNum + "\",");
			sb.append("\"GoodsName\"" + ":\"" + exportInfo.GoodsName + "\",");
			sb.append("\"Number\"" + ":\"" + exportInfo.Number + "\",");
			sb.append("\"Price\"" + ":\"" + exportInfo.Price+ "\",");
			sb.append("\"UserNum\"" + ":\"" + exportInfo.UserNum + "\",");
			sb.append("\"UserName\"" + ":\"" + exportInfo.UserName + "\",");
			sb.append("\"SubTime\"" + ":\"" + exportInfo.SubTime + "\"");
			sb.append("}");
			
			sb.append("]");
			sb.append("}");
			myResponse.getWriter().print(sb.toString());
		/*	JsonConfig jsonConfig=new JsonConfig();
		    JSONArray jsonarray=JSONArray.fromObject(exportInfo,jsonConfig);
			myResponse.getWriter().print(exportInfo);
			System.out.println("3333333333333");*/
			/*System.out.println("id:"+exportInfo.Id);
			//声明JSONObject
			JSONObject json=new JSONObject();
			//传属性
			json=json.fromObject(exportInfo);*/
//			System.out.println(exportInfo.Id);
//			json.put("exportInfo",exportInfo);
//			json.put("s","123789");
//			json.put("t",123);
			
			//myResponse.getWriter().write(json.toString());
		}
		else{
			System.out.println("对象为空，单号不存在！");
			myResponse.getWriter().print("none");
		}
	}

	//进入入货页面Action
	public void EnterGoodsImportAction() throws SQLException, ServletException, IOException{
		ArrayList<GoodsInfo> goodsInfoArr=new GoodsInfo().getAllGoodsInfo();
		System.out.println("数据访问成功，返回");
		myRequest.setAttribute("goodsInfoArr", goodsInfoArr);
		//跳转到商品销售页
		RequestDispatcher dispatcher = myRequest.getRequestDispatcher("WEB-INF/View/Home/GoodsImport.jsp");
		dispatcher.forward(myRequest, myResponse);

	}

	
	//退出系统
	public void Exit(){
		mySession.setAttribute("username", null);
		
	}
	
	
	//确认入货Action
	public void GoodsImportAction() throws SQLException{
		System.out.println("进入GoodsImportAction()成功！");
		String goodsNum = myRequest.getParameter("goodsNum");
		System.out.println(goodsNum);
		String goodsName = myRequest.getParameter("goodsName");
		/*String providerName = myRequest.getParameter("providerName");*/
		String number = myRequest.getParameter("number");
		String price = myRequest.getParameter("price");
		
		String userNum = (String) mySession.getAttribute("username");
		String userName = (String) mySession.getAttribute("truename");
		
		String address = myRequest.getParameter("address");
		String phone = myRequest.getParameter("phone");
	
		//先根据商品编号来确定该商品的供货商名字
		String providerName=new ImportInfo().GetProviderNameByGooodsNum(goodsNum);
		System.out.println("数据访问成功，返回"+providerName);
		
		String rst=new ImportInfo().GoodsImport(goodsNum, goodsName, providerName, number, price, userNum, userName);
		System.out.println("数据访问成功，返回"+rst);
		try {
			myResponse.getWriter().print(rst);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
