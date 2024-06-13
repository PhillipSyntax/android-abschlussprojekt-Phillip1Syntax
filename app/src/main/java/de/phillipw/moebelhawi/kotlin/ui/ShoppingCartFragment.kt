package de.phillipw.moebelhawi.kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.phillipw.moebelhawi.kotlin.HomeViewModel
import de.phillipw.moebelhawi.kotlin.R
import de.phillipw.moebelhawi.kotlin.adapter.ShoppingCartItemAdapter
import de.phillipw.moebelhawi.kotlin.databinding.FragmentShoppingCartBinding


class ShoppingCartFragment : Fragment() {

    private lateinit var binding: FragmentShoppingCartBinding

    private val viewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShoppingCartBinding.inflate(layoutInflater)
        viewModel.getCartItems()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cartItems.observe(viewLifecycleOwner) { shoppingCartItem ->
            viewModel.calculateTotalPrice()
            binding.rvItemlist.adapter = ShoppingCartItemAdapter(shoppingCartItem) {
                viewModel.deleteItemFromShoppingCart(it)
                viewModel.updateCartItem(it)
            }
        }
        viewModel.totalPrice.observe(viewLifecycleOwner) {totalPrice ->
            binding.tvTotalPrice.text = "Total Price: %.2f €".format(totalPrice)
        }
        binding.btnCheckout.setOnClickListener{
            val action = ShoppingCartFragmentDirections.cartToCheckout()
            findNavController().navigate(action)
        }
    }
}