package com.mlkt.development.messages.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mlkt.development.messages.R;

import java.util.concurrent.Callable;

public class DialogUtils {

    public static void showErrorDialog(Activity activity, String text, Callable<Void> action) {
        makeDialog(activity, DialogType.ERROR.getValue(), text, action, null, null,null);
    }

    public static void showInfoDialog(Activity activity, String text, Callable<Void> action) {
        makeDialog(activity, DialogType.INFO.getValue(), text, action, null,null,null);
    }

    public static void showHintDialog(Activity activity, String text, Callable<Void> action) {
        makeDialog(activity, DialogType.HINT.getValue(), text, action, null,null,null);
    }

    public static void showConfirmDialog(Activity activity, String text, Callable<Void> positiveAction, Callable<Void> negativeAction) {
        makeDialog(activity, DialogType.CONFIRM.getValue(), text, positiveAction, negativeAction, null,null);
    }

    public static void showInputDialog(Activity activity, String message, Callable<Void> positiveAction, Callable<Void> negativeAction, String text, InputDialogListener listener) {
        makeDialog(activity, DialogType.INPUT.getValue(), message, positiveAction, negativeAction, text, listener);
    }

    enum DialogType{
        ERROR(0),
        INFO(1),
        HINT(2),
        CONFIRM(3),
        INPUT(4);

        private final int value;
        private DialogType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public interface InputDialogListener{
        void onTextChanged(String text);
    }

    private static void makeDialog(Activity activity, int dialogType, String message, final Callable<Void> positiveAction, final Callable<Void> negativeAction, String inputText, final InputDialogListener listener){

        final Dialog dialog = new Dialog(activity, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.layout_dialog);

        TextView title = dialog.findViewById(R.id.title);
        ImageView icon = dialog.findViewById(R.id.icon);
        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(message);

        final DialogType dt = DialogType.values()[dialogType];

        final EditText input = dialog.findViewById(R.id.input);
        if (dt == DialogType.INPUT && inputText != null && !inputText.isEmpty()) {
            input.setText(inputText);
            input.setSelection(inputText.length());
        }

        Button ok = dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (positiveAction != null) {
                    try {
                        positiveAction.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (dt == DialogType.INPUT && listener != null && !input.getText().toString().isEmpty()) {
                    listener.onTextChanged(input.getText().toString());
                }
            }
        });

        Button cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (negativeAction != null) {
                    try {
                        negativeAction.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        View buttonSeparator = dialog.findViewById(R.id.button_separator);

        switch (dt) {
            case ERROR:
                title.setText("Error");
                icon.setImageResource(R.drawable.ic_error_dialog);
                break;
            case INFO:
                title.setText("Info");
                icon.setImageResource(R.drawable.ic_info_dialog);
                break;
            case HINT:
                title.setText("Hint");
                icon.setImageResource(R.drawable.ic_hint_dialog);
                break;
            case CONFIRM:
                title.setText("Confirm");
                icon.setImageResource(R.drawable.ic_info_dialog);
                cancel.setVisibility(View.VISIBLE);
                buttonSeparator.setVisibility(View.VISIBLE);
                break;
            case INPUT:
                title.setText("Input");
                icon.setImageResource(R.drawable.ic_info_dialog);
                cancel.setVisibility(View.VISIBLE);
                input.setVisibility(View.VISIBLE);
                buttonSeparator.setVisibility(View.VISIBLE);
                break;
        }

        dialog.show();
    }

}

