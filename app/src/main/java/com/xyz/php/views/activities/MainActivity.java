package com.xyz.php.views.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xyz.core.base.BaseEntity;
import com.xyz.php.R;
import com.xyz.php.abs.presenters.IPhpPresenter;
import com.xyz.php.abs.views.IPhpView;
import com.xyz.php.entities.BMIEntity;
import com.xyz.php.presenters.PhpPresenterTest;

public class MainActivity extends AppCompatActivity implements IPhpView, View.OnClickListener {

    private EditText etName;
    private EditText etSex;
    private EditText etAge;
    private EditText etHeight;
    private EditText etWeight;
    private TextView tvBMI;

    private IPhpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new PhpPresenterTest(this);
    }

    @Override
    public void initUI() {
        etName = findViewById(R.id.etName);
        etSex = findViewById(R.id.etSex);
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        tvBMI = findViewById(R.id.tvBMI);
        findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.btnGet).setOnClickListener(this);
    }

    @Override
    public void onRequestSuccess(BaseEntity entity) {
        Toast.makeText(this, entity.code, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubmitSuccess(BMIEntity entity) {
//        tvBMI.setText(entity.bmi);
        Toast.makeText(this, entity.msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toastNegative(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                presenter.submitBmi();
                break;

            case R.id.btnGet:
                tvBMI.setText(presenter.getDBMobile());
                break;
        }
    }

    @Override
    public BMIEntity getUserInput() {

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

        return bmiEntity;
    }
}