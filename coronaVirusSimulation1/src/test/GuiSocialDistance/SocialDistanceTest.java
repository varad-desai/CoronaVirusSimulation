package GuiSocialDistance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import GuiSocialDistance.AnimationPanel;
import Model.Person;

class SocialDistanceTest {

	@Test
	void testActionPerformed() {
		AnimationPanel panel = new AnimationPanel(600,800);
		assertEquals(panel.getHeight(), 600);
		assertEquals(panel.getWidth(), 800);
		
		panel.setPopulation(1000);
		
		assertEquals(1000, panel.getPopulation());
	}

	@Test
	void testCalculate_r_factor() {
		AnimationPanel panel = new AnimationPanel(800, 600);
		Person person = new Person(1, 1);
		assertEquals(0, panel.calculate_r_factor());
	}

	@Test
	void testCalculate_no_of_infected() {
		AnimationPanel panel = new AnimationPanel(800, 600);
		Person person = new Person(1, 1);
		
		panel.setPopulation(0);
		assertFalse ( panel.calculate_no_of_infected() <= 0);
	}

	@Test
	void testCalculate_no_of_immune() {
		AnimationPanel panel = new AnimationPanel(500, 400);
		
		panel.setPopulation(1000);	
		assertEquals(0, panel.calculate_no_of_immune());
		
		panel.setPopulation(500);	
		assertEquals(0, panel.calculate_no_of_immune());
	}

	@Test
	void testCalculate_no_of_susceptible() {
		AnimationPanel panel = new AnimationPanel(1000, 800);
		
		panel.setPopulation(200);
		assertEquals(191, panel.calculate_no_of_susceptible());
	}

	@Test
	void testCalculate_no_of_deaths() {
		AnimationPanel panel = new AnimationPanel(1000, 800);
		
		panel.setPopulation(1000);
		assertEquals(0, panel.calculate_no_of_deaths());
	}

	@Test
	void testHandleCollisions() {
		AnimationPanel panel = new AnimationPanel(1000, 800);

		panel.handleCollisions();
		
		assertTrue ( panel.calculate_no_of_infected() >= 0);
		assertEquals(0, panel.calculate_no_of_immune());
		assertTrue(panel.calculate_no_of_susceptible() >= 0);
		assertEquals(0, panel.calculate_no_of_deaths());
	}

}
