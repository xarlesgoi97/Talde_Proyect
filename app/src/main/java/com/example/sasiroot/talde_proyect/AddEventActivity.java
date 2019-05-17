package com.example.sasiroot.talde_proyect;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import android.content.DialogInterface;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;

import android.support.v7.app.AlertDialog;

import android.widget.Toast;



import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class AddEventActivity extends AppCompatActivity {

    private Uri photo;
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;



    //TAG
    private static final String TAG = "AddEventActivity";

    //BUTTONS, IMAGEVIEW, TEXTVIEW
    private ImageView imgUserPhoto;
    private Button btnSend;
    private Button botonCargar;
    //EVENT DATA
    private EditText txtTitle,txtCity,txtEventDay,txtWhere, txtEventStart,txtEventEnd, txtDescription;
    private TextView txtUserName;


    private FirebaseUser user;
    private  String userEmail;

    //FIREBASE DATANASE CLOUDFIRE
    private FirebaseFirestore db;

    //FIREBASE STORAGE
    private StorageReference mStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        this.setTitle(R.string.app_name);

        //STORAGE
        mStorage = FirebaseStorage.getInstance().getReference();


        this.imgUserPhoto = findViewById(R.id.user_photo);
        this.btnSend = findViewById(R.id.btnSend);
        this.txtTitle = findViewById(R.id.txtTitle);
        this.txtCity = findViewById(R.id.txtCity);
        this.txtEventDay = findViewById(R.id.txtEventDay);
        this.txtWhere = findViewById(R.id.txtWhere);
        this.txtEventStart = findViewById(R.id.txtEventStart);
        this.txtEventEnd = findViewById(R.id.txtEventEnd);
        this.txtDescription = findViewById(R.id.txtDescription);
        //CLOUDFIRE DATABASE
        db = FirebaseFirestore.getInstance();

        //FOTOS
        botonCargar= (Button) findViewById(R.id.btnCargarImg);

        if(validaPermisos()){
            botonCargar.setEnabled(true);
        }else{
            botonCargar.setEnabled(false);
        }


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventId = UUID.randomUUID().toString();
                writeEvent(eventId, txtTitle.getText().toString(),txtCity.getText().toString(),txtEventDay.getText().toString(),txtWhere.getText().toString(),txtEventStart.getText().toString(),txtEventEnd.getText().toString(),txtDescription.getText().toString(),getFileName(photo), new Date(), userEmail);
                finish();
            }
        });

        Intent intent  = getIntent();
        user = (FirebaseUser) intent.getExtras().get("user");
        userEmail = user.getEmail();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    private void logout() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
    private void back() {
        Intent i = new Intent(this,EventActivity.class);
        startActivity(i);
    }


    private void writeEvent(String eventId, String title, String city, String eventDay, String where,  String eventStart, String eventEnd, String description, String photoInfo, Date createDate, String createBy) {
        final Event event = new Event(eventId, title, city, eventDay, where, eventStart, eventEnd, description, photoInfo, createDate, createBy);
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("eventId", event.geteventId());
        eventData.put("title", event.getTitle());
        eventData.put("city", event.getCity());
        eventData.put("eventDay", event.getEventDay());
        eventData.put("where", event.getWhere());
        eventData.put("eventStart", event.getEventStart());
        eventData.put("eventEnd", event.getEventEnd());
        eventData.put("description", event.getDescription());
        eventData.put("photoInfo", user.getEmail()+ "/" +event.getPhotoInfo());
        eventData.put("createDate", event.getCreateDate());
        eventData.put("createBy", event.getCreateBy());

        StorageReference filePath = mStorage.child(user.getEmail()+"/" + getFileName(photo));
        filePath.putFile(photo);



        db.collection("events").document(event.geteventId())
                .set(eventData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + event.geteventId());
                    }

                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }




    //FOTOS
    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(AddEventActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(AddEventActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    public void onclick(View view) {
        cargarImagen();
    }

    private void cargarImagen() {

        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(AddEventActivity.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    tomarFotografia();
                }else{
                    if (opciones[i].equals("Cargar Imagen")){
                        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent,COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    private void tomarFotografia() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,COD_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
                    photo=data.getData();
                    imgUserPhoto.setImageURI(photo);
                    break;

                case COD_FOTO:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    photo= getImageUri(this.getApplicationContext(),bitmap);

                    imgUserPhoto.setImageURI(photo);
                    break;
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "xar", null);
        return Uri.parse(path);
    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


}
