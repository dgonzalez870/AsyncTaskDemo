# Introducción al uso de la clase AsyncTask en Android

La clase **AsyncTask** se utiliza para realizar tareas en segundo plano sin bloquear el hilo en el que se ejecuta la interfaz gráfica de la aplicación, se recomienda su uso en tareas que se realicen en pocos segundos.

### Instrucciones para el uso de AsyncTask
1. Crear una subclase de **AsyncTask**, por ejemplo: `public class Tarea extends AsyncTask<Void, Integer, Void>{...}`, se observa que posee tres parámetros, el primero de estos es el que se pasa como parámetro al método **doInBackground** del AsyncTask, para este ejemplo en particular es del tipo **Void**, pero puede ser de cualquier otro tipo dependiendo de las operaciones que se deseen realizar en el **AsyncTask**, el segundo parámetro representa el dato que se pasa como parámetro al método **onProgressUpdate**, este se utiliza para mantener informado del progreso de nuestra tarea por ejemplo actualizando una barra de progreso, el tercer y último parámetro es el resultado que se espera obtener del método **doInBackground** y que además se puede pasar como parámetro al método **onPostExecute**.
2. Implementar los métodos de la clase, el más importante es **doInBackground**.
 * **doInBackground**: En este método se ejecuta la tarea en segundo plano.
 * **onPreExecute**: Es llamado justo antes de iniciar **doInBackgroud**, su implementación es opcional y puede usarse por ejemplo para mostrar una barra de progreso.
 * **onProgressUpdate:** Es llamado cuando desde el método **doInBackgroud** se ejecuta la instrucción **publishProgress**, se puede utilizar por ejemplo para mantener actualizada una barra de progreso en que se conozca la duración de nuestra tarea.
 * **onPostExecute:** Es llamado una vez que el método **doInBackground** ha finalizado, su implementación es opcional y puede utilizarse para actualizar la interfaz gráfica con el resultado de la tarea ejecutada en **doInBackground**.
 3. Instanciar la subclase de **AsyncTask** y llamar el método **execute()**.
 
 ### Ejemplo
 
 En este ejemplo se implementa un AsyncTask sencillo que mantiene actualizada una barra de progreso durante 11 segundos. Al finalizar se oculta la barra de progreso y se presenta un mensaje en pantalla.
 
 ### Requerimientos de Funcionamiento
 
 1. La interfaz gráfica posee un botón a través del cual se inicia el **AsyncTask**.
 2. Al iniciarse el AsyncTask se debe mostrar una barra de progreso.
 3. La barra de progreso debe ser actualizada cada segundo.
 4. Al finalizar la cuenta que se realiza en el método **doInBackground** debe ocultarse la barra de progreso y presentarse en pantalla un mensaje indicando la finalización de la tarea.
 
 ####Layout del Activity
 
 El layout posee un boton y un **TextView** que debe mantenerse oculto hasta finalizar el **AsyncTask**.
 
 ```xml
 <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent" android:layout_height="match_parent">

    <TextView
        android:layout_width="match_parent"
        android:gravity="center"
        android:layout_height="0dp"
        android:layout_weight="4"
        android:text="New Text"
        android:textSize="40sp"
        android:id="@+id/textView"
        android:visibility="gone"/>

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Iniciar"
        android:onClick="iniciarTarea"
        android:id="@+id/button" />
</LinearLayout>
```
####Código del Activity

```java
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
```

Nótese que en el Activity se ha implementado un método `iniciarTarea` que está asociado al botón de la vista.

![Inicio](/capturas/inicio.png)![progreso](/capturas/progreso.png)![finalizada](/capturas/finalizada.png)