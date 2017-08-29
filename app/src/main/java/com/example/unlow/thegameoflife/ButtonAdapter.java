package com.example.unlow.thegameoflife;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.Locale;

class ButtonAdapter extends BaseAdapter {
    private Context mContext;
    final static int width = 10;
    final static int height = 13;
    private final String[] filenames = new String[width * height];

    // Gets the context so it can be used later
    ButtonAdapter(Context c) {
        mContext = c;
    }

    // Total number of things contained within the adapter
    public int getCount() {
        return filenames.length;
    }

    // Require for structure, not really used in my code.
    public Object getItem(int position) {
        return null;
    }

    // Require for structure, not really used in my code. Can
    // be used to get the id of an item in the adapter for
    // manual control.
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position,
                        final View convertView, ViewGroup parent) {
        Button btn;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            btn = new Button(mContext);
            btn.setPadding(3, 3, 3, 3);
            btn.setLayoutParams(new GridView.LayoutParams(90, 90));
            btn.setTextColor(Color.TRANSPARENT);
            btn.setText(filenames[position]);
            btn.setOnClickListener(view -> {
                if (!MainActivity.start)
                    changeState((Button) view);
                else {
                    Button button = (Button) view;
                    GradientDrawable gd;
                    if (button.getText().equals("")) {
                        gd = new GradientDrawable();
                        gd.setColor(Color.YELLOW);
                        gd.setCornerRadius(20);
                    } else
                        gd = (GradientDrawable) view.getBackground();
                    gd.setStroke(10, Color.YELLOW);
                    view.setBackground(gd);
                    if (!button.getText().toString().contains("+"))
                        button.setText(String.format(Locale.getDefault(), "%s%s", button.getText(), "+"));
                    else
                        refreshFrame(button);
                }
            });
        } else
            btn = (Button) convertView;
        btn.setId(position);

        return btn;
    }

    void refreshFrame(Button button) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(20);
        gd.setStroke(1, Color.BLACK);
        if (button.getText().equals("dead+") || button.getText().equals("+")) {
            gd.setColor(Color.RED);
            button.setText(R.string.dead_label);
        } else {
            gd.setColor(Color.GREEN);
            button.setText(R.string.live_label);
        }
        button.setBackground(gd);
    }

    void changeState(Button button) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(20);
        gd.setStroke(1, Color.BLACK);
        if (button.getText().equals("dead") || button.getText().equals("dead+") || button.getText().equals("")) {
            gd.setColor(Color.GREEN);
            button.setText(R.string.live_label);
        } else {
            gd.setColor(Color.RED);
            button.setText(R.string.dead_label);
        }
        button.setBackground(gd);
    }


    boolean checkStates(Button button) {
        int liveNeigh = liveNeighbors(button.getId());
        CharSequence state = button.getText();
        if ((state.toString().startsWith("dead") || state.equals("")) && liveNeigh == 3) {
            if (state.equals(("dead+")))
                MainActivity.score++;
            return true;
        } else if (state.toString().startsWith("live") && liveNeigh != 2 && liveNeigh != 3) {
            if (state.equals(("live+")))
                MainActivity.score++;
            return true;
        } else if (state.equals("+")) {
            if (liveNeigh == 3) {
                button.setText(R.string.dead_label); // because it will be alive in changeState() method
                MainActivity.score++;
            } else {
                button.setText(String.format(Locale.getDefault(), "%s%s", R.string.live_label, "++")); // because it will be dead in changeState() method
                MainActivity.score--;
            }
            return true;
        }
        return false;
    }

    private int liveNeighbors(int position) {
        int counter = 0;
        Button button;
        if (position + 1 < height * width && (position + 1) / width == position / width) {
            button = ((Activity) mContext).getWindow().getDecorView().findViewById(position + 1);
            if (button.getText().equals("live") || button.getText().equals("live+"))
                counter++;
        }
        if (position + width + 1 < height * width && (position + width + 1) / width == position / width + 1) {
            button = ((Activity) mContext).getWindow().getDecorView().findViewById(position + width + 1);
            if (button.getText().equals("live") || button.getText().equals("live+"))
                counter++;
        }
        if (position + width < height * width && (position + width) / width == position / width + 1) {
            button = ((Activity) mContext).getWindow().getDecorView().findViewById(position + width);
            if (button.getText().equals("live") || button.getText().equals("live+"))
                counter++;
        }
        if (position + width - 1 < height * width && (position + width - 1) / width == position / width + 1) {
            button = ((Activity) mContext).getWindow().getDecorView().findViewById(position + width - 1);
            if (button.getText().equals("live") || button.getText().equals("live+"))
                counter++;
        }
        if (position - 1 >= 0 && (position - 1) / width == position / width) {
            button = ((Activity) mContext).getWindow().getDecorView().findViewById(position - 1);
            if (button.getText().equals("live") || button.getText().equals("live+"))
                counter++;
        }
        if (position - width - 1 >= 0 && (position - width - 1) / width == position / width - 1) {
            button = ((Activity) mContext).getWindow().getDecorView().findViewById(position - width - 1);
            if (button.getText().equals("live") || button.getText().equals("live+"))
                counter++;
        }
        if (position - width >= 0 && (position - width) / width == position / width - 1) {
            button = ((Activity) mContext).getWindow().getDecorView().findViewById(position - width);
            if (button.getText().equals("live") || button.getText().equals("live+"))
                counter++;
        }
        if (position - width + 1 >= 0 && (position - width + 1) / width == position / width - 1) {
            button = ((Activity) mContext).getWindow().getDecorView().findViewById(position - width + 1);
            if (button.getText().equals("live") || button.getText().equals("live+"))
                counter++;
        }
        return counter;
    }
}