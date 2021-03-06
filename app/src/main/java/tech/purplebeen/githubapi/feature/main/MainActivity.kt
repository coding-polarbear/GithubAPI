package tech.purplebeen.githubapi.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import tech.purplebeen.githubapi.R
import tech.purplebeen.githubapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val adapter: GithubReccylerAdapter = GithubReccylerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        initRecyclerView()
        observeViewModel()
        handleScheme(intent.dataString)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            handleScheme(it.dataString)
        }
    }

    private fun handleScheme(dataString: String?) {
        viewModel.loadUserInfo(viewModel.parseString(dataString))
    }

    private fun initRecyclerView() {
        binding.recyclerview.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.loadFinishEvent.observe(this) {
            adapter.submitList(viewModel.dataList)
        }
    }
}