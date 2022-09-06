package uz.alijonovz.startdroid21onlineshopping.screen.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.startdroid21onlineshopping.adapter.CartAdapter
import uz.alijonovz.startdroid21onlineshopping.databinding.FragmentCartBinding
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel
import uz.alijonovz.startdroid21onlineshopping.screen.MainViewModel
import uz.alijonovz.startdroid21onlineshopping.screen.makeorder.MakeOrderActivity
import uz.alijonovz.startdroid21onlineshopping.utils.BaseFragment
import uz.alijonovz.startdroid21onlineshopping.utils.Constants
import uz.alijonovz.startdroid21onlineshopping.utils.PrefUtils
import java.io.Serializable

class CartFragment : BaseFragment<FragmentCartBinding>() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCartBinding {
        return FragmentCartBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipe.isRefreshing = it
        })

        viewModel.productData.observe(requireActivity(), Observer {
            binding.recyclerView.adapter = CartAdapter(it)
        })
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        binding.btnMakeOrder.setOnClickListener {
            startActivity(Intent(requireActivity(), MakeOrderActivity::class.java))
        }
        binding.swipe.setOnRefreshListener {
            loadData()
        }

        binding.btnMakeOrder.setOnClickListener {
            val intent = Intent(requireActivity(), MakeOrderActivity::class.java)
            intent.putExtra(
                Constants.EXTRA_DATA,
                (viewModel.productData.value ?: emptyList<ProductModel>()) as Serializable
            )
            startActivity(intent)
        }
    }

    override fun loadData() {
        viewModel.loadProductByIds(PrefUtils.getCartList().map { it.product_id })
    }

    override fun loadUpdate() {

    }


    companion object {
        @JvmStatic
        fun newInstance() = CartFragment()
    }
}