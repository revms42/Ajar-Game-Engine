package space.ui.status;

import javax.swing.Action;
import javax.swing.text.Document;

public interface Message {

	public final static short INDUSTRY = 1;
	public final static short ORBITAL = 2;
	public final static short MILITARY = 4;
	public final static short EXPLORATION = 8;
	public final static short TERRAFORMING = 16;
	public final static short ESPIONAGE = 32;
	public final static short DIPLOMATIC = 64;
	
	public Action goTo();
	public Document message();
	public short type();
}
