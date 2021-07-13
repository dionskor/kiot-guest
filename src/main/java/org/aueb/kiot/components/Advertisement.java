package org.aueb.kiot.components;

import java.nio.ByteBuffer;

import org.aueb.kiot.components.messages.Packet;
import org.aueb.kiot.general.Configuration;

public class Advertisement extends Packet {

    public Advertisement(BloomFilter bf, int fp) {
        super(bf, Configuration.MSG_ADVERTISEMENT, fp);
    }

    public byte[] toByteArray() {
        int buff_size = 1 /* size of message id */ + Configuration.BF_SIZE / 8 /* size of bloom filter in bytes */
                + 4 /* size of fib port */;
        ByteBuffer buffer = ByteBuffer.allocate(buff_size);
        byte[] bs = this.bf.getBitSet().toByteArray();
        buffer.put(this.messageId.getBytes());
        buffer.put(bs);
        buffer.putInt(fibPort);
        return buffer.array();
    }

}
