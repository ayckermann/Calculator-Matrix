package com.ayckermann.matrixcalculator;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
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
                setBtnAddA();
            }
        } );

        btnAddB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtnAddB();
            }
        } );

        spinner.setOnItemSelectedListener(this);

        Show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
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

    }

    public void setBtnAddA(){
        // TODO Auto-generated method stub
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
                txt.setHint("A" +i+j+"  ");
                row.addView(txt);

            }
            tblInputA.addView(row);

        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutViewA);
        final Button create = new Button(MainActivity.this);
        create.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        create.setText("Create Matrix A");
        layout.addView(create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArrayA();
                matrixA = new Matrix(RowA,ColA,arrayA);
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
                arrayA[i][j] = Double.parseDouble(value);
            }
        }

    }

    public void setBtnAddB(){
        // TODO Auto-generated method stub
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
                txt.setHint("B" +i+j+"  ");
                row.addView(txt);

            }
            tblInputB.addView(row);

        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutViewB);
        final Button create = new Button(MainActivity.this);
        create.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        create.setText("Create Matrix B");
        layout.addView(create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArrayB();
                matrixB = new Matrix(RowB,ColB,arrayB);
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
                arrayB[i][j] = Double.parseDouble(value);
            }
        }

    }

    public void setShow(){

        String test = Integer.toString(spinner.getSelectedItemPosition());
        Log.e("spinner", test);
        switch (spinner.getSelectedItemPosition()){
            case 0:
                matrixResult = new Matrix(RowA,ColA);
                matrixResult = matrixA.tambahMatrix(matrixB);
                break;
            case 1:
                matrixResult = new Matrix(RowA,ColA);
                matrixResult = matrixA.kurangMatrix(matrixB);
                break;
            case 2:
                matrixResult = new Matrix(RowA,ColB);
                matrixResult = matrixA.kaliMatrix(matrixB);
                break;
            case 3:
                matrixResult = new Matrix(ColA,RowA);
                matrixResult = matrixA.transpose();
                break;
            case 4:
                matrixResult = new Matrix(ColB,RowB);
                matrixResult = matrixB.transpose();
                break;
            case 5:
                Double determinantA[][] = new Double[1][1];
                determinantA[0][0] = matrixA.determinan();
                matrixResult = new Matrix(1,1,determinantA);
                break;
            case 6:
                Double determinantB[][] = new Double[1][1];
                determinantB[0][0] = matrixB.determinan();
                matrixResult = new Matrix(1,1,determinantB);
                break;
            case 7:
                matrixResult = new Matrix(RowA,ColA);
                matrixResult = matrixA.kofaktor();
                break;
            case 8:
                matrixResult = new Matrix(RowB,ColB);
                matrixResult = matrixB.kofaktor();
                break;
            case 9:
                matrixResult = new Matrix(RowA,ColA);
                matrixResult = matrixA.adjoin();
                break;
            case 10:
                matrixResult = new Matrix(RowB,ColB);
                matrixResult = matrixB.adjoin();
                break;
            case 11:
                matrixResult = new Matrix(RowA,ColA);
                matrixResult = matrixA.invers();
                break;
            case 12:
                matrixResult = new Matrix(RowA,ColA);
                matrixResult = matrixB.invers();
                break;
            default:
                break;
        }


        int row = matrixResult.getX();
        int col = matrixResult.getY();
        Log.e("row", Integer.toString(row));
        Log.e("col", Integer.toString(col));
        for(i=0;i<row;i++)
        {
            final TableRow row1 = new TableRow(MainActivity.this);

            for(j=0;j<col;j++)
            {
                final TextView txt=new TextView(MainActivity.this);
                txt.setTextColor(Color.BLACK);
                txt.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8);
                txt.setTypeface(Typeface.SERIF, Typeface.BOLD);
                txt.setGravity(Gravity.LEFT);
                txt.setText(matrixResult.value[i][j].toString() + " ");
                row1.addView(txt);
            }
            TabLayout_Show.addView(row1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
