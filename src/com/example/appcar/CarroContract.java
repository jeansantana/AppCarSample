package com.example.appcar;

import android.provider.BaseColumns;

public final class CarroContract {

	public CarroContract() {
	}

	public static abstract class FeedEntry implements BaseColumns {
		public static final String TABLE_NAME = "carro";
		public static final String COLUMN_NAME_ENTRY_ID = "entryid";
		public static final String COLUMN_NAME_NOME = "nome";
		public static final String COLUMN_NAME_MODELO = "modelo";
		public static final String COLUMN_NAME_ANO = "ano";
		public static final String COLUMN_NAME_FABRICANTE = "fabricante";
	}

}
