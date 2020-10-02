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
 * This class is responsible for parsing and extracing data from the Ebay.com website. <br>
 * From here the DOM is broken down into elements which we can loop through and extract data from <br>
 * 
 * @author Cian Gannon
 * @author Danielis Joniškis
 * @author Eddie Eldridge
 */
public class Ebay {
	
	/**
	 * The main component.<br> 
	 * 
	 * 
	 * @param searchTerm
	 *            the search term the user has entered
	 * @param dtm
	 *            the name of the TableModel
	 */	
	public static void main(String searchTerm, DefaultTableModel dtm) throws IOException {

		DecimalFormat df = new DecimalFormat("#.00");
		
		// Variables
		String name = null;
		String price = null;
		String postage = "€ 0.00";
		double total = 0.00;
		int totalQueries = 0;
		double allPrices = 0;
		String url = "https://www.ebay.ie/sch/i.html?_from=R40&_trksid=p2380057.m570.l1313.TR0.TRC0.H0.X";

		// Replace any spaces with a +
		searchTerm = searchTerm.replaceAll(" ", "+");

		// Complete the url with search terms added
		url = url + searchTerm + ".TRS0&_nkw=" + searchTerm;

		// Create a document of the HTML of the webpage we are searching
		Document doc = Jsoup.connect(url)
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
				.timeout(60000).get();

		// Get all html of this class
		Elements els = doc.select("li.sresult.lvresult");

		// For every element of the element we assign above
		for (Element el : els) {
			
			totalQueries++;

			name = (el.getElementsByClass("lvtitle").text()).replaceAll("Name: ", "");

			price = "€ " + (el.getElementsByClass("lvprice prc").text()).replaceAll("[^0-9.]", "");

			postage = (el.getElementsByClass("fee").text()).replaceAll("[^0-9.]", "");

			if (postage.isEmpty()) {
				postage = "0.00";
			} else {
				postage = "€ " + postage;
			}

			if (price.indexOf('.', price.indexOf('.') + 1) != -1) {
				continue;
			}

			total = Double.parseDouble(price.replaceAll("[^0-9.]", ""))
					+ Double.parseDouble(postage.replaceAll("[^0-9.]", ""));
			
			allPrices += total;

			// Add the found stuff to our table
			dtm.addRow(new Object[] { name, price, postage, "€ " + df.format(total), "Ebay" });
		}
		
		StoreInfo.setEbayAVG(df.format(allPrices/totalQueries));
	}
}
