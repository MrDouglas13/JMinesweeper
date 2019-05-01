import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class Ranking implements Serializable{
	
	//private static final long serialVersionUID = 1L;
	
	private int[] times;
	private String[] names;
	private static JLabel[] labels;
	private static MineMap m;
	
	public Ranking() {
		times = new int[3];
		names = new String[3];
		labels = new JLabel[9];
	}
	
	public void addMap(MineMap map) {
		m = map;
	}
	
	public void setLabels(int idx, String s) {
		labels[idx].setText(s);
	}
	
	public void setRanking(int time, String name, int idx) {
		times[idx] = time;
		names[idx] = name;
	}
	
	public int getTime(int idx) {
		return times[idx];
	}
	
	public String getName(int idx) {
		return names[idx];
	}
	
	public void reset(){
		for(int i = 0; i < 3; i++) {
			times[i] = 999;
			names[i] = "Anonymous";
		}
		
		outSerialize();
	}
	
	public void showRanking() {
		
		JPanel bigpanel = new JPanel();
		bigpanel.setLayout(new BoxLayout (bigpanel, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 3));
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		
		labels[0] = new JLabel("Beginner:");
		labels[1] = new JLabel(getTime(0) + " seconds");
		labels[2] = new JLabel(getName(0));
		labels[3] = new JLabel("Intermediate:");
		labels[4] = new JLabel(getTime(1) + " seconds");
		labels[5] = new JLabel(getName(1));
		labels[6] = new JLabel("Expert:");
		labels[7] = new JLabel(getTime(2) + " seconds");
		labels[8] = new JLabel(getName(2));
		
		Font besttimefont = new Font("Segoe UI", Font.PLAIN, 11);
		
		for(JLabel lab: labels) {
			lab.setFont(besttimefont);
			lab.setForeground(Color.BLACK);
			panel.add(lab);
		}
		
		JButton reset = new JButton("Reset Scores");
		
		ActionListener rankinglistener = new MyActionListener(m, this);
		reset.addActionListener(rankinglistener);
		reset.setActionCommand("reset");
		
		panel2.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));
		
		panel2.add(reset);
		
		bigpanel.add(panel);
		bigpanel.add(panel2);
		
		Object[] options = {"OK"};
		JOptionPane.showOptionDialog(m.getFrame(),
										bigpanel,
										"Fastest Mine Sweepers",
										JOptionPane.DEFAULT_OPTION,
										JOptionPane.PLAIN_MESSAGE,
										null,
										options,
										options[0]);	
	}
	
	public void inSerialize() {
		
		try {
			File file1 = new File("times.txt");
			FileInputStream fis = new FileInputStream(file1);
			ObjectInputStream in = new ObjectInputStream(fis);
			times = (int[]) in.readObject();
			in.close();
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		
		try {
			File file1 = new File("names.txt");
			FileInputStream fis = new FileInputStream(file1);
			ObjectInputStream in = new ObjectInputStream(fis);
			names = (String[]) in.readObject();
			in.close();
		} catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void outSerialize() {
		
		try {
			File file1 = new File("times.txt");
			FileOutputStream f = new FileOutputStream(file1);
			ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(times);
			out.close();
		} catch (IOException i) {
	        i.printStackTrace();
	    }
		
		try {
			File file1 = new File("names.txt");
			FileOutputStream f = new FileOutputStream(file1);
			ObjectOutputStream out = new ObjectOutputStream(f);
			out.writeObject(names);
			out.close();
		} catch (IOException i) {
	        i.printStackTrace();
	    }
	}
	
}
