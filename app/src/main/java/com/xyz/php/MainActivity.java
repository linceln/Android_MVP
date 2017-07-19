package com.xyz.php;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.xyz.core.http.DoTransform;
import com.xyz.core.http.HttpSubscriber;
import com.xyz.php.business.entity.BMIEntity;
import com.xyz.php.business.request.PhpRequest;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etSex;
    private EditText etAge;
    private EditText etHeight;
    private EditText etWeight;
    private TextView tvBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
    }

    private void initUI() {
        etName = (EditText) findViewById(R.id.etName);
        etSex = (EditText) findViewById(R.id.etSex);
        etAge = (EditText) findViewById(R.id.etAge);
        etHeight = (EditText) findViewById(R.id.etHeight);
        etWeight = (EditText) findViewById(R.id.etWeight);
        tvBMI = (TextView) findViewById(R.id.tvBMI);
        RxView.clicks(findViewById(R.id.btn))
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        submit();
                    }
                });
    }

    private void submit() {
        if (checkNotNull()) {
            String name = etName.getText().toString().trim();
            String sex = etSex.getText().toString().trim();
            String age = etAge.getText().toString().trim();
            String height = etHeight.getText().toString().trim();
            String weight = etWeight.getText().toString().trim();

            BMIEntity bmiEntity = new BMIEntity();
            bmiEntity.name = name;
            bmiEntity.sex = sex;
            bmiEntity.age = age;
            bmiEntity.height = height;
            bmiEntity.weight = weight;
            PhpRequest.calculateBMI(bmiEntity)
                    .compose(DoTransform.<BMIEntity>applyScheduler())
                    .subscribe(new HttpSubscriber<BMIEntity>(this) {
                        @Override
                        protected void onSuccess(BMIEntity entity) {
                            tvBMI.setText("BMI: " + entity.bmi);
                            Toast.makeText(MainActivity.this, entity.message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        protected void onFail(String msg) {
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean checkNotNull() {
        return true;
    }

    private void initData() {
    }
}
