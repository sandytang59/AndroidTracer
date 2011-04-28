package cs169.tracer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Arcade extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arcade);
        
        final Button standard_button = (Button) findViewById(R.id.arcade_standard_button);
        standard_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent levels_intent = new Intent(Arcade.this, Levels.class);
                startActivity(levels_intent);
            }
        });
        
        final Button time_attack_button = (Button) findViewById(R.id.arcade_time_attack_button);
        time_attack_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent levels_intent = new Intent(Arcade.this, Levels.class);
                startActivity(levels_intent);
            }
        });
        
    }
}
