package GuiQuarantine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import GuiQuarantine.AnimationPanel;
import Model.Person;


class QuarantineTest {

	@Test
	void testActionPerformed() {
		AnimationPanel panel = new AnimationPanel(800, 600);
		assertEquals(panel.getHeight(), 800);
		assertEquals(panel.getWidth(), 600);
		
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
		assertEquals(0, panel.calculate_no_of_infected());
		
		panel.setPopulation(1000);
		assertEquals(1, panel.calculate_no_of_infected());
		
		panel.setPopulation(200);
		assertEquals(1, panel.calculate_no_of_infected());
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
		
		panel.setPopulation(1000);
		assertEquals(999, panel.calculate_no_of_susceptible());
		
		panel.setPopulation(500);
		assertEquals(499, panel.calculate_no_of_susceptible());
		
		panel.setPopulation(150);
		assertEquals(149, panel.calculate_no_of_susceptible());
	}

	@Test
	void testCalculate_no_of_deaths() {
		AnimationPanel panel = new AnimationPanel(1000, 800);
		
		panel.setPopulation(1000);
		assertEquals(0, panel.calculate_no_of_deaths());
	}
	
	@Test
	void testMove_to_quarantine1() {
		AnimationPanel panel = new AnimationPanel(800, 600);
		int population = 1000;
		Person[] p = new Person[population];
		p[0].quarantine = false;
		panel.move_to_quarantine(0);
		assertTrue(p[0].quarantine);	
	}
	
	@Test
	void testMove_to_quarantine2() {
		AnimationPanel panel = new AnimationPanel(800, 600);
		int population = 1000;
		Person[] p = new Person[population];
		p[0].quarantine = true;
		panel.move_to_quarantine(0);
		assertTrue(p[0].quarantine);	
	}

	@Test
	void testMove_to_real_world1() {
		AnimationPanel panel = new AnimationPanel(800, 600);
		int population = 1000;
		Person[] p = new Person[population];
		p[0].quarantine = true;
		panel.move_to_real_world(0);
		assertFalse(p[0].quarantine);	
	}
	
	@Test
	void testMove_to_real_world2() {
		AnimationPanel panel = new AnimationPanel(800, 600);
		int population = 1000;
		Person[] p = new Person[population];
		p[0].quarantine = false;
		panel.move_to_real_world(0);
		assertFalse(p[0].quarantine);	
	}

	@Test
	void testHandleCollisions() {
		AnimationPanel panel = new AnimationPanel(1000, 800);
		
		panel.setPopulation(2);
		
		panel.handleCollisions();
		
		assertEquals(1, panel.calculate_no_of_infected());
		assertEquals(0, panel.calculate_no_of_immune());
		assertEquals(1, panel.calculate_no_of_susceptible());
		assertEquals(0, panel.calculate_no_of_deaths());
	}

}
