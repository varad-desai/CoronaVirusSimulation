/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.Random;
/**
 *
 * @author varad
 */
public class Person {

   public int x;
   public int y;
   public boolean immune = false;
   public int counter_for_quarantine = 0;
   public boolean quarantine = false;
   public boolean vaccinated = false;
   public boolean tested = false;
   public boolean testResult = false;
   public boolean died = false;
   public boolean can_move = true;
   public int infected = 0; // when infected, this will be positive and increase until the
                            // infectedDuration is reached, at which point the person
                            // will be immune
   public int no_of_person_infected = 0; // number of other person infected by this person over the course of infection
   private int vel_x = 0;
   private int vel_y = 0;
   private int infected_duration = 140;  // how many steps in the animation the person
                                        // remains infected
   private int height = 600;
   private int width = 800;

   private Random random = new Random();
      
   public Person(int x, int y) {
      this.x = x;
      this.y = y;
      // randomly set the Person's velocity- from -5 to 4 in both x and y directions
      vel_x = random.nextInt(10) - 5;
      vel_y = random.nextInt(10) - 5;
   	}
   
    public int getX() {
    	return x;
    }

    public void setX(int x) {
    	this.x = x;
    }

    public int getY() {
    	return y;
    }

    public void setY(int y) {
    	this.y = y;
    }
    
	public int getVel_x() {
		return vel_x;
	}

	public void setVel_x(int vel_x) {
		this.vel_x = vel_x;
	}

	public int getVel_y() {
		return vel_y;
	}

	public void setVel_y(int vel_y) {
		this.vel_y = vel_y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isTested() {
		return tested;
	}

	public void setTested(boolean tested) {
		this.tested = tested;
	}

	public int getInfected() {
		return infected;
	}

	public void setInfected(int infected) {
		this.infected = infected;
	}

	public boolean isVaccinated() {
		return vaccinated;
	}

	public void setVaccinated(boolean vaccinated) {
		this.vaccinated = vaccinated;
	}

	public boolean isDied() {
		return died;
	}

	public void setDied(boolean died) {
		this.died = died;
	}

	public boolean isImmune() {
		return immune;
	}

	public void setImmune(boolean immune) {
		this.immune = immune;
	}

	public int getInfected_duration() {
		return infected_duration;
	}

	public void setInfected_duration(int infected_duration) {
		this.infected_duration = infected_duration;
	}
	
	public int getNo_of_person_infected() {
		return no_of_person_infected;
	}

	public void setNo_of_person_infected(int no_of_person_infected) {
		this.no_of_person_infected = no_of_person_infected;
	}

	public void checkForImmunity(){
        // each time they move, if already infected they get closer to immunity
        if(infected > 0) {
            infected++;
            // Comment this below line to stop vaccination.
        }
        if(infected > infected_duration/2 && infected < infected_duration){
            vaccinate_after_infection();
        }
        if(infected == infected_duration-1){
            deathProbability();
        }
        // check to see if they've reached the immunity threshold
        if(infected > infected_duration && !died) {
            infected = 0;
            immune = true;
        }

    }
   
    public void deathProbability() {
            int random_number = random.nextInt(100);
            died = random_number >= 1 && random_number <= 2;
    }
    
    public void vaccinate_after_infection(){
        int random_number = random.nextInt(4);
        if(random_number == 1){
            vaccinated = true;
        }
    }
    
    // CDC estimates that about 40% of carriers are asymptomatic
    // Based on this number, 2 in 5 people that are infected will get tested
    // Assumption: once tested positive, person will take quarantine measures
    public void testing() {
    	int random_number = random.nextInt(100);
    	if (infected > 0 && random_number <= 4) {
    		testResult = true;
    		}
    	else if (immune) {
    		testResult = false;
    	}
    }
   
    public void move() {
        if(can_move){
            x += vel_x;
            y += vel_y;
//        if(reverse_direction){
//            vel_x = -vel_x;
//            vel_y = -vel_y;
//        }
        // if they hit the walls, bounce
        if (x > width || x < 0) {
            vel_x = -vel_x;
        }
        if (y > height || y < 0) {
            vel_y = -vel_y;
        }
        }
    }

    /**
     * Method to simulate that when person is quarantining then moves only for 10% of the time.
     * Rest of the population will remain healthy is rest of the population do not intersect with this population
     * and follows the social distancing protocol to move only 50% of the time to reduce the spread.
     */
   public void moveWithSocialDistance() {
       if(infected > 0){
           // person is infected, so next person will be following at home quarantine
           if (Math.random()<.1) {
               move ();
           }
       } else {
           if (Math.random()<.5) {
               move ();
           }
       }

    }
   
   public void move_within_quarantine_boundaries() {
       if(can_move){
           x += vel_x;
           y += vel_y;

       // if they hit the walls, bounce
       if (x > 170 || x < 0) {
           vel_x = -vel_x;
       }
       if (y > height || y < 0) {
           vel_y = -vel_y;
       }
       }
   }
   
   public void moveWithLockdown() {
           if (Math.random()<0.3) {
               move ();
           }

    }
}
