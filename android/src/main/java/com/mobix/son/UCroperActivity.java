package com.mobix.son;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;
import java.io.File;


public class UCroperActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        try {
            String source_uri = getIntent().getStringExtra(Constants.SOURCE_URI);
            String file_name = getIntent().getStringExtra(Constants.FILE_NAME);
            Uri sourceUri = Uri.parse(source_uri);
            UCrop.Options options = new UCrop.Options();
            options.setFreeStyleCropEnabled(true);
            options.setMaxScaleMultiplier((float) 150);
            options.setToolbarTitle("裁剪");
            options.setToolbarColor(Color.parseColor("#FFFFFF"));
            options.setToolbarWidgetColor(Color.parseColor("#000000"));
            options.setStatusBarColor(Color.parseColor("#FFFFFF"));
            options.setActiveControlsWidgetColor(Color.parseColor("#1890FF"));
            options.setLogoColor(Color.parseColor("#FFFFFF"));
            UCrop of = UCrop.of(sourceUri, Uri.fromFile(new File(getCacheDir(), file_name)));
            of.withOptions(options);
            of.start(UCroperActivity.this, UCrop.REQUEST_CROP);
        }catch (Exception e){
            Log.e("ERROR ",e.getMessage());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP && data != null) {
            final Uri resultUri = UCrop.getOutput(data);
            Intent returnIntent = new Intent();
            returnIntent.putExtra(Constants.RESULT_URI, resultUri.toString());
            setResult(RESULT_OK,returnIntent);
            finish();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            finish();
        }else{
            setResult(RESULT_CANCELED, null);
            finish();
        }
    }

}
