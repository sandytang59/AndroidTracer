package cs169.tracer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Levels extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
        
        GridView gridview = (GridView) findViewById(R.id.levels_gridview);
        gridview.setAdapter(new LevelAdapter(this));
        
        // This is where we link up the levels.
        
//        gridview.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Toast.makeText(Levels.this, "" + position + 1, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}
