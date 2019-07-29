package io.github.reginildo.tomateapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class FinalTimerFragment extends DialogFragment {

    public static final String DIALOG_TAG = "editDialog";
    public static final String EXTRA_TIMER = "timer";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == DialogInterface.BUTTON_NEGATIVE) {

                }else{

                }
            }
        };

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.fim_tempo)
                .setMessage(R.string.sobre_fim_mensagem)
                .setPositiveButton(android.R.string.yes, null)
                .setNegativeButton(R.string.no, listener)
                .create();
        return dialog;
    }
    public interface AoFinalizarTimer {
        void finalizouTimer();
    }
}

