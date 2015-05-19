package com.example.appcar;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private CarroFacadeImpl facade;
	private List<Carro> allCars;
	private EditText nomeCarro;
	private EditText modeloCarro;
	private EditText anoCarro;
	private EditText fabricanteCarro;
	private ListView listCars;
	private ArrayAdapter<Carro> adapter;
	private Carro toHandle;
	private Context context;
	private static final String TAG = "MAIN";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.activity_main);
		facade = CarroFacadeImpl.getInstance(this);
		allCars = new ArrayList<Carro>();
		nomeCarro = (EditText) findViewById(R.id.txtNome);
		modeloCarro = (EditText) findViewById(R.id.txtModelo);
		anoCarro = (EditText) findViewById(R.id.txtAno);
		fabricanteCarro = (EditText) findViewById(R.id.txtFabricante);
		listCars = (ListView) findViewById(R.id.listCars);

		new GetAllCarsTask().execute();

		((Button) findViewById(R.id.btnInserir)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Carro c = new Carro();
				c.setNome(nomeCarro.getText().toString());
				c.setModelo(modeloCarro.getText().toString());
				c.setAno(Integer
						.parseInt(anoCarro.getText().toString()));
				c.setFabricante(fabricanteCarro.getText().toString());
				clearScreen();
				new InsertCarTask().execute(c);

			}
		});
		
		((Button) findViewById(R.id.btnRemover))
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (toHandle != null) {
					new DeleteCarTask().execute(toHandle);
				} else {
					Toast.makeText(context, "Selecione um objeto da lista antes de fazer essa operação.", Toast.LENGTH_SHORT).show();
				}

			}
		});
		
		((Button) findViewById(R.id.btnUpdate))
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Carro c = new Carro();
				c.setNome(nomeCarro.getText().toString());
				c.setModelo(modeloCarro.getText().toString());
				c.setAno(Integer
						.parseInt(anoCarro.getText().toString()));
				c.setFabricante(fabricanteCarro.getText().toString());

				//new InsertCarTask().execute(c);

			}
		});
		
		((Button) findViewById(R.id.btnGetAll))
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				new GetAllCarsTask().execute();
				
				adapter = new ArrayAdapter<Carro>(MainActivity.this,
						android.R.layout.simple_list_item_1, allCars);

				listCars.setAdapter(adapter);	

			}
		});
		
		((Button) findViewById(R.id.btnUpdate))
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					//Carro toHandle = new Carro();
					toHandle.setNome(nomeCarro.getText().toString());
					toHandle.setModelo(modeloCarro.getText().toString());
					toHandle.setAno(Integer
							.parseInt(anoCarro.getText().toString()));
					toHandle.setFabricante(fabricanteCarro.getText().toString());
					
					new UpdateCarTask().execute(toHandle);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	

			}
		});
		
		listCars.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Carro c = allCars.get(position);
				nomeCarro.setText(c.getNome());
				modeloCarro.setText(c.getModelo());
				anoCarro.setText(c.getAno().toString());
				fabricanteCarro.setText(c.getFabricante());
				toHandle = c;
			}
			
		});

	}

	private void clearScreen() {
		nomeCarro.setText("");
		modeloCarro.setText("");
		anoCarro.setText("");
		fabricanteCarro.setText("");
	}
	
	private List<String> carrosToSring() {
		List<String> res = new ArrayList<String>();
		for (Carro c : allCars) {
			res.add(c.toString());
		}
		return res;
	}

	@Override
	protected void onResume() {
		facade.openBD();
		super.onResume();
	}

	@Override
	public void onPause() {
		facade.closeBD();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class GetAllCarsTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				allCars = facade.getAll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			adapter = new ArrayAdapter<Carro>(MainActivity.this,
					android.R.layout.simple_list_item_1, allCars);

			listCars.setAdapter(adapter);

		}
	}

	private class InsertCarTask extends AsyncTask<Carro, Void, Void> {

		@Override
		protected Void doInBackground(Carro... car) {

			try {
				facade.insert(car[0]);

				allCars = facade.getAll();

				/*
				 * for (Carro carro : allCars) { Log.d(TAG, carro.toString()); }
				 */

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			adapter = new ArrayAdapter<Carro>(MainActivity.this,
					android.R.layout.simple_list_item_1, allCars);

			listCars.setAdapter(adapter);
			//adapter.notifyDataSetChanged();
		}

	}

	private class DeleteCarTask extends AsyncTask<Carro, Void, Void> {

		@Override
		protected Void doInBackground(Carro... car) {

			//Carro car = new Carro();
			
			try {
				facade.delete(car[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			allCars.remove(toHandle);
			toHandle = null;
			adapter = new ArrayAdapter<Carro>(MainActivity.this,
					android.R.layout.simple_list_item_1, allCars);

			listCars.setAdapter(adapter);
		}

	}

	private class UpdateCarTask extends AsyncTask<Carro, Void, Carro> {

		@Override
		protected Carro doInBackground(Carro... c) {
			Carro res = null;
			try {
				facade.update(c[0]);
				res = c[0];
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return res;
		}
		
		@Override
		protected void onPostExecute(Carro result) {
			super.onPostExecute(result);
			int idx = allCars.indexOf(result);
			if (idx != -1) {
				allCars.set(idx, result);
			} else {
				Toast.makeText(context, "Erro ao atualizar objeto", Toast.LENGTH_SHORT).show();
			}
			
			adapter.notifyDataSetChanged();
		}

	}

	public boolean formValidation() {
		return !nomeCarro.getText().toString().isEmpty()
				&& !modeloCarro.getText().toString().isEmpty()
				&& !anoCarro.getText().toString().isEmpty()
				&& !fabricanteCarro.getText().toString().isEmpty();
	}

}
