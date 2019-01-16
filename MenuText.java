package com.experiment;
/*
 * 功能：简易记事本的开发，可以保存文件，打开文件，退出记事本
 * author：尚志刚
 */
import javax.swing.*;
 
import java.awt.*;
import java.awt.event.*;
import java.io.*;
 
public class MenuText {
	
	//定义组件：
	JFrame f;
	MenuBar mb;    //菜单栏
	Menu mu;       //菜单
	JTextArea jta;
	MenuItem openItem, saveItem, closeItem;   //子菜单
	FileDialog openDia,saveDia;    //弹出的保存和打开框	
	File file;
	//构造函数
	public MenuText()
	{
		//调用初始化函数
		init();
	}
    //对组件进行初始化操作
	public void init()
	{
		f=new JFrame("简易记事本");
		mb=new MenuBar();
		mu=new Menu("文件");
		openItem=new MenuItem("打开");
		saveItem=new MenuItem("保存");
		closeItem=new MenuItem("退出");
		jta=new JTextArea();
		
		f.add(jta);
		//添加
		mu.add(openItem);
		mu.add(saveItem);
		mu.add(closeItem);
		mb.add(mu);
		
		f.setMenuBar(mb);
		openDia=new FileDialog(f, "打开", FileDialog.LOAD);
		saveDia=new FileDialog(f, "保存", FileDialog.SAVE);
		
		//设置JFrame属性
		f.setBounds(200, 300, 500, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		//调用事件函数
		event();
	}
	
	//事件函数，对事件进行处理
	public void event()
	{
		//打开选项
		openItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
		
			//调用打开文件的方法
				openFile();							
			}
		});
		
		//保存选项
		saveItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {	
			//调用保存文件的方法。
	         saveFile();					
			}
		});
		//添加一个文本区域的事件，即按下Ctrl+S可以保存
		//键盘监听事件使用适配器KeyAdapter
		jta.addKeyListener(new KeyAdapter()
		{
			//键盘按下方法
			public void keyPressed(KeyEvent e){
	
				if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S)
				{
					//调用保存文件的方法。
			         saveFile();	
				}
			}
		});
		
		//关闭选项
		closeItem.addActionListener(new ActionListener()
		{ 
			@Override
			public void actionPerformed(ActionEvent e) {			
				//退出系统
				System.exit(0);				
			}		
		});
	}
	
	//打开文本的方法
	public void openFile()
	{
		openDia.setVisible(true); //设置其显示出来	
		//获取路径和文件名
		String dirPath=openDia.getDirectory();
		String fileName=openDia.getFile();
		
		//防止点击取消报错
		if(dirPath==null || fileName==null)
			return ;
		
		jta.setText(""); //将文本区域清空		
		file=new File(dirPath,fileName); //建立文件对象
		
		//按照行来读取数据，显示在文本区域
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			String line = null; 
			while((line=br.readLine())!=null)
			{
				jta.append(line+"\r\n");
			} 
			br.close();
		}
		catch (IOException ex)
		{
			throw new RuntimeException("读取失败");
		}
	}
	//保存文本的方法。
	public void saveFile()
	{
		//先判断文件是否存在
		if(file==null)
		{
			saveDia.setVisible(true);			
			String dirPath = saveDia.getDirectory();
			String fileName = saveDia.getFile();
			
			//防止点击取消报错
			if(dirPath==null || fileName==null)
				return;
			//因为文件不存在。所以需要建立file对象
			file = new File(dirPath,fileName);				
		}		
		//将数据写入文件
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(file));			
			String info=jta.getText();  //得到文本区域的信息			
			bw.write(info);  //写入操作
			bw.flush();
			bw.close();			
		} catch (IOException e1) {			
			throw new RuntimeException();
		}			
	}
	
	public static void main(String[] args) {		
           //创建对象
		new MenuText();
	}
}
