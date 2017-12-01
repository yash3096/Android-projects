package com.hattricktech.stockmarketai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hattricktech.stockmarketai.adapter.StockRestAdapter;
import com.hattricktech.stockmarketai.data_entity.Message;
import com.microsoft.cognitiveservices.luis.clientlibrary.LUISClient;
import com.microsoft.cognitiveservices.luis.clientlibrary.LUISEntity;
import com.microsoft.cognitiveservices.luis.clientlibrary.LUISResponse;
import com.microsoft.cognitiveservices.luis.clientlibrary.LUISResponseHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class BotActivity extends AppCompatActivity {


    LUISResponse previousResponse;
    LUISClient client;
    EditText messageET;
    ListView messageLV;
    Button sendBTN;

    ArrayList<Message> messageAL;
    MessageCustomAdapter messageCustomAdapter;
    StockRestAdapter stockRestAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot);
        messageET = (EditText) findViewById(R.id.et_message);
        messageLV = (ListView) findViewById(R.id.lv_message);
        sendBTN= (Button) findViewById(R.id.btn_send);

        messageAL = new ArrayList<Message>();
        messageCustomAdapter = new MessageCustomAdapter();
        messageLV.setAdapter(messageCustomAdapter);
        stockRestAdapter = new StockRestAdapter();
        client = new LUISClient(getString(R.string.app_id),getString(R.string.app_key));
        Log.d("Tag","Client_Created");

    }

    public void onSend(View view)
    {
        if(!isEmpty(messageET))
        {
            Message message = new Message(messageET.getText().toString(),true, Calendar.getInstance());
            messageAL.add(message);
            LUISPredict(messageET.getText().toString());
            sendBTN.setEnabled(false);
            messageCustomAdapter.notifyDataSetChanged();
            messageET.setText("");

        }



    }

    public void LUISPredict(String message)
    {
        try {
            client.predict(message, new LUISResponseHandler() {
                @Override
                public void onSuccess(LUISResponse response) {
                    sendBTN.setEnabled(true);
                    previousResponse = response;

                    List<LUISEntity> luisEntities = response.getEntities();
                    for (LUISEntity entity : luisEntities)
                    {
                        stockRestAdapter.getStockFromApi("NSE",entity.getName(),stockDataCallback);
                    }


                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(BotActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    sendBTN.setEnabled(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    class MessageCustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return messageAL.size();
        }

        @Override
        public Object getItem(int position) {
            return messageAL.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View view;
            Message message = messageAL.get(position);
            LayoutInflater inflater = getLayoutInflater();


            if(message.isSend())
            {
                view = inflater.inflate(R.layout.list_item_send,null);
            }
            else
            {
                view = inflater.inflate(R.layout.list_item_reply,null);
            }
             TextView messageTV = (TextView) view.findViewById(R.id.tv_message);
            messageTV.setText(message.getMessage().toString().trim());

            return view;
        }
    }

    public Callback<StockData> stockDataCallback = new Callback<StockData>() {
        @Override
        public void onResponse(Response<StockData> response, Retrofit retrofit) {
            Log.d("STOCK DATA API CALLBACK","Response Recieved");
            Log.d("______________________",""+response.code());
            StockData data = response.body();
            //Toast.makeText(BotActivity.this,""+ response.code(), Toast.LENGTH_SHORT).show();
            Message message = new Message(false,Calendar.getInstance());
            String s = String.format("Price of %s is %s",data.getDataset().getName(),data.getDataset().getData().get(0).get(4));
            message.setMessage(s);
            messageAL.add(message);
            messageCustomAdapter.notifyDataSetChanged();

        }

        @Override
        public void onFailure(Throwable t) {
            Log.d("STOCK DATA API CALLBACK","Failure");
            Log.d("STOCK DATA API CALLBACK",t.toString());


        }
    } ;


}
