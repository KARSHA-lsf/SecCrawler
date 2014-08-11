/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package getsecpage;

import java.sql.*;
import java.util.ArrayList;
import getsecpage.Filing;
import java.util.HashMap;

/**
 *
 * @author kasun
 */
public class FilingDB {

    public static void insert(ArrayList<Filing> filingList) {
	
	ConnectionPool pool = ConnectionPool.getInstance();
	Connection connection = pool.getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;


	//This method adds a new record to the Filing table in the database
	String query =
		"INSERT IGNORE INTO filing (filingType, filingURL, filingDate, filingDescription, filingFileFilmNo, CIK, NAICScode, TICKER, SECcompanyName, TRACEcompanyName, CRSPcompanyName, flag) "
		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	try {
	    int count = 0;
	    if (filingList != null) {
		for (int i = 0; i < filingList.size(); i++) {
		    if (filingList.get(i).getSECcompanyName() != null) {
			count++;
			ps = connection.prepareStatement(query);
			ps.setString(1, filingList.get(i).getFilingType());
			ps.setString(2, filingList.get(i).getFilingURL());
			ps.setDate(3, new java.sql.Date(filingList.get(i).getFilingDate().getTime()));
			ps.setString(4, filingList.get(i).getFilingDescription());
			ps.setString(5, filingList.get(i).getFilingFileFilmNo());
			ps.setString(6, filingList.get(i).getCIK());
			ps.setString(7, filingList.get(i).getNAICScode());
			ps.setString(8, filingList.get(i).getTICKER());
			ps.setString(9, filingList.get(i).getSECcompanyName());
			ps.setString(10, filingList.get(i).getTRACEcompanyName());
			ps.setString(11, filingList.get(i).getCRSPcompanyName());
			ps.setBoolean(12, filingList.get(i).getFlag());
			
			ps.executeUpdate();
		    }
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

    public static ArrayList<Filing> getFiling(String filingType) {
	ArrayList<Filing> filingList = new ArrayList<Filing>();
	
	
	
	ConnectionPool pool = ConnectionPool.getInstance();
	Connection connection = pool.getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;


	//This method adds a new record to the Filing table in the database
	String query =
		"SELECT filingID, filingDescription from filing where filingType LIKE ?";
	try {
	    
	    ps = connection.prepareStatement(query);
	    ps.setString(1, filingType);
	    rs = ps.executeQuery();
	    
	    while (rs.next()) {
		Filing d = new Filing();
		d.setFilingID(Integer.parseInt(rs.getString("filingID")));
		d.setFilingDescription(rs.getString("filingDescription"));
		// d.setFiboDefinition(rs.getString("TermDefinition").replaceAll("[\";\',.%$]()", " ").trim());
		filingList.add(d);
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
	return filingList;
    }
    
        public static ArrayList<Filing> getFilingDates(String filingType) {
	ArrayList<Filing> filingList = new ArrayList<Filing>();
	
	
	
	ConnectionPool pool = ConnectionPool.getInstance();
	Connection connection = pool.getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;


	//This method adds a new record to the Filing table in the database
	String query =
		"SELECT filingDate  from filing where filingType LIKE ? AND group by filingDate" ;
	try {
	    
	    ps = connection.prepareStatement(query);
	    ps.setString(1, filingType);
	    rs = ps.executeQuery();
	    
	    while (rs.next()) {
		Filing d = new Filing();
		//d.setFilingID(Integer.parseInt(rs.getString("filingID")));
		d.setFilingDate(rs.getDate("filingDescription"));
		// d.setFiboDefinition(rs.getString("TermDefinition").replaceAll("[\";\',.%$]()", " ").trim());
		filingList.add(d);
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
	return filingList;
    }
	
	    public static HashMap<String, ArrayList<Integer>> getFilingRatingJoin() {
	
	
	HashMap<String, ArrayList<Integer>> filingRatingMap= new HashMap<String, ArrayList<Integer>>();
	
	ConnectionPool pool = ConnectionPool.getInstance();
	Connection connection = pool.getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;


	//This method adds a new record to the Filing table in the database
	String query =
		"SELECT  `filingDate` ,  `filing_ratings`.`filingID` ,  `score` FROM  `filing` JOIN  `filing_ratings` ON  `filing_ratings`.`filingID` = filing.`filingID`";
	try {
	    
	    ps = connection.prepareStatement(query);
	    //ps.setString(1, filingType);
	    rs = ps.executeQuery();
	    
	    while (rs.next()) {
		String date= rs.getString("filingDate");
		if(filingRatingMap.containsKey(date)){
		    filingRatingMap.get(date).add(rs.getInt("score"));
		}
		else{
		    ArrayList<Integer> newList = new  ArrayList<>();
		    newList.add(rs.getInt("score"));
		    filingRatingMap.put(date, newList);
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
	return filingRatingMap;
    }
}


