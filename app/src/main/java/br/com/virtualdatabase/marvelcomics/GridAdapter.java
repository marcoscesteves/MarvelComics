package br.com.virtualdatabase.marvelcomics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by marcoscesteves on 07/06/16.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Revista> listaRevistas;

    GridAdapter(Context ctx, Revista[] list){
        this.context = ctx;
        List<Revista> lista = Arrays.asList(list);
        this.listaRevistas = new ArrayList<>(lista);
        Collections.sort(listaRevistas);
    }

    @Override
    public int getCount() {

        return listaRevistas.size();

    }

    @Override
    public Revista getItem(int position) {
        return listaRevistas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaRevistas.indexOf(getItem(position));
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
            holder.capaRevista = (ImageView) convertView.findViewById(R.id.capaRevista);
            holder.codigoRevista = (TextView) convertView.findViewById(R.id.codigoRevista);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        String tamanhoImagem="pequena";
       /*if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            tamanhoImagem = "grande";
            holder.capaRevista.setMaxHeight(250);
        } else {
            tamanhoImagem = "pequena";
        }*/

        //tamanhoImagem = "pequena";

        Picasso.with(context).load(listaRevistas.get(position).getThumbnail(tamanhoImagem))
                .into(holder.capaRevista);


        holder.codigoRevista.setText("# "+listaRevistas.get(position).getIssueNumber());

        return convertView;
    }

    public static class ViewHolder {
        public ImageView capaRevista;
        public TextView codigoRevista;

    }

}








