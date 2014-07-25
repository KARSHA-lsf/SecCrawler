/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package getsecpage;

import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author kasun
 */
public class SecElements {

    public static ArrayList<Filing> getFilingData(String WebPageDir, String TRACEcompanyName, String CRSPcompanyName, String ticker, Date startDate, Date endDate, String NAICScode) {
	try {
	    String filingType = "";
	    String filingURL = "";
	    String filingDesc = "";
	    String filingDate = "";
	    String file_film_Number = "";
	    String baseURL = "http://www.sec.gov";
	    String companyName = "";
	    //String companySic = "";
	    String CIK = "";
	    ArrayList<Filing> fillingList = new ArrayList<Filing>();

	    File input = new File(WebPageDir);
	    Document doc = Jsoup.parse(input, "UTF-8", baseURL);

	    //get company name

	    Elements CompanyNmElements = doc.getElementsByClass("companyName");
	    if (!CompanyNmElements.isEmpty()) {
		companyName = CompanyNmElements.get(0).ownText();
		companyName = companyName.substring(0, companyName.length() - 2).trim();//there are two extra charaters appear at end of string "#:"
		System.out.println(companyName);

		//find SIC value
		//Elements CompanySicElements = doc.getElementsByClass("identInfo");
		//if (!CompanySicElements.isEmpty()) {
		  //  Elements CompanySicChildElements = CompanySicElements.get(0).getElementsByTag("a");
		  //  companySic = CompanySicChildElements.get(0).ownText();
		  //  System.out.println(companySic);
		    //find the CIK value

		    Elements CIKelements = doc.getElementsByTag("input");
		    if (!CIKelements.isEmpty()) {
			Elements CIKelementChild = CIKelements.get(1).getElementsByAttributeValue("name", "CIK"); // 2nd index element has the value for CIK
			CIK = CIKelementChild.get(0).val();
			System.out.println(CIK);

			//filling data is on a table under element "seriesDiv"
			//if(doc.hasAttr("seriesDiv")){
			Element content = doc.getElementById("seriesDiv");

			Elements links = content.getElementsByTag("tr"); //get the table rows 
			for (Element link : links) {
			    if (link.hasText()) {

				//get table elements in each row
				Elements sublinks = link.getElementsByTag("td");
				if (!sublinks.isEmpty()) {

				    Filing newFiling = new Filing();
				    filingDate = sublinks.get(3).text(); //index 3 contain date of the filling
				    if (isDateWithinPeriod(startDate, endDate, filingDate)) {
					//System.out.println("-------------------\n");
					newFiling.setFilingType(sublinks.get(0).text());
					newFiling.setFilingURL(baseURL + sublinks.get(1).getElementsByTag("a").get(0).attr("href"));
					newFiling.setFilingDate(new SimpleDateFormat("yyyy-MM-dd").parse(filingDate.trim()));
					newFiling.setSECcompanyName(companyName);
					newFiling.setCIK(CIK);
					newFiling.setNAICScode(NAICScode);
					newFiling.setCRSPcompanyName(CRSPcompanyName);
					newFiling.setTRACEcompanyName(TRACEcompanyName);
					//when TICKER is not empty
					if (!ticker.isEmpty()) {
					    newFiling.setTICKER(ticker);
					} //when TICKER is empty
					else {
					    newFiling.setTICKER("NA");
					}

					newFiling.setFlag(isFlagTrue(CRSPcompanyName, TRACEcompanyName, companyName));
					//filing Description
					newFiling.setFilingDescription(sublinks.get(2).text());

					//file_film_Number
					newFiling.setFilingFileFilmNo(sublinks.get(4).text());
					// System.out.println(filingType + "\n" + filingURL + "\n" + filingDesc + "\n" + filingDate + "\n" + file_film_Number);
				    }

				    fillingList.add(newFiling);
				}
			    }
			}
		    }
		//}

	    }
	    return fillingList;
	} catch (ParseException ex) {
	    System.out.println("ParseException");
	    return null;
	    // Logger.getLogger(SecElements.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    System.out.println("IOException");
	    // Logger.getLogger(SecElements.class.getName()).log(Level.SEVERE, null, ex);
	    return null;
	}
    }

    private static boolean isDateWithinPeriod(Date startdate, Date endDate, String filingDate) throws ParseException {
	boolean status = false;
	//filing date is in the following format 2007-06-15
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	Date date = formatter.parse(filingDate.trim());
	if (date.before(endDate) && date.after(startdate)) {
	    status = true;

	}
	//System.out.println(status);
	//System.out.println(startdate);
	//System.out.println(date);
	//System.out.println(endDate);
	return status;
    }

    private static boolean isFlagTrue(String CRSPcompanyName, String TRACEcompanyName, String companyName) {
	boolean status = true;
	//CRSPcompanyName is not empty
	if (!CRSPcompanyName.isEmpty()) {

	    //TRACEcompanyName is not empty
	    if (!TRACEcompanyName.isEmpty()) {

		//if TRACEcompanyName OR CRSPcompanyName is matched with SECcompany name
		if (TRACEcompanyName.equals(companyName) || CRSPcompanyName.equals(companyName)) {
		    status = false;
		}
	    } //TRACEcompanyName is empty
	    else {
		//if CRSPcompanyName is matched with SECcompany name
		if (CRSPcompanyName.equals(companyName)) {
		    status = false;
		}
	    }
	} //CRSPcompanyName is empty
	else {
	    //CRSPcompanyName is not empty and matches with SECcompany name
	    if (!TRACEcompanyName.isEmpty() && TRACEcompanyName.equals(companyName)) {
		status = false;
	    }
	}
	return status;
    }
}
