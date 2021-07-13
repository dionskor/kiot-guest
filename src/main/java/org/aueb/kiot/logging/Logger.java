package org.aueb.kiot.logging;

import org.aueb.kiot.general.Configuration;

/**
 * This class provides a text logger to other classes.
 *
 * @author Ilias Paidakakos (paidakili@aueb.gr)
 * @version 1.0
 */
public class Logger {

    /**
     * Outputs the message in argument to the preselected stream (console and/or
     * file)
     * <p>
     * @param msg A String containing the org.aueb.kiot.logging message
     */
    public static void log(String msg) {
        try {
            TxtLogger logger = new TxtLogger(Configuration.logMode);
            logger.log(msg);
            logger.close();
            logger = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
