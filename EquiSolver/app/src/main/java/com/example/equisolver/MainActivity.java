package com.example.equisolver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_REQUEST=2, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private Uri mImageUri;
    File f;
    ProgressBar progressBar;
    EditText equation_input;
    TextView answer_text;
    String equation="",answer="",selected="";
    TextToSpeech tts;
    ListView history;
    ArrayAdapter<String> adapter;
    ArrayList<String> answers_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answers_list = new ArrayList<String>();
        progressBar= (ProgressBar) findViewById(R.id.progressbar);
        equation_input = (EditText) findViewById(R.id.equation_input);
        answer_text = (TextView) findViewById(R.id.answer_text);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        history = (ListView) findViewById(R.id.history_list);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, answers_list);
        history.setAdapter(adapter);
        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = adapter.getItem(position);
                answer = selected.substring(selected.indexOf("=")+1);
                equation = selected.substring(0,selected.indexOf("="));
                answer_text.setText(answer);
                equation_input.setText(equation);

            }
        });

        history.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3) {

                answers_list.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }

        });
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
                else
                    Toast.makeText(getApplicationContext(),""+status,Toast.LENGTH_LONG).show();
            }
        });
        tts.speak("Welcome to smart Equation Solving App", TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    public void onStop() {
        if (tts != null) {
            tts.stop();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.shutdown();
        }
        super.onDestroy();
    }

    public void onImage(View view) {
        if(equation_input.getText().toString().equals("")) {
            tts.speak("Click a valid equation", TextToSpeech.QUEUE_FLUSH, null);
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            File photo;
            try {
                File filesDir = this.getFilesDir();
                // place where to store camera taken picture temporarily
                photo = createTemporaryFile("capture", ".jpg");
                System.out.println("HI");
                //photo.delete();
            } catch (Exception e) {
                Log.v("Djsce Image capture", "Can't create file to take picture!");
                Toast.makeText(getApplicationContext(), "Please check SD card! Image shot is impossible!", Toast.LENGTH_LONG).show();
                return;
            }
            mImageUri = Uri.fromFile(photo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);

            startActivityForResult(intent, 100);
        }
        else
        {
            selected = "";
            equation = equation_input.getText().toString();
            equation_input.setText("");
            answer = MainActivity.eval(equation)+"";
            answer_text.setText(answer);
            answers_list.add(equation + " = " + answer);
            tts.speak(equation.replace("*","into").replace("/","divided by")+ " = " + answer, TextToSpeech.QUEUE_FLUSH, null);

            adapter.notifyDataSetChanged();
        }
    }
    private File createTemporaryFile(String part, String ext) throws Exception {
        File tempDir = Environment.getExternalStorageDirectory();


        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)){
            Log.d("myAppName", "Error: external storage is unavailable");
        }
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d("myAppName", "Error: external storage is read only.");
        }
        else
            Log.d("myAppName", "External storage is not read only or unavailable");

        if (ContextCompat.checkSelfPermission(this, // request permission when it is not granted.
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("myAppName", "permission:WRITE_EXTERNAL_STORAGE: NOT granted!");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                System.out.println("Show an expanation to the user *");
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                System.out.println("No explanation needed, we can request the permission");
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        System.out.println("checking for directory: "+tempDir.exists());
        tempDir = new File(tempDir.getAbsolutePath() + "/FOOD/");
        if (!tempDir.exists()) {
            System.out.println("Making diretory");
            tempDir.mkdir();
        }
        System.out.println(tempDir.toString());
        return File.createTempFile(part, ext, tempDir);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100
                && resultCode == RESULT_OK) {

            try {
                selected = "";
                equation_input.setText("");
                answer_text.setText("");

//                equation_input.setEnabled(false);

                f = grabImageFile(true, 80); //true for compression , 80% quality, To get the File object of the image
                if (f != null) {
                    Toast.makeText(getApplicationContext(), "File to upload is " + f.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    //call image uplaod code here
                    //doFileUpload(f, Constants.IMAGE,f.getName());

                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected void onPreExecute() {
                            progressBar.setVisibility(View.VISIBLE);
                            super.onPreExecute();
                        }

                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                doFileUpload(f, 2, f.getName());//uploads the file

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        protected void onPostExecute(Void result) {
                            Toast.makeText(getApplicationContext(),
                                    "image uploaded", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }.execute();


                } else {
                    Toast.makeText(getApplicationContext(), "image mila hi nahi bro!!", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {

            }

        }

        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageURI = data.getData();

            f = new File(getRealPathFromURI(selectedImageURI));

            System.out.println("file created");
            if (f != null) {
                Toast.makeText(getApplicationContext(), "File to upload is " + f.getAbsolutePath(), Toast.LENGTH_LONG).show();
                //call image uplaod code here
                //doFileUpload(f, Constants.IMAGE,f.getName());

                new AsyncTask<Void, Void, Void>() {


                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            System.out.println("trying to upload file from gallery");
                            doFileUpload(f, 2, f.getName());//uploads the file

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    protected void onPostExecute(Void result) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),
                                "image uploaded", Toast.LENGTH_LONG).show();
                    }
                }.execute();


            } else {
                Toast.makeText(getApplicationContext(), "image mila hi nahi bro!!", Toast.LENGTH_LONG).show();
            }

        }

    }


    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }



    private String getRealPathFromURI(Uri contentURI) {
        String result;
        System.out.println("hi1");
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        System.out.println("hi2");
        if (cursor == null) { // Source is Dropbox or other similar local file path
            System.out.println("cursor null");
            result = contentURI.getPath();
        } else {
            System.out.println("cursor not null");
            cursor.moveToFirst();
            System.out.println("hi1");
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            System.out.println("hi2");
            result = cursor.getString(idx);
            System.out.println("final result path "+ result);
            cursor.close();
        }
        return result;
    }
    public void doFileUpload(File f, int type, String data)
            throws JSONException, IOException {
        Log.e("CHAT VIEW", "Started");
        System.out.println("Entered douploadfile");
        String charset = "UTF-8";
        String requestURL = "http://6d13d402.ngrok.io/predict";

        MultiPartUtility multipart;
        try {
            multipart = new MultiPartUtility(requestURL, charset);

            multipart.addFilePart("image", f);
            String response = multipart.finish().get(0);
            response = response.replaceAll("\'","");
            System.out.println("response: "+response);
            equation = response.split(",")[0];
            equation = equation.substring(1);
            answer = response.split(",")[1];
            answer = answer.substring(0,answer.length()-1);
            answers_list.add(equation + " = " + answer);
            // response from server.
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                    equation_input.setText(equation, TextView.BufferType.EDITABLE);
                    answer_text.setText(answer);
                    tts.speak(equation.replace("*","into").replace("/","divided by") + " = "+answer, TextToSpeech.QUEUE_FLUSH, null);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("SORRY BRO ERROR AAYA ");
            //Toast.makeText(getApplicationContext(),"some error occurred" , Toast.LENGTH_LONG).show();


        }

    }




    public File grabImageFile(boolean compress,int quality) {
        File returnFile= null;
        try {
            returnFile = new File(mImageUri.getPath());
            if(returnFile.exists() && compress){
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(returnFile.getAbsolutePath(), bmOptions);
                File compressedFile = createTemporaryFile("capture_compressed", ".jpg");
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = new FileOutputStream(compressedFile);
                System.out.println("image file successfully created");
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                returnFile.delete();
                returnFile = compressedFile;
            }
//
        } catch (Exception e){
            Log.e("Image123 Capture Error",e.toString());
        }
        return returnFile;
    }
}
