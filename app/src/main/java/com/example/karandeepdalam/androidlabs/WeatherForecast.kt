package com.example.karandeepdalam.androidlabs

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_weather_forecast.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.net.MalformedURLException


class WeatherForecast : Activity() {

     var windValue : String =""
     var tempValue : String=""
     var tempMinValue : String=""
     var tempMaxValue : String=""
     var iconName : String=""

    lateinit var image: ImageView       // you gonna set it later
    lateinit var currentTemp : TextView
    lateinit var minTemp : TextView
    lateinit var maxTemp : TextView
    lateinit var windSpeed : TextView
    lateinit var progress: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        image = findViewById<ImageView>(R.id.currentWeather)
        currentTemp = findViewById<TextView>(R.id.currentTemp)
        minTemp = findViewById<TextView>(R.id.minTemp)
        maxTemp = findViewById<TextView>(R.id.maxTemp)
        windSpeed = findViewById<TextView>(R.id.windSpeed)
        progress = findViewById<ProgressBar>(R.id.progress)


        progress.visibility = View.VISIBLE
        var myQuery = ForecastQuery()
        myQuery.execute("")     // runs query
    }

    inner class ForecastQuery : AsyncTask<String, Integer, String> () {
       var progressInt = 0
        var weatherImage: Bitmap? = null

        override fun doInBackground(vararg params: String?): String {
            try {
                var url = URL("http://api.openweathermap.org/data/iWatch.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric")
                val urlConnection = url.openConnection() as HttpURLConnection
                val stream = urlConnection.getInputStream()

                val factory = XmlPullParserFactory.newInstance()
                factory.setNamespaceAware(false)
                val xpp = factory.newPullParser()
                xpp.setInput( stream  , "UTF-8")  // set the input stream


                while(xpp.eventType != XmlPullParser.END_DOCUMENT)          // check if we are not at the end of the document
                {
                    when(xpp.eventType)
                    {
                        XmlPullParser.START_TAG ->{
                            if(xpp.name.equals("speed")) {
                                windValue = xpp.getAttributeValue(null, "value")
                                progressInt += 20
                                publishProgress()
                            }

                            else if(xpp.name.equals("temperature")) {
                                tempValue = xpp.getAttributeValue(null, "value")
                                progressInt += 20

                                tempMinValue = xpp.getAttributeValue(null, "min")
                                progressInt += 20

                                tempMaxValue = xpp.getAttributeValue(null, "max")
                                progressInt += 20
                                publishProgress()

                            }

                            else if(xpp.name.equals("weather")) {
                                iconName = xpp.getAttributeValue(null, "icon")
                                var weatherURL = "http://openweathermap.org/img/w/$iconName.png"
                                weatherImage = getImage(weatherURL)

                                if(fileExistance( "$iconName.png") )//already downloaded
                                    {
                                        var fis: FileInputStream? = null
                                        try {    fis = openFileInput("$iconName.png")   }
                                        catch (e: FileNotFoundException) {    e.printStackTrace()  }
                                        weatherImage = BitmapFactory.decodeStream(fis)
                                    }
                                    else //download it
                                    {
                                        weatherImage = getImage(weatherURL)
                                        val outputStream = openFileOutput( "$iconName.png", Context.MODE_PRIVATE);
                                        weatherImage?.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                        outputStream.flush();
                                        outputStream.close();
                                    }

                                progressInt += 20
                                publishProgress()

                            }
                            //var weatherURL = "http://openweathermap.org/img/w/$iconName.png"

                            publishProgress()   //causes android to call onProgressUpdate when GUI is ready
                        }
                        XmlPullParser.TEXT->{      }
                    }
                    xpp.next()      // move to next tag
                }
            }
            catch ( e:Exception)
            {
                Log.i("Error","Error")
            }
            return "Done"
        }


        fun fileExistance(fname : String):Boolean{
            val file = getBaseContext().getFileStreamPath(fname)
            return file.exists()   }


        override fun onProgressUpdate(vararg values: Integer?) {        //update your gui

            currentTemp.setText("Current Tempearture : "+ tempValue)
            minTemp.setText("Minimum Tempearture : "+tempMinValue)
            maxTemp.setText("Maximum Tempearture : "+tempMaxValue)
            windSpeed.setText("Wind Speed : "+windValue)

            progress.progress = progressInt

        }

        override fun onPostExecute(result: String?) {
            image.setImageBitmap(weatherImage)
        }

        fun getImage(url: URL): Bitmap? {
            var connection: HttpURLConnection? = null
            try {
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val responseCode = connection.responseCode
                return if (responseCode == 200) {
                    BitmapFactory.decodeStream(connection.inputStream)
                } else
                    null
            } catch (e: Exception) {
                return null
            } finally {
                connection?.disconnect()
            }
        }

        fun getImage(urlString: String): Bitmap? {
            try {
                val url = URL(urlString)
                return getImage(url)
            } catch (e: MalformedURLException) {
                return null
            }

        }
    }
}
