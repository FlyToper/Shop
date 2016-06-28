package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import Core.DBOper;

//商品销售
public class ExportInfo {
	public int Id;
	public String GoodsNum;//商品编号 
	public String GoodsName;//商品名字
	public int Number;//出货量
	public int Price;//出售价格
	public String UserNum;//员工编号 
	public String UserName;//员工名字
	public java.sql.Timestamp SubTime;
	public int DelFlag;
	public String State;
	public String Remark;

	//商品销售处理方法
	public String SaleGoods(String userNum,String userName,String goodsNum,String goodsName,String number,String price){
		System.out.println("进入数据访问层SaleGoods()成功");
		
		String sql = "insert into ExportInfo(GoodsNum,GoodsName,Number,Price,UserNum,UserName) values(?,?,?,?,?,?)";
		String params[]={goodsNum, goodsName, number, price, userNum, userName};
		int n = new DBOper().executeUpdate(sql, params);
		
		sql = "update GoodsInfo set TotalNumber=TotalNumber-?, SaleNumber=SaleNumber+? where GoodsNum=?";
		String params1[]={number, number, goodsNum};
		
		n = new DBOper().executeUpdate(sql, params1);
		if(n>=1){
			return "success";
		}else{
			return "error";
		}
		
		
	}

	//查询商品销售单号方法
    public ExportInfo QueryExportInfoByExportId(String exportId){
    	System.out.println("进入数据访问层QueryExportInfoByExportId(成功");
		
		String sql = "select * from ExportInfo where Id=?";
		String params[]={exportId};
		ResultSet rst = new DBOper().executeQuery(sql, params);
		
		ExportInfo exportInfo = new ExportInfo();
		try {
			if(rst.next()){
				exportInfo.Id=rst.getInt(1);
				exportInfo.GoodsNum=rst.getString(2);
				exportInfo.GoodsName=rst.getString(3);
				exportInfo.Number=rst.getInt(4);
				exportInfo.Price=rst.getInt(5);
				exportInfo.UserNum=rst.getString(6);
				exportInfo.UserName=rst.getString(7);
				exportInfo.SubTime=rst.getTimestamp(8);
				exportInfo.DelFlag=rst.getInt(9);
				exportInfo.State=rst.getString(10);
				exportInfo.Remark=rst.getString(11);
				
				return exportInfo;
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return exportInfo;
    }
}