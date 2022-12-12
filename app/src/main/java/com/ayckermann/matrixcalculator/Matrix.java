package com.ayckermann.matrixcalculator;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Matrix {

    public int x,y;
    public Double[][] value;

    public  Matrix(){

    }
    public Matrix(int x, int y){
        this.x = x;
        this.y = y;
        Double[][] temp = new Double[x][y];
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                temp[i][j] = 0.0;
            }
        }
        this.value = temp;
    }
    public Matrix(int x, int y, Double[][] value){
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Double[][] getValue() {
        return value;
    }

    public void setValue(Double[][] value) {
        this.value = value;
    }

    public void display()
    {
        for (int i = 0; i < x; i++) {
            for (int j =0; j < y; j++){
                Log.e("SKUY", value[i][j].toString());
            }
        }
    }

    public Matrix tambahMatrix(Matrix b){
        Matrix hasil = new Matrix(x,y);
        for (int i = 0; i < x; i++) {
            for (int j =0; j < y; j++){
                hasil.value[i][j] = value[i][j] + b.value[i][j];
            }
        }
        return hasil;
    }

    public Matrix kurangMatrix(Matrix b){
        Matrix hasil = new Matrix(x,y);
        for (int i = 0; i < x; i++) {
            for (int j =0; j < y; j++){
                hasil.value[i][j] = value[i][j] - b.value[i][j];
            }
        }
        return hasil;
    }

    public Matrix kaliMatrix(Matrix b){
        if(y == b.x){
            Matrix hasil = new Matrix(x, b.y);
            for (int i = 0; i < x; i++) {
                for (int j =0; j < b.y; j++){
                    for (int k = 0; k < y; k++){
                        hasil.value[i][j] += value[i][k] * b.value[k][j];

                    }
                }
            }
            return hasil;
        }
        else{
            System.out.println("Gagal! Dimensi tidak cocok");
            Log.e("Dimensi", "Dimensi tidak cocok");
            Matrix hasil = new Matrix();
            return hasil;

        }
    }

    public Matrix kaliKoefisien(double n){
        Matrix hasil = new Matrix(x,y);
        for (int i = 0; i < x; i++) {
            for (int j =0; j < y; j++){
                hasil.value[i][j] = value[i][j] * n;
            }
        }
        return hasil;
    }
    public Matrix transpose(){
        Matrix hasil = new Matrix(y,x);
        for (int i = 0; i < x; i++) {
            for (int j =0; j < y; j++){
                hasil.value[j][i] = value[i][j];

            }
        }
        return hasil;
    }

    public Matrix minorMatrix(int a, int b){
        Matrix hasil = new Matrix(x-1,y-1);
        int a1 =0, b1=0;

        if (x==1 && y ==1){
            hasil = this;
        }
        else{
            for (int i = 0; i < x; i++) {
                for (int j =0; j < y; j++){
                    if(i != a && j != b){
                        if(b1<y-1){
                            hasil.value[a1][b1] = value[i][j];
                            b1++;
                        }
                        if(b1==y-1){
                            b1=0;
                            a1++;
                        }
                    }
                }
            }
        }
        return hasil;
    }
    public double detMinor(int a, int b){
        Matrix temp = minorMatrix(a,b);
        double hasil = temp.determinan();
        return hasil;

    }
    public Matrix kofaktor(){
        Matrix hasil = new Matrix(x,y);
        if (x==1 && y ==1){
            hasil = this;
        }
        else{
            for(int i =0;i < x;i++){
                for(int j =0 ; j < y;j++){
                    if(i%2 ==0 && j%2 !=0){
                        hasil.value[i][j] = -1 * detMinor(i,j);
                    }
                    else if(i%2!=0 && j%2==0){
                        hasil.value[i][j] = -1 * detMinor(i,j);
                    }
                    else{
                        hasil.value[i][j] =detMinor(i,j);
                    }
                }
            }
        }
        return hasil;

    }
    public Matrix adjoin(){

        Matrix hasil = this.kofaktor().transpose();

        if (x==1 && y ==1){
            hasil = this;
        }
        return hasil;

    }

    public Matrix invers(){
        Matrix hasil = this.adjoin();
        if (x==1 && y ==1){
            hasil = this;
        }
        else if(x==2 && y==2){
            double det = determinan();
            Double temp2[][] = {{value[1][1],-1*value[0][1]},{-1*value[1][0],value[0][0]} };
            Matrix temp = new Matrix(x,y,temp2);
            hasil = temp.kaliKoefisien(1/det);
        }
        else if(x>2 && y>2){
            Matrix temp = this.adjoin();
            double det = determinan();
            hasil = temp.kaliKoefisien(1/det);
        }
        return hasil;
    }
    public double determinan(){
        if (x==1 && y ==1){
            return value[0][0];
        }

        else if(x==2 && y==2){
            double hasil = (value[0][0] * value[1][1]) - (value[0][1]*value[1][0]) ;
            return hasil;
        }
        else if(x==3 && y==3){
            double hasil =0;
            hasil = (value[0][0] * detMinor(0,0)) -
                    (value[0][1] * detMinor(0,1)) +
                    (value[0][2] * detMinor(0,2))
            ;
            return hasil;
        }
        else if(x==4 && y==4){
            double hasil;
            hasil = (value[0][0] * detMinor(0,0)) -
                    (value[0][1] * detMinor(0,1)) +
                    (value[0][2] * detMinor(0,2)) -
                    (value[0][3] * detMinor(0,3));

            return hasil;

        }
        else if(x==5 && y==5){
            double hasil;
            hasil = (value[0][0] * detMinor(0,0)) -
                    (value[0][1] * detMinor(0,1)) +
                    (value[0][2] * detMinor(0,2)) -
                    (value[0][4] * detMinor(0,3)) +
                    (value[0][5] * detMinor(0,4));

            return hasil;

        }

        else{

            Log.e("Dimensi Determinant", "Dimensi tidak cocok");
            return 0;
        }


    }
}
