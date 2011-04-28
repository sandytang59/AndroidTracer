package cs169.tracer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Play extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        
        final Button trace_button = (Button) findViewById(R.id.trace_button);
        trace_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent trace_intent = new Intent(Play.this, Trace.class);
                startActivity(trace_intent);
            }
        });
        
        final Button arcade_button = (Button) findViewById(R.id.arcade_button);
        arcade_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent arcade_intent = new Intent(Play.this, Arcade.class);
                startActivity(arcade_intent);
            }
        });
        
        final Button practice_button = (Button) findViewById(R.id.practice_button);
        practice_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent practice_intent = new Intent(Play.this, Levels.class);
                startActivity(practice_intent);
            }
        });
          
        final Button create_button = (Button) findViewById(R.id.create_button);
        create_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent play_intent = new Intent(Play.this, Drawer.class);
                startActivity(play_intent);
            }
        });
        
    }
}
