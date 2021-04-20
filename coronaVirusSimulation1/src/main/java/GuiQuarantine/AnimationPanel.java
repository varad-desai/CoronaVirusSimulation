/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiQuarantine;
import GuiRegular.*;
import Model.Person;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;

/**
 *
 * @author varad
 */
public class AnimationPanel extends JPanel implements ActionListener {
   
    private Timer tm = new Timer(100, this); // timer for animation
    private int population = 1000;
    private Person[] p = new Person[population];
    private int circle_size = 10;
    private int infect_distance = 10;// how close 2 people can be to get infected
    private int height = 600; // screen height
    private int width = 800; // screen width
    private  ChartGuiQuarantine chartGuiQuarantine = new ChartGuiQuarantine();
    
    // Line coordinates for quarantine
    private int x1 = 200;
    private int y1 = height;
    private int x2 = 200;
    private int y2 = 0;
 
    private Random random = new Random();
    
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
//            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            int x = random.nextInt(width - x1) + x1;
            int y = random.nextInt(height);
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
            if(p[i].counter_for_quarantine > 25 && !p[i].quarantine) {
                move_to_quarantine(i);
            }
            if (!p[i].died && !p[i].quarantine) {
    		p[i].move_in_real_world();
            }
            if (!p[i].died && p[i].quarantine){
                p[i].move_within_quarantine_boundaries();
            }
            if(!p[i].died && p[i].immune && p[i].quarantine){
                move_to_real_world(i);
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
              " Quarantine: "+calculate_no_of_quarantine ()+
              " Deaths: "+calculate_no_of_deaths()+
              " Population: "+population
              );
      chartGuiQuarantine.showChartWithQuarantine(calculated_r_factor,calculate_no_of_infected(),
               calculate_no_of_immune(),
               calculate_no_of_susceptible(),calculate_no_of_quarantine(), calculate_no_of_deaths(), population);
      repaint();
   }

    private int calculate_no_of_quarantine() {
        int no_of_quarantine = 0;
        for(int i=0; i< population; i++){
            if(p[i].quarantine){
                no_of_quarantine++;
            }
        }
        return no_of_quarantine;
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
       if (count_infection_spreaders > 0) {
    	   r_factor = (double)sum/count_infection_spreaders;
       }
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
   
    public void move_to_quarantine(int person_index){
        p[person_index].quarantine = true; 
        p[person_index].x = random.nextInt(x1-30);
    }
    
    public void move_to_real_world(int person_index){
        p[person_index].quarantine = false;
        p[person_index].counter_for_quarantine = 0;
        p[person_index].x = random.nextInt(width - (x1+30))+(x1+30);
    }
   
   public void handleCollisions() {
      // compare each point to all the other points
      for(int i=0;i<population;i++) {
         for(int j=i+1;j<population;j++) {
            int deltax = p[i].x - p[j].x;
            int deltay = p[i].y - p[j].y;
            double dist = Math.sqrt(deltax*deltax+deltay*deltay);
            // if the distance between 2 points is small enough, and one of
            // the Persons is infected, then infect the other Person
            if (dist < infect_distance) {
               if (p[i].infected > 0 && !p[i].immune && !p[j].immune && !p[i].died && p[j].infected == 0) {
                  p[j].infected++;
                  p[i].no_of_person_infected++;
               }
               if (p[j].infected > 0 && !p[j].immune && !p[i].immune && !p[j].died && p[i].infected == 0) {
                  p[i].infected++;
                  p[j].no_of_person_infected++;
               }
               
            }
         }
      }      
   }
   
    public void paintComponent(Graphics g) {
        // each time we paint the screen, set the color based on 
        // who is infected and who isn't, and who has recovered
        super.paintComponent(g);
        g.drawLine(200,600, 200, 0);
        for(int i=0;i<population;i++) {
       	 	if (p[i].died) {
       		 	g.setColor(Color.black);
       	 	}
       	 	else if (p[i].infected > 0 && !p[i].immune) {
                g.setColor(Color.red);
                p[i].counter_for_quarantine++;
            } else if (p[i].immune) {
                g.setColor(Color.green);
                
            } else {
                g.setColor(Color.blue);
            }
            g.fillOval(p[i].x, p[i].y, circle_size, circle_size);
        }
   }
   
   
}
