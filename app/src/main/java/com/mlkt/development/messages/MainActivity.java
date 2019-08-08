package com.mlkt.development.messages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mlkt.development.messages.utils.DialogUtils;
import com.mlkt.development.messages.utils.ToastUtils;

import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Click(final View v) {

        switch (v.getTag().toString()) {
            case "error":
                DialogUtils.showErrorDialog(this, "Error dialog message", null);
                break;
            case "info":
                DialogUtils.showInfoDialog(this,"Info dialog message",null);
                break;
            case "hint":
                DialogUtils.showHintDialog(this,"Hint dialog message",null);
                break;
            case "confirm":
                DialogUtils.showConfirmDialog(this, "Confirm dialog message", new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        ToastUtils.showSuccessToast(getApplicationContext(),getLayoutInflater(),v,"Confirmed");
                        return null;
                    }
                }, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        ToastUtils.showErrorToast(getApplicationContext(),getLayoutInflater(),v,"Declined");
                        return null;
                    }
                });
                break;
            case "input":
                DialogUtils.showInputDialog(this, "Input dialog message", null, null,"text", new DialogUtils.InputDialogListener() {
                    @Override
                    public void onTextChanged(String text) {
                        ToastUtils.showInfoToast(getApplicationContext(),getLayoutInflater(),v,"You typed: "+text);
                    }
                });
                break;
            case "toast":
                ToastUtils.showInfoToast(getApplicationContext(),getLayoutInflater(),v,"Simple Toast message");
                break;
            case "toast_success":
                ToastUtils.showSuccessToast(getApplicationContext(),getLayoutInflater(),v,"Success Toast message");
                break;
            case "toast_error":
                ToastUtils.showErrorToast(getApplicationContext(),getLayoutInflater(),v,"Error Toast message");
                break;
        }

    }
}
