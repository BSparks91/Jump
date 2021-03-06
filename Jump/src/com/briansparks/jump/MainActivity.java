package com.briansparks.jump;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_main);

        
        Button newGameButton = (Button)findViewById(R.id.newGame);
        
        newGameButton.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v) 
			{
				Intent intent = new Intent (MainActivity.this, game.class);
				startActivityForResult(intent, 0);
			}
		});
        
    }


}
