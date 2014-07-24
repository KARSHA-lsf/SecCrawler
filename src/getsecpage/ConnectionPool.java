/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package getsecpage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
//import javax.naming.InitialContext;
//import javax.sql.DataSource;
//import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSource;
//import org.apache.commons.pool.KeyedObjectPoolFactory;
//import org.apache.taglibs.standard.tag.rt.core.


/*
 * This class creates connection to the the database
 */
public class ConnectionPool {

    private static ConnectionPool pool = null;
    private static BasicDataSource dataSource = null;

    public synchronized static ConnectionPool getInstance() {
	if (pool == null) {
	    pool = new ConnectionPool();
	}
	return pool;
    }

    private ConnectionPool() {
	try {



	    String url = null;
	    String username = null;
	    String password = null;
	    String line;
	    
	    //read database details

	    BufferedReader fileReader = new BufferedReader(new FileReader(new File("database_local_server")));

	    while ((line = fileReader.readLine()) != null) {
		if (!line.isEmpty()) {
		    String stringList[] = line.split("=");
		    switch (stringList[0].trim()) {
		    	case "url":
			    url= stringList[1].trim();
			    break;
		    	case "username":
			    username=stringList[1].trim();
			    break;
		    	case "password":
			    password=stringList[1].trim();
			    break;
		    }
		}
	    }


	    dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    dataSource.setUrl(url);
	    dataSource.setUsername(username);
	    dataSource.setPassword(password);

//            dataSource.setValidationQuery("SELECT 1");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public Connection getConnection() {
	try {
	    return dataSource.getConnection();
	} catch (SQLException sqle) {
	    sqle.printStackTrace();
	    return null;
	}
    }

    public void freeConnection(Connection c) {
	try {
	    c.close();
	} catch (SQLException sqle) {
	    sqle.printStackTrace();
	}
    }
}
