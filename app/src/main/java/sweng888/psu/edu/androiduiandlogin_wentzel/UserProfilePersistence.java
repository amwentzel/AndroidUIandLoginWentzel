package sweng888.psu.edu.androiduiandlogin_wentzel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserProfilePersistence implements IPersistence {

    private DatabaseAccess databaseAccess;

    public UserProfilePersistence (Context context){
        this.databaseAccess = new DatabaseAccess(context);
    }

    @Override
    public void insert(Object o) {
        UserProfile userProfile = (UserProfile) o;
        SQLiteDatabase sqLiteDatabase = databaseAccess.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserProfileTable.COLUMN_FIRST_NAME, userProfile.getFirstName());
        contentValues.put(UserProfileTable.COLUMN_LAST_NAME, userProfile.getLastName());
        contentValues.put(UserProfileTable.COLUMN_USERNAME, userProfile.getUsername());
        contentValues.put(UserProfileTable.COLUMN_PHONE, userProfile.getPhone());
        contentValues.put(UserProfileTable.COLUMN_EMAIL, userProfile.getEmail());
        contentValues.put(UserProfileTable.COLUMN_PASSWORD, userProfile.getPassword());
        contentValues.put(UserProfileTable.COLUMN_BIRTHDAY, String.valueOf(userProfile.getBirthday()));

        sqLiteDatabase.insert(UserProfileTable.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    @Override
    public void delete(Object o) {
    }

    @Override
    public void edit(Object o) {
    }

    @Override
    public ArrayList getDataFromDB() {
        ArrayList<UserProfile> userProfiles;
        SQLiteDatabase sqLiteDatabase = databaseAccess.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(UserProfileTable.select(), null);
        cursor.moveToFirst();
        userProfiles = new ArrayList<>();

        if(cursor != null && cursor.moveToFirst()){
            do {
                String firstName = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_LAST_NAME));
                String username = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_USERNAME));
                String phone = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_PASSWORD));
                String birthday = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_BIRTHDAY));

                UserProfile userProfile = new UserProfile(firstName, lastName, username, phone, email, password, birthday);
                userProfiles.add(userProfile);
            } while (cursor.moveToNext()) ;
        }
        cursor.close();
        return userProfiles;
    }
}