package com.caiguantianxia.database.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class IDGenerator implements IdentifierGenerator {
    private static long ID_FLAG = 1260000000000L;
    private static int SERVER_ID = 1;

    /**
     * Generate auto added id with current time, can distribute on AT MOST 128 machines
     *
     * @param serverID every server allocate its uniq serverID, 0 < serverID < 127
     * @return long id
     */
    public synchronized static Long generateId(int serverID) throws HibernateException {
        if (serverID <= 0) {
            throw new HibernateException("ServerID must > 0");
        }

        long infoid = System.currentTimeMillis() - ID_FLAG;
        infoid = (infoid << 7) | serverID;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return infoid;
    }

    /**
     * Get time by id
     *
     * @return long timeMilis
     */
    public static Long getTimeByID(long id) {
        return (id >> 7) + ID_FLAG;
    }

    /**
     * Get serverID by id
     *
     * @param id
     * @return serverID
     */
    public static int getServerIdByID(long id) {
        byte serverId = (byte) (id & 0x7F);
        return (int) serverId;
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return generateId(1);
    }
}
