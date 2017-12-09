package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Divyansh Singh on 11-11-2017.
 */

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {
    public EarthQuakeAdapter(@NonNull Context context, @NonNull List<EarthQuake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_pattern,parent,false);

        }

        EarthQuake curretnEarthQuake = getItem(position);

        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        DecimalFormat decimalFormat =  new DecimalFormat("0.0");
        String magn = decimalFormat.format(curretnEarthQuake.getmMagnitude());
        magnitude.setText(magn);

        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(curretnEarthQuake.getmMagnitude());
        Log.e("color","magnitudeColor");
        magnitudeCircle.setColor(magnitudeColor);



        String part1,part2;

        TextView placePart1 = (TextView) listItemView.findViewById(R.id.text_part1);
        TextView placePart2 = (TextView) listItemView.findViewById(R.id.text_part2);


        String location = curretnEarthQuake.getmLocation();
        if(location.contains("o")){
            String[] parts = location.split(" of ");
            part1 = parts[0] + " of ";
            part2 = parts[1];
        }
        else {
            part1 = "Near the";
            part2 = location;
        }
        placePart1.setText(part1);
        placePart2.setText(part2);

        TextView date = (TextView) listItemView.findViewById(R.id.date);
        date.setText(curretnEarthQuake.dateToDisplay);

        TextView time = (TextView) listItemView.findViewById(R.id.time);
        time.setText(curretnEarthQuake.getTime());

        return listItemView;


    }
    private int getMagnitudeColor(double magnitude){
        int colourResourceId = 0;
        int mag = (int) magnitude;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (mag){
            case 0:
            case 1:
                colourResourceId = R.color.magnitude1;
                break;
            case 2:
                colourResourceId = R.color.magnitude2;
                break;
            case 3:
                colourResourceId = R.color.magnitude3;
                break;
            case 4:
                colourResourceId = R.color.magnitude4;
                break;
            case 5:
                colourResourceId = R.color.magnitude5;
                break;
            case 6:
                colourResourceId = R.color.magnitude6;
                break;
            case 7:
                colourResourceId = R.color.magnitude7;
                break;
            case 8:
                colourResourceId= R.color.magnitude8;
                break;
            case 9:
                colourResourceId = R.color.magnitude9;
                break;
            default:
                colourResourceId = R.color.magnitude10plus;


        }
        return ContextCompat.getColor(getContext(), colourResourceId);

    }
}
