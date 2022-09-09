//$Id$
package model;

public class Book {
	private int ISBN;
	private String bookTitle;
	private int bookPrice;
	private String category;
	private String edition;
	private String authorName;
	private int publisherId;
	private String issueInfo;
	private int publishedYear;
	private String listOfReaders;
	public Book() {
		super();
	}
	public Book(String bookTitle, int bookPrice, String category, String edition, String authorName, int publisherId, String issueInfo, int publishedYear, String listOfReaders) {
		super();
		this.bookTitle = bookTitle;
		this.bookPrice = bookPrice;
		this.category = category;
		this.edition = edition;
		this.authorName = authorName;
		this.publisherId = publisherId;
		this.issueInfo = issueInfo;
		this.publishedYear = publishedYear;
		this.listOfReaders = listOfReaders;
	}
	public Book(int iSBN, String bookTitle, int bookPrice, String category, String edition, String authorName, int publisherId, String issueInfo, int publishedYear, String listOfReaders) {
		super();
		ISBN = iSBN;
		this.bookTitle = bookTitle;
		this.bookPrice = bookPrice;
		this.category = category;
		this.edition = edition;
		this.authorName = authorName;
		this.publisherId = publisherId;
		this.issueInfo = issueInfo;
		this.publishedYear = publishedYear;
		this.listOfReaders = listOfReaders;
	}
	public int getISBN() {
		return ISBN;
	}
	public void setISBN(int iSBN) {
		ISBN = iSBN;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public int getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public int getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
	public String getIssueInfo() {
		return issueInfo;
	}
	public void setIssueInfo(String issueInfo) {
		this.issueInfo = issueInfo;
	}
	public int getPublishedYear() {
		return publishedYear;
	}
	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}
	public String getListOfReaders() {
		return listOfReaders;
	}
	public void setListOfReaders(String listOfReaders) {
		this.listOfReaders = listOfReaders;
	}
	
	
}
