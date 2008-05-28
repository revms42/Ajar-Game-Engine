package test.experimental;

public enum ActionCore {
	ADD("add"){
		public Number performOpperation(
				IActionArg first, 
				IActionArg second)
		{
			return first.get().doubleValue() + second.get().doubleValue();
		}
	},
	SUBTRACT("subtract"){
		public Number performOpperation(
				IActionArg first, 
				IActionArg second)
		{
			return first.get().doubleValue() - second.get().doubleValue();
		}
	},
	MULTIPLY("multiply"){
		public Number performOpperation(
				IActionArg first, 
				IActionArg second)
		{
			return first.get().doubleValue() * second.get().doubleValue();
		}
	},
	DIVIDE("divide"){
		public Number performOpperation(
				IActionArg first, 
				IActionArg second)
		{
			return first.get().doubleValue() / second.get().doubleValue();
		}
	},
	POWER("power"){
		public Number performOpperation(
				IActionArg first, 
				IActionArg second)
		{
			return Math.pow(first.get().doubleValue(), second.get().doubleValue());
		}
	},
	MODULO("modulo"){
		public Number performOpperation(
				IActionArg first, 
				IActionArg second)
		{
			return first.get().doubleValue() % second.get().doubleValue();
		}
	},
	EQUALS("equals"){
		public Number performOpperation(
				IActionArg first, 
				IActionArg second)
		{
			return first.get().doubleValue();
		}
	},
	AND("and"){
		public Number performOpperation(
				IActionArg first, 
				IActionArg second)
		{
			return first.get().longValue() & second.get().longValue();
		}
	},
	OR("or"){
		public Number performOpperation(
				IActionArg first, 
				IActionArg second)
		{
			return first.get().longValue() | second.get().longValue();
		}
	},
	XOR("xor"){
		public Number performOpperation(
				IActionArg first, 
				IActionArg second)
		{
			return first.get().longValue() ^ second.get().longValue();
		}
	},
	NOT("not"){
		public Number performOpperation(
				IActionArg first, 
				IActionArg second)
		{
			return ~first.get().longValue();
		}
	};
	
	public final String type;
	
	private ActionCore(String type){
		this.type = type;
	}
	
	public static ActionCore parse(String type){
		if(type.equalsIgnoreCase(ADD.type)){
			return ADD;
		}else if(type.equalsIgnoreCase(SUBTRACT.type)){
			return SUBTRACT;
		}else if(type.equalsIgnoreCase(MULTIPLY.type)){
			return MULTIPLY;
		}else if(type.equalsIgnoreCase(DIVIDE.type)){
			return DIVIDE;
		}else if(type.equalsIgnoreCase(POWER.type)){
			return POWER;
		}else if(type.equalsIgnoreCase(MODULO.type)){
			return MODULO;
		}else if(type.equalsIgnoreCase(EQUALS.type)){
			return EQUALS;
		}else if(type.equalsIgnoreCase(AND.type)){
			return AND;
		}else if(type.equalsIgnoreCase(OR.type)){
			return OR;
		}else if(type.equalsIgnoreCase(XOR.type)){
			return XOR;
		}else if(type.equalsIgnoreCase(NOT.type)){
			return NOT;
		}else{
			return null;
		}
	}
	
	public abstract Number performOpperation(IActionArg first,IActionArg second);
}
