import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

final class MyMouseListener implements MouseListener {
	
	private Field f;
	protected static boolean firstclick = true;
	protected static boolean firstbomb = true;
	private MineMap m;
	
	public MyMouseListener(Field ff, MineMap mm) {
		f = ff;
		m = mm;
	}

	public void mousePressed(MouseEvent me) {
		if(!m.getGameended() && SwingUtilities.isLeftMouseButton(me)) m.setSmileystate("oface");
		if(!m.getGameended() && SwingUtilities.isLeftMouseButton(me) && !f.getOpened() && !f.getFlagged() && !f.getQmarked()) f.setIcon("holddown");
		if(!m.getGameended() && SwingUtilities.isLeftMouseButton(me) && f.getQmarked()) f.setIcon("holddownqmark");
		
		if(SwingUtilities.isRightMouseButton(me) && !f.getFlagged() && !f.getClickable() && !f.getQmarked()) {
			m.decreaseBombsleft();
			f.setIcon("flagged");
			f.setFlagged(true);
		} else if(SwingUtilities.isRightMouseButton(me) && f.getFlagged() && !f.getClickable() && !m.getQmarks() && !f.getQmarked()){
			m.increaseBombsleft();
			f.setIcon("facingdown");
			f.setFlagged(false);
		} else if(SwingUtilities.isRightMouseButton(me) && !f.getFlagged() && !f.getClickable() && !m.getQmarks() && f.getQmarked()){
			f.setIcon("facingdown");
			f.setQmarked(false);
		} else if(SwingUtilities.isRightMouseButton(me) && f.getFlagged() && !f.getClickable() && m.getQmarks() && !f.getQmarked()) {
			m.increaseBombsleft();
			f.setIcon("questionmark");
			f.setFlagged(false);
			f.setQmarked(true);
		} else if(SwingUtilities.isRightMouseButton(me) && !f.getFlagged() && !f.getClickable() && m.getQmarks() && f.getQmarked()) {
			f.setIcon("facingdown");
			f.setQmarked(false);
		}
	}
	
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mouseClicked(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {
		if(!m.getGameended()) m.setSmileystate("normal");
		if(SwingUtilities.isLeftMouseButton(me) && !f.getFlagged() && !f.getClickable()) {
			f.Reveal();
			f.setOpened(true);
			f.setQmarked(false);
			if(firstclick) {
				m.startTimer();
				m.putRandomBombs();
				m.howManyBombsAround();
				firstclick = false;
			}
			if(f.getBomb() && firstbomb) {
				f.setFieldtype("explodedbomb");
				firstbomb = false;
				m.exploded();
			}
			f.setIcon("fieldtype");
			if(m.isEnd())
				m.revealAllFields();
			if(f.getFieldtype().equals("0")) {
				recursionForAllNull(f);
			}
		}
	}
	
	public void recursionForAllNull(Field f) {
		f.Reveal();
		f.setOpened(true);
		f.setQmarked(false);
		if(f.getFlagged()) {
			m.increaseBombsleft();
			f.setFlagged(false);
		}
		f.setIcon("fieldtype");
		if(f.getX() > 0 && f.getY() > 0 && !m.getField(f.getX()-1, f.getY()-1).getClickable() && f.getFieldtype().equals("0")) {
			recursionForAllNull(m.getField(f.getX()-1, f.getY()-1));
		}
		if(f.getX() > 0 && !m.getField(f.getX()-1, f.getY()).getClickable() && f.getFieldtype().equals("0")) {
			recursionForAllNull(m.getField(f.getX()-1, f.getY()));
		}
		if(f.getX() > 0 && m.getMapY() > f.getY() && !m.getField(f.getX()-1, f.getY()+1).getClickable() && f.getFieldtype().equals("0")) {
			recursionForAllNull(m.getField(f.getX()-1, f.getY()+1));
		}
		if(f.getY() > 0 && !m.getField(f.getX(), f.getY()-1).getClickable() && f.getFieldtype().equals("0")) {
			recursionForAllNull(m.getField(f.getX(), f.getY()-1));
		}
		if(m.getMapY() > f.getY() && !m.getField(f.getX(), f.getY()+1).getClickable() && f.getFieldtype().equals("0")) {
			recursionForAllNull(m.getField(f.getX(), f.getY()+1));
		}
		if(f.getX() < m.getMapX() && f.getY() > 0 && !m.getField(f.getX()+1, f.getY()-1).getClickable() && f.getFieldtype().equals("0")) {
			recursionForAllNull(m.getField(f.getX()+1, f.getY()-1));
		}
		if(f.getX() < m.getMapX() && !m.getField(f.getX()+1, f.getY()).getClickable() && f.getFieldtype().equals("0")) {
			recursionForAllNull(m.getField(f.getX()+1, f.getY()));
		}
		if(f.getX() < m.getMapX() && f.getY() < m.getMapY() && !m.getField(f.getX()+1, f.getY()+1).getClickable() && f.getFieldtype().equals("0")) {
			recursionForAllNull(m.getField(f.getX()+1, f.getY()+1));
		}
		if(m.isEnd())
			m.revealAllFields();
	}
	
}