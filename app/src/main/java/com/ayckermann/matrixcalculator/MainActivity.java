package com.ayckermann.matrixcalculator;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int RowA,ColA, RowB,ColB;
    int count,i,j;
    Double arrayA[][] , arrayB[][];
    Matrix matrixA = new Matrix(), matrixB = new Matrix(), matrixResult = new Matrix();
    String strRowA,strColA,strRowB,strColB;
    ArrayAdapter<CharSequence> adapter;

    EditText edtRowA, edtColA, edtRowB, edtColB;
    Button btnAddB,btnAddA,Show;
    TableLayout tblInputA,tblInputB,TabLayout_Show;
    Spinner spinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();

        btnAddA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( TextUtils.isEmpty(edtRowA.getText()) | TextUtils.isEmpty(edtColA.getText())){
                    if(TextUtils.isEmpty(edtRowA.getText())){
                        edtRowA.setError("Input number 1 - 5");
                    }
                    else {
                        edtColA.setError("Input number 1 - 5");
                    }
                }
                else {
                    setBtnAddA();
                }
            }
        } );

        btnAddB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( TextUtils.isEmpty(edtRowB.getText()) | TextUtils.isEmpty(edtColB.getText())){
                    if(TextUtils.isEmpty(edtRowB.getText())){
                        edtRowB.setError("Input number 1 - 5");
                    }
                    else {
                        edtColB.setError("Input number 1 - 5");
                    }
                }
                else {
                    setBtnAddB();
                }
            }
        } );

        spinner.setOnItemSelectedListener(this);

        Show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setShow();
            }
        });

    }

    public void initComponent(){
        edtRowA=findViewById(R.id.edtRowA);
        edtColA=findViewById(R.id.edtColA);
        btnAddA=findViewById(R.id.btnAddA);

        edtColB=findViewById(R.id.edtRowB);
        edtRowB=findViewById(R.id.edtColB);
        btnAddB=findViewById(R.id.btnAddB);

        Show=findViewById(R.id.Show);
        tblInputA= findViewById(R.id.tblInputA);
        tblInputB=findViewById(R.id.tblInputB);
        TabLayout_Show=findViewById(R.id.TableLayout2);

        spinner =findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.operation,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        edtRowA.setFilters(new InputFilter[]{new InputFilterMinMax("1","5")});
        edtColA.setFilters(new InputFilter[]{new InputFilterMinMax("1","5")});
        edtRowB.setFilters(new InputFilter[]{new InputFilterMinMax("1","5")});
        edtColB.setFilters(new InputFilter[]{new InputFilterMinMax("1","5")});
    }

    public void setBtnAddA(){

        strRowA=edtRowA.getText().toString();
        strColA=edtColA.getText().toString();

        RowA=Integer.parseInt(strRowA);
        ColA=Integer.parseInt(strColA);
        arrayA = new Double[RowA][ColA];


        for( i=0;i<RowA;i++)
        {
            final TableRow row = new TableRow(MainActivity.this);

            for(j=0;j<ColA;j++)
            {
                final EditText txt=new EditText(MainActivity.this);
                txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                txt.setWidth(150);
                txt.setTextColor(Color.BLACK);
                txt.setHintTextColor(Color.rgb(173, 181, 189));
                txt.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);
                txt.setTypeface(Typeface.SERIF, Typeface.BOLD);
                txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                txt.setHint("A" +(i+1)+(j+1)+"  ");
                row.addView(txt);

            }
            tblInputA.addView(row);

        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutButttonA);
        final Button create = new Button(MainActivity.this);
        create.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        create.setText("Create Matrix A");
        layout.addView(create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArrayA();
                matrixA = new Matrix(RowA,ColA,arrayA);
                Toast.makeText(view.getContext(), "Matrix A Created", Toast.LENGTH_SHORT).show();
            }
        });

        final Button delete = new Button(MainActivity.this);
        delete.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        delete.setText("Delete");
        layout.addView(delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tblInputA.removeAllViews();
                create.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }
        });


    }

    public void getArrayA(){
        for(i=0;i<RowA;i++)
        {
            final TableRow row = (TableRow)tblInputA.getChildAt(i);

            for(j=0;j<ColA;j++)
            {
                final EditText etxt=(EditText)row.getChildAt(j);

                String value = etxt.getText().toString();
                Log.e("cek", value);
                if(value.isEmpty()){
                    arrayA[i][j] = 0.0;
                }
                else{
                    arrayA[i][j] = Double.parseDouble(value);
                }

            }
        }

    }

    public void setBtnAddB(){

        strRowB=edtRowB.getText().toString();
        strColB=edtColB.getText().toString();

        RowB=Integer.parseInt(strRowB);
        ColB=Integer.parseInt(strColB);
        arrayB = new Double[RowB][ColB];



        for( i=0;i<RowB;i++)
        {
            final TableRow row = new TableRow(MainActivity.this);
            for(j=0;j<ColB;j++)
            {
                final EditText txt=new EditText(MainActivity.this);
                txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                txt.setWidth(150);
                txt.setTextColor(Color.BLACK);
                txt.setHintTextColor(Color.rgb(173, 181, 189));
                txt.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);
                txt.setTypeface(Typeface.SERIF, Typeface.BOLD);
                txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                txt.setHint("B" +(i+1)+(j+1)+"  ");
                row.addView(txt);

            }
            tblInputB.addView(row);

        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutButtonB);
        final Button create = new Button(MainActivity.this);
        create.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        create.setText("Create Matrix B");
        layout.addView(create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArrayB();
                matrixB = new Matrix(RowB,ColB,arrayB);
                Toast.makeText(view.getContext(), "Matrix B Created", Toast.LENGTH_SHORT).show();
            }
        });

        final Button delete = new Button(MainActivity.this);
        delete.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        delete.setText("Delete");
        layout.addView(delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tblInputB.removeAllViews();
                create.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }
        });

    }

    public void getArrayB(){
        for(i=0;i<RowB;i++)
        {
            final TableRow row = (TableRow)tblInputB.getChildAt(i);

            for(j=0;j<ColB;j++)
            {
                final EditText etxt=(EditText)row.getChildAt(j);

                String value = etxt.getText().toString();
                if(value.isEmpty()){
                    arrayB[i][j] = 0.0;
                }
                else{
                    arrayB[i][j] = Double.parseDouble(value);
                }
            }
        }

    }

    public void setShow(){
        TabLayout_Show.removeAllViews();
        String test = Integer.toString(spinner.getSelectedItemPosition());
        switch (spinner.getSelectedItemPosition()){
            case 0:
                matrixResult = new Matrix(RowA,ColA);
                if(matrixA.getX() == matrixB.getX() && matrixA.getY() == matrixB.getY()) {
                    matrixResult = matrixA.tambahMatrix(matrixB);
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
                break;
            case 1:
                matrixResult = new Matrix(RowA,ColA);
                if(matrixA.getX() == matrixB.getX() && matrixA.getY() == matrixB.getY()) {
                    matrixResult = matrixA.kurangMatrix(matrixB);
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                matrixResult = new Matrix(RowA,ColA);
                if(matrixA.getX() == matrixB.getX() && matrixA.getY() == matrixB.getY()) {
                    matrixResult = matrixB.kurangMatrix(matrixA);
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                matrixResult = new Matrix(RowA,ColB);
                if(matrixA.getY() == matrixB.getX()) {
                    matrixResult = matrixA.kaliMatrix(matrixB);
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                matrixResult = new Matrix(RowA,ColB);
                if(matrixB.getY() == matrixA.getX()) {
                    matrixResult = matrixB.kaliMatrix(matrixA);
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }

                break;
            case 5:
                matrixResult = new Matrix(ColA,RowA);
                matrixResult = matrixA.transpose();
                break;
            case 6:
                matrixResult = new Matrix(ColB,RowB);
                matrixResult = matrixB.transpose();
                break;
            case 7:
                matrixResult = new Matrix(1,1);
                Double[][] determinantA = new Double[1][1];
                if(matrixA.getX() == matrixA.getY()) {
                    determinantA[0][0] = matrixA.determinan();
                    matrixResult = new Matrix(1,1,determinantA);
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
                break;
            case 8:
                matrixResult = new Matrix(1,1);
                Double[][] determinantB = new Double[1][1];
                if(matrixB.getX() == matrixB.getY()) {
                    determinantB[0][0] = matrixB.determinan();
                    matrixResult = new Matrix(1,1,determinantB);
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
            case 9:
                matrixResult = new Matrix(RowA,ColA);
                if(matrixA.getX() == matrixA.getY()) {
                    matrixResult = matrixA.kofaktor();
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
                break;
            case 10:
                matrixResult = new Matrix(RowB,ColB);
                if(matrixB.getX() == matrixB.getY()) {
                    matrixResult = matrixB.kofaktor();
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
                break;
            case 11:
                matrixResult = new Matrix(RowA,ColA);
                if(matrixA.getX() == matrixA.getY()) {
                    matrixResult = matrixA.adjoin();
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
                break;
            case 12:
                matrixResult = new Matrix(RowB,ColB);
                if(matrixB.getX() == matrixB.getY()) {
                    matrixResult = matrixB.adjoin();
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
                break;
            case 13:
                matrixResult = new Matrix(RowA,ColA);
                if(matrixA.getX() == matrixA.getY()) {
                    matrixResult = matrixA.invers();
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }

                break;
            case 14:
                matrixResult = new Matrix(RowB,ColB);
                if(matrixB.getX() == matrixB.getY()) {
                    matrixResult = matrixB.invers();
                }
                else{
                    Toast.makeText(this, "Ordo Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }


        int row = matrixResult.getX();
        int col = matrixResult.getY();


        for(i=0;i<row;i++)
        {
            final TableRow row1 = new TableRow(MainActivity.this);

            for(j=0;j<col;j++)
            {
                final TextView txt=new TextView(MainActivity.this);
                txt.setTextColor(Color.BLACK);
                txt.setTextSize(TypedValue.COMPLEX_UNIT_PT, 12);
                txt.setTypeface(Typeface.SERIF, Typeface.BOLD);
                txt.setGravity(Gravity.LEFT);
                txt.setText( " " +removeTrailingZeroes(matrixResult.value[i][j].toString()) + " ");
                row1.addView(txt);
            }
            TabLayout_Show.addView(row1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    String removeTrailingZeroes(String s) {
        s = s.contains(".") ? s.replaceAll("0*$","").replaceAll("\\.$","") : s;
        return s;
    }
}
