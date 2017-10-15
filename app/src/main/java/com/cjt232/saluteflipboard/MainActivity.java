package com.cjt232.saluteflipboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FlipboardView flipboard;

    Button buttonAnimation;

    SeekBar seekBar_AboveX;
    SeekBar seekBar_BelowX;
    SeekBar seekBar_Z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        flipboard = (FlipboardView) findViewById(R.id.flipboard);

        buttonAnimation = (Button) findViewById(R.id.startAnimation);
        buttonAnimation.setOnClickListener(this);

        seekBar_AboveX = (SeekBar) findViewById(R.id.seek_AX);
        seekBar_BelowX = (SeekBar) findViewById(R.id.seek_BX);
        seekBar_Z = (SeekBar) findViewById(R.id.seek_Z);

        seekBar_AboveX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                flipboard.rotateAboveX(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar_BelowX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                flipboard.rotateBelowX(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar_Z.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                flipboard.rotateZ(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startAnimation:
                flipboard.startAnim();
                break;
        }
    }
}
