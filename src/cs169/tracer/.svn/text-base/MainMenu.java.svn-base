package cs169.tracer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Button play_button = (Button) findViewById(R.id.play_button);
        play_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent play_intent = new Intent(MainMenu.this, Play.class);
                startActivity(play_intent);
            }
        });
    }
}