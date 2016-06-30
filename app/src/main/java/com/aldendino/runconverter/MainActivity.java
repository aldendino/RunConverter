package com.aldendino.runconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private NumberPicker hrsPicker;
    private NumberPicker minPicker;
    private NumberPicker secPicker;
    private NumberPicker milPicker;
    private EditText kmText;

    private Button convertButton;
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kmText = (EditText) findViewById(R.id.kilometersText);

        convertButton = (Button) findViewById(R.id.convertButton);
        resultView = (TextView) findViewById(R.id.resultView);

        hrsPicker = (NumberPicker) findViewById(R.id.hoursPicker);
        hrsPicker.setMaxValue(59);
        hrsPicker.setMinValue(0);

        minPicker = (NumberPicker) findViewById(R.id.minutesPicker);
        minPicker.setMaxValue(59);
        minPicker.setMinValue(0);

        secPicker = (NumberPicker) findViewById(R.id.secondsPicker);
        secPicker.setMaxValue(59);
        secPicker.setMinValue(0);

        milPicker = (NumberPicker) findViewById(R.id.millisecondsPicker);
        milPicker.setMaxValue(99);
        milPicker.setMinValue(0);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double totalMil = milPicker.getValue()
                        + 100 * (secPicker.getValue()
                        + 60 * (minPicker.getValue()
                        + 60 * hrsPicker.getValue()));
                double milPerKm = totalMil / getEditTextValue(kmText);

                double hrsPre = milPerKm / (100 * 60 * 60);
                int hrs = (int) Math.floor(hrsPre);
                hrsPre = (hrsPre - hrs) * (100 * 60 * 60);

                double minPre = hrsPre / (100 * 60);
                int min = (int) Math.floor(minPre);
                minPre = (minPre - min) * (100 * 60);

                double secPre = minPre / (100);
                int sec = (int) Math.floor(secPre);
                secPre = (secPre - sec) * (100);

                int mil = (int) Math.round(secPre);

                String hrsStr = zeroPad(hrs);
                String minStr = zeroPad(min);
                String secStr = zeroPad(sec);
                String milStr = zeroPad(mil);

                resultView.setText(String.format(Locale.CANADA,
                        "%s:%s:%s:%s", hrsStr, minStr, secStr, milStr));
            }
        });
    }

    private double getEditTextValue(EditText editText) {
        String text = editText.getText().toString();
        if(text.equals("")) return 0;
        else return Double.parseDouble(text);
    }

    private String zeroPad(int value) {
        String paddedValue = Integer.toString(value);
        if(value < 10) paddedValue = "0" + paddedValue;
        return paddedValue;
    }
}
