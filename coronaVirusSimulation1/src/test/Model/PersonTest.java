package Model;

import static org.junit.jupiter.api.Assertions.*;
import Model.Person;
import org.junit.jupiter.api.Test;

class PersonTest {
	
	@Test
	void testPerson() {
		Person person = new Person(10, 10);
		
		person.setX(20);
		person.setY(30);
		
		assertEquals(20, person.getX());
		assertEquals(30, person.getY());
		
	}

	@Test
	void testCheckForImmunity1() {
		Person person = new Person(10, 10);
		assertFalse(person.isImmune());
		
		person.setInfected(150);
		person.setInfected_duration(100);
		
		person.checkForImmunity();
		assertTrue(person.isImmune());
	}

	@Test
	void testCheckForImmunity2() {
		Person person = new Person(10, 10);
		assertFalse(person.isImmune());
		
		person.setInfected(100);
		person.setInfected_duration(200);
		
		person.checkForImmunity();
		assertFalse(person.isImmune());
	}
	
	@Test
	void testCheckForImmunity3() {
		Person person = new Person(10, 10);
		assertFalse(person.isImmune());
		
		person.setInfected(150);
		person.setInfected_duration(100);
		person.setDied(true);
		
		person.checkForImmunity();
		assertFalse(person.isImmune());
	}
	
	@Test
	void testDeathProbability() {
		Person person = new Person(10, 10);
		
		person.setInfected(0);
		person.deathProbability();
		assertFalse(person.isDied());
	}

	@Test
	void testVaccinate_after_infection() {
		Person person = new Person(10, 10);
		assertFalse(person.isTested());
	}

	@Test
	void testTesting() {
		Person person = new Person(10, 10);
		assertFalse(person.isTested());
	}

	@Test
	void testMove1() {
		Person person = new Person(10, 10);
		
		person.setWidth(100);
		person.setHeight(200);
		person.setX(-100);
		person.setY(-200);
		person.setVel_x(1);
		person.setVel_y(2);
		
		person.move();

		assertEquals(-1, person.getVel_x());
		assertEquals(-2, person.getVel_y());
	}
	
	@Test
	void testMove2() {
		Person person = new Person(10, 10);
		
		person.setWidth(100);
		person.setHeight(200);
		
		person.setX(100);
		person.setY(200);
		
		person.setVel_x(2);
		person.setVel_y(1);
		
		person.move();

		assertEquals(-2, person.getVel_x());
		assertEquals(-1, person.getVel_y());
	}
	
	@Test
	void testMove3() {
		Person person = new Person(10, 10);
		
		person.setWidth(400);
		person.setHeight(600);
		
		person.setX(100);
		person.setY(200);
		
		person.setVel_x(5);
		person.setVel_y(10);
		
		person.move();

		assertEquals(5, person.getVel_x());
		assertEquals(10, person.getVel_y());
	}

	@Test
	void testMove4() {
		Person person = new Person(10, 10);
		
		person.setWidth(100);
		person.setHeight(600);
		
		person.setX(500);
		person.setY(200);
		
		person.setVel_x(5);
		person.setVel_y(10);
		
		person.move();

		assertEquals(-5, person.getVel_x());
		assertEquals(10, person.getVel_y());
	}

}
