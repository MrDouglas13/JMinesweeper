import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class MineMain {
	
	private static Ranking myRanking;
	
	private static MineMap m;

	public static void setUIFont (FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	    	Object key = keys.nextElement();
	    	Object value = UIManager.get (key);
	    	if (value instanceof FontUIResource)
	    		UIManager.put (key, f);
	    }
	}
	
	public static void main(String[] args){

		setUIFont (new FontUIResource("Segoe UI", Font.PLAIN, 12));
		
		myRanking = new Ranking();
		
		m = new MineMap(9, 9, 10, myRanking);
		
		myRanking.addMap(m);
		
		myRanking.inSerialize();	
	}
}
