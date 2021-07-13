package org.aueb.kiot.connection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.BitSet;

import org.aueb.kiot.components.BloomFilter;
import org.aueb.kiot.general.Configuration;
import org.aueb.kiot.logging.Logger;

public class Receiver extends Thread {

    DatagramSocket serverSocket;

    public Receiver() {
        try {
            serverSocket = new DatagramSocket(9000);
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {

                byte[] receiveData = new byte[50];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                ByteBuffer buffer = ByteBuffer.wrap(receiveData);
                String messageId = this.getMessageId(buffer);
                Logger.log("Packet received: messageId = " + messageId);

                if (messageId.equals(Configuration.MSG_DATA)) {
                    String data = getData(buffer);
                    Logger.log("Data message came: Data = " + data);
                } else if (messageId.equals(Configuration.MSG_INTEREST)) {
                    Logger.log("Warning: Interest Packet Recieved");
                } else {
                    Logger.log("Warning: Unknown packet Recieved with ID=" + messageId);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }

    }

    private int getFibPort(ByteBuffer buffer) {
        return buffer.getInt(25);
    }

    private String getMessageId(ByteBuffer buffer) {
        try {
            byte[] messageIdBytes = new byte[1];
            buffer.get(messageIdBytes, 0, 1);
            return new String(messageIdBytes, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private BloomFilter getBloomFilter(ByteBuffer buffer) {
        byte[] finalBfBytes = new byte[Configuration.BF_SIZE / 8];
        buffer.get(finalBfBytes, 0, Configuration.BF_SIZE / 8);
        return new BloomFilter(Configuration.BF_SIZE, Configuration.MAX_NUM_TAGS, BitSet.valueOf(finalBfBytes));
    }

    private String getData(ByteBuffer buffer) {
        try {
            byte[] dataBytes = new byte[Configuration.DATA_SIZE];
            buffer.get(dataBytes, 0, Configuration.DATA_SIZE);
            return new String(dataBytes, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
