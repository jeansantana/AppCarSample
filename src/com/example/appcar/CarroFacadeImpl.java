package com.example.appcar;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;

public class CarroFacadeImpl extends Application implements CarroFacade {

	private static CarroFacadeImpl singleton = null;
	private CarroDAO dao;
	private static Context context;	
	
	private CarroFacadeImpl() {
		dao = new CarroDAO(context);
	}

	public static CarroFacadeImpl getInstance(Context context) {
		CarroFacadeImpl.context = context;
		if (singleton == null) {
			singleton = new CarroFacadeImpl();
		}
		return singleton;
	}

	private String url_conn = Consts.URL_BASE + "carro/";
	private String TAG = "CARRO";
	private final String USER_AGENT = "Mozilla/5.0";

	public void openBD() {
		dao.open();
	}

	public void closeBD() {
		dao.close();
	}

	@Override
	public List<Carro> getAll() throws Exception {
		List<Carro> carros = new ArrayList<Carro>();
		// Log.d(TAG, "Fazendo requisção: " + url_conn);
		if (isOnline()) {
			URL url = new URL(url_conn);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			Log.d(TAG, "\nSending 'GET' request to URL : " + url);
			Log.d(TAG, "Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			Log.d(TAG, response.toString());

			Gson gson = new Gson();

			Carro[] all = gson.fromJson(response.toString(), Carro[].class);

			for (int i = 0; i < all.length; i++) {
				carros.add(all[i]);
				Log.d(TAG, all[i].toString());
			}
			
		} else {
			carros = dao.getAllCars();
		}
		
		return carros;

		// return null;
	}

	@Override
	public void delete(Carro c) throws Exception {
		//Carro toDelete = null;
		if (isOnline()) {
			URL url = new URL(url_conn + c.getId());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// optional default is GET
			con.setRequestMethod("DELETE");

			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Content-Type", "application/json");

			int responseCode = con.getResponseCode();
			Log.d(TAG, "\nSending 'DELETE' request to URL : " + url);
			Log.d(TAG, "Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//Gson gson = new Gson();
			//toDelete = gson.fromJson(response.toString(), Carro.class);
		}
		
		dao.deleteCar(c);

		// Gson gso
	}

	@Override
	public void insert(Carro c) throws Exception {
		Carro toInsert = null;
		if (isOnline()) {
			URL url = new URL(url_conn);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// optional default is GET
			con.setRequestMethod("POST");

			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Content-Type", "application/json");

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			Log.d(TAG, "Sending:\n " + new Gson().toJson(c));
			wr.writeBytes(new Gson().toJson(c));
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			Log.d(TAG, "\nSending 'POST' request to URL : " + url);
			Log.d(TAG, "Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			toInsert = new Gson().fromJson(response.toString(), Carro.class);
		}
		
		if (toInsert == null) 
			dao.insertCarro(c);
		else
			dao.insertCarro(toInsert);
	}

	@Override
	public void update(Carro c) throws Exception {
		Carro toUpdate = null;
		if (isOnline()) {
			URL url = new URL(url_conn);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// optional default is GET
			con.setRequestMethod("PUT");

			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Content-Type", "application/json");

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			Log.d(TAG, "Sending:\n " + new Gson().toJson(c));
			wr.writeBytes(new Gson().toJson(c));
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			Log.d(TAG, "\nSending 'PUT' request to URL : " + url);
			Log.d(TAG, "Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			toUpdate = new Gson().fromJson(response.toString(), Carro.class);
		}
		
		if (toUpdate == null) 
			dao.updateCar(c);
		else
			dao.updateCar(toUpdate);

	}

	private boolean isOnline() {

		boolean isOnline = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null) {
			isOnline = true;
		}

		return isOnline;

	}

}
