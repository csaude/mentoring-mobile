package mz.org.fgh.mentoring.dialog;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mz.org.fgh.mentoring.AlertListner;
import mz.org.fgh.mentoring.R;

public class AlertDialogManager {

    private Context context;

    public AlertDialogManager(Context context) {
        this.context = context;
    }

    public void showAlert(String message, final AlertListner listner) {

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_alert)
                .setCancelable(Boolean.FALSE).create();

        dialog.show();

        TextView messageTxt = dialog.findViewById(R.id.dialog_alert_message);
        messageTxt.setText(message);

        Button yesBtn = dialog.findViewById(R.id.dialog_alert_yes_btn);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                if (listner != null) {
                    listner.perform();
                }
            }
        });

        Button noBtn = dialog.findViewById(R.id.dialog_alert_no_btn);

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void showAlert(String message) {

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(R.layout.dialog_alert)
                .setCancelable(Boolean.FALSE).create();

        dialog.show();

        TextView messageTxt = dialog.findViewById(R.id.dialog_alert_message);
        messageTxt.setText(message);

        Button yesBtn = dialog.findViewById(R.id.dialog_alert_yes_btn);
        yesBtn.setVisibility(View.INVISIBLE);

        Button noBtn = dialog.findViewById(R.id.dialog_alert_no_btn);
        noBtn.setText(context.getString(R.string.ok));

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
