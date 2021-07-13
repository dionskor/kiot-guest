package org.aueb.kiot.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.aueb.kiot.components.Advertisement;
import org.aueb.kiot.components.BloomFilter;
import org.aueb.kiot.components.Data;
import org.aueb.kiot.components.messages.Interest;
import org.aueb.kiot.logging.Logger;

/**
 * Sender class is for sending UDP packets
 *
 * @author kiot
 */
public class Sender extends Thread {

    private DatagramSocket ctrlSocket;
    private Object sendDataObject;
    private InetAddress dstIP;
    private int dstPort;

    /**
     * Constructor
     *
     * @param obj advertisement or interest
     * @param ip ip to send
     * @param port port to send
     */
    public Sender(Object obj, InetAddress ip, int port) {
        try {
            this.ctrlSocket = new DatagramSocket();
            this.sendDataObject = obj;
            this.dstIP = ip;
            this.dstPort = port;
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            byte[] sendData = null;
            if (this.sendDataObject instanceof Advertisement) {
                sendData = ((Advertisement) this.sendDataObject).toByteArray();
                Logger.log("Advertisement packet sending...");
            } else if (this.sendDataObject instanceof Interest) {
                sendData = ((Interest) this.sendDataObject).toByteArray();
                Logger.log("Interest packet sending...");
            } else if (this.sendDataObject instanceof Data) {
                sendData = ((Data) this.sendDataObject).toByteArray();
                Logger.log("Data packet sending...");
            }
            Logger.log("Bloom Filter is: " + BloomFilter.getBF_catalog().getBitSet());

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, dstIP, dstPort);
            ctrlSocket.send(sendPacket);
            Logger.log("Packet sent");

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
