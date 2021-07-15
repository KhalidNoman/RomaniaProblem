import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class RomaniaProblemKhalidNoman extends JFrame {
	//Variables to be used throughout
	String[] cities = {"Arad", "Bucharest","Craiova", "Drobeta", "Eforie", "Fagaras", "Giurgiu",
					   "Hirsova", "Iasi", "Lugoj", "Mehadia", "Neamt", "Oradea", "Pitesti", 
					   "Rimmicu Vilcea", "Sibiu", "Timisoara", "Urziceni", "Vaslui","Zerind"} ;
	
	Integer[][] adjMatrix = new Integer[20][20];
	
	String selected;
	Integer pathCostBFS = 0, stepsBFS = 0;
	
	

	private JPanel contentPane;
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
	
	public RomaniaProblemKhalidNoman() {
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
		
		JPanel bfsPanel = new JPanel();
		tabbedPane.addTab("Breadth-First Search", null, bfsPanel, null);
		
		JPanel dfsPanel = new JPanel();
		tabbedPane.addTab("Depth-First Search", null, dfsPanel, null);
		
		JPanel idsPanel = new JPanel();
		tabbedPane.addTab("Iterative-Deepening Search", null, idsPanel, null);
		
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
			}
		});
		goBtn.setBounds(250, 10, 89, 23);
		contentPane.add(goBtn);
		
		
		
		/*********************************Setup************************************/
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
		for(int i = 0; i < 20; i++) {
			JLabel lblTemp = new JLabel(cities[i] + " :");
			lblTemp.setVerticalAlignment(SwingConstants.TOP);
			lblTemp.setHorizontalAlignment(SwingConstants.LEFT);
			lblTemp.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblTemp.setBounds(10, i*35+100, 970, 870);
			mapPanel.add(lblTemp);
			//lblConnections.setText(String.format("<html>" +lblConnections.getText().toString() + "%-14s : ", cities[i]));
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
					//lblConnections.setText(String.format(lblConnections.getText().toString() + "%-14s", cityNames.values()[j]));
					//System.out.printf("%-14s ", cityNames.values()[j]);
				}
			}
			space = 1;
			//lblConnections.setText(lblConnections.getText().toString() + "<br/>");
		}
		//lblConnections.setText(lblConnections.getText().toString() + "</html>");
		
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
	
	public Queue BFS() {
		Queue goalPath = new LinkedList();
		Queue frontier = new LinkedList();
		System.out.println("Start");
		
		ArrayList<String> visited = new ArrayList<String>();
		String current = selected;
		//visited.add(current);
		//goalPath.add(current);
		frontier.add(current);
		
		if(current.equalsIgnoreCase("Bucharest")) {
			return goalPath;
		}else {
			while(!frontier.isEmpty()) {
				System.out.println("Front " + current);
				current = frontier.remove().toString();
				/*for(int i = 0; i < 20; i++) {
					if(adjMatrix[Arrays.asList(cities).indexOf(current)][i] == 1)
						frontier.add(cities[i]);
				}*/
				/*visited.add(current);
				goalPath.add(current);*/
				if(!visited.contains(current) && !frontier.contains(current)) {
					if(current.equalsIgnoreCase("Bucharest")) {
						goalPath.add(current);
						visited.add(current);
						System.out.println("\nMY PATH:");
						while(!goalPath.isEmpty())
							System.out.println(goalPath.remove().toString());
						System.out.println("Stop here");
						
						System.out.println("\nMY VISITS:");
						System.out.println(visited.toString());
						System.out.println("Stop");
						return goalPath;
					}
					//frontier.add(current);
					for(int i = 0; i < 20; i++) {
						if(adjMatrix[Arrays.asList(cities).indexOf(current)][i] > 0 ) {
							System.out.println("Adding " + cities[i] + " parent: " + current);
							frontier.add(cities[i]);
						}
					}
					visited.add(current);
					goalPath.add(current);
				}
			}
		}
		System.out.println("MY VISITS:");
		while(!visited.isEmpty())
			System.out.println(visited.toString());
		System.out.println("Stop");
		return goalPath;
		
		
	}
	
	public void DFS() {
		
	}
	
	public void IDS() {
		
	}
}
