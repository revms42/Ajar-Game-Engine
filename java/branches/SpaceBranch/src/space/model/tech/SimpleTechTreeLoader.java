package space.model.tech;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.model.LinearStat;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import space.model.component.ComponentType;
import space.model.component.DefaultComponent;
import space.model.component.IComponent;

public class SimpleTechTreeLoader implements ITechTreeLoader<File,String> {

	private Vector<IComponent<String>> components;
	
	private final DocumentBuilder builder;
	
	public SimpleTechTreeLoader() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		
		builder = factory.newDocumentBuilder();
	}
	
	@Override
	public Vector<IComponent<String>> loadTree(File file) throws SAXException, IOException {
		components = new Vector<IComponent<String>>();
		
		Document doc = builder.parse(file);
		Node root = doc.getFirstChild();
		
		parseNode(root,null,false);
		
		return components;
	}
	
	private void iterate(
			Node root,
			IComponent<String> comp,
			ParserHelper helper,
			boolean secondary
	){
		if(root.hasChildNodes()){
			NodeList list = root.getChildNodes();
			
			for(int i = 0; i < list.getLength(); i++){
				parseNode(list.item(i),comp,secondary);
			}
		}
	}
	
	private String getAttribute(Node root, String name){
		NamedNodeMap map = root.getAttributes();
		
		return map.getNamedItem(name).getNodeValue();
	}

	private String parseNode(Node root,IComponent<String> comp,boolean secondary){
		switch(root.getNodeType()){
		case Node.ATTRIBUTE_NODE:
			return root.getNodeValue();
		case Node.CDATA_SECTION_NODE:
			//Supported in future.
			break;
		case Node.COMMENT_NODE:
			//Irrelevant.
			break;
		case Node.DOCUMENT_FRAGMENT_NODE:
			//Not supported
			break;
		case Node.DOCUMENT_NODE:
			//Root of the xml....
			iterate(root,null,null,false);
			break;
		case Node.DOCUMENT_TYPE_NODE:
			break;
		case Node.ELEMENT_NODE:
			ComponentType type = parseComponentType(root);
			
			if(type != null){
				handleComponent(root,type);
			}else{
				if(comp == null){
					iterate(root,null,secondary);
				}else{
					if(root.getNodeName().equalsIgnoreCase("TechReqs")){
						addTechReqs(root,comp);
					}else if(root.getNodeName().equalsIgnoreCase("Cost")){
						addCostReqs(root,comp);
					}else if(root.getNodeName().equalsIgnoreCase("Stats")){
						addStats(root,comp,secondary);
					}else if(root.getNodeName().equalsIgnoreCase("Secondary")){
						iterate(root,comp,true);
					}
				}
			}
			
			break;
		case Node.ENTITY_NODE:
			//Not supported?
			break;
		case Node.ENTITY_REFERENCE_NODE:
			//Not supported.
			break;
		case Node.NOTATION_NODE:
			//Not supported.
			break;
		case Node.PROCESSING_INSTRUCTION_NODE:
			//Not supported.
			break;
		case Node.TEXT_NODE:
			break;
		default:
			break;
		}
		
		return root.getNodeValue();
	}


	private ComponentType parseComponentType(Node node){
		String name = node.getNodeName();
		
		for(ComponentType type : ComponentType.COMPONENTTYPES){
			if(type.getName().equalsIgnoreCase(name)){
				return type;
			}
		}
		
		return null;
	}
	
	private void handleComponent(Node node, ComponentType type){
		DefaultComponent<String> component = 
			new DefaultComponent<String>(
				getAttribute(node,"name"),
				node.getNodeValue()
			);
		
		switch(type){
		case ARMOR:
			break;
		case BAY:
			break;
		case BULKHEAD:
			break;
		case CONDUIT:
			break;
		case ELECTRICAL:
			break;
		case ENGINE:
			break;
		case MECHANICAL:
			break;
		case MISC:
			break;
		case ORBITAL:
			break;
		case SHIELD:
			break;
		case WEAPON:
			break;
		default:
			break;
		}
	}
	
	
	private void addTechReqs(Node root, IComponent<String> comp) {
		
	}
	
	private void addCostReqs(Node root, IComponent<String> comp) {
		// TODO Auto-generated method stub
	}
	
	private void addStats(Node root, IComponent<String> comp, boolean secondary) {
		// TODO Auto-generated method stub
	}
	
	private void addStat(Node node, String name, IComponent<String> comp, boolean secondary){
		Integer v = new Integer(node.getNodeValue());
		
		if(secondary){
			name = "Secondary" + comp.getSecondaryTypes().size() + ":" + name;
		}
		
		LinearStat lstat = new LinearStat(
				v.intValue(),
				Integer.MAX_VALUE,
				0,
				v.intValue()
		);
		
		comp.setStat(name, lstat);
	}
	
	private abstract class ParserHelper {
		
		private final String start;
		
		public ParserHelper(String start){
			this.start = start;
		}
		
		public abstract void call(
				Node node, 
				String end, 
				IComponent<String> comp, 
				boolean secondary
		);
	}
}
