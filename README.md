Program Matrix Calculator

        

Link github repository 	https://github.com/ayckermann/Matrix-Calculator

Link download apk	https://drive.google.com/file/d/1ZWBreEY1yaF_lx3LCoFNPfYHUhnxo-nW/view?usp=sharing

 
Dalam folder project Matrix Calculator terdapat banyak file dan folder namun kebanyakan hanyalah program untuk tampilan dan juga builder seperti gradle yang harus ada dalam project pemrograman mobile menggunakan android studio.

Untuk algoritma tentang bagaimana aplikasi Matrix Calculator meng-kalkulasikan tiap operasi matrix ada dalam file class Matrix.java.

Penjelasan  Algoritma atau codenya sebagai berikut :
Inisialisasi
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
Menginisialisasi class dengan attribute int x(untuk baris matrix), int y(untuk kolom matrix) 
2d array double value dengan index yang akan di isi dengan baris dan kolom (untuk nilai di tiap elemen matrix).

Inisialisasi-nya juga akan disertai dengan constructor(meng-assign nilai awal class) di sini ada 3 constructor:
1. Yang pertama tanpa parameter => tujuannya untuk menginisialisasi variable, agar data tidak redundant.
2.  Dengan parameter x dan y. Di sini akan meng-assign attribute x dan y dengan inputan, untuk attribute value akan di-assign dengan default 0. => tujuannya adalah jika baris dan kolom sudah diketahui namun belum mengetahui nilai tiap elemennya, sehingga nilai tiap elemennya akan diberi default 0.
3. Dengan parameter x , y dan value. Seluruh attribute akan di assign dengan inputan  => tujuannya adalah jika seluruh atribut sudah diketahui ingin di isi apa.

 

Operasi penjumlahan dan pengurangan
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
Melakukan perulangan untuk tiap index elemen matrix, lalu meng-assign variable hasil dengan penjumlahan atau pengurangan matrix a dan b di tiap index elemen yang sama.

Operasi perkalian matrix dengan matrix
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

Melakukan perulangan untuk tiap index elemen matrix dan satu lagi perulangan dibawahnya untuk menjumlahkan hasil perkalian antar baris dan kolom.

 
Operasi transpose matrix
public Matrix transpose(){
    Matrix hasil = new Matrix(y,x);
    for (int i = 0; i < x; i++) {
        for (int j =0; j < y; j++){
            hasil.value[j][i] = value[i][j];
        }
    }
    return hasil;
}
Melakukan perulangan untuk tiap index elemen matrix dan meng-assign nilai hasil dengan index elemen yang terbalik dari matrix normal nya.

Operasi kofaktor matrix
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
Melakukan perulangan untuk tiap index elemen matrix dan meng-assign hasil dengan determinan dari minor di elemen tersebut (diambil dari fungsi determinan minor), dan juga memberikan kondisi saat elemen tersebut harus mengganti tanda (+ / -).
Untuk matrix ordo 1x1 hasilnya adalah nilai elemen 1 1 itu sendiri.

Operasi adjoint matrix
public Matrix adjoin(){

    Matrix hasil = this.kofaktor().transpose();

    if (x==1 && y ==1){
        hasil = this;
    }
    return hasil;

}
Mentranspose kan matrix kofaktor.
Untuk matrix ordo 1x1 hasilnya adalah nilai elemen 1 1 itu sendiri.

Operasi invers matrix
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
Untuk matrix ordo 1x1 hasilnya adalah nilai elemen 1 1 itu sendiri.

Untuk matrix ordo 2x2 algoritmanya ditulis manual sesuai dengan rumus invers ordo 2x2. 

Untuk Matrix ordo 3x3 dan keatas menggunakan adjoin(diambil dari fungsi adjoin) lalu dikalikan 1/determinan-nya.

Determinan diambil dari fungsi determinan.
