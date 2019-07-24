package com.swipecard.model;

import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import com.swipecard.util.DESUtils;
import com.swipecard.util.FormatDateUtil;

import oracle.jdbc.internal.OracleTypes;

public class OnLineEmpTableModel extends AbstractTableModel{
	private static Logger logger = Logger.getLogger(OnLineEmpTableModel.class);
	/** * @author SYR */
	private Vector<Object> TableData;// 用来存放表格数据的线性表
	// private Vector TableTitle;// 表格的 列标题
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	static Properties pps = new Properties();
	static Reader pr = null;
	static {
		try {
			pr = Resources.getResourceAsReader("db.properties");
			pps.load(pr);
			pps.setProperty("username", DESUtils.getDecryptString(pps.getProperty("username")));
			pps.setProperty("password", DESUtils.getDecryptString(pps.getProperty("password")));
			reader = Resources.getResourceAsReader("Configuration.xml");
			/*
			 * String filePath = System.getProperty("user.dir") +
			 * "/Configuration.xml"; FileReader reader=new FileReader(filePath);
			 */
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,pps);
		} catch (Exception e) {
			logger.error("綁定指示單號時Error building SqlSession，原因:"+e);
			e.printStackTrace();
		}
	}
	private String[] columnNames = { "序號","工號", "姓名","刷卡日期","刷卡時間" };

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}
	
	public OnLineEmpTableModel(){
		
	}
	
	public OnLineEmpTableModel(String WorkshopNo,String Lineno){
		TableData = new Vector<Object>();
		SqlSession session = sqlSessionFactory.openSession();
		Date time = FormatDateUtil.getDateTime();//確保是準確的時間
//		String time = "2017-06-14 07:00:00";
		SwipeCardTimeInfos swipeUser = new SwipeCardTimeInfos();
		SwipeCardTimeInfos swipeDay = new SwipeCardTimeInfos();
		swipeUser.setSwipeCardTime(time);
		swipeUser.setWorkshopNo(WorkshopNo);
		swipeUser.setShift("D");
		if(Lineno == null || Lineno.equals("")){
			Lineno = "0";
		}
		swipeUser.setProdLineCode(Lineno);
		
		swipeDay.setSwipeCardTime(time);
		swipeDay.setWorkshopNo(WorkshopNo);
		swipeDay.setProdLineCode(Lineno);
		
		List<SwipeCardTimeInfos> swipeInfos = null;
		try{
			int currentTime = time.getHours()*100 + time.getMinutes();
			System.out.println("currentTime:"+currentTime);
			if(currentTime<645){
				Calendar begin=Calendar.getInstance();
				begin.setTime(time);
				begin.add(Calendar.DAY_OF_MONTH,-1);
				swipeUser.setSwipeCardTime(begin.getTime());
				swipeUser.setShift("N");
			}else if(currentTime<1845){
				swipeDay.setShift("D");
				int resultCount = session.selectOne("selectEmpCountByLineNoAndWorkshopNo", swipeDay);
				if(resultCount==0){
					Calendar begin=Calendar.getInstance();
					begin.setTime(time);
					begin.add(Calendar.DAY_OF_MONTH,-1);
					swipeUser.setSwipeCardTime(begin.getTime());
					swipeUser.setShift("N");
				}
			}else{
				swipeDay.setShift("N");
				int resultCount = session.selectOne("selectEmpCountByLineNoAndWorkshopNo", swipeDay);
				if(resultCount>0){
					swipeUser.setShift("N");
				}
			}
			System.out.println(swipeUser);
			swipeInfos = session.selectList("selectEmpByLineNoAndWorkshopNo", swipeUser);
			/*Map<String, Object> param = new HashMap<String, Object>();
			param.put("WorkshopNo", 123);
			param.put("ProdLineCode", 123);
			param.put("SwipeCardTime", time);
			param.put("v_refcur", OracleTypes.CURSOR);
			swipeInfos = session.selectList("selectEmpByLineNoAndWorkshopNoProc", param);
			System.out.println(swipeInfos);*/
			int i=0;
			if(swipeInfos==null){
				System.out.println("swipeInfos:0--");
			}else{
				System.out.println("swipeInfos:"+swipeInfos.size());
			}
			String Name = "",empId = "",swipe_date = "",swipecardTime = "";
			int j = 1;
			for(i=0;i<swipeInfos.size();i++){
//				System.out.println("lineno: "+eif.get(i).getName());
				empId=swipeInfos.get(i).getEMP_ID();
				Employee empInfo = session.selectOne(
							"selectUserByEmpId", empId);			
				if(empInfo!=null){
				if(empInfo.getName()==null){
					Name="";
				}else{
					Name = empInfo.getName();
				}
				if(swipeInfos.get(i).getSWIPE_DATE()!=null){
					swipe_date = swipeInfos.get(i).getSWIPE_DATE();
				}else{
					swipe_date = "";
				}
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(swipeInfos.get(i).getSwipeCardTime()!=null){
					swipecardTime = dateFormatter.format(swipeInfos.get(i).getSwipeCardTime());
				}
				Object[] tableSwipeInfos = {j,empId,Name,swipe_date,swipecardTime};
				j++;
				TableData.add(tableSwipeInfos);
				}
			}
			System.out.println("TableData:"+TableData.size());
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public int getColumnCount() {
		// 告知列数，用标题数组的大小,这样表格就是TableData.size()行，TableTitle.size()列了
		return columnNames.length;
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		int size = 0;
		if(TableData!=null){
			size = TableData.size();
		}
		return size;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Object LineTemp[] = (Object[]) this.TableData.get(rowIndex);
		// 提取出对 应的数据
		return LineTemp[columnIndex];
	}
	
	public static void main(String[] args) {
		OnLineEmpTableModel a = new OnLineEmpTableModel("123","123");
	}

}
