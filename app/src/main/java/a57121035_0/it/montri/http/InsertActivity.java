package a57121035_0.it.montri.http;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedWriter;
import java.io.IOException;

public class InsertActivity extends AppCompatActivity {
    EditText edt_ID,edt_Name,edt_Lastname,edt_Email,edt_Url;
    RadioButton rdo_Male,rdo_Female,rdo_Wed,rdo_Thu;
    Spinner spinner;
    Button btn_Insert,btn_Clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        edt_ID = (EditText) findViewById(R.id.edtID);
        edt_Name = (EditText) findViewById(R.id.edtName);
        edt_Lastname = (EditText) findViewById(R.id.edtLastname);
        edt_Email = (EditText) findViewById(R.id.edtEmail);
        edt_Url = (EditText) findViewById(R.id.edtUrl);
        rdo_Male = (RadioButton) findViewById(R.id.rdoMale);
        rdo_Male.setChecked(true);
        rdo_Female = (RadioButton) findViewById(R.id.rdoFemale);
        rdo_Wed = (RadioButton) findViewById(R.id.rdoWed);
        rdo_Wed.setChecked(true);
        rdo_Thu = (RadioButton) findViewById(R.id.rdoThu);
        spinner = (Spinner) findViewById(R.id.spinner);
        btn_Insert = (Button) findViewById(R.id.btnInsert);
        btn_Clear = (Button) findViewById(R.id.btnClear);

        btn_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_ID.setText(null);
                edt_Url.setText(null);
                edt_Name.setText(null);
                edt_Lastname.setText(null);
                rdo_Male.setChecked(true);
                rdo_Wed.setChecked(true);
                edt_Email.setText(null);
                spinner.setSelection(0);
            }
        });

        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edt_ID.getText().toString().equals("")&&!edt_Name.getText().toString().equals("")&&!edt_Lastname.getText().toString().equals("")&&
                        !edt_Email.getText().toString().equals("")&&!edt_Url.getText().toString().equals("")) {
                    String url = "http://10.0.2.2/android/insert.php"; // 10.0.2.2 = Macbook localhost
                    url += "?id=" + edt_ID.getText().toString();
                    url += "&name="+edt_Name.getText().toString().substring(0,1).toUpperCase()+edt_Name.getText().toString().substring(1);
                    url += "&lastname="+edt_Lastname.getText().toString().substring(0,1).toUpperCase()+edt_Name.getText().toString().substring(1);
                    if(rdo_Male.isChecked())
                        url += "&gender=M";
                    else
                        url += "&gender=F";
                    url += "&email="+edt_Email.getText().toString();
                    url += "&url="+edt_Url.getText().toString();
                    if(rdo_Wed.isChecked())
                        url += "&daylab=WED";
                    else
                        url += "&daylab=THU";
                    int sec=1;
                    switch (spinner.getSelectedItemPosition())
                    {
                        case 0 : sec = 1;
                            break;
                        case 1 : sec = 2;
                            break;
                        case 2 : sec = 3;
                            break;
                        case 3 : sec = 4;
                            break;
                        case 4 : sec = 5;
                            break;
                        case 5 : sec = 6;
                            break;
                        case 6 : sec = 7;
                            break;
                        case 7 : sec = 8;
                            break;
                        case 8 : sec = 9;
                            break;
                        case 9 : sec = 10;
                            break;
                        case 10 : sec = 11;
                            break;
                        case 11 : sec = 12;
                            break;
                        case 12 : sec = 13;
                            break;
                        case 13 : sec = 14;
                            break;
                        case 14 : sec = 15;
                            break;
                        case 15 : sec = 16;
                            break;
                    }
                    url +="&grouplab="+sec;
                    try {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(url);
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        httpResponse.getEntity().getContent();
                        StatusLine status = httpResponse.getStatusLine();
                        int Code = status.getStatusCode();
                        if(Code==200) {
                            Toast.makeText(InsertActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(InsertActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        myAlertDialog(e.getMessage(),"Error","OK");
                    }
                }
                else {
                    myAlertDialog("Please Input All Data !","Error","OK");
                }
            }
        });
    }
    public void myAlertDialog(String Message,String Title,String btnText){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(Message)
                .setCancelable(false)
                .setTitle(Title)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
    }
}
