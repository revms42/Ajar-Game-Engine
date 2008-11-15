package space.model.tech;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.display.DisplayFactory;
import org.display.IDisplayContext;
import org.display.ImageBoard;
import org.display.ImageOpPalette;
import org.display.ImagePalette;
import org.interaction.IEntity;
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
import space.model.component.IComponentType;
import space.model.planet.Environment;

public class XMLTechTreeLoader implements ITechTreeLoader<File,String> {
	public final static String namespaceURI = 
		"http://sourceforge.net/projects/macchiatodoppio/";
	private final static String techtreenodename = "TechTree";
	private final static String componentnodename = "Component";
	
	private final DocumentBuilder builder;
	
	private Vector<IComponent<String>> components;
	private ImagePalette<String> palette;
	private DisplayFactory<String,String> factory;
	
	private DirectedCompContext directedContext;
	private DefaultCompContext defaultContext;
	
	private final int[] d1x = {24,2,2,24};
	private final int[] d1y = {24,11,35,46};
	
	private final int[] d2x = {24,2,24,46};
	private final int[] d2y = {24,11,2,11};
	
	private final int[] d3x = {24,46,46,24};
	private final int[] d3y = {24,11,35,46};
	
	public XMLTechTreeLoader(File model)
			throws SAXException, ParserConfigurationException
	{
		SchemaFactory sfact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sfact.newSchema(model);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		//factory.setSchema(schema);
		
		builder = factory.newDocumentBuilder();
	}
	
	private boolean check(Node node, String type){
		return  node.getNodeName().equalsIgnoreCase(type) || 
				node.getNodeName().equalsIgnoreCase("tns:" + type);
	}
	
	private Node getNextValid(NodeList list, String type){
		for(int i = 0; i < list.getLength(); i++){
			if(list.item(i).getNodeType() != Node.TEXT_NODE){
				if(check(list.item(i),type)){
					return list.item(i);
				}
			}
		}
		return null;
	}
	
	private Node getNextValid(NodeList list, int start){
		for(int i = start; i < list.getLength(); i++){
			if(list.item(i).getNodeType() != Node.TEXT_NODE){
				return list.item(i);
			}
		}
		return null;
	}
	
	private String getValue(NamedNodeMap map, String name){
		Node node = map.getNamedItem(name);
		if(node == null){
			node = map.getNamedItem("tns:" + name);
		}
		return node.getNodeValue();
	}
	
	@Override
	public Vector<IComponent<String>> loadTree(File file) throws SAXException, IOException {
		components = new Vector<IComponent<String>>();
		
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
		}else if(check(root,"TechTree")){
			NodeList children = root.getFirstChild().getChildNodes();
			
			for(int i = 0; i < children.getLength(); i++){
				Node child = children.item(i);
				
				if(check(child,componentnodename)){
					parseComponent(child);
				}
			}
		}
		
		System.out.println(components.size());
		
