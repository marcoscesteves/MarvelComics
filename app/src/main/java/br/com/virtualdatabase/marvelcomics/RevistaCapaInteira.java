package br.com.virtualdatabase.marvelcomics;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

/**
 * Created by marcoscesteves on 24/06/16.
 */
public class RevistaCapaInteira extends AppCompatActivity {

    private ImageView img_capaRevista;
    private Revista revista = new Revista();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {

            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.capa_transition));
            postponeEnterTransition();

        }

        setContentView(R.layout.acitivity_capa_integral);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.containerGroup);
        Button btn_close = (Button) findViewById(R.id.button_close);
        img_capaRevista = (ImageView) findViewById(R.id.img_capaGrande);

        btn_close.setVisibility(container.VISIBLE);


        //->  Pegando dados da Activity anterior
        Intent intent = getIntent();
        revista = (Revista) intent.getSerializableExtra("revistaSelecionada");
        //<-

        Picasso.with(this).load(revista.getThumbnail("grande")).into(img_capaRevista, new
                com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {
                        scheduleStartPostPonedTransition(img_capaRevista);
                    }

                    @Override
                    public void onError() {

                    }
                });




    }


    public void goBack(View v) {
        onBackPressed();
    }

    private void scheduleStartPostPonedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        supportStartPostponedEnterTransition();
                        return true;
                    }
                }
        );
    }


}

