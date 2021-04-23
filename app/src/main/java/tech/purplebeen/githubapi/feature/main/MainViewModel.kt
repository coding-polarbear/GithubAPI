package tech.purplebeen.githubapi.feature.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tech.purplebeen.githubapi.model.Data
import tech.purplebeen.githubapi.model.ViewType
import tech.purplebeen.githubapi.network.GithubService
import tech.purplebeen.githubapi.util.SingleLiveEvent

class MainViewModel: ViewModel() {
    companion object {
        val TAG: String = MainViewModel::class.java.simpleName
    }


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    private val githubService: GithubService = retrofit.create(GithubService::class.java)

    private val _dataList: ArrayList<Data> = ArrayList()
    val dataList: ArrayList<Data>
        get() = _dataList

    private val _loadFinishEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val loadFinishEvent: SingleLiveEvent<Void>
        get() = _loadFinishEvent

    @SuppressLint("CheckResult")
    fun loadUserInfo(userName: String) {
        githubService.getUserInfo(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { response ->
                val data = Data(ViewType.USER_TYPE, response.name)
                data.profile = response.profileUrl
                _dataList.add(data)
                return@flatMap githubService.getRepoList(userName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .subscribe({ response ->
                for (item in response) {
                    val data = Data(ViewType.REPOSITORY_TYPE, item.name)
                    data.description = item.description
                    data.starCount = item.startCount
                    _dataList.add(data)
                }
                loadFinishEvent.call()
            }, { error ->
                Log.e(TAG, error.message.toString())
            })
    }

    fun parseString(scheme: String?): String {
        scheme?.let {
            val array = scheme.split("/")
            val username = array[array.size - 1]
            return username
        }
        return ""
    }
}