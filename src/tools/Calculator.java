/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kasun
 */
public class Calculator {

    public static void main(String[] args) throws IOException {
	FileWriter sumfile= new FileWriter("sum.csv", true);
	FileWriter maxfile= new FileWriter("max.csv", true);
	HashMap<String, ArrayList<Integer>> filingRatingMap = getsecpage.FilingDB.getFilingRatingJoin();
	for (Map.Entry entry : filingRatingMap.entrySet()) {
	    int max= max((ArrayList<Integer>)entry.getValue());
	    int sum= sum((ArrayList<Integer>)entry.getValue());
	    sumfile.append((String)entry.getKey() +"," + sum+"\n");
	    maxfile.append((String)entry.getKey() +"," + max+"\n");
	   // System.out.println((String)entry.getKey() +" Max " + max);
	   // System.out.println((String)entry.getKey() +" Sum " + sum);
	}
	sumfile.close();
	maxfile.close();
    }

    public static Integer sum(ArrayList<Integer> list) {
	Integer sum = 0;
	for (Integer i : list) {
	    sum = sum + i;
	}
	return sum;
    }

    public static Integer max(ArrayList<Integer> list) {
	Integer max = 0;
	for (Integer i : list) {
	    if (i > max) {
		max = i;
	    }

	}
	return max;
    }
}
