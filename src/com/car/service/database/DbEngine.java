package com.car.service.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.car.service.listmode.ItemModel;
import com.car.service.model.CarServiceApplication;
import com.car.service.utils.ConfigInfo;
import com.car.service.utils.WLog;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by r.savuschuk on 8/7/14.
 */
public class DbEngine {
	private String TAG = getClass().getSimpleName();
	Context context;
	private StatDBHelper dbHelper;
	private ExecutorService pool;
	private CallbackHandler callbackHandler;
	private static final int ERROR_VALUE = -1;

	/*
	do not change order
	it will have side effect in previous version user items in DB*/
	public enum Action {
		SERVICE(0),
		FUEL(1),
		ADMIN(2),
		COMMENT(3),
		NULL(255);
		private final int value;

		Action(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

	}

	public enum Mode {
		LIST(0),
		GRAPH(1);
		private final int value;

		Mode(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

	}

	public DbEngine(Context context) {
		this.context = context;
		dbHelper = new StatDBHelper(this.context);
		pool = Executors.newFixedThreadPool(1);
		callbackHandler = new CallbackHandler();
	}

	public interface Callback<T> extends Serializable {
		void onSuccess(T data, Double requestSumPrice);

		void onFail(DbError error);
	}

	/*
		method is for make all database write transactions in separate thread
		par1 type of item
		par2 quantity
		par3 comment
	 */
	public void dbWriteRequest(final DbEngine.Action type, final String price, final String comment, final String odometer,
		final String serviceItems,final String quantity,
		final Callback<Long> callback) {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				DbAnswer<Integer> rezult = null;
				switch (type) {
					case SERVICE:
						rezult = insertServiceRow(price, comment, odometer, serviceItems, Action.SERVICE);
						//insertTestRows();
						break;
					case FUEL:
						rezult = insertFuelRow(quantity,price,comment,odometer, type);
						break;
					case ADMIN:
						//rezult = insertOnlyCommentRow(par2, Action.PE);
						break;
					case COMMENT:
						//rezult = insertServiceRow(par1, par2, Action.TEMPERATURE);
						break;
				}
				if (rezult != null) {
					callbackHandler.sendCallback(callback, rezult);
				}
			}
		});
	}

	/*
	method is for make all database delete transactions in separate thread
    par1 id of item
 */
	public void dbDeleteRequest(final int rowId) {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				DbAnswer<Integer> rezult = null;
				deleteRow(rowId);
			}
		});
	}

	/*
		method is for make all database write transactions in separate thread
		par1 type of item
		par2 quantity
		par3 comment
	 */
	public void dbReadRequest(final DbEngine.Mode type, final int itemsType, final long time, final Callback<List<ItemModel>> callback) {
		pool.execute(new Runnable() {

			@Override
			public void run() {
				DbAnswer<List<ItemModel>> rezult = null;
				Bundle bundle = new Bundle();
				Message msg = new Message();
				switch (type) {
					case LIST:
						rezult = getListModeSeparateTypeRows(itemsType, time);
						break;
					case GRAPH:
						rezult = getSeparateTypeRows(itemsType);
						break;
				}
				if (rezult != null) {
					callbackHandler.sendCallback(callback, rezult);
				}
			}
		});
	}

	private DbAnswer insertServiceRow(String price, String comment, String odometer, String checkedItemsStr, Action action) {
		DbAnswer answer = new DbAnswer();
		ContentValues cv = new ContentValues();
		try {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			cv.put(StatDBHelper.ActionContentTable.COL_ACT_TYPE, action.value);
			cv.put(StatDBHelper.ActionContentTable.COL_PRICE, price);
			cv.put(StatDBHelper.ActionContentTable.COL_DATA, getDate());
			cv.put(StatDBHelper.ActionContentTable.COL_ACT_COMMENT, comment);
			cv.put(StatDBHelper.ActionContentTable.COL_ODOMETER, odometer);
			cv.put(StatDBHelper.ActionContentTable.COL_SERVICE_ITEMS, checkedItemsStr);
			long rowID = db.insert(dbHelper.TABLE_ACTION_LIST, null, cv);
			if (rowID == -1) {
				answer.setError(new DbError(DbError.ErrorCode.CANT_SAVE_DATA));
			} else {
				answer.setData(rowID);
			}
			WLog.e("TEST", "rowID=" + rowID);
		} catch (Exception e) {
			answer.setError(new DbError(DbError.ErrorCode.CANT_SAVE_DATA, e.getMessage()));
			WLog.e(TAG, "Can't save row", e);
		} finally {
			if (dbHelper != null) {
				dbHelper.close();
			}
		}


		return answer;
	}
	private DbAnswer insertFuelRow(String quantity,String price, String comment, String odometer, Action action) {
		DbAnswer answer = new DbAnswer();
		ContentValues cv = new ContentValues();
		try {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			cv.put(StatDBHelper.ActionContentTable.COL_ACT_TYPE, action.value);
			cv.put(StatDBHelper.ActionContentTable.COL_QUANTITY, quantity);
			cv.put(StatDBHelper.ActionContentTable.COL_PRICE, price);
			cv.put(StatDBHelper.ActionContentTable.COL_DATA, getDate());
			cv.put(StatDBHelper.ActionContentTable.COL_ACT_COMMENT, comment);
			cv.put(StatDBHelper.ActionContentTable.COL_ODOMETER, odometer);
			long rowID = db.insert(dbHelper.TABLE_ACTION_LIST, null, cv);
			if (rowID == -1) {
				answer.setError(new DbError(DbError.ErrorCode.CANT_SAVE_DATA));
			} else {
				answer.setData(rowID);
			}
			WLog.e("TEST", "rowID=" + rowID);
		} catch (Exception e) {
			answer.setError(new DbError(DbError.ErrorCode.CANT_SAVE_DATA, e.getMessage()));
			WLog.e(TAG, "Can't save row", e);
		} finally {
			if (dbHelper != null) {
				dbHelper.close();
			}
		}


		return answer;
	}

	private DbAnswer insertOnlyCommentRow(String comment, Action action) {
		DbAnswer answer = new DbAnswer();
		ContentValues cv = new ContentValues();
		try {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			cv.put(StatDBHelper.ActionContentTable.COL_ACT_TYPE, action.value);
			cv.put(StatDBHelper.ActionContentTable.COL_PRICE, "");
			cv.put(StatDBHelper.ActionContentTable.COL_DATA, getDate());
			cv.put(StatDBHelper.ActionContentTable.COL_ACT_COMMENT, comment);
			long rowID = db.insert(dbHelper.TABLE_ACTION_LIST, null, cv);
			if (rowID == -1) {
				answer.setError(new DbError(DbError.ErrorCode.CANT_SAVE_DATA));
			} else {
				answer.setData(rowID);
			}
			WLog.e(TAG, "rowID=" + rowID);
		} catch (Exception e) {
			answer.setError(new DbError(DbError.ErrorCode.CANT_SAVE_DATA, e.getMessage()));
			WLog.e(TAG, "Can't save row", e);
		} finally {
			if (dbHelper != null) {
				dbHelper.close();
			}
		}
		return answer;
	}

	private DbAnswer deleteRow(int rowId) {
		DbAnswer answer = new DbAnswer();
		try {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			String[] whereArgs = new String[] {String.valueOf(rowId)};
			int i = db.delete(StatDBHelper.TABLE_ACTION_LIST, StatDBHelper.ActionContentTable.COL_PRIMARY_ID + "=?", whereArgs);
			if (i == 0) {
				answer.setError(new DbError(DbError.ErrorCode.CANT_REMOVE_DATA));
			} else {
				answer.setData(i);
			}
			WLog.e("TEST", "rowID=");
		} catch (Exception e) {
			answer.setError(new DbError(DbError.ErrorCode.CANT_REMOVE_DATA, e.getMessage()));
			WLog.e(TAG, "Can't delete row", e);
		} finally {
			if (dbHelper != null) {
				dbHelper.close();
			}
		}
		return answer;
	}

	public void insertTestRows() {
		Integer quantity = 1;
		long date = getDate();
		ContentValues cv = new ContentValues();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		for (int i = 0; i < 2000; i++) {
			cv.put(StatDBHelper.ActionContentTable.COL_ACT_TYPE, Action.SERVICE.value);
			cv.put(StatDBHelper.ActionContentTable.COL_PRICE, String.valueOf(quantity));
			cv.put(StatDBHelper.ActionContentTable.COL_DATA, date);
			cv.put(StatDBHelper.ActionContentTable.COL_ACT_COMMENT, "Test method autogenerated");
			long rowID = db.insert(dbHelper.TABLE_ACTION_LIST, null, cv);
			WLog.e("TEST", "rowID=" + rowID);
			quantity++;
			date -= 60000L;
		}
		dbHelper.close();
	}

	private Long getDate() {
		Calendar c = Calendar.getInstance();
		Long temp = c.getTimeInMillis();
		Date t = new Date(temp);
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String formattedTime = df.format(t);

		WLog.d("TEST", formattedTime);

		return temp;
	}

	/*List<ItemModel> getAllRows(){

		List<ItemModel> itemList=new ArrayList<ItemModel>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		//Cursor cursor=db.rawQuery(StatDBHelper.SELECT_ALL_ROWS,null);
		String[] columns = new String[]{
			StatDBHelper.ActionContentTable.COL_PRIMARY_ID,
			StatDBHelper.ActionContentTable.COL_ACT_COMMENT,
			StatDBHelper.ActionContentTable.COL_PRICE,
			StatDBHelper.ActionContentTable.COL_ACT_TYPE,
			StatDBHelper.ActionContentTable.COL_DATA

		};
		//String selectionCondition =StatDBHelper.ActionContentTable.COL_DATA + "?";
		//String[] selectionArgs = new String[]{null};
		String orderBy = StatDBHelper.ActionContentTable.COL_DATA + " DESC ";
		Cursor cursor= db.query(StatDBHelper.TABLE_ACTION_LIST,columns,null,null,null,null,orderBy,null);

		WLog.e("TEST", "cursor=" + cursor.getCount() + " ");
		if (cursor.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int idColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_PRIMARY_ID);
			int typeColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_ACT_TYPE);
			int dataColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_DATA);
			int quantColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_PRICE);
			int commentColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_ACT_COMMENT);



			do {
				// получаем значения по номерам столбцов и пишем все в лог

				ItemModel item =new ItemModel();
				item.setId(cursor.getInt(idColIndex));
				item.setData(cursor.getLong(dataColIndex));
				item.setQuantity(cursor.getString(quantColIndex));
				item.setType(cursor.getInt(typeColIndex));
				item.setComment(cursor.getString(commentColIndex));
				itemList.add(item);

				WLog.d("TEST",
					"ID = " + cursor.getInt(idColIndex) +
						", type = " + cursor.getString(typeColIndex) +
						", data = " + cursor.getString(dataColIndex));

				// переход на следующую строку
				// а если следующей нет (текущая - последняя), то false - выходим из цикла
			} while (cursor.moveToNext());
		} else
			WLog.d("TEST", "0 rows");
		cursor.close();
		dbHelper.close();
		return itemList;
	}*/

	/*List<ItemModel> getCalendarPeriodRows(Long start,Long finish){
		List<ItemModel> itemList=new ArrayList<ItemModel>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		//Cursor cursor=db.rawQuery(StatDBHelper.SELECT_ALL_ROWS,null);
		String[] columns = new String[]{
			StatDBHelper.ActionContentTable.COL_PRIMARY_ID,
			StatDBHelper.ActionContentTable.COL_ACT_COMMENT,
			StatDBHelper.ActionContentTable.COL_PRICE,
			StatDBHelper.ActionContentTable.COL_ACT_TYPE,
			StatDBHelper.ActionContentTable.COL_DATA

		};
		String selectionCondition =StatDBHelper.ActionContentTable.COL_DATA + " >? AND "+ StatDBHelper.ActionContentTable.COL_DATA+" <? ";
		String[] selectionArgs = new String[]{String.valueOf(start),String.valueOf(finish)};
		String orderBy = StatDBHelper.ActionContentTable.COL_DATA + " DESC ";
		Cursor cursor= db.query(StatDBHelper.TABLE_ACTION_LIST,columns,selectionCondition,selectionArgs,null,null,orderBy,null);
		WLog.e("TEST", "startDate" + new Date(start)+ "finishdate "+new Date(finish));
		WLog.e("TEST", "cursor=" + cursor.getCount() + " ");
		if (cursor.moveToFirst()) {

			// определяем номера столбцов по имени в выборке
			int idColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_PRIMARY_ID);
			int typeColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_ACT_TYPE);
			int dataColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_DATA);
			int quantColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_PRICE);
			int commentColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_ACT_COMMENT);



			do {
				// получаем значения по номерам столбцов и пишем все в лог
				String str = cursor.getString(dataColIndex);

				ItemModel item =new ItemModel();
				item.setId(cursor.getInt(idColIndex));
				item.setData(cursor.getLong(dataColIndex));
				item.setQuantity(cursor.getString(quantColIndex));
				item.setType(cursor.getInt(typeColIndex));
				item.setComment(cursor.getString(commentColIndex));
				itemList.add(item);

				WLog.d("TEST",
					"ID = " + cursor.getInt(idColIndex) +
						", type = " + cursor.getString(typeColIndex) +
						", data = " + cursor.getString(dataColIndex));

				// переход на следующую строку
				// а если следующей нет (текущая - последняя), то false - выходим из цикла
			} while (cursor.moveToNext());
		} else
			WLog.d("TEST", "0 rows");
		cursor.close();
		dbHelper.close();
		return itemList;
	}
*/

	/**
	 * for Graphic mode
	 * Select all raws for type. which happend from  startTime to finishTime  period.
	 */

	private DbAnswer getSeparateTypeRows(int type) {
		DbAnswer answer = new DbAnswer();
		long startTime = CarServiceApplication.getFromTime();
		long finishTime = CarServiceApplication.getToTime();
		//		SimpleDateFormat df = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss");
		//		String formattedDate = df.format(startTime);
		//		WLog.e("STARTTIME", "start" + formattedDate);
		//		formattedDate = df.format(finishTime);
		//		WLog.e("FINISHTIME", "finish " + formattedDate);
		List<ItemModel> itemList = new ArrayList<ItemModel>();
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			String[] columns = new String[] {StatDBHelper.ActionContentTable.COL_PRIMARY_ID, StatDBHelper.ActionContentTable.COL_ACT_COMMENT,
				StatDBHelper.ActionContentTable.COL_PRICE, StatDBHelper.ActionContentTable.COL_ACT_TYPE, StatDBHelper.ActionContentTable.COL_DATA
			};
			String selectionCondition = StatDBHelper.ActionContentTable.COL_ACT_TYPE + "=? AND " +
				StatDBHelper.ActionContentTable.COL_DATA + " >? AND " +
				StatDBHelper.ActionContentTable.COL_DATA + " <? ";
			String[] selectionArgs = new String[] {String.valueOf(type), String.valueOf(startTime), String.valueOf(finishTime)};
			String orderBy = StatDBHelper.ActionContentTable.COL_DATA + " DESC ";
			Cursor cursor = db.query(StatDBHelper.TABLE_ACTION_LIST, columns, selectionCondition, selectionArgs, null, null, orderBy, null);

			WLog.e("TEST", "cursor=" + cursor.getCount() + " ");
			if (cursor.moveToFirst()) {

				// определяем номера столбцов по имени в выборке
				int idColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_PRIMARY_ID);
				int typeColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_ACT_TYPE);
				int dataColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_DATA);
				int quantColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_PRICE);
				int commentColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_ACT_COMMENT);


				do {
					// получаем значения по номерам столбцов и пишем все в лог
					String str = cursor.getString(dataColIndex);

					ItemModel item = new ItemModel();
					item.setId(cursor.getInt(idColIndex));
					item.setData(cursor.getLong(dataColIndex));
					item.setPrice(cursor.getString(quantColIndex));
					//item.setType(cursor.getInt(typeColIndex));//todo delete this str
					item.setActionType(cursor.getInt(typeColIndex));
					item.setComment(cursor.getString(commentColIndex));
					itemList.add(item);

					WLog.d("TEST", "ID = " + cursor.getInt(idColIndex) +
						", type = " + cursor.getString(typeColIndex) +
						", data = " + cursor.getString(dataColIndex));

					// переход на следующую строку
					// а если следующей нет (текущая - последняя), то false - выходим из цикла
				} while (cursor.moveToNext());
			} else {
				WLog.d("TEST", "0 rows");
			}
			answer.setData(itemList);
			cursor.close();
		} catch (Exception e) {
			answer.setError(new DbError(DbError.ErrorCode.CANT_OBTAIN_DATA, e.getMessage()));
			WLog.e(TAG, "Can't obtain row", e);
		} finally {
			if (dbHelper != null) {
				dbHelper.close();
			}
		}

		dbHelper.close();
		return answer;
	}

	/**
	 * for List mode
	 * Select all raws for type. which happend from  startTime to finishTime  period.
	 */

	private DbAnswer getListModeSeparateTypeRows(int type, long finishTime) {
		WLog.d("ListScrollListener", "  DATABASE REQUEST");
		DbAnswer answer = new DbAnswer();
		double sumPrice = 0;
		long startTime = CarServiceApplication.getFromTime();
		List<ItemModel> itemList = new ArrayList<ItemModel>();
		try {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			String[] columns = new String[] {StatDBHelper.ActionContentTable.COL_PRIMARY_ID, StatDBHelper.ActionContentTable.COL_ACT_COMMENT,
				StatDBHelper.ActionContentTable.COL_PRICE, StatDBHelper.ActionContentTable.COL_ACT_TYPE, StatDBHelper.ActionContentTable.COL_DATA,
				StatDBHelper.ActionContentTable.COL_ODOMETER, StatDBHelper.ActionContentTable.COL_SERVICE_ITEMS
			};
			String selectionCondition = StatDBHelper.ActionContentTable.COL_ACT_TYPE + "=? AND " +
				StatDBHelper.ActionContentTable.COL_DATA + " >? AND " +
				StatDBHelper.ActionContentTable.COL_DATA + " <? ";
			String[] selectionArgs = new String[] {String.valueOf(type), String.valueOf(startTime), String.valueOf(finishTime)};
			String orderBy = StatDBHelper.ActionContentTable.COL_DATA + " DESC ";
			String limit = ConfigInfo.DB_READITEMLIMIT;
			Cursor cursor = db.query(StatDBHelper.TABLE_ACTION_LIST, columns, selectionCondition, selectionArgs, null, null, orderBy, limit);

			WLog.e("TEST", "cursor=" + cursor.getCount() + " ");
			if (cursor.moveToFirst()) {

				// определяем номера столбцов по имени в выборке
				int idColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_PRIMARY_ID);
				int typeColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_ACT_TYPE);
				int dataColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_DATA);
				int priceColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_PRICE);
				int odometerColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_ODOMETER);
				int serviceItemsColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_SERVICE_ITEMS);
				int commentColIndex = cursor.getColumnIndex(StatDBHelper.ActionContentTable.COL_ACT_COMMENT);


				do {
					// получаем значения по номерам столбцов и пишем все в лог
					String str = cursor.getString(dataColIndex);

					ItemModel item = new ItemModel();
					item.setId(cursor.getInt(idColIndex));
					item.setData(cursor.getLong(dataColIndex));
					item.setPrice(cursor.getString(priceColIndex));
					item.setActionType(cursor.getInt(typeColIndex));
					item.setComment(cursor.getString(commentColIndex));
					item.setItemServicesList(cursor.getString(serviceItemsColIndex));
					item.setOdometer(cursor.getString(odometerColIndex));
					itemList.add(item);

					sumPrice += item.getPriceAsDouble();
					WLog.d("TEST", "ID = " + cursor.getInt(idColIndex) +
						", type = " + cursor.getString(typeColIndex) +
						", data = " + cursor.getString(dataColIndex));

					// переход на следующую строку
					// а если следующей нет (текущая - последняя), то false - выходим из цикла
				} while (cursor.moveToNext());
			} else {
				WLog.d("TEST", "0 rows");
			}
			answer.setAllItemsSumPrice(sumPrice);
			answer.setData(itemList);
			cursor.close();
		} catch (Exception e) {
			answer.setError(new DbError(DbError.ErrorCode.CANT_OBTAIN_DATA, e.getMessage()));
			WLog.e(TAG, "Can't obtain row", e);
		} finally {
			if (dbHelper != null) {
				dbHelper.close();
			}
		}
		WLog.d("ListScrollListener", " database return " + itemList.size() + " eat rows");
		//DbAnswer rezult = new DbAnswer(itemList);
		return answer;
	}

	private static class CallbackHandler extends Handler {
		private static final int CALLBACK_MESSAGE = 131761;
		private static final String CALLBACK_KEY = "db.callback_key";
		private static final String RESPONSE_KEY = "db.response_key";
		private String TAG = getClass().getSimpleName();


		public void handleMessage(Message msg) {
			if (msg.what == CALLBACK_MESSAGE) {
				DbEngine.Callback callback = (DbEngine.Callback) msg.getData().get(CALLBACK_KEY);
				DbAnswer rezult = (DbAnswer) msg.getData().get(RESPONSE_KEY);
				if (callback == null || rezult == null) {
					WLog.e(TAG, "CallbackHandler: callback = null  and result = null");
					return;
				}

				if (rezult.isSuccess()) {
					callback.onSuccess(rezult.getData(), rezult.getAllItemsSumPrice());
				} else {
					callback.onFail(rezult.getError());
				}
			}
		}

		public void sendCallback(DbEngine.Callback callback, DbAnswer result) {
			if (callback == null) {
				return;
			}
			Bundle bundle = new Bundle();
			bundle.putSerializable(CallbackHandler.RESPONSE_KEY, result);
			bundle.putSerializable(CallbackHandler.CALLBACK_KEY, callback);
			Message msg = obtainMessage(CallbackHandler.CALLBACK_MESSAGE);
			msg.setData(bundle);
			msg.sendToTarget();
		}
	}
}
