package com.example.ejerciciopractico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log




class Productos : AppCompatActivity() {

    private lateinit var productosAdapter: ProductosAdapter
    private lateinit var productosRecyclerView: RecyclerView

    data class Producto(
        val id: String = "",
        val nombre: String = "",
        val precio: Double = 0.0

    )
    companion object {
        private const val TAG = "Productos"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        val recyclerView = findViewById<RecyclerView>(R.id.productos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        productosAdapter = ProductosAdapter(listOf())
        recyclerView.adapter = productosAdapter

        cargarProductosDesdeFirestore()

    }


    private fun cargarProductosDesdeFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("productos")
            .get()
            .addOnSuccessListener { result ->
                val productos = result.map { document -> document.toObject(Producto::class.java) }
                productosAdapter.updateProductos(productos)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}