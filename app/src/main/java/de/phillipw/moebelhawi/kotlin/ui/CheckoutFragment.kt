package de.phillipw.moebelhawi.kotlin.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import de.phillipw.moebelhawi.kotlin.HomeViewModel
import de.phillipw.moebelhawi.kotlin.R
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

        binding.btnConfirm.setOnClickListener {

            val correctName = "^[a-zA-Z ]+$".toRegex()

            val correctPlz = "^[0-9]{5}$".toRegex()

            val firstName = binding.firstNameEt.text.toString()
            val secondName = binding.secondNameEt.text.toString()
            val adress = binding.addressEt.text.toString()
            val place = binding.placeEt.text.toString()
            val zip = binding.zipEt.text.toString()
            if (firstName.isNotBlank() && firstName.matches(correctName) && secondName.isNotBlank() && secondName.matches(correctName)
                && adress.isNotBlank()
                && place.isNotBlank() && zip.isNotBlank() && zip.matches(correctPlz)
            ) {
                viewModel.deleteAllCartItems()
                showAlert()
                findNavController().navigate(R.id.homeFragment)
            } else {
                if (firstName.isBlank() || !firstName.matches(correctName)) {
                    Toast.makeText(requireContext(), "please fill a correct first name", Toast.LENGTH_SHORT)
                        .show()
                } else if (secondName.isBlank() || !secondName.matches(correctName)) {
                    Toast.makeText(
                        requireContext(),
                        "please fill a correct second name",
                        Toast.LENGTH_SHORT)
                        .show()

                } else if (zip.isBlank() || !zip.matches(correctPlz)) {
                    Toast.makeText(requireContext(), "please fill a coorect zip",Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(), "please fill all fields!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        //builder.setView(R.layout.) NICHT VERGESSEN ZU IMPLEMENTIERT
        builder.setTitle("Vielen Dank für Ihre Bestellung!")
        builder.setMessage("Sie erhalten per E-Mail eine Bestätigung.")

        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()

            // Positive Button Click Logic
        }
//        builder.setNegativeButton("Abbrechen") { dialog, which ->
//            // Negative Button Click Logic
//            dialog.dismiss()
//        }
        builder.show()
    }
}