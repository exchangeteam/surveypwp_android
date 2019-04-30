package com.berkethetechnerd.surveypwp;

import android.support.v7.app.AppCompatActivity;

import com.kaopiz.kprogresshud.KProgressHUD;

abstract class BaseActivity extends AppCompatActivity {

    KProgressHUD hud;

    protected void showIndicator() {
        hud = new KProgressHUD(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    protected void setIndicatorDetail(String indicatorDetail) {
        if(hud != null) {
            hud.setDetailsLabel(indicatorDetail);
        }
    }

    protected void hideIndicator() {
        hud.dismiss();
        hud = null;
    }
}
