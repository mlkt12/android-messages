package com.mlkt.development.messages.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mlkt.development.messages.R;

public class ToastUtils {

    public static void showInfoToast(Context context, LayoutInflater inflater, View relative, String toastText){
        makeToast(ToastType.INFO.getValue(),context,inflater,relative,toastText);
    }

    public static void showSuccessToast(Context context, LayoutInflater inflater, View relative, String toastText){
        makeToast(ToastType.SUCCESS.getValue(),context,inflater,relative,toastText);
    }

    public static void showErrorToast(Context context, LayoutInflater inflater, View relative, String toastText){
        makeToast(ToastType.ERROR.getValue(),context,inflater,relative,toastText);
    }

    enum ToastType{
        INFO(0),
        SUCCESS(1),
        ERROR(2);

        private final int value;
        private ToastType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private static void makeToast(int type, Context context, LayoutInflater inflater, View relative, String toastText) {
        View layout = inflater.inflate(R.layout.layout_toast,
                (ViewGroup) relative.findViewById(R.id.toast_layout_root));

        ImageView image = layout.findViewById(R.id.image);

        ToastType dt = ToastType.values()[type];
        switch (dt) {
            case INFO:
                image.setVisibility(View.GONE);
                break;
            case SUCCESS:
                image.setImageResource(R.drawable.ic_toast_success);
                break;
            case ERROR:
                image.setImageResource(R.drawable.ic_toast_error);
                break;
        }

        TextView text = layout.findViewById(R.id.text);
        text.setText(toastText);

        final Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
