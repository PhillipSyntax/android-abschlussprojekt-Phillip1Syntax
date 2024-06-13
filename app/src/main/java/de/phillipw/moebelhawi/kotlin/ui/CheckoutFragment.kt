package de.phillipw.moebelhawi.kotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import de.phillipw.moebelhawi.kotlin.HomeViewModel
import de.phillipw.moebelhawi.kotlin.adapter.CheckoutAdapter
import de.phillipw.moebelhawi.kotlin.databinding.FragmentCheckoutBinding


class CheckoutFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding

    private val viewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(layoutInflater)
        viewModel.getCartItems()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cartItems.observe(viewLifecycleOwner) { shoppingCartItem ->
            viewModel.calculateTotalPrice()
            binding.rvImageCheckout.adapter = CheckoutAdapter(shoppingCartItem)
        }
        viewModel.totalPrice.observe(viewLifecycleOwner) { totalPrice ->
            binding.tvPriceCheckout.text = "Total Price: %.2f €".format(totalPrice)


        }
    }
}