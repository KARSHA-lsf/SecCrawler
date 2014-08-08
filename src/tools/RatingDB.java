/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import getsecpage.ConnectionPool;
import getsecpage.DBUtil;
import getsecpage.Filing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author kasun
 */
public class RatingDB {
    
        public static ArrayList<Rating> getAllRating() {
	ArrayList<Rating> ratingList = new ArrayList<Rating>();
	
	
	
	ConnectionPool pool = ConnectionPool.getInstance();
	Connection connection = pool.getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;


	//This method adds a new record to the Filing table in the database
	String query =
		"SELECT item, description, rating from rating";
	try {
	    
	    ps = connection.prepareStatement(query);
	    rs = ps.executeQuery();
	    
	    while (rs.next()) {
		Rating r = new Rating();
		r.setRating(Integer.parseInt(rs.getString("rating")));
		r.setItem(rs.getString("item"));
		r.setDescription(rs.getString("description"));
		// d.setFiboDefinition(rs.getString("TermDefinition").replaceAll("[\";\',.%$]()", " ").trim());
		ratingList.add(r);
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
	return ratingList;
    }
    
}
