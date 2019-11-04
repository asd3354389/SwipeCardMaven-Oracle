package com.swipecard;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.Reader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.swipecard.util.DESUtils;
import com.swipecard.util.FormatDateUtil;
import com.swipecard.util.FrameShowUtil;
import com.swipecard.util.GetLocalHostIpAndName;
import com.swipecard.util.JsonFileUtil;
import com.swipecard.util.RoundButton;
import com.swipecard.util.SwipeCardJButton;
import com.swipecard.util.UUID32;
import com.swipecard.model.EmpShiftInfos;
import com.swipecard.model.Employee;
import com.swipecard.model.LineNO;
import com.swipecard.model.OnLineEmpTableModel;
import com.swipecard.model.RCLine;
import com.swipecard.model.RawRecord;
import com.swipecard.model.RepairReasons;
import com.swipecard.model.RepairSwipecard;
import com.swipecard.model.RepairWorkshopNo;
import com.swipecard.model.SwingBase;
import com.swipecard.model.SwipeCardTimeInfos;
import com.swipecard.model.SwipeCardUserTableModel;
import com.swipecard.model.WorkedOneWeek;
import com.swipecard.services.SwipeCardService;
import com.swipecard.swipeRecordLog.SwipeRecordLogToDB;

public class SwipeCard extends JFrame {

	private static final long serialVersionUID = 1216479862784043108L;
	private final static String CurrentVersion="V20191009";
	private static Logger logger = Logger.getLogger(SwipeCard.class);
	private Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
	private JTable table;
	private String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";	
	private int ONE_SECOND = 1000;

	static JTabbedPane tabbedPane;
	static JLabel label1, label3, swipeTimeLable, curTimeLable,p5workshopNoLabel,p5workshopNoLabelDesc,p5linenoLabel,p5linenoLabelDesc;
	static JLabel labelS1, labelS2, labelS3,workshopNo6_1,p5swipecardLabel,p5ReasonClass,p5ReasonDesc,p5swipecardClass;
	static JLabel p3WorksNoLabel,p3LinkNoLabel,p3swipeTimeLable, p3curTimeLable,p3swipeLabel,p3tipLable,p1tipLable,p4tipLable,p5curTimeLable,p5swipeTimeLable;
	static JPanel panel1, panel2, panel3,panel4,panel5,panel6,panel6_1;
	static ImageIcon image;
	static JLabel labelT2_1, labelT2_2, labelT2_3, labelT1_1,workShopNoJlabel, labelT1_3, labelT1_5, labelT1_6, labelT1_4, linenoLabel, labelT1_7;
	static JComboBox comboBox, comboBox2,p3WorkShopNoComboBox,p3LinkNoComboBox,p5ReasonClassCombobox,p5ReasonDescCombobox,p5swipecardClassCombobox,p6WorkShopNoComboBox;
	static SwipeCardJButton butT1_3, butT1_4, butT1_5, butT1_6, butT2_1, butT2_2, butT2_3, butT1_7, butT2_rcno,p5But1_1,p5But1_2;
	static JTextArea jtextT1_1, jtextT1_2,p3JTextArea,jtextT5_1;
	static TextField textT2_1, textT2_2, textT1_3, textT1_1, textT1_5, textT1_6,p3SwipeText,p5swipecardText;
	static JTextField jtf, jtf2;
	static JScrollPane jspT1_1, jspT2_2, JspTable, myScrollPane,p3jsp,empScrollPane,jsp6_1,jspT5_1;
	// static Object[] str1 = getItems();
	static Object[] str1 = null;
	private SwipeCardUserTableModel myModel;
	private JTable mytable;
	
	private OnLineEmpTableModel empModel;
	private JTable emptable;
	
	final Object[] WorkshopNoList = getWorkshopNo();
	final JSONObject LineNoObject = getLineNoObject();
	Object[] lineno = null;
	Object[] ReasonClass = null;
	List<RepairReasons> ReasonDescList = null;
	Object[] ReasonDesc = null;
	
	static GetLocalHostIpAndName getLocalHostIpAndName = new GetLocalHostIpAndName();
	static String Realip = getLocalHostIpAndName.getLocalHostIP();

	static JsonFileUtil jsonFileUtil = new JsonFileUtil();
	static String defaultWorkshopNo = jsonFileUtil.getSaveWorkshopNo();
	static  String defaultLineNo = jsonFileUtil.getSaveLineNo();
	
	int RepairCount = 0;
	Date RepairSwipe = new Date();
	int RepairSwipeSecond = 3;
	  
