package com.example.ejerciciopractico

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ejerciciopractico.Productos.Producto

class ProductosAdapter(private var productos: List<Producto>) : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {




    class ProductoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.nombre)
        val precio: TextView = view.findViewById(R.id.precio)

        // Agrega más vistas si es necesario
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.producto_item, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombre.text = producto.nombre
        holder.precio.text = producto.precio.toString()
        // Configura más vistas si es necesario
    }

    override fun getItemCount() = productos.size

    fun updateProductos(productos: List<Producto>) {
        this.productos = productos
        notifyDataSetChanged()
    }
}