		return components;
	}

	private void parseComponent(Node entry){
		String name;
		String description;
		
		name = entry.getAttributes().getNamedItem("name").getNodeValue();
		description = entry.getAttributes().getNamedItem("description").getNodeValue();
		
		DefaultComponent<String> component = new DefaultComponent<String>(name,description);
		
		NamedNodeMap attrs = entry.getAttributes();
		
		Node id = attrs.getNamedItem("javaID");
		Long hashCode = new Long(
				attrs.getNamedItem("hashCode").getNodeValue()
		);
		
		String image = attrs.getNamedItem("image").getNodeValue();
		/*
		addStat("mass","mass",component,attrs);
		addStat("signature","signature",component,attrs);
		addStat("hitpoints","hitpoints",component,attrs);
		
		for(Resource res : Resource.RESOURCES){
			addStat(res.name(),"cost" + res.shortName(),component,attrs);
		}
		
		for(Technology tech : Technology.TECHS){
			addStat(tech.getName(),tech.getName(),component,attrs);
		}*/
		
		if(id != null && id.getNodeValue().length() > 0){
			String[] typeName = id.getNodeValue().split(".");
			
			NodeList nodes = entry.getChildNodes();
			for(int i = 0; i < nodes.getLength(); i++){
				if(check(nodes.item(i),"Primary")){
					parseComponentType(nodes.item(i),component,true,typeName);
					break;
				}
			}
		}else if(hashCode != null && hashCode.intValue() != 0){
			//TODO: We don't support this yet.
		}else{
			NodeList nodes = entry.getChildNodes();
			for(int i = 0; i < nodes.getLength(); i++){
				if(check(nodes.item(i),"Primary")){
					Node typeNode = getNextValid(nodes.item(i).getChildNodes(),0);
					
					String typename = typeNode.getNodeName();
					if(typename.startsWith("tns:")){
						typename = typename.substring(4);
					}
					
					parseComponentType(nodes.item(i),component,true,typename);
					break;
				}
			}
		}
		
		NodeList nodes = entry.getChildNodes();
		for(int i = 0; i < nodes.getLength(); i++){
			if(check(nodes.item(i),"Secondary")){
				Node typeNode = getNextValid(nodes.item(i).getChildNodes(),(0));
				
				String typename = typeNode.getNodeName();
				if(typename.startsWith("tns:")){
					typename = typename.substring(4);
				}
				
				parseComponentType(nodes.item(i),component,false,typename);
				break;
			}
		}

		parseComponentImage(image, component);
		
		components.add(component);
	}
	
	private void parseComponentImage(String image, DefaultComponent<String> component) {
		BufferedImage bi = loadImage(image);
		
		if(bi == null){
			bi = new BufferedImage(48,48,BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2 = bi.createGraphics();
			
			g2.setColor(Color.MAGENTA.darker());
			g2.fillPolygon(d1x, d1y, 4);
			g2.setColor(Color.CYAN.darker());
			g2.fillPolygon(d2x, d2y, 4);
			g2.setColor(Color.GREEN.darker());
			g2.fillPolygon(d3x, d3y, 4);
			
			g2.finalize();
			g2.dispose();
		}
		
		ImageBoard board = new ImageBoard(bi,new Dimension(48,48));
		
		if(palette == null){
			palette = new ImagePalette<String>();
		}
		
		palette.put(component.getName(), board);
		
		if(factory == null){
			factory = new DisplayFactory<String,String>(palette);
		}
		
		component.setDisplayFactory(factory);
		
		switch((ComponentType)component.getPrimaryType()){
		case BAY:
			if(directedContext == null){
				directedContext = new DirectedCompContext();
			}
			component.setDisplayContext(directedContext);
			break;
		case CONDUIT:
			if(directedContext == null){
				directedContext = new DirectedCompContext();
			}
			component.setDisplayContext(directedContext);
			break;
		case BULKHEAD:
			if(directedContext == null){
				directedContext = new DirectedCompContext();
			}
			component.setDisplayContext(directedContext);
			break;
		default:
			if(defaultContext == null){
				defaultContext = new DefaultCompContext();
			}
			component.setDisplayContext(defaultContext);
			break;
		}
	}
	
	private BufferedImage loadImage(String uri){
		try {
			File file = new File(new URI(uri));
			
			try {
				return ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			return null;
		} catch (IllegalArgumentException e2){
			e2.printStackTrace();
			return null;
		}
	}

	private void parseComponentType(
			Node node, 
			DefaultComponent<String> component,
			boolean isPrimary,
			String... id
	){
		for(ComponentType type : ComponentType.COMPONENTTYPES){
			if(		type.getName().equalsIgnoreCase(id[0]) ||
					(type.getName() + "s").equalsIgnoreCase(id[0])){
				if(isPrimary){
					component.setType(type);
				}else{
					component.addSecondaryType(type);
				}
				
				node = getNextValid(node.getChildNodes(),0);
				
				switch(type){
				case ARMOR:
					parseArmor(node,component,isPrimary);
					break;
				case BAY:
					if(id.length > 1){
						parseBay(node,component,id[1],isPrimary);
					}else{
						parseBay(node,component,null,isPrimary);
					}
					break;
				case BULKHEAD:
					parseBulkhead(node,component,isPrimary);
					break;
				case CONDUIT:
					parseConduit(node,component,isPrimary);
					break;
				case ELECTRICAL:
					if(id.length > 1){
						parseElectrical(node,component,id[1],isPrimary);
					}else{
						parseElectrical(node,component,null,isPrimary);
					}
					break;
				case ENGINE:
					if(id.length > 1){
						parseEngine(node,component,id[1],isPrimary);
					}else{
						parseEngine(node,component,null,isPrimary);
					}
					break;
				case MECHANICAL:
					if(id.length > 1){
						parseMechanical(node,component,id[1],isPrimary);
					}else{
						parseMechanical(node,component,null,isPrimary);
					}
					break;
				case MISC:
					parseMisc(node,component,isPrimary);
					break;
				case ORBITAL:
					if(id.length > 1){
						parseOrbital(node,component,id[1],isPrimary);
					}else{
						parseOrbital(node,component,null,isPrimary);
					}
					break;
				case SHIELD:
					parseShield(node,component,isPrimary);
					break;
				case WEAPON:
					if(id.length > 1){
						parseWeapon(node,component,id[1],isPrimary);
					}else{
						parseWeapon(node,component,null,isPrimary);
					}
					break;
				default:
					break;
				}
			}
		}
	}
	
	private void parseWeapon(
			Node weapon, 
			DefaultComponent<String> component, 
			String subtype,
			boolean isPrimary
	){
		addDefaultStats(ComponentType.WEAPON,component,weapon.getAttributes(),isPrimary);
		
		Node subNode = null;
		for(int i = 0; i < weapon.getChildNodes().getLength(); i++){
			Node node = weapon.getChildNodes().item(i);
			
			if(node.getNodeName().equalsIgnoreCase("Catagorey")){
				subNode = node;
				
				if(subtype == null){
					if(node.getNodeName().equalsIgnoreCase("Beam")){
						subtype = "Beams";
					}else if(node.getNodeName().equalsIgnoreCase("Cannon")){
						subtype = "Cannons";
					}else if(node.getNodeName().equalsIgnoreCase("Rockets")){
						subtype = "Rockets";
					}else if(node.getNodeName().equalsIgnoreCase("Torpeado")){
						subtype = "Torpeados";
					}else if(node.getNodeName().equalsIgnoreCase("Bomb")){
						subtype = "Bombs";
					}else if(node.getNodeName().equalsIgnoreCase("Mine")){
						subtype = "Mines";
					}else if(node.getNodeName().equalsIgnoreCase("Missile")){
						subtype = "Missiles";
					}else{
						subtype = node.getNodeName();
					}
				}
			}else if(node.getNodeName().equalsIgnoreCase("DamageType")){
				NamedNodeMap attrs = node.getAttributes();
				
				String type = attrs.getNamedItem("type").getNodeValue();
				String power = attrs.getNamedItem("power").getNodeValue();
				
				
				setTypedStat(
						component,
						IComponentType.STAT_DAMAGETYPE,
						type,
						power,
						isPrimary
				);
			}
		}
		
		if(subNode != null){
			for(ComponentType.SubType stype : ComponentType.WEAPON.getSubTypes()){
				if(stype.getName().equalsIgnoreCase(subtype)){
					addDefaultStats(stype,component,subNode.getAttributes(),isPrimary);
				}
			}
		}
	}
	
	private void parseArmor(Node armor, DefaultComponent<String> component, boolean isPrimary){
		addDefaultStats(ComponentType.ARMOR,component,armor.getAttributes(),isPrimary);
		
		Node node;
		NamedNodeMap attrs;
		for(int i = 0; i < armor.getChildNodes().getLength(); i++){
			node = armor.getChildNodes().item(i);
			
			if(check(node,"Section")){
				attrs = node.getAttributes();
				
				String type = getValue(attrs,"type");
				String rating = getValue(attrs,"rating");
				
				if(type != null && rating != null){
					setTypedStat(
							component,
							IComponentType.STAT_RATING,
							type,
							rating,
							isPrimary
					);
				}
			}
		}
	}
	
	private void parseShield(
			Node shield, 
			DefaultComponent<String> component, 
			boolean isPrimary
	){
		addDefaultStats(ComponentType.SHIELD,component,shield.getAttributes(),isPrimary);
		
		Node node;
		NamedNodeMap attrs;
		for(int i = 0; i < shield.getChildNodes().getLength(); i++){
			node = shield.getChildNodes().item(i);
			
			if(node.getNodeName().equalsIgnoreCase("Capacity")){
				attrs = node.getAttributes();
				
				if(
					attrs.getNamedItem("type") != null && 
					attrs.getNamedItem("hitpoints") != null
				){
					String type = attrs.getNamedItem("type").getNodeValue();
					String rating = attrs.getNamedItem("hitpoints").getNodeValue();
					
					setTypedStat(
							component,
							IComponentType.STAT_DAMAGETYPE,
							type,
							rating,
							isPrimary
					);
				}
			}
		}
	}
	
	private void parseEngine(
			Node engine, 
			DefaultComponent<String> component, 
			String subtype, 
			boolean isPrimary
	){
		addDefaultStats(ComponentType.ENGINE,component,engine.getAttributes(),isPrimary);
		
		Node subNode = null;
		for(int i = 0; i < engine.getChildNodes().getLength(); i++){
			Node node = engine.getChildNodes().item(i);
			
			if(		node.getNodeName().equalsIgnoreCase("Warp") ||
					node.getNodeName().equalsIgnoreCase("Hyperspace") ||
					node.getNodeName().equalsIgnoreCase("Manuever")
			){
				subNode = node;
				
				if(subtype == null){
					if(node.getNodeName().equalsIgnoreCase("Manuever")){
						subtype = "Thrusters";
					}else if(node.getNodeName().equalsIgnoreCase("Warp")){
						subtype = "Warp Engines";
					}else if(node.getNodeName().equalsIgnoreCase("Hyperspace")){
						subtype = "Hyperspace Uplinks";
					}else{
						subtype = node.getNodeName();
					}
				}
			}
		}
		
		if(subNode != null){
			for(ComponentType.SubType stype : ComponentType.ENGINE.getSubTypes()){
				if(stype.getName().equalsIgnoreCase(subtype)){
					addDefaultStats(stype,component,subNode.getAttributes(),isPrimary);
				}
			}
		}
	}
	
	private void parseBulkhead(
			Node bulkhead, 
			DefaultComponent<String> component, 
			boolean isPrimary
	){
		addDefaultStats(ComponentType.BULKHEAD,component,bulkhead.getAttributes(),isPrimary);
	}
	
	private void parseConduit(
			Node conduit, 
			DefaultComponent<String> component, 
			boolean isPrimary
	){
		addDefaultStats(ComponentType.CONDUIT,component,conduit.getAttributes(),isPrimary);
	}
	
	private void parseBay(
			Node bay, 
			DefaultComponent<String> component, 
			String subtype, 
			boolean isPrimary
	){
		addDefaultStats(ComponentType.BAY,component,bay.getAttributes(),isPrimary);
		
		if(subtype == null){
			subtype = bay.getAttributes().getNamedItem("type").getNodeValue();
			
			if(subtype.equalsIgnoreCase("assualt")){
				subtype = "Assualt Berths";
			}else if(subtype.equalsIgnoreCase("boarding")){
				subtype = "Marine Berths";
			}else if(subtype.equalsIgnoreCase("repair")){
				subtype = "Repair Bays";
			}else if(subtype.equalsIgnoreCase("carrier")){
				subtype = "Carrier Berths";
			}else if(subtype.equalsIgnoreCase("cargo")){
				subtype = "Cargo Bays";
			}else if(subtype.equalsIgnoreCase("colony")){
				subtype = "Colonist Berths";
			}
		}
		
		NamedNodeMap attrs = bay.getAttributes();
		attrs.removeNamedItem("type");
		
		for(ComponentType.SubType stype : ComponentType.BAY.getSubTypes()){
			if(stype.getName().equalsIgnoreCase(subtype)){
				addDefaultStats(stype,component,attrs,isPrimary);
				
				String max = attrs.getNamedItem("storage").getNodeValue();
				
				for(String name : stype.getAssociatedStats()){
					if(isPrimary){
						component.max(name, new Integer(max));
					}else{
						component.max(
								"Secondary" + component.getSecondaryTypes().size() + "." +
								name, new Integer(max)
						);
					}
				}
				break;
			}
		}
	}
	
	private void parseElectrical(
			Node electrical, 
			DefaultComponent<String> component, 
			String subtype, boolean isPrimary
	){
		addDefaultStats(
				ComponentType.ELECTRICAL,
				component,
				electrical.getAttributes(),
				isPrimary
		);
		
		Node subNode = null;
		for(int i = 0; i < electrical.getChildNodes().getLength(); i++){
			Node node = electrical.getChildNodes().item(i);
			
			if(		node.getNodeName().equalsIgnoreCase("Scanner") ||
					node.getNodeName().equalsIgnoreCase("Cloak") ||
					node.getNodeName().equalsIgnoreCase("Overcharger") ||
					node.getNodeName().equalsIgnoreCase("Computer") ||
					node.getNodeName().equalsIgnoreCase("CounterMeasures")
			){
				subNode = node;
				
				if(subtype == null){
					if(node.getNodeName().equalsIgnoreCase("Scanner")){
						subtype = "Scanners";
					}else if(node.getNodeName().equalsIgnoreCase("Cloak")){
						subtype = "Cloak";
					}else if(node.getNodeName().equalsIgnoreCase("Overcharger")){
						subtype = "Overchargers";
					}else if(node.getNodeName().equalsIgnoreCase("Computer")){
						subtype = "Computers";
					}else if(node.getNodeName().equalsIgnoreCase("CounterMeasures")){
						subtype = "Countermeasures";
					}else{
						subtype = node.getNodeName();
					}
				}
			}
		}
		
		if(subNode != null){
			for(ComponentType.SubType stype : ComponentType.ELECTRICAL.getSubTypes()){
				if(stype.getName().equalsIgnoreCase(subtype)){
					addDefaultStats(stype,component,subNode.getAttributes(),isPrimary);
				}
			}
		}	
	}
	
	private void parseMechanical(
			Node mechanical, 
			DefaultComponent<String> component, 
			String subtype, boolean isPrimary
	){
		addDefaultStats(
				ComponentType.MECHANICAL,
				component,
				mechanical.getAttributes(),
				isPrimary
		);
		
		Node subNode = null;
		for(int i = 0; i < mechanical.getChildNodes().getLength(); i++){
			Node node = mechanical.getChildNodes().item(i);
			
			if(		node.getNodeName().equalsIgnoreCase("Thruster") ||
					node.getNodeName().equalsIgnoreCase("Turret") ||
					node.getNodeName().equalsIgnoreCase("RangeExtender") ||
					node.getNodeName().equalsIgnoreCase("Overdrive") ||
					node.getNodeName().equalsIgnoreCase("Terraformer") ||
					node.getNodeName().equalsIgnoreCase("Miner")
			){
				subNode = node;
				
				if(subtype == null){
					if(node.getNodeName().equalsIgnoreCase("Thruster")){
						subtype = "Thrusters";
					}else if(node.getNodeName().equalsIgnoreCase("Turret")){
						subtype = "Turrets";
					}else if(node.getNodeName().equalsIgnoreCase("RangeExtender")){
						subtype = "Range Extenders";
					}else if(node.getNodeName().equalsIgnoreCase("Overdrive")){
						subtype = "Overdrivers";
					}else if(node.getNodeName().equalsIgnoreCase("Terraformer")){
						subtype = "Terraforming";
					}else if(node.getNodeName().equalsIgnoreCase("Miner")){
						subtype = "Mining Beams";
					}else{
						subtype = node.getNodeName();
					}
				}
			}
		}
		
		NamedNodeMap attrs = subNode.getAttributes();
		if(subNode != null){
			for(ComponentType.SubType stype : ComponentType.MECHANICAL.getSubTypes()){
				if(stype.getName().equalsIgnoreCase(subtype)){
					addDefaultStats(stype,component,attrs,isPrimary);
					
					if(stype == ComponentType.MechanicalType.MINING){
						for(Resource res : Resource.RESOURCES){
							if(res.isNatural()){
								String name = "mining(" + res.shortName() + ")";
								String value = attrs.getNamedItem(
										res.shortName()).getNodeValue();
								
								if(isPrimary){
									component.value(name, new Integer(value));
								}else{
									component.value(
											"Secondary" + 
											component.getSecondaryTypes().size() + "." +
											name, new Integer(value)
									);
								}
							}
						}
					}else if(stype == ComponentType.MechanicalType.TERRAFORM){
						for(Environment env : Environment.ATTRIBUTES){
							String name = "terraform(" + env.getName() + ")";
							String value = attrs.getNamedItem(
									env.getName()).getNodeValue();
							
							if(isPrimary){
								component.value(name, new Integer(value));
							}else{
								component.value(
										"Secondary" + 
										component.getSecondaryTypes().size() + "." +
										name, new Integer(value)
								);
							}
						}
					}
				}
			}
		}	
	}
	
	private void parseOrbital(
			Node orbital, 
			DefaultComponent<String> component, 
			String subtype, 
			boolean isPrimary
	){
		addDefaultStats(
				ComponentType.ORBITAL,
				component,
				orbital.getAttributes(),
				isPrimary
		);
		
		Node subNode = null;
		for(int i = 0; i < orbital.getChildNodes().getLength(); i++){
			Node node = orbital.getChildNodes().item(i);
			
			if(		node.getNodeName().equalsIgnoreCase("HSTerminal") ||
					node.getNodeName().equalsIgnoreCase("Stargate") ||
					node.getNodeName().equalsIgnoreCase("MassAccelerator") ||
					node.getNodeName().equalsIgnoreCase("ConstructionDock")
			){
				subNode = node;
				
				if(subtype == null){
					if(node.getNodeName().equalsIgnoreCase("HSTerminal")){
						subtype = "Hyperspace Nodes";
					}else if(node.getNodeName().equalsIgnoreCase("Stargate")){
						subtype = "Stargates";
					}else if(node.getNodeName().equalsIgnoreCase("MassAccelerator")){
						subtype = "Mass Accelerator";
					}else if(node.getNodeName().equalsIgnoreCase("ConstructionDock")){
						subtype = "Ship Yards";
					}else{
						subtype = node.getNodeName();
					}
				}
			}
		}
		
		if(subNode != null){
			for(ComponentType.SubType stype : ComponentType.ORBITAL.getSubTypes()){
				if(stype.getName().equalsIgnoreCase(subtype)){
					addDefaultStats(stype,component,subNode.getAttributes(),isPrimary);
				}
			}
		}
	}
	
	private void parseMisc(Node misc, DefaultComponent<String> component, boolean isPrimary){
		addDefaultStats(
				ComponentType.MISC,
				component,
				misc.getAttributes(),
				isPrimary
		);
	}
	/*
	private void addStat(
			String name,
			String nodeName,
			DefaultComponent<String> component, 
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
	*/
	private void addDefaultStats(
			IComponentType type, 
			DefaultComponent<String> component, 
			NamedNodeMap map,
			boolean isPrimary
	){
		for(String stat : type.getAssociatedStats()){
			Node node = map.getNamedItem(stat);
			
			Integer v;
			if(node == null || node.getNodeValue().isEmpty()){
				v = new Integer(0);
			}else{
				v = new Integer(node.getNodeValue());
			}
			
			LinearStat lstat = new LinearStat(
					v.intValue(),
					Integer.MAX_VALUE,
					0,
					v.intValue()
			);
			
			String name = null;
			if(isPrimary){
				name = stat;
			}else{
				name = "Secondary" + component.getSecondaryTypes().size() + "." + stat;
			}
			
			if(name != null){
				component.getStats().setStat(name, lstat);
			}
		}
	}
	
	private void setTypedStat(
			DefaultComponent<String> component,
			String supertype,
			String subtype,
			String value,
			boolean isPrimary
	){
		if(isPrimary){
			component.value(supertype + "(" + subtype + ")", new Integer(value));
		}else{
			component.value(
					"Secondary" + component.getSecondaryTypes().size() + "." +
					supertype + "(" + subtype + ")", new Integer(value)
			);
		}
	}
	
	public class DirectedCompContext extends DefaultCompContext {
		private final static String x = "tile-X";
		private final static String y = "tile-Y";
		//TODO: Make the images via compositing as needed.
		@Override
		public Point getTile(IEntity<String> arg0) {
			if(arg0.value(x) != null && arg0.value(y) != null){
				return new Point(
						arg0.value(x).intValue(),
						arg0.value(y).intValue()
				);
			}else{
				return super.getTile(arg0);
			}
		}
		
		public void addDirectedStats(DefaultComponent<String> d, int max){
			LinearStat stat = new LinearStat(0,max,0,0);
			
			d.getStats().setStat(x, stat);
		}
	}
	
	public class DefaultCompContext implements IDisplayContext<String,String> {

		private final static String xpos = "xpos";
		private final static String ypos = "ypos";
		private final static String rot = "rot";
		protected Point tile;
		
		protected AffineTransformOp op;
		protected AffineTransform rotation;
		
		@SuppressWarnings("unchecked")
		@Override
		public String getBoard(IEntity<String> arg0) {
			if(arg0 instanceof IComponent){
				return ((IComponent<String>)arg0).getName();
			}else{
				return null;
			}
		}

		@Override
		public ImageOpPalette<?> getImageOpPalette() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Point getPosition(IEntity<String> arg0) {
			if(arg0.value(xpos) != null && arg0.value(ypos) != null){
				return new Point(arg0.value(xpos).intValue(),arg0.value(ypos).intValue());
			}else{
				return null;
			}
		}

		@Override
		public Point getTile(IEntity<String> arg0) {
			if(tile == null){
				tile = new Point(0,0);
			}
			
			return tile;
		}

		@Override
		public BufferedImageOp getTransform(IEntity<String> arg0) {
			if(arg0.value(rot) != null){
				if(op == null){
					if(rotation == null){
						rotation = AffineTransform.getRotateInstance(
								arg0.value(rot).doubleValue()
						);
					}else{
						rotation.setToRotation(arg0.value(rot).doubleValue());
					}
					
					op = new AffineTransformOp(rotation,AffineTransformOp.TYPE_BILINEAR);
				}else{
					op.getTransform().setToRotation(arg0.value(rot).doubleValue());
				}
				
				return op;
			}else{
				return null;
			}
		}

		@Override
		public void setImageOpPalette(ImageOpPalette<String> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
