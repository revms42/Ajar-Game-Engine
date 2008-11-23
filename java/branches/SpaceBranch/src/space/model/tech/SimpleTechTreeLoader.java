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
import java.util.HashMap;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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

import space.display.DisplayStats;
import space.model.Resource;
import space.model.component.ComponentType;
import space.model.component.DefaultComponent;
import space.model.component.IComponent;
import space.model.tech.XMLTechTreeLoader.DefaultCompContext;
import space.model.tech.XMLTechTreeLoader.DirectedCompContext;

public class SimpleTechTreeLoader implements ITechTreeLoader<File,String> {

	private Vector<IComponent<String>> components;
	
	private DirectedCompContext directedContext;
	private DefaultCompContext defaultContext;
	private ImagePalette<String> palette;
	private DisplayFactory<String,String> factory;
	
	private final int[] d1x = {24,2,2,24};
	private final int[] d1y = {24,11,35,46};
	
	private final int[] d2x = {24,2,24,46};
	private final int[] d2y = {24,11,2,11};
	
	private final int[] d3x = {24,46,46,24};
	private final int[] d3y = {24,11,35,46};
	
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
		
		parseSections(root);
		
		return components;
	}
	
	private void parseSections(Node root){
		Vector<Node> list = filterList(root.getChildNodes());
		
		for(Node node : list){
			if(parseComponentType(node.getNodeName()) == null){
				parseSections(node);
			}else{
				parseComponent(node);
			}
		}
	}
	
	private void parseComponent(Node node){
		String name = getAttribute(node,"name");
		String description = node.getTextContent().trim().split("\n")[0];
		
		DefaultComponent<String> component = new DefaultComponent<String>(name,description);
		
		addStat("mass",getAttribute(node,"mass"),component);
		addStat("signature",getAttribute(node,"sig"),component);
		addStat("hitpoints",getAttribute(node,"hitpoints"),component);
		
		parseComponentImage(getAttribute(node,"image"),component);
		
		ComponentType type = parseComponentType(node.getNodeName());
		component.setType(type);
		
		switch(type){
		case BAY:
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
		case CONDUIT:
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
		
		parseParts(node,component,false);
		
		components.add(component);
	}
	
	private void parseParts(Node root, IComponent<String> comp, boolean secondary){
		Vector<Node> list = filterList(root.getChildNodes());
		
		for(Node node : list){
			if(node.getNodeName().equalsIgnoreCase("TechReqs")){
				parseStats(node,"",comp);
			}else if(node.getNodeName().equalsIgnoreCase("Cost")){
				parseStats(node,"cost",comp);
			}else if(node.getNodeName().equalsIgnoreCase("Stats")){
				if(secondary){
					parseStats(node,"Secondary" + comp.getSecondaryTypes().size() + ":",comp);
				}else{
					parseStats(node,"",comp);
				}
			}else if(node.getNodeName().equalsIgnoreCase("Secondary")){
				ComponentType type = parseComponentType(getAttribute(node,"type"));
				comp.getSecondaryTypes().add(type);
				
				parseParts(node,comp,true);
			}
		}
	}
	
	private void parseStats(Node root, String pre, IComponent<String> comp){
		Vector<Node> list = filterList(root.getChildNodes());
		
		for(Node node : list){
			if(node.hasAttributes()){
				String type = getAttribute(node,"type");
				
				addStat(pre + node.getNodeName() + "(" + type + ")", node.getTextContent().trim().split("\n")[0], comp);
			}else{
				addStat(pre + node.getNodeName(), node.getTextContent().trim().split("\n")[0], comp);
			}
		}
	}
	
	private Vector<Node> filterList(NodeList list){
		Vector<Node> newList = new Vector<Node>();
		
		for(int i = 0; i < list.getLength(); i++){
			Node node = list.item(i);
			
			switch(node.getNodeType()){
			case Node.ELEMENT_NODE:
				newList.add(node);
				break;
			default:
				break;
			}
		}
		
		return newList;
	}
	
	private String getAttribute(Node root, String name){
		NamedNodeMap map = root.getAttributes();
		
		return map.getNamedItem(name).getNodeValue();
	}

	private ComponentType parseComponentType(String name){
		for(ComponentType type : ComponentType.COMPONENTTYPES){
			if(type.getName().equalsIgnoreCase(name)){
				return type;
			}
		}
		
		return null;
	}
	
	private BufferedImage loadImage(String uri){
		if(uri != null && uri.length() != 0){
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
		}else{
			return null;
		}
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
	}
	
	private void addStat(String name, String value, IComponent<String> comp){
		if(value != null){
			Integer v = new Integer(value);
			
			LinearStat lstat = new LinearStat(
					v.intValue(),
					Integer.MAX_VALUE,
					0,
					v.intValue()
			);
			
			comp.setStat(name, lstat);
		}else{
			System.out.println(name);
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
	
	public class DefaultCompContext implements IDisplayContext<String,String>,DisplayStats {
		
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
			if(arg0.value(STAT_X_POS) != null && arg0.value(STAT_Y_POS) != null){
				return new Point(
						arg0.value(STAT_X_POS).intValue(),
						arg0.value(STAT_Y_POS).intValue()
				);
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
			if(arg0.value(STAT_ROTATION) != null){
				if(op == null){
					if(rotation == null){
						rotation = AffineTransform.getRotateInstance(
								arg0.value(STAT_ROTATION).doubleValue()
						);
					}else{
						rotation.setToRotation(arg0.value(STAT_ROTATION).doubleValue());
					}
					
					op = new AffineTransformOp(rotation,AffineTransformOp.TYPE_BILINEAR);
				}else{
					op.getTransform().setToRotation(arg0.value(STAT_ROTATION).doubleValue());
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
