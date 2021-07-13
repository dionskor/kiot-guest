package org.aueb.kiot.Main;

import org.aueb.kiot.components.BloomFilter;
import org.aueb.kiot.components.messages.Interest;
import org.aueb.kiot.connection.Receiver;
import org.aueb.kiot.connection.Sender;
import org.aueb.kiot.general.Configuration;
import org.aueb.kiot.logging.Logger;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		if (args[0] == null) {
			Logger.log("Forgot config file as parameter");
			System.out.println("Forgot config file as parameter");
			return;
		}
		// We delay the execution so that the Kiot finishes its structure
		Thread.sleep(30000);
		try {
			Configuration.configure(args[0]);

			new Receiver().start();
			Logger.log("Receiver started...");

			if (Configuration.type == 0) {
				System.out.println("message sending");
				new Sender(new Interest(BloomFilter.getBF_catalog()), Configuration.rootIP, Configuration.rootPort)
						.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
