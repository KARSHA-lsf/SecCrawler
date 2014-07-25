/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package getsecpage;

import java.util.Date;





/**
 *
 * @author kasun
 */
public class Filing {

    private String filingType;
    private String filingURL;
    private Date filingDate;
    private String CIK;
    private String TICKER;
    private String NAICScode;
    private String filingDescription;
    private String filingFileFilmNo;
    private String SECcompanyName;
    private String TRACEcompanyName;
    private String CRSPcompanyName;
    private boolean flag = false;

    public String getFilingType() {
	return filingType;
    }

    public void setFilingType(String filingType) {
	this.filingType = filingType;
    }

    public String getFilingURL() {
	return filingURL;
    }
    
        public void setFilingDescription(String filingDescription) {
	this.filingDescription = filingDescription;
    }

    public String getFilingDescription() {
	return filingDescription;
    }
    
    public void setFilingFileFilmNo(String filingFileFilmNo) {
	this.filingFileFilmNo = filingFileFilmNo;
    }

    public String getFilingFileFilmNo() {
	return filingFileFilmNo;
    }

    public void setFilingURL(String filingURL) {
	this.filingURL = filingURL;
    }

    public Date getFilingDate() {
	return filingDate;
    }

    public void setFilingDate(Date filingDate) {
	this.filingDate = filingDate;
    }

    public String getCIK() {
	return CIK;
    }

    public void setCIK(String CIK) {
	this.CIK = CIK;
    }
    
        public String getNAICScode() {
	return NAICScode;
    }

    public void setNAICScode(String NAICScode) {
	this.NAICScode = NAICScode;
    }
    
    public String getTICKER() {
	return TICKER;
    }

    public void setTICKER(String TICKER) {
	this.TICKER = TICKER;
    }
    public String getSECcompanyName() {
	return SECcompanyName;
    }

    public void setSECcompanyName(String SECcompanyName) {
	this.SECcompanyName = SECcompanyName;
    }

    public String getTRACEcompanyName() {
	return TRACEcompanyName;
    }

    public void setTRACEcompanyName(String TRACEcompanyName) {
	this.TRACEcompanyName = TRACEcompanyName;
    }

    public String getCRSPcompanyName() {
	return CRSPcompanyName;
    }

    public void setCRSPcompanyName(String CRSPcompanyName) {
	this.CRSPcompanyName = CRSPcompanyName;
    }

    public boolean getFlag() {
	return flag;
    }

    public void setFlag(boolean flag) {
	this.flag = flag;
    }
}
