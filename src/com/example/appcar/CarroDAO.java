package com.example.appcar;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler.Value;
import android.util.Log;

import com.example.appcar.CarroContract.FeedEntry;

public class CarroDAO {
	private String TAG = "CARRO_DAO";
	private SQLiteDatabase database;
	private BDHelper dbHelper;
	private String[] allColumns = { FeedEntry.COLUMN_NAME_ENTRY_ID,
			FeedEntry.COLUMN_NAME_NOME, FeedEntry.COLUMN_NAME_MODELO,
			FeedEntry.COLUMN_NAME_ANO, FeedEntry.COLUMN_NAME_FABRICANTE };

	public CarroDAO(Context context) {
		dbHelper = new BDHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Carro insertCarro(Carro c) {
		ContentValues values = new ContentValues();

		values.put(FeedEntry.COLUMN_NAME_ENTRY_ID, c.getId());
		values.put(FeedEntry.COLUMN_NAME_NOME, c.getNome());
		values.put(FeedEntry.COLUMN_NAME_MODELO, c.getModelo());
		values.put(FeedEntry.COLUMN_NAME_ANO, c.getAno());
		values.put(FeedEntry.COLUMN_NAME_FABRICANTE, c.getFabricante());

		long insertId = database.insert(FeedEntry.TABLE_NAME, null, values);
		Cursor cursor = database.query(FeedEntry.TABLE_NAME, allColumns,
				FeedEntry.COLUMN_NAME_ENTRY_ID + " = " + insertId, null, null,
				null, null);
		cursor.moveToFirst();
		Carro newCar = cursorToCar(cursor);
		cursor.close();
		return newCar;
	}

	public List<Carro> getAllCars() {

		List<Carro> carros = new ArrayList<Carro>();

		Cursor cursor = database.query(FeedEntry.TABLE_NAME, allColumns, null,
				null, null, null, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Carro c = cursorToCar(cursor);
			carros.add(c);
			cursor.moveToNext();
		}

		cursor.close();

		return carros;
	}

	private Carro cursorToCar(Cursor cursor) {
		Carro c = new Carro();
		c.setId(cursor.getInt(0));
		c.setNome(cursor.getString(1));
		c.setModelo(cursor.getString(2));
		c.setAno(cursor.getInt(3));
		c.setFabricante(cursor.getString(4));
		return c;
	}

	public void deleteCar(Carro c) {
		long id = c.getId();
		Log.d(TAG, "Comment deleted with id: " + id);
		database.delete(FeedEntry.TABLE_NAME, FeedEntry.COLUMN_NAME_ENTRY_ID
				+ " = " + id, null);
	}
	
	public void updateCar(Carro c) {
		ContentValues values = new ContentValues();
		//values.put(FeedEntry.COLUMN_NAME_ENTRY_ID, c.getId());
		values.put(FeedEntry.COLUMN_NAME_NOME, c.getNome());
		values.put(FeedEntry.COLUMN_NAME_MODELO, c.getModelo());
		values.put(FeedEntry.COLUMN_NAME_ANO, c.getAno());
		values.put(FeedEntry.COLUMN_NAME_FABRICANTE, c.getFabricante());
		
		database.update(FeedEntry.TABLE_NAME, values, FeedEntry.COLUMN_NAME_ENTRY_ID + "=" + c.getId(), null);
	}

}
