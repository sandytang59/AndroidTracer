package cs169.tracer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Trace extends Activity {
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trace);
        
        final Button standard_button = (Button) findViewById(R.id.trace_standard_button);
        standard_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent levels_intent = new Intent(Trace.this, Levels.class);
                startActivity(levels_intent);
            }
        });
        
        final Button time_attack_button = (Button) findViewById(R.id.trace_time_attack_button);
        time_attack_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent levels_intent = new Intent(Trace.this, Levels.class);
                startActivity(levels_intent);
            }
        });
        
    }
    
}
