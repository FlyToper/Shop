package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import Core.DBOper;

//普通员工
public class UserInfo {
	public int id;
	public String UserNum;
	public String UserPwd;
	public String UserName;
	public String Email;
	public String Phone;
	public String Gender;
	
	public java.sql.Timestamp SubTime;
	public int DelFlag;
	public String State;
	public String Remark;
	
	//检查员工登录
	public  String CheckLogin(String username, String password){
		System.out.println("进入数据访问层CheckLogin(成功");
		String sql="select * from UserInfo where UserNum=? and UserPwd=? and DelFlag=0";
		String[] params={username,password};
		
		ResultSet rst = new DBOper().executeQuery(sql, params);
		System.out.print("1233");
		try {
			if(rst.next()){   
				return "success";
			}else{
				return "error";  
			}
		} catch (SQLException e) {
			return "error";
		}
	}

	//根据员工编号查询取得该员工的信息
	public UserInfo getUserInfoByStuNum(String UserNum) throws ParseException{
		System.out.println("进入数据访问层getStuInfoByStuNum(String UserNum)成功");
		UserInfo s = new UserInfo();
		
		String sql = "select * from UserInfo where DelFlag =0 and  UserNum =?";
		String[] params = {UserNum};
		
		ResultSet rst =  new DBOper().executeQuery(sql, params);
		
		try {
			if(rst.next()){
				//编号
				if(rst.getString(2) == null){
					s.UserNum="";
				}else{
					s.UserNum=rst.getString(2);
				}
				//姓名
				if(rst.getString(4) == null){
					s.UserName="";
				}else{
					s.UserName=rst.getString(4);
				}
				//邮箱
				if(rst.getString(5) == null){
					s.Email="";
				}else{
					s.Email=rst.getString(5);
				}
				//电话
				if(rst.getString(6) == null){
					s.Phone="";
				}else{
					s.Phone=rst.getString(6);
				}
				//性别
				if(rst.getString(7) == null){
					s.Gender="";
				}else{
					s.Gender=rst.getString(7);
				}
				//注册时间
				if(rst.getTimestamp(8) == null){

				}else{
					s.SubTime=rst.getTimestamp(8);
					System.out.println("SubTime:"+s.SubTime);
				}
				
			}else{
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	//修改员工密码
	public String updateStaffPwd(String userName, String oldPwd,String newPwd){
		System.out.println("进入数据访问层成功");
		String sql="Update UserInfo set UserPwd = ? where UserNum = ? and UserPwd=?";
		String[] params={newPwd,userName,oldPwd};
		
		int n = new DBOper().executeUpdate(sql, params);
		
		if(n>0){
			return "success";
		}else{
			return "error";
		}
	}
	
	//修改员工基本信息
	public String updateStaffInfo(String userName, String Gender,String Email, String Phone, String userNum){
		System.out.println("进入数据访问层updateStaffInfo(成功");
		
		String sql="Update UserInfo set UserName = ?, Gender=?, Email=?, Phone=? where UserNum = ?";
		String[] params={userName, Gender, Email, Phone, userNum};
		
		int n = new DBOper().executeUpdate(sql, params);
		
		if(n>0){
			return "success";
		}else{
			return "error";
		}
	}
}
