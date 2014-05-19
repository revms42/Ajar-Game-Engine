package org.ajar.age.logic.loader;

import java.io.BufferedReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ajar.age.Attributes;
import org.ajar.age.logic.AbstractChainableEffect;
import org.ajar.age.logic.AbstractCondition;
import org.ajar.age.logic.AbstractEffect;
import org.ajar.age.logic.Action;
import org.ajar.age.logic.ChainableEffect;
import org.ajar.age.logic.Condition;
import org.ajar.age.logic.DefaultState;
import org.ajar.age.logic.DerivedState;
import org.ajar.age.logic.Effect;
import org.ajar.age.logic.State;

public class StateLoader<A extends Attributes> {

	/*
	 * Ok, here are the things that we worry about loading:
	 * 
	 * Simple Constructor constants: Enums, primatives, strings, or
	 * things that take the above as arguments.
	 * Actions: Have to be unique.
	 * Attributes: For reference.
	 * Effects: Can have simple arguments. Results in a given state.
	 * States: Map Actions to effects.
	 * Conditions: Map two effects to a condition.
	 * ChainableEffect: Two or more effects executed in sequence.
	 * 
	 * Two options: Let people provide all the class information in the
	 * descriptor file as well as any configuration information and we
	 * initalize everything for them. Or, we have them pass in HashMaps
	 * of named classes and use those.
	 * The first option is more complicated and requires us to have logic
	 * to handle stuff through reflection.
	 * The second is easier, but puts more of a burden on the writers to 
	 * pack everything for shipping beforehand.
	 * 
	 * Third option: We do initialization of classes in age in the descriptor (
	 * i.e. the stuff we know about already, like conditions) but let people drop
	 * in their own stuff if it won't fit via maps.
	 * 
	 * Ok. Here we go:
	 * 
	 * State:Basic {
	 * 	Action:null -> Effect:animate -> Result:no-change
	 * }
	 * State:Move (base:Basic) {
	 * 	Action:Start_Move -> Condition:checkMove ? Effect(true): startMove, toggleInput(1)
	 * 	, resetCounter = Result:moving | Effect(false): animate = Result:no-change
	 * }
	 * State:Moving (base:Basic) {
	 * 	Action:null -> Condition:checkDone ? Effect(true):endMove, toggleInput(1), startCounter
	 * -> Result:move | Effect(false): animate -> Result:no-change
	 * }
	 * 
	 * Syntax:
	 * Type(\:)Name
	 * For State -> \s*\((\qbase:\e){0,1}Name\s*\{
	 * For Condition -> ?\s*(\q[Ee]ffect\e)*\([true]|[false]\)\:Name(,\s*Name)*(->\s*[Rr]esult)*\:
	 * Name\s*\|\s*(\q[Ee]ffect\e)*\([true]|[false]\)\:Name(,\s*Name)*(->\s*[Rr]esult)*\:Name
	 * 
	 * You can omit the keywords, but not punctuation.
	 * Effects can have simple input ("string",int, long, float, double, byte, enum).
	 * 
	 * State:Move(^Basic){
	 * StartMove->checkMove?startMove&toggleInput(1)&resetCounter=moving|animate=no-change
	 * }
	 * 
	 * You can also define other things outside of the state if you want to use them
	 * multiple places.
	 * 
	 * Action:Move=com.myproject.MyEnum.MOVE
	 * Condition:checkDone?startMove,toggleInput(1),resetCounter:moving|animate:no-change
	 * 
	 * We'll have to prescan all the states at least. We may have to prescan everything.
	 */
	
	private final static String actionKW = "[aA]ction";
	private final static String effectKW = "[eE]ffect";
	private final static String chainKW = "[cC]hain";
	private final static String conditionKW = "[cC]ondition";
	private final static String stateKW = "[sS]tate";
	
	private final static String splitAction = "\\s*\\Q->\\E\\s*";
	private final static String splitEffect = "\\s*\\=\\s*";
	private final static String splitChain = "\\s*\\&\\s*";
	private final static String splitConditionTrue = "\\s*\\?\\s*";
	private final static String splitConditionFalse = "\\s*\\|\\s*";
	
