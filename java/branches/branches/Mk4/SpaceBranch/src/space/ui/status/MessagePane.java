package space.ui.status;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;

public class MessagePane extends JPanel {
	private static final long serialVersionUID = -3718141132105631244L;

	private int messageMarker;
	
	private Message currentMessage;
	
	private final Vector<Message> messages;
	private final Vector<Vector<Message>> history;
	
	public final JTextArea messageArea;
	public final JButton goTo;
	public final JButton next;
	public final JButton prev;
	
	public MessagePane(){
		messages = new Vector<Message>();
		history = new Vector<Vector<Message>>();
		
		messageArea = new JTextArea();
		messageArea.setEditable(false);
		
		
		JPanel buttonPanel = new JPanel();
		SpringLayout buttonPanelLayout = new SpringLayout();
		buttonPanel.setLayout(buttonPanelLayout);
		
		prev = new JButton(new AbstractAction("Prev"){
			private static final long serialVersionUID = 0L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				prevMessage();
			}
		});buttonPanel.add(prev);
		
		goTo = new JButton("View");buttonPanel.add(goTo);
		goTo.setEnabled(false);
		
		next = new JButton(new AbstractAction("Next"){
			private static final long serialVersionUID = 0L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				nextMessage();
			}
		});buttonPanel.add(next);
		
		//The goto button in the center.
		buttonPanelLayout.putConstraint(
				SpringLayout.HORIZONTAL_CENTER, goTo, 
				0,
				SpringLayout.HORIZONTAL_CENTER, buttonPanel
		);
		buttonPanelLayout.putConstraint(
				SpringLayout.NORTH, goTo, 
				5,
				SpringLayout.NORTH, buttonPanel
		);
		//The prev button to the left.
		buttonPanelLayout.putConstraint(
				SpringLayout.EAST, prev, 
				-5,
				SpringLayout.WEST, goTo
		);
		buttonPanelLayout.putConstraint(
				SpringLayout.NORTH, prev, 
				0,
				SpringLayout.NORTH, goTo
		);
		//The next button to the right.
		buttonPanelLayout.putConstraint(
				SpringLayout.WEST, next, 
				5,
				SpringLayout.EAST, goTo
		);
		buttonPanelLayout.putConstraint(
				SpringLayout.NORTH, next, 
				0,
				SpringLayout.NORTH, goTo
		);
		buttonPanel.setPreferredSize(new Dimension(200,50));
		buttonPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		
		this.setLayout(new BorderLayout());
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(messageArea, BorderLayout.CENTER);
	}
	
	public void addMessage(Message message){
		messages.add(message);
	}
	
	@SuppressWarnings("unchecked")
	public void backupMessages(){
		history.add((Vector<Message>)messages.clone());
		messages.removeAllElements();
		messageMarker = 0;
	}
	
	public void nextMessage(){
		if((messageMarker-1) != messages.size()){
			messageMarker++;
			updateMessage();
		}
	}
	
	public void prevMessage(){
		if(messageMarker != 0){
			messageMarker--;
			updateMessage();
		}
	}
	
	private void updateMessage(){
		currentMessage = messages.get(messageMarker);
		messageArea.setDocument(currentMessage.message());
		
		if(currentMessage.goTo() != null){
			goTo.setAction(currentMessage.goTo());
		}
		if(messageMarker == 0){
			prev.setEnabled(false);
		}else{
			next.setEnabled(true);
		}
		if((messageMarker-1) == messages.size()){
			next.setEnabled(false);
		}else{
			prev.setEnabled(true);
		}
	}
}
