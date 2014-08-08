/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import getsecpage.Filing;
import getsecpage.FilingDB;
import java.util.ArrayList;

/**
 *
 * @author kasun
 */
public class FindRating {

    public static void main(String[] args) {

	ArrayList<Rating> ratingList = RatingDB.getAllRating();
//	for (int i = 0; i < ratingList.size(); i++) {
//	    System.out.println(ratingList.get(i).getItem().trim());
//	}
//	
	ArrayList<Filing> filingList = FilingDB.getFiling("8-k%");
	for (int i = 0; i < filingList.size(); i++) {
	    ArrayList<FilingRating> filingRatingList = new ArrayList<>();
	    System.out.println("fifling id "+filingList.get(i).getFilingID());
	    	for (int j = 0; j < ratingList.size(); j++) {
	    //System.out.println(ratingList.get(j).getItem().trim());
	    if(filingList.get(i).getFilingDescription().contains(ratingList.get(j).getItem().trim())){
		FilingRating fr = new FilingRating();
		fr.setFilingID(filingList.get(i).getFilingID());
		fr.setItem(ratingList.get(j).getItem().trim());
		fr.setScore(ratingList.get(j).getRating());
		filingRatingList.add(fr);
		System.out.println(ratingList.get(j).getItem().trim());
	    }
	}
	     FilingRatingDB.insert(filingRatingList);
	}


    }

    public static ArrayList<Filing> getFilingDesc() {
	ArrayList<Filing> filingList = FilingDB.getFiling("8-k%");

	return filingList;
    }
}