	private final static Pattern matchNameArgs = Pattern.compile("(.*)\\s*\\((.*)\\)");
	private final static String splitComment = "\\Q#\\E";
	
	public interface ITypeDeclaration {
		public <A extends Attributes> void addToMap(StateLoader<A> loader, String name, String clazz)  throws ClassNotFoundException;
		public <A extends Attributes> String addToInventory(StateLoader<A> loader, String line, BufferedReader reader) throws Exception; 
	}
	
	public enum TypeDeclaration implements ITypeDeclaration {
		ACTION(actionKW){

			@Override
			public <A extends Attributes> void addToMap(StateLoader<A> loader,String name, String clazz) throws ClassNotFoundException {
				loader.addAction(name, clazz);
			}

			@Override
			public <A extends Attributes> String addToInventory(StateLoader<A> loader, String line, BufferedReader reader) throws Exception {
				return loader.makeAction(loader, line, reader);
			}},
		EFFECT(effectKW){

			@Override
			public <A extends Attributes> void addToMap(StateLoader<A> loader, String name, String clazz) throws ClassNotFoundException {
				loader.addEffect(name, clazz);
			}

			@Override
			public <A extends Attributes> String addToInventory(StateLoader<A> loader, String line, BufferedReader reader) throws Exception {
				return loader.makeEffect(loader, line, reader, null);
			}},
		CHAIN(chainKW){

			@Override
			public <A extends Attributes> void addToMap(StateLoader<A> loader, String name, String clazz) throws ClassNotFoundException {
				loader.addEffect(name, clazz);
			}

			@Override
			public <A extends Attributes> String addToInventory(StateLoader<A> loader, String line, BufferedReader reader) throws Exception {
				return loader.makeChain(loader, line, reader, null);
			}},
		CONDITION(conditionKW){

			@Override
			public <A extends Attributes> void addToMap(StateLoader<A> loader, String name, String clazz) throws ClassNotFoundException {
				loader.addEffect(name, clazz);
			}

			@Override
			public <A extends Attributes> String addToInventory(StateLoader<A> loader, String line, BufferedReader reader) throws Exception {
				return loader.makeCondition(loader, line, reader, null);
			}},
		STATE(stateKW){

			@Override
			public <A extends Attributes> void addToMap(StateLoader<A> loader, String name, String clazz) throws ClassNotFoundException {
				loader.addState(name, clazz);
			}

			@Override
			public <A extends Attributes> String addToInventory(StateLoader<A> loader, String line, BufferedReader reader) {
				try {
					return loader.makeState(loader, line, reader);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}};
		
		private final static String clzPattern = ":\\s*(\\w+)\\s*=\\s*([\\w\\.&&[^,\\|\\?\\:]]+)\\s*$";
			
		public Pattern keyword;
		public Pattern namedPattern;
		
		private TypeDeclaration(String keyword){
			this.keyword = Pattern.compile("\\W"+keyword);
			this.namedPattern = Pattern.compile("^\\s*"+keyword+clzPattern);
		}
	}
	
	private final HashMap<String,Class<? extends Action>> actions;
	private final HashMap<String,Class<? extends Effect<A>>> effects;
	private final HashMap<String,Class<? extends ChainableEffect<A>>> chains;
	private final HashMap<String,Class<? extends Condition<A>>> conditions;
	private final HashMap<String,Class<? extends State<A>>> states;
	
	private final HashMap<String,Action> namedActions;
	private final HashMap<String,Effect<A>> namedEffects;
	private final HashMap<String,ChainableEffect<A>> namedChains;
	private final HashMap<String,Condition<A>> namedConditions;
	private final HashMap<String,State<A>> namedStates;
	
	private final ClassLoader loader;
	
