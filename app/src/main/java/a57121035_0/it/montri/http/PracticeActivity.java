package a57121035_0.it.montri.http;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PracticeActivity extends AppCompatActivity {
    TextView tvCount;
    Spinner spn_Condition,spn_Name,spn_Lastname,spn_Gender,spn_Day;
    EditText edt_Searchname,edt_Searchlastname;
    Button btn_Search;
    String sql = "";
    ListView lv_Show;
    List<HashMap<String,String>> myList = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        spn_Condition = (Spinner) findViewById(R.id.spnCondition);
        spn_Name = (Spinner) findViewById(R.id.spnName);
        spn_Lastname = (Spinner) findViewById(R.id.spnLastname);
        spn_Gender = (Spinner) findViewById(R.id.spnGender);
        spn_Day = (Spinner) findViewById(R.id.spnDay);
        edt_Searchname = (EditText) findViewById(R.id.edtSearchname);
        edt_Searchlastname = (EditText) findViewById(R.id.edtSearchlastname);
        tvCount = (TextView) findViewById(R.id.tvCount);
        tvCount.setVisibility(View.INVISIBLE);
        btn_Search = (Button) findViewById(R.id.btnSearch);
        lv_Show = (ListView) findViewById(R.id.lvShow);

        lv_Show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Button btn_Edit,btn_Delete,btn_Close;
                EditText edt_DialogID,edt_DialogName,edt_DialogLastname,edt_DialogUrl;
                final Dialog editdata = new Dialog(PracticeActivity.this);
                editdata.setContentView(R.layout.dialog_layout);
                btn_Edit = (Button) editdata.findViewById(R.id.btnEdit);
                btn_Delete = (Button) editdata.findViewById(R.id.btnDelete);
                btn_Close = (Button) editdata.findViewById(R.id.btnClose);
                edt_DialogID = (EditText) editdata.findViewById(R.id.edtDialogID);
                edt_DialogName = (EditText) editdata.findViewById(R.id.edtDialogName);
                edt_DialogLastname = (EditText) editdata.findViewById(R.id.edtDialogLastname);
                edt_DialogUrl = (EditText) editdata.findViewById(R.id.edtDialogUrl);

                edt_DialogID.setText(myList.get(i).toString());
                editdata.show();
                btn_Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editdata.dismiss();
                    }
                });



            }
        });

        spn_Name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    edt_Searchname.setText("");
                    edt_Searchname.setVisibility(View.INVISIBLE);
                }
                else{
                    edt_Searchname.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spn_Lastname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0) {
                    edt_Searchlastname.setText("");
                    edt_Searchlastname.setVisibility(View.INVISIBLE);
                }
                else{
                    edt_Searchlastname.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url,con = "",name= "" ,lastname = "",gender = "",dayLab = "";

                switch (spn_Condition.getSelectedItemPosition()){
                    case 0 : con ="";
                        break;
                    case 1 : con =" AND ";
                        break;
                    case 2 : con =" OR ";
                        break;
                }
                switch (spn_Name.getSelectedItemPosition()){
                    case 0 : name ="" ;
                        break;
                    case 1 : name =" name LIKE \'"+edt_Searchname.getText().toString()+"\' ";
                        break;
                    case 2 : name =" name NOT LIKE \'"+edt_Searchname.getText().toString()+"\' ";
                        break;
                    case 3 : name =" name LIKE \'"+edt_Searchname.getText().toString()+"%\' ";
                        break;
                    case 4 : name =" name LIKE \'%"+edt_Searchname.getText().toString()+"\' ";
                        break;
                    case 5 : name =" name LIKE \'%"+edt_Searchname.getText().toString()+"%\' ";
                        break;
                }
                switch (spn_Lastname.getSelectedItemPosition()){
                    case 0 : lastname ="";
                        break;
                    case 1 : lastname =" lastname LIKE \'"+edt_Searchlastname.getText().toString()+"\' ";
                        break;
                    case 2 : lastname =" lastname NOT LIKE \'"+edt_Searchlastname.getText().toString()+"\' ";
                        break;
                    case 3 : lastname =" lastname LIKE \'"+edt_Searchlastname.getText().toString()+"\'% ";
                        break;
                    case 4 : lastname =" lastname LIKE \'%"+edt_Searchlastname.getText().toString()+"\' ";
                        break;
                    case 5 : lastname =" lastname LIKE \'%"+edt_Searchlastname.getText().toString()+"%\' ";
                        break;
                }
                switch (spn_Gender.getSelectedItemPosition()){
                    case 0 : gender ="";
                        break;
                    case 1 : gender =" gender=\'M\' ";
                        break;
                    case 2 : gender =" gender=\'F\' ";
                        break;
                }
                switch (spn_Day.getSelectedItemPosition()){
                    case 0 : dayLab ="";
                        break;
                    case 1 : dayLab =" dayLab=\'WED\' ";
                        break;
                    case 2 : dayLab =" dayLab=\'THU\' ";
                        break;
                }

                url = "SELECT id,name,lastname,url FROM student WHERE "+name+con+lastname+con+gender+con+dayLab;
                url = url.substring(0,url.lastIndexOf("'")+1);
//                Log.e("url",url);
                sql = Base64.encodeToString(url.getBytes(),Base64.DEFAULT);
                sql = sql.replaceAll("(?:\\r\\n|\\n\\r|\\n|\\r)", "");
                Log.e("sql",sql);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = null;
//                List<NameValuePair> nv = new ArrayList<NameValuePair>();
//                nv.add(new BasicNameValuePair("name",name));
//                nv.add(new BasicNameValuePair("gender","M"));
                httpPost = new HttpPost("http://10.0.2.2/android/json.php?SQL="+sql);
//                httpPost = new HttpPost("http://10.0.2.2/android/searchAll.php");
//                try {
//                    httpPost.setEntity(new UrlEncodedFormEntity(nv));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                HttpResponse httpResponse = null;
                BufferedReader bufferedReader = null;
                String data = "";
                    try {
                        httpResponse = httpClient.execute(httpPost);
                        bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                        data = bufferedReader.readLine();
                        String[] data1 = data.split("[|]");
                        int i = 0;
                        while (i < data1.length) {
                            String[] data2 = data1[i].split("[$]");
                            HashMap<String, String> myHashmap = new HashMap<String, String>();
                            myHashmap.put("No", data2[0]);
                            myHashmap.put("ID", data2[1]);
                            myHashmap.put("Name", data2[2]);
                            myHashmap.put("Lastname", data2[3]);
                            myHashmap.put("url", data2[4]);
                            myList.add(myHashmap);
                            i++;
                        }
                        String[] from = new String[] {"No","ID","Name","Lastname","url"};
                        int[] to = new int[]{R.id.tvsNo,R.id.tvsId,R.id.tvsName,R.id.tvsLastname,R.id.tvsUrl};
                        SimpleAdapter simpleAdapter = new SimpleAdapter(PracticeActivity.this,myList,R.layout.search_layout,from,to);
                        lv_Show.setAdapter(simpleAdapter);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(PracticeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }
}
