package com.update.ipc.aidl;

interface IBook {
    List<String> getNames();
    void addName(String name);
}