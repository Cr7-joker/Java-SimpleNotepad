package com.experiment;
/*
 * ���ܣ����׼��±��Ŀ��������Ա����ļ������ļ����˳����±�
 * author����־��
 */
import javax.swing.*;
 
import java.awt.*;
import java.awt.event.*;
import java.io.*;
 
public class MenuText {
	
	//���������
	JFrame f;
	MenuBar mb;    //�˵���
	Menu mu;       //�˵�
	JTextArea jta;
	MenuItem openItem, saveItem, closeItem;   //�Ӳ˵�
	FileDialog openDia,saveDia;    //�����ı���ʹ򿪿�	
	File file;
	//���캯��
	public MenuText()
	{
		//���ó�ʼ������
		init();
	}
    //��������г�ʼ������
	public void init()
	{
		f=new JFrame("���׼��±�");
		mb=new MenuBar();
		mu=new Menu("�ļ�");
		openItem=new MenuItem("��");
		saveItem=new MenuItem("����");
		closeItem=new MenuItem("�˳�");
		jta=new JTextArea();
		
		f.add(jta);
		//���
		mu.add(openItem);
		mu.add(saveItem);
		mu.add(closeItem);
		mb.add(mu);
		
		f.setMenuBar(mb);
		openDia=new FileDialog(f, "��", FileDialog.LOAD);
		saveDia=new FileDialog(f, "����", FileDialog.SAVE);
		
		//����JFrame����
		f.setBounds(200, 300, 500, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		//�����¼�����
		event();
	}
	
	//�¼����������¼����д���
	public void event()
	{
		//��ѡ��
		openItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
		
			//���ô��ļ��ķ���
				openFile();							
			}
		});
		
		//����ѡ��
		saveItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {	
			//���ñ����ļ��ķ�����
	         saveFile();					
			}
		});
		//���һ���ı�������¼���������Ctrl+S���Ա���
		//���̼����¼�ʹ��������KeyAdapter
		jta.addKeyListener(new KeyAdapter()
		{
			//���̰��·���
			public void keyPressed(KeyEvent e){
	
				if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S)
				{
					//���ñ����ļ��ķ�����
			         saveFile();	
				}
			}
		});
		
		//�ر�ѡ��
		closeItem.addActionListener(new ActionListener()
		{ 
			@Override
			public void actionPerformed(ActionEvent e) {			
				//�˳�ϵͳ
				System.exit(0);				
			}		
		});
	}
	
	//���ı��ķ���
	public void openFile()
	{
		openDia.setVisible(true); //��������ʾ����	
		//��ȡ·�����ļ���
		String dirPath=openDia.getDirectory();
		String fileName=openDia.getFile();
		
		//��ֹ���ȡ������
		if(dirPath==null || fileName==null)
			return ;
		
		jta.setText(""); //���ı��������		
		file=new File(dirPath,fileName); //�����ļ�����
		
		//����������ȡ���ݣ���ʾ���ı�����
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
			throw new RuntimeException("��ȡʧ��");
		}
	}
	//�����ı��ķ�����
	public void saveFile()
	{
		//���ж��ļ��Ƿ����
		if(file==null)
		{
			saveDia.setVisible(true);			
			String dirPath = saveDia.getDirectory();
			String fileName = saveDia.getFile();
			
			//��ֹ���ȡ������
			if(dirPath==null || fileName==null)
				return;
			//��Ϊ�ļ������ڡ�������Ҫ����file����
			file = new File(dirPath,fileName);				
		}		
		//������д���ļ�
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(file));			
			String info=jta.getText();  //�õ��ı��������Ϣ			
			bw.write(info);  //д�����
			bw.flush();
			bw.close();			
		} catch (IOException e1) {			
			throw new RuntimeException();
		}			
	}
	
	public static void main(String[] args) {		
           //��������
		new MenuText();
	}
}
