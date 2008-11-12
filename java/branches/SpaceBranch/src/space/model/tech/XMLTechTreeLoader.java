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
		factory.setSchema(schema);
		
		builder = factory.newDocumentBuilder();
	}
	
	private boolean check(Node node, String type){
		return	node.isDefaultNamespace(namespaceURI) && 
				node.getNodeName().equalsIgnoreCase(type);
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
		}
		
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
			
			NodeList nodes = entry.getChildNodes();
			for(int i = 0; i < nodes.getLength(); i++){
				if(nodes.item(i).getNodeName().equalsIgnoreCase("Primary")){
					parseComponentType(nodes.item(i),component,true,typeName);
					break;
				}
			}
		}else if(hashCode != null){
			//TODO: We don't support this yet.
		}else{
			NodeList nodes = entry.getChildNodes();
			for(int i = 0; i < nodes.getLength(); i++){
				if(nodes.item(i).getNodeName().equalsIgnoreCase("Primary")){
					Node typeNode = nodes.item(i).getChildNodes().item(0);
					
					parseComponentType(nodes.item(i),component,true,typeNode.getNodeName());
					break;
				}
			}
		}
		
		NodeList nodes = entry.getChildNodes();
		for(int i = 0; i < nodes.getLength(); i++){
			if(nodes.item(i).getNodeName().equalsIgnoreCase("Secondary")){
				Node typeNode = nodes.item(i).getChildNodes().item(0);
				
				parseComponentType(nodes.item(i),component,false,typeNode.getNodeName());
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
		
		switch((ComponentType)component.getType()){
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
		}
	}

	private void parseComponentType(
			Node node, 
			DefaultComponent<String> component,
			boolean isPrimary,
			String... id
	){
		for(ComponentType type : ComponentType.COMPONENTTYPES){
			if(type.getName().equalsIgnoreCase(id[0])){
				if(isPrimary){
					component.setType(type);
				}
				
				switch(type){
				case ARMOR:
					parseArmor(node,component);
					break;
				case BAY:
					if(id.length > 1){
						parseBay(node,component,id[1]);
					}else{
						parseBay(node,component,null);
					}
					break;
				case BULKHEAD:
					parseBulkhead(node,component);
					break;
				case CONDUIT:
					parseConduit(node,component);
					break;
				case ELECTRICAL:
					if(id.length > 1){
						parseElectrical(node,component,id[1]);
					}else{
						parseElectrical(node,component,null);
					}
					break;
				case ENGINE:
					if(id.length > 1){
						parseEngine(node,component,id[1]);
					}else{
						parseEngine(node,component,null);
					}
					break;
				case MECHANICAL:
					if(id.length > 1){
						parseMechanical(node,component,id[1]);
					}else{
						parseMechanical(node,component,null);
					}
					break;
				case MISC:
					parseMisc(node,component);
					break;
				case ORBITAL:
					if(id.length > 1){
						parseOrbital(node,component,id[1]);
					}else{
						parseOrbital(node,component,null);
					}
					break;
				case SHIELD:
					parseShield(node,component);
					break;
				case WEAPON:
					if(id.length > 1){
						parseWeapon(node,component,id[1]);
					}else{
						parseWeapon(node,component,null);
					}
					break;
				default:
					break;
				}
			}
		}
	}
	
	private void parseWeapon(Node weapon, DefaultComponent<String> component, String subtype){
		
	}
	
	private void parseArmor(Node armor, DefaultComponent<String> component){
		
	}
	
	private void parseShield(Node shield, DefaultComponent<String> component){
		
	}
	
	private void parseEngine(Node engine, DefaultComponent<String> component, String subtype){
		
	}
	
	private void parseBulkhead(Node bulkhead, DefaultComponent<String> component){
		
	}
	
	private void parseConduit(Node conduit, DefaultComponent<String> component){
		
	}
	
	private void parseBay(Node bay, DefaultComponent<String> component, String subtype){
		
	}
	
	private void parseElectrical(
			Node electrical, 
			DefaultComponent<String> component, 
			String subtype
	){
		
	}
	
	private void parseMechanical(
			Node mechanical, 
			DefaultComponent<String> component, 
			String subtype
	){
		
	}
	
	private void parseOrbital(Node orbital, DefaultComponent<String> component, String subtype){
		
	}
	
	private void parseMisc(Node misc, DefaultComponent<String> component){
		
	}
	
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
	
	public class DirectedCompContext extends DefaultCompContext {
		private final static String x = "tile-X";
		private final static String y = "tile-Y";
		
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
