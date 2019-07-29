package io.github.reginildo.tomateapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public MainActivityPlaceHolder placeHolder;

    public static Tomate tomate = new Tomate(4, 25, 15, 5, R.raw.zvuk_budilnika);
    private static int countInteractions = 1;
    public static long timeInFuture = tomate.getPomodoroTime();
    private static boolean timeToShortBreak = false;
    private static boolean timeToLongBreak = false;
    private static boolean timeToPomodoro = false;
    //private static boolean timeToEnd = false;
    private static final String EXTRA_TOMATE = "tomate";

    //public enum Finalizacao {FINAL_YES, FINAL_NO};

    Button buttonSetttings, buttonStart, buttonStop;
    TextView textViewTimerCounter;
    TomateCountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placeHolder = new MainActivityPlaceHolder();
        textViewTimerCounter = findViewById(R.id.timer_counter);
        buttonStop = findViewById(R.id.button_stop);
        buttonStop.setOnClickListener(new ButtonStopListener());
        buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new ButtonStartListener());

        buttonSetttings = findViewById(R.id.button_settings);
        buttonSetttings.setOnClickListener(new ButtonSetttingsListener());
        countDownTimer = new TomateCountDownTimer(textViewTimerCounter, timeInFuture, 1000);

        textViewTimerCounter.setText(placeHolder.getCorrectTimerOnCreate());
    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewTimerCounter.setText(placeHolder.getPomodoroCorrectTimer());

    }

    static String getCorrectTimer(boolean isMinute, long millisUntilFinished) {
        String aux;
        int constCalendar = isMinute ? Calendar.MINUTE : Calendar.SECOND;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisUntilFinished);
        aux = calendar.get(constCalendar) < 10 ? "0" + calendar.get(constCalendar) : "" + calendar.get(constCalendar);
        return (aux);
    }

    public static boolean isTimeToShortBreak() {
        if ((countInteractions % 2 == 0) && (countInteractions !=8) && (countInteractions !=16)) {
            timeToPomodoro = false;
            timeToLongBreak = false;
            timeToShortBreak = true;
        } else {
            timeToShortBreak = false;
        }
        return timeToShortBreak;
    }

    public static boolean isTimeToLongBreak() {
        if (countInteractions == 8 || countInteractions == 16) {
            timeToShortBreak = false;
            timeToPomodoro = false;
            timeToLongBreak = true;
        } else {
            timeToLongBreak = false;
        }
        return timeToLongBreak;
    }

    public static boolean isTimeToPomodoro() {
        if (countInteractions % 2 != 0) {
            timeToLongBreak = false;
            timeToShortBreak  = false;
            timeToPomodoro = true;
        } else {
            timeToPomodoro = false;
        }
        return timeToPomodoro;
    }

    /*
    public static boolean isTimeToEnd() {
        if (countInteractions == tomate.getCiclosTime()){
            timeToEnd = true;
        }
        return timeToEnd;
    }
    */

    /*
    private void initCorrectCountDownTimer() {
        if (isTimeToShortBreak()) {
            setTimeToShortBreak(false);
            countInteractions++;
            countDownTimer.start();
        } else if (isTimeToLongBreak()) {
            setTimeToLongBreak(false);
            countInteractions++;
            countDownTimer.start();
        } else if (isTimeToPomodoro()) {
            setTimeToPomodoro(false);
            countInteractions++;
            countDownTimer.start();
        } else if (isTimeToEnd()) {
            setTimeToEnd(false);
            countInteractions++;
            textViewTimerCounter.setText("Fim");
        }
    }
    */

    private class ButtonSetttingsListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle parametros = new Bundle();
            parametros.putSerializable(EXTRA_TOMATE, tomate);
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.putExtras(parametros);
            startActivity(intent);
        }
    }


    @SuppressLint("ValidFragment")
    public class FinalizeDialogFragment extends DialogFragment {

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            countDownTimer.start();
                        }
                    }
                };
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setTitle(R.string.init_pomodoro)
                    .setMessage(R.string.msg_init_pomodoro)
                    .setPositiveButton(R.string.yes, listener)
                    .setNegativeButton(R.string.no, null)
                    .create();
        }
    }

    private class ButtonStartListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                timeInFuture = tomate.getPomodoroTime();
                startCountDownTimer();

            } else {
                countDownTimer = new TomateCountDownTimer(textViewTimerCounter, timeInFuture, 1000);
                startCountDownTimer();
            }
        }
    }
    private void startCountDownTimer() {
        countDownTimer = new TomateCountDownTimer(textViewTimerCounter, timeInFuture, 1000);
        countDownTimer.start();
    }

    private class ButtonStopListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = new TomateCountDownTimer(textViewTimerCounter, timeInFuture, 1000);
                textViewTimerCounter.setText(placeHolder.getPomodoroCorrectTimer());
            }
        }
    }

    private class TomateCountDownTimer extends CountDownTimer {
        TomateCountDownTimer(TextView tv, long millisInFuture,
                             long countDownInterval) {
            super(millisInFuture, countDownInterval);
            textViewTimerCounter = tv;
        }

        /**
         * Callback fired on regular interval.
         *
         * @param millisUntilFinished The amount of time until finished.
         */
        @Override
        public void onTick(long millisUntilFinished) {
            Log.i("Script", "Timer: " + millisUntilFinished);
            timeInFuture = millisUntilFinished;
            textViewTimerCounter.setText(placeHolder.getCorrectTimerOnTick(millisUntilFinished));
        }

        /**
         * Callback fired when the time is up.
         */
        public void onFinish() {
            countInteractions++;
            callTheNextTimer();
            playAlarm();
        }
    }

    private void playAlarm() {
        MediaPlayer mp;
        switch (tomate.getBeepAlarm()){
            case 0 : mp = MediaPlayer.create(MainActivity.this,R.raw.zvuk_budilnika); break;
            case 1 : mp = MediaPlayer.create(MainActivity.this,R.raw.alarm_clock_beep); break;
            case 2 : mp = MediaPlayer.create(MainActivity.this,R.raw.alarm_clock_nice); break;
            case 3 : mp = MediaPlayer.create(MainActivity.this,R.raw.nineoneone_pager_tone); break;
            case 4 : mp = MediaPlayer.create(MainActivity.this,R.raw.zvonok_starogo_budilnika); break;
            default: mp = MediaPlayer.create(MainActivity.this,R.raw.zvuk_budilnika);
        }
        mp.start();
    }

    private void callTheNextTimer() {
        if (isTimeToShortBreak()){
            Toast.makeText(MainActivity.this, R.string.hora_interv_curto, Toast.LENGTH_SHORT).show();
            timeInFuture = tomate.getShortBreakTime();
            Log.i("Script", "Interaction Short Break" + Integer.toString(countInteractions));
        } else if(isTimeToPomodoro()) {
            Toast.makeText(MainActivity.this,R.string.hora_pomodoro,Toast.LENGTH_SHORT).show();
            timeInFuture = tomate.getPomodoroTime();
            Log.i("Script", "Interaction Pomo" + Integer.toString(countInteractions));
            // todo é aqui que te que aparecer o popup pedindo pra iniciar um novo pomodoro
            new FinalizeDialogFragment();
        }  else if (isTimeToLongBreak()){
            countInteractions = 0;
            Toast.makeText(MainActivity.this, R.string.hora_interv_longo, Toast.LENGTH_SHORT).show();
            timeInFuture = tomate.getLongBreakTime();
            Log.i("Script", "Interaction Long Break" + Integer.toString(countInteractions));
            // todo verificar aqui se o usuario quer iniciar um novo ciclo com um popup
        }
        startCountDownTimer();
    }

    private class MainActivityPlaceHolder{
        String pomodoroCorrectTimer, correctTimerOnStart, correctTimerOnTick;

        String getPomodoroCorrectTimer() {
            pomodoroCorrectTimer = getCorrectTimer(true,
                    tomate.getPomodoroTime()) + ":" + getCorrectTimer(false, tomate.getPomodoroTime());
            return pomodoroCorrectTimer;
        }

        String getCorrectTimerOnCreate() {
            correctTimerOnStart = getCorrectTimer(true, timeInFuture) + ":" + getCorrectTimer(false, timeInFuture);
            return correctTimerOnStart;
        }

        String getCorrectTimerOnTick(long millisUntilFinished) {
            correctTimerOnTick = getCorrectTimer(true,
                    millisUntilFinished) + ":" + getCorrectTimer(false, millisUntilFinished);
            return correctTimerOnTick;
        }
    }
}