/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import getsecpage.ConnectionPool;
import getsecpage.DBUtil;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author kasun
 */
public class FilingRatingDB {
    
    public static void insert(ArrayList<FilingRating> filingRatingList) {
	
	ConnectionPool pool = ConnectionPool.getInstance();
	Connection connection = pool.getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;


	//This method adds a new record to the Filing table in the database
	String query =
		"INSERT INTO filing_ratings (filingID, item ) "
		+ "VALUES (?, ?)";
	try {
	    int count = 0;
	    if (filingRatingList != null) {
		for (int i = 0; i < filingRatingList.size(); i++) {

			count++;
			ps = connection.prepareStatement(query);
			ps.setInt(1, filingRatingList.get(i).getFilingID());
			ps.setString(2, filingRatingList.get(i).getItem());
			
			ps.executeUpdate();
		    
		}
	    }

	    //System.out.println(count +" rows Inserted");
	} catch (SQLException e) {
	    e.printStackTrace();
	    //return 0;
	} finally {
	    DBUtil.closeResultSet(rs);
	    DBUtil.closePreparedStatement(ps);
	    pool.freeConnection(connection);
	}
	
    }
    
}
