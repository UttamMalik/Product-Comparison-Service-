package ie.gmit.proskills.Websites;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.table.DefaultTableModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ie.gmit.proskills.object.StoreInfo;
/**
 * This class is responsible for parsing and extracing data from the Newegg.ie website. <br>
 * From here the DOM is broken down into elements which we can loop through and extract data from <br>
 * 
 * @author Cian Gannon
 * @author Danielis Joni�kis
 * @author Eddie Eldridge
 */
public class Newegg {

	/**
	 * The main component.<br>
	 * 
	 * 
	 * @param searchTerm
	 *            the search term the user has entered
	 * @param dtm
	 *            the name of the TableModel
	 */	
	public static void run(String searchTerm, DefaultTableModel dtm) throws IOException
	{
		DecimalFormat df = new DecimalFormat("#.00");

		// Variables
		String name = null;
		String priceString=null;
		String urlPart1="https://www.flipkart.com/search?q=";
		String urlPart2="&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off";
		String completeUrl;
		double postage = 7.50;
		double total =0.00;
		double price=0.00;
		int totalQueries = 0;
		double allPrices = 0;
		
		// Replace any spaces with a +
		searchTerm = searchTerm.replaceAll(" ", "+");
		
		// Complete the url with search terms added 
		completeUrl = urlPart1 + searchTerm + urlPart2;
		
		// Tell the user they're request is being sent to the user
		System.out.println("\nSending request..." + "\"" + completeUrl + "\"");
		
		// Create a document of the HTML of the webpage we are searching (In our case ebay)
		Document doc =  Jsoup.connect(completeUrl)
			      .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
			      .referrer("http://www.google.com")
			      .get();
	
		// Get all HTML of this class
		Elements els  = doc.getElementsByClass("t-0M7P _2doH3V");
				
		// For every element of the element we assigned above
		for(Element el : els)
		{
			totalQueries++;
				
			name = (el.getElementsByClass("_3wU53n").text()).replaceAll("Name: ", "");					
												
		    priceString =  ((el.getElementsByClass("_1vC4OE _2rQ-NK").text().replaceAll("[^0-9.]", "")));
			//String newString = priceString.replaceAll(",",".");
			//String subbedPriceString = newString.substring(0, newString.length() - 1);
			price = Double.parseDouble(priceString);				
			System.out.println("price");
			total = price + postage;
			
			allPrices += total;
			totalQueries++;
			
			// Add the found stuff to our list
			dtm.addRow(new Object [] { name, "�" + df.format(price), "�" + df.format(postage), "�" + df.format(total), "Newegg"});
		}
		
		StoreInfo.setNeweggAvg(df.format(allPrices/totalQueries));
		System.out.println(allPrices);
	}
	
}
