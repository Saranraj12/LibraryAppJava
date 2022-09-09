//$Id$
package model;

import java.sql.Date;

public class Report {
	private int reportId;
	private int readerId;
	private int ISBN;
	private Date reserveDate;
	private Date returnDate;
	private boolean isReturned;
	
	public Report() {
		super();
	}
	
	public Report(int readerId, int iSBN, Date reserveDate, Date returnDate, boolean isReturned) {
		super();
		this.readerId = readerId;
		ISBN = iSBN;
		this.reserveDate = reserveDate;
		this.returnDate = returnDate;
		this.isReturned = isReturned;
	}
	
	public Report(int reportId, int readerId, int iSBN, Date reserveDate, Date returnDate, boolean isReturned) {
		super();
		this.reportId = reportId;
		this.readerId = readerId;
		ISBN = iSBN;
		this.reserveDate = reserveDate;
		this.returnDate = returnDate;
		this.isReturned = isReturned;
	}
	
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public int getReaderId() {
		return readerId;
	}
	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}
	public int getISBN() {
		return ISBN;
	}
	public void setISBN(int iSBN) {
		this.ISBN = iSBN;
	}
	public Date getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public boolean isReturned() {
		return isReturned;
	}

	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}
	
	
}
