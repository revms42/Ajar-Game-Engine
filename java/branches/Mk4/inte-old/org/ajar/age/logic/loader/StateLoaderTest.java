package org.ajar.age.logic.loader;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.ajar.age.logic.Action;
import org.ajar.age.logic.DefaultEvent;
import org.ajar.age.logic.DefaultState;
import org.ajar.age.logic.Effect;
import org.ajar.age.logic.HashAttributes;
import org.ajar.age.logic.State;
import org.ajar.age.logic.loader.test.TestAction;
import org.ajar.age.logic.loader.test.TestChainable1;
import org.ajar.age.logic.loader.test.TestChainable2;
import org.ajar.age.logic.loader.test.TestChainable3;
import org.ajar.age.logic.loader.test.TestCondition1;
import org.ajar.age.logic.loader.test.TestCondition2;
import org.ajar.age.logic.loader.test.TestCondition3;
import org.ajar.age.logic.loader.test.TestEffect1;
import org.ajar.age.logic.loader.test.TestEffect2;
import org.ajar.age.logic.loader.test.TestEffect3;
import org.ajar.age.logic.loader.test.TestEnum;
import org.junit.Before;
import org.junit.Test;

public class StateLoaderTest {

	private static final String[] files = {
		"TestFile.txt"
	};
	
	private StateLoader<HashAttributes> loader;
	
	@Before
	public void setup() {
		loader = new StateLoader<HashAttributes>();
	}
	
