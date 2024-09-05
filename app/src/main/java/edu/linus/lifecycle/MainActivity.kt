package edu.linus.lifecycle

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    companion object{
        var defaultColor:String = "#ff00ff" //Magenta

    }
    private lateinit var tv:TextView;
    fun `test_23` (): String{
        return "empty"
    }
    fun linus(para:String = "hejsan"):Int {
        return 5;
    }

    fun merge(vararg arr:String) {
        for (x in arr) {
            Log.i("Linus", "x: $x")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //variabler
        var foo = 1

        var bar:Int = 5 //Har datatyp

        var foo2:String = "Hej";

        var decimalTal: Float = 3.22f

        var c:Char = 'S';

        var longTal:Long = 3232222;

        var dubbelTal:Double = 3333333333333333.0;

        var bool:Boolean = true;

        var x:Any = "hej"

        tv = findViewById(R.id.textView);

        val CONST_VALUE:Int = 1337;

        // int x,y,z = 15;
        var y:Int; var z:Int=5;

        var arr:IntArray = IntArray(5);
        var arr2:IntArray = intArrayOf(1,2,3,4,5);

        var sa:Array<String> = arrayOf("test", "blä")
        val unk:Any = 3;
        if (unk === 5) {
            Log.d("alrik", "onCreate: ")
        } else
            Log.d("Linus", "NO ")

        Log.d("Linus", if(1==1) "hej" else "nej")

        when (foo) {
            1 -> {
                Log.i("Linus", "onCreate: ")
            }
            else -> {
                Log.i("Linus", "No: ")
            }
        }
        var irange:IntRange = 0..10
        for (i in 0..10) {
            Log.i("Linus", "i$i")
        }
        merge("test", "test2", "test3")
        var h:Human = Human("test", "test2" as String);
        Log.i("Linus", (h is Human).toString())
        h.name = "YOY";
        var sss:String? = "hej";
        Log.i("Linus", "onCreate: " + sss!!);

        fun String.log() {
            val show = Toast.makeText(baseContext, this, Toast.LENGTH_LONG).show()
        }

        "test".log();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                val mailField = findViewById<EditText>(R.id.editTextTextEmailAddress)
                val passwordField = findViewById<EditText>(R.id.editTextTextPassword2)
                val button = findViewById<Button>(R.id.button)
                button.setOnClickListener{view ->
                    Log.i("Test", String.format("Login attempt... Mail: %s Password: %s", mailField.text, passwordField.text))
                }

                insets


        }
    }
}