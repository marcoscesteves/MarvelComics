package br.com.virtualdatabase.marvelcomics;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by marcoscesteves on 08/06/16.
 */
public class RevistaDetalhes extends AppCompatActivity {

    private Revista revista = new Revista();
    private ImageView img_CollapsingToolBar;
    private ImageView img_capaRevista;
    private TextView titulo;
    private TextView txt_published;
    private TextView txt_price;
    private TextView txt_pages;
    private TextView txt_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.grid_exit));
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.capa_transition));
        }

            //->  Layout pattern
        setContentView(R.layout.activity_revista_detalhes);
        img_capaRevista = (ImageView) findViewById(R.id.imagemCapaDetalhes);
        titulo = (TextView) findViewById(R.id.tituloPrincipalRevista);
        txt_description = (TextView) findViewById(R.id.descricaoRevista);
        txt_published = (TextView) findViewById(R.id.dataPublicacao);
        txt_price = (TextView) findViewById(R.id.price);
        txt_pages = (TextView) findViewById(R.id.pages);
        //<-

        //->  Pegando dados da Activity anterior
        Intent intent = getIntent();
        revista = (Revista) intent.getSerializableExtra("revistaSelecionada");
        //<-

        //->  Preenchendo dados na tela
        titulo.setText(revista.getTitle());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            txt_description.setText(Html.fromHtml(revista.getDescription(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            txt_description.setText(Html.fromHtml(revista.getDescription()));
        }

        txt_published.setText(revista.getDates().toString());

        // Chegando se a revista possui versão digital (além da versão em papel)
        if (revista.getPrices("digital")!=0.0){
            txt_price.setText("$ "+revista.getPrices("paper")+" (Paperback)"+ "\n$ "+
                    revista.getPrices("digital")+ " (Digital Version)");
        } else {
            txt_price.setText("$ "+revista.getPrices("paper")+" (Paperback)");
        }
        txt_pages.setText(revista.getPageCount());
        Picasso.with(this).load(revista.getThumbnail("pequena")).into(img_capaRevista);
        //<-


        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

    }

    //-> Atribuindo função no clique da Capa da Revista (Exibindo ela maior):

    public void showFullImage(View v) {

        Intent intent = new Intent(this, RevistaCapaInteira.class);
        intent.putExtra("revistaSelecionada", revista);
        intent.setAction(Intent.ACTION_VIEW);


        if (Build.VERSION.SDK_INT >= 21) {

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, img_capaRevista, "imagemCompartilhada");
            startActivity(intent,optionsCompat.toBundle());


        } else {
            startActivity(intent);
        }

    }


    public void goBack(View v){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= 21) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    //<-

}
