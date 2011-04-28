package cs169.tracer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class LevelAdapter extends BaseAdapter {
    private Context mContext;
    private final static int levelCount = 20; // Number of levels to display in the grid
    
    public LevelAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return levelCount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    // create a new TextView for each item referenced by the Adapter
    // This can change to an ImageView or our own class later on.
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            textView.setTextSize((float) 15);
            textView.setGravity(0x11); // Center it.
            textView.setPadding(8, 8, 8, 8);
            textView.setBackgroundColor(0xFF0000FF);
        } else {
            textView = (TextView) convertView;
        }
        
        Integer levelNumber = new Integer(position + 1);
        textView.setText(levelNumber.toString());
        return textView;
    }

}
