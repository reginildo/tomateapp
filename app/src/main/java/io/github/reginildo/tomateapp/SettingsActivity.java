package io.github.reginildo.tomateapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity {

    ViewHolder viewHolder;
    public static Tomate tomate;
    private static final String EXTRA_TOMATE = "tomate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        tomate = (Tomate) getIntent().getSerializableExtra(EXTRA_TOMATE);
        viewHolder = new ViewHolder();
        createArrayAdapters();
        setSelectionForSpinners();
        setOnItemSelectedListenersForSpinners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.tomate = tomate;
    }

    private void setOnItemSelectedListenersForSpinners() {
        viewHolder.spinnerQuantCiclos.setOnItemSelectedListener(
                new SpinnerQuantCiclosSelectedListener());
        viewHolder.spinnerDuracCiclo.setOnItemSelectedListener(
                new SpinnerDuracSelectedListener());
        viewHolder.spinnerIntervCurto.setOnItemSelectedListener(
                new SpinnerIntervCurtoSelectedListener());
        viewHolder.spinnerIntervLongo.setOnItemSelectedListener(
                new SpinnerIntervLongoSelectedListener());
        viewHolder.spinnerAlarm.setOnItemSelectedListener(
                new SpinnerAlarmSelectedListener());

    }

    private void setSelectionForSpinners() {
        switch (tomate.getCiclosTime()){
            case 1 : {viewHolder.spinnerQuantCiclos.setSelection(0);break;}
            case 2 : {viewHolder.spinnerQuantCiclos.setSelection(1);break;}
            case 3 : {viewHolder.spinnerQuantCiclos.setSelection(2);break;}
            case 4 : {viewHolder.spinnerQuantCiclos.setSelection(3);break;}
            case 5 : {viewHolder.spinnerQuantCiclos.setSelection(4);break;}
            case 6 : {viewHolder.spinnerQuantCiclos.setSelection(5);break;}
            case 7 : {viewHolder.spinnerQuantCiclos.setSelection(6);break;}
            case 8 : {viewHolder.spinnerQuantCiclos.setSelection(7);break;}
        }
        switch (tomate.getPomodoroTime() / 60 / 1000){
            case 5 : {viewHolder.spinnerDuracCiclo.setSelection(0);break;}
            case 10 : {viewHolder.spinnerDuracCiclo.setSelection(1);break;}
            case 15 : {viewHolder.spinnerDuracCiclo.setSelection(2);break;}
            case 20 : {viewHolder.spinnerDuracCiclo.setSelection(3);break;}
            case 25 : {viewHolder.spinnerDuracCiclo.setSelection(4);break;}
            case 30 : {viewHolder.spinnerDuracCiclo.setSelection(5);break;}
            case 35 : {viewHolder.spinnerDuracCiclo.setSelection(6);break;}
            case 40 : {viewHolder.spinnerDuracCiclo.setSelection(7);break;}
            case 45 : {viewHolder.spinnerDuracCiclo.setSelection(8);break;}
            case 50 : {viewHolder.spinnerDuracCiclo.setSelection(9);break;}
        }
        switch (tomate.getShortBreakTime() / 60 / 1000){
            case 5 : {viewHolder.spinnerIntervCurto.setSelection(0);break;}
            case 10 : {viewHolder.spinnerIntervCurto.setSelection(1);break;}
            case 15 : {viewHolder.spinnerIntervCurto.setSelection(2);break;}
            case 20 : {viewHolder.spinnerIntervCurto.setSelection(3);break;}
            case 25 : {viewHolder.spinnerIntervCurto.setSelection(4);break;}
        }
        switch (tomate.getLongBreakTime() / 60 / 1000){
            case 5 : {viewHolder.spinnerIntervLongo.setSelection(0);break;}
            case 10 : {viewHolder.spinnerIntervLongo.setSelection(1);break;}
            case 15 : {viewHolder.spinnerIntervLongo.setSelection(2);break;}
            case 20 : {viewHolder.spinnerIntervLongo.setSelection(3);break;}
            case 25 : {viewHolder.spinnerIntervLongo.setSelection(4);break;}
        }
        switch (tomate.getBeepAlarm()){
            case 0: viewHolder.spinnerAlarm.setSelection(0); break;
            case 1: viewHolder.spinnerAlarm.setSelection(1); break;
            case 2: viewHolder.spinnerAlarm.setSelection(2); break;
            case 3: viewHolder.spinnerAlarm.setSelection(3); break;
            case 4: viewHolder.spinnerAlarm.setSelection(4); break;
        }
    }

    private void createArrayAdapters(){
        ArrayAdapter<CharSequence> spinnerAlarmAdapter = ArrayAdapter.createFromResource(this, R.array.brew_array, android.R.layout.simple_spinner_dropdown_item);
        setStaticAdapter(spinnerAlarmAdapter, viewHolder.spinnerAlarm);
        ArrayAdapter<CharSequence> quantCiclosAdapter = ArrayAdapter.createFromResource(this, R.array.array_quant_ciclos, android.R.layout.simple_spinner_dropdown_item);
        setStaticAdapter(quantCiclosAdapter, viewHolder.spinnerQuantCiclos);
        ArrayAdapter<CharSequence> duracCiclosAdapter = ArrayAdapter.createFromResource(this, R.array.array_durac_ciclos, android.R.layout.simple_spinner_dropdown_item);
        setStaticAdapter(duracCiclosAdapter, viewHolder.spinnerDuracCiclo);
        ArrayAdapter<CharSequence> intervCurtoAdapter = ArrayAdapter.createFromResource(this, R.array.array_interv_curto, android.R.layout.simple_spinner_dropdown_item);
        setStaticAdapter(intervCurtoAdapter, viewHolder.spinnerIntervCurto);
        ArrayAdapter<CharSequence> intervLongoAdapter = ArrayAdapter.createFromResource(this, R.array.array_interv_longo, android.R.layout.simple_spinner_dropdown_item);
        setStaticAdapter(intervLongoAdapter, viewHolder.spinnerIntervLongo);
    }

    private void setStaticAdapter(ArrayAdapter adapter, Spinner spinner){
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
    }

    public Tomate getTomate() {
        return tomate;
    }

    class ViewHolder {
        Spinner spinnerAlarm, spinnerQuantCiclos, spinnerDuracCiclo, spinnerIntervCurto, spinnerIntervLongo;
        TextView textViewQuantCiclos, textViewDuracCiclo, textViewIntervCurto, textViewIntervLongo;
        Button buttonPlayTheChoice;

        ViewHolder() {
            initSpinners();
            initButtons();
            initTextViews();
        }

        private void initButtons() {
            buttonPlayTheChoice = findViewById(R.id.button_play_the_choice);
            buttonPlayTheChoice.setOnClickListener(new ButtonPlayTheChoiceListener());
        }

        private void initSpinners() {
            spinnerQuantCiclos = findViewById(R.id.spinner_quant_ciclos);
            spinnerDuracCiclo = findViewById(R.id.spinner_durac_ciclo);
            spinnerIntervCurto = findViewById(R.id.spinner_interv_curto);
            spinnerIntervLongo = findViewById(R.id.spinner_interv_longo);
            spinnerAlarm = findViewById(R.id.spinner_alarm);
        }

        private void initTextViews() {
            textViewQuantCiclos = findViewById(R.id.text_quant_ciclos);
            textViewDuracCiclo = findViewById(R.id.text_durac_ciclo);
            textViewIntervCurto = findViewById(R.id.text_interv_curto);
            textViewIntervLongo = findViewById(R.id.text_interv_longo);
        }

    }

    class ButtonPlayTheChoiceListener implements View.OnClickListener{
        MediaPlayer mp = MediaPlayer.create(SettingsActivity.this,R.raw.zvuk_budilnika);

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int position  = viewHolder.spinnerAlarm.getSelectedItemPosition();
            switch (position){
                case 0 :{ playBeepAlarm(R.raw.zvuk_budilnika); break; }
                case 1 :{ playBeepAlarm(R.raw.alarm_clock_nice); break; }
                case 2 :{ playBeepAlarm(R.raw.nineoneone_pager_tone); break; }
                case 3 :{ playBeepAlarm(R.raw.zvonok_starogo_budilnika); break; }
                case 4 :{ playBeepAlarm(R.raw.alarm_clock_beep); break; }
            }
        }

        private void playBeepAlarm(int beepAlarm) {
            if (mp.isPlaying()){ mp.stop(); }
            switch (beepAlarm){
                case R.raw.zvonok_starogo_budilnika: mp = MediaPlayer.create(SettingsActivity.this, R.raw.zvonok_starogo_budilnika); break;
                case R.raw.zvuk_budilnika: mp = MediaPlayer.create(SettingsActivity.this, R.raw.zvuk_budilnika); break;
                case R.raw.alarm_clock_beep: mp = MediaPlayer.create(SettingsActivity.this, R.raw.alarm_clock_beep); break;
                case R.raw.alarm_clock_nice: mp = MediaPlayer.create(SettingsActivity.this, R.raw.alarm_clock_nice); break;
                case R.raw.nineoneone_pager_tone: mp = MediaPlayer.create(SettingsActivity.this, R.raw.nineoneone_pager_tone); break;
            }
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mp.start();
        }
    }

    private class SpinnerDuracSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:{ getTomate().setPomodoroTime(5); break; }
                case 1:{ getTomate().setPomodoroTime(10); break; }
                case 2:{ getTomate().setPomodoroTime(15); break; }
                case 3:{ getTomate().setPomodoroTime(20); break; }
                case 4:{ getTomate().setPomodoroTime(25); break; }
                case 5:{ getTomate().setPomodoroTime(30); break; }
                case 6:{ getTomate().setPomodoroTime(35); break; }
                case 7:{ getTomate().setPomodoroTime(40); break; }
                case 8:{ getTomate().setPomodoroTime(45); break; }
                case 9:{ getTomate().setPomodoroTime(50); break; }
            }
            makeToastToPomodoroTime();
        }

        private void makeToastToPomodoroTime() {
            String msg = getString(R.string.your_pomodoro) +" " + (getTomate().getPomodoroTime() / 60 / 1000) +" " + getString(R.string.text_minutos);
            Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class SpinnerIntervCurtoSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:{ getTomate().setShortBreakTime(5); break; }
                case 1:{ getTomate().setShortBreakTime(10); break; }
                case 2:{ getTomate().setShortBreakTime(15); break; }
                case 3:{ getTomate().setShortBreakTime(20); break; }
                case 4:{ getTomate().setShortBreakTime(25); break; }
            }
            makeToastToShortBreakTime();
        }

        private void makeToastToShortBreakTime() {
            String msg = getString(R.string.your_shortbreak) +" " + (getTomate().getShortBreakTime() / 60 / 1000) +" " + getString(R.string.text_minutos);
            Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class SpinnerIntervLongoSelectedListener implements AdapterView.OnItemSelectedListener {
        /**
         * <p>Callback method to be invoked when an item in this view has been
         * selected. This callback is invoked only when the newly selected
         * position is different from the previously selected position or if
         * there was no selected item.</p>
         * <p>
         * Implementers can call getItemAtPosition(position) if they need to access the
         * data associated with the selected item.
         *
         * @param parent   The AdapterView where the selection happened
         * @param view     The view within the AdapterView that was clicked
         * @param position The position of the view in the adapter
         * @param id       The row id of the item that is selected
         */
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (position){
                case 0:{ getTomate().setLongBreakTime(5); break; }
                case 1:{ getTomate().setLongBreakTime(10); break; }
                case 2:{ getTomate().setLongBreakTime(15); break; }
                case 3:{ getTomate().setLongBreakTime(20); break; }
                case 4:{ getTomate().setLongBreakTime(25); break; }
            }
            makeToastToLongBreakTime();
        }

        private void makeToastToLongBreakTime() {
            String msg = getString(R.string.your_longbreak) +" " + (getTomate().getLongBreakTime() / 60 / 1000) +" " + getString(R.string.text_minutos);
            Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

        /**
         * Callback method to be invoked when the selection disappears from this
         * view. The selection can disappear for instance when touch is activated
         * or when the adapter becomes empty.
         *
         * @param parent The AdapterView that now contains no selected item.
         */
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    private class SpinnerQuantCiclosSelectedListener implements
            AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            switch (position) {
                case 0: { getTomate().setCiclosTime(1); break; }
                case 1: { getTomate().setCiclosTime(2); break; }
                case 2: { getTomate().setCiclosTime(3); break; }
                case 3: { getTomate().setCiclosTime(4); break; }
                case 4: { getTomate().setCiclosTime(5); break; }
                case 5: { getTomate().setCiclosTime(6); break; }
                case 6: { getTomate().setCiclosTime(7); break; }
                case 7: { getTomate().setCiclosTime(8); break; }
            }
            makeToastToCiclosTime();
        }

        private void makeToastToCiclosTime() {
            String msg = getString(R.string.you_will_have) +" " + (getTomate().getCiclosTime() / 60 / 1000) + " " + getString(R.string.text_ciclos);
            Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    class SpinnerAlarmSelectedListener implements AdapterView.OnItemSelectedListener {

        /**
         * <p>Callback method to be invoked when an item in this view has been
         * selected. This callback is invoked only when the newly selected
         * position is different from the previously selected position or if
         * there was no selected item.</p>
         * <p>
         * Implementers can call getItemAtPosition(position) if they need to access the
         * data associated with the selected item.
         *
         * @param parent   The AdapterView where the selection happened
         * @param view     The view within the AdapterView that was clicked
         * @param position The position of the view in the adapter
         * @param id       The row id of the item that is selected
         */
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.v("item", (String) parent.getItemAtPosition(position));
            switch (position){
                case 0: getTomate().setBeepAlarm(0);break;
                case 1: getTomate().setBeepAlarm(1);break;
                case 2: getTomate().setBeepAlarm(2);break;
                case 3: getTomate().setBeepAlarm(3);break;
                case 4: getTomate().setBeepAlarm(4);break;
            }
            Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        }

        /**
         * Callback method to be invoked when the selection disappears from this
         * view. The selection can disappear for instance when touch is activated
         * or when the adapter becomes empty.
         *
         * @param parent The AdapterView that now contains no selected item.
         */
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
