import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;

final class MyActionListener implements ActionListener {
	
	private MineMap m;
	private Ranking myRanking;
	
	public MyActionListener(MineMap mm, Ranking r) {
		m = mm;
		myRanking = r;
	}
	
	public void actionPerformed(ActionEvent ae) {
		switch(ae.getActionCommand()) {
			case "time":
				if(!MySmileyMouseListener.getHolddown())
					m.updateTimer();
				break;
			case "newgame":
				m.newGame(m.getMapX(), m.getMapY(), m.getBombs());
				break;
			case "beginner":
				m.setGametype(0);
				m.newGame(9, 9, 10);	// 12,3456% bomb
				break;
			case "intermediate":
				m.setGametype(1);
				m.newGame(16, 16, 40);	// 15.625% bomb
				break;
			case "expert":
				m.setGametype(2);
				m.newGame(16, 30, 99);	// 20.625% bomb
				break;
			case "custom":
				m.setGametype(3);
				JTextField xField = new JTextField(4);
				xField.setText(Integer.toString(m.getMapX()));
			    JTextField yField = new JTextField(4);
			    yField.setText(Integer.toString(m.getMapY()));
			    JTextField bField = new JTextField(4);
			    bField.setText(Integer.toString(m.getBombs()));

			    JPanel myPanel = new JPanel();
			    myPanel.setLayout(new GridLayout(1,2));
			    JPanel panel1 = new JPanel();
			    JPanel panel2 = new JPanel();
			    myPanel.add(panel1);
			    myPanel.add(panel2);
			    panel1.setLayout (new GridLayout(3,1));
			    
			    panel1.add(new JLabel("Height:"));
			    panel1.add(xField);
			    
			    panel1.add(new JLabel("Width:"));
			    panel1.add(yField);
			    
			    panel1.add(new JLabel("Mines:"));
			    panel1.add(bField);

			    int result = JOptionPane.showConfirmDialog(m.getFrame(), myPanel, "Custom Field", JOptionPane.OK_CANCEL_OPTION);
			    
			    String scutx = xField.getText();
			    String scuty = yField.getText();
			    String scutb = bField.getText();
			    
			    if(scutx.length() > 9)
			    	scutx = scutx.substring(0, 8);
			    if(scuty.length() > 9)
			    	scuty = scuty.substring(0, 8);
			    if(scutb.length() > 9)
			    	scutb = scutb.substring(0, 8);
			    
			    if(!scutx.matches("^[0-9]+$"))
			    	scutx = "9";
			    if(!scuty.matches("^[0-9]+$"))
			    	scuty = "9";
			    if(!scutb.matches("^[0-9]+$"))
			    	scutb = "10";
			    
			    int cutx = Integer.parseInt(scutx);
			    int cuty = Integer.parseInt(scuty);
			    int cutb = Integer.parseInt(scutb);
			    
			    if (result == JOptionPane.OK_OPTION) {
			    	m.newGame(cutx, cuty, cutb);
			    }
				
				break;
			case "marks":
				if(m.getQmarks())
					m.setQmarks(false);
				else m.setQmarks(true);
				break;
			case "besttimes":
				myRanking.showRanking();
				break;
			case "quitgame":
				System.exit(0);
				break;
			case "info":
				String str = "JMinesweeper\nCreated by Mark Angyan\n\nBest on 1920x1080 monitors or higher.";
				
				//image transform and resize
				ImageIcon imageIcon = new ImageIcon("images\\\\icon\\\\mineicon.png");
				Image image = imageIcon.getImage();
				Image newimg = image.getScaledInstance(44, 41,  java.awt.Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(newimg);
				
				JOptionPane.showMessageDialog(m.getFrame(), str,
													"Information", JOptionPane.INFORMATION_MESSAGE,
													imageIcon);
				break;
			case "reset":
				myRanking.reset();
				myRanking.setLabels(1, "999 seconds");
				myRanking.setLabels(2, "Anonymous");
				myRanking.setLabels(4, "999 seconds");
				myRanking.setLabels(5, "Anonymous");
				myRanking.setLabels(7, "999 seconds");
				myRanking.setLabels(8, "Anonymous");
				break;
			case "xp":
				if(!m.getSkintype().equals("xp")) {
					m.reSkin("xp");
				}
				break;
			case "xpblack":
				if(!m.getSkintype().equals("xpblack")) {
					m.reSkin("xpblack");
				}
				break;
			case "win98":
				if(!m.getSkintype().equals("win98")) {
					m.reSkin("win98");
				}
				break;
			case "mario":
				if(!m.getSkintype().equals("mario")) {
					m.reSkin("mario");
				}
				break;
			case "mine":
				if(!m.getSkintype().equals("mine")) {
					m.reSkin("mine");
				}
				break;
			/*case "newskin":
				if(!m.getSkintype().equals("newskin")) {
					m.reSkin("newskin");
				}
				break;*/
			default:
				//won't happen
				break;
			}	
	}
}