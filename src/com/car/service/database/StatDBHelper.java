package com.car.service.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class StatDBHelper extends SQLiteOpenHelper{

	private static final String DB_NAME = "db_of_actions";
	private static final int DB_VERSION = 1;

	public static final String TABLE_ACTION_LIST = "actionlist";


	private static final String TABLE_ACTION_LIST_CREATE = "CREATE TABLE " +
		TABLE_ACTION_LIST +
		" (" + ActionContentTable.COL_PRIMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ ActionContentTable.COL_DATA + " INTEGER, "
		+ ActionContentTable.COL_ACT_TYPE + " INTEGER, "
		+ ActionContentTable.COL_PRICE + " TEXT, "
		+ ActionContentTable.COL_ACT_COMMENT + " TEXT, "
		+ ActionContentTable.COL_ODOMETER + " TEXT, "
		+ ActionContentTable.COL_SERVICE_ITEMS + " TEXT, "
		+ ActionContentTable.HEAP_STR + " TEXT "
		+ ");";

	/**
	 * SQL for selecting items
	 */
	public static final String SELECT_ALL_ROWS =
		" SELECT * FROM " + TABLE_ACTION_LIST;



	public StatDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	/**
	 * Called when the database is created for the first time.
	 * This is where the  creation of tables and the initial population of the tables should happen.
	 *
	 * @param db The database.
	 */
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(TABLE_ACTION_LIST_CREATE);
	}

	/**
	 * Called when the database needs to be upgraded. The implementation
	 * should use this method to drop tables, add tables, or do anything else it
	 * needs to upgrade to the new schema version.
	 * <p/>
	 * <p>The SQLite ALTER TABLE documentation can be found
	 * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
	 * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
	 * you can use ALTER TABLE to rename the old table, then create the new table and then
	 * populate the new table with the contents of the old table.
	 *
	 * @param db         The database.
	 * @param oldVersion The old database version.
	 * @param newVersion The new database version.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//11.10.2013: DB_VERSION = 1, so this code is stub.
		//Add code for upgrade operation, if db structure/version will be changed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTION_LIST);
		onCreate(db);
	}

	/**
	 * Called when the database has been opened.  The implementation
	 * should check {@link android.database.sqlite.SQLiteDatabase#isReadOnly} before updating the
	 * database.
	 *
	 * @param db The database.
	 */
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}



	/**
	 * Info about columns of song table
	 */
	public static final class ActionContentTable {
		private ActionContentTable() {
		}

		public static final String COL_PRIMARY_ID = "action_id";
		public static final String COL_DATA = "act_data";
		public static final String COL_ACT_TYPE = "act_type";
		public static final String COL_PRICE = "act_quantity";
		public static final String COL_ACT_COMMENT = "act_comment";
		public static final String COL_ODOMETER = "act_odometer";
		public static final String COL_SERVICE_ITEMS = "act_service_items";
		/**
		 * You can put here all values without Database update
		 */
		public static final String HEAP_STR = "HEAP_STR";
	}
}



