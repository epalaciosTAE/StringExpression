package com.tae.stringexpresion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @Bind(R.id.userInput)
    EditText etUserInput;
    @Bind(R.id.tvResult)
    TextView tvResult;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new PresenterImpl();
        presenter.initMainView(this);
    }

    @OnClick(R.id.fab)
    public void onClick(View v) {
        String input = etUserInput.getText().toString();
        presenter.makeOperation(input);
    }

    @Override
    public void showResult(String result) {
        tvResult.setText(result);
    }
}
