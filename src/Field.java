import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Field {
	
	private MineMap m;
	private int x;
	private int y;
	private JButton b;
	private boolean bomb;
	private boolean flagged;
	private boolean qmarked;
	private boolean clickable;
	private boolean opened;
	private String fieldtype;
	
	public Field(String s, int i, int j, MineMap mm) {
		x = i;
		y = j;
		m = mm;
		b = new JButton();
		if(s.equals("bomb"))
			bomb = true;
		else
			bomb = false;
		qmarked = false;
		fieldtype = s;
		flagged = false;
		clickable = false;
		opened = false;
		b.setBorder(BorderFactory.createEmptyBorder());
		b.setIcon(new ImageIcon("images\\" + m.getSkintype() + "\\facingdown.png"));
		b.addMouseListener(new MyMouseListener(this, m));
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setIcon(String  s) {
		if(!s.equals("fieldtype"))
			b.setIcon(new ImageIcon("images\\" + m.getSkintype() + "\\" + s + ".png"));
		else
			b.setIcon(new ImageIcon("images\\" + m.getSkintype() + "\\" + getFieldtype() + ".png"));
	}
	
	public JButton getButton() {
		return b;
	}
	
	public boolean getBomb() {
		return bomb;
	}
	
	public boolean getFlagged() {
		return flagged;
	}
	
	public void setFlagged(boolean b) {
		flagged = b;
	}
	
	public void setBomb(boolean b) {
		bomb = b;
	}
	
	public boolean getQmarked() {
		return qmarked;
	}
	
	public void setQmarked(boolean b) {
		qmarked = b;
	}
	
	public boolean getClickable() {
		return clickable;
	}
	
	public String getFieldtype() {
		return fieldtype;
	}
	
	public void setFieldtype(String s) {
		fieldtype = s;
	}
	
	public void Reveal() {
		clickable = true;
	}
	
	public void UnReveal() {
		clickable = false;
	}
	
	public void setOpened(boolean b) {
		opened = b;
	}
	
	public boolean getOpened() {
		return opened;
	}
}
