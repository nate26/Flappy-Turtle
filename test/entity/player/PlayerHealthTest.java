package entity.player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import entity.player.PlayerHealth;
import entity.player.PlayerManager;

class PlayerHealthTest {
	
	private PlayerHealth pH;
	
	@Before
	public void setUp() {
		PlayerManager player = new PlayerManager(null, null, null); //dont worry about it
		pH = new PlayerHealth();
	}

	@Test
	public void testHeartSlots() {
		PlayerHealth slots = new PlayerHealth(10);
		assertEquals(5, slots.getBar().getHeartSlots());
		slots = new PlayerHealth(9);
		assertEquals(5, slots.getBar().getHeartSlots());
		slots = new PlayerHealth(8);
		assertEquals(4, slots.getBar().getHeartSlots());
		slots = new PlayerHealth(7);
		assertEquals(4, slots.getBar().getHeartSlots());
		slots = new PlayerHealth(2);
		assertEquals(1, slots.getBar().getHeartSlots());
		slots = new PlayerHealth(1);
		assertEquals(1, slots.getBar().getHeartSlots());
	}

}
