package sweng888.psu.edu.androiduiandlogin_wentzel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import sweng888.psu.edu.androiduiandlogin_wentzel.UserProfile;

public class UserProfilePersistence implements IPersistence {

    public DatabaseAccess databaseAccess;

    public UserProfilePersistence (Context context){
        this.databaseAccess = new DatabaseAccess(context);
    }

    @Override
    public void insert(Object o) {
        // Cast the generic object to have access to the user info.
        UserProfile userProfile = (UserProfile) o;

        SQLiteDatabase sqLiteDatabase = databaseAccess.getWritableDatabase();

        // The ContentValues object create a map of values, where the columns are the keys
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserProfileTable.COLUMN_NAME_, userProfile.getName());
        contentValues.put(UserProfileTable.COLUMN_SURNAME, userProfile.getSurname());
        contentValues.put(UserProfileTable.COLUMN_USERNAME, userProfile.getUsername());
        contentValues.put(UserProfileTable.COLUMN_PHONE, userProfile.getPhone());
        contentValues.put(UserProfileTable.COLUMN_EMAIL, userProfile.getEmail());
        contentValues.put(UserProfileTable.COLUMN_PASSWORD, userProfile.getPassword());
        contentValues.put(UserProfileTable.COLUMN_BIRTHDAY, String.valueOf(userProfile.getBirthday()));

        // Insert the ContentValues into the User table.
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
        // Create ArrayList of movies
        ArrayList<UserProfile> userProfiles = null;

        // Instatiate the database.
        SQLiteDatabase sqLiteDatabase = databaseAccess.getWritableDatabase();

        // Gather all the records found for the MOVIE table.
        Cursor cursor = sqLiteDatabase.rawQuery(UserProfileTable.select(), null);

        // It will iterate since the first record gathered from the database.
        cursor.moveToFirst();

        // Check if there exist other records in the cursor
        userProfiles = new ArrayList<>();

        if(cursor != null && cursor.moveToFirst()){

            do {
                String name = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_NAME_));
                String surname = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_SURNAME));
                String username = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_USERNAME));
                String phone = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_PASSWORD));
                String birthday = cursor.getString(cursor.getColumnIndex(UserProfileTable.COLUMN_BIRTHDAY));

                UserProfile userProfile = new UserProfile(name, surname, username, phone, email, password, birthday);
                userProfiles.add(userProfile);

            } while (cursor.moveToNext()) ;
        }

        return userProfiles;

    }
}