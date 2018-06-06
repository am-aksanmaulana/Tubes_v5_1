package am.aksanmaulana.gmail.com.tubes_v5_1.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {

    //kalau ada upgrage, increment versi database
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "umkm.db";

    public static final String TABLE_User =
            "CREATE TABLE UserUmkm (" +
                    "idUserUmkm INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT," +
                    "password TEXT," +
                    "nameUser TEXT," +
                    "genderUser TEXT," +
                    "addressUser TEXT," +
                    "phoneUser TEXT," +
                    "emailUser TEXT," +
                    "imageUser INT," +
                    "userType TEXT" +
                    ")";

    public static final String TABLE_Business =
            "CREATE TABLE Business (" +
                    "idBusiness INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nameBusiness TEXT," +
                    "locationBusiness TEXT," +
                    "startBusiness TEXT," +
                    "businessType TEXT," +
                    "userUmkm INT," +
                    "FOREIGN KEY(userUmkm) REFERENCES UserUmkm(idUserUmkm)" +
                    ")";

    public static final String TABLE_Product =
            "CREATE TABLE Product (" +
                    "idProduct INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nameProduct TEXT," +
                    "stockProduct INTEGER," +
                    "priceProduct INTEGER," +
                    "imageProduct INTEGER," +
                    "business INTEGER," +
                    "FOREIGN KEY(business) REFERENCES Business(idBusiness)" +
                    ")";


    public static final String TABLE_DuitKu =
            "CREATE TABLE DuitKu (" +
                    "idDuitKu INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "codeDuitKu TEXT," +
                    "balanceDuitKu TEXT," +
                    "userUmkm INTEGER," +
                    "FOREIGN KEY(userUmkm) REFERENCES UserUmkm(idUserUmkm)" +
                    ")";
/*
    public static final String TABLE_Messages =
            "CREATE TABLE Messages (" +
                "idMessages INTEGER PRIMARY KEY AUTOINCREMENT," +
                "codeMessage UNIQUE TEXT," +
                "fromIdUserUmkm INTEGER," +
                "toIdUserUmkm INTEGER" +
                "message TEXT," +
                "status TEXT," +
                "FOREIGN KEY(fromIdUserUmkm) REFERENCES UserUmkm(idUser)," +
                "FOREIGN KEY(toIdUserUmkm) REFERENCES UserUmkm(idProduct)" +
            ")";

    public static final String TABLE_Replys =
            "CREATE TABLE Replys (" +
                "idReplys INTEGER PRIMARY KEY AUTOINCREMENT," +
                "codeMessage TEXT," +
                "chactFrom INTEGER," +
            ")"; */

    /*
    public static final String TABLE_Transactions =
            "CREATE TABLE Transactions (" +
                "idTransaction INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product INTEGER," +
                "user INTEGER" +
                "amount INTEGER," +
                "total INTEGER," +
                "FOREIGN KEY(user) REFERENCES User(idUser)," +
                "FOREIGN KEY(product) REFERENCES Product(idProduct)" +
            ")"; */

    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create database
        db.execSQL(TABLE_Business);
        db.execSQL(TABLE_Product);
        db.execSQL(TABLE_User);
        db.execSQL(TABLE_DuitKu);
        //db.execSQL(TABLE_Transactions);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //jika app diupgrade (diinstall yang baru) maka database akan dicreate ulang (data hilang)
        //jika tidak tidak ingin hilang, bisa diproses disini
        db.execSQL("DROP TABLE IF EXISTS BusinessType");
        db.execSQL("DROP TABLE IF EXISTS Business");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS UserUmkm");
        db.execSQL("DROP TABLE IF EXISTS Proposal");
        db.execSQL("DROP TABLE IF EXISTS DuitKu");
        //db.execSQL("DROP TABLE IF EXISTS Transactions");
    }
}
