/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiSocialDistanceWithQuarantine;
import Model.Person;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class AnimationPanel extends JPanel implements ActionListener {
    // Timer is needed for animation
    private Timer TM = new Timer(100, this);
    // the number of people in the simulation
    private int POPULATION = 1000;
    // an array of Person objects
    private Person[] p = new Person[POPULATION];
    // how large to draw each Person
    private int CIRCLE_SIZE = 10;
    private int infectDistance = 10;
    // how close two Persons need to be in order to infect one another
    private int DISTANCE_FOR_INFECTION = 10;
    // height and width of the screen
    private int HEIGHT = 600;
    private int WIDTH = 800;
    // random number generator to randomly place the people initially
    private Random gen = new Random();


    //    System.out.println(p.length);
    int moveablePopulation =(POPULATION * 50)/100;
//    System.out.println(moveablePopulation);


    public AnimationPanel(int h, int w) {
        WIDTH = w;
        HEIGHT = h;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // populate the Person array with randomly placed people
        for(int i=0;i<p.length;i++) {
            int x = gen.nextInt(WIDTH);
            int y = gen.nextInt(HEIGHT);
            p[i] = new Person(x, y);
        }
        // set Patient 0- initially this is the only person infected
        p[0].infected = 1;
        // start the timer!
        TM.start();
    }

    public void actionPerformed(ActionEvent e) {
        // at each step in the animation, move 10% the Person objects

        for(int i=0; i<moveablePopulation; i++) {
            p[i].moveWithSocialDistance();
        }
        for(int i=0; i<p.length; i++){
            p[i].checkForImmunity();
        }
        // check to see if any of the people are close enough to infect someone
        handle_collisions();
        double calculated_r_factor = calculate_r_factor();
        System.out.println("R Factor: "+calculated_r_factor+
                " Infected: "+calculate_no_of_infected()+
                " Immune: "+calculate_no_of_immune()+
                " Susceptible: "+calculate_no_of_susceptible()+
                " Population: "+p.length
        );
        repaint();
    }

    public void handle_collisions() {
        // compare each point to all the other points
        for(int i=0;i<p.length;i++) {
            for(int j=i+1;j<p.length;j++) {
                int deltax = p[i].x - p[j].x;
                int deltay = p[i].y - p[j].y;
                double dist = Math.sqrt(deltax*deltax+deltay*deltay);
                // if the distance between 2 points is small enough, and one of
                // the Persons is infected, then infect the other Person
                if (dist < infectDistance) {
                    if (p[i].infected > 0 && !p[i].immune && !p[j].immune && p[j].infected == 0) {
                        p[j].infected++;
                        p[i].no_of_person_infected++;
                    }
                    if (p[j].infected > 0 && !p[j].immune && !p[i].immune && p[i].infected == 0) {
                        p[i].infected++;
                        p[j].no_of_person_infected++;
                    }

                }
            }
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        // each time we paint the screen, set the color based on
        // who is infected and who isn't, and who has recovered
        super.paintComponent(g);
        for(int i=0;i<p.length;i++) {
            if (p[i].infected > 0 && p[i].immune == false) {
                g.setColor(Color.red);
            } else if (p[i].immune) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.black);
            }
            g.fillOval(p[i].x, p[i].y, CIRCLE_SIZE, CIRCLE_SIZE);
        }
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
        return no_of_immune;
    }

    public int calculate_no_of_susceptible(){
        int no_of_susceptible = 0;
        for(int i=0; i<p.length; i++){
            if(p[i].infected == 0) no_of_susceptible++;
        }
        return no_of_susceptible;
    }

}
