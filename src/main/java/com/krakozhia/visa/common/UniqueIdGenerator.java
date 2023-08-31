package com.krakozhia.visa.common;
public class UniqueIdGenerator {
    private static long lastId = 0;

    public synchronized static long generateUniqueId() {
        long currentTimestamp = System.currentTimeMillis();
        if (currentTimestamp <= lastId) {
            lastId++;
            currentTimestamp = lastId;
        } else {
            lastId = currentTimestamp;
        }
        return currentTimestamp;
    }

    public static void main(String[] args) {
        long uniqueId = UniqueIdGenerator.generateUniqueId();
        System.out.println("Generated Unique ID: " + uniqueId);
    }
}
