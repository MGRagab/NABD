package com.appventure.nabd.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.appventure.nabd.beans.News;
import com.google.gson.Gson;

public class SharedPrefrencesClass {



	public static final String PREFS_ARTICLES_LIST = "ARTICLES_LISTS";
	public static final String ARTICLES_LIST = "ARTICLES_LIST";

	// This four methods are used for maintaining OFFLINE ARTICLES.
	/** OFFLINE ARTICLES  **/
	public void saveOFFLINEARTICLES(Context context, List<News> favorites) {
		SharedPreferences settings;
		Editor editor;
		settings = context.getSharedPreferences(PREFS_ARTICLES_LIST,
				Context.MODE_PRIVATE);
		editor = settings.edit();
		Gson gson = new Gson();
		String jsonFavorites = gson.toJson(favorites);
		editor.putString(ARTICLES_LIST, jsonFavorites);
		editor.commit();
	}

	public void addOFFLINEARTICLES(Context context, News product) {
		List<News> favorites = getOFFLINEARTICLES(context);
		if (favorites == null)
			favorites = new ArrayList<News>();
		favorites.add(product);
		saveOFFLINEARTICLES(context, favorites);
	}

	public void removeOFFLINEARTICLES(Context context, News product) {
		ArrayList<News> favorites = getOFFLINEARTICLES(context);
		if (favorites != null) {
			ArrayList<News> cats = new ArrayList<News>();
			cats = getOFFLINEARTICLES(context);
			int id = 0;
			for (int i = 0; i < cats.size(); i++) {
				if (cats.get(i).getTitle().equals(product.getTitle()) ) {
					id = i;
				}
			}
			favorites.remove(id);
			saveOFFLINEARTICLES(context, favorites);
		}
	}

	public ArrayList<News> getOFFLINEARTICLES(Context context) {
		SharedPreferences settings;
		List<News> favorites;

		settings = context.getSharedPreferences(PREFS_ARTICLES_LIST,
				Context.MODE_PRIVATE);

		if (settings.contains(ARTICLES_LIST)) {
			String jsonFavorites = settings.getString(ARTICLES_LIST, null);
			Gson gson = new Gson();
			News[] favoriteItems = gson.fromJson(jsonFavorites,
					News[].class);

			favorites = Arrays.asList(favoriteItems);
			favorites = new ArrayList<News>(favorites);
		} else
			return new ArrayList<News>();

		return (ArrayList<News>) favorites;
	}

	/** Favourites **/


}
