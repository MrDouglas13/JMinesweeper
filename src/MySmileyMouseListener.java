import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class MySmileyMouseListener implements MouseListener {
	
	private static boolean holddown = false;
	private boolean inside = false;
	private MineMap m;
	
	public MySmileyMouseListener(MineMap mm) {
		m = mm;
	}
	
	public static boolean getHolddown() {
		return holddown;
	}
	
	public void mousePressed(MouseEvent me) {
		if(inside && SwingUtilities.isLeftMouseButton(me)) holddown = true;
		if(SwingUtilities.isLeftMouseButton(me)) m.setSmileystate("holddown", true);
	}
	public void mouseEntered(MouseEvent me) {
		inside = true;
		if(SwingUtilities.isLeftMouseButton(me) && holddown) m.setSmileystate("holddown", true);
	}
	public void mouseExited(MouseEvent me) {
		inside = false;
		if(SwingUtilities.isLeftMouseButton(me)) m.setSmileystate(m.getSmileystate());
	}
	public void mouseClicked(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {
		holddown = false;
		if(SwingUtilities.isLeftMouseButton(me) && inside) m.newGame(m.getMapX(), m.getMapY(), m.getBombs());
	}
}

