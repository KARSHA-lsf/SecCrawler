/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package getsecpage;


import java.sql.*;
import java.util.ArrayList;
import getsecpage.Filing;
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
                "INSERT IGNORE INTO filing (filingType, filingURL, filingDate, filingDescription, filingFileFilmNo, CIK, TICKER, SECcompanyName, TRACEcompanyName, CRSPcompanyName, flag) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
	    int count=0;
	    for(int i=0; i< filingList.size();i++){
		if(filingList.get(i).getSECcompanyName() != null){
		    count++;
            ps = connection.prepareStatement(query);
            ps.setString(1, filingList.get(i).getFilingType());
            ps.setString(2, filingList.get(i).getFilingURL());
            ps.setDate(3, new java.sql.Date(filingList.get(i).getFilingDate().getTime()));
	    ps.setString(4, filingList.get(i).getFilingDescription());
	    ps.setString(5, filingList.get(i).getFilingFileFilmNo());
	    ps.setString(6, filingList.get(i).getCIK());
	    ps.setString(7, filingList.get(i).getTICKER());
	    ps.setString(8, filingList.get(i).getSECcompanyName());
	    ps.setString(9, filingList.get(i).getTRACEcompanyName());
	    ps.setString(10, filingList.get(i).getCRSPcompanyName());
	    ps.setBoolean(11, filingList.get(i).getFlag());

            ps.executeUpdate();
		}
	    }
	    
	    System.out.println(count +" rows Inserted");
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
