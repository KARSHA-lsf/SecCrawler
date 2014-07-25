/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package getsecpage;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.cookie.BasicClientCookie;

/**
 *
 * @author kasun
 */
public class GetSecPage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
	String webPageDir = "EDGAR Search Results.html";
	String TRACEcompanyName = "";
	String CRSPcompanyName = "";
	String ticker = "";
	String NAICScode = "";
	Date startDate = new Date(2012 - 1900, 6, 1); //year - the year minus 1900. month - the month between 0-11. date - the day of the month between 1-31.
	Date endDate = new Date(2013 - 1900, 11, 31);//year - the year minus 1900. month - the month between 0-11. date - the day of the month between 1-31.

	String datafile[] = new String[2];
	//data files has following data in the follwing order comma "," seperated
	//TICKER, COMNAM, NAICS.x, ISSUER_NM, NAICS.y
	datafile[0] = "Select_coulms_Daily_Bonds_and_Equities_Jul_Sep_2013.csv";
	datafile[1] = "Select_coulms_Daily_Bonds_and_Equities_Jul_Sep_2012.csv";


	for (int i = 0; i < datafile.length; i++) {
	    File uniqueCatNamesFile = new File(datafile[i]);
	    String line;
	    BufferedReader fileReader = new BufferedReader(new FileReader(uniqueCatNamesFile));
	    //FileWriter outFile;
	    // FileWriter outFileCatNotFound;


	    while ((line = fileReader.readLine()) != null) {
		if (!line.isEmpty()) {
		    String stringList[] = line.split(",");
		   // System.out.println("$$$$$$$$$$$$$$$$$$$$\n");
		    //System.out.println(stringList[0].replace("\"", "").trim()); //TICKER
		    //System.out.println(stringList[1].replace("\"", "").trim()); //COMNAM
//		System.out.println(stringList[2].replace("\"", "").trim()); //ISSUER_NM


		    //when TICKER is NOT avaibale
		    if (stringList[0].replace("\"", "").trim().isEmpty()) {

			ticker = stringList[0].replace("\"", "").trim();
			CRSPcompanyName = stringList[1].replace("\"", "").trim();
			TRACEcompanyName = stringList[3].replace("\"", "").trim();
			NAICScode =  stringList[2].replace("\"", "").trim();
			if(NAICScode.equals("NA")){
			    NAICScode =  stringList[4].replace("\"", "").trim();
			    
			}
			if (!CRSPcompanyName.equals("NA")) {
			    getPageByCompanyName(CRSPcompanyName.replace(" ", "+"), "8-k", "20131231", "exclude", "40");
			    ArrayList<Filing> filingList = SecElements.getFilingData(webPageDir, TRACEcompanyName, CRSPcompanyName, ticker, startDate, endDate, NAICScode);
			   FilingDB.insert(filingList);
			}

		    } //when TICKER is avaibale
		    else if (!stringList[0].replace("\"", "").trim().equals("TICKER")) {
			ticker = stringList[0].replace("\"", "").trim();
			CRSPcompanyName = stringList[1].replace("\"", "").trim();
			TRACEcompanyName = stringList[3].replace("\"", "").trim();
			NAICScode =  stringList[2].replace("\"", "").trim();
			if(NAICScode.equals("NA")){
			    NAICScode =  stringList[4].replace("\"", "").trim();
			    
			}

			getPageByTicker(ticker, "8-k", "20131231", "exclude", "80");
			ArrayList<Filing> filingList = SecElements.getFilingData(webPageDir, TRACEcompanyName, CRSPcompanyName, ticker, startDate, endDate, NAICScode);
			FilingDB.insert(filingList);
		    }

		}
	    }
	}
    }

    public static void getPageByTicker(String ticker, String type, String dateb, String owner, String count) throws IOException {
	HttpClient client = new DefaultHttpClient();
	//System.out.println("http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&Find=Search&CIK=" + ticker + "&type=" + type + "&dateb=" + dateb + "&owner=" + owner + "&count=" + count);
	//to get perticulater filing
	//HttpGet request = new HttpGet("http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&Find=Search&CIK=" + ticker + "&type=" + type + "&dateb=" + dateb + "&owner=" + owner + "&count=" + count);
	//to get all filings
	HttpGet request = new HttpGet("http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&Find=Search&CIK=" + ticker + "&dateb=" + dateb + "&owner=" + owner + "&count=" + count);
	
	HttpResponse response = client.execute(request);

	HttpEntity entity = response.getEntity();

	if (entity != null) {

	    InputStream inputStream = entity.getContent();

	    String filePath = "EDGAR Search Results.html";
	    try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
		int inByte;
		while ((inByte = inputStream.read()) != -1) {
		    fos.write(inByte);
		}
		inputStream.close();
		fos.close();
	    }
	}

    }

    public static void getPageByCompanyName(String companyName, String type, String dateb, String owner, String count) throws IOException {
	HttpClient client = new DefaultHttpClient();
	
	//to get only perticular filing
	//HttpGet request = new HttpGet("http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&company=" + companyName + "&type=" + type + "&dateb=" + dateb + "&owner=" + owner + "&count=" + count);
	//to get all filings
	HttpGet request = new HttpGet("http://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&company=" + companyName + "&dateb=" + dateb + "&owner=" + owner + "&count=" + count);
	
	HttpResponse response = client.execute(request);

	HttpEntity entity = response.getEntity();

	if (entity != null) {

	    InputStream inputStream = entity.getContent();

	    String filePath = "EDGAR Search Results.html";
	    try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
		int inByte;
		while ((inByte = inputStream.read()) != -1) {
		    fos.write(inByte);
		}
		inputStream.close();
		fos.close();
	    }
	}

    }
}
