package test.ai;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.IGameManifest;
import org.interaction.IAction;

public abstract class VerboseAction<K> implements IAction<K> {
	protected final JTree log;
	private final IGameManifest manifest;
	
	public VerboseAction(IGameManifest manifest, JTree log) {
		this.log = log;
		this.manifest = manifest;
	}

	public void logAction(String message){
		MutableTreeNode node = (MutableTreeNode)log.getSelectionPath().getLastPathComponent();
		DefaultMutableTreeNode child = new DefaultMutableTreeNode(message);
		
		((DefaultTreeModel)log.getModel()).insertNodeInto(child, node, node.getChildCount());
	}
	
	@Override
	public IGameManifest getManifest() {
		return manifest;
	}
}
