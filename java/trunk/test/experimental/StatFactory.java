package test.experimental;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Pattern;

import org.model.LinearStat;
import org.model.Stats;

public class StatFactory {
	public static String TEST_EXPERIMENTAL_STATFACTORY_TYPE = "type";
	public static String TEST_EXPERIMENTAL_STATFACTORY_NAME = "name";
	public static String TEST_EXPERIMENTAL_STATFACTORY_DISPLAY = "displayValue";
	public static String TEST_EXPERIMENTAL_STATFACTORY_STATS = "Stats";
	public static String TEST_EXPERIMENTAL_STATFACTORY_STAT = "Stat";
	
	public enum Numbers {
		BYTE("Byte"),
		SHORT("Short"),
		INTEGER("Integer"),
		LONG("Long"),
		FLOAT("Float"),
		DOUBLE("Double");
		
		public final String type;
		
		Numbers(String type){
			this.type = type;
		}
		
		public static Numbers parse(String type){
			if(type.contains(BYTE.type)){
				return BYTE;
			}else if(type.contains(SHORT.type)){
				return SHORT;
			}else if(type.contains(INTEGER.type)){
				return INTEGER;
			}else if(type.contains(LONG.type)){
				return LONG;
			}else if(type.contains(FLOAT.type)){
				return FLOAT;
			}else if(type.contains(DOUBLE.type)){
				return DOUBLE;
			}else{
				return null;
			}
		}
	}
	
	public enum StatPart {
		ORG_MODEL_ISTAT_VALUE("value"),
		ORG_MODEL_ISTAT_MAX("max"),
		ORG_MODEL_ISTAT_MIN("min"),
		ORG_MODEL_ISTAT_NOMINAL("nominal");
		
		public final String type;
		
		StatPart(String type){
			this.type = type;
		}
		
		public static StatPart parse(String type){
			if(type.equalsIgnoreCase(ORG_MODEL_ISTAT_VALUE.type)){
				return ORG_MODEL_ISTAT_VALUE;
			}else if(type.equalsIgnoreCase(ORG_MODEL_ISTAT_MAX.type)){
				return ORG_MODEL_ISTAT_MAX;
			}else if(type.equalsIgnoreCase(ORG_MODEL_ISTAT_MIN.type)){
				return ORG_MODEL_ISTAT_MIN;
			}else if(type.equalsIgnoreCase(ORG_MODEL_ISTAT_NOMINAL.type)){
				return ORG_MODEL_ISTAT_NOMINAL;
			}else{
				return null;
			}
		}
	}
	
	private static String readToEnd(BufferedReader br) throws IOException{
		String msg = new String();
		String line = new String();
		do{
			line = br.readLine();
			msg = msg + line;
		}while(line != null);

		br.close();
		
		return msg;
	}

	public static synchronized Stats<String> deserializeStats(File file){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String contents = readToEnd(br);
			
			if(contents.contains("<" + TEST_EXPERIMENTAL_STATFACTORY_STATS) &&
			   contents.contains("</" + TEST_EXPERIMENTAL_STATFACTORY_STATS) )
			{
				Stats<String> stats = new Stats<String>();
				
				int begin = contents.indexOf("<" + TEST_EXPERIMENTAL_STATFACTORY_STATS);
				int end = contents.indexOf("</" + TEST_EXPERIMENTAL_STATFACTORY_STATS);
				
				String sub = contents.substring(begin, end);
				
				String[] lines = sub.split("<" + TEST_EXPERIMENTAL_STATFACTORY_STAT);
				
				if(lines.length != 0){
					for(String line : lines){
						String[] attrs = line.split(Pattern.compile("\\s").pattern());
						
						String name = null;
						for(String attr : attrs){
							String[] pair = attr.split("=");
							
							if(pair[0].equalsIgnoreCase(TEST_EXPERIMENTAL_STATFACTORY_NAME)){
								if(pair[1].startsWith("\"") && pair[1].endsWith("\"")){
									pair[1] = pair[1].substring(1, pair[1].length()-1);
								}else if(pair[1].startsWith("\"") && pair[1].endsWith("\"/>")){
									pair[1] = pair[1].substring(1, pair[1].length()-3);
								}
								
								name = pair[1];
								break;
							}
						}
						
						if(name != null){
							LinearStat stat = parseStat(line);
							
							if(stat != null){
								stats.put(name, stat);
							}
						}
					}
				}
				
				return stats;
			}else{
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static LinearStat parseStat(String desc){
		String[] lines = desc.split(Pattern.compile("\\s").pattern());

		Numbers valtype = null;
		Vector<String> values = new Vector<String>();
		
		for(String line : lines){
			String[] ends = line.split("=");
			
			StatPart type = StatPart.parse(ends[0]);
			
			if(type != null){
				values.add(line);
			}else if(ends[0].equalsIgnoreCase(TEST_EXPERIMENTAL_STATFACTORY_TYPE)){
				valtype = Numbers.parse(ends[1]);
			}else if(ends[0].equalsIgnoreCase(TEST_EXPERIMENTAL_STATFACTORY_DISPLAY)){
				//TODO: Not implementing display attributes yet.
			}
		}
		
		Number val = null;
		Number max = null;
		Number min = null;
		Number nominal = null;
		
		for(String value : values){
			String[] ends = value.split("=");
			
			if(ends.length > 1){
				StatPart type = StatPart.parse(ends[0]);
				
				switch(type){
				case ORG_MODEL_ISTAT_VALUE:
					val = decode(valtype,ends[1]);
					break;
				case ORG_MODEL_ISTAT_MAX:
					max = decode(valtype,ends[1]);
					break;
				case ORG_MODEL_ISTAT_MIN:
					min = decode(valtype,ends[1]);
					break;
				case ORG_MODEL_ISTAT_NOMINAL:
					nominal = decode(valtype,ends[1]);
					break;
				default:
					break;
				}
			}
		}

		/* TODO: Obsolete return new Stat(val,max,min,nominal);*/
		return null;
	}
	
	private static Number decode(Numbers type, String value){
		if(value.startsWith("\"") && value.endsWith("\"")){
			value = value.substring(1, value.length()-1);
		}else if(value.startsWith("\"") && value.endsWith("\"/>")){
			value = value.substring(1, value.length()-3);
		}
		
		Number val = null;
		
		if(type != null){
			switch(type){
			case BYTE:
				val = Byte.decode(value);
				break;
			case SHORT:
				val = Short.decode(value);
				break;
			case INTEGER:
				val = Integer.decode(value);
				break;
			case LONG:
				val = Long.decode(value);
				break;
			case FLOAT:
				val = Float.valueOf(value);
				break;
			case DOUBLE:
				val = Double.valueOf(value);
				break;
			default:
				return null;
			}
		}
		return val;
	}
}
