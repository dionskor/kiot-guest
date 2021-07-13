package org.aueb.kiot.components.messages;

import org.aueb.kiot.components.BloomFilter;

public abstract class Packet {

    protected BloomFilter bf;
    protected String messageId;
    protected int fibPort;
    protected String data;

    public Packet(BloomFilter bf, String mid, int fp) {
        this.bf = bf;
        this.messageId = mid;
        this.fibPort = fp;
    }

    public Packet(BloomFilter bf, String mid) {
        this.bf = bf;
        this.messageId = mid;
    }

    public Packet(String data, String mid) {
        this.messageId = mid;
        this.data = data;
    }

    protected abstract byte[] toByteArray();

}
