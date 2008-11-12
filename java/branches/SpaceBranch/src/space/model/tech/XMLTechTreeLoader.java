package space.model.tech;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.model.LinearStat;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import space.model.Resource;
import space.model.component.ComponentType;
import space.model.component.DefaultComponent;
import space.model.component.IComponent;

public class XMLTechTreeLoader<I> implements ITechTreeLoader<File,I> {
	public final static String namespaceURI = 
		"http://sourceforge.net/projects/macchiatodoppio/";
	private final static String techtreenodename = "TechTree";
	private final static String componentnodename = "Component";
	
	private final DocumentBuilder builder;
	
	private Vector<IComponent<I>> components;
	
	public XMLTechTreeLoader(File model)
			throws SAXException, ParserConfigurationException
	{
		SchemaFactory sfact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sfact.newSchema(model);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setSchema(schema);
		
		builder = factory.newDocumentBuilder();
	}
	
	private boolean check(Node node, String type){
		return	node.isDefaultNamespace(namespaceURI) && 
				node.getNodeName().equalsIgnoreCase(type);
	}
	
	@Override
	public Vector<IComponent<I>> loadTree(File file) throws SAXException, IOException {
		components = new Vector<IComponent<I>>();
		
		Document doc = builder.parse(file);
		Node root = doc.getFirstChild();
		
		if(check(root,techtreenodename)){
			NodeList children = root.getChildNodes();
			
			for(int i = 0; i < children.getLength(); i++){
				Node child = children.item(i);
				
				if(check(child,componentnodename)){
					parseComponent(child);
				}
			}
		}
		
		return components;
	}

	private void parseComponent(Node entry){
		String name;
		String description;
		
		name = entry.getAttributes().getNamedItem("name").getNodeValue();
		description = entry.getAttributes().getNamedItem("description").getNodeValue();
		
		DefaultComponent<I> component = new DefaultComponent<I>(name,description);
		
		NamedNodeMap attrs = entry.getAttributes();
		
		Node id = attrs.getNamedItem("javaID");
		Long hashCode = new Long(
				attrs.getNamedItem("hashCode").getNodeValue()
		);
		
		String image = attrs.getNamedItem("image").getNodeValue();
		
		addStat("mass","mass",component,attrs);
		addStat("signature","signature",component,attrs);
		addStat("hitpoints","hitpoints",component,attrs);
		
		for(Resource res : Resource.RESOURCES){
			addStat(res.name(),"cost" + res.shortName(),component,attrs);
		}
		
		for(Technology tech : Technology.TECHS){
			addStat(tech.getName(),tech.getName(),component,attrs);
		}
		
		if(id != null){
			String[] typeName = id.getNodeValue().split(".");
			
			for(ComponentType type : ComponentType.COMPONENTTYPES){
				if(type.getName().equalsIgnoreCase(typeName[0])){
					switch(type){
					//TODO: Left off here.
					}
				}
			}
		}

	}
	
	private void parseWeapon(Node weapon){
		
	}
	
	private void parseArmor(Node armor){
		
	}
	
	private void parseShield(Node shield){
		
	}
	
	private void parseEngine(Node engine){
		
	}
	
	private void parseBulkhead(Node bulkhead){
		
	}
	
	private void parseConduit(Node conduit){
		
	}
	
	private void parseBay(Node bay){
		
	}
	
	private void parseElectrical(Node electrical){
		
	}
	
	private void parseMechanical(Node mechanical){
		
	}
	
	private void parseOrbital(Node orbital){
		
	}
	
	private void parseMisc(Node misc){
		
	}
	
	private void addStat(
			String name,
			String nodeName,
			DefaultComponent<I> component, 
			NamedNodeMap map
	){
		Node node = map.getNamedItem(nodeName);
		
		Integer v;
		if(node == null || node.getNodeValue().isEmpty()){
			v = new Integer(0);
		}else{
			v = new Integer(node.getNodeValue());
		}
		
		LinearStat stat = new LinearStat(
				v.intValue(),
				Integer.MAX_VALUE,
				0,
				v.intValue()
		);
		
		component.getStats().setStat(name, stat);
		map.removeNamedItem(nodeName);
	}
}
