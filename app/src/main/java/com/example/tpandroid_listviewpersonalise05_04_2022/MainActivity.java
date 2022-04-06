package com.example.tpandroid_listviewpersonalise05_04_2022;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListAdapter(new CustomAdapter(
                this,
                R.layout.row,
                R.id.label,
                getResources().getStringArray(R.array.movies)));
    }

    class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            View customConvertView = convertView;
            if(convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService((Context.LAYOUT_INFLATER_SERVICE));
                customConvertView = layoutInflater.inflate(R.layout.row, parent, false);
            }

            View view = super.getView(position, convertView, parent);

            LinearLayout iconsLayout = view.findViewById(R.id.iconsLayout);
            for (int i = 0; i < iconsLayout.getChildCount(); i++) {
                Log.d("test23", iconsLayout.getChildAt(i).toString());

                iconsLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // On récupère les données dont on a besoin pour changer nos images
                        ImageView icon = (ImageView) v;
                        String ID = getResources().getResourceEntryName(icon.getId());
                        LinearLayout viewGroup = (LinearLayout) v.getParent();
                        ImageView iconToChange;
                        int counter = Integer.parseInt(String.valueOf(ID.charAt(ID.length()-1)));

                        // On met une étoile dorée à toutes les images à gauche de celle ou l'on clique
                        for(int j = counter; j > 0; j--) {
                            iconToChange = viewGroup.findViewById(getResources().getIdentifier("icon"+j, "id", getPackageName()));
                            iconToChange.setImageResource(android.R.drawable.star_big_on);
                        }

                        // On met une étoile non-dorée à toutes celle à droite
                        for(int j = counter+1; j <= 5; j++) {
                            iconToChange = viewGroup.findViewById(getResources().getIdentifier("icon"+j, "id", getPackageName()));
                            iconToChange.setImageResource(android.R.drawable.btn_star);
                        }

                        View rootView = (View) (viewGroup.getParent()).getParent();
                        TextView ratingEditText = (TextView) rootView.findViewById(getResources().getIdentifier("ratingTextView", "id", getPackageName()));
                        String imageViewTag = v.getTag().toString();
                        ratingEditText.setText(imageViewTag);
                    }
                });
            }

            return view;
        }

    }
}