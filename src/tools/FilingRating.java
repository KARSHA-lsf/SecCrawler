/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author kasun
 */
public class FilingRating {
       private String item;
       private int filingID;
       private int score;

    public int getFilingID() {
	return filingID;
    }

    public void setFilingID(int filingID) {
	this.filingID = filingID;
    }
    public String getItem() {
	return item;
    }

    public void setItem(String item) {
	this.item = item;
    }
    
        public int getScore() {
	return score;
    }

    public void setScore(int score) {
	this.score = score;
    }
    
}
