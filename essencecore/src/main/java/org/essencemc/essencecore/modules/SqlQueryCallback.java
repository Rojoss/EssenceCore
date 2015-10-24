package org.essencemc.essencecore.modules;

import java.sql.ResultSet;

public interface SqlQueryCallback {
    void onExecute(ResultSet result);
}
