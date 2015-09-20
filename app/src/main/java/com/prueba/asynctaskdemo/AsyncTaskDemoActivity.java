package com.prueba.asynctaskdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AsyncTaskDemoActivity extends Activity {
    private ProgressDialog progreso;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_asynctask);
        textView= (TextView) findViewById(R.id.textView);
        progreso=new ProgressDialog(this);
        progreso.setTitle("Progreso");
        progreso.setMessage("Progreso de Tarea...");
        progreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    public void iniciarTarea(View v){
        Tarea tarea=new Tarea();
        tarea.execute();
    }

    public class Tarea extends AsyncTask<Void, Integer, String>{

        @Override
        protected String doInBackground(Void... voids) {
            for (int i=0;i<11;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(10*i);
            }
            return "Tarea Finalizada";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setVisibility(View.VISIBLE);
            textView.setText(s);
            progreso.dismiss();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progreso.setProgress(values[0]);
        }
    }
}
