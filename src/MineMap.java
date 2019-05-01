import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MineMap extends JFrame{

	//private static final long serialVersionUID = 1L;
	
	private Ranking myRanking;
	
	private int x;
	private int y;
	private int bombs;
	private List<List<Field>> fields;
	
	private boolean exploded = false;
	private boolean qmarks = true;
	private boolean gameended = false;
	private boolean won = false;
	
	private String smileystate = "normal";
	private String skintype = "xp";
	private int gametype = 0; //0: beginner 1: intermediate 2: expert 3: custom
	
	private JPanel outer = new JPanel();
	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();
	private JPanel inner1 = new JPanel();
	private JPanel inner2 = new JPanel();
	private JPanel inner3 = new JPanel();
	private int bombsleft;
	private JLabel bleft = new JLabel();
	private int seconds = 0;
	private JButton smiley = new JButton();
	private JLabel timecounter = new JLabel(String.format("%03d", seconds));
	private Timer timer;
	
	private JMenuBar mb = new JMenuBar();
	private JMenu game = new JMenu("Game");
	private JMenuItem newgame = new JMenuItem("New");
	private JSeparator sep1 = new JSeparator();
	private JRadioButtonMenuItem radioButton1 = new JRadioButtonMenuItem("Beginner", true);
	private JRadioButtonMenuItem radioButton2 = new JRadioButtonMenuItem("Intermediate");
	private JRadioButtonMenuItem radioButton3 = new JRadioButtonMenuItem("Expert");
	private JRadioButtonMenuItem radioButton4 = new JRadioButtonMenuItem("Custom...");
	private JSeparator sep2 = new JSeparator();
	private JCheckBoxMenuItem marks = new JCheckBoxMenuItem("Marks (?)");
	private JSeparator sep3 = new JSeparator();
	
	private JMenuItem besttimes = new JMenuItem("Best Times...");
	private JSeparator sep4 = new JSeparator();
	private JMenuItem quitgame = new JMenuItem("Quit");
	private JMenu help = new JMenu("Help");
	private JMenuItem info = new JMenuItem("Info");
	
	private JMenu skins = new JMenu("Skins");
	private JRadioButtonMenuItem xp = new JRadioButtonMenuItem("Windows XP");
	private JRadioButtonMenuItem xpblack = new JRadioButtonMenuItem("Windows XP Black and White");
	private JRadioButtonMenuItem win98 = new JRadioButtonMenuItem("Windows 98");
	private JRadioButtonMenuItem mario = new JRadioButtonMenuItem("Mario");
	private JRadioButtonMenuItem mine = new JRadioButtonMenuItem("Mine");
	//private JRadioButtonMenuItem newskin = new JRadioButtonMenuItem("newskin");
	
	public MineMap(int xx, int yy, int b, Ranking r) {
		
		myRanking = r;
		
		x = xx;
		y = yy;
		bombs = b;
		bombsleft = bombs;
		
		updateBombsleft();
		
		setResizable(false);
		smiley.setBorder(BorderFactory.createEmptyBorder());
		smiley.setIcon(new ImageIcon("images\\" + getSkintype() + "\\normalsmiley.png"));
		smiley.setContentAreaFilled(false);
		ActionListener al = new MyActionListener(this, myRanking);
		smiley.addMouseListener(new MySmileyMouseListener(this));
		
		timer = new Timer(1000, al);
		timer.setActionCommand("time");
		
		fields = new ArrayList<List<Field>>(x);
		
		for(int i = 0; i < x; i++)  {
	        fields.add(new ArrayList<Field>());
	    }
		
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				Field f = new Field("0", i, j, this);
				fields.get(i).add(f);
				p2.add(f.getButton());
			}
		}
		
		colorMap();

		inner1.add(bleft);
		inner2.add(smiley);
		inner3.add(timecounter);
		
		try {
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font\\ds-digital-bold.ttf")));
		} catch (IOException | FontFormatException e) {}
		
		Font counters = new Font("DS-Digital", Font.BOLD, 24);
		bleft.setFont(counters);
		timecounter.setFont(counters);
		
		inner1.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		inner3.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		
		p1.setLayout(new GridLayout(1,3));
		
		p1.add(inner1);
		p1.add(inner2);
		p1.add(inner3);
		
		outer.setLayout(new BoxLayout (outer, BoxLayout.Y_AXIS));
		outer.add(p1);
		outer.add(p2);
		/*f.*/add(outer);
		p2.setLayout(new GridLayout(x, y));
		
		ButtonGroup group1 = new ButtonGroup();
		game.add(newgame);
		game.add(sep1);
	    group1.add(radioButton1);
	    game.add(radioButton1);
	    group1.add(radioButton2);
	    game.add(radioButton2);
	    group1.add(radioButton3);
	    game.add(radioButton3);
	    group1.add(radioButton4);
	    
	    ButtonGroup group2 = new ButtonGroup();
	    group2.add(xp);
	    group2.add(xpblack);
	    group2.add(win98);
	    group2.add(mario);
	    group2.add(mine);
	    //group2.add(newskin);
	    xp.setSelected(true);
	    
	    game.add(radioButton4);
		game.add(sep2);
		game.add(marks);
		game.add(sep3);
		game.add(besttimes);
		game.add(sep4);
		game.add(quitgame);
		help.add(info);
		
		skins.add(xp);
		skins.add(xpblack);
		skins.add(win98);
		skins.add(mario);
		skins.add(mine);
		//skins.add(newskin);
		
		mb.setBackground(new Color(255, 255, 255));
		mb.add(game);
		mb.add(help);
		mb.add(skins);
		setJMenuBar(mb);
		
		marks.setSelected(true);
		
		newgame.setActionCommand("newgame");
		newgame.addActionListener(al);
		radioButton1.setActionCommand("beginner");
		radioButton1.addActionListener(al);
		radioButton2.setActionCommand("intermediate");
		radioButton2.addActionListener(al);
		radioButton3.setActionCommand("expert");
		radioButton3.addActionListener(al);
		radioButton4.setActionCommand("custom");
		radioButton4.addActionListener(al);
		marks.setActionCommand("marks");
		marks.addActionListener(al);
		besttimes.setActionCommand("besttimes");
		besttimes.addActionListener(al);
		quitgame.setActionCommand("quitgame");
		quitgame.addActionListener(al);
		info.setActionCommand("info");
		info.addActionListener(al);
		xp.setActionCommand("xp");
		xp.addActionListener(al);
		xpblack.setActionCommand("xpblack");
		xpblack.addActionListener(al);
		win98.setActionCommand("win98");
		win98.addActionListener(al);
		mario.setActionCommand("mario");
		mario.addActionListener(al);
		mine.setActionCommand("mine");
		mine.addActionListener(al);
		//newskin.setActionCommand("newskin");
		//newskin.addActionListener(al);
		
		setTitle("JMinesweeper");
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setIconImage(new ImageIcon("images\\icon\\mineicon.png").getImage());
	}
	
	public JFrame getFrame() {
		return this;
	}
	
	public boolean getQmarks() {
		return qmarks;
	}
	
	public void setQmarks(boolean b) {
		qmarks = b;
	}
	
	public String getSkintype() {
		return skintype;
	}
	
	public void setSkintype(String s) {
		skintype = s;
	}
	
	public int getGametype() {
		return gametype;
	}
	
	public void setGametype(int n) {
		gametype = n;
	}
	
	public void decreaseBombsleft() {
		bombsleft--;
		updateBombsleft();
	}
	
	public void increaseBombsleft() {
		bombsleft++;
		updateBombsleft();
	}
	
	public void setSmileystate(String s) {
		smileystate = s;
		smiley.setIcon(new ImageIcon("images\\" + getSkintype() + "\\" + smileystate + "smiley.png"));
	}
	
	public void setSmileystate(String s, boolean b) {
		smiley.setIcon(new ImageIcon("images\\" + getSkintype() + "\\" + s + "smiley.png"));
	}
	
	public String getSmileystate() {
		return smileystate;
	}
	
	public boolean getGameended() {
		return gameended;
	}
	
	public void startTimer() {
		updateTimer();
		timer.start();
	}
	
	public void updateBombsleft() {
		//bleft.setText(Integer.toString(bombsleft));
		bleft.setText(String.format("%03d", bombsleft));
	}
	
	public void updateTimer() {
		if(seconds < 999) seconds++;
        //timecounter.setText(Integer.toString(seconds));
		timecounter.setText(String.format("%03d", seconds));
	}
	
	public int getMapX() {
		return x;
	}
	
	public int getMapY() {
		return y;
	}
	
	public int getBombs() {
		return bombs;
	}
	
	public Field getField(int i, int j) {
		if(i < 0 || j < 0 || i >= x || j >= y)
			return new Field("error", 1, 1, this);

		return fields.get(i).get(j);
	}
	
	public void reSkin(String s) {
		skintype = s;
		setSmileystate(smileystate);
		colorMap();
		
		if(!gameended) {
			for(int i = 0; i < x; i++) {
				for(int j = 0; j < y; j++) {
						if(getField(i, j).getFlagged()) {
							getField(i, j).setIcon("flagged");
						} else if(getField(i, j).getQmarked()) {
							getField(i, j).setIcon("questionmark");
						} else if(!getField(i, j).getClickable()) {
							getField(i, j).setIcon("facingdown");
						} else
							getField(i, j).setIcon("fieldtype");
				}
			}
		} else {
			for(int i = 0; i < x; i++) {
				for(int j = 0; j < y; j++) {
					getField(i, j).setIcon("facingdown");
					if(getField(i, j).getOpened() && !getField(i, j).getFlagged()) {
						getField(i, j).setIcon("fieldtype");
					} else if(getField(i, j).getQmarked() && !getField(i, j).getOpened()) {
						getField(i, j).setIcon("questionmark");
					} else if(!getField(i, j).getBomb() && getField(i, j).getFlagged()) {
						getField(i, j).setIcon("crossedbomb");
					} else if(getField(i, j).getOpened() && getField(i, j).getFlagged()) {
						getField(i, j).setIcon("flagged");
					}
				}
			}
			
			
		}
		
	}
	
	public void colorMap() {
		Color color1;
		Color color2;
		Color color3;
		Color color4;
		Color color5;
		
		switch(skintype) {
			case "xp":
				color1 = new Color(192, 192, 192);	// edges
				color2 = new Color(128, 128, 128);	// darkedge
				color3 = new Color(255, 255, 255);	// brightedge
				color4 = new Color(250, 0, 0);		// counters
				color5 = new Color(192, 192, 192);	// upperpanel
				break;
			case "xpblack":
				color1 = new Color(192, 192, 192);	// edges
				color2 = new Color(0, 0, 0);		// darkedge
				color3 = new Color(255, 255, 255);	// brightedge
				color4 = new Color(0, 0, 0);		// counters
				color5 = new Color(192, 192, 192);	// upperpanel
				break;
			case "win98":
				color1 = new Color(192, 192, 192);	// edges
				color2 = new Color(128, 128, 128);	// darkedge
				color3 = new Color(255, 255, 255);	// brightedge
				color4 = new Color(250, 0, 0);		// counters
				color5 = new Color(192, 192, 192);	// upperpanel
				break;
			case "mario":
				color1 = new Color(208, 176, 48);	// edges
				color2 = new Color(152, 120, 24);	// darkedge
				color3 = new Color(248, 248, 96);	// brightedge
				color4 = new Color(248, 248, 96);	// counters
				color5 = new Color(208, 176, 48);	// upperpanel
				break;
			case "mine":
				color1 = new Color(52, 67, 70);		// edges
				color2 = new Color(6, 13, 18);		// darkedge
				color3 = new Color(6, 13, 18);		// brightedge
				color4 = new Color(113, 177, 195);	// counters
				color5 = new Color(26, 38, 42);		// upperpanel
				break;
			/*case "newskin":
				color1 = new Color(192, 192, 192);	// edges
				color2 = new Color(128, 128, 128);	// darkedge
				color3 = new Color(255, 255, 255);	// brightedge
				color4 = new Color(250, 0, 0);		// counters
				color5 = new Color(192, 192, 192);	// upperpanel
				break;*/
			default:
				color1 = Color.WHITE;	// background
				color2 = Color.WHITE;	// darkedge
				color3 = Color.WHITE;	// brightedge
				color4 = Color.WHITE;	// counters
				color5 = Color.WHITE;	// upperpanel
				break;
		}
		
		p1.setBackground(color1);
		inner1.setBackground(color5);
		inner2.setBackground(color5);
		inner3.setBackground(color5);
		p2.setBackground(color1);
		
		bleft.setForeground(color4);
		timecounter.setForeground(color4);
		
		p1.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(6,6,0,5), 
				BorderFactory.createBevelBorder(0, color2, color2, color3, color3)));
		
		p2.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(6,6,5,5), 
				BorderFactory.createBevelBorder(0, color2, color2, color3, color3)));
		
		outer.setBorder(BorderFactory.createBevelBorder(0, color3, color3, color2, color2));
	}
	
	public void putRandomBombs() {
		for(int i = 0; i < bombs; i++) {
			Random r = new Random();
			int Low = 0;
			int High = x*y;
			int Result = r.nextInt(High-Low) + Low;
			
			if(getField(Result / y, Result % y).getBomb() || getField(Result / y, Result % y).getClickable())
				i--;
			else {
				getField(Result / y, Result % y).setBomb(true);
				getField(Result / y, Result % y).setFieldtype("bomb");
			}
		}
	}
	
	public void howManyBombsAround() {
		int bombcnt = 0;
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				bombcnt = 0;
					if(getField(i-1, j-1).getBomb())
						bombcnt++;
					if(getField(i-1, j).getBomb())
						bombcnt++;
					if(getField(i-1, j+1).getBomb())
						bombcnt++;
					if(getField(i, j-1).getBomb())
						bombcnt++;
					if(getField(i, j+1).getBomb())
						bombcnt++;
					if(getField(i+1, j-1).getBomb())
						bombcnt++;
					if(getField(i+1, j).getBomb())
						bombcnt++;
					if(getField(i+1, j+1).getBomb())
						bombcnt++;

				if(!getField(i, j).getBomb())
					getField(i, j).setFieldtype(Integer.toString(bombcnt));
			}
		}
	}
	
	public void exploded() {
		timer.stop();
		setSmileystate("dead");
		exploded = true;
		gameended = true;
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				if(getField(i, j).getBomb()) {
					getField(i, j).setOpened(true);
					getField(i, j).setIcon("fieldtype");
				}
				if(!getField(i, j).getClickable() && getField(i, j).getFlagged()) {
					getField(i, j).setOpened(true);
					getField(i, j).setIcon("crossedbomb");
					if(getField(i, j).getBomb())
						getField(i, j).setIcon("flagged");
				}
				getField(i, j).Reveal();
			}
		}
	}
	
	public boolean isEnd() {
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				if(!getField(i, j).getBomb() && !getField(i, j).getClickable()) {
					return false;
				}
			}
		}
		timer.stop();
		gameended = true;
		
		return true;
	}
	
	public void revealAllFields() {
		if(!exploded) setSmileystate("glasses");
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				if(getField(i, j).getBomb() && !getField(i, j).getClickable()) {
					getField(i, j).setIcon("flagged");
					getField(i, j).setFlagged(true);
				}
				getField(i, j).Reveal();
			}
		}
		//RANKING CHECK
		if(!exploded && !won && gametype != 3 && seconds < myRanking.getTime(gametype)) {
			won = true;
			
			String mode = "";
			
			if(gametype == 0)
				mode = "beginner";
			if(gametype == 1)
				mode = "intermediate";
			if(gametype == 2)
				mode = "expert";
			
			JPanel panel = new JPanel();
			
			JLabel[] labels = new JLabel[3];
			
			labels[0] = new JLabel("You have the fastest time");
			labels[1] = new JLabel("for " + mode + " level.");
			labels[2] = new JLabel("Please enter your name.");
			
			for(JLabel lab: labels) {
				lab.setForeground(Color.BLACK);
				panel.add(lab);
				lab.setAlignmentX(Component.CENTER_ALIGNMENT);
			}
			panel.setLayout (new BoxLayout (panel, BoxLayout.Y_AXIS));
			
			JTextField text = new JTextField(myRanking.getName(gametype));
			
			text.addFocusListener(new FocusListener() {
	            @Override public void focusLost(final FocusEvent pE) {}
	            @Override public void focusGained(final FocusEvent pE) {
	                text.selectAll();
	            }
	        });
			
			labels[2].setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
			
			panel.add(text);
			
			Object[] options = {"OK"};
		    JOptionPane.showOptionDialog(this,
		                   panel,
		                   null,//"Title",
		                   JOptionPane.PLAIN_MESSAGE,
		                   JOptionPane.PLAIN_MESSAGE,
		                   null,
		                   options,
		                   options[0]);
		    
		    String finaltext = text.getText();
		    if(finaltext.length() > 32)
		    	finaltext = finaltext.substring(0, 32);
		    
		    myRanking.setRanking(seconds, finaltext, gametype);
		    
		    myRanking.outSerialize();
		    
		    myRanking.showRanking();
		}
	}
	
	public void newGame(int a, int b, int c) {
		if(a < 1) a = 1;						//MIN height
		if(a > 57) a = 57;						//MAX height
		if(b < 9) b = 9;						//MIN width
		if(b > 114) b = 114;					//MAX width
		if(c < 10 && a > 2) c = 10;				//MIN bombs
		if(a < 3){								//MAX bombs
			if(c < 1)
				c = 1;
			if(c > b)
				c = b-1;
			if(a == 2)
				if((a-1)*(b-1) < c) c = (a-1)*(b-1);
		}
		else if((a-1)*(b-1) < c) c = (a-1)*(b-1);
		setSmileystate("normal");
		bombs = c;
		bombsleft = bombs;
		won = false;
		timer.stop();
		seconds = 0;
		timecounter.setText(String.format("%03d", seconds));
		
		updateBombsleft();
		
		MyMouseListener.firstclick = true;
		MyMouseListener.firstbomb = true;
		exploded = false;
		gameended = false;
		
		if(a != x || b != y) {
			p2.removeAll();
			for(int i = 0; i < x; i++)  {
		        fields.clear();
		    }
			for(int i = 0; i < a; i++)  {
		        fields.add(new ArrayList<Field>());
		    }
			
			for(int i = 0; i < a; i++) {
				for(int j = 0; j < b; j++) {
					Field f = new Field("0", i, j, this);
					fields.get(i).add(f);
					p2.add(f.getButton());
				}
			}
			
			p2.setLayout(new GridLayout(a, b));
			pack();
		} else {
			for(int i = 0; i < x; i++) {
				for(int j = 0; j < y; j++) {
					if(getField(i, j).getOpened() || getField(i, j).getFlagged() || getField(i, j).getQmarked()) getField(i, j).setIcon("facingdown");
					getField(i, j).setBomb(false);
					getField(i, j).setFlagged(false);
					getField(i, j).setQmarked(false);
					getField(i, j).UnReveal();
					getField(i, j).setOpened(false);
					getField(i, j).setFieldtype("0");
				}
			}
		}
		x = a;
		y = b;
	}
}