package org.aueb.kiot.general;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * This class stores configuration parameters and types of messages.
 */
public class Configuration {

    public static final int ROOT_NODE = 1;
    public static final int MIDDLE_NODE = 2;
    public static final int LEAF_NODE = 3;
    /**
     * Advertisement message
     */
    public static String MSG_ADVERTISEMENT = "1";

    /**
     * Interest message
     */
    public static String MSG_INTEREST = "3";

    /**
     * Data message
     */
    public static String MSG_DATA = "4";

    /**
     * Bloom Filter (BitSet) size
     */
    public static int BF_SIZE = 160;

    /**
     * Size of data
     */
    public static int DATA_SIZE = 50;

    /**
     * Maximum number of tags that we can add in Bloom Filter
     */
    public static int MAX_NUM_TAGS = 20;

    public static InetAddress rootIP;
    public static int rootPort;
    public static int type;
    public static List<String> tags;
    public static short nodeId, logMode;
    public static String outputFile;

    /**
     * Reads the configuration file (hard-coded - not given as an argument) and
     * stores the read values to the corresponding variables. Also, parse some
     * other arguments that we will need.
     */
    public static void configure(String config_file) {

        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(config_file));

            nodeId = Short.parseShort(prop.getProperty("NodeID"));
            type = Integer.parseInt(prop.getProperty("Type"));
            //IP = InetAddress.getByName(prop.getProperty("MyIP"));
            //port = Integer.parseInt(prop.getProperty("MyPort"));
            outputFile = prop.getProperty("OutputFile");
            logMode = Short.parseShort(prop.getProperty("LoggingMode"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            rootIP = InetAddress.getByName(prop.getProperty("RootIP"));
            System.out.println(rootIP);
            rootPort = Integer.parseInt(prop.getProperty("RootPort"));
            System.out.println(rootPort);
            tags = Arrays.asList(prop.getProperty("Tags").split(","));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
