package com.example.maria.converter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

    Context context;
    int[] image;
    String[] country;
    String[] nameCountry;
    String[] converters;

    CustomAdapter (Context context,int[] image, String[] country, String[] nameCountry, String[] converters){
        super (context, R.layout.custom_layout, country);
        this.context = context;
        this.image = image;
        this.country = country;
        this.nameCountry = nameCountry;
        this.converters = converters;
    }
    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater countryInflater = LayoutInflater.from(getContext());
        View customView = countryInflater.inflate(R.layout.custom_layout, parent, false);

        ImageView countryImage = (ImageView) customView.findViewById(R.id.imageView);
        TextView countryText = (TextView) customView.findViewById(R.id.textCountry);
        TextView countryNameText = (TextView) customView.findViewById(R.id.textCountryName);
        TextView converter = (TextView) customView.findViewById(R.id.textConverter);

        countryText.setText(country[position]);
        countryNameText.setText(nameCountry[position]);
        countryImage.setImageResource(image[position]);
        converter.setText(converters[position]);

        return customView;
    }
}
