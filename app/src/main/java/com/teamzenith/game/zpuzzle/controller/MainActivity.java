package com.teamzenith.game.zpuzzle.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.teamzenith.game.zpuzzle.R;
import com.teamzenith.game.zpuzzle.model.Level;
import com.teamzenith.game.zpuzzle.model.LevelFactory;
import com.teamzenith.game.zpuzzle.model.LevelType;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button hardBtn;
    Button medelBtn;
    Button kidsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createComponents();
        Actions();
    }

    private void createComponents() {
        hardBtn=(Button) findViewById(R.id.hardBtn);
        medelBtn=(Button) findViewById(R.id.medelBtn);
        kidsBtn=(Button) findViewById(R.id.kidsBtn);
    }

    private void Actions() {
        hardBtn.setOnClickListener(this);
        medelBtn.setOnClickListener(this);
        kidsBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Button btn=(Button)v;
        Level niveau;
        LevelFactory niveauFactory= LevelFactory.getInstance();


        if(btn.getId()==R.id.hardBtn){
            niveau=niveauFactory.createNiveau(LevelType.HARD);
        }
        else if(btn.getId()==R.id.medelBtn){
            niveau=niveauFactory.createNiveau(LevelType.MEDEL);
        }
        else{
            niveau=niveauFactory.createNiveau(LevelType.KIDS);
        }

        Intent intent=new Intent(this,Game.class);
        intent.putExtra("Level",niveau);
        startActivity(intent);
    }
}
