package ie.gmit.proskills.Processes;

import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

import ie.gmit.proskills.Websites.Adverts;
import ie.gmit.proskills.Websites.Ebay;
import ie.gmit.proskills.Websites.Newegg;

/**
 * This class is the main class for handling the threads for each store
 * each store is run in a seperate thread to return the results of each search at the same time.<br>
 * 
 * @author Cian Gannon
 * @author Danielis Joni�kis
 * @author Eddie Eldridge
 */
public class StoreSearch {

	public static void main(String item, DefaultTableModel dtm, JCheckBox chckbxEbay, JCheckBox chckbxDonedeal, JCheckBox chkBoxNewegg)
			throws InterruptedException {

		// Threads
		EbayThread EbayThread;
		AdvertsThread AdvertsThread;
		NeweggThread NeweggThread;

		if (chckbxEbay.isSelected()) {
			EbayThread = new EbayThread(item, dtm);
			EbayThread.start();
			EbayThread.join();
		}

		if (chckbxDonedeal.isSelected()) {
			AdvertsThread = new AdvertsThread(item, dtm);
			AdvertsThread.start();
			AdvertsThread.join();
		}
		
		if (chkBoxNewegg.isSelected()) {
			NeweggThread = new NeweggThread(item, dtm);
			NeweggThread.start();
			NeweggThread.join();
		}
	}
}

// Ebay thread
class EbayThread extends Thread {
	String item;
	DefaultTableModel dtm;

	EbayThread(String i, DefaultTableModel d) {
		item = i;
		dtm = d;

	}

	public void run() {

		try {
			Ebay.main(item, dtm);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

// Adverts Thread
class AdvertsThread extends Thread {
	// Variables
	String item;
	DefaultTableModel dtm;

	// Constructor
	AdvertsThread(String i, DefaultTableModel d) {
		item = i;
		dtm = d;
	}

	// Run method for thread
	public void run() {
		// Try catch
		try {
			Adverts.run(item, dtm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

// Newegg Thread
class NeweggThread extends Thread {
	// Variables
	String item;
	DefaultTableModel dtm;

	// Constructor
	NeweggThread(String i, DefaultTableModel d) {
		item = i;
		dtm = d;
	}

	// Run method for thread
	public void run() {
		// Try catch
		try {
			Newegg.run(item, dtm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}