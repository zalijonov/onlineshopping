package uz.alijonovz.startdroid21onlineshopping.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.startdroid21onlineshopping.adapter.*
import uz.alijonovz.startdroid21onlineshopping.databinding.FragmentHomeBinding
import uz.alijonovz.startdroid21onlineshopping.model.CategoryModel
import uz.alijonovz.startdroid21onlineshopping.screen.MainViewModel
import uz.alijonovz.startdroid21onlineshopping.utils.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView() {

        binding.swipe.setOnRefreshListener {
            loadData()
        }
        viewModel.error.observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(requireActivity(), Observer {
            binding.swipe.isRefreshing = it
        })

        viewModel.offersData.observe(requireActivity(), Observer {
            binding.viewPager.adapter = ViewPagerAdapter(requireActivity(), it)
            binding.viewPager.autoScroll(2000)
        })

        viewModel.categoriesData.observe(requireActivity(), Observer {
            binding.category.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            binding.category.adapter = CategoryAdapter(it, object : CategoryAdapterCallback {
                override fun onClickItem(item: CategoryModel) {
                    viewModel.loadCategoryProducts(item.id)
                }
            })
        })

        viewModel.topProductData.observe(requireActivity(), Observer {
            binding.tvProduct.layoutManager = LinearLayoutManager(requireActivity())
            binding.tvProduct.adapter = ProductAdapter(it)
        })

    }

    override fun loadData() {
//        viewModel.loadCategories()
        viewModel.loadOffers()
//        viewModel.loadTopProducts()
        viewModel.loadAllDBProducts()
        viewModel.loadAllDBCategories()

    }

    override fun loadUpdate() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}