package com.example.filtrarnumeros

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

fun filtrar(array : Array<Int>, operacion:(Int)->Boolean): Array<Int>
{
    //Defino una colección que va almacenar los valores
    //del array que cumplen con la operacion
    var array_resultado=mutableListOf<Int>()
    //Recorro el array y voy comprobando uno a uno los elementos
    //de ese array
    for(elemento in array)
    {
        if(operacion(elemento)){
            array_resultado.add(elemento)
        }
    }
    return array_resultado.toTypedArray()

}
class MainActivity : AppCompatActivity() {
    //Declaramos tantos objetos de Views como componentes quiera
    //acceder
    lateinit var tv_arraysinfiltrar: TextView
    lateinit var tv_arrayfiltrado: TextView
    lateinit var rg_filtrarnumeros: RadioGroup
    //Defino el Array de enteros que sirve como base para filtrar
    val miarrayenteros=Array<Int>(10){Random.nextInt(1,1000)}


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarComponentes()

    }

    fun inicializarComponentes(){
        //Instancio los objetos que representan componentes
        //visuales o views
        this.tv_arrayfiltrado=findViewById<TextView>(R.id.arrayfiltradoTV)
        this.tv_arraysinfiltrar=findViewById<TextView>(R.id.arraysinFiltrarTV)
        this.rg_filtrarnumeros=findViewById<RadioGroup>(R.id.radioGroup)
        this.tv_arraysinfiltrar.text=miarrayenteros.contentToString()

        //Establezco los escuchadores que pueda necesitar
        //Solo establezco escuchador para el RadioGroup
        rg_filtrarnumeros.setOnCheckedChangeListener { group, i ->  }
        var miobjeto_escuchador= claseListenerRadioGroup(miarrayenteros,tv_arrayfiltrado)
        rg_filtrarnumeros.setOnCheckedChangeListener(miobjeto_escuchador)


    }
}
class claseListenerRadioGroup(val miarray:Array<Int>,val miTextView: TextView):RadioGroup.OnCheckedChangeListener {

    override fun onCheckedChanged(p0: RadioGroup, id_RadioButton: Int) {

        when (id_RadioButton) {
            R.id.filtrarPrimosRB -> miTextView.text = filtrar(miarray) { n: Int ->
                if (n <= 1) {
                    false
                } else {
                    //!(2 until n).any ({ i -> n % i == 0 })
                    var es_primo = true
                    var cont = 2
                    while (es_primo && cont < (n / 2)) {
                        if (n % cont == 0) {
                            es_primo = false
                        }
                        cont++

                    }
                    es_primo
                }
            }.contentToString()

            R.id.filtrarMagicosRB -> miTextView.text = filtrar(miarray) { n ->
                var cubo = n * n * n
                var suma = 0
                while (cubo > 0) {
                    suma += cubo % 10
                    cubo /= 10
                }
                n == suma
            }.contentToString()

            else -> miTextView.text =
                filtrar(miarray) { it.toString() == it.toString().reversed() }.contentToString()


        }

    }
}