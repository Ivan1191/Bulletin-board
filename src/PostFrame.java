import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PostFrame extends JFrame
   implements ItemListener, ActionListener
   {
   private JButton likeButton, cancelButton; 
   private JButton editButton, importButton;
   private JButton writeButton, saveButton, renewButton;
   private JTextArea text;
   private JLabel IconLabel;
   private JComboBox<String> weatherBox;
   private static int preWeather, currentWeather;
   private static String editText, demo;
   private static boolean islike;
   private ImageIcon bug1, bug2;
   private JLabel time, name, name1;
   private Date editTime = new Date();
   private JPanel mainPanel, mainPanel2, topPanel, topPanel1;
   private Color color;
   private static ObjectInputStream input;
   private static final String[] names = {"sunny","rainy","cloudy"}; 
   private static final String[] weathers = {"sunny.png","rainy.png","cloudy.png"}; 
   private final Icon[] icons = { 
		      new ImageIcon(getClass().getResource(weathers[0])),
		      new ImageIcon(getClass().getResource(weathers[1])), 
		      new ImageIcon(getClass().getResource(weathers[2]))}; 


   public PostFrame(int option) throws Exception
   {
	  
	  super("Post");
	  
	  try
      {
		//post�����eŪ�J���i�ϩM�s��ɶ��M�R��
		InputStream post = Files.newInputStream(Paths.get("src/post"));		
		input = new ObjectInputStream(post);	
		PostSerializable file =  (PostSerializable) input.readObject();
		
		time = new JLabel("   "+file.getEditTime().toString()); 
		islike = file.getIsLike(); // �w�]�O�S���R��
		text = new JTextArea(file.getContent(),13,60);
		demo = file.getContent(); 
		preWeather = file.getWeather(); // �����w�]���Ѯ𴸤� 
	    IconLabel = new JLabel(icons[preWeather]);
      }
	  catch(IOException i)
      {
		  System.out.println("IOException error.");
      }
	  catch(ClassNotFoundException c)
	  {
		  System.out.println("ClassNotFoundException error");
	  } 
	  finally {
			try {
				if (input != null) {
					input.close(); // ������~������귽����L�HŪ��
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // �Nclose�g�bfinally�O�T�O�����`�ɤ]�ॿ�`������Ƭy
	  
	  
	  topPanel = new JPanel();
	  topPanel1 = new JPanel();

	  color = new Color(0,84,50);
	  topPanel.setBackground(color);
	  topPanel1.setBackground(color);
	  topPanel.setLayout(new GridLayout(3,1)); // 5�O���Layout�������Z��
      name = new JLabel(" POST");
      name1 = new JLabel("");
      
      weatherBox = new JComboBox<String>(names);
      weatherBox.setMaximumRowCount(3);
      weatherBox.addItemListener(this);
      
      name.setFont(new Font("Dialog", Font.BOLD, 25));
      name1.setFont(new Font("Dialog", Font.BOLD, 15));
      
      name.setForeground(Color.white);
      name1.setForeground(Color.white);
	  IconLabel.setForeground(Color.white);
	  time.setForeground(Color.white);
	  
	  FlowLayout layout = new FlowLayout(FlowLayout.LEFT,5,5); // panel�a��
     
	  topPanel1.add(name1);
	  topPanel1.add(IconLabel);
	  topPanel1.setLayout(layout); 
	  
	  topPanel.add(name);
	  topPanel.add(topPanel1);
	  topPanel.add(time);
	  topPanel.setPreferredSize(new Dimension(50, 105)); // change the size of topPanel
	  
	  add(topPanel, BorderLayout.NORTH);

	  mainPanel = new JPanel();
	  mainPanel.setBackground(color);
	  text.setForeground(Color.YELLOW.darker());
	  text.setBackground(Color.WHITE); // �YOpaque�]�N�O���z������ܪ��I���C��
	  text.setRows(100);
	  text.setColumns(200);
	  text.setFont(new Font("Dialog", Font.BOLD, 16));
	  text.setEditable(false);
	  text.setOpaque(false); // ��textArea���I���ܳz���e�j��panel�����
	  mainPanel.add(text);

	  add(mainPanel, BorderLayout.WEST); // �����e�i�H�a�k���
	  
	  mainPanel2 = new JPanel();
	  mainPanel2.setBackground(color);
	  add(mainPanel2, BorderLayout.CENTER);
      
	  JPanel downPanel = new JPanel();
      JPanel downPanel2 = new JPanel();
      downPanel.setBackground(new Color(117,79,51));
      downPanel2.setBackground(new Color(117,79,51));
      downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.X_AXIS));
      downPanel2.setLayout(layout);
      
      bug1 = new ImageIcon(getClass().getResource("like.png")); 
      bug2 = new ImageIcon(getClass().getResource("unlike.png"));
      
      Image image1 = bug1.getImage(); 
      Image newimg1 = image1.getScaledInstance(30, 30, java.awt.Image.SCALE_AREA_AVERAGING);  // �վ��طR�ߤj�p
      bug1 = new ImageIcon(newimg1);
    		
      Image image2 = bug2.getImage(); 
      Image newimg2 = image2.getScaledInstance(30, 30, java.awt.Image.SCALE_AREA_AVERAGING); 
      bug2 = new ImageIcon(newimg2);
      
      if(islike)
    	  likeButton = new JButton(bug1);
      else
    	  likeButton = new JButton(bug2);
      
      likeButton.setContentAreaFilled(false); // �]�w���s�I���z��
      likeButton.setFocusPainted(false);  // �����J�I���A
      likeButton.setBorderPainted(false); // �����Ϥ����	
      likeButton.addActionListener(this);
      
      editButton = new JButton("�s��");
      editButton.addActionListener(this);
      
      writeButton = new JButton("���s�K��");
      writeButton.addActionListener(this);
      
      saveButton = new JButton("�x�s");
      saveButton.addActionListener(this);
      
      renewButton = new JButton("�t�s���e");
      renewButton.addActionListener(this);
      
      importButton = new JButton("�פJ���e");
      importButton.addActionListener(this);
      
      cancelButton = new JButton("����");
      cancelButton.addActionListener(this);
      
      //�P�_�O�_���s���
      if(option == 1 || option == 0)
    	  likeButton.setEnabled(true);
      else
    	  System.exit(0);
      
      downPanel.add(likeButton);
      
      
      saveButton.setSize(100,100); // ���s��e���ó������s
      downPanel2.add(saveButton); 
      saveButton.setVisible(false);
      
      downPanel2.add(renewButton);
      renewButton.setVisible(false);
      
      downPanel2.add(importButton);
      importButton.setVisible(false);
      
      downPanel2.add(cancelButton);
      cancelButton.setVisible(false);
      
      downPanel.add(downPanel2);

      downPanel.add(Box.createGlue()); // �Ϥ������Ͷ��j
      
      if(option == 0)
      {
    	  downPanel.add(editButton);
    	  downPanel.add(writeButton);
      }

      add(downPanel, BorderLayout.SOUTH);

   } 
   
   public void itemStateChanged(ItemEvent e)
   {
	   if(e.getStateChange() == ItemEvent.SELECTED) 
	   {   
		   currentWeather = weatherBox.getSelectedIndex(); // �����x�s��~�|��諸�Ѯ𪬺A	   
	   }
   }  
   
   public void actionPerformed(ActionEvent e)
   {
      if (e.getSource() == likeButton)
      {
    	  if (islike)
    	  {
    		  likeButton.setIcon(bug2);
    		  islike = false;
    	  }  
    	  else
    	  {
    		  likeButton.setIcon(bug1);
    		  islike = true;
    	  }
      }
      
      else if(e.getSource() == writeButton)
      {
    	  Edit("");
      }
      
      else if(e.getSource() == editButton)
      {
    	  Edit(demo);
      }
      
      else if(e.getSource() == saveButton)
      {
    	  try 
    	  {
    		demo = text.getText();
			editTime.setTime(Files.getLastModifiedTime(Paths.get("src\\post")).toMillis());
						
    		ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(Paths.get("src\\post")));
			PostSerializable file = new PostSerializable(demo, islike, editTime);
			file.setWeather(currentWeather); // �T�{���Ѯ�
			output.writeObject(file); // �N���᪺PostŪ�J   
			output.flush(); // ��w�İϪ����e�j��g�X
			output.close();
			
			IconLabel = new JLabel(icons[currentWeather]);
    		time.setText("   "+editTime.toString());
    		
    		likeButton.setVisible(true);
	      	editButton.setVisible(true);
	      	writeButton.setVisible(true);
	      	text.setVisible(true);
    		saveButton.setVisible(false);
    		renewButton.setVisible(false);
    		importButton.setVisible(false);
    		cancelButton.setVisible(false);
    		  
    		topPanel1.add(name1); // add the weather label
    		topPanel1.add(IconLabel);
    		topPanel1.remove(weatherBox);
    		  
    		topPanel.setPreferredSize(new Dimension(50, 106));
    		
    		text.setForeground(Color.YELLOW);
    		mainPanel.setBackground(color);
    		mainPanel2.setBackground(color);
		  } 
    	  catch (IOException e1) 
    	  {
    		  e1.printStackTrace();
		  }
      }
      
      else if(e.getSource() == cancelButton)
      {
    	  likeButton.setVisible(true);
    	  editButton.setVisible(true);
    	  writeButton.setVisible(true);
  		  text.setVisible(true);
  		  saveButton.setVisible(false);
  		  renewButton.setVisible(false);
  		  importButton.setVisible(false);
  		  cancelButton.setVisible(false);
  		  
  		  topPanel1.add(name1); // add the weather label
  		  topPanel1.add(IconLabel);
  		  topPanel1.remove(weatherBox);
  		  
  		  topPanel.setPreferredSize(new Dimension(50, 106));
  		  
  		  text.setText(demo);
  		  text.setVisible(true);
  		  text.setEditable(false);
  		  text.setForeground(Color.YELLOW);
  		  mainPanel.setBackground(color);
  		  mainPanel2.setBackground(color);
      }
      
      else if(e.getSource() == importButton)
      {
    	  JFileChooser fileChooser = new JFileChooser();
    	  fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    	  int returnValue = fileChooser.showOpenDialog(this);
    	  Path path = null;
    	  if(returnValue == JFileChooser.CANCEL_OPTION)
    		  return;
    	  else
    		  path = fileChooser.getSelectedFile().toPath();
    	  if (path != null && Files.exists(path)) 
    	  {
    	  	  try
    	  	  {
	    	  		InputStream post = Files.newInputStream(path);		
	    			input = new ObjectInputStream(post);	
	    			PostSerializable file =  (PostSerializable) input.readObject();
	    			
	    			time = new JLabel("   "+file.getEditTime().toString()); 
	    			islike = file.getIsLike();
	    			text = new JTextArea(file.getContent(),13,60);
	    			demo = file.getContent(); 
	    			
	    			preWeather = file.getWeather();
	    		    IconLabel = new JLabel(icons[preWeather]);
	    		    
			  }
    		  catch (NoSuchElementException e1) //�P�_�O�_�����N���V�ɡA�|��next�]�^�ɭP
			  {
			       System.err.println("File improperly formed. Terminating.");
			  } 
			  catch (IllegalStateException e1)
			  {
			       System.err.println("Error reading from file. Terminating.");
			  } 
    	  	  catch (FileNotFoundException e1) 
    	  	  {
    	  		   System.err.println("File Not Found. Terminating.");
			  } 
    	  	  catch (IOException e1) 
    	  	  {
    	  		   System.err.println("IOException. Terminating.");
    	  	  } 
    	  	  catch (ClassNotFoundException e1) 
    	  	  {
   	  		  	   System.err.println("ClassNotFoundException. Terminating.");
    	  	  }
		 }
  
      }
      
      else if(e.getSource() == renewButton)
      {
    	  JFileChooser fileChooser = new JFileChooser();
    	  fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    	  int returnValue = fileChooser.showOpenDialog(this);
    	  if(returnValue == JFileChooser.CANCEL_OPTION)
    		  return;
    	  
    	  if (fileChooser.getSelectedFile() != null) 
    	  {  
    		  try 
				{
    			  	Path path = fileChooser.getSelectedFile().toPath();
					ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(path));
					PostSerializable file = new PostSerializable(demo, islike, editTime);
					file.setWeather(currentWeather);
					output.writeObject(file); // �N���᪺PostŪ�J   
					output.flush(); // ��w�İϪ����e�j��g�X
					output.close();
				}				
				catch(IOException ioexception)
				{
					System.err.println("Error IOException.");
				}
    	  }
      }
   	  
   }
   
   public void Edit(String s)
   {
	   likeButton.setVisible(false);
	   editButton.setVisible(false);
	   writeButton.setVisible(false);
	   
	   topPanel1.remove(name1); // temporarily delete weather label
	   topPanel1.remove(IconLabel);
	   topPanel1.add(weatherBox);
	   topPanel.setPreferredSize(new Dimension(50, 105));
	   
	   weatherBox.setVisible(true);
	   saveButton.setVisible(true);
	   renewButton.setVisible(true);
	   importButton.setVisible(true);
	   cancelButton.setVisible(true);
	   
	   editText = s;
	   mainPanel.setBackground(Color.WHITE);
	   mainPanel2.setBackground(Color.WHITE);
	   text.setEditable(true);
	   text.setForeground(Color.BLACK);
	   text.setText(editText);
   }
   }