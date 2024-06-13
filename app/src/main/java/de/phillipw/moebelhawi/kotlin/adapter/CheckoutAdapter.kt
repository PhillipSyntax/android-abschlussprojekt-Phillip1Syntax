package de.phillipw.moebelhawi.kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.phillipw.moebelhawi.kotlin.data.models.ShoppingCartItem
import de.phillipw.moebelhawi.kotlin.databinding.ItemCheckoutBinding

class CheckoutAdapter (

    private val dataset: List<ShoppingCartItem>


): RecyclerView.Adapter<CheckoutAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(val binding: ItemCheckoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemCheckoutBinding.inflate(layoutInflater,parent,false)
        return ItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val checkoutArea = dataset[position]

        holder.binding.ivCheckout.load(checkoutArea.imageUrl)
        holder.binding.tvQuantityCheckout.text = "${checkoutArea.quantity}x"

    }
    override fun getItemCount(): Int {
        return dataset.size
    }
}