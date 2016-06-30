package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Core.DBOper;

//商品信息
public class GoodsInfo {
	public int id;
	public String GoodsNum;//商品编号
	public String GoodsName;//商品名字
	public int CateId;//商品类型Id
	public String CateName;//商品类型名
	public int Price;
	public int TotalNumber;//商品库存量
	public int SaleNumber;//商品已经售出数量
	
	public java.sql.Timestamp SubTime;//提交时间
	public int DelFlag;
	public String State;
	public String Remark;
	public int IsSale;

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	//查询所有商品信息
	public ArrayList<GoodsInfo> getAllGoodsInfo() throws SQLException{
		System.out.println("进入数据访问层getAllGoodsInfo()成功");
		ArrayList<GoodsInfo> goodsInfoArr=new ArrayList<GoodsInfo>();
	
		String sql = "select * from GoodsInfo where DelFlag =0 order by GoodsNum desc";
		String params[]={};
		
		ResultSet rst = new DBOper().executeQuery(sql,params);
		
		while(rst.next()){
			GoodsInfo goodsInfo = new GoodsInfo();
			
			goodsInfo.GoodsNum=rst.getString(2);
			goodsInfo.GoodsName=rst.getString(3);
			goodsInfo.CateId=rst.getInt(4);
			goodsInfo.CateName=rst.getString(5);
			goodsInfo.Price=rst.getInt(6);
			goodsInfo.TotalNumber=rst.getInt(7);
			goodsInfo.SaleNumber=rst.getInt(8);
			goodsInfo.SubTime=rst.getTimestamp(9);
			goodsInfo.DelFlag=rst.getInt(10);
			goodsInfo.State=rst.getString(11);
			goodsInfo.Remark=rst.getString(12);
			goodsInfo.IsSale=rst.getInt(13);
			
			goodsInfoArr.add(goodsInfo);
		}
		return goodsInfoArr;
	}
	
	//商品上架
	public int ExecuteUpToSale(String goodsNum){
		String sql = "Update goodsinfo set IsSale = 1 where GoodsNum = ?";
		String params[] = {goodsNum};
		
		//执行更新
		return  new DBOper().executeUpdate(sql,params);
	}
	
	//商品下架
	public int ExecuteDownToSale(String goodsNum){
		String sql = "Update goodsinfo set IsSale = 0 where GoodsNum = ?";
		String params[] = {goodsNum};
		
		//执行更新
		return  new DBOper().executeUpdate(sql,params);
	}
	
	//新增商品信息
	public int AddNewGoodsInfo(GoodsInfo goods){
		String sql = "Insert into goodsinfo(GoodsNum, GoodsName, CateId, CateName,Price, TotalNumber,SaleNumber,State, Remark, IsSale) values(?,?,?,?,?,?,?,?,?,?)";
		String params[] = {goods.GoodsNum, goods.GoodsName, String.valueOf(goods.CateId) , goods.CateName, String.valueOf(goods.Price), String.valueOf(goods.TotalNumber), String.valueOf(goods.SaleNumber), goods.State, goods.Remark, String.valueOf(goods.IsSale)};
		
		DBOper db = new DBOper();
		
		return db.executeUpdate(sql, params);
		
	}
	
	//产生一个新的值
	public String getNewGoodsNum() throws SQLException{
		String sql = "Select GoodsNum from goodsinfo order by GoodsNum";
		String[] params = {};
		DBOper db = new DBOper();
		
		ResultSet rst = db.executeQuery(sql, params);
		String lastGoodsNum1 = "";
		String lastGoodsNum2 = "";
		if(rst.last()){
			lastGoodsNum1 = rst.getString(1);
			
			//截取字符串中数字部分
			Pattern p = Pattern.compile("[0-9\\.]+");
			Matcher m = p.matcher(lastGoodsNum1);
			
			
			while(m.find()){
				lastGoodsNum2 += m.group();
			}
		}
		
		String newGoodsNum =  "G"+(Integer.parseInt(lastGoodsNum2) +1); 
		//System.out.print("产生新的值："+ newGoodsNum);
		return newGoodsNum;
		
	}

}
