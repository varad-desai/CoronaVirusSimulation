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
   public boolean vaccinated = false;
   public boolean died = false;
   public int infected = 0; // when infected, this will be positive and increase until the
                            // infectedDuration is reached, at which point the person
                            // will be immune
   public int no_of_person_infected = 0; // number of other persson infected by this person over the course of infection
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
   
    public void checkForImmunity(){
        // each time they move, if already infected they get closer to immunity
        if(infected > 0) {
            infected++;
            // Comment this below line to stop vaccination.
        }
        if(infected > infected_duration/2 && infected < infected_duration){
            vaccinate();
        }
        // check to see if they've reached the immunity threshold
        if(infected > infected_duration) {
            //infected = 0;
            immune = true;
        }

    }
   
    public void vaccinate(){
        int random_number = random.nextInt(4);
        if(random_number == 1){
            vaccinated = true;
        }
    }    
   
   public void move() {
      x += vel_x;
      y += vel_y;
      // if they hit the walls, bounce
      if (x > width || x < 0) {
         vel_x = -vel_x;
      }
      if (y > height || y < 0) {
         vel_y = -vel_y;
      }
   }
}
