package org.essencemc.essencecore.modules;

public interface SqlUpdateCallback {
    void onExecute(int rowsChanged);
}
