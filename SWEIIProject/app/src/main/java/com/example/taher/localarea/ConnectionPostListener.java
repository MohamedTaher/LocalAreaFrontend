package com.example.taher.localarea;

/**
 * Created by taher on 20/03/16.
 */

//When you execute a service, this is where you process the response, you have to implement this function.
// this function is called after the doInBackground function finished its work.
public interface ConnectionPostListener {
    public void doSomething(String result);
}
