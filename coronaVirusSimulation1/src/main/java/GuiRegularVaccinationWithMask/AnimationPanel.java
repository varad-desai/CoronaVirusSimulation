package GuiRegularVaccinationWithMask;
import GuiRegularWithMask.*;
import Model.Person;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 *
 * @author varad
 * Story: Initially 1 person is introduced to the population who is infected 
 * with corona virus. Once 10% of the population gets infected we enforce mask 
 * wearing on the population. 
 * Before enforcement of mask wearing, every person is 100% probable of getting 
 * infected if he comes close to the infected person
 * Once mask wearing begins every person is 3% probable of getting infected.
 */
public class AnimationPanel extends JPanel implements ActionListener {
    private Timer tm = new Timer(100, this); // timer for animation
    private int population = 1000;
    private int threshold_for_mask = (population*10)/100;
    private int threshold_for_vaccination = (population*10)/100;
    private boolean mask_wearing_begins = false;
    private Person[] p = new Person[population]; // array of person 
    private int circle_size = 10;
    private int infect_distance = 10; // how close 2 people can be to get infected
    private int height = 600; // screen height
    private int width = 800; // screen width
   
    private Random random = new Random();
    private int steps = 0;
    
    

   
    public AnimationPanel(int h, int w) {
        width = w;
        height = h;
        setPreferredSize(new Dimension(width, height));
        // populate the Person array with randomly placed people
        for(int i=0;i<p.length;i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            p[i] = new Person(x, y);
        }
        // set Patient 0- initially this is the only person infected
        p[0].infected = 1;
        
        tm.start();
    }
   
    public void actionPerformed(ActionEvent e) {
        // at each step in the animation, move all the Person objects
        //System.out.println(p.length);
        for(int i=0;i<p.length;i++) {
       	 if (!p[i].died) {
    		 p[i].move();
    	 }
            p[i].checkForImmunity();
        }
        
        int no_of_infected = calculate_no_of_infected();
        // check to see if any of the people are close enough to infect someone
        steps++;
        if(no_of_infected > threshold_for_mask){
            mask_wearing_begins = true;
            if(steps%10 == 0){
                vaccinate_after_threshold();
            }
        }
        handleCollisions(mask_wearing_begins);
      
        double calculated_r_factor = calculate_r_factor();
        System.out.println("R Factor: "+calculated_r_factor+
            " Infected: "+no_of_infected+
            " Immune: "+calculate_no_of_immune()+
            " Susceptible: "+calculate_no_of_susceptible()+
            " Deaths: "+calculate_no_of_deaths()+
            " Population: "+p.length
            );
        repaint();
    }
   
    public double calculate_r_factor(){
        double r_factor = 0.0;
        int count_infection_spreaders = 0;
        int sum = 0;
        for(int i=0; i<p.length; i++){
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
        for(int i=0; i<p.length; i++){
            if(p[i].infected > 0){
                no_of_infected++;
            }
        }
        return no_of_infected;
    }
   
    public int calculate_no_of_immune(){
        int no_of_immune = 0;
        for(int i=0; i<p.length; i++){
            if(p[i].immune) no_of_immune++;
        }
        int deaths = calculate_no_of_deaths();
        return no_of_immune - deaths;
    }
   
    public int calculate_no_of_susceptible(){
        int no_of_susceptible = 0;
        for(int i=0; i<p.length; i++){
            if(p[i].infected == 0) no_of_susceptible++;
        }
        return no_of_susceptible;
    }
   
    public int calculate_no_of_deaths() {
        int no_of_deaths = 0;
        for(int i=0; i<p.length; i++){
            if(p[i].died) no_of_deaths++;
        }
        return no_of_deaths;
    }
    
    public void vaccinate_after_threshold(){
        // randomy vaccinate 10 people
        int count = 0;
        while(count!=10){
            int random_person = random.nextInt(population);
            if(p[random_person].vaccinated == false){
                p[random_person].vaccinated = true;
                count++;
            }
        }
    }
   
    public void handleCollisions(boolean mask_wearing_begins) {
        // compare all points with each other
        for(int i=0;i<p.length;i++) {
            for(int j=i+1;j<p.length;j++) {
                int deltax = p[i].x - p[j].x;
                int deltay = p[i].y - p[j].y;
                double dist = Math.sqrt(deltax*deltax+deltay*deltay);
                boolean allowed_to_get_infected = true;
                int random_number = random.nextInt(100);
                if(mask_wearing_begins){
                    allowed_to_get_infected = 
                            random_number >= 1 && random_number <= 3;
                }
                // if distance between 2 person is smaller than infect_distance
                // and 1 of the 2 person is infected and the other one is not immune
                /// the other person will get infected
                if (dist < infect_distance) {
                    // implementing 10% probability of getting infected
                    // showcasing wearing of masks by both the person
                    // actual probability according to WHO is 3%
                    //https://www.livescience.com/face-masks-eye-protection-covid-19-prevention.html
                    if (p[i].infected > 0 
                            && !p[i].immune 
                            && !p[j].immune 
                            && !p[i].died 
                            && p[j].infected == 0
                            && !p[i].vaccinated 
                        ) {
                        if(allowed_to_get_infected){
                            p[j].infected++;
                            p[i].no_of_person_infected++;
                        }
                    }
                    if (p[j].infected > 0 
                            && !p[j].immune 
                            && !p[i].immune 
                            && !p[j].died 
                            && p[i].infected == 0
                            && !p[j].vaccinated
                        ) {
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
        for(int i=0;i<p.length;i++) {
            if (p[i].infected > 0 && !p[i].immune) {
                g.setColor(Color.red);
            } else if (p[i].died) {
           	 g.setColor(Color.black);
            } else if(p[i].vaccinated){
                g.setColor(Color.orange);
            } else if (p[i].immune) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.blue);
            }
            g.fillOval(p[i].x, p[i].y, circle_size, circle_size);
        }
    }  
}
