package com.example.excelstylecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private Spinner calculationTypeSpinner, ogpOvpSpinner;
    private EditText editPpsVolume, editNgtVolume, editPpsToPrepare, editNgtToPrepare;
    private Button calculateBtn;
    private TextView textPpsResultsTitle, textPpsNaNO2, textPpsPAA, textPpsWater;
    private TextView textPpsResultsTitle2, textPpsNH4NO3, textPpsAcetate, textPpsUK, textPpsWater2;

    private TextView textNgtResultsTitle, textNgtChem, textNgtChrysotile, textNgtFiber, textNgtWater;

    private TextView labelOgpOvp, labelPpsVolume, labelNgtVolume, labelPpsToPrepare, labelNgtToPrepare;

    private DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        setupSpinners();

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        calculationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateVisibility(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initViews() {

        calculationTypeSpinner = findViewById(R.id.calculationTypeSpinner);
        ogpOvpSpinner = findViewById(R.id.ogpOvpSpinner);

        editPpsVolume = findViewById(R.id.ppsVolume);
        editNgtVolume = findViewById(R.id.ngtVolume);
        editPpsToPrepare = findViewById(R.id.ppsToPrepare);
        editNgtToPrepare = findViewById(R.id.ngtToPrepare);
        calculateBtn = findViewById(R.id.calculateBtn);


        labelOgpOvp = findViewById(R.id.labelOgpOvp);
        labelPpsVolume = findViewById(R.id.labelPpsVolume);
        labelNgtVolume = findViewById(R.id.labelNgtVolume);
        labelPpsToPrepare = findViewById(R.id.labelPpsToPrepare);
        labelNgtToPrepare = findViewById(R.id.labelNgtToPrepare);


        textPpsResultsTitle = findViewById(R.id.ppsResultsTitle);
        textPpsNaNO2 = findViewById(R.id.ppsNaNO2);
        textPpsPAA = findViewById(R.id.ppsPAA);
        textPpsWater = findViewById(R.id.ppsWater);
        textPpsResultsTitle2 = findViewById(R.id.ppsResultsTitle2);
        textPpsNH4NO3 = findViewById(R.id.ppsNH4NO3);
        textPpsAcetate = findViewById(R.id.ppsAcetate);
        textPpsUK = findViewById(R.id.ppsUK);
        textPpsWater2 = findViewById(R.id.ppsWater2);


        textNgtResultsTitle = findViewById(R.id.ngtResultsTitle);
        textNgtChem = findViewById(R.id.ngtChem);
        textNgtChrysotile = findViewById(R.id.ngtChrysotile);
        textNgtFiber = findViewById(R.id.ngtFiber);
        textNgtWater = findViewById(R.id.ngtWater);
    }

    private void setupSpinners() {

        String[] calculationTypes = {"ППС", "NGT-3", "Оба"};
        ArrayAdapter<String> calcAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, calculationTypes);
        calcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calculationTypeSpinner.setAdapter(calcAdapter);


        String[] ogpOvpTypes = {"ОГП", "ОВП"};
        ArrayAdapter<String> ogpAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ogpOvpTypes);
        ogpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ogpOvpSpinner.setAdapter(ogpAdapter);
    }

    private void updateVisibility(int position) {

        labelOgpOvp.setVisibility(View.GONE);
        ogpOvpSpinner.setVisibility(View.GONE);
        labelPpsVolume.setVisibility(View.GONE);
        editPpsVolume.setVisibility(View.GONE);
        labelNgtVolume.setVisibility(View.GONE);
        editNgtVolume.setVisibility(View.GONE);
        labelPpsToPrepare.setVisibility(View.GONE);
        editPpsToPrepare.setVisibility(View.GONE);
        labelNgtToPrepare.setVisibility(View.GONE);
        editNgtToPrepare.setVisibility(View.GONE);


        switch (position) {
            case 0:
                labelOgpOvp.setVisibility(View.VISIBLE);
                ogpOvpSpinner.setVisibility(View.VISIBLE);
                labelPpsVolume.setVisibility(View.VISIBLE);
                editPpsVolume.setVisibility(View.VISIBLE);
                labelNgtVolume.setVisibility(View.VISIBLE);
                editNgtVolume.setVisibility(View.VISIBLE);
                labelPpsToPrepare.setVisibility(View.VISIBLE);
                editPpsToPrepare.setVisibility(View.VISIBLE);
                break;

            case 1:
                labelNgtToPrepare.setVisibility(View.VISIBLE);
                editNgtToPrepare.setVisibility(View.VISIBLE);
                break;

            case 2:
                labelOgpOvp.setVisibility(View.VISIBLE);
                ogpOvpSpinner.setVisibility(View.VISIBLE);
                labelPpsVolume.setVisibility(View.VISIBLE);
                editPpsVolume.setVisibility(View.VISIBLE);
                labelNgtVolume.setVisibility(View.VISIBLE);
                editNgtVolume.setVisibility(View.VISIBLE);
                labelPpsToPrepare.setVisibility(View.VISIBLE);
                editPpsToPrepare.setVisibility(View.VISIBLE);
                labelNgtToPrepare.setVisibility(View.VISIBLE);
                editNgtToPrepare.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void calculate() {
        int calcType = calculationTypeSpinner.getSelectedItemPosition();


        hideAllResults();

        switch (calcType) {
            case 0:
                calculatePPS();
                break;
            case 1:
                calculateNGT();
                break;
            case 2:
                calculatePPS();
                calculateNGT();
                break;
        }
    }

    private void calculatePPS() {
        try {
            double ppsVol = getDoubleFromEditText(editPpsVolume);
            double ngtVol = getDoubleFromEditText(editNgtVolume);
            double ppsToPrep = getDoubleFromEditText(editPpsToPrepare);
            int ogpOvpType = ogpOvpSpinner.getSelectedItemPosition();

            if (ppsToPrep <= 0) return;

            textPpsResultsTitle.setVisibility(View.VISIBLE);
            textPpsResultsTitle2.setVisibility(View.VISIBLE);

            final double NA_NO2_PER_50M3 = 6500; // кг на 50
            final double PAA_PER_50M3 = 800; // кг на 50
            final double WATER_PER_50M3 = 47.5; // м3 на 50

            final double NH4NO3_PER_50M3 = 5000; // кг на 50
            final double ACETATE_PER_50M3 = 210; // л на 50
            final double UK_PER_50M3 = 40; // л на 50
            final double WATER2_PER_50M3 = 46.5; // м3 на 50

            double factor = ppsToPrep / 50.0;

            double naNO2 = NA_NO2_PER_50M3 * factor;
            double paa = PAA_PER_50M3 * factor;
            double water = WATER_PER_50M3 * factor;


            int naNO2Bags = (int) Math.ceil(naNO2 / 25);
            int paaBags = (int) Math.ceil(paa / 25);


            double nh4NO3 = NH4NO3_PER_50M3 * factor;
            double acetate = ACETATE_PER_50M3 * factor;
            double uk = UK_PER_50M3 * factor;
            double water2 = WATER2_PER_50M3 * factor;

            int nh4NO3Bags = (int) Math.ceil(nh4NO3 / 25);

            double acetateCanisters = acetate / 36.8;

            textPpsNaNO2.setVisibility(View.VISIBLE);
            textPpsNaNO2.setText(String.format("NaNO2: %s кг (%s меш.)", df.format(naNO2), naNO2Bags));

            textPpsPAA.setVisibility(View.VISIBLE);
            textPpsPAA.setText(String.format("ПАА: %s кг (%s меш.)", df.format(paa), paaBags));

            textPpsWater.setVisibility(View.VISIBLE);
            textPpsWater.setText(String.format("Вода: %s м³", df.format(water)));

            textPpsNH4NO3.setVisibility(View.VISIBLE);
            textPpsNH4NO3.setText(String.format("NH4NO3: %s кг (%s меш.)", df.format(nh4NO3), nh4NO3Bags));

            textPpsAcetate.setVisibility(View.VISIBLE);
            textPpsAcetate.setText(String.format("Ацетат хрома (50%%): %s л (%.1f канистр)",
                    df.format(acetate), acetateCanisters));

            textPpsUK.setVisibility(View.VISIBLE);
            textPpsUK.setText(String.format("УК (70%%): %s л", df.format(uk)));

            textPpsWater2.setVisibility(View.VISIBLE);
            textPpsWater2.setText(String.format("Вода: %s м³", df.format(water2)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateNGT() {
        try {
            double ngtToPrep = getDoubleFromEditText(editNgtToPrepare);

            if (ngtToPrep <= 0) return;


            textNgtResultsTitle.setVisibility(View.VISIBLE);


            final double NGT_CHEM_PER_20M3 = 399.91; // кг на 20 м3
            final double CHRYSOTILE_PER_20M3 = 299.831; // кг на 20 м3
            final double FIBER_PER_20M3 = 30.45; // кг на 20 м3
            final double WATER_PER_20M3 = 19.60026; // м3 на 20 м3


            double factor = ngtToPrep / 20.0;

            double ngtChem = NGT_CHEM_PER_20M3 * factor;
            double chrysotile = CHRYSOTILE_PER_20M3 * factor;
            double fiber = FIBER_PER_20M3 * factor;
            double water = WATER_PER_20M3 * factor;


            int ngtChemBags = (int) Math.ceil(ngtChem / 25);
            int chrysotileBags = (int) Math.ceil(chrysotile / 25);

            textNgtChem.setVisibility(View.VISIBLE);
            textNgtChem.setText(String.format("NGT Chem-3: %s кг (%s меш.)", df.format(ngtChem), ngtChemBags));

            textNgtChrysotile.setVisibility(View.VISIBLE);
            textNgtChrysotile.setText(String.format("Хризотил: %s кг (%s меш.)", df.format(chrysotile), chrysotileBags));

            textNgtFiber.setVisibility(View.VISIBLE);
            textNgtFiber.setText(String.format("Фибра: %s кг", df.format(fiber)));

            textNgtWater.setVisibility(View.VISIBLE);
            textNgtWater.setText(String.format("Вода: %s м³", df.format(water)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideAllResults() {

        textPpsResultsTitle.setVisibility(View.GONE);
        textPpsNaNO2.setVisibility(View.GONE);
        textPpsPAA.setVisibility(View.GONE);
        textPpsWater.setVisibility(View.GONE);
        textPpsResultsTitle2.setVisibility(View.GONE);
        textPpsNH4NO3.setVisibility(View.GONE);
        textPpsAcetate.setVisibility(View.GONE);
        textPpsUK.setVisibility(View.GONE);
        textPpsWater2.setVisibility(View.GONE);


        textNgtResultsTitle.setVisibility(View.GONE);
        textNgtChem.setVisibility(View.GONE);
        textNgtChrysotile.setVisibility(View.GONE);
        textNgtFiber.setVisibility(View.GONE);
        textNgtWater.setVisibility(View.GONE);
    }

    private double getDoubleFromEditText(EditText editText) {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}