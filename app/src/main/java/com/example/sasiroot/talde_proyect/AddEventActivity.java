package com.example.sasiroot.talde_proyect;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;





import android.content.DialogInterface;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import android.widget.Toast;

import java.io.File;


import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class AddEventActivity extends AppCompatActivity {

    //FOTOS
    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    Button botonCargar;
    ImageView imagen;
    String path;

    //TAG
    private static final String TAG = "AddEventActivity";

    //BUTTONS, IMAGEVIEW, TEXTVIEW
    private Button btnLogout;
    private Button btnSend;
    //EVENT DATA
    private EditText txtTitle;
    private EditText txtCity;
    private EditText txtEventDay;
    private EditText txtWhere;
    private EditText txtEventStart;
    private EditText txtEventEnd;
    private EditText txtDescription;
    private TextView txtUserName;

    private ImageView imgUserPhoto;


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

        this.btnLogout = findViewById(R.id.logout);
        this.txtUserName = findViewById(R.id.user_name);
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
        imagen= (ImageView) findViewById(R.id.imagemId);
        botonCargar= (Button) findViewById(R.id.btnCargarImg);

        if(validaPermisos()){
            botonCargar.setEnabled(true);
        }else{
            botonCargar.setEnabled(false);
        }






        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                prueba();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeEvent(txtTitle.getText().toString(),txtCity.getText().toString(),txtEventDay.getText().toString(),txtWhere.getText().toString(),txtEventStart.getText().toString(),txtEventEnd.getText().toString(),txtDescription.getText().toString(), new Date());
                back();
            }
        });


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

//        String userNameFirebase = intent.getStringExtra("user_name");
//        Uri userPhotoFirebase = Uri.parse(extras.getString("user_photo"));

//        txtUserName.setText(userNameFirebase);

//        imgUserPhoto.setImageURI(userPhotoFirebase);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    private void prueba() {
        Intent i = new Intent(this,Prueba.class);

        startActivity(i);
    }
    private void back() {
        Intent i = new Intent(this,EventActivity.class);

        startActivity(i);
    }


    private void writeEvent(String title, String city, String eventDay, String where,  String eventStart, String eventEnd, String description/*, Uri photo*/, Date createDate) {
        Event event = new Event(title, city, eventDay, where, eventStart, eventEnd, description/*, photo*/, createDate);
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("title", event.getTitle());
        eventData.put("city", event.getCity());
        eventData.put("eventDay", event.getEventDay());
        eventData.put("where", event.getWhere());
        eventData.put("eventStart", event.getEventStart());
        eventData.put("eventEnd", event.getEventEnd());
        eventData.put("description", event.getDescription());
        eventData.put("createDate", event.getCreateDate());

        db.collection("events")
                .add(eventData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
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
                    Uri mPath=data.getData();
                    imgUserPhoto.setImageURI(mPath);
                    StorageReference filePath = mStorage.child(mPath.getLastPathSegment());
                    filePath.putFile(mPath);
                    break;

                case COD_FOTO:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imgUserPhoto.setImageBitmap(bitmap);


                    break;
            }


        }
    }

}
