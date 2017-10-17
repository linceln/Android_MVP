package com.xyz.php.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xyz.php.R;
import com.xyz.php.abs.IPhpView;
import com.xyz.php.models.entity.BMIEntity;
import com.xyz.php.presenters.PhpPresenter;

public class MainActivity extends AppCompatActivity implements IPhpView, View.OnClickListener {

    private EditText etName;
    private EditText etSex;
    private EditText etAge;
    private EditText etHeight;
    private EditText etWeight;
    private TextView tvBMI;

    private PhpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new PhpPresenter(this);
    }

    @Override
    public void initUI() {
        etName = (EditText) findViewById(R.id.etName);
        etSex = (EditText) findViewById(R.id.etSex);
        etAge = (EditText) findViewById(R.id.etAge);
        etHeight = (EditText) findViewById(R.id.etHeight);
        etWeight = (EditText) findViewById(R.id.etWeight);
        tvBMI = (TextView) findViewById(R.id.tvBMI);
        findViewById(R.id.btn).setOnClickListener(this);
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

    @Override
    public void onRequestSuccess(BMIEntity entity) {
        tvBMI.setText(entity.bmi);
    }

    @Override
    public void onRequestFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        BMIEntity userInput = getUserInput();
        presenter.submitBmi(userInput);
    }
}
