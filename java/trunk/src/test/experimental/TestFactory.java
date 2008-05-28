package test.experimental;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.interaction.ActionPalette;
import org.interaction.IAction;
import org.model.IStat;
import org.model.LinearStat;
import org.model.Stats;
import org.model.number.Number;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestFactory {
	private static final String schema = 
		"C:\\Documents and Settings\\mstockbr\\workspace\\MDJ\\test\\data\\MDJ.xsd";
	
	public static Node loadRoot(File xml) throws Exception {
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		dbf.setIgnoringComments(true);
		dbf.setIgnoringElementContentWhitespace(true);
		
		Schema sch = sf.newSchema(new File(schema));
		dbf.setSchema(sch);
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document d = db.parse(xml);
		
		Node root = d.getFirstChild();
		
		if(root.getNodeName().equalsIgnoreCase("mdj:GameDescription")){
			return root;
		}else{
			throw new Exception("Root node is not a GameDescription");
		}
	}
	
	public static String parseEndCondition(Node root) throws Exception{
		if(root.getNodeName().equalsIgnoreCase("mdj:GameDescription")){
			NamedNodeMap nnm = root.getAttributes();
			
			return nnm.getNamedItem("endCondition").getNodeValue();
		}else{
			throw new Exception("Root node is not a GameDescription");
		}
	}
	
	public static String parseOnTurnCondition(Node root) throws Exception{
		if(root.getNodeName().equalsIgnoreCase("mdj:GameDescription")){
			NamedNodeMap nnm = root.getAttributes();
			
			return nnm.getNamedItem("onTurnDescription").getNodeValue();
		}else{
			throw new Exception("Root node is not a GameDescription");
		}
	}
	
	public static HashMap<String,Stats<String>> parseDefaultStats(Node root) 
			throws Exception
	{
		if(root.getNodeName().equalsIgnoreCase("mdj:GameDescription")){
			NodeList list = root.getChildNodes();
			
			HashMap<String,Stats<String>> defaultStats = new HashMap<String,Stats<String>>();
			
			for(int i = 0; i < list.getLength(); i++){
				Node node = list.item(i);
				
				if(node.getNodeName().equalsIgnoreCase("mdj:DefaultStats")){
					Map.Entry<String,Stats<String>> stats = parseDS(node);
					defaultStats.put(stats.getKey(), stats.getValue());
				}
			}
			
			return defaultStats;
		}else if(root.getNodeName().equalsIgnoreCase("mdj:DefaultStats")){
			HashMap<String,Stats<String>> defaultStats = new HashMap<String,Stats<String>>();
			
			Map.Entry<String,Stats<String>> stats = parseDS(root);
			defaultStats.put(stats.getKey(), stats.getValue());
			
			return defaultStats;
		}else{
			throw new Exception("Root node is not a GameDescription");
		}
	}
	
	private static Map.Entry<String,Stats<String>> parseDS(Node node){
		if(node.getNodeName().equalsIgnoreCase("mdj:DefaultStats")){
			NamedNodeMap nnm = node.getAttributes();
			
			final String name = nnm.getNamedItem("name").getNodeValue();
			
			NodeList statslist = node.getChildNodes();
			for(int i = 0; i < statslist.getLength(); i++){
				Node statsnode = statslist.item(i);
				
				if(statsnode.getNodeName().equalsIgnoreCase("mdj:Stats")){
					NodeList statlist = statsnode.getChildNodes();
					
					Vector<String> keys = new Vector<String>();
					Vector<IStat> values = new Vector<IStat>();
					
					for(int j = 0; j < statlist.getLength(); j++){
						Node statnode = statlist.item(j);
						
						if(statnode.getNodeName().equalsIgnoreCase("mdj:Stat")){
							Map.Entry<String,LinearStat> stat = parseStat(statnode);
							
							keys.add(stat.getKey());
							values.add(stat.getValue());
						}
					}
					
					final Stats<String> stats = new Stats<String>(keys,values);
					
					return new Map.Entry<String, Stats<String>>(){
						private Stats<String> pstats = stats;
						
						@Override
						public String getKey() {
							return name;
						}

						@Override
						public Stats<String> getValue() {
							return pstats;
						}

						@Override
						public Stats<String> setValue(Stats<String> value) {
							this.pstats = value;
							return pstats;
						}
					};
				}
			}
		}else{
			return null;
		}
		return null;
	}
	
	public static Map.Entry<String,LinearStat> parseStat(Node node){
		if(node.getNodeName().equalsIgnoreCase("mdj:Stat")){
			NamedNodeMap nnm = node.getAttributes();
			
			final String name = nnm.getNamedItem("name").getNodeValue();
			String max = nnm.getNamedItem("max").getNodeValue();
			String min = nnm.getNamedItem("min").getNodeValue();
			String value = nnm.getNamedItem("value").getNodeValue();
			String nominal = nnm.getNamedItem("nominal").getNodeValue();
			String type = nnm.getNamedItem("type").getNodeValue();
			
			Number nval = parseNumber(type,value);
			Number nmax = parseNumber(type,max);
			Number nmin = parseNumber(type,min);
			Number nnom = parseNumber(type,nominal);
			
			final LinearStat stat = new LinearStat(nval,nmax,nmin,nnom);
			
			return new Map.Entry<String, LinearStat>(){
				private LinearStat pstat = stat;
				
				@Override
				public String getKey() {
					return name;
				}

				@Override
				public LinearStat getValue() {
					return pstat;
				}

				@Override
				public LinearStat setValue(LinearStat value) {
					this.pstat = value;
					return pstat;
				}
				
			};
		}else{
			return null;
		}
	}
	
	protected static Number parseNumber(String type, String number){
		if(type.equalsIgnoreCase("double")){
			return Number.parse(Double.parseDouble(number));
		}else if(type.equalsIgnoreCase("float")){
			return Number.parse(Float.parseFloat(number));
		}else if(type.equalsIgnoreCase("long")){
			return Number.parse(Long.parseLong(number));
		}else if(type.equalsIgnoreCase("int")){
			return Number.parse(Integer.parseInt(number));
		}else if(type.equalsIgnoreCase("short")){
			return Number.parse(Short.parseShort(number));
		}else if(type.equalsIgnoreCase("byte")){
			return Number.parse(Byte.parseByte(number));
		}else{
			return null;
		}
	}
	
	public static Map.Entry<String,ActionPalette<String,String>> parseActionPalette(Node root){
		if(root.getNodeName().equalsIgnoreCase("mdj:GameDescription")){
			NodeList list = root.getChildNodes();
			
			for(int i = 0; i < list.getLength(); i++){
				Node tops = list.item(i);
				
				if(tops.getNodeName().equalsIgnoreCase("mdj:ActionPalette")){
					NamedNodeMap nnm = tops.getAttributes();
					
					final String name = nnm.getNamedItem("name").getNodeValue();
					
					NodeList actnodes = tops.getChildNodes();
					
					HashMap<String,IAction<String>> actions = 
						new HashMap<String,IAction<String>>();
					
					for(int j = 0; j < actnodes.getLength(); j++){
						Node node = actnodes.item(j);
						
						if(node.getNodeName().equalsIgnoreCase("mdj:Action")){
							Map.Entry<String,IAction<String>> stats = parseAction(node);
							actions.put(stats.getKey(), stats.getValue());
						}
					}
					
					final ActionPalette<String,String> palette = new ActionPalette<String,String>(
							actions.keySet(),actions.values()
					);
					
					return new Map.Entry<String, ActionPalette<String,String>>(){
						private ActionPalette<String,String> npalette = palette;
						
						@Override
						public String getKey() {
							return name;
						}
		
						@Override
						public ActionPalette<String, String> getValue() {
							return npalette;
						}
		
						@Override
						public ActionPalette<String, String> setValue(
								ActionPalette<String, String> value) 
						{
							this.npalette = value;
							return npalette;
						}
					};
				}
			}
			
			return null;
		}else{
			return null;
		}
	}
	
	public static Map.Entry<String,IAction<String>> parseAction(Node node){
		if(node.getNodeName().equalsIgnoreCase("mdj:Action")){
			NamedNodeMap nnm = node.getAttributes();
			
			final String name = nnm.getNamedItem("name").getNodeValue();
			final String type = nnm.getNamedItem("type").getNodeValue();
			
			NodeList children = node.getChildNodes();
			
			IActionTarget target = null;
			IActionArg firstarg = null;
			IActionArg secondarg = null;
			
			for(int i = 0; i < children.getLength(); i++){
				if(children.item(i).getNodeName().equalsIgnoreCase("mdj:TargetStat")){
					target = parseActionTarget(children.item(i));
				}else if(children.item(i).getNodeName().equalsIgnoreCase("mdj:FirstArgument")){
					firstarg = parseActionArgument(children.item(i));
				}else if(children.item(i).getNodeName().equalsIgnoreCase("mdj:SecondArgument")){
					secondarg = parseActionArgument(children.item(i));
				}
			}
			
			final SimpleAction action = new SimpleAction(type,target,firstarg,secondarg,null);
			
			return new Map.Entry<String, IAction<String>>(){
				IAction<String> inneraction = action;
				
				@Override
				public String getKey() {
					return name;
				}

				@Override
				public IAction<String> getValue() {
					return inneraction;
				}

				@Override
				public IAction<String> setValue(IAction<String> value) {
					return inneraction;
				}
			};
		}else{
			return null;
		}
	}
	
	
	private static IActionTarget parseActionTarget(Node node){
		if(node.getNodeName().equalsIgnoreCase("mdj:TargetStat")){
			NodeList list = node.getChildNodes();
			
			for(int i = 0; i < list.getLength(); i++){
				Node sub = list.item(i);
				
				if(sub.getNodeName().equalsIgnoreCase("mdj:Subject")){
					String[] ps = parseTarget(sub);
					
					return new EntityActionTarget<String>(ps[1],-1,ps[0]);
				}else if(sub.getNodeName().equalsIgnoreCase("mdj:Object")){
					String[] ps = parseTarget(sub);
					
					return new EntityActionTarget<String>(ps[1],0,ps[0]);
				}
			}
			
			return null;
		}else{
			return null;
		}
	}
	
	private static IActionArg parseActionArgument(Node node){
		if(node.getNodeName().equalsIgnoreCase("mdj:FirstArgument") || 
		   node.getNodeName().equalsIgnoreCase("mdj:SecondArgument")
		){
			NodeList list = node.getChildNodes();
			
			for(int i = 0; i < list.getLength(); i++){
				Node sub = list.item(i);
				
				if(sub.getNodeName().equalsIgnoreCase("mdj:Subject")){
					String[] ps = parseTarget(sub);
					
					return new EntityActionArg<String>(ps[1],-1,ps[0]);
				}else if(sub.getNodeName().equalsIgnoreCase("mdj:Object")){
					String[] ps = parseTarget(sub);
					
					return new EntityActionArg<String>(ps[1],0,ps[0]);
				}else if(sub.getNodeName().equalsIgnoreCase("mdj:Number")){
					String number = sub.getTextContent();
					
					try {
						final java.lang.Number n = NumberFormat.getNumberInstance().parse(number);
						
						return new IActionArg(){

							@Override
							public java.lang.Number get() {
								return n;
							}
						};
						
					} catch (ParseException e) {
						e.printStackTrace();
						return null;
					}
				}
			}
			
			return null;
		}else{
			return null;
		}
	}
	
	private static String[] parseTarget(Node node){
		if(node.getNodeName().equalsIgnoreCase("mdj:TargetStat") ||
		   node.getNodeName().equalsIgnoreCase("mdj:Subject") ||
		   node.getNodeName().equalsIgnoreCase("mdj:Object")
		){
			NamedNodeMap nnm = node.getAttributes();
			
			String prop = nnm.getNamedItem("property").getNodeValue();
			String stat = nnm.getNamedItem("stat").getNodeValue();
			
			return new String[] {prop,stat};
		}else{
			return null;
		}
	}
}
