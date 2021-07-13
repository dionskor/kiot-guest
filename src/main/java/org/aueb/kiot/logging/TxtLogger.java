package org.aueb.kiot.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aueb.kiot.general.Configuration;

/**
 * This class outputs a text log message to the predefined output streams.
 */
public class TxtLogger {

    public static int outputType;
    private PrintWriter outFile;
    private static final String LINE_SEPARATOR = "\r\n";
    private static long beginTime = System.currentTimeMillis();

    public TxtLogger(int ot) {

        outputType = ot;
        Path path = Paths.get(Configuration.outputFile);
        Boolean fileExits = Files.exists(path);
        if (outputType > 1) {
            File f = new File(path.toString());
            if (!fileExits) {
                try {
                    System.out.println("Logger does not exist");
                    f.createNewFile();
                    outFile = new PrintWriter(new FileOutputStream(f));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("File exists");
                try {
                    outFile = new PrintWriter(new FileOutputStream(f, true));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void logOld(String msg) {

        Date date = new Date();
        date.toString();
        String timeStamp = String.valueOf((System.currentTimeMillis() - beginTime) / 1000);

        if (outputType == 1) {
            System.out.println(timeStamp + " / " + Configuration.nodeId + " > " + msg);
        } else if (outputType == 2) {
            outFile.append(timeStamp + " / " + Configuration.nodeId + " > " + msg + LINE_SEPARATOR);
            outFile.flush();
        } else {
            System.out.println(timeStamp + " / " + Configuration.nodeId + " > " + msg);
            outFile.append(timeStamp + " " + msg + LINE_SEPARATOR);
            outFile.flush();
        }
    }

    public void log(String msg) {
        if (outputType == 1) {
            System.out.println(dateStamp() + "[g" + Configuration.nodeId + "]:" + msg);
        } else if (outputType == 2) {
            outFile.append(dateStamp() + "[g" + Configuration.nodeId + "]:" + msg + LINE_SEPARATOR);
            outFile.flush();
        } else {
            System.out.println(dateStamp() + "[g" + Configuration.nodeId + "]:" + msg);
            outFile.append(dateStamp() + "[g" + Configuration.nodeId + "]:" + msg + LINE_SEPARATOR);
            outFile.flush();
        }

    }

    private String dateStamp() {
        String info = new String();
        String stringFormat = "[yyyy-MM-dd][HH:mm:ss:SSS]";
        SimpleDateFormat sdf = new SimpleDateFormat(stringFormat);
        Date date = new Date();
        info = sdf.format(date);
        return info;
    }

    public void close() {
        try {
            outFile.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