	static SqlSessionFactory sqlSessionFactory;
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
			 * "/Configuration.xml"; FileReader reader = new
			 * FileReader(filePath);
			 */
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,pps);
		} catch (Exception e) {
			logger.error("Error opening session:"+e);
			SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}

	/**
	 * Timer task to update the time display area
	 *
	 */
	protected class JLabelTimerTask extends TimerTask {
		@Override
		public void run() {			
			//time = dateFormatter.format(Calendar.getInstance().getTime());
			Date date = new Date();
			SimpleDateFormat dateFormatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
            String time = dateFormatter.format(date);
			curTimeLable.setText(time);
			p3curTimeLable.setText(time);
			p5curTimeLable.setText(time);
		}
	}
	
	protected class EmpTableTimerTask extends TimerTask {
		@Override
		public void run() {			
			//time = dateFormatter.format(Calendar.getInstance().getTime());
			String WorkshopNo = workShopNoJlabel.getText();
			String LineNo = linenoLabel.getText();

			panel4.remove(empScrollPane);
			empModel = new OnLineEmpTableModel(WorkshopNo, LineNo);
			emptable = new JTable(empModel);
			emptable.getColumnModel().getColumn(1).setPreferredWidth(20);
			emptable.getColumnModel().getColumn(2).setPreferredWidth(20);
			setEmpTable();
			empScrollPane = new JScrollPane(emptable);
			empScrollPane.setBounds(50, 80, 600, 500);
			p1tipLable.setText("<html>當前人數:"+empModel.getRowCount()+"</html>");
			p4tipLable.setText("<html>當前人數:"+empModel.getRowCount()+"</html>");
			
			panel4.add(empScrollPane);
			panel4.updateUI();
			panel4.repaint();
		}
	}
	
	protected class getRepairLineNoStatusTimerTask extends TimerTask {
		@Override
		public void run() {			
			//time = dateFormatter.format(Calendar.getInstance().getTime());
			String RepairWorkshopNo = p6WorkShopNoComboBox.getSelectedItem().toString();
			getRepairLineNoStatus(RepairWorkshopNo);
			if(RepairCount==0){
				RepairCount++;
			}else{
				Object[] RepairWorkshopNoArray = getRepairWorkshopNo();
				p6WorkShopNoComboBox.removeAllItems();
				for (Object object : RepairWorkshopNoArray) {
					p6WorkShopNoComboBox.addItem(object);
				}
				p6WorkShopNoComboBox.setSelectedItem(RepairWorkshopNo);
			}
		}
	}
	

	public SwipeCard(final String WorkshopNo,final String LineNo) {

		super("產線端刷卡程式-"+CurrentVersion);
		SwipeCardService service=new SwipeCardService();
		//setBounds(12, 84, 1000, 630);		
		setResizable(true);
		
		Container c = getContentPane();
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT); // 创建选项卡面板对象
		// 创建标签
		labelS1 = new JLabel("指示單號");
		labelS2 = new JLabel("料號");
		labelS3 = new JLabel("標準人數");

		panel1 = new JPanel();
		panel1.setLayout(null);
		panel2 = new JPanel();
		panel2.setLayout(null);
		panel3 = new JPanel();
		panel3.setLayout(null);
		panel4 = new JPanel();
		panel4.setLayout(null);
		panel5 = new JPanel();
		panel5.setLayout(null);
		panel6 = new JPanel();
		panel6.setLayout(null);
		panel6_1 = new JPanel();
		panel6_1.setLayout(null);
		panel6_1.setPreferredSize(new Dimension(1000,10000));
		panel1.setBackground(Color.WHITE);
		panel2.setBackground(Color.WHITE);
		panel3.setBackground(Color.WHITE);
		panel4.setBackground(Color.WHITE);
		panel5.setBackground(Color.WHITE);
		panel6.setBackground(Color.WHITE);
		panel6_1.setBackground(Color.WHITE);
		jsp6_1 = new JScrollPane(panel6_1);
		jsp6_1.setBounds(0, 170, 1100, 1000);
		jsp6_1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp6_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		labelT2_1 = new JLabel("班別：");// 指示單號

		str1 = getRcLine();
		if (str1 != null) {
			comboBox = new JComboBox(str1);
		} else {
			comboBox = new JComboBox();
		}

		comboBox.setEditable(true);

		comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		jtf = (JTextField) comboBox.getEditor().getEditorComponent();

		comboBox2 = new JComboBox();// getLineNoByWorkNo
		// comboBox2.addItem("");
		comboBox2.addItem("日班");
		comboBox2.addItem("夜班");
		comboBox2.setEditable(false);// 可編輯
		comboBox2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		jtf2 = (JTextField) comboBox2.getEditor().getEditorComponent();

		textT1_1 = new TextField(15);// 車間
		textT1_1.setFont(new Font("微软雅黑", Font.PLAIN, 25));

		textT1_3 = new TextField(15);// 上班
		textT1_3.setFont(new Font("微软雅黑", Font.PLAIN, 25));

		jtextT1_1 = new JTextArea();// 刷卡人員信息,JTextArea(int rows, int columns)
		jtextT1_1.setBackground(Color.WHITE);
		jtextT1_2 = new JTextArea();// 備註
		textT2_1 = new TextField(15);// "料號"
		textT2_2 = new TextField(15);// "標準人數"

		// text3 = new JTextArea(2, 20);

		labelT1_1 = new JLabel("車間:");
		labelT1_1.setFont(new Font("微软雅黑", Font.BOLD, 25));
		
		workShopNoJlabel = new JLabel(WorkshopNo);
		workShopNoJlabel.setFont(new Font("微软雅黑", Font.BOLD, 25));
		
		linenoLabel = new JLabel("線號");
		linenoLabel.setFont(new Font("微软雅黑", Font.BOLD, 25));

		labelT1_3 = new JLabel("刷卡:");
		labelT1_3.setFont(new Font("微软雅黑", Font.BOLD, 25));
		
		labelT1_4 = new JLabel("線號：");
		labelT1_4.setFont(new Font("微软雅黑", Font.BOLD, 25));

		labelT1_5 = new JLabel("實際人數:");
		labelT1_6 = new JLabel("備註:");
		labelT2_2 = new JLabel("指示單號:");
		labelT2_3 = new JLabel("標準人數:");
		labelT1_7 = new JLabel("更換車間刷卡：");
		labelT1_7.setFont(new Font("微软雅黑", Font.BOLD, 25));
		labelT1_7.setVisible(false);
		
		curTimeLable = new JLabel();
		curTimeLable.setFont(new Font("微软雅黑", Font.BOLD, 35));

		swipeTimeLable = new JLabel();
		swipeTimeLable.setFont(new Font("微软雅黑", Font.BOLD, 35));

		// 未補充指示單號人員信息
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("姓名");
		columnNames.add("刷卡時間1");
		columnNames.add("刷卡時間2");
		table = new JTable(new DefaultTableModel(rowData, columnNames));
		JspTable = new JScrollPane(table);
		JspTable.setBounds(310, 40, 520, 400);

		Object ShiftName = comboBox2.getSelectedItem();
		String ShiftRcNo = "";
		if (ShiftName.equals("夜班")) {
			ShiftRcNo = "N";
		} else {
			ShiftRcNo = "D";
		}

		myModel = new SwipeCardUserTableModel(WorkshopNo, ShiftRcNo ,LineNo);
		mytable = new JTable(myModel);
		setTable();
		myScrollPane = new JScrollPane(mytable);
		myScrollPane.setBounds(360, 40, 520, 400);
		
		
		empModel = new OnLineEmpTableModel(WorkshopNo,LineNo);
		emptable = new JTable(empModel);
		setEmpTable();
		empScrollPane = new JScrollPane(emptable);
		empScrollPane.setBounds(50, 80, 600, 500);
		
		p1tipLable = new JLabel("<html>當前人數:"+empModel.getRowCount()+"</html>");
		p1tipLable.setFont(new Font("微软雅黑", 1, 30));
		p1tipLable.setForeground(Color.red);
		
		p3swipeLabel = new JLabel("刷卡：");
		p3swipeLabel.setFont(new Font("微软雅黑", Font.BOLD, 25));
		
		p3SwipeText = new TextField(15);// 上班
		p3SwipeText.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		
		p3tipLable = new JLabel("<html>此界面只限換線使用,上下班刷卡請切換至上下班刷卡界面</html>");
		p3tipLable.setFont(new Font("微软雅黑", 1, 30));
		p3tipLable.setForeground(Color.red);
		
		p3WorksNoLabel = new JLabel("車間：");
		p3WorksNoLabel.setFont(new Font("微软雅黑", Font.BOLD, 25));
		
		p3LinkNoLabel = new JLabel("線號：");
		p3LinkNoLabel.setFont(new Font("微软雅黑", Font.BOLD, 25));
		
		p3curTimeLable = new JLabel();
		p3curTimeLable.setFont(new Font("微软雅黑", Font.BOLD, 25));
		
		p3swipeTimeLable = new JLabel();
		p3swipeTimeLable.setFont(new Font("微软雅黑", Font.BOLD, 35));
		
		p3JTextArea = new JTextArea();// 刷卡人員信息,JTextArea(int rows, int columns)
		p3JTextArea.setBackground(Color.WHITE);
		
		p3WorkShopNoComboBox = new JComboBox(WorkshopNoList);
		p3WorkShopNoComboBox.setSelectedItem(WorkshopNo);
		p3WorkShopNoComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		p3LinkNoComboBox = new JComboBox();
		p3LinkNoComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		p4tipLable = new JLabel("<html>當前人數:"+empModel.getRowCount()+"</html>");
		p4tipLable.setFont(new Font("微软雅黑", 1, 30));
		p4tipLable.setForeground(Color.red);
		
		lineno = getLineno(p3WorkShopNoComboBox.getSelectedItem().toString());
		if (lineno != null) {
			for (Object object : lineno) {
				p3LinkNoComboBox.addItem(object);
				if(LineNo==null||LineNo.equals("")){
					p3LinkNoComboBox.setSelectedItem("不需要選擇線號");
				}else{
					p3LinkNoComboBox.setSelectedItem(LineNo);
				}
				
			}
		} else {
			p3LinkNoComboBox.addItem("不需要選擇線號");
		}
		

		int x1 = 15, x2 = 100, x3 = 200, x4 = 400, x5 = 130, x6 = 460, x7 = 90, x8 = 50;
		int y1 = 40, y4 = 180;

		labelT2_1.setBounds(x1, y1, x7, y1);
		labelT2_2.setBounds(x1, 2 * y1 + 10, x7, y1);
		comboBox2.setBounds(x1 + x7, y1, x3 + x8, y1); 
		comboBox.setBounds(x1 + x7, 2 * y1 + 10, x3 + x8, y1);

		labelT2_3.setBounds(x1, 2 * y1 + 10, x7, y1);

		labelT1_1.setBounds(x1 + 20, y1, x7, y1);
		labelT1_3.setBounds(x1 + 20, 2 * y1 + 20, x7, y1);

		labelT1_6.setBounds(x1, 8 * y1, x7, y1);
		linenoLabel.setBounds(x1 + x7, 4 * y1 + 80, y4 + 100, y1);
		workShopNoJlabel.setBounds(x1 + x7, 1 * y1, y4 + 100, y1);
		textT1_3.setBounds(x1 + x7, 2 * y1 + 20, y4 + 100, y1);
		labelT1_4.setBounds(x1 + 20, 4 * y1 + 80, x7, y1);
		jtextT1_2.setBounds(x1 + x7, 9 * y1, x4, y1);

		textT2_1.setBounds(x1 + x7, 1 * y1, y4, y1);
		textT2_2.setBounds(x1 + x7, 2 * y1 + 10, y4, y1);

		swipeTimeLable.setBounds(400, y1, x4, 50);
		curTimeLable.setBounds(x1 + 10, 3 * y1 + 40, 400, 50);

		jspT1_1 = new JScrollPane(jtextT1_1);
		jspT1_1.setBounds(400, 2 * y1 + 20, x4, 250);

		jspT2_2 = new JScrollPane(jtextT1_2);
		jspT2_2.setBounds(x1, 9 * y1, x3 + x7, 150);
		
		p1tipLable.setBounds(x1 + 20,5 * y1 + 60, y4 + 100 + x7, 4 * y1);
		
		p3WorksNoLabel.setBounds(x1, y1, x7, y1);
		p3LinkNoLabel.setBounds(x1,2 * y1 + 20, x7, y1);
		p3WorkShopNoComboBox.setBounds(x1 + x7, 1 * y1, y4 + 100, y1);
		p3LinkNoComboBox.setBounds(x1 + x7,2 * y1 + 20, y4 + 100, y1);
		p3curTimeLable.setBounds(x1 + 10, 3 * y1 + 40, 400, 50);
		p3swipeTimeLable.setBounds(400, y1, x4, y1);
		p3swipeLabel.setBounds(x1,4 * y1 + 60, x7, y1);
		p3SwipeText.setBounds(x1 + x7,4 * y1 + 60, y4 + 100, y1);
		p3tipLable.setBounds(x1,5 * y1 + 60, y4 + 100 + x7, 3 * y1);
		p3jsp = new JScrollPane(p3JTextArea);
		p3jsp.setBounds(400, y1 + 40, x4, 250);
		
		p4tipLable.setBounds(50,20, y4 + 100, y1);
		int cc = 240;
		Color d = new Color(cc, cc, cc);

		// 将标签面板加入到选项卡面板对象上
		tabbedPane.addTab("上下班刷卡界面", null, panel1, "First panel");
		tabbedPane.addTab("補充指示單號", null, panel2, "Second panel");
		tabbedPane.addTab("更換線別刷卡", null, panel3, "Third panel");
		tabbedPane.addTab("當前產線人員", null, panel4, "Fourth panel");
		tabbedPane.addTab("機台維護刷卡", null, panel5, "Fourth panel");
		tabbedPane.addTab("機台維護看板", null, panel6, "Fifth panel");
		tabbedPane.setSelectedIndex(0); // 设置默认选中的
		// tabbedPane.setEnabledAt(1,false);
		this.setVisible(true);

		textT1_1.setEditable(false);
		textT1_3.setEditable(true);

		jtextT1_1.setEditable(false);
		jtextT1_2.setEditable(false);
		p3JTextArea.setEditable(false);

		textT2_1.setEditable(false);
		textT2_2.setEditable(false);

		jtextT1_1.setLineWrap(true);
		jtextT1_2.setLineWrap(true);
		p3JTextArea.setLineWrap(true);

		jtextT1_2.setBackground(d);
		labelT1_7.setBounds(x1 + 20, 350 + y1 + 20, x3, y1);

		butT1_5 = new SwipeCardJButton("更換車間", 2);
		butT1_6 = new SwipeCardJButton("退出程式", 2);

		butT2_1 = new SwipeCardJButton("換料 ", 2);
		butT2_2 = new SwipeCardJButton("確認提交", 2);
		butT2_3 = new SwipeCardJButton("人員刷新", 2);
		butT2_rcno = new SwipeCardJButton("刷新指示單", 2);

		butT1_5.setBounds(x6, 350 + y1 + 20, x5, y1);
		butT1_6.setBounds(x6 + 160, 350 + y1 + 20, x5, y1);
		butT2_1.setBounds(x4, 400, x5, y1);
		butT2_3.setBounds(x6 + 60, 12 * y1, x5, y1);

		butT2_rcno.setBounds(x2, 3 * y1 + 30, 100, y1);
		butT2_2.setBounds(x2 + 110, 3 * y1 + 30, 90, y1);
		
		textT1_6 = new TextField(15);// 管理员刷卡
		textT1_6.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		textT1_6.setVisible(false);
		textT1_6.setBounds(x1 + x3, 350 + y1 + 20, y4 + 30, y1);
		textT1_6.setEditable(false);
		panel1.add(textT1_6);

		panel1.add(textT1_3);
		panel1.add(labelT1_7);
		panel1.add(labelT1_1);
		panel1.add(workShopNoJlabel);
		panel1.add(labelT1_4);
		panel1.add(linenoLabel);
		panel1.add(p1tipLable);
		
		panel1.add(labelT1_3);
		panel1.add(swipeTimeLable);
		panel1.add(curTimeLable);

		panel1.add(jspT1_1);
		panel1.add(butT1_5);
		panel1.add(butT1_6);

		panel2.add(butT2_2);

		panel2.add(butT2_3);
		panel2.add(butT2_rcno);

		panel2.add(labelT2_1);
		panel2.add(labelT2_2);
		panel2.add(comboBox);
		panel2.add(comboBox2);

		panel2.add(myScrollPane);
		
		panel3.add(p3WorksNoLabel);
		panel3.add(p3LinkNoLabel);
		panel3.add(p3WorkShopNoComboBox);
		panel3.add(p3LinkNoComboBox);
		panel3.add(p3swipeLabel);
		panel3.add(p3SwipeText);
		panel3.add(p3tipLable);
		panel3.add(p3jsp);
		panel3.add(p3curTimeLable);
		panel3.add(p3swipeTimeLable);
		
		panel4.add(empScrollPane);
		panel4.add(p4tipLable);
		
		//機台維護刷卡
		p5workshopNoLabel = new JLabel("車間:");
		p5workshopNoLabel.setFont(new Font("微软雅黑", Font.BOLD, 25));
		p5workshopNoLabel.setBounds(x1 + 20, y1, x7, y1);
		
		p5workshopNoLabelDesc = new JLabel(WorkshopNo);
		p5workshopNoLabelDesc.setFont(new Font("微软雅黑", Font.BOLD, 25));
		p5workshopNoLabelDesc.setBounds(x1 + x7, 1 * y1, y4 + 100, y1);
		
		p5linenoLabel = new JLabel("線號：");
		p5linenoLabel.setFont(new Font("微软雅黑", Font.BOLD, 25));
		p5linenoLabel.setBounds(x1 + 20, 2 * y1, x7, y1);
		
		p5linenoLabelDesc = new JLabel("線號");
		p5linenoLabelDesc.setFont(new Font("微软雅黑", Font.BOLD, 25));
		p5linenoLabelDesc.setBounds(x1 + x7, 2 * y1, y4 + 100, y1);
		
		if(LineNo == null || LineNo.equals("")){
			p5linenoLabelDesc.setText("");
			p5linenoLabel.setVisible(false);
		}else{
			p5linenoLabelDesc.setText(LineNo);
		}
		
		p5curTimeLable = new JLabel();
		p5curTimeLable.setFont(new Font("微软雅黑", Font.BOLD, 35));
		p5curTimeLable.setBounds(x1 + 10, 3 * y1, 400, 50);
		
		p5swipeTimeLable = new JLabel();
		p5swipeTimeLable.setFont(new Font("微软雅黑", Font.BOLD, 35));
		p5swipeTimeLable.setBounds(400, y1, x4, y1);
		
		p5swipecardLabel = new JLabel("刷卡:");
		p5swipecardLabel.setFont(new Font("微软雅黑", Font.BOLD, 25));
		p5swipecardLabel.setBounds(x1 + 20, 4 * y1 + 20, x7, y1);
		
		p5swipecardText = new TextField(15);// 上班
		p5swipecardText.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		p5swipecardText.setBounds(x1 + x7, 4 * y1 + 20, y4 + 100, y1);
		
		p5ReasonClass = new JLabel("維修類別:");
		p5ReasonClass.setFont(new Font("微软雅黑", Font.BOLD, 25));
		p5ReasonClass.setBounds(x1 + 20, 5 * y1 + 40, x7*2, y1);
		
		p5ReasonClassCombobox = new JComboBox();
		p5ReasonClassCombobox.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		p5ReasonClassCombobox.setBounds(x1 + x7 + 50, 5 * y1 + 40, y4 + 50, y1);
		p5ReasonClassCombobox.setEditable(false);
		
		ReasonClass = getReasonClass();
		if (ReasonClass != null) {
			for (Object object : ReasonClass) {
				p5ReasonClassCombobox.addItem(object);
			}
		} else {
			p5ReasonClassCombobox.addItem("無維修類別");
		}
		
		p5ReasonDesc = new JLabel("維修原因:");
		p5ReasonDesc.setFont(new Font("微软雅黑", Font.BOLD, 25));
		p5ReasonDesc.setBounds(x1 + 20, 6 * y1 + 60, x7*2, y1);
		
		
		p5ReasonDescCombobox = new JComboBox();// 上班
		p5ReasonDescCombobox.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		p5ReasonDescCombobox.setBounds(x1 + x7 + 50, 6 * y1 + 60, y4 + 50, y1);
		p5ReasonDescCombobox.setEditable(false);
		
		ReasonDesc = getReasonDesc(p5ReasonClassCombobox.getSelectedItem().toString());
		if (ReasonDesc != null) {
			for (Object object : ReasonDesc) {
				p5ReasonDescCombobox.addItem(object);
			}
		} else {
			p5ReasonDescCombobox.addItem("無維修原因");
		}
		
		
		jtextT5_1 = new JTextArea();// 刷卡人員信息,JTextArea(int rows, int columns)
		jtextT5_1.setBackground(Color.WHITE);
		jtextT5_1.setEditable(false);
		jtextT5_1.setLineWrap(true);
		
		jspT5_1 = new JScrollPane(jtextT5_1);
		jspT5_1.setBounds(400, 2 * y1 + 20, x4, 250);
		
		p5But1_1 = new SwipeCardJButton("維修完成", 2);
		p5But1_1.setBounds(x6, 350 + y1, x5, y1);
		
		p5But1_2 = new SwipeCardJButton("重新維修", 2);
		p5But1_2.setBounds(x6 + 160, 350 + y1, x5, y1);
		
		String checkRepairStatus = checkRepairStatus(WorkshopNo,LineNo);
		System.out.println(checkRepairStatus);
		if(checkRepairStatus.equals("3")){
			p5But1_1.setEnabled(true);
			p5But1_2.setEnabled(true);
			p5swipecardText.setEnabled(false);
			jtextT5_1.setText("請QC確認維修是否成功！\n------------\n");
		}else{
			if(checkRepairStatus.equals("0")){
				jtextT5_1.setText("當前線別無異常！\n------------\n");
			}else if(checkRepairStatus.equals("1")){
				jtextT5_1.setText("當前線別線長已提報故障，待維修人員刷卡維修！\n------------\n");
			}else if(checkRepairStatus.equals("2")){
				jtextT5_1.setText("當前線別維修人員正在維修！\n------------\n");
				List<Employee> repairEmp = getRepairEmp(WorkshopNo,LineNo);
				if(repairEmp!=null&&repairEmp.size()>0){
					for(int e1 = 0;e1<repairEmp.size();e1++){
						jtextT5_1.append(repairEmp.get(e1).getId() + "   " +repairEmp.get(e1).getName()+"\n");
					}
				}else{
					jtextT5_1.append("當前已無人維修"+"\n");
				}
			}else if(checkRepairStatus.equals("4")){
				jtextT5_1.setText("當前線別QC已確認維修完成，待線長刷卡結案！\n------------\n");
			}else if(checkRepairStatus.equals("5")){
				jtextT5_1.setText("QC確認任然存在故障，請維修員重新維修！\n------------\n");
			}else{
				jtextT5_1.append("當前線別狀態異常！\n------------\n");
			}
			p5But1_1.setEnabled(false);
			p5But1_2.setEnabled(false);
			p5swipecardText.setEnabled(true);
		}
		
		p5swipecardClass = new JLabel("刷卡類型:");
		p5swipecardClass.setFont(new Font("微软雅黑", Font.BOLD, 25));
		p5swipecardClass.setBounds(x1 + 20, 350 + y1, x7*2, y1);
		
		p5swipecardClassCombobox = new JComboBox();// 上班
		p5swipecardClassCombobox.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		p5swipecardClassCombobox.setBounds(x1 + x7 + 50, 350 + y1, y4 + 50, y1);
		p5swipecardClassCombobox.setEditable(false);
		p5swipecardClassCombobox.addItem("線別維護刷卡");
		p5swipecardClassCombobox.addItem("強制結束刷卡");
		p5swipecardClassCombobox.addItem("QC巡檢");
		p5swipecardClassCombobox.addItem("打樣");
		
		panel5.add(p5workshopNoLabel);
		panel5.add(p5workshopNoLabelDesc);
		panel5.add(p5linenoLabel);
		panel5.add(p5linenoLabelDesc);
		panel5.add(p5curTimeLable);
		panel5.add(p5swipeTimeLable);
		panel5.add(p5swipecardText);
		panel5.add(p5swipecardLabel);
		panel5.add(jspT5_1);
		panel5.add(p5ReasonClassCombobox);
		panel5.add(p5ReasonClass);
		panel5.add(p5ReasonDescCombobox);
		panel5.add(p5ReasonDesc);
		panel5.add(p5But1_1);
		panel5.add(p5But1_2);
		panel5.add(p5swipecardClassCombobox);
		panel5.add(p5swipecardClass);
		
		
		//機台維護看板
		workshopNo6_1 = new JLabel("車間：");
		workshopNo6_1.setFont(new Font("微软雅黑", Font.BOLD, 25));
		workshopNo6_1.setBounds(10, 35, 90, 30);
		
		p6WorkShopNoComboBox = new JComboBox(getRepairWorkshopNo());
		p6WorkShopNoComboBox.setSelectedItem(WorkshopNo);
		p6WorkShopNoComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		p6WorkShopNoComboBox.setBounds(100, 35, y4 + 100, y1);
		
		for(int s1 = 0;s1<6;s1++){
			String lineStatus = "";
			if(s1==0){
				lineStatus = "正常";
			}else if(s1==1){
				lineStatus = "故障";
			}else if(s1==2){
				lineStatus = "維修中";
			}else if(s1==3){
				lineStatus = "QC確認中";
			}else if(s1==4){
				lineStatus = "QC已確認";
			}else {
				lineStatus = "無資料";
			}
			JButton buttontest1 = new JButton(lineStatus);
			if(s1==0){
				buttontest1.setBackground(Color.green);
			}else if(s1==1){
				buttontest1.setBackground(Color.red);
			}else if(s1==2){
				buttontest1.setBackground(Color.YELLOW);
			}else if(s1==3){
				Color col = new Color(187,255,255);
				buttontest1.setBackground(col);
			}else if(s1==4){
				buttontest1.setBackground(Color.PINK);
			}
			
			buttontest1.setFont(new Font("微软雅黑", Font.BOLD, 25));
			buttontest1.setBounds(s1 * 160 + 10, 100, 150, 50);
			panel6.add(buttontest1);
		}
		
		panel6.add(workshopNo6_1);
		panel6.add(p6WorkShopNoComboBox);
		panel6.add(jsp6_1);
		
		if(LineNo == null || LineNo.equals("")){
			linenoLabel.setText("");
			labelT1_4.setVisible(false);
		}else{
			linenoLabel.setText(LineNo);
		}
		
		FrameShowUtil frameShow=new FrameShowUtil();
		frameShow.sizeWindowOnScreen(this, 0.51, 0.6);
			
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), ONE_SECOND);
		tmr.scheduleAtFixedRate(new EmpTableTimerTask(), new Date(), 1000*60);
		tmr.scheduleAtFixedRate(new getRepairLineNoStatusTimerTask(), new Date(), 1000*60);
		
		p5ReasonClassCombobox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				if (e.getStateChange() == ItemEvent.SELECTED) {
					ReasonDesc = getReasonDesc(p5ReasonClassCombobox.getSelectedItem().toString());
					p5ReasonDescCombobox.removeAllItems();
					if (ReasonDesc != null) {
						for (Object object : ReasonDesc) {
							p5ReasonDescCombobox.addItem(object);
						}
					} else {
						p5ReasonDescCombobox.addItem("無維修原因");
					}
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						p5swipecardText.requestFocusInWindow();
					}
				});
			}
		});
		
		p5ReasonDescCombobox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						p5swipecardText.requestFocusInWindow();
					}
				});
			}
		});
		
		p5swipecardClassCombobox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String swipecardClass = p5swipecardClassCombobox.getSelectedItem().toString();
				if(swipecardClass.equals("線別維護刷卡")){
					String checkRepairStatus = checkRepairStatus(WorkshopNo,LineNo);
					System.out.println(checkRepairStatus);
					if(checkRepairStatus.equals("3")){
						p5But1_1.setEnabled(true);
						p5But1_2.setEnabled(true);
						p5swipecardText.setEnabled(false);
						jtextT5_1.setText("請QC確認維修是否成功！\n------------\n");
					}else{
						if(checkRepairStatus.equals("0")){
							jtextT5_1.setText("當前線別無異常！\n------------\n");
						}else if(checkRepairStatus.equals("1")){
							jtextT5_1.setText("當前線別線長已提報故障，待維修人員刷卡維修！\n------------\n");
						}else if(checkRepairStatus.equals("2")){
							jtextT5_1.setText("當前線別維修人員正在維修！\n------------\n");
							List<Employee> repairEmp = getRepairEmp(WorkshopNo,LineNo);
							if(repairEmp!=null&&repairEmp.size()>0){
								for(int e1 = 0;e1<repairEmp.size();e1++){
									jtextT5_1.append(repairEmp.get(e1).getId() + "   " +repairEmp.get(e1).getName()+"\n");
								}
							}else{
								jtextT5_1.append("當前已無人維修"+"\n");
							}
						}else if(checkRepairStatus.equals("4")){
							jtextT5_1.setText("當前線別QC已確認維修完成，待線長刷卡結案！\n------------\n");
						}else if(checkRepairStatus.equals("5")){
							jtextT5_1.setText("QC確認任然存在故障，請維修員重新維修！\n------------\n");
						}else{
							jtextT5_1.setText("當前線別狀態異常！\n------------\n");
						}
						p5But1_1.setEnabled(false);
						p5But1_2.setEnabled(false);
						p5swipecardText.setEnabled(true);
					}
				}else{
					jtextT5_1.setText(swipecardClass + "刷卡！\n------------\n");
					p5swipecardText.setEnabled(true);
				}
				jtextT5_1.setBackground(Color.WHITE);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						p5swipecardText.requestFocusInWindow();
					}
				});
			}
		});
		
		p6WorkShopNoComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String RepairWorkshopNo = p6WorkShopNoComboBox.getSelectedItem().toString();
					getRepairLineNoStatus(RepairWorkshopNo);
				}
			}
		});
		
		p3WorkShopNoComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				if (e.getStateChange() == ItemEvent.SELECTED) {
					lineno = getLineno(p3WorkShopNoComboBox.getSelectedItem().toString());
					p3LinkNoComboBox.removeAllItems();
					if (lineno != null) {
						for (Object object : lineno) {
							p3LinkNoComboBox.addItem(object);
						}
					} else {
						p3LinkNoComboBox.addItem("不需要選擇線號");
					}
				}
			}
		});
		
		// ItemListene取得用户选取的项目,ActionListener在JComboBox上自行输入完毕后按下[Enter]键,运作相对应的工作
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO

				if (e.getStateChange() == ItemEvent.SELECTED) {
					// System.out.println("-----------e.getItem():"+e.getStateChange()+"-------------");
					String RC_NO = jtf.getText();
					if (RC_NO.length() == 0) {
						textT2_1.setText("");
						textT2_2.setText("");
					} else {
						SqlSession session = sqlSessionFactory.openSession();
						try {
							RCLine rcLine = (RCLine) session.selectOne("selectUserByRCNo", RC_NO);
							if (rcLine != null) {
								textT2_1.setText(rcLine.getPRIMARY_ITEM_NO());
								textT2_2.setText(rcLine.getSTD_MAN_POWER());
							}

						} catch (Exception e1) {
							logger.error(e1);
							System.out.println("Error opening session");
							dispose();
							SwipeCardNoDB d = new SwipeCardNoDB(WorkshopNo);
							throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e1, e1);
						} finally {
							ErrorContext.instance().reset();
							if (session != null) {
								session.close();
							}
						}
					}

				}
			}
		});

		// TODO addKeyListener用于接收键盘事件（击键）的侦听器接口
		jtf.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				String key = jtf.getText();
				comboBox.removeAllItems();
				// for (Object item : getItems()) {
				if (str1 != null) {
					for (Object item : str1) {
						// 可以把contains改成startsWith就是筛选以key开头的项目
						// contains(key)/startsWith(key)
						if (((String) item).startsWith(key)) {
							comboBox.addItem(item);
						}
					}
				}
				jtf.setText(key);
			}

			public void keyPressed(KeyEvent e) {
			}
		});
		
		p5But1_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SqlSession session = sqlSessionFactory.openSession();
				// TODO Auto-generated method stub
				Date swipeCardTime = FormatDateUtil.getDateTime();
				SwipeCardService swipeCardService=new SwipeCardService();
				String repairLino = null;
				if(LineNo==null||LineNo.equals("")){
					repairLino = "0";
				}else{
					repairLino = LineNo;
				}
				String Reason = null;
				String Status = null;
				String ReasonDesc = p5ReasonDescCombobox.getSelectedItem().toString();
				try{
					RawRecord swipeRecord = new RawRecord();
					swipeRecord.setSwipeCardTime(swipeCardTime);
					swipeRecord.setWorkshopNo(WorkshopNo);
					swipeRecord.setLineNo(repairLino);
					int isRepairWorkshopNo = session.selectOne("isRepairWorkshopNo", swipeRecord);
					if(isRepairWorkshopNo==1){
						String swipeType = "1";
						String privilegeLevel = "3";
						Reason = getReasonNo(ReasonDesc);
						Status = "4";
						RepairWorkshopNo repairInfo = session.selectOne("getRepairInfo",swipeRecord);
						swipeCardService.updateSwipecardOut(session,swipeCardTime,repairInfo,Reason,Status,swipeType,privilegeLevel);
						session.commit();
						jtextT5_1.setBackground(Color.WHITE);
						jtextT5_1.setText("QC已確認，待線長刷卡結案！\n------------\n");
						p5swipecardText.setText("");
						p5But1_1.setEnabled(false);
						p5But1_2.setEnabled(false);
						p5swipecardText.setEnabled(true);
						p5swipecardText.requestFocusInWindow();
					}else{
						jtextT5_1.setBackground(Color.RED);
						jtextT5_1.setText("該線未添加到車間線別維修列表中！\n------------\n");
						p5swipecardText.setText("");
					}
				} catch (Exception e1) {
					session.rollback();
					logger.error("QC確認維修狀態错误，原因：" + e1);
					dispose();
					SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
				} finally {
					ErrorContext.instance().reset();
					if (session != null) {
						session.close();
					}
				}
				
			}
		});
		
		p5But1_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SqlSession session = sqlSessionFactory.openSession();
				// TODO Auto-generated method stub
				Date swipeCardTime = FormatDateUtil.getDateTime();
				SwipeCardService swipeCardService=new SwipeCardService();
				String repairLino = null;
				String ReasonDesc = p5ReasonDescCombobox.getSelectedItem().toString();
				if(LineNo==null||LineNo.equals("")){
					repairLino = "0";
				}else{
					repairLino = LineNo;
				}
				String Reason = "100010";
				String Status = null;
				try{
					RawRecord swipeRecord = new RawRecord();
					swipeRecord.setSwipeCardTime(swipeCardTime);
					swipeRecord.setWorkshopNo(WorkshopNo);
					swipeRecord.setLineNo(repairLino);
					int isRepairWorkshopNo = session.selectOne("isRepairWorkshopNo", swipeRecord);
					if(isRepairWorkshopNo==1){
						String swipeType = "1";
						String privilegeLevel = "3";
						Reason = getReasonNo(ReasonDesc);
						Status = "5";
						RepairWorkshopNo repairInfo = session.selectOne("getRepairInfo",swipeRecord);
						swipeCardService.updateSwipecardOut(session,swipeCardTime,repairInfo,Reason,Status,swipeType,privilegeLevel);
						session.commit();
						jtextT5_1.setBackground(Color.RED);
						jtextT5_1.setText("QC確認任然存在故障，請維修員重新維修！\n------------\n");
						p5swipecardText.setText("");
						p5But1_1.setEnabled(false);
						p5But1_2.setEnabled(false);
						p5swipecardText.setEnabled(true);
						p5swipecardText.requestFocusInWindow();
					}else{
						jtextT5_1.setBackground(Color.RED);
						jtextT5_1.setText("該線未添加到車間線別維修列表中！\n------------\n");
						p5swipecardText.setText("");
					}
				} catch (Exception e1) {
					session.rollback();
					logger.error("QC確認維修狀態错误，原因：" + e1);
					dispose();
					SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
				} finally {
					ErrorContext.instance().reset();
					if (session != null) {
						session.close();
					}
				}
				
			}
		});
		
		butT1_5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String bt = butT1_5.getText();
				if (bt.equals("更換車間")) {
					butT1_5.setText("刷卡上下班");
					jtextT1_1.setText("請管理員刷卡");
					jtextT1_1.setBackground(Color.WHITE);
					textT1_3.setEditable(false);
					textT1_6.setEditable(true);
					textT1_6.setVisible(true);
					labelT1_7.setVisible(true);
					// 使用swing的线程做獲取焦點的界面绘制，避免获取不到的情况。
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							textT1_6.requestFocusInWindow();
						}
					});
				} else {
					butT1_5.setText("更換車間");
					jtextT1_1.setText("刷卡上下班");
					jtextT1_1.setBackground(Color.WHITE);
					textT1_3.setEditable(true);
					textT1_6.setEditable(false);
					textT1_6.setVisible(false);
					labelT1_7.setVisible(false);
					// 使用swing的线程做獲取焦點的界面绘制，避免获取不到的情况。
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							textT1_3.requestFocusInWindow();
						}
					});
				}
			}
		});
		
		/*
		 * l 刷管理员的卡选择车间
		 */
		textT1_6.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {
				SqlSession session = sqlSessionFactory.openSession();
				// TODO Auto-generated method stub
				String cardID = textT1_6.getText();
				Date swipeCardTime = FormatDateUtil.getDateTime();
				String PROD_LINE_CODE = linenoLabel.getText();
				if (cardID.length() > 10) {
					jtextT1_1.setBackground(Color.RED);
					jtextT1_1.setText("卡號輸入有誤，請再次刷卡\n");
					textT1_6.setText("");
				} else {
					String pattern = "^[0-9]\\d{9}$";
					Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
					Matcher m = r.matcher(cardID);
					if (m.matches() == true) {
						try{
							//只要刷卡都將記錄至raw_record table
							Employee eif = (Employee) session.selectOne("selectUserByCardID", cardID);
							String Record_Status="13";
							addRawSwipeRecord(session, eif, cardID, swipeCardTime, WorkshopNo,Record_Status,PROD_LINE_CODE);
							boolean admin = IsAdminByCardID(cardID, session);
							if (admin) {
								dispose();
								SwipeCardLogin swipeCardLogin = new SwipeCardLogin();
								textT1_6.setText("");
							} else {
								jtextT1_1.setBackground(Color.RED);
								jtextT1_1.setText("您的卡权限不够\n请刷管理员的卡");
								textT1_6.setText("");
							}
						} catch (Exception e1) {
							logger.error("判断是否管理员错误，原因：" + e1);
							dispose();
							SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
						} finally {
							ErrorContext.instance().reset();
							if (session != null) {
								session.close();
							}
						}
					} else {
						System.out.println("無輸入內容或輸入錯誤!");
					}
				}
			}
		});

		butT1_6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				System.exit(0);
			}
		});

		butT2_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 中途刷卡原因
				jtf.setEditable(true);
			}
		});

		butT2_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int countRow = mytable.getRowCount();
				Boolean State = null;			
				SwipeCardService service=new SwipeCardService();
				String WorkshopNo = workShopNoJlabel.getText();
				String RC_NO = jtf.getText();
				String PRIMARY_ITEM_NO = textT2_1.getText();
				String Name = "", empID = "";
				SqlSession session = null;
				try {
					session = sqlSessionFactory.openSession();
					StringBuilder strBuilder = new StringBuilder();
					for (int i = 0; i < RC_NO.length(); i++) {
						char charAt = RC_NO.charAt(i);
						if (charAt == ' ')
							continue;
						strBuilder.append(charAt);
					}
					RC_NO = strBuilder.toString();
					
					if (!RC_NO.equals("") && RC_NO != "" && RC_NO != null) {						
						RCLine rcLine = new RCLine();
						rcLine.setPROD_LINE_CODE(WorkshopNo);
						rcLine.setRC_NO(RC_NO);
						rcLine.setPRIMARY_ITEM_NO(PRIMARY_ITEM_NO);
						boolean isaddItem = false;
						str1 = getRcLine();
						if (str1 != null) {
							for (Object item : str1) {
								if (((String) item).equals(RC_NO)) {
									isaddItem = false;
									break;
								} else {
									isaddItem = true;
								}
							}
						}
						if (!isaddItem) {
							for (int i = 0; i < countRow; i++) {
								State = (Boolean) mytable.getValueAt(i, 0);
								if (State == true) {
									empID = (String) mytable.getValueAt(i, 2);
									Name = (String) mytable.getValueAt(i, 3);
								    SwipeCardTimeInfos swipeInfo=new SwipeCardTimeInfos();
									swipeInfo.setEMP_ID(empID);
									swipeInfo.setRC_NO(RC_NO);
									swipeInfo.setPRIMARY_ITEM_NO(PRIMARY_ITEM_NO);
									session.update("Update_rcno_ByLineNOandCardID", swipeInfo);
									session.commit();
								}
							}
						}else{
							JOptionPane.showMessageDialog(null, "指示單號不存在!", "提示", JOptionPane.WARNING_MESSAGE);
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "指示單號不得為空!", "提示", JOptionPane.WARNING_MESSAGE);
					}

					panel2.remove(myScrollPane);
					myModel = new SwipeCardUserTableModel(WorkshopNo, "D" , LineNo);
					mytable = new JTable(myModel);
					setTable();
					myScrollPane = new JScrollPane(mytable);
					myScrollPane.setBounds(360, 40, 520, 400);
					panel2.add(myScrollPane);
					panel2.updateUI(); // 重绘
					panel2.repaint(); // 重绘此组件。
					// System.out.println("State!"+ mytable.getColumnClass(0));
				} catch (Exception e1) {
					System.out.println("Error opening session");
					logger.error("綁定指示單號失敗,原因:"+e1);
					dispose();
					SwipeCardNoDB d = new SwipeCardNoDB(WorkshopNo);
					throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e1, e1);
				} finally {
					ErrorContext.instance().reset();
					if (session != null) {
						session.close();
					}
				}
			}
		});

		butT2_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				update();
			}
		});

		butT2_rcno.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				str1 = getRcLine();
			}
		});
		
		comboBox.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});

		// TODO 刷卡模式
		textT1_3.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
				SqlSession session = sqlSessionFactory.openSession();
				SwipeCardService swipeCardService=new SwipeCardService();
				String CardID = textT1_3.getText();
				SwingBase fieldSetting=null;
				Date swipeCardTime = FormatDateUtil.getDateTime();
			    String swipeCardTimeStr = FormatDateUtil.changeTimeToStr(swipeCardTime);
				String curDate=FormatDateUtil.getCurDate();				
				String yesterdayDate=FormatDateUtil.getYesterdayDate();
				
				String WorkshopNo = workShopNoJlabel.getText();
				String PROD_LINE_CODE = linenoLabel.getText();
				// 驗證是否為10位整數，是則繼續執行，否則提示
				if (CardID.length() > 10) {
					jtextT1_1.setBackground(Color.red);
					jtextT1_1.append("卡號輸入有誤，請再次刷卡\n");
					textT1_3.setText("");
				} else if(CardID.length()<10){
					jtextT1_1.setBackground(Color.RED);
					jtextT1_1.append("卡號輸入有誤，請再次刷卡\n");
					textT1_3.setText("");
				}else {
					String pattern = "^[0-9]\\d{9}$";
					Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
					Matcher m = r.matcher(CardID);
					if (m.matches() == true) {
						try {
							// 通過卡號查詢員工個人信息
							// 1、判斷是否今天第一次刷卡
							// System.out.println("getRowsa: " +
							// rows.getRowsa());
							swipeTimeLable.setText(swipeCardTimeStr);

							Employee eif = (Employee) session.selectOne("selectUserByCardID", CardID);
							//只要刷卡都將記錄至raw_record table
							String Record_Status=null;
							addRawSwipeRecord(session, eif, CardID, swipeCardTime, WorkshopNo,Record_Status,PROD_LINE_CODE);
							RawRecord swipeRecord = new RawRecord();
							swipeRecord.setCardID(CardID);
							swipeRecord.setSwipeCardTime(swipeCardTime);
							List<String> machineBindingCostIdList = new ArrayList<String>();
							boolean isMachineBinding = false;
							
							if (eif != null) {
								int machineBinding = session.selectOne("selectMachineBindingCount", Realip);
								System.out.println("machineBinding:"+machineBinding);
								if(machineBinding>0){
									machineBindingCostIdList = session.selectList("selectMachineBinding", Realip);
									if(machineBindingCostIdList.size()>0){
										for (int i = 0; i < machineBindingCostIdList.size(); i++) {
											System.out.println("machineBindingCostIdList:"+machineBindingCostIdList.get(i));
											System.out.println(eif.getCostID()+"---"+machineBindingCostIdList.get(i).equals(eif.getCostID()));
											if(machineBindingCostIdList.get(i).equals(eif.getCostID())){
												isMachineBinding = true;
											}
										}
									}
								}else{
									isMachineBinding = true;
								}
							}
							
							if (eif == null) {	
								swipeRecord.setRecord_Status("1");
								int lostRows = session.selectOne("selectLoseEmployee", swipeRecord);				
								if (lostRows > 1) {
									
									jtextT1_1.append("已記錄當前異常刷卡人員，當前刷卡人員不存在，今天不用再次刷卡！\n");
									jtextT1_1.setBackground(Color.RED);
									textT1_3.setText("");
									session.update("updateRawRecordStatus",swipeRecord);
									session.commit();
									return;
								}
								/*
								 * JOptionPane.showMessageDialog(null,
								 * "當前刷卡人員不存在；可能是新進人員，或是舊卡丟失補辦，人員資料暫時未更新，請線長記錄，協助助理走原有簽核流程！"
								 * );
								 */
								jtextT1_1.append("當前刷卡人員不存在；可能是新進人員，或是舊卡丟失補辦，人員資料暫時未更新，請線長記錄，協助助理走原有簽核流程！\n");
								jtextT1_1.setBackground(Color.RED);	
								session.update("updateRawRecordStatus",swipeRecord);
								session.commit();

							} else if (!isMachineBinding) {
								jtextT1_1.append("工號：" + eif.getId() + " \n姓名：" + eif.getName() + "\n該卡機只允許"+machineBindingCostIdList+"刷卡！!\n");
								jtextT1_1.setBackground(Color.RED);
								swipeRecord.setId(eif.getId());
								swipeRecord.setRecord_Status("10");
								session.update("updateRawRecordStatus", swipeRecord);
								session.commit();
							} else {
								String name = eif.getName();
								String RC_NO = jtf.getText();
								String PRIMARY_ITEM_NO = textT2_1.getText();
								String Id = eif.getId();						
								//判斷該卡號是否已連續工作六天
								WorkedOneWeek workedOneWeek=swipeCardService.isUserContinuesWorkedOneWeek(session, eif, CardID, WorkshopNo, swipeCardTime);
								//是否卡七休一
//								workedOneWeek.setWorkedOneWeek(false);
								if(!workedOneWeek.isWorkedOneWeek()){					
									
									//該卡號是連續工作日小於六天
									EmpShiftInfos curShiftUser = new EmpShiftInfos();
								    curShiftUser.setId(Id);
								    curShiftUser.setShiftDay(0);
								     
								    EmpShiftInfos yesShiftUser = new EmpShiftInfos();
								    yesShiftUser.setId(Id);
								    yesShiftUser.setShiftDay(1);
								    
								    int empCurShiftCount =  session.selectOne("getShiftCount", curShiftUser);
									int empYesShiftCount =  session.selectOne("getShiftCount", yesShiftUser);
									EmpShiftInfos empYesShift = (EmpShiftInfos) session.selectOne("getShiftByEmpId", yesShiftUser);
								
									String yesterdayShift = "";
									if (empYesShiftCount > 0) {
										String yesterdayClassDesc = empYesShift.getClass_desc();
							
										yesterdayShift = empYesShift.getShift();
										if (yesterdayShift.equals("N")) {
											Timestamp yesClassEnd = empYesShift.getClass_end();
											Timestamp goWorkSwipeTime = new Timestamp(new Date().getTime());

											Calendar outWorkc = Calendar.getInstance();
											outWorkc.setTime(yesClassEnd);
											outWorkc.set(Calendar.HOUR_OF_DAY,
													outWorkc.get(Calendar.HOUR_OF_DAY) + 3);
											outWorkc.set(Calendar.MINUTE,
													outWorkc.get(Calendar.MINUTE) + 30);
											Date dt = outWorkc.getTime();
											Timestamp afterClassEnd = new Timestamp(dt.getTime());
											
											if (empCurShiftCount == 0) {
												if (goWorkSwipeTime.before(afterClassEnd)) {
													// 刷卡在夜班下班3.5小時之內,記為昨日夜班下刷
													fieldSetting=swipeCardService.offDutyNightShiftSwipeCard(session, RC_NO, PRIMARY_ITEM_NO, WorkshopNo, eif, swipeCardTime, empYesShift,PROD_LINE_CODE);
													showLabelContent(fieldSetting);
												}else{
													// 刷卡在夜班下班3.5小時之后,今日班別有誤
													jtextT1_1.setBackground(Color.red);
													jtextT1_1.append("ID: " + eif.getId() + " Name: " + eif.getName() + "\n班別有誤，請聯繫助理核對班別信息!\n");
													swipeRecord.setId(Id);
													swipeRecord.setRecord_Status("2");
													session.update("updateRawRecordStatus",swipeRecord);
													session.commit();
												}												
												
											} else {
												EmpShiftInfos empCurShift = (EmpShiftInfos) session.selectOne("getShiftByEmpId", curShiftUser);

												String curShift = empCurShift.getShift();
												String curClassDesc = empCurShift.getClass_desc();
												Timestamp curClassStart = empCurShift.getClass_start();
												Timestamp curClassEnd = empCurShift.getClass_end();						

												SwipeCardTimeInfos userNSwipe = new SwipeCardTimeInfos();
												Date SwipeCardTime2 = swipeCardTime;														
												userNSwipe.setEMP_ID(Id);
												userNSwipe.setSWIPE_DATE(yesterdayDate);
												userNSwipe.setSwipeCardTime2(SwipeCardTime2);
												userNSwipe.setRC_NO(RC_NO);
												userNSwipe.setPRIMARY_ITEM_NO(PRIMARY_ITEM_NO);
												userNSwipe.setShift(yesterdayShift);
												userNSwipe.setWorkshopNo(WorkshopNo);
												

												if (curShift.equals("N")) {													
													if (swipeCardTime.getHours() < 12) {
														fieldSetting=swipeCardService.offDutyNightShiftSwipeCard(session, RC_NO, PRIMARY_ITEM_NO, WorkshopNo, eif, SwipeCardTime2, empYesShift,PROD_LINE_CODE);
														showLabelContent(fieldSetting);
													} else {
														// 上班刷卡
														fieldSetting=swipeCardService.swipeCardRecord(session, eif, swipeCardTime, RC_NO, PRIMARY_ITEM_NO, WorkshopNo,PROD_LINE_CODE);
														showLabelContent(fieldSetting);
													}
												} else {													
													
													int goWorkNCardCount =  session
															.selectOne("selectGoWorkNByCardID", userNSwipe);
													if (goWorkNCardCount > 0) { 
														// 昨日夜班已存在上刷
														int yesterdaygoWorkCardCount =  session
																.selectOne("selectCountNByCardID", userNSwipe);
														if (yesterdaygoWorkCardCount == 0) {
															// 夜班下刷刷卡記錄不存在
															
															if (goWorkSwipeTime.before(afterClassEnd)) {
																// 刷卡在夜班下班3.5小時之內,記為昨日夜班下刷
																jtextT1_1.setBackground(Color.WHITE);
																jtextT1_1.append(
																		"下班刷卡\n" + "ID: " + eif.getId() + "\nName: "
																				+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																				+"\n昨日班別為:"+yesterdayClassDesc
																				+ "\n" + "員工下班刷卡成功！\n------------\n");
																session.update("updateOutWorkNSwipeTime", userNSwipe);
																session.commit();
															} else {
																// 刷卡在夜班下班3.5小時之后,記為今日白班上刷
																
																fieldSetting=swipeCardService.swipeCardRecord(session, eif, swipeCardTime, RC_NO, PRIMARY_ITEM_NO, WorkshopNo,PROD_LINE_CODE);
																showLabelContent(fieldSetting);
															}
														} else {
															// 夜班下刷刷卡記錄已存在
															int isOutWoakSwipeDuplicate =  session
																	.selectOne("isOutWorkSwipeDuplicate", userNSwipe);
															if (isOutWoakSwipeDuplicate > 0) {
																fieldSetting=swipeCardService.offDutySwipeDuplicate(session, eif, swipeCardTime, curShift);
																showLabelContent(fieldSetting);
															} else {
																fieldSetting=swipeCardService.swipeCardRecord(session, eif, swipeCardTime, RC_NO, PRIMARY_ITEM_NO, WorkshopNo,PROD_LINE_CODE);
																showLabelContent(fieldSetting);
															}
														}
													} else {													
														// 昨天夜班，今天白班的，昨日夜班上刷不存在，直接記為今天白班刷卡
														fieldSetting=swipeCardService.swipeCardRecord(session, eif, swipeCardTime, RC_NO, PRIMARY_ITEM_NO, WorkshopNo,PROD_LINE_CODE);
														showLabelContent(fieldSetting);																								
													}													
												}

											}
											
										} else {
											fieldSetting=swipeCardService.swipeCardRecord(session, eif, swipeCardTime, RC_NO, PRIMARY_ITEM_NO, WorkshopNo,PROD_LINE_CODE);
											showLabelContent(fieldSetting);
										}
									} else {
										fieldSetting=swipeCardService.swipeCardRecord(session, eif, swipeCardTime, RC_NO, PRIMARY_ITEM_NO, WorkshopNo,PROD_LINE_CODE);
										showLabelContent(fieldSetting);
									}
								}
								else{
									//該卡號已連續工作六天，顯示錯誤訊息
									jtextT1_1.append("工號："+eif.getId()+" 姓名："+eif.getName()+" 已連續上班六天，此次刷卡不列入記錄！!\n");
									jtextT1_1.setBackground(Color.RED);
									
									swipeRecord.setId(Id);
									swipeRecord.setRecord_Status("4");
									session.update("updateRawRecordStatus",swipeRecord);
									session.commit();
								}
							}
						} catch (Exception e1) {
							logger.error("刷卡異常,原因:"+e1);
							dispose();
							SwipeCardNoDB d = new SwipeCardNoDB(WorkshopNo);
							throw ExceptionFactory.wrapException("刷卡異常,原因:" + e1, e1);
						} finally {
							ErrorContext.instance().reset();
							if (session != null) {
								session.close();
							}
							textT1_3.setText("");
						}
						textT1_3.setText("");
					} else {
						System.out.println("無輸入內容或輸入錯誤!");
					}
				}
				}
			}
		});
		
		p3SwipeText.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					SqlSession session = sqlSessionFactory.openSession();
					SwipeCardService swipeCardService=new SwipeCardService();
					String CardID = p3SwipeText.getText();
					SwingBase fieldSetting=null;
					Date swipeCardTime = FormatDateUtil.getDateTime();
				    String swipeCardTimeStr = FormatDateUtil.changeTimeToStr(swipeCardTime);
					String curDate=FormatDateUtil.getCurDate();				
					String yesterdayDate=FormatDateUtil.getYesterdayDate();
					
					String WorkshopNo = p3WorkShopNoComboBox.getSelectedItem().toString();
					String PROD_LINE_CODE = p3LinkNoComboBox.getSelectedItem().toString();
					// 驗證是否為10位整數，是則繼續執行，否則提示
					if (CardID.length() > 10) {
						p3JTextArea.setBackground(Color.red);
						p3JTextArea.append("卡號輸入有誤，請再次刷卡\n");
						p3SwipeText.setText("");
					} else if(CardID.length()<10){
						p3JTextArea.setBackground(Color.RED);
						p3JTextArea.append("卡號輸入有誤，請再次刷卡\n");
						p3SwipeText.setText("");
					}else if(WorkshopNo.equals("--請選擇車間--")){
						p3JTextArea.setBackground(Color.RED);
						p3JTextArea.append("請選擇相應的車間\n");
						p3SwipeText.setText("");
					}else if(PROD_LINE_CODE.equals("請選擇線號")){
						p3JTextArea.setBackground(Color.RED);
						p3JTextArea.append("請選擇相應的線號，若沒有線號，請選擇'不需要選擇線號'\n");
						p3SwipeText.setText("");
					}else {
						if(PROD_LINE_CODE.equals("不需要選擇線號")){
							PROD_LINE_CODE = "";
						}
						String pattern = "^[0-9]\\d{9}$";
						Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
						Matcher m = r.matcher(CardID);
						if (m.matches() == true) {
							try {
								// 通過卡號查詢員工個人信息
								// 1、判斷是否今天第一次刷卡
								// System.out.println("getRowsa: " +
								// rows.getRowsa());
								p3swipeTimeLable.setText(swipeCardTimeStr);

								Employee eif = (Employee) session.selectOne("selectUserByCardID", CardID);
								//只要刷卡都將記錄至raw_record table
								String Record_Status="8";
								addRawSwipeRecord(session, eif, CardID, swipeCardTime, WorkshopNo,Record_Status,PROD_LINE_CODE);
								RawRecord swipeRecord = new RawRecord();
								swipeRecord.setCardID(CardID);
								swipeRecord.setSwipeCardTime(swipeCardTime);
								if (eif == null) {	
									swipeRecord.setRecord_Status("8");
									int lostRows = session.selectOne("selectLoseEmployee", swipeRecord);				
									if (lostRows > 1) {
										p3JTextArea.append("已記錄當前異常刷卡人員，當前刷卡人員不存在，今天不用再次刷卡！\n");
										p3JTextArea.setBackground(Color.RED);
										session.update("updateRawRecordStatus",swipeRecord);
										session.commit();
										return;
									}
									/*
									 * JOptionPane.showMessageDialog(null,
									 * "當前刷卡人員不存在；可能是新進人員，或是舊卡丟失補辦，人員資料暫時未更新，請線長記錄，協助助理走原有簽核流程！"
									 * );
									 */
									p3JTextArea.append("當前刷卡人員不存在；可能是新進人員，或是舊卡丟失補辦，人員資料暫時未更新，請線長記錄，協助助理走原有簽核流程！\n");
									p3JTextArea.setBackground(Color.RED);	
									session.update("updateRawRecordStatus",swipeRecord);
									session.commit();

								}else{
									p3JTextArea.append("換線刷卡\n" + "ID: " + eif.getId() + "\nName: "
											+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
											+ "\n" + "員工換線刷卡記錄成功！\n------------\n");
									p3JTextArea.setBackground(Color.WHITE);
								}
							}catch (Exception e1) {
								logger.error("換線刷卡異常,原因:"+e1);
								dispose();
								SwipeCardNoDB d = new SwipeCardNoDB(WorkshopNo);
								throw ExceptionFactory.wrapException("換線刷卡異常,原因:" + e1, e1);
							} finally {
								ErrorContext.instance().reset();
								if (session != null) {
									session.close();
								}
								p3SwipeText.setText("");
							}
							p3SwipeText.setText("");
						}
						
					}
				}
			}
		});

		c.add(tabbedPane);
		c.setBackground(Color.lightGray);
		
		// 使用swing的线程做獲取焦點的界面绘制，避免获取不到的情况。
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textT1_3.requestFocusInWindow();
			}
		});
		//添加鼠标监听事件
		tabbedPane.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(MouseEvent e){ //响应鼠标点击事件
				p(e);
		    }
			private void p(MouseEvent e){
				for(int i = 0; i< tabbedPane.getTabCount();i++){
					Rectangle rect = tabbedPane.getBoundsAt(i);
					if(rect.contains(e.getX(),e.getY())){
						if(i==0){
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									textT1_3.requestFocusInWindow();
								}
							});
						}else if(i==2){
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									p3SwipeText.requestFocusInWindow();
								}
							});
						}else if(i==4){
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									p5swipecardText.requestFocusInWindow();
								}
							});
						}
					}
				}
			}
		});
		
		
		/**
		 * 車間維護刷卡
		 * 
		 * 
		 * */
		p5swipecardText.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					// TODO Auto-generated method stub
					if(e.getKeyChar() == KeyEvent.VK_ENTER){
						SqlSession session = sqlSessionFactory.openSession();
						SwipeCardService swipeCardService=new SwipeCardService();
						String CardID = p5swipecardText.getText();
						SwingBase fieldSetting=null;
						Date swipeCardTime = FormatDateUtil.getDateTime();
					    String swipeCardTimeStr = FormatDateUtil.changeTimeToStr(swipeCardTime);
						String repairLino = null;
						String ReasonNO = null;
						String status = null;
						String swipecardClass = p5swipecardClassCombobox.getSelectedItem().toString();
						String swipeType = null;
						if(LineNo==null||LineNo.equals("")){
							repairLino = "0";
						}else{
							repairLino = LineNo;
						}
						String ReasonDesc = p5ReasonDescCombobox.getSelectedItem().toString();
						
						Boolean RepairSwipeInterval = false;
						Calendar begin=Calendar.getInstance();
						begin.setTime(RepairSwipe);
						begin.add(Calendar.SECOND,RepairSwipeSecond);
						if(swipeCardTime.before(begin.getTime())){
							RepairSwipeInterval=true;
						}
						// 驗證是否為10位整數，是則繼續執行，否則提示
						if (CardID.length() > 10) {
							jtextT5_1.setBackground(Color.red);
							jtextT5_1.append("卡號輸入有誤，請再次刷卡\n");
							p5swipecardText.setText("");
						} else if(CardID.length()<10){
							jtextT5_1.setBackground(Color.RED);
							jtextT5_1.append("卡號輸入有誤，請再次刷卡\n");
							p5swipecardText.setText("");
						}else if(WorkshopNo.equals("--請選擇車間--")){
							jtextT5_1.setBackground(Color.RED);
							jtextT5_1.append("請選擇相應的車間\n");
							p5swipecardText.setText("");
						}else if(RepairSwipeInterval){
							jtextT5_1.setBackground(Color.RED);
							jtextT5_1.append("兩次刷卡間隔需大於"+RepairSwipeSecond+"秒\n");
							p5swipecardText.setText("");
						}else {
							String pattern = "^[0-9]\\d{9}$";
							Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
							Matcher m = r.matcher(CardID);
							if (m.matches() == true) {
								try {
									// 通過卡號查詢員工個人信息
									// 1、判斷是否今天第一次刷卡
									// System.out.println("getRowsa: " +
									// rows.getRowsa());
									p3swipeTimeLable.setText(swipeCardTimeStr);

									Employee eif = (Employee) session.selectOne("selectUserByCardID", CardID);
									//只要刷卡都將記錄至raw_record table
									String Record_Status="12";
									addRawSwipeRecord(session, eif, CardID, swipeCardTime, WorkshopNo,Record_Status,LineNo);
									RawRecord swipeRecord = new RawRecord();
									swipeRecord.setCardID(CardID);
									swipeRecord.setSwipeCardTime(swipeCardTime);
									swipeRecord.setWorkshopNo(WorkshopNo);
									if (eif == null) {	
										swipeRecord.setLineNo(LineNo);
										swipeRecord.setRecord_Status("12");
										int lostRows = session.selectOne("selectLoseEmployee", swipeRecord);				
										if (lostRows > 1) {
											jtextT5_1.append("已記錄當前異常刷卡人員，當前刷卡人員不存在，今天不用再次刷卡！\n");
											jtextT5_1.setBackground(Color.RED);
											session.update("updateRawRecordStatus",swipeRecord);
											session.commit();
											return;
										}
										/*
										 * JOptionPane.showMessageDialog(null,
										 * "當前刷卡人員不存在；可能是新進人員，或是舊卡丟失補辦，人員資料暫時未更新，請線長記錄，協助助理走原有簽核流程！"
										 * );
										 */
										jtextT5_1.append("當前刷卡人員不存在；可能是新進人員，或是舊卡丟失補辦，人員資料暫時未更新，請線長記錄，協助助理走原有簽核流程！\n");
										jtextT5_1.setBackground(Color.RED);	
										session.update("updateRawRecordStatus",swipeRecord);
										session.commit();
									}else{
										swipeRecord.setLineNo(repairLino);
										int isRepairWorkshopNo = session.selectOne("isRepairWorkshopNo", swipeRecord);
										if(isRepairWorkshopNo==1){
											RepairWorkshopNo repairInfo = session.selectOne("getRepairInfo",swipeRecord);
											System.out.println(repairInfo);
											int privilegeCount = session.selectOne("getPrivilegeCount", eif.getId());
											if(privilegeCount == 1){
												String privilegeLevel = session.selectOne("selectPrivilegeLevel", eif.getId());
												if(privilegeLevel!=null){
													if(swipecardClass.equals("線別維護刷卡")){
														swipeType = "1";
														String UUID = null;
														//線長刷卡
														if(privilegeLevel.equals("1")){
															if(repairInfo.getStatus().equals("0")){
																UUID = UUID32.GetUUID32();
																ReasonNO = getReasonNo(ReasonDesc);
																status = "1";
																swipeCardService.updateRepairStatus(session,eif,swipeCardTime,repairInfo,status,UUID);
																swipeCardService.addLineLeaderSwipecardIn(session,eif,swipeCardTime,repairInfo,ReasonNO,UUID,swipeType,privilegeLevel);
																session.commit();
																jtextT5_1.setBackground(Color.WHITE);
																jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																		+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																		+ "\n" + "線長報故障成功！\n------------\n");
																p5swipecardText.setText("");
															}else if(repairInfo.getStatus().equals("4")){
																UUID = repairInfo.getRecv_id();
																ReasonNO = getReasonNo(ReasonDesc);
																status = "0";
																swipeCardService.updateRepairStatus(session,eif,swipeCardTime,repairInfo,status,null);
																swipeCardService.addLineLeaderSwipecardOut(session,eif,swipeCardTime,repairInfo,ReasonNO,swipeType,privilegeLevel);
																session.commit();
																jtextT5_1.setBackground(Color.WHITE);
																jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																		+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																		+ "\n" + "當前故障結案成功！\n------------\n");
																p5swipecardText.setText("");
															}else{
																if(repairInfo.getStatus().equals("1")){
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線已提報故障，請找相關人員維修！\n------------\n");
																}else if(repairInfo.getStatus().equals("5")){
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "QC確認任然存在故障，請維修員重新維修！\n------------\n");
																}else if(repairInfo.getStatus().equals("2")){
																	int noSwipeOutCount = session.selectOne("selectNoSwipeOutCount", repairInfo);
																	if(noSwipeOutCount>0){
																		List<Employee> empList = session.selectList("selectNoSwipeOut",repairInfo);
																		jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																				+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																				+ "\n" + "以下維修人員未刷結束維修刷卡,請先讓以下員工刷結束維修刷卡，再刷QC卡！\n------------\n");
																		jtextT5_1.setBackground(Color.RED);
																		p5swipecardText.setText("");
																	}else{
																		jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																				+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																				+ "\n" + "當前已無人維修，請QC確認維修是否完成！\n------------\n");
																	}
																}else if(repairInfo.getStatus().equals("3")){
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線QC正在確認是否維修完成！\n------------\n");
																}else{
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線狀態未知！\n------------\n");
																}
																jtextT5_1.setBackground(Color.RED);
																p5swipecardText.setText("");
															}
														}else if(privilegeLevel.equals("2")){
															//機修刷卡
															if(repairInfo.getStatus().equals("1")||repairInfo.getStatus().equals("5")){
																UUID = repairInfo.getRecv_id();
																ReasonNO = getReasonNo(ReasonDesc);
																status = "2";
																swipeCardService.addRepairSwipecardIn(session,eif,swipeCardTime,repairInfo,ReasonNO,swipeType,privilegeLevel);
																swipeCardService.updateRepairStatus(session,eif,swipeCardTime,repairInfo,status,null);
																session.commit();
																jtextT5_1.setBackground(Color.WHITE);
																jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																		+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																		+ "\n" + "開始修理線別故障！\n------------\n");
																p5swipecardText.setText("");
															}else if(repairInfo.getStatus().equals("2")){
																UUID = repairInfo.getRecv_id();
																ReasonNO = getReasonNo(ReasonDesc);
																fieldSetting = swipeCardService.mechanicInOfOut(session,eif,swipeCardTime,repairInfo,ReasonNO,swipeType,privilegeLevel);
																session.commit();
																showLabelContent5(fieldSetting);
															}else{
																if(repairInfo.getStatus().equals("0")){
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線未提報故障，請找線長提報故障！\n------------\n");
																}else if(repairInfo.getStatus().equals("4")){
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線QC已確認完畢，請找線長刷卡確認改故障結案！\n------------\n");
																}else if(repairInfo.getStatus().equals("3")){
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線QC正在確認是否維修完成！\n------------\n");
																}else{
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線狀態未知！\n------------\n");
																}
																jtextT5_1.setBackground(Color.RED);
																p5swipecardText.setText("");
															}
														}else if (privilegeLevel.equals("3")){
															//QC刷卡
															if(repairInfo.getStatus().equals("2")){
																int noSwipeOutCount = session.selectOne("selectNoSwipeOutCount", repairInfo);
																if(noSwipeOutCount>0){
																	List<Employee> empList = session.selectList("selectNoSwipeOut",repairInfo);
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "維修人員未刷結束維修刷卡,請先讓員工刷結束維修刷卡，再刷QC卡！\n------------\n");
																	jtextT5_1.setBackground(Color.RED);
																	p5swipecardText.setText("");
																}else{
																	UUID = repairInfo.getRecv_id();
																	ReasonNO = getReasonNo(ReasonDesc);
																	status = "3";
																	swipeCardService.addRepairSwipecardIn(session,eif,swipeCardTime,repairInfo,ReasonNO,swipeType,privilegeLevel);
																	swipeCardService.updateRepairStatus(session,eif,swipeCardTime,repairInfo,status,null);
																	session.commit();
																	p5But1_1.setEnabled(true);
																	p5But1_2.setEnabled(true);
																	p5swipecardText.setEnabled(false);
																	jtextT5_1.setBackground(Color.WHITE);
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "請QC確認維修是否成功！\n------------\n");
																	p5swipecardText.setText("");
																}
															}else{
																if(repairInfo.getStatus().equals("0")){
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線未提報故障，請找線長提報故障！\n------------\n");
																}else if(repairInfo.getStatus().equals("1")){
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線已提報故障，請找相關人員維修！\n------------\n");
																}else if(repairInfo.getStatus().equals("5")){
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "QC確認任然存在故障，請維修員重新維修！\n------------\n");
																}else if(repairInfo.getStatus().equals("4")){
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線QC已確認完畢，請找線長刷卡確認改故障結案！\n------------\n");
																}else if(repairInfo.getStatus().equals("3")){
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線QC正在確認是否維修完成！\n------------\n");
																}else{
																	jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																			+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																			+ "\n" + "當前線狀態未知！\n------------\n");
																}
																jtextT5_1.setBackground(Color.RED);
																p5swipecardText.setText("");
															}
														}else{
															jtextT5_1.setBackground(Color.RED);
															jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
																	+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																	+ "\n" + "該員工職位與線別維修無關！\n------------\n");
															p5swipecardText.setText("");
														}
														RepairWorkshopNo repairEmpInfo = session.selectOne("getRepairInfo",swipeRecord);
														if(repairEmpInfo.getStatus().endsWith("2")){
															int noSwipeOutCount = session.selectOne("selectNoSwipeOutCount", repairEmpInfo);
															if(noSwipeOutCount>0){
																List<Employee> empList = session.selectList("selectNoSwipeOut",repairEmpInfo);
																for(int e1 = 0;e1<empList.size();e1++){
																	jtextT5_1.append(empList.get(e1).getId() + "   " +empList.get(e1).getName()+"\n");
																}
															}else{
																jtextT5_1.append("當前已無人維修"+"\n");
															}
														}
													}else if(swipecardClass.equals("強制結束刷卡")){
														swipeType = "1";
														if(privilegeLevel.equals("1")){
															ReasonNO = getReasonNo(ReasonDesc);
															status = "0";
															if(repairInfo.getStatus().equals("0")){
																jtextT5_1.setBackground(Color.RED);
																jtextT5_1.setText("強制結束刷卡\n" + "ID: " + eif.getId() + "\nName: "
																		+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																		+ "\n" + "當前線別處於正常狀態，強制結束刷卡失敗！\n------------\n");
															}else{
																swipeCardService.updateSwipecardEnd(session,swipeCardTime,repairInfo,ReasonNO,status,eif);
																session.commit();
																jtextT5_1.setBackground(Color.WHITE);
																jtextT5_1.setText("強制結束刷卡\n" + "ID: " + eif.getId() + "\nName: "
																		+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																		+ "\n" + "線長強制結束維修成功！\n------------\n");
																p5swipecardText.setText("");
																p5But1_1.setEnabled(false);
																p5But1_2.setEnabled(false);
																p5swipecardText.setEnabled(true);
															}
														}else{
															jtextT5_1.setBackground(Color.RED);
															jtextT5_1.setText("強制結束刷卡\n" + "ID: " + eif.getId() + "\nName: "
																	+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																	+ "\n" + "該員工非線長，若是要強制結束維修請讓線長刷卡，若是維修線別刷卡，請把強制結束刷卡"
																			+ "改為線別維護刷卡！\n------------\n");
															p5swipecardText.setText("");
														}
													}else if(swipecardClass.equals("QC巡檢")){
														swipeType = "2";
														if(privilegeLevel.equals("3")){
															ReasonNO = getReasonNo(ReasonDesc);
															status = "0";
															repairInfo.setRecv_id("");
															fieldSetting = swipeCardService.otherSwipecardInOfOut(session,eif,swipeCardTime,repairInfo,ReasonNO,swipecardClass,swipeType,privilegeLevel);
															session.commit();
															showLabelContent5(fieldSetting);
															p5swipecardText.setText("");
														}else{
															jtextT5_1.setBackground(Color.RED);
															jtextT5_1.setText("QC巡檢刷卡\n" + "ID: " + eif.getId() + "\nName: "
																	+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
																	+ "\n" + "該員工未添加QC權限！\n------------\n");
															p5swipecardText.setText("");
														}
													}else if(swipecardClass.equals("打樣")){
															swipeType = "3";
															ReasonNO = getReasonNo(ReasonDesc);
															status = "0";
															repairInfo.setRecv_id("");
															fieldSetting = swipeCardService.otherSwipecardInOfOut(session,eif,swipeCardTime,repairInfo,ReasonNO,swipecardClass,swipeType,privilegeLevel);
															session.commit();
															showLabelContent5(fieldSetting);
															p5swipecardText.setText("");
													}else{
														jtextT5_1.setBackground(Color.RED);
														jtextT5_1.setText("刷卡類型未知，請重新啟動程序或者聯繫相關人員！\n------------\n");
														p5swipecardText.setText("");
													}
												}else{
													jtextT5_1.setBackground(Color.RED);
													jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
															+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
															+ "\n" + "該員工無權限！\n------------\n");
													p5swipecardText.setText("");
												}
											}else{
												jtextT5_1.setBackground(Color.RED);
												jtextT5_1.setText("線別維修刷卡\n" + "ID: " + eif.getId() + "\nName: "
														+ eif.getName() + "\n刷卡時間： " + swipeCardTimeStr
														+ "\n" + "該員工無權限！\n------------\n");
												p5swipecardText.setText("");
											}
										}else{
											jtextT5_1.setBackground(Color.RED);
											jtextT5_1.setText("該線未添加到車間線別維修列表中！\n------------\n");
											p5swipecardText.setText("");
										}
									}
								}catch (Exception e1) {
									e1.printStackTrace();
									session.rollback();
									logger.error("車間維護刷卡異常,原因:"+e1);
									dispose();
									SwipeCardNoDB d = new SwipeCardNoDB(WorkshopNo);
									throw ExceptionFactory.wrapException("車間維護刷卡異常,原因:" + e1, e1);
								} finally {
									ErrorContext.instance().reset();
									if (session != null) {
										session.close();
									}
									p5swipecardText.setText("");
								}
								p5swipecardText.setText("");
								RepairSwipe = new Date();
							}
							
						}
					}
				}
			}
		});

		//textT1_1.setText(WorkshopNo);// 綁定車間
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	private void showLabelContent(SwingBase fieldSetting) {
		jtextT1_1.append(fieldSetting.getFieldContent());
		jtextT1_1.setBackground(fieldSetting.getFieldColor());
	}
	
	private void showLabelContent5(SwingBase fieldSetting) {
		jtextT5_1.setText(fieldSetting.getFieldContent());
		jtextT5_1.setBackground(fieldSetting.getFieldColor());
	}
	
	public String getShiftByClassDesc(String classDesc) {
		String shift = null;
		if (classDesc.indexOf("日") != -1 || classDesc.indexOf("中") != -1) {
			shift = "D";
		} else if (classDesc.indexOf("夜") != -1) {
			shift = "N";
		}
		return shift;
	}

	private void breakShow() {
		return;
	}

	private static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
	
	public static void main(String args[]) {
		boolean OneWindow = OpenOneWindow.checkLock();
		//秦川那邊不卡單開程序
		//Boolean OneWindow = true;
		if (OneWindow) {
		InitGlobalFont(new Font("微软雅黑", Font.BOLD, 18));
		SwipeCardService swipeCardService = new SwipeCardService();
		String WorkShopNo = "";
		String LineNo ="";
		if(defaultLineNo != null){
			LineNo = defaultLineNo;
		}
		if (defaultWorkshopNo != null) {
			WorkShopNo = defaultWorkshopNo;
			SwipeCard d = new SwipeCard(WorkShopNo,LineNo);
		} else {
			SwipeCardLogin d = new SwipeCardLogin();
		}
		//檢測ip是否可用
		String ip = GetLocalHostIpAndName.getLocalIp();
		CheckIp checkIp = new CheckIp(ip);
		Thread executeCheckIp = new Thread(checkIp);
		executeCheckIp.start();
		//检测版本是否最新
		CheckCurrentVersion chkVersion = new CheckCurrentVersion(CurrentVersion);
		Thread executeCheckVersion = new Thread(chkVersion);
		executeCheckVersion.start();
		SwipeRecordLogToDB logToDB=new SwipeRecordLogToDB();
		logToDB.SwipeRecordLogToDB();
		} else {
			JOptionPane.showConfirmDialog(null, "程序已經開啟，請不要重複開啟", "程序重複打開", JOptionPane.DEFAULT_OPTION);
			System.exit(0);
		}
	}

	public void update() {
		// String LineNo = textT1_2.getText();
		//String WorkshopNo = textT1_1.getText();
		String WorkshopNo = workShopNoJlabel.getText();
		Object ShiftName = comboBox2.getSelectedItem();
		String LineNo = linenoLabel.getText();
		System.out.println("comboBox2" + ShiftName);
		String ShiftRcNo = "";
		if (ShiftName.equals("夜班")) {
			ShiftRcNo = "N";
		} else {
			ShiftRcNo = "D";
		}

		panel2.remove(myScrollPane);
		myModel = new SwipeCardUserTableModel(WorkshopNo, ShiftRcNo , LineNo);
		mytable = new JTable(myModel);
		setTable();
		myScrollPane = new JScrollPane(mytable);
		myScrollPane.setBounds(360, 40, 520, 400);
		
		panel2.add(myScrollPane);
		panel2.updateUI();
		panel2.repaint();
	}

	public void setTable() {
		mytable.getColumnModel().getColumn(0).setMaxWidth(40);
		mytable.getColumnModel().getColumn(1).setMaxWidth(40);
		mytable.getColumnModel().getColumn(2).setMaxWidth(60);
		mytable.setRowHeight(25);
		mytable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTableHeader header = mytable.getTableHeader();
		header.setFont(new Font("微软雅黑", Font.BOLD, 16));
		header.setPreferredSize(new Dimension(header.getWidth(), 30));
	}
	
	public void setEmpTable() {
		emptable.getColumnModel().getColumn(0).setMaxWidth(50);
		emptable.setRowHeight(25);
		emptable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		JTableHeader header = emptable.getTableHeader();
		header.setFont(new Font("微软雅黑", Font.BOLD, 16));
		header.setPreferredSize(new Dimension(header.getWidth(), 30));
	}
	
	private Object[] getRcLine() {
		List<RCLine> rcLine;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			rcLine = session.selectList("selectRCNo");
			int con = rcLine.size();
			System.out.println("rcLine:"+rcLine.size());
			Object[] a = null;
			if (con > 0) {
				a = new Object[con + 1];
				a[0] = "";
				for (int i = 1; i < con + 1; i++) {
					a[i] = rcLine.get(i - 1).getRC_NO();
				}
			}
			else {
				a = new Object[1];
				a[0] = "";
			}
			final Object[] s = a;
			return a;
		} catch (Exception e1) {
			System.out.println("Error opening session");
			dispose();
			SwipeCardNoDB d = new SwipeCardNoDB(null);
			throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e1, e1);
		} finally {
			ErrorContext.instance().reset();
			if (session != null) {
				session.close();
			}
		}
	}
	
	protected boolean IsAdminByCardID(String cardID, SqlSession session) {
		// TODO Auto-generated method stub
		try {

			int isAdmin = session.selectOne("isAdminByCardID", cardID);
			if (isAdmin > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("判断是否管理员错误，原因：" + e);
			dispose();
			SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
		} finally {
			ErrorContext.instance().reset();
			if (session != null) {
				session.close();
			}
		}
		return false;
	}
	
	/*當員工刷卡時，立即記錄一筆刷卡資料至raw_record table中*/
	public void addRawSwipeRecord(SqlSession session, Employee eif, String CardID,Date SwipeCardTime,String WorkshopNo,String Record_Status, String pROD_LINE_CODE) {
		String Id=null;
		try {
			if(eif!=null)
				Id=eif.getId();
			if(Id==null){
				Id="";
			}
			synchronized (this) {	
				GetLocalHostIpAndName hostIP=new GetLocalHostIpAndName();
				String swipeCardHostIp=hostIP.getLocalIp();
				
				RawRecord swipeRecord=new RawRecord();
				swipeRecord.setCardID(CardID);
				swipeRecord.setId(Id);
				swipeRecord.setSwipeCardTime(SwipeCardTime);
				swipeRecord.setRecord_Status(Record_Status);
				swipeRecord.setSwipeCardHostIp(swipeCardHostIp);
				swipeRecord.setWorkshopNo(WorkshopNo);
				swipeRecord.setLineNo(pROD_LINE_CODE);
				session.insert("addRawSwipeRecord", swipeRecord);
				session.commit();
			}
		}
		catch(Exception ex) {
			dispose();
			SwipeCardNoDB d = new SwipeCardNoDB(WorkshopNo);
			ex.printStackTrace();
		}
	}
	
	public Object[] getWorkshopNo() {// TODO
		List<LineNO> workshopNoInfo;
		Object[] a = null;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			JSONArray workshopNoArray = new JSONArray();
			workshopNoInfo = session.selectList("selectWorkshopNo");
			int con = workshopNoInfo.size();
			if (con > 0) {
				a = new Object[con + 1];
				a[0] = "--請選擇車間--";
				for (int i = 1; i < con + 1; i++) {
					a[i] = workshopNoInfo.get(i - 1).getWorkShopNo();
					JSONObject workshopNoJson = new JSONObject();
					workshopNoJson.put("workshopNo", a[i].toString());
					workshopNoArray.put(workshopNoJson);
				}
				String fileName = "WorkshopNo.json";
				jsonFileUtil.createWorkshopNoJsonFile(workshopNoArray.toString(), fileName);
			} else {
				a = new Object[1];
				a[0] = "--請選擇車間--";
			}
		} catch (Exception e) {
			logger.error("取得車間異常,原因" + e);
			dispose();
			SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
			throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
			if (session != null) {
				session.close();
			}
		}
		return a;
	}
	
	private JSONObject getLineNoObject() {
		// TODO Auto-generated method stub
		List<LineNO> LineNo;
		JSONObject jsonObject = new JSONObject();
		Map<String, String> map = new HashMap<String, String>();
		SqlSession session = sqlSessionFactory.openSession();
		try {
			LineNo = session.selectList("selectLineNoList");
			int con = LineNo.size();
			if (con > 0) {
				for (int i = 0; i < con; i++) {
					if (map.containsKey(LineNo.get(i).getWorkShopNo())) {
						String str = map.get(LineNo.get(i).getWorkShopNo()) + "," + LineNo.get(i).getLineNo();
						map.put(LineNo.get(i).getWorkShopNo(), str);
					} else {
						map.put(LineNo.get(i).getWorkShopNo(), LineNo.get(i).getLineNo());
					}
				}
				Iterator<Entry<String, String>> entries = map.entrySet().iterator();
				while (entries.hasNext()) {
					Entry<String, String> entry = entries.next();
					String str = "";
					if(entry.getValue() == null){
						jsonObject.put(entry.getKey(), str);
					}else{
						jsonObject.put(entry.getKey(), entry.getValue());
					}
				}
				String fileName = "LineNo.json";
				jsonFileUtil.createWorkshopNoJsonFile(jsonObject.toString(), fileName);
			}
		} catch (Exception e) {
			System.out.println("Error opening session");
			logger.error("取得线体異常,原因" + e);
			dispose();
			SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
			throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
			if (session != null) {
				session.close();
			}
		}
		System.out.println(jsonObject);
		return jsonObject;
	}
	
	public Object[] getLineno(String selectWorkshopNo) {// TODO
		String linenoList;
		Object[] a = null;
		Object[] s = null;
		ArrayList<Object> list = new ArrayList<Object>();
		System.out.println(selectWorkshopNo);
		if (!(selectWorkshopNo == null || selectWorkshopNo.equals("") || selectWorkshopNo.equals("--請選擇車間--"))) {
			if (!(LineNoObject == null || LineNoObject.equals(""))) {
				linenoList = LineNoObject.getString(selectWorkshopNo);
				System.out.println(linenoList);
				if (!(linenoList == null || linenoList.equals(""))) {
					s = linenoList.split(",");
					int con = s.length;
					for (int i = 0; i < con; i++) {
						String str;
						str = s[i].toString().trim();
						if (!(str == null || str.equals("") || str.equals("null"))) {
							list.add(s[i]);
						}
					}
					int lcon = list.size();
					System.out.println(lcon);
					if (lcon > 0) {
						a = new Object[lcon + 2];
						a[0] = "請選擇線號";
						a[1] = "不需要選擇線號";
						for (int i = 0; i < lcon; i++) {
							a[i + 2] = list.get(i);
							System.out.println(list.get(i).toString());
						}
					}
				}
			}
		}
		return a;
	}
	
	public Object[] getReasonClass() {
		List<String> ReasonClassList;
		Object[] a = null;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			ReasonClassList = session.selectList("selectReasonClassList");
			if(ReasonClassList!=null){
				int con = ReasonClassList.size();
				if (con > 0) {
					a = new Object[con];
					for (int i = 0; i < con; i++) {
						a[i] = ReasonClassList.get(i);
					}
				} else {
					a = new Object[1];
					a[0] = "無維修類別";
				}
			}else{
				a = new Object[1];
				a[0] = "無維修類別";
			}
		} catch (Exception e) {
			logger.error("取得維修類別,原因" + e);
			dispose();
			SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
			throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
			if (session != null) {
				session.close();
			}
		}
		return a;
	}
	
	public Object[] getReasonDesc(String ReasonClass) {
		Object[] a = null;
		if(ReasonClass.equals("無維修類別")||ReasonClass==null){
			a = new Object[1];
			a[0] = "無維修原因";
		}else{
			SqlSession session = sqlSessionFactory.openSession();
			try {
				ReasonDescList = session.selectList("selectReasonDescList",ReasonClass);
				if(ReasonDescList!=null){
					int con = ReasonDescList.size();
					if (con > 0) {
						a = new Object[con];
						for (int i = 0; i < con; i++) {
							a[i] = ReasonDescList.get(i).getReason_Desc();
						}
					} else {
						a = new Object[1];
						a[0] = "無維修原因";
					}
				}else{
					a = new Object[1];
					a[0] = "無維修原因";
				}
			} catch (Exception e) {
				logger.error("取得維修類別,原因" + e);
				dispose();
				SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
				throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
			} finally {
				ErrorContext.instance().reset();
				if (session != null) {
					session.close();
				}
			}
		}
		return a;
	}
	
	public String getReasonNo(String ReasonDesc) {
		String ReasonNo = null;
		if(ReasonDescList!=null){
			for(int i = 0;i<ReasonDescList.size();i++){
				if(ReasonDescList.get(i).getReason_Desc().equals(ReasonDesc)){
					ReasonNo = ReasonDescList.get(i).getReason_No();
				}
			}
		}
		if(ReasonNo==null){
			ReasonNo = "999999";
		}
		return ReasonNo;
	}
	
	public Object[] getRepairWorkshopNo() {// TODO
		List<String> workshopNoInfo;
		Object[] a = null;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			workshopNoInfo = session.selectList("selectRepairWorkshopNo");
			if(workshopNoInfo!=null){
				int con = workshopNoInfo.size();
				if (con > 0) {
					a = new Object[con + 1];
					a[0] = "--請選擇車間--";
					for (int i = 1; i < con + 1; i++) {
						a[i] = workshopNoInfo.get(i - 1);
					}
				} else {
					a = new Object[1];
					a[0] = "--無車間--";
				}
			}else{
				a = new Object[1];
				a[0] = "--無車間--";
			}
		} catch (Exception e) {
			logger.error("取得車間異常,原因" + e);
			dispose();
			SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
			throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
			if (session != null) {
				session.close();
			}
		}
		return a;
	}
	
	public void getRepairLineNoStatus(String workshopNo) {// TODO
		List<RepairWorkshopNo> RepairLineNoStatus;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			panel6_1.removeAll();
			if(workshopNo.endsWith("--請選擇車間--")||workshopNo.equals("--無車間--")){
				JButton buttontest1 = new RoundButton("無該車間資料");
				buttontest1.setFont(new Font("微软雅黑", Font.BOLD, 25));
				buttontest1.setBounds( 25,  30, 250, 100);
				panel6_1.add(buttontest1);
			}else{
				RepairLineNoStatus = session.selectList("getRepairLineNoStatus",workshopNo);
				if(RepairLineNoStatus!=null){
					int con = RepairLineNoStatus.size();
					if (con > 0) {
						for(int s1 = 0;s1<RepairLineNoStatus.size();s1++){
							JButton buttontest1 = new JButton(RepairLineNoStatus.get(s1).getLineno());
							String RepairStatus = RepairLineNoStatus.get(s1).getStatus();
							if(RepairStatus.equals("0")){
								buttontest1.setBackground(Color.green);
							}else if(RepairStatus.equals("1")||RepairStatus.equals("5")){
								buttontest1.setBackground(Color.red);
							}else if(RepairStatus.equals("2")){
								buttontest1.setBackground(Color.YELLOW);
							}else if(RepairStatus.equals("3")){
								Color col = new Color(187,255,255);
								buttontest1.setBackground(col);
							}else{
								buttontest1.setBackground(Color.PINK);
							}
							buttontest1.setFont(new Font("微软雅黑", Font.BOLD, 25));
							int row = (int) (Math.ceil(s1/4));
							int hang = s1 % 4;
							buttontest1.setBounds(hang * 260 + 25, row * 110 + 30, 250, 100);
							panel6_1.add(buttontest1);
						}
					}else{
						JButton buttontest1 = new RoundButton("無該車間資料");
						buttontest1.setFont(new Font("微软雅黑", Font.BOLD, 25));
						buttontest1.setBounds( 25,  30, 250, 100);
						panel6_1.add(buttontest1);
					}
				}else{
					JButton buttontest1 = new RoundButton("無該車間資料");
					buttontest1.setFont(new Font("微软雅黑", Font.BOLD, 25));
					buttontest1.setBounds( 25,  30, 250, 100);
					panel6_1.add(buttontest1);
				}
			}
			panel6_1.updateUI();
			panel6_1.repaint();
		} catch (Exception e) {
			logger.error("取得車間異常,原因" + e);
			dispose();
			SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
			throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
			if (session != null) {
				session.close();
			}
		}
	}
	
	public String checkRepairStatus(String WorkshopNo,String LineNo){
		SqlSession session = sqlSessionFactory.openSession();
		String repairLino = null;
		String repairStatus = null;
		if(LineNo==null||LineNo.equals("")){
			repairLino = "0";
		}else{
			repairLino = LineNo;
		}
		try {
			RawRecord swipeRecord = new RawRecord();
			swipeRecord.setWorkshopNo(WorkshopNo);
			swipeRecord.setLineNo(repairLino);
			int isRepairWorkshopNo = session.selectOne("isRepairWorkshopNo", swipeRecord);
			if(isRepairWorkshopNo==1){
				RepairWorkshopNo repairInfo = session.selectOne("getRepairInfo",swipeRecord);
				repairStatus=repairInfo.getStatus();
			}else{
				repairStatus = "99";
				jtextT5_1.setBackground(Color.RED);
				jtextT5_1.setText("該線未添加到車間線別維修列表中！\n------------\n");
				p5swipecardText.setText("");
			}
		} catch (Exception e) {
			logger.error("線別維修狀態異常,原因" + e);
			dispose();
			SwipeCardNoDB d = new SwipeCardNoDB(defaultWorkshopNo);
			throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
			if (session != null) {
				session.close();
			}
		}
		return repairStatus;
	}
	
	public List<Employee> getRepairEmp(String WorkshopNo,String LineNo){
		SqlSession session = sqlSessionFactory.openSession();
		RawRecord swipeRecord = new RawRecord();
		String repairLino = null;
		if(LineNo==null||LineNo.equals("")){
			repairLino = "0";
		}else{
			repairLino = LineNo;
		}
		swipeRecord.setWorkshopNo(WorkshopNo);
		swipeRecord.setLineNo(repairLino);
		List<Employee> empList = null;
		RepairWorkshopNo repairEmpInfo = session.selectOne("getRepairInfo",swipeRecord);
		if(repairEmpInfo.getStatus().endsWith("2")){
			int noSwipeOutCount = session.selectOne("selectNoSwipeOutCount", repairEmpInfo);
			if(noSwipeOutCount>0){
				empList = session.selectList("selectNoSwipeOut",repairEmpInfo);
			}
		}
		return empList;
	}
	
}
