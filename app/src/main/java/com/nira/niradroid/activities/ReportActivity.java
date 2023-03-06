package com.nira.niradroid.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nira.niradroid.R;
import com.omarshehe.forminputkotlin.FormInputAutoComplete;
import com.omarshehe.forminputkotlin.FormInputButton;
import com.omarshehe.forminputkotlin.FormInputMultiline;
import com.omarshehe.forminputkotlin.FormInputSpinner;
import com.omarshehe.forminputkotlin.FormInputText;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;

public class ReportActivity extends AppCompatActivity {

    FormInputSpinner problem;
    FormInputAutoComplete station;
    FormInputText date, staffName, yourname, yourID, yourphonenumber, yourapplicationid;
    FormInputMultiline comments;
    FormInputButton submitEmail;

    ProgressDialog progressDialog;

    // Creating a variable for Firebase Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Get the connectivity manager
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        // Get the active network info
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // Getting our instance from Firebase Firestore
        db = FirebaseFirestore.getInstance();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getResources().getString(R.string.report_activity_heading));

        problem = findViewById(R.id.problem);
        station = findViewById(R.id.station);
        date = findViewById(R.id.date);
        staffName = findViewById(R.id.staffName);
        comments = findViewById(R.id.comment);
        yourname = findViewById(R.id.yourname);
        yourapplicationid = findViewById(R.id.yourapplicationid);
        yourID = findViewById(R.id.yourID);
        yourphonenumber = findViewById(R.id.yourphonenumber);

        submitEmail = findViewById(R.id.btnSubmit);
        submitEmail.setOnClickListener(view -> {

            // Getting text from the text input fields
            String issue = problem.getValue();
            String stationarea = station.getValue();
            String nameofreporter = yourname.getValue();
            String dateentered = date.getValue();
            String appid = yourapplicationid.getValue();
            String nationalid = yourID.getValue();
            String phonenumber = yourphonenumber.getValue();
            String nameofstaff = staffName.getValue();
            String comment = comments.getValue();


            // Checks for errors
            if (TextUtils.isEmpty(issue)) {
                problem.setError("Please enter an issue");
            } else if (TextUtils.isEmpty(stationarea)) {
                station.setError("Please enter a station");
            } else if (TextUtils.isEmpty(nameofreporter)) {
                yourname.setError("Please enter a name");
            } else if(TextUtils.isEmpty((dateentered))) {
                station.setError("Please enter the date");
            } else if (networkInfo != null && networkInfo.isConnected()) {

                // A progress dialog to show data is being sent
                progressDialog = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
                progressDialog.setMessage("Sending...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                // calling method to add data to Firebase Firestore.
                addDataToFirestore(issue, stationarea, nameofreporter, dateentered, appid, nationalid, phonenumber, nameofstaff, comment);
            } else {

                // A toast to show failure in sending of data
                FancyToast.makeText(this,getString(R.string.failedtosend),FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                finish();
            }

            // This commented out method was used to send data to a specified email. This method was dropped to be replaced by Firebase Firestore
            /*String[] email_address = { getResources().getString(R.string.email)};

            if(problem.noError(problem.getRootView(), false) && station.noError(station.getRootView(), false) && date.noError(date.getRootView(), false) ){
                String email_message =
                        getResources().getString(R.string.problem_type)   + ": "  + problem.getValue()    + "\n" +
                                getResources().getString(R.string.station)        + ": "  + station.getValue()    + "\n" +
                                getResources().getString(R.string.name)    +": " + yourname.getValue()   +"\n"  +
                                getResources().getString(R.string.date)           + ": "  + date.getValue()       + "\n" +
                                getResources().getString(R.string.national_id)    +": " + yourID.getValue()   +"\n"  +
                                getResources().getString(R.string.application_id) +": " + yourapplicationid.getValue()   +"\n"  +
                                getResources().getString(R.string.phone_number)    +": " + yourphonenumber.getValue()   +"\n"  +
                                getResources().getString(R.string.staffName)      + ": "  + staffName.getValue()  + "\n" +
                                getResources().getString(R.string.comments)       + ": "  + comments.getValue();

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, email_address);
                intent.putExtra(Intent.EXTRA_SUBJECT, email_message);
                startActivity(intent);
            }*/

        });

    }

    // This commented out method was for Firebase Realtime Database, which was dropped for Firebase Firestore
    /*private void addDatatoFirebase(String issue, String stationarea, String dateentered, String nameofreporter, String appid, String nationalid, String phonenumber, String nameofstaff, String comment) {
        // below lines of code is used to set
        // data in our object class.
        reportActivityObject.setProblem(issue);
        reportActivityObject.setStation(stationarea);
        reportActivityObject.setDate(dateentered);
        reportActivityObject.setYourname(nameofreporter);
        reportActivityObject.setYourapplicationid(appid);
        reportActivityObject.setYourID(nationalid);
        reportActivityObject.setYourphonenumber(phonenumber);
        reportActivityObject.setStaffName(nameofstaff);
        reportActivityObject.setComments(comment);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(reportActivityObject);

                // after adding this data we are showing toast message.
                Toast.makeText(ReportActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(ReportActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    // IMPORTANT! keep the order of variables as they appear in the activity_report XML layout
    private void addDataToFirestore(String issue, String stationarea, String nameofreporter, String dateentered, String appid, String nationalid, String phonenumber, String nameofstaff, String comment) {

        // creating a collection reference for our Firebase Firestore database
        CollectionReference dbReport = db.collection("Report");

        // adding data to our reports object class
        ReportActivityObject reportActivityObject = new ReportActivityObject(issue, stationarea, nameofreporter, dateentered, appid, nationalid, phonenumber, nameofstaff, comment);

        //below is the method used to add data to our Firebase firestore
        dbReport.add(reportActivityObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data deletion is successful
                // we are displaying a success toast message and dismissing the progress dialog.
                // and returning to the MainActivity
                progressDialog.dismiss();
                FancyToast.makeText(ReportActivity.this,getString(R.string.success),FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process has failed.
                // displaying a toast message when the data addition process has failed
                // while also dismissing the progress dialog to allow the user navigate the app.
                progressDialog.dismiss();
                Toast.makeText(ReportActivity.this, "Failed to send your data, please check your internet connection \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}