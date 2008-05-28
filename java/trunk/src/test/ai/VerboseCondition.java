package test.ai;

import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import org.IGameManifest;
import org.interaction.AbstractCondition;
import org.interaction.ActionPalette;

public abstract class VerboseCondition<A,K> extends AbstractCondition<A,K> {
	protected final JTree log;
	
	public VerboseCondition(
			ActionPalette<A, K> palette, 
			Vector<A> actions, 
			IGameManifest manifest,
			JTree log) 
	{
		super(palette, actions, manifest);
		this.log = log;
	}
	
	public VerboseCondition(
			ActionPalette<A, K> palette, 
			IGameManifest manifest,
			JTree log) 
	{
		super(palette, manifest);
		this.log = log;
	}
	
	public VerboseCondition(
			IGameManifest manifest,
			JTree log) 
	{
		super(manifest);
		this.log = log;
	}
	
	public void logAction(String message){
		MutableTreeNode node = (MutableTreeNode)log.getSelectionPath().getLastPathComponent();
		DefaultMutableTreeNode child = new DefaultMutableTreeNode(message);
		
		((DefaultTreeModel)log.getModel()).insertNodeInto(child, node, node.getChildCount());
	}
}
