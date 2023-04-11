package edu.ucalgary.oop;

interface ToConnectDB {
    boolean createConnection();
    void close();
}