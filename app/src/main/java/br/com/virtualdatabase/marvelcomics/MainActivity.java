package br.com.virtualdatabase.marvelcomics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import in.srain.cube.views.GridViewWithHeaderAndFooter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ConnectionHelper connectionHelper = new ConnectionHelper();
    private GridViewWithHeaderAndFooter gridView;
    private Toolbar toolbar;
    private Thread thread;
    private AppCompatImageButton img_button;
    private ProgressBar progressBar;
    private TextView marvelLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //-> Checking version of Android (Only android 21+ (Lollipop) runs Animations)
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.grid_exit));

        }

        //->  Layout pattern
        setContentView(R.layout.activity_main);
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.gridView);
        toolbar = (Toolbar) findViewById(R.id.main_toolBar);
        img_button = (AppCompatImageButton) findViewById(R.id.btn_goToPerfil);
        marvelLogo = (TextView) findViewById(R.id.marvel_logo_text);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        /**
         * Atribuindo uma fonte específica ao TextView do Logo. Assim, teremos o logo em
         * necessidade de imagens.
         */

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/American Captain.ttf");
        marvelLogo.setTypeface(myCustomFont);

        setSupportActionBar(toolbar);
        //<-

        //-> Configurating Button on ToolBar
        img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,null);
                Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
                startActivity(intent, compat.toBundle());
            }
        });
        //<-

        rodarThread();

    }



    /**
     * Avalia a conexão e executa a Thread responsável por pegar o JSON, avaliá-lo e decompor o mesmo
     * nas variáveis pertinentes
     */
    protected void rodarThread() {

        //-> Creating a Secong Thread for take the info from WebService
        if (isNetworkAvailable()){
            thread = new Thread();
            thread.execute();
        }
        //<-
    }

    @Override
    protected void onPause() {
        thread.cancel(true);
        super.onPause();
    }

    /**
     * Método para avaliar se há conexão disponível
     * @return true - há conexão, false - não há conexão
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    /**
     * Método que cria o GridView com dados e footer
     * @param entrada - Array de revistas a exibir
     */
    public void montandoGridView(Revista[] entrada){
        //-> GridView Adapter:
        final GridAdapter adapter = new GridAdapter(MainActivity.this, entrada);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View footerView = layoutInflater.inflate(R.layout.footer_view,null);
        gridView.addFooterView(footerView);

        gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                progressBar.setVisibility(View.INVISIBLE);

            }
        });


        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Revista revista = new Revista();
                revista = adapter.getItem(position);
                //revista = connectionHelper.getData().getRevista(position);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,null);
                Intent intent = new Intent(MainActivity.this, RevistaDetalhes.class);
                intent.putExtra("revistaSelecionada", revista);
                startActivity(intent, compat.toBundle());
            }
        });
    }

    /**
     * Thread para conectar ao webservice e pegar o JSON
     */
    class Thread extends AsyncTask<String, String, Revista[]> {

        private OkHttpClient client = new OkHttpClient();

        @Override
        protected Revista[] doInBackground(String... params) {
            try {

                //Request request = new Request.Builder().url("http://gateway.marvel.com/v1/public/characters/1009610/comics?ts=1&apikey=bb4470a46d0659" +
                //                "a43c566ac6056ed48d&hash=479474cf0a28eac9998960da4d96f06b").build();

                Request request = new Request.Builder().url("http://www.virtualdatabase.com.br/others/receive.json").build();

                Response response = client.newCall(request).execute();
                String json = response.body().string();

                // Pegando o GSON e transformando em objetos:
                Gson gson = new Gson();

                connectionHelper = gson.fromJson(json, ConnectionHelper.class);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return connectionHelper.getData().getResults();

        }

        @Override
        protected void onPostExecute(Revista[] arrayRevistas) {

            montandoGridView(arrayRevistas);

        }

    }

}
