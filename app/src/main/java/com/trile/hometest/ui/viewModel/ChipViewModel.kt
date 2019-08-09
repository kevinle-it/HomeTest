package com.trile.hometest.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trile.hometest.R
import com.trile.hometest.store.room.entity.ChipEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

/**
 *
 * @author lmtri
 * @since Aug 06, 2019 at 5:33 PM
 */
class ChipViewModel(private val app: Application) : AndroidViewModel(app) {
    private val mRepository = ChipRepository(app)
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _mChipListLiveData: MutableLiveData<List<ChipEntity>> = MutableLiveData()
    val mChipListLiveData: LiveData<List<ChipEntity>> = _mChipListLiveData

    private val _mErrorLiveData: MutableLiveData<String> = MutableLiveData()
    val mErrorLiveData: LiveData<String> = _mErrorLiveData

    fun loadData() {
        val maybeDB = mRepository.getChipListFromDB()

        val disposableDB =
                maybeDB.observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ itemList: List<ChipEntity>? ->
                            itemList?.let { _mChipListLiveData.value = it }
                            callAPI()
                        }, { _: Throwable? ->
                            // Nothing to do
                        }, {
                            callAPI()
                        })

        disposableDB.let { mCompositeDisposable.add(it) }
    }

    private fun callAPI() {
        val maybeAPI = mRepository.getChipListFromAPI()

        val disposableAPI =
                maybeAPI.observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ itemList: List<ChipEntity>? ->
                            // Save server response to DB then show item list on UI
                            mRepository.saveToDB(itemList)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe { items: List<ChipEntity>?, t: Throwable? ->
                                        items?.let {
                                            _mChipListLiveData.value = it
                                        }
                                        t?.let {
                                            it.printStackTrace()
                                        }
                                    }
                        }, { t: Throwable? ->
                            t?.printStackTrace()
                        }, {
                            _mErrorLiveData.value = app.getString(R.string.empty_server_response)
                        })

        disposableAPI.let { mCompositeDisposable.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.dispose()
    }
}