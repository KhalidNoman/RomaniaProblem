import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class RomaniaProblemKhalidNoman extends JFrame {
	//Variables to be used throughout
	String[] cities = {"Arad", "Bucharest","Craiova", "Drobeta", "Eforie", "Fagaras", "Giurgiu",
					   "Hirsova", "Iasi", "Lugoj", "Mehadia", "Neamt", "Oradea", "Pitesti", 
					   "Rimmicu Vilcea", "Sibiu", "Timisoara", "Urziceni", "Vaslui","Zerind"} ;
	
	Integer[][] adjMatrix = new Integer[20][20];
	
	String selected;
	Integer pathCostBFS = 0, stepsBFS = 0;
	
	JPanel bfsPanel;
	JPanel dfsPanel;
	JPanel idsPanel;
	
	

	private JPanel contentPane;
	private JTable tblConnections;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RomaniaProblemKhalidNoman frame = new RomaniaProblemKhalidNoman();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public RomaniaProblemKhalidNoman() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1000);
		this.setTitle("Khalid Noman Romania Problem");
		this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 50, 1000, 950);
		contentPane.add(tabbedPane);
		
		JPanel mapPanel = new JPanel();
		tabbedPane.addTab("Graph", null, mapPanel, null);
		mapPanel.setLayout(null);
		
		JLabel lblConnections = new JLabel("Connections");
		lblConnections.setVerticalAlignment(SwingConstants.TOP);
		lblConnections.setHorizontalAlignment(SwingConstants.LEFT);
		lblConnections.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblConnections.setBounds(10, 10, 970, 870);
		mapPanel.add(lblConnections);
		
		bfsPanel = new JPanel();
		tabbedPane.addTab("Breadth-First Search", null, bfsPanel, null);
		bfsPanel.setLayout(null);
		
		dfsPanel = new JPanel();
		tabbedPane.addTab("Depth-First Search", null, dfsPanel, null);
		dfsPanel.setLayout(null);
		
		idsPanel = new JPanel();
		tabbedPane.addTab("Iterative-Deepening Search", null, idsPanel, null);
		idsPanel.setLayout(null);
		
		JComboBox selectDrop = new JComboBox(cities);
		selectDrop.setBounds(10, 10, 200, 20);
		contentPane.add(selectDrop);
		
		JButton goBtn = new JButton("Go");
		goBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selected = selectDrop.getSelectedItem().toString();
				if(selected.equalsIgnoreCase("Bucharest"))
					JOptionPane.showMessageDialog(null, "Starting point cannot be the same as destination!");
				BFS();
				DFS();
				IDS();
				//IDS2();
			}
		});
		goBtn.setBounds(250, 10, 89, 23);
		contentPane.add(goBtn);
		
		BufferedImage picture = ImageIO.read(new File("imgs/map.png"));
		Image resPic = picture.getScaledInstance(850, 500, Image.SCALE_DEFAULT);
		JLabel lblImage = new JLabel();
		lblImage.setIcon(new ImageIcon(resPic));
		lblImage.setBounds(1050, 50, 850, 500);
		contentPane.add(lblImage);
		
		for(int i = 0; i < 20; i++) 
			for(int j = 0; j < 20; j++)
				adjMatrix[i][j] = 0;
		
		//Connect vertices
			//Arad
				adjMatrix[cityNames.Arad.ordinal()][cityNames.Zerind.ordinal()] = 75;
				adjMatrix[cityNames.Arad.ordinal()][cityNames.Timisoara.ordinal()] = 118;
				adjMatrix[cityNames.Arad.ordinal()][cityNames.Sibiu.ordinal()] = 140;
			//Bucharest 
				adjMatrix[cityNames.Bucharest.ordinal()][cityNames.Pitesti.ordinal()] = 101;
				adjMatrix[cityNames.Bucharest.ordinal()][cityNames.Fagaras.ordinal()] = 211;
				adjMatrix[cityNames.Bucharest.ordinal()][cityNames.Giurgiu.ordinal()] = 90;
				adjMatrix[cityNames.Bucharest.ordinal()][cityNames.Urziceni.ordinal()] = 85;
			//Craiova
				adjMatrix[cityNames.Craiova.ordinal()][cityNames.Drobeta.ordinal()] = 120;
				adjMatrix[cityNames.Craiova.ordinal()][cityNames.RimnicuVilcea.ordinal()] = 146;
				adjMatrix[cityNames.Craiova.ordinal()][cityNames.Pitesti.ordinal()] = 138;
			//Drobeta
				adjMatrix[cityNames.Drobeta.ordinal()][cityNames.Mehadia.ordinal()] = 75;
				adjMatrix[cityNames.Drobeta.ordinal()][cityNames.Craiova.ordinal()] = 120;
			//Eforie
				adjMatrix[cityNames.Eforie.ordinal()][cityNames.Hirsova.ordinal()] = 86;
			//Fagaras
				adjMatrix[cityNames.Fagaras.ordinal()][cityNames.Sibiu.ordinal()] = 99;
				adjMatrix[cityNames.Fagaras.ordinal()][cityNames.Bucharest.ordinal()] = 211;
			//Giurgiu
				adjMatrix[cityNames.Giurgiu.ordinal()][cityNames.Bucharest.ordinal()] = 90;
			//Hirsova
				adjMatrix[cityNames.Hirsova.ordinal()][cityNames.Eforie.ordinal()] = 86;
				adjMatrix[cityNames.Hirsova.ordinal()][cityNames.Urziceni.ordinal()] = 98;
			//Iasi
				adjMatrix[cityNames.Iasi.ordinal()][cityNames.Neamt.ordinal()] = 87;
				adjMatrix[cityNames.Iasi.ordinal()][cityNames.Vaslui.ordinal()] = 92;
			//Lugoj
				adjMatrix[cityNames.Lugoj.ordinal()][cityNames.Mehadia.ordinal()] = 70;
				adjMatrix[cityNames.Lugoj.ordinal()][cityNames.Timisoara.ordinal()] = 111;
			//Mehadia
				adjMatrix[cityNames.Mehadia.ordinal()][cityNames.Lugoj.ordinal()] = 70;
				adjMatrix[cityNames.Mehadia.ordinal()][cityNames.Drobeta.ordinal()] = 75;
			//Neamt
				adjMatrix[cityNames.Neamt.ordinal()][cityNames.Iasi.ordinal()] = 87;
			//Oradea
				adjMatrix[cityNames.Oradea.ordinal()][cityNames.Zerind.ordinal()] = 71;
				adjMatrix[cityNames.Oradea.ordinal()][cityNames.Sibiu.ordinal()] = 151;
			//Pitesti
				adjMatrix[cityNames.Pitesti.ordinal()][cityNames.RimnicuVilcea.ordinal()] = 97;
				adjMatrix[cityNames.Pitesti.ordinal()][cityNames.Bucharest.ordinal()] = 101;
				adjMatrix[cityNames.Pitesti.ordinal()][cityNames.Craiova.ordinal()] = 138;
			//RimnicuVilcea
				adjMatrix[cityNames.RimnicuVilcea.ordinal()][cityNames.Sibiu.ordinal()] = 80;
				adjMatrix[cityNames.RimnicuVilcea.ordinal()][cityNames.Pitesti.ordinal()] = 97;
				adjMatrix[cityNames.RimnicuVilcea.ordinal()][cityNames.Craiova.ordinal()] = 146;
			//Sibiu
				adjMatrix[cityNames.Sibiu.ordinal()][cityNames.Fagaras.ordinal()] = 99;
				adjMatrix[cityNames.Sibiu.ordinal()][cityNames.Oradea.ordinal()] = 151;
				adjMatrix[cityNames.Sibiu.ordinal()][cityNames.Arad.ordinal()] = 140;
				adjMatrix[cityNames.Sibiu.ordinal()][cityNames.RimnicuVilcea.ordinal()] = 180;
			//Timisoara
				adjMatrix[cityNames.Timisoara.ordinal()][cityNames.Arad.ordinal()] = 118;
				adjMatrix[cityNames.Timisoara.ordinal()][cityNames.Lugoj.ordinal()] = 111;
			//Urziceni
				adjMatrix[cityNames.Urziceni.ordinal()][cityNames.Vaslui.ordinal()] = 142;
				adjMatrix[cityNames.Urziceni.ordinal()][cityNames.Hirsova.ordinal()] = 98;
				adjMatrix[cityNames.Urziceni.ordinal()][cityNames.Bucharest.ordinal()] = 85;
			//Vaslui
				adjMatrix[cityNames.Vaslui.ordinal()][cityNames.Iasi.ordinal()] = 92;
				adjMatrix[cityNames.Vaslui.ordinal()][cityNames.Urziceni.ordinal()] = 142;
			//Zerind
				adjMatrix[cityNames.Zerind.ordinal()][cityNames.Oradea.ordinal()] = 71;
				adjMatrix[cityNames.Zerind.ordinal()][cityNames.Arad.ordinal()] = 75;
		//End of connections 
		
		DefaultTableModel model = new DefaultTableModel();
		tblConnections = new JTable(model);
		tblConnections.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		model.insertRow(0, cities);
		model.addColumn(0, cities);
		for(int i = 1; i < 21; i++)
			model.addColumn(i, adjMatrix[i-1]);
		tblConnections.setEnabled(false);
		tblConnections.setBackground(getBackground());
		tblConnections.setBounds(1050, 600, 850, 400);
		contentPane.add(tblConnections);
				
		for(int i = 0; i < 20; i++) {
			JLabel lblTemp = new JLabel(i + " " + cities[i] + " :");
			lblTemp.setVerticalAlignment(SwingConstants.TOP);
			lblTemp.setHorizontalAlignment(SwingConstants.LEFT);
			lblTemp.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblTemp.setBounds(10, i*35+100, 970, 870);
			mapPanel.add(lblTemp);
			int space = 1;
			for(int j = 0; j < 20; j++) {
				if(adjMatrix[i][j] > 0) {
					space++;
					JLabel lblTemp2 = new JLabel(cities[j]);
					lblTemp2.setVerticalAlignment(SwingConstants.TOP);
					lblTemp2.setHorizontalAlignment(SwingConstants.LEFT);
					lblTemp2.setFont(new Font("Tahoma", Font.PLAIN, 20));
					lblTemp2.setBounds(150*space, i*35+100, 970, 870);
					mapPanel.add(lblTemp2);
				}
			}
			space = 1;
		}
		
		for(int i = 0; i < 20; i++) {
			System.out.printf("%-14s : ", cities[i]);
			for(int j = 0; j < 20; j++) {
				if(adjMatrix[i][j] > 0) {
					System.out.printf("%-14s ", cityNames.values()[j]);
				}
			}
			System.out.println();
		}
		
		
	}
	
	public enum cityNames{
		Arad, Bucharest, Craiova, Drobeta, Eforie, Fagaras, Giurgiu,
		   Hirsova, Iasi, Lugoj, Mehadia, Neamt, Oradea, Pitesti, 
		   RimnicuVilcea, Sibiu, Timisoara, Urziceni, Vaslui, Zerind;
	}
	
	public class node{
		String parent;
		String child;
		int depth = 0;
		
		public node(String p, String c) {
			parent = p;
			child = c;
		}
	}
	
	public void BFS() {
		bfsPanel.removeAll();
		Queue goalPath = new LinkedList();
		Queue frontier = new LinkedList();
		int steps = 0;
		int score = 0;
		
		ArrayList<String> visited = new ArrayList<String>();
		ArrayList<node> family = new ArrayList<node>();
		String parent;
		
		
		String current = selected;
		frontier.add(current);
		family.add(new node("", selected));
		
		if(current.equalsIgnoreCase("Bucharest")) {
			//return goalPath;
		}else {
			while(!frontier.isEmpty()) {
				System.out.println("Front " + current);
				current = frontier.remove().toString();

				//if(!visited.contains(current) && !frontier.contains(current)) {
				if(!visited.contains(current)) {
					if(current.equalsIgnoreCase("Bucharest")) {
						goalPath.add(current);
						visited.add(current);
						
						System.out.println("\nMY PATH:");
						while(!goalPath.isEmpty())
							System.out.println(goalPath.remove().toString());
						
						JLabel lblVisit = new JLabel("Visited cities in order visited: ");
						lblVisit.setVerticalAlignment(SwingConstants.TOP);
						lblVisit.setHorizontalAlignment(SwingConstants.LEFT);
						lblVisit.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblVisit.setBounds(10, 10, 970, 870);
						bfsPanel.add(lblVisit);
						
						
						
						System.out.println("\nMY VISITS:");
						System.out.println(visited.toString());
						
						
						JTextArea lblVisit2 = new JTextArea(visited.get(0));
						lblVisit2.setBackground(getBackground());
						lblVisit2.setLineWrap(true);
						lblVisit2.setFont(new Font("Tahoma", Font.PLAIN, 20));
						lblVisit2.setBounds(10, 60, 970, 150);
						for(int i = 1; i < visited.size(); i++)
							lblVisit2.setText(lblVisit2.getText().toString() + " -> " + visited.get(i));
						bfsPanel.add(lblVisit2);
						
						bfsPanel.repaint();
						
						System.out.println("Goal path: ");
						int index = 0;
						for(int i = 0; i < family.size(); i++) {
							if(family.get(i).child.equalsIgnoreCase("Bucharest"))
								index = i;
						}
						int i = index;
						int j = 0;
						ArrayList<String> myList = new ArrayList<String>();
						myList.add(family.get(index).child);
						while(i != 0) {
							if(family.get(i).parent.equalsIgnoreCase(family.get(j).child)) {
								i = j; j = 0;
								myList.add(family.get(i).child);
							}
							else j++;
						}
						
						JLabel lblGoal = new JLabel("Straight path from origin to goal: ");
						lblGoal.setVerticalAlignment(SwingConstants.TOP);
						lblGoal.setHorizontalAlignment(SwingConstants.LEFT);
						lblGoal.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblGoal.setBounds(10, 250, 970, 50);
						bfsPanel.add(lblGoal);
						
						JTextArea lblGoal2 = new JTextArea();
						lblGoal2.setBackground(getBackground());
						lblGoal2.setLineWrap(true);
						lblGoal2.setFont(new Font("Tahoma", Font.PLAIN, 20));
						lblGoal2.setBounds(10, 300, 970, 150);
						
						for(int x = 0; x < myList.size()-1; x++) {
							int from = 0, to = 0;
							for(int y = 0; y < cities.length; y++) {
								if(myList.get(x).equalsIgnoreCase(cities[y]))
									from = y;
								else if(myList.get(x+1).equalsIgnoreCase(cities[y]))
									to = y;	
							}
							score = score + adjMatrix[from][to];
						}
						
						for(int x = myList.size()-1; x >= 0; x--) {
							if(myList.get(x).equalsIgnoreCase("Bucharest")) {
								System.out.println(myList.get(x));
								lblGoal2.setText(lblGoal2.getText().toString() + myList.get(x));
							}else {
								System.out.print(myList.get(x) + " -> ");
								lblGoal2.setText(lblGoal2.getText().toString() + myList.get(x) + " -> ");
							}
						}
						bfsPanel.add(lblGoal2);
						
						JLabel lblSteps = new JLabel("Steps: " + steps);
						lblSteps.setVerticalAlignment(SwingConstants.TOP);
						lblSteps.setHorizontalAlignment(SwingConstants.LEFT);
						lblSteps.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblSteps.setBounds(10, 500, 970, 50);
						bfsPanel.add(lblSteps);
						
						JLabel lblScore = new JLabel("Score: " + score);
						lblScore.setVerticalAlignment(SwingConstants.TOP);
						lblScore.setHorizontalAlignment(SwingConstants.LEFT);
						lblScore.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblScore.setBounds(10, 600, 970, 50);
						bfsPanel.add(lblScore);
						
						bfsPanel.repaint();
						break;
					}
					steps++;
					for(int i = 0; i < 20; i++) {
						if(adjMatrix[Arrays.asList(cities).indexOf(current)][i] > 0 ) {
							family.add(new node(current, cities[i]));
							System.out.println("Adding " + cities[i] + " parent: " + current);
							frontier.add(cities[i]);
						}
					}
					visited.add(current);
					goalPath.add(current);
				}
			}
		}	
		
	}
	
	public void DFS() {
		dfsPanel.removeAll();
		Queue goalPath = new LinkedList();
		Stack frontier = new Stack();
		int steps = 0;
		int score = 0;
		
		ArrayList<String> visited = new ArrayList<String>();
		ArrayList<node> family = new ArrayList<node>();
		String parent;
		
		
		String current = selected;
		frontier.add(current);
		family.add(new node("", current));
		
		if(current.equalsIgnoreCase("Bucharest")) {
			//return goalPath;
		}else {
			while(!frontier.isEmpty()) {
				System.out.println("Front " + current);
				current = frontier.pop().toString();

				//if(!visited.contains(current) && !frontier.contains(current)) {
				if(!visited.contains(current)) {
					if(current.equalsIgnoreCase("Bucharest")) {
						goalPath.add(current);
						visited.add(current);
						
						System.out.println("\nMY PATH:");
						while(!goalPath.isEmpty())
							System.out.println(goalPath.remove().toString());
						
						JLabel lblVisit = new JLabel("Visited cities in order visited: ");
						lblVisit.setVerticalAlignment(SwingConstants.TOP);
						lblVisit.setHorizontalAlignment(SwingConstants.LEFT);
						lblVisit.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblVisit.setBounds(10, 10, 970, 870);
						dfsPanel.add(lblVisit);
						
						
						
						System.out.println("\nMY VISITS:");
						System.out.println(visited.toString());
						
						
						JTextArea lblVisit2 = new JTextArea(visited.get(0));
						lblVisit2.setBackground(getBackground());
						lblVisit2.setLineWrap(true);
						lblVisit2.setFont(new Font("Tahoma", Font.PLAIN, 20));
						lblVisit2.setBounds(10, 60, 970, 150);
						for(int i = 1; i < visited.size(); i++)
							lblVisit2.setText(lblVisit2.getText().toString() + " -> " + visited.get(i));
						dfsPanel.add(lblVisit2);
						
						dfsPanel.repaint();
						
						System.out.println("Goal path: ");
						int index = 0;
						for(int i = 0; i < family.size(); i++) {
							if(family.get(i).child.equalsIgnoreCase("Bucharest"))
								index = i;
						}
						int i = index;
						int j = 0;
						ArrayList<String> myList = new ArrayList<String>();
						myList.add(family.get(index).child);
						while(i != 0) {
							if(family.get(i).parent.equalsIgnoreCase(family.get(j).child)) {
								i = j; j = 0;
								myList.add(family.get(i).child);
							}
							else j++;
						}
						
						JLabel lblGoal = new JLabel("Straight path from origin to goal: ");
						lblGoal.setVerticalAlignment(SwingConstants.TOP);
						lblGoal.setHorizontalAlignment(SwingConstants.LEFT);
						lblGoal.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblGoal.setBounds(10, 250, 970, 50);
						dfsPanel.add(lblGoal);
						
						JTextArea lblGoal2 = new JTextArea();
						lblGoal2.setBackground(getBackground());
						lblGoal2.setLineWrap(true);
						lblGoal2.setFont(new Font("Tahoma", Font.PLAIN, 20));
						lblGoal2.setBounds(10, 300, 970, 150);
						
						for(int x = 0; x < myList.size()-1; x++) {
							int from = 0, to = 0;
							for(int y = 0; y < cities.length; y++) {
								if(myList.get(x).equalsIgnoreCase(cities[y]))
									from = y;
								else if(myList.get(x+1).equalsIgnoreCase(cities[y]))
									to = y;	
							}
							score = score + adjMatrix[from][to];
						}
						
						for(int x = myList.size()-1; x >= 0; x--) {
							if(myList.get(x).equalsIgnoreCase("Bucharest")) {
								System.out.println(myList.get(x));
								lblGoal2.setText(lblGoal2.getText().toString() + myList.get(x));
							}else {
								System.out.print(myList.get(x) + " -> ");
								lblGoal2.setText(lblGoal2.getText().toString() + myList.get(x) + " -> ");
							}
						}
						dfsPanel.add(lblGoal2);
						
						JLabel lblSteps = new JLabel("Steps: " + steps);
						lblSteps.setVerticalAlignment(SwingConstants.TOP);
						lblSteps.setHorizontalAlignment(SwingConstants.LEFT);
						lblSteps.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblSteps.setBounds(10, 500, 970, 50);
						dfsPanel.add(lblSteps);
						
						JLabel lblScore = new JLabel("Score: " + score);
						lblScore.setVerticalAlignment(SwingConstants.TOP);
						lblScore.setHorizontalAlignment(SwingConstants.LEFT);
						lblScore.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblScore.setBounds(10, 600, 970, 50);
						dfsPanel.add(lblScore);
						
						dfsPanel.repaint();
						break;
					}
					steps++;
					for(int i = 0; i < 20; i++) {
						if(adjMatrix[Arrays.asList(cities).indexOf(current)][i] > 0  && !visited.contains(cities[i])) {
							family.add(new node(current, cities[i]));
							System.out.println("Adding " + cities[i] + " parent: " + current);
							frontier.add(cities[i]);
						}
					}
					visited.add(current);
					goalPath.add(current);
				}
			}
		}	
		
	}
	
	public void IDS() {
		idsPanel.removeAll();
		Queue goalPath = new LinkedList();
		Stack frontier = new Stack();
		int steps = 0;
		int score = 0;
		
		ArrayList<String> visited = new ArrayList<String>();
		ArrayList<String> currVisits = new ArrayList<String>();
		ArrayList<node> family = new ArrayList<node>();
		String parent;
		int depth = 1;
		
		
		String current = selected;
		frontier.add(current);
		family.add(new node("", current));
		
		if(current.equalsIgnoreCase("Bucharest")) {
			//return goalPath;
		}else {
			for(Integer limit = 0; limit < 100; limit++) {
				System.out.println("Limit: " + limit);
				frontier.removeAllElements();		
				frontier.add(selected);
				currVisits.clear();
				family.clear();
				family.add(new node("", selected));
			while(!frontier.isEmpty()) {
				System.out.println(frontier.toString());
				System.out.println("inner");
				current = frontier.pop().toString();
				System.out.println("Front: " + current);
				
				
				depth = 1;
				
				node temp= new node("", "");
				for(int x = 0; x < family.size(); x++) {
					//System.out.println("bad");
					if(current.equalsIgnoreCase(family.get(x).child))
						temp = family.get(x);
					//System.out.println("bad2");
				}
				
				while(!temp.child.equalsIgnoreCase(selected) && !temp.parent.equalsIgnoreCase("") && depth <20) {
					//System.out.println(family.size());
					for(int x = 0; x < family.size(); x++) {
						System.out.println(temp.parent + " " + temp.child + " " + family.get(x).parent + family.get(x).child);
						if(temp.parent.equalsIgnoreCase(family.get(x).child)) {
							temp = family.get(x);
							System.out.println(temp.parent + " parent of " + family.get(x).child + " depth " + depth);
						}
					}
					depth++;
				}
			if(depth <= limit) {
				System.out.println("Here " + currVisits.toString());
				
				//if(!visited.contains(current) && !frontier.contains(current)) {
				if(!currVisits.contains(current)) {
					if(current.equalsIgnoreCase("Bucharest")) {
						goalPath.add(current);
						visited.add(current);
						currVisits.add(current);
						
						System.out.println("\nMY PATH:");
						while(!goalPath.isEmpty())
							System.out.println(goalPath.remove().toString());
						
						JLabel lblVisit = new JLabel("Visited cities in order visited: ");
						lblVisit.setVerticalAlignment(SwingConstants.TOP);
						lblVisit.setHorizontalAlignment(SwingConstants.LEFT);
						lblVisit.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblVisit.setBounds(10, 10, 970, 870);
						idsPanel.add(lblVisit);
						
						
						
						System.out.println("\nMY VISITS:");
						System.out.println(visited.toString());
						
						
						JTextArea lblVisit2 = new JTextArea(visited.get(0));
						lblVisit2.setBackground(getBackground());
						lblVisit2.setLineWrap(true);
						lblVisit2.setFont(new Font("Tahoma", Font.PLAIN, 20));
						lblVisit2.setBounds(10, 60, 970, 150);
						for(int i = 1; i < visited.size(); i++)
							lblVisit2.setText(lblVisit2.getText().toString() + " -> " + visited.get(i));
						idsPanel.add(lblVisit2);
						
						idsPanel.repaint();
						
						System.out.println("Goal path: ");
						int index = 0;
						for(int i = 0; i < family.size(); i++) {
							if(family.get(i).child.equalsIgnoreCase("Bucharest"))
								index = i;
						}
						int i = index;
						int j = 0;
						ArrayList<String> myList = new ArrayList<String>();
						myList.add(family.get(index).child);
						while(i != 0) {
							if(family.get(i).parent.equalsIgnoreCase(family.get(j).child)) {
								i = j; j = 0;
								myList.add(family.get(i).child);
							}
							else j++;
						}
						
						JLabel lblGoal = new JLabel("Straight path from origin to goal: ");
						lblGoal.setVerticalAlignment(SwingConstants.TOP);
						lblGoal.setHorizontalAlignment(SwingConstants.LEFT);
						lblGoal.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblGoal.setBounds(10, 250, 970, 50);
						idsPanel.add(lblGoal);
						
						JTextArea lblGoal2 = new JTextArea();
						lblGoal2.setBackground(getBackground());
						lblGoal2.setLineWrap(true);
						//lblGoal2.enable(false);
						lblGoal2.setFont(new Font("Tahoma", Font.PLAIN, 20));
						lblGoal2.setBounds(10, 300, 970, 150);
						
						for(int x = 0; x < myList.size()-1; x++) {
							int from = 0, to = 0;
							for(int y = 0; y < cities.length; y++) {
								if(myList.get(x).equalsIgnoreCase(cities[y]))
									from = y;
								else if(myList.get(x+1).equalsIgnoreCase(cities[y]))
									to = y;	
							}
							score = score + adjMatrix[from][to];
						}
						
						for(int x = myList.size()-1; x >= 0; x--) {
							if(myList.get(x).equalsIgnoreCase("Bucharest")) {
								System.out.println(myList.get(x));
								lblGoal2.setText(lblGoal2.getText().toString() + myList.get(x));
							}else {
								System.out.print(myList.get(x) + " -> ");
								lblGoal2.setText(lblGoal2.getText().toString() + myList.get(x) + " -> ");
							}
						}
						idsPanel.add(lblGoal2);
						
						JLabel lblSteps = new JLabel("Steps: " + steps);
						lblSteps.setVerticalAlignment(SwingConstants.TOP);
						lblSteps.setHorizontalAlignment(SwingConstants.LEFT);
						lblSteps.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblSteps.setBounds(10, 500, 970, 50);
						idsPanel.add(lblSteps);
						
						JLabel lblScore = new JLabel("Score: " + score);
						lblScore.setVerticalAlignment(SwingConstants.TOP);
						lblScore.setHorizontalAlignment(SwingConstants.LEFT);
						lblScore.setFont(new Font("Tahoma", Font.PLAIN, 30));
						lblScore.setBounds(10, 600, 970, 50);
						idsPanel.add(lblScore);
						
						idsPanel.repaint();
						

						return;
					}
				}
					steps++;
					for(int i = 0; i < 20; i++) {
						if(adjMatrix[Arrays.asList(cities).indexOf(current)][i] > 0 && !currVisits.contains(cities[i]) && !frontier.contains(cities[i])) {
							family.add(new node(current, cities[i]));
							System.out.println("Adding " + cities[i] + " parent: " + current + " depth: " + depth);
							frontier.add(cities[i]);
						}
					}
					
					visited.add(current.concat(limit.toString()));
					currVisits.add(current);
					goalPath.add(current);
				}
			}
			}
		}	
	
	}
	
	//Unused different attempt
	public void IDS2() {
		idsPanel.removeAll();
		Queue goalPath = new LinkedList();
		Stack frontier = new Stack();
		int steps = 0;
		int score = 0;
		
		ArrayList<String> visited = new ArrayList<String>();
		ArrayList<String> currVisits = new ArrayList<String>();
		ArrayList<node> family = new ArrayList<node>();
		String parent;
		Integer depth = 1;
		
		String current = selected;
		frontier.add(current);
		family.add(new node("", current));
		int limit = 0;
		while(!current.equalsIgnoreCase("Bucharest")) {
			current = frontier.pop().toString();
			visited.add(current.concat(depth.toString()));
			
			node temp= new node("", "");
			for(int x = 0; x < family.size(); x++) {
				if(current.equalsIgnoreCase(family.get(x).child))
					temp = family.get(x);
			}
			
			while(!temp.child.equalsIgnoreCase(selected) && !temp.parent.equalsIgnoreCase("") && depth <20) {
				for(int x = 0; x < family.size(); x++) {
					System.out.println(temp.parent + " " + temp.child + " " + family.get(x).parent + family.get(x).child);
					if(temp.parent.equalsIgnoreCase(family.get(x).child)) {
						temp = family.get(x);
						System.out.println(temp.parent + " parent of " + family.get(x).child + " depth " + depth);
					}
				}
				depth++;
			}
			
			if(depth < limit) {
				for(int i = 0; i < 20; i++) {
					if(adjMatrix[Arrays.asList(cities).indexOf(current)][i] > 0 && !visited.contains(cities[i])) {
						family.add(new node(current, cities[i]));
						frontier.add(cities[i]);
					}
	
				}
				limit++;
				System.out.println(frontier.toString());
				if(visited.contains("Bucharest"))
					break;
			}
		}
	}

}
