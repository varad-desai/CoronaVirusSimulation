package GuiCovidTesting;
import Model.Person;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 *
 * @author varad
 */
public class AnimationPanel extends JPanel implements ActionListener {
    // Timer is needed for animation
   private Timer tm = new Timer(100, this);
   // the number of people in the simulation
   private int population = 1000;
   // an array of Person objects
   private Person[] p = new Person[population];
   // how large to draw each Person
   private int circleSize = 10;
   // how close two Persons need to be in order to infect one another
   private int infectDistance = 30;
   // height and width of the screen
   private int height = 600;
   private int width = 800;
   // random number generator to randomly place the people initially
   private Random gen = new Random();
   
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

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
	
	public AnimationPanel(int h, int w) {
      width = w;
      height = h;
      setPreferredSize(new Dimension(width, height));
      // populate the Person array with randomly placed people
      for(int i=0;i<population;i++) {
         int x = gen.nextInt(width);
         int y = gen.nextInt(height);
         p[i] = new Person(x, y);
      }
      // set Patient 0- initially this is the only person infected
      p[0].infected = 1;
      // start the timer!
      tm.start();
   }
   
   public void actionPerformed(ActionEvent e) {
      // at each step in the animation, move all the Person objects
//      System.out.println(p.length);
       for(int i=0;i<population;i++) {
       	p[i].testing();
       	if (!p[i].testResult && !p[i].died) {	
           p[i].move();
       	}
       	else if (p[i].testResult){
       		p[i].moveWithSocialDistance();
       	}
           p[i].checkForImmunity();
       }
      // check to see if any of the people are close enough to infect someone
      handleCollisions();
      
      double calculated_r_factor = calculate_r_factor();
      System.out.println("R Factor: "+calculated_r_factor+
              " Infected: "+calculate_no_of_infected()+
              " Immune: "+calculate_no_of_immune()+
              " Susceptible: "+calculate_no_of_susceptible()+
              " Deaths: "+calculate_no_of_deaths()+
              " Population: "+population
              );
      repaint();
   }
   
   public double calculate_r_factor(){
       double r_factor = 0.0;
       int count_infection_spreaders = 0;
       int sum = 0;
       for(int i=0; i<population; i++){
           if(p[i].no_of_person_infected != 0){
                sum += p[i].no_of_person_infected;
                count_infection_spreaders++;
           }
       }
       r_factor = (double)sum/count_infection_spreaders;
       return r_factor;
   }
   
   public int calculate_no_of_infected(){
       int no_of_infected = 0;
       for(int i=0; i<population; i++){
           if(p[i].infected > 0){
               no_of_infected++;
           }
       }
       return no_of_infected;
   }
   
   public int calculate_no_of_immune(){
       int no_of_immune = 0;
       for(int i=0; i<population; i++){
           if(p[i].immune) no_of_immune++;
       }
       int deaths = calculate_no_of_deaths();
       return no_of_immune - deaths;
   }
   
   public int calculate_no_of_susceptible(){
       int no_of_susceptible = 0;
       for(int i=0; i<population; i++){
           if(p[i].infected == 0) no_of_susceptible++;
       }
       return no_of_susceptible;
   }
   
   public int calculate_no_of_deaths() {
       int no_of_deaths = 0;
       for(int i=0; i<population; i++){
           if(p[i].died) no_of_deaths++;
       }
       return no_of_deaths;
   }
   
   public void handleCollisions() {
      // compare each point to all the other points
      for(int i=0;i<population;i++) {
         for(int j=i+1;j<population;j++) {
            int deltax = p[i].x - p[j].x;
            int deltay = p[i].y - p[j].y;
            double dist = Math.sqrt(deltax*deltax+deltay*deltay);
            // Implements statistic that 40% of cases are asymptomatic
            // People who tested positive are assumed to quarantine for 2 weeks
            // This slows down the spread
            boolean allowed_to_get_infected;
            int random_number = gen.nextInt(100);
            allowed_to_get_infected = random_number >= 1 && random_number <= 1.5;
            // if the distance between 2 points is small enough, and one of
            // the Persons is infected, then infect the other Person
            if (dist < infectDistance) {
               if (p[i].infected > 0 && !p[i].immune && !p[j].immune && !p[i].died && p[j].infected == 0) {
            	   if(allowed_to_get_infected){
                       p[j].infected++;
                       p[i].no_of_person_infected++;
                   }
               }
               if (p[j].infected > 0 && !p[j].immune && !p[i].immune && !p[j].died && p[i].infected == 0) {
                   if(allowed_to_get_infected){
                	   p[i].infected++;
                	   p[j].no_of_person_infected++;
                   }
               }
               
            }
         }
      }      
   }
   public void paintComponent(Graphics g) {
      // each time we paint the screen, set the color based on 
      // who is infected and who isn't, and who has recovered
      super.paintComponent(g);
      for(int i=0;i<population;i++) {
         if (p[i].infected > 0 && !p[i].immune) {
        	 if(p[i].testResult) {
        	 g.setColor(Color.pink);
        	 }
        	 else {
        		 g.setColor(Color.red);
        	 }
         }
         	else if (p[i].died) {
        	g.setColor(Color.black);
         }
         	else if (p[i].immune) {
            g.setColor(Color.green);
         } else {
            g.setColor(Color.blue);
         }
         g.fillOval(p[i].x, p[i].y, circleSize, circleSize);
      }
   }
   
   
}
