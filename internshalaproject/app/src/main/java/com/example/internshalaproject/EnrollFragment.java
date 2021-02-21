package com.example.internshalaproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

import static android.app.Activity.RESULT_OK;


public class EnrollFragment extends Fragment {


    private ImageView imageView;
    private EditText editText, editText2,editText3,editText4,editText5,editText6,editText7,editText8,editText9;
    Uri filepath;
    private Button button;
    Bitmap bitmap;
    public EnrollFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_enroll, container, false);

        imageView = view.findViewById(R.id.imageView);
        button = view.findViewById(R.id.button);

        editText =  view.findViewById(R.id.editText);
        editText2 = view.findViewById(R.id.editText2);
        editText3 = view.findViewById(R.id.editText3);
        editText4 = view.findViewById(R.id.editText4);
        editText5 = view.findViewById(R.id.editText5);
        editText6 = view.findViewById(R.id.editText6);
        editText7 = view.findViewById(R.id.editText7);
        editText8 = view.findViewById(R.id.editText8);
        editText9 = view.findViewById(R.id.editText9);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Dexter.withActivity(getActivity())
                                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        Intent intent=new Intent(Intent.ACTION_PICK);
                                        intent.setType("image/*");
                                        startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);

                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {


                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                        token.continuePermissionRequest();
                                    }
                                }).check();
                    }
                });


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       String firstname = editText.getText().toString();
                       String phoneno = editText8.getText().toString();



                       if(firstname.matches("") && phoneno.matches("")){

                           Toast.makeText(getActivity(),"fill data",Toast.LENGTH_SHORT).show();
                           return;

                       }else{
                           uploadtoFirebase();
                       }

                    }
                });


        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if(requestCode==1 && resultCode==RESULT_OK ){
            filepath=data.getData();
            try{
//                InputStream inputStream=getContentResolver().openInputStream(filepath);
                InputStream inputStream = getContext().getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void uploadtoFirebase() {

        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setTitle("File Uploader");
        dialog.show();


        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference("Image1"+new Random().nextInt(500));

        uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        dialog.dismiss();
                        String firstname = editText.getEditableText().toString();
                        String lastname = editText2.getEditableText().toString();
                        String dateofbirth= editText3.getEditableText().toString();
                        String gender = editText4.getEditableText().toString();
                        String country = editText5.getEditableText().toString();
                        String state = editText6.getEditableText().toString();
                        String hometown = editText7.getEditableText().toString();
                        String phonenumber = editText8.getEditableText().toString();
                        String telephoneno = editText9.getEditableText().toString();




                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference root = db.getReference();


                            dataholder obj = new dataholder(firstname, lastname, dateofbirth, gender, country,state,hometown,phonenumber,telephoneno,uri.toString());
                            root.child(phonenumber).setValue(obj);
                            Toast.makeText(getActivity(),"Uploaded",Toast.LENGTH_SHORT).show();




                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                dialog.setMessage("Uploaded :"+(int)percent+" %");
            }
        });
    }
}