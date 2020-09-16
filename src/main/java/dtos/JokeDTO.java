/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Joke;

/**
 *
 * @author Jonas
 */
public class JokeDTO {
    
    private int id;
    private String theJoke;
    private String type;
        
    public JokeDTO(int id, String name, String studentID) {
        this.id = id;
        this.theJoke = theJoke;
        this.type = type;
    }
    
    public JokeDTO(Joke joke) {
        this.id = joke.getId();
        this.theJoke = joke.getTheJoke();
        this.type = joke.getType();
    }

    public String getTheJoke() {
        return theJoke;
    }

    public void setTheJoke(String theJoke) {
        this.theJoke = theJoke;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
       
}
