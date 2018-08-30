package com.uep.followmymoney.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;

import com.uep.followmymoney.dao.CuentaDao;
import com.uep.followmymoney.dao.DaoMaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hchan on 10/04/2018.
 */

public class DatabaseHelper extends DaoMaster.OpenHelper {
    private String DB_NAME;
    private Context context;

    DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
        this.context = context;
        this.DB_NAME = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);

        db.execSQL("INSERT INTO " + CuentaDao.TABLENAME.toUpperCase()
                + " (NOMBRE,DESCRIPCION,FECHA_CREACION,TIPO) VALUES ('EFECTIVO','DINERO EFECTIVO',1,1)");
        db.execSQL("INSERT INTO " + CuentaDao.TABLENAME.toUpperCase()
                + " (NOMBRE,DESCRIPCION,FECHA_CREACION,TIPO) VALUES ('SALIDAS','SALIDAS DE FLUJO',1,2)");
        db.execSQL("INSERT INTO " + CuentaDao.TABLENAME.toUpperCase()
                + " (NOMBRE,DESCRIPCION,FECHA_CREACION,TIPO) VALUES ('ENTRADAS','ENTRADAS DE FLUJO',1,3)");
        /*CARGA DE PLANES
        RunScriptByFile scriptByFile = new RunScriptByFile(context,db);
        scriptByFile.execute("ConcessionaryScript.txt");
        scriptByFile.execute("PlanScript.txt");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int actualVersion) {
        for (int i = oldVersion; i < actualVersion; i++) {
            int version = db.getVersion();
            int newVersion = version + 1;
            if (version != actualVersion) {
                if (db.isReadOnly()) {
                    throw new SQLiteException("Can't upgrade read-only database from version " +
                            db.getVersion() + " to " + newVersion + ": " + DB_NAME);
                }
                db.beginTransaction();
                try {
                    switch (version) {
                        case 1:

                            break;
                    }
                    db.setVersion(newVersion);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }
        }
    }

    private void renameTempTable(SQLiteDatabase db, String tableName, String newTableName) {
        db.execSQL("ALTER TABLE " + tableName + " RENAME TO " + newTableName );
    }

    private void dropTempTable(SQLiteDatabase db, String tableName) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName );
    }

    private void updateTableColumns(SQLiteDatabase db,
                                    String tableName,
                                    String[] colsToRemove) {

        List<String> updatedTableColumns = getTableColumns(db, tableName);
        // Remove the columns we don't want anymore from the table's list of columns
        updatedTableColumns.removeAll(Arrays.asList(colsToRemove));
        String columnsSeperated = TextUtils.join(",", updatedTableColumns);
        // Populating the table with the data
        db.execSQL("INSERT INTO " + tableName + "(" + columnsSeperated + ") SELECT "
                + columnsSeperated + " FROM " + tableName + "_old;");
        dropTempTable(db, tableName + "_old;");
    }

    private List<String> getTableColumns(SQLiteDatabase db, String tableName) {
        ArrayList<String> columns = new ArrayList<>();
        String cmd = "pragma table_info(" + tableName + ");";
        Cursor cur = db.rawQuery(cmd, null);
        while (cur.moveToNext()){
            String columnName = cur.getString(cur.getColumnIndex("name"));
            columns.add(columnName);
        }
        cur.close();
        return columns;
    }
}
