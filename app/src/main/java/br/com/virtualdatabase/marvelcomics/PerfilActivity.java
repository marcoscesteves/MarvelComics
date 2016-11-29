package br.com.virtualdatabase.marvelcomics;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by marcoscesteves on 15/06/16.
 */
public class PerfilActivity extends AppCompatActivity {

    private ImageView minhaImagem;
    private Toolbar toolBar;
    private TextView minhaDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //-> Checking version of Android (Only android 21+ (Lollipop) runs Animations)
        if (Build.VERSION.SDK_INT >= 21) {

            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);

        }
        //->  Layout pattern
        setContentView(R.layout.activity_perfil);
        minhaImagem = (ImageView) findViewById(R.id.minhaFoto);
        toolBar = (Toolbar) findViewById(R.id.perfil_Toolbar);
        minhaDescricao = (TextView) findViewById(R.id.minhaDescricao);
        setSupportActionBar(toolBar);

    }

    public void goBack(View v){
        onBackPressed();
    }


}
