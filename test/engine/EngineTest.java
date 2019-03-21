package engine;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import engine.Engine;

class EngineTest {
	private Engine engine;

	@BeforeEach
	void setUp() throws Exception {
		JFrame f = new JFrame();
		//engine = new Engine(f);
	}

	@Test
	void test() {
		//assertEquals(10, engine.animateGravity(-1, 1000));
		//assertEquals(9, engine.animateGravity(-1, 967));
		//assertEquals(10, engine.animateGravity(1, 1));
		//assertEquals(9, engine.animateGravity(1, 120));
	}

}