	@Test
	public void testLoadSimpleEffect() {
		InputStream stream = StateLoaderTest.class.getResourceAsStream(files[0]);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		try {
			DefaultState<HashAttributes> state = 
					(DefaultState<HashAttributes>) loader.loadFromDesc(
							"SimpleEffect", 
							reader
					);
			for(TestAction action : TestAction.values()){
				assertTrue(state.getActions().contains(action));
			}
			assertEquals(TestAction.values().length,state.getActions().size());
			
			for(Action a : state.getActions()){
				Effect<HashAttributes> e = state.getEffectMap().get(a);
				if(a instanceof TestAction){
					switch((TestAction)a){
					case ACTION_1:
						assertTrue(e instanceof TestEffect1);
						break;
					case ACTION_2:
						assertTrue(e instanceof TestEffect2);
						break;
					case ACTION_3:
						assertTrue(e instanceof TestEffect3);
						break;
					default:
						fail("You forgot to code for " + a.toString());
						break;
					}
				}else{
					fail("Don't know what to do with " + a.toString());
				}
				
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLoadSimpleChain() {
		InputStream stream = StateLoaderTest.class.getResourceAsStream(files[0]);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		try {
			DefaultState<HashAttributes> state = 
					(DefaultState<HashAttributes>) loader.loadFromDesc(
							"SimpleChain", 
							reader
					);
			for(TestAction action : TestAction.values()){
				assertTrue(state.getActions().contains(action));
			}
			assertEquals(TestAction.values().length,state.getActions().size());
			
			for(Action a : state.getActions()){
				Effect<HashAttributes> e = state.getEffectMap().get(a);
				if(a instanceof TestAction){
					switch((TestAction)a){
					case ACTION_3:
						assertTrue(e instanceof TestChainable3);
						e = ((TestChainable3)e).getChild();
					case ACTION_2:
						assertTrue(a.toString(),e instanceof TestChainable2);
						e = ((TestChainable2)e).getChild();
						assertTrue(e instanceof TestChainable1);
						break;
					case ACTION_1:
						assertTrue(e instanceof TestEffect1);
						break;
					default:
						fail("You forgot to code for " + a.toString());
						break;
					}
				}else{
					fail("Don't know what to do with " + a.toString());
				}
				
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLoadSimpleCondition() {
		InputStream stream = StateLoaderTest.class.getResourceAsStream(files[0]);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		try {
			DefaultState<HashAttributes> state = 
					(DefaultState<HashAttributes>) loader.loadFromDesc(
							"SimpleCondition", 
							reader
					);
			for(TestAction action : TestAction.values()){
				assertTrue(state.getActions().contains(action));
			}
			assertEquals(TestAction.values().length,state.getActions().size());
			
			for(Action a : state.getActions()){
				Effect<HashAttributes> e = state.getEffectMap().get(a);
				if(a instanceof TestAction){
					switch((TestAction)a){
					case ACTION_1:
						assertTrue(e instanceof TestCondition1);
						TestCondition1 t1 = (TestCondition1)e;
						assertTrue(t1.trueEffect() instanceof TestEffect1);
						TestEffect1 te11 = (TestEffect1)t1.trueEffect();
						assertEquals(loader.getLoadedState("SimpleCondition"),te11.getResult());
						assertTrue(t1.falseEffect() instanceof TestEffect2);
						TestEffect2 te12 = (TestEffect2)t1.falseEffect();
						assertEquals(loader.getLoadedState("SimpleEffect"),te12.getResult());
						break;
					case ACTION_2:
						assertTrue(e instanceof TestCondition2);
						TestCondition2 t2 = (TestCondition2)e;
						assertTrue(t2.trueEffect() instanceof TestEffect2);
						TestEffect2 te21 = (TestEffect2)t2.trueEffect();
						assertEquals(loader.getLoadedState("SimpleCondition"),te21.getResult());
						assertTrue(t2.falseEffect() instanceof TestEffect3);
						TestEffect3 te22 = (TestEffect3)t2.falseEffect();
						assertEquals(loader.getLoadedState("SimpleEffect"),te22.getResult());
						break;
					case ACTION_3:
						assertTrue(e instanceof TestCondition3);
						TestCondition3 t3 = (TestCondition3)e;
						assertTrue(t3.trueEffect() instanceof TestEffect3);
						TestEffect3 te31 = (TestEffect3)t3.trueEffect();
						assertEquals(loader.getLoadedState("SimpleCondition"),te31.getResult());
						assertTrue(t3.falseEffect() instanceof TestEffect1);
						TestEffect1 te32 = (TestEffect1)t3.falseEffect();
						assertEquals(loader.getLoadedState("SimpleEffect"),te32.getResult());
						break;
					default:
						fail("You forgot to code for " + a.toString());
						break;
					}
				}else{
					fail("Don't know what to do with " + a.toString());
				}
				
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLoadNullResult() {
		InputStream stream = StateLoaderTest.class.getResourceAsStream(files[0]);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		try {
			DefaultState<HashAttributes> state = 
					(DefaultState<HashAttributes>) loader.loadFromDesc(
							"First", 
							reader
					);
			Effect<HashAttributes> e = state.getEffectMap().get(TestAction.ACTION_1);
			assertTrue(e instanceof TestEffect1);
			TestEffect1 te = (TestEffect1)e;
			assertEquals(null,te.getResult());
			//Because this is a default state we allow null return on perform.
			assertEquals(
					null,
					state.perform(null, new DefaultEvent<HashAttributes>(TestAction.ACTION_1,null))
			);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLoadDerivedState() {
		InputStream stream = StateLoaderTest.class.getResourceAsStream(files[0]);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		try {
			DefaultState<HashAttributes> state = 
					(DefaultState<HashAttributes>) loader.loadFromDesc(
							"Second", 
							reader
					);
			Effect<HashAttributes> e2 = state.getEffectMap().get(TestAction.ACTION_2);
			assertTrue(e2 instanceof TestEffect2);
			TestEffect2 te2 = (TestEffect2)e2;
			assertEquals(null,te2.getResult());
			State<HashAttributes> result = state.perform(null, new DefaultEvent<HashAttributes>(TestAction.ACTION_1,null));
			assertEquals(
					null,
					result
			);
			assertEquals(
					null,
					state.perform(null, new DefaultEvent<HashAttributes>(TestAction.ACTION_2,null))
			);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLoadSecondDerivedState() {
		InputStream stream = StateLoaderTest.class.getResourceAsStream(files[0]);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		try {
			DefaultState<HashAttributes> state = 
					(DefaultState<HashAttributes>) loader.loadFromDesc(
							"Third", 
							reader
					);
			Effect<HashAttributes> e3 = state.getEffectMap().get(TestAction.ACTION_3);
			assertTrue(e3 instanceof TestEffect3);
			TestEffect3 te3 = (TestEffect3)e3;
			assertEquals(null,te3.getResult());
			Effect<HashAttributes> e1 = state.getEffectMap().get(TestAction.ACTION_1);
			assertTrue(e1 instanceof TestEffect3);
			TestEffect3 te1 = (TestEffect3)e1;
			assertEquals(loader.getLoadedState("Second"),te1.getResult());
			assertEquals(
					null,
					state.perform(null, new DefaultEvent<HashAttributes>(TestAction.ACTION_3,null))
			);
			assertEquals(
					null,
					state.perform(null, new DefaultEvent<HashAttributes>(TestAction.ACTION_2,null))
			);
			assertEquals(
					loader.getLoadedState("Second"),
					state.perform(null, new DefaultEvent<HashAttributes>(TestAction.ACTION_1,null))
			);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testEffectArgs() {
		InputStream stream = StateLoaderTest.class.getResourceAsStream(files[0]);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		try {
			DefaultState<HashAttributes> state = 
					(DefaultState<HashAttributes>) loader.loadFromDesc(
							"InitEffect", 
							reader
					);
			Effect<HashAttributes> e3 = state.getEffectMap().get(TestAction.ACTION_1);
			assertTrue(e3 instanceof TestEffect3);
			TestEffect3 te3 = (TestEffect3)e3;
			assertEquals(loader.getLoadedState("InitEffect"),te3.getResult());
			
			assertEquals(Integer.class,te3.args[0].getClass());
			assertEquals(1,te3.args[0]);
			assertEquals(Long.class,te3.args[1].getClass());
			assertEquals(2l,te3.args[1]);
			assertEquals(Float.class,te3.args[2].getClass());
			assertEquals(3f,te3.args[2]);
			assertEquals(Double.class,te3.args[3].getClass());
			assertEquals(4d,te3.args[3]);
			
			assertEquals(TestEnum.B,te3.en);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
