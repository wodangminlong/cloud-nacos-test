package com.ml;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * id generator utils
 *
 * @author dml
 * @date 2021/10/28 10:45
 */
public class IdWorkerUtils {

    private IdWorkerUtils() {

    }

    /**
     * start time,do not change this
     */
    private static final long TWEPOCH = 1564999903485L;

    /**
     * worker id bits
     */
    private static final long WORKER_ID_BITS = 5L;

    /**
     * data center id bits
     */
    private static final long DATA_CENTER_ID_BITS = 5L;

    /**
     * max worker id
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /**
     * max data center id
     */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);

    /**
     * sequence bits
     */
    private static final long SEQUENCE_BITS = 12L;

    /**
     * worker id shift
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * data center id shift
     */
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /**
     * timestamp left shift
     */
    private static final long TIME_STAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    /**
     * sequence mask
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * last timestamp
     */
    private static long lastTimestamp = -1L;

    /**
     * 0，qps control
     */
    private static long sequence = 0L;

    /**
     * data center id
     */
    private static final long DATA_CENTER_ID = getDataCenterId();

    /**
     * worker id
     */
    private static final long WORKER_ID = getMaxWorkerId();

    /**
     * get next id
     */
    public static synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            return 0;
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return  ((timestamp - TWEPOCH) << TIME_STAMP_LEFT_SHIFT)
                | (DATA_CENTER_ID << DATA_CENTER_ID_SHIFT)
                | (WORKER_ID << WORKER_ID_SHIFT) | sequence;
    }

    private static long tilNextMillis(final long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * get maxWorkerId
     */
    private static long getMaxWorkerId() {
        StringBuilder mpid = new StringBuilder();
        mpid.append(DATA_CENTER_ID);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            // GET jvmPid
            mpid.append(name.split("@")[0]);
        }
        // MAC + PID 's hashcode
        return (mpid.toString().hashCode() & 0xffff) % (MAX_WORKER_ID + 1);
    }

    /**
     * get data center id
     */
    private static long getDataCenterId() {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (MAX_DATA_CENTER_ID + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

}
