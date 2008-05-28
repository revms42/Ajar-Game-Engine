package test.experimental;

import java.util.HashMap;
import java.util.Map;

import org.display.ImageBoard;
import org.w3c.dom.Node;

public class SVGAnimationFactory {
	
	public enum SvgElement {
		DESC("svg:desc"),
		TITLE("svg:title"),
		METADATA("svg:metadata"),
		DEFS("svg:defs"),
		PATH("svg:path"),
		TEXT("svg:text"),
		RECT("svg:rect","x|Number","y|Number","width|Number","heigth|Number","rx|Number","ry|Number"),
		CIRCLE("svg:circle","cx|Number","cy|Number","r|Number"),
		ELLIPSE("svg:ellipse","cx|Number","cy|Number","rx|Number","ry|Number"),
		LINE("svg:line","x1|Number","y1|Number","x2|Number","y2|Number"),
		POLYLINE("svg:polyline"),
		POLYGON("svg:polygon"),
		USE("svg:use"),
		IMAGE("svg:image"),
		SVG("svg:svg"),
		G("svg:g"),
		VIEW("svg:view"),
		SWITCH("svg:switch"),
		A("svg:a"),
		ALT_GLYPH_DEF("svg:altGlyphDef"),
		SCRIPT("svg:script"),
		STYLE("svg:style"),
		SYMBOL("svg:symbol"),
		MARKER("svg:marker"),
		CLIP_PATH("svg:clipPath"),
		MASK("svg:mask"),
		LINEAR_GRADIENT("svg:linearGradient"),
		RADIAL_GRADIENT("svg:radialGradient"),
		PATTERN("svg:pattern"),
		FILTER("svg:filter"),
		CURSOR("svg:cursor"),
		FONT("svg:font"),
		ANIMATE("svg:animate"),
		SET("svg:set"),
		ANIMATE_MOTION("svg:animateMotion"),
		ANIMATE_COLOR("svg:animateColor"),
		ANIMATE_TRANSFORM("svg:animateTransform"),
		COLOR_PROFILE("svg:color-profile"),
		FONT_FACE("svg:font-face");
  
		public final String type;
		public final HashMap<String,String> variables;
		
		SvgElement(String type, String... variables){
			this.type = type;
			this.variables = new HashMap<String,String>();
			
			for(String var : variables){
				String[] split = var.split("|");
				
				if(split.length == 2){
					this.variables.put(split[0], split[1]);
				}
			}
		}
 
	}

	public static Map.Entry<String,ImageBoard> parseImageBoard(Node root){
		return null;
	}
}
