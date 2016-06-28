package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
	
		String sql = "select * from GoodsInfo where DelFlag =0";
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
			
			goodsInfoArr.add(goodsInfo);
		}
		return goodsInfoArr;
	}

}