	public StateLoader(){
		actions = new HashMap<String,Class<? extends Action>>();
		effects = new HashMap<String,Class<? extends Effect<A>>>();
		chains = new HashMap<String,Class<? extends ChainableEffect<A>>>();
		conditions = new HashMap<String,Class<? extends Condition<A>>>();
		states = new HashMap<String,Class<? extends State<A>>>();
		
		namedActions = new HashMap<String,Action>();
		namedEffects = new HashMap<String,Effect<A>>();
		namedChains = new HashMap<String,ChainableEffect<A>>();
		namedConditions = new HashMap<String,Condition<A>>();
		namedStates = new HashMap<String,State<A>>();
		
		loader = StateLoader.class.getClassLoader();
	}
	
	@SuppressWarnings("unchecked")
	public void addAction(String name, String clazz) throws ClassNotFoundException{
		try {
			actions.put(name, (Class<? extends Action>) loader.loadClass(clazz));
		}catch(ClassNotFoundException e){
			int index = clazz.lastIndexOf(".");
			String clz = clazz.substring(0, index);
			String member = clazz.substring(index+1, clazz.length());
			
			Class<? extends Action> action = 
					(Class<? extends Action>) loader.loadClass(clz);
			
			if(action.isEnum()){
				for(Action a : action.getEnumConstants()){
					if(a.toString().equalsIgnoreCase(member)){
						namedActions.put(name, a);
						return;
					}
				}
				throw e;
			}else{
				throw e;
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void addEffect(String name, String clazz) throws ClassNotFoundException{
		Class<? extends Effect<A>> effect = 
				(Class<? extends Effect<A>>) loader.loadClass(clazz);
		
		if(ChainableEffect.class.isAssignableFrom(effect)){
			chains.put(name, (Class<? extends ChainableEffect<A>>)effect);
		}
		if(Condition.class.isAssignableFrom(effect)){
			conditions.put(name, (Class<? extends Condition<A>>)effect);
		}
		effects.put(name, effect);
	}
	
	@SuppressWarnings("unchecked")
	public void addState(String name, String clazz) throws ClassNotFoundException{
		states.put(name, (Class<? extends State<A>>) loader.loadClass(clazz));
	}
	
	public void addNamedAction(String name, Action action){
		namedActions.put(name, action);
	}
	
	public void addNamedEffect(String name, Effect<A> effect){
		namedEffects.put(name, effect);
	}
	
	public void addNamedChain(String name, ChainableEffect<A> effect){
		namedChains.put(name, effect);
	}
	
	public void addNamedCondition(String name, Condition<A> condition){
		namedConditions.put(name, condition);
	}
	
	public void addNamedState(String name, State<A> state){
		namedStates.put(name, state);
	}
	/**
	 * Supported syntax:
	 * 
	 * Action:Action1=org.ajar.age.logic.loader.test.TestAction.ACTION_1
	 * 
	 * Effect:Effect1=org.ajar.age.logic.loader.test.TestEffect1
	 * 
	 * Chain:Chain1=org.ajar.age.logic.loader.test.TestChainable1
	 * 
	 * Condition:Condition1=org.ajar.age.logic.loader.test.TestCondition1
	 * 
	 * State:Move(^Basic){
	 * 	StartMove->checkMove?startMove&toggleInput(1)&resetCounter=moving|animate=no-change
	 * }
	 * @param state
	 * @param reader
	 * @return
	 * @throws Exception
	 */
	public State<A> loadFromDesc(String state, Reader reader) throws Exception {
		BufferedReader br = new BufferedReader(reader);
		
		String line = "";
		String processLine = "";
		Matcher m = null;
		reading:
		while((line = br.readLine()) != null){
			line = line.split(splitComment)[0];
			
			if(line == null){
				continue reading;
			}
			
			if(line.length() == 0 && line.matches("$\\w*^")){
				continue reading;
			}else{
				processLine = processLine + " " + line;
			}
			//Check for classes being prepared.
			for(TypeDeclaration td : TypeDeclaration.values()){
				m = td.namedPattern.matcher(processLine);
				if(m.find()){
					td.addToMap(this, m.group(1), m.group(2));
					processLine = "";
					continue reading;
				}
				//TODO: Use the same strategy to look for and instantiate 
				//actions effects and states.
				m = td.keyword.matcher(processLine);
				if(m.find()){
					td.addToInventory(this, processLine, br);
					processLine = "";
					continue reading;
				}
			}
		}
		return namedStates.get(state);
	}
	
	public State<A> getLoadedState(String name){
		return namedStates.get(name);
	}
	
	public Action getLoadedAction(String name){
		return namedActions.get(name);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String makeState(StateLoader<A> loader, String line, BufferedReader reader) throws Exception {
		line = line.split(stateKW + ":",2)[1];
		
		String name = null;
		State<A> state = null;
		//You need initialization.
		if(line.contains("(")){
			Matcher m = matchNameArgs.matcher(line);
			if(m.find()){
				//Your name.
				name = m.group(1);
				//Your args.
				String args = m.group(2);
				
				//You're a derived state.
				if(args.contains("^")){
					State<A> base = null;
					//You're a derived state whose base needs initization.
					if(args.contains("(")){
						//Your base state args.
						m = matchNameArgs.matcher(args);
						Vector<Object> conArgs = stringToArgs(m.group(2));
						
						Class<State<A>> baseClass = (Class<State<A>>) states.get(name);
						Constructor<State<A>> con = baseClass.getConstructor(argsToConList(conArgs));
						//Your base state.
						base = con.newInstance(conArgs.toArray());
					}else{
						//No args, grab a named state or make a new named state.
						if(!namedStates.containsKey(args.split("\\^")[1])){
							namedStates.put(args.split("\\^")[1], new DefaultState<A>());
						}
						base = (State<A>) namedStates.get(args.split("\\^")[1]);
					}
					//Your derived state.
					state = new DerivedState<A>(base);
				}else{
					//States that need initialization that aren't derived.
					//i.e. annonymous
					m = matchNameArgs.matcher(args);
					Vector<Object> conArgs = stringToArgs(m.group(2));
					
					//Because of the way you're constructing this we assume
					//you want the same name as the class.
					Class<State<A>> annonState = (Class<State<A>>) states.get(name);
					Constructor<State<A>> con = annonState.getConstructor(argsToConList(conArgs));
					//Your initialized state.
					state = (State<A>) con.newInstance(conArgs.toArray());
				}
			}else{
				System.err.println("ERROR: No Arguments found! Bad syntax on : " + line);
				return null;
			}
		}else{
			Matcher m = Pattern.compile("\\w+").matcher(line);
			if(m.find()){
				name = m.group();
				//States that don't need initialization.
				if(!namedStates.containsKey(name)){
					namedStates.put(name, new DefaultState<A>());
				}
				state = (State<A>) namedStates.get(name);
			}else{
				System.err.println("ERROR: No name found! Bad syntax on : " + line);
				return null;
			}
		}
		
		//You have your state. Now, do something with it.
		String nextLine = null;
		while(!(nextLine = reader.readLine()).contains("}")){
			if(nextLine.contains("?")){
				makeCondition(loader,nextLine,reader,(DefaultState<A>) state);
			}else if(nextLine.contains("&")){
				makeChain(loader,nextLine,reader,(DefaultState<A>) state);
			}else{
				makeEffect(loader, nextLine, reader, (DefaultState<A>)state);
			}
		}
		
		namedStates.put(name, (State)state);
		return name;
	}
	
	private String makeEffect(StateLoader<A> loader, String line, BufferedReader reader, DefaultState<A> target) throws Exception {
		String[] actionLine = line.split(splitAction,2);
		
		Action trigger = parseAction(loader,line,reader);
		
		Effect<A> effect = makeSubEffect(actionLine[1]);
		
		target.put(trigger, effect);
		
		String effectName = actionLine[1].split(splitEffect,2)[0];
		return effectName;
	}
	
	//TODO: This is a temporary method while I try to integrate this logic into the makeEffect method for makeCondition.
	//?)startMove&toggleInput(1)&resetCounter=moving(|
	private Effect<A> makeSubEffect(String line) throws Exception{
		String[] effectResult = line.split(splitEffect,2);
		
		//Now, find the result state.
		State<A> result = null;
		if(		!effectResult[1].equalsIgnoreCase("null") && 
				!effectResult[1].equalsIgnoreCase("no-change")){
			if(!namedStates.containsKey(effectResult[1])){
				//This isn't the place to init states. So if they're here give them a default.
				namedStates.put(effectResult[1], new DefaultState<A>());
			}
			result = namedStates.get(effectResult[1]);
		}
		
		return instantiateEffect(effectResult[0],result);
	}
	
	private Effect<A> instantiateEffect(String effectLine, State<A> resultState) throws Exception {
		Effect<A> effect = null;
		if(effectLine.contains("(")){
			//This effect needs to be initialized.
			Matcher m = matchNameArgs.matcher(effectLine);
			
			if(m.find()){
				//Here are your arguments.
				Class<? extends Effect<A>> eClass = effects.get(m.group(1));
				
				//TODO: Most of the time we'd want to set the result state too.
				Vector<Object> args = stringToArgs(m.group(2));
				
				if(AbstractEffect.class.isAssignableFrom(eClass)){
					//TODO: First arg must be result state.
					args.add(0,resultState);
				}
				
				Constructor<? extends Effect<A>> eCon = 
						eClass.getConstructor(argsToConList(args));
				//Here is a new instance of your effect.
				effect = eCon.newInstance(args.toArray());
			}else{
				System.err.println("ERROR: Syntax error parsing effect args.");
				return null;
			}
		}else{
			//This effect either takes no args or is instantiated.
			if(namedEffects.containsKey(effectLine)){
				effect = namedEffects.get(effectLine);
			}else{
				//Most of the effects will be abstract effects, meaning they take
				//a result.
				Class<? extends Effect<A>> eClass = effects.get(effectLine);
				if(AbstractEffect.class.isAssignableFrom(eClass)){
					Constructor<? extends Effect<A>> eCon = 
							eClass.getConstructor(State.class);
					effect = eCon.newInstance(resultState);
				}else{
					effect = eClass.newInstance();
				}
				
			}
		}
		
		return effect;
	}
	
	private Condition<A> instantiateCondition(String conditionLine, Effect<A> trueEffect, Effect<A> falseEffect) throws Exception {
		Condition<A> condition = null;
		if(conditionLine.contains("(")){
			//This effect needs to be initialized.
			Matcher m = matchNameArgs.matcher(conditionLine);
			
			if(m.find()){
				//Here are your arguments.
				Class<? extends Condition<A>> cClass = conditions.get(m.group(1));
				
				Vector<Object> args = stringToArgs(m.group(2));
				args.add(trueEffect);
				args.add(falseEffect);
				Constructor<? extends Condition<A>> cCon = 
						cClass.getConstructor(argsToConList(args));
				//Here is a new instance of your condition.
				condition = cCon.newInstance(args.toArray());
			}else{
				System.err.println("ERROR: Syntax error parsing effect args.");
				return null;
			}
		}else{
			//This effect either takes no args or is instantiated.
			if(namedConditions.containsKey(conditionLine)){
				condition = namedConditions.get(conditionLine);
			}else{
				//Most of the effects will be abstract effects, meaning they take
				//a result.
				Class<? extends Condition<A>> cClass = conditions.get(conditionLine);
				if(AbstractCondition.class.isAssignableFrom(cClass)){
					Constructor<? extends Condition<A>> cCon = 
							cClass.getConstructor(Effect.class,Effect.class);
					condition = cCon.newInstance(trueEffect,falseEffect);
				}else{
					condition = cClass.newInstance();
				}
				
			}
		}
		
		return condition;
	}
	
	private Action parseAction(StateLoader<A> loader, String line, BufferedReader reader) throws Exception {
		String[] actionLine = line.split(splitAction,2);
		
		String action = actionLine[0].trim();
		action = makeAction(loader,action,reader);
		return namedActions.get(action);
	}
	
	//Action already parsed.
	private Effect<A> determineEffectType(String line) throws Exception {
		if(line.contains("?")){
			return makeSubCondition(line);
		}else if(line.contains("&")){
			return makeSubChain(line);
		}else{
			return makeSubEffect(line);
		}
	}
	
	private String makeCondition(StateLoader<A> loader, String line, BufferedReader reader, DefaultState<A> target) throws Exception {
		String[] actionLine = line.split(splitAction,2);
		
		Action trigger = parseAction(loader,line,reader);
		
		Condition<A> condition = makeSubCondition(actionLine[1]);
		target.put(trigger, condition);
		
		String effectName = actionLine[1].split(splitEffect,2)[0];
		return effectName;
	}
	
	private Condition<A> makeSubCondition(String line) throws Exception {
		//String[] conditionResult = line.split(splitCondition,2);
		String[] conditionResult = line.split(splitConditionTrue);
		String[] trueFalse = conditionResult[1].split(splitConditionFalse);
		
		Effect<A> effectTrue = determineEffectType(trueFalse[0]);
		Effect<A> effectFalse = determineEffectType(trueFalse[1]);
		
		Condition<A> condition = instantiateCondition(conditionResult[0],effectTrue,effectFalse);
		return condition;
	}
	
	private String makeChain(StateLoader<A> loader, String line, BufferedReader reader, DefaultState<A> target) throws Exception {
		String[] actionLine = line.split(splitAction,2);
		
		Action trigger = parseAction(loader,line,reader);
		
		ChainableEffect<A> chain = makeSubChain(actionLine[1]);
		target.put(trigger, chain);
		
		String effectName = actionLine[1].split(splitEffect,2)[0];
		return effectName;
	}
	
	private ChainableEffect<A> makeSubChain(String line) throws Exception {
		String[] chainLinks = line.split(splitChain,2);
		
		String[] chainResult = chainLinks[0].split(splitEffect,2);
		
		//Now, find the result state.
		State<A> result = null;
		if(chainResult.length < 1){
			if(!namedStates.containsKey(chainResult[1])){
				//This isn't the place to init states. So if they're here give them a default.
				namedStates.put(chainResult[1], new DefaultState<A>());
			}
			result = namedStates.get(chainResult[1]);
		}
		
		ChainableEffect<A> chain = instantiateChain(chainResult[0],result);
		//TODO: We should be sending this back through the determineEffectType method
		//but we need to figure out how to force it to recognize it as a chain...
		//Either that or start putting things in every category they qualify for.
		if(chainLinks.length > 1) chain.addToChain(makeSubChain(chainLinks[1]));
		
		return chain;
	}
	
	private String makeAction(StateLoader<A> loader, String line, BufferedReader reader) throws Exception{
		//TODO: Last step. Figure out what to do to instantiate actions.
		//TODO: I think this is what we have to do to make this work.
		namedActions.put(line, instantiateAction(line));
		return line;
	}
	
	private ChainableEffect<A> instantiateChain(String effectLine, State<A> resultState) throws Exception {
		ChainableEffect<A> effect = null;
		if(effectLine.contains("(")){
			//This effect needs to be initialized.
			Matcher m = matchNameArgs.matcher(effectLine);
			
			if(m.find()){
				//Here are your arguments.
				Class<? extends ChainableEffect<A>> eClass = chains.get(m.group(1));
				
				//TODO: Most of the time we'd want to set the result state too.
				Vector<Object> args = stringToArgs(m.group(2));
				
				Constructor<? extends ChainableEffect<A>> eCon = null;
				
				try{
					eCon = eClass.getConstructor(argsToConList(args));
					effect = eCon.newInstance(args.toArray());
				}catch(NoSuchMethodException e){
					try{
						eCon = eClass.getConstructor(argsToConList(args,State.class));
						args.add(0,resultState);
						effect = eCon.newInstance(args.toArray());
					}catch(NoSuchMethodException e2){
						throw e2;
					}
				}
						
				//Here is a new instance of your effect.
				
			}else{
				System.err.println("ERROR: Syntax error parsing effect args.");
				return null;
			}
		}else{
			//This effect either takes no args or is instantiated.
			if(namedChains.containsKey(effectLine)){
				effect = namedChains.get(effectLine);
			}else{
				//Most of the effects will be abstract effects, meaning they take
				//a result.
				Class<? extends ChainableEffect<A>> eClass = chains.get(effectLine);
				if(AbstractChainableEffect.class.isAssignableFrom(eClass)){
					Constructor<? extends ChainableEffect<A>> eCon = 
							eClass.getConstructor(State.class);
					effect = eCon.newInstance(resultState);
				}else{
					effect = eClass.newInstance();
				}
				
			}
		}
		
		return effect;
	}
	
	private Action instantiateAction(String line) throws Exception {
		Action action = null;
		//TODO: We should never have to go down the initialized road.
		if(line.contains("(")){
			//This action needs to be initialized.
			Matcher m = matchNameArgs.matcher(line);
			
			if(m.find()){
				//Here are your arguments.
				Class<? extends Action> aClass = actions.get(m.group(1));
				
				//TODO: Most of the time we'd want to set the result state too.
				Vector<Object> args = stringToArgs(m.group(2));
				Constructor<? extends Action> aCon = 
						aClass.getConstructor(argsToConList(args));
				//Here is a new instance of your action.
				action = aCon.newInstance(args.toArray());
			}else{
				System.err.println("ERROR: Syntax error parsing effect args.");
				return null;
			}
		}else{
			//This effect either takes no args or is instantiated.
			action = namedActions.get(line);
		}
		return action;
	}
	
	private Vector<Object> stringToArgs(String str){
		Vector<Object> args = new Vector<Object>();
		parse:
		for(String s : str.split(",")){
			if(s.contains("\"")){
				s.replaceAll("\"", "");
				args.add(s);
			}else if(s.matches("\\d+\\.*\\d*d")){
				args.add(Double.parseDouble(s.split("d")[0]));
			}else if(s.matches("\\d+\\.*\\d*f")){
				args.add(Float.parseFloat(s.split("f")[0]));
			}else if(s.matches("\\d+l")){
				args.add(Long.parseLong(s.split("l")[0]));
			}else if(s.matches("\\d+")){
				args.add(Integer.parseInt(s));
			}else if(s.matches("[\\w\\.]+")){
				Matcher m = Pattern.compile("(.+)\\.(\\w+)$").matcher(s);
				
				if(m.find()){
					try {
						Class<?> clazz = loader.loadClass(m.group(1));
						if(clazz.isEnum()){
							for(Object o : clazz.getEnumConstants()){
								if(o.toString().equalsIgnoreCase(m.group(2))){
									args.add(o);
									continue parse;
								}
							}
							System.err.println("ERROR: Could not find enum value with name " + m.group(2));
						}else{
							System.err.println("ERROR: Could not process " + s + " as enum: Only simple arguments supported.");
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}else{
					System.err.println("ERROR: Syntax error on argument " + s);
				}
			}else{
				System.err.println("ERROR: Could not process " + s + ": Only simple arguments supported.");
			}
		}
		return args;
	}
	
	private Class<?>[] argsToConList(Vector<Object> objects){
		return argsToConList(objects,new Class<?>[0]);
	}
	
	private Class<?>[] argsToConList(Vector<Object> objects, Class<?>... initial){
		Vector<Class<?>> classes = new Vector<Class<?>>();
		
		if(initial != null && initial.length > 0){
			for(Class<?> init : initial){
				classes.add(init);
			}
		}
		
		for(Object o : objects){
			if(o instanceof Double){
				classes.add(Double.TYPE);
			}else if(o instanceof Float){
				classes.add(Float.TYPE);
			}else if(o instanceof Long){
				classes.add(Long.TYPE);
			}else if(o instanceof Integer){
				classes.add(Integer.TYPE);
			}else{
				if(State.class.isAssignableFrom(o.getClass())){
					classes.add(State.class);
				}else{
					classes.add(o.getClass());
				}
			}
		}
		
		return classes.toArray(new Class<?>[classes.size()]);
	}
}